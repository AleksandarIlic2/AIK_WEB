package rs.aik.testautomation.AIKTestAutomation.Helpers;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.AndTerm;
import jakarta.mail.search.FromStringTerm;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SubjectTerm;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailReaderService {

    private final String host;
    private final String email;
    private final String password;
    private final int port;

    public EmailReaderService(String host, String email, String password, int port) {
        this.host = host;
        this.email = email;
        this.password = password;
        this.port = port;
    }

    public List<String> getLastMessages(int count) throws MessagingException {
        List<String> results = new ArrayList<>();

        try (Store store = connectToStore()) {
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int total = inbox.getMessageCount();
            int from = Math.max(1, total - count + 1);
            Message[] messages = inbox.getMessages(from, total);

            for (Message msg : messages) {
                results.add(extractText(msg));
            }

            inbox.close(false);
        } catch (Exception e) {
            throw new MessagingException("Greška pri čitanju emailova: " + e.getMessage(), e);
        }

        return results;
    }

    public String getLatestMessage() throws MessagingException {
        List<String> messages = getLastMessages(1);
        return messages.isEmpty() ? null : messages.get(0);
    }

    public String getMessageBySender(String senderEmail) throws MessagingException {
        return searchMessage(new FromStringTerm(senderEmail));
    }

    public String getMessageBySubject(String subject) throws MessagingException {
        return searchMessage(new SubjectTerm(subject));
    }

    public String getMessageBySenderAndSubject(String senderEmail, String subject) throws MessagingException {
        SearchTerm searchTerm = new AndTerm(
                new FromStringTerm(senderEmail),
                new SubjectTerm(subject)
        );
        return searchMessage(searchTerm);
    }

    public String waitForEmail(String senderEmail, String subject, int timeoutMinutes)
            throws MessagingException, InterruptedException {

        Date startTime = new Date();
        long timeoutMillis = timeoutMinutes * 60 * 1000L;
        int pollingIntervalSeconds = 10;

        System.out.println("Čekam email od: " + senderEmail + " | Subject: " + subject);

        while (System.currentTimeMillis() - startTime.getTime() < timeoutMillis) {

            try (Store store = connectToStore()) {
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_WRITE);

                SearchTerm searchTerm = new AndTerm(
                        new FromStringTerm(senderEmail),
                        new SubjectTerm(subject)
                );

                Message[] messages = inbox.search(searchTerm);

                // Ručno filtriramo po tačnom vremenu
                Message found = null;
                for (Message msg : messages) {
                    Date receivedDate = msg.getReceivedDate();
                    if (receivedDate != null && receivedDate.after(startTime)) {
                        if (found == null || receivedDate.after(found.getReceivedDate())) {
                            found = msg;
                        }
                    }
                }

                if (found != null) {
                    System.out.println("Email pronađen!");
                    String result = extractText(found);

                    // Obriši email
                    found.setFlag(Flags.Flag.DELETED, true);
                    inbox.close(true); // true = expunge, trajno briše

                    return result;
                }

                inbox.close(false);

            } catch (Exception e) {
                throw new MessagingException("Greška pri čekanju emaila: " + e.getMessage(), e);
            }

            System.out.println("Email još nije stigao, čekam " + pollingIntervalSeconds + " sekundi...");
            Thread.sleep(pollingIntervalSeconds * 1000L);
        }

        throw new MessagingException("Timeout! Email nije stigao u roku od " + timeoutMinutes + " minuta.");
    }

    private Store connectToStore() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.imap.host", host);
        props.put("mail.imap.port", String.valueOf(port));
        props.put("mail.imap.starttls.enable", "true");
        props.put("mail.imap.ssl.enable", "false");

        Session session = Session.getInstance(props);
        Store store = session.getStore("imap");
        store.connect(host, email, password);
        return store;
    }

    private String searchMessage(SearchTerm searchTerm) throws MessagingException {
        try (Store store = connectToStore()) {
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(searchTerm);

            if (messages.length == 0) {
                return null;
            }

            Message latest = messages[messages.length - 1];
            String result = extractText(latest);

            inbox.close(false);
            return result;

        } catch (Exception e) {
            throw new MessagingException("Greška pri pretrazi emailova: " + e.getMessage(), e);
        }
    }

    private String extractText(Part part) throws Exception {
        if (part.isMimeType("text/plain")) {
            return (String) part.getContent();

        } else if (part.isMimeType("text/html")) {
            String html = (String) part.getContent();
            return extractTextAndLinks(html);

        } else if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                sb.append(extractText(multipart.getBodyPart(i)));
            }
            return sb.toString();
        }

        return "";
    }

    private String extractTextAndLinks(String html) {
        Document doc = Jsoup.parse(html);
        StringBuilder sb = new StringBuilder();

        for (Element element : doc.body().getAllElements()) {
            if (element.tagName().equals("a")) {
                String text = element.text();
                String href = element.attr("href");
                if (!href.isEmpty()) {
                    sb.append(text).append(" [").append(href).append("] ");
                }
            }
        }

        sb.append("\n").append(doc.body().text());

        return sb.toString().trim();
    }
}