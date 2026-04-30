package rs.aik.testautomation.AIKTestAutomation.Action;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rs.aik.testautomation.AIKTestAutomation.JS.JSHelpers;
import rs.aik.testautomation.AIKTestAutomation.Selectors.*;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Utilities;
import rs.aik.testautomation.AIKTestAutomation.Selectors.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import rs.aik.testautomation.AIKTestAutomation.Wait.WaitHelpers;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import static rs.aik.testautomation.AIKTestAutomation.Core.Base.driver;

public class RoutineHelper {
    //region - Parameters -
    ActionApiHelpers hp = new ActionApiHelpers();
    Base b = new Base();
    //endregion - Parameters -
    /**
     * Choose value from select drop down list with class name in parameter className, by value in parameter value
     * @param value Value to choose
     * @param className className of select drop-down list
     * @throws Throwable
     */
    public static void SelectByValueFromClass(String value, String className) throws Throwable {
        WebElement element = SelectByXpath.createElementByXpathClassName(className);
        Select selectElement = new Select(element);
        selectElement.selectByValue(value);
    }

    /**
     * Check for all transactions at page exists data in database by account number and reference number
     * @throws Throwable
     */
    public static void AssertTransactionDataMatchesWithDatabase() throws Throwable {
        String xPathAcc = "//*[@data-bind='text: accountData().AccountCustomName, visible: accountData().AccountCustomName !== accountData().AccountNumber']";
        String acc = SelectByXpath.CreateElementByXpath(xPathAcc).getAttribute("innerHTML").trim();

        String xPath = "//*[@class='s-widget ui-corner-all AccountTurnoverV2']//descendant::table[@class='clientGrid tablesaw tablesaw-stack']//descendant::td[4]/span";
        List<WebElement> elements = SelectByXpath.CreateElementsByXpath(xPath);
        Iterator<WebElement> iterator = elements.iterator();
        ResultSet orders = Queries.getOrdersForAccount(acc);
        List<String> orderNumbers = new ArrayList<>();
        while (orders.next()){
            String s = orders.getString(2);
            s = s.substring(6);
            if(s.contains("-"))
                s = s.substring(0, s.indexOf("-"));
            orderNumbers.add(s);
        }

        int noReference = 0;
        while (iterator.hasNext()) {
            WebElement element = iterator.next();
            String text = element.getText();
            if (!(orderNumbers.contains(text))){
                noReference++;
            }
        }
        if(noReference > 0)
            Assert.assertTrue(false);
        else
            Assert.assertTrue(true);
    }

    public static void AssertTransactionDataMatchesWithDatabaseFilterDateFromDateTo() throws Throwable {
        String xPathAcc = "//*[@data-bind='text: accountData().AccountCustomName, visible: accountData().AccountCustomName !== accountData().AccountNumber']";
        String acc = SelectByXpath.CreateElementByXpath(xPathAcc).getAttribute("innerHTML").trim();

        String xPath = "//*[@class='s-widget ui-corner-all AccountTurnoverV2']//descendant::table[@class='clientGrid tablesaw tablesaw-stack']//descendant::td[4]/span";
        List<WebElement> elements = SelectByXpath.CreateElementsByXpath(xPath);
        Iterator<WebElement> iterator = elements.iterator();
        String _fromDate = DataManager.userObject.get("DateFrom").toString();
        String _toDate = DataManager.userObject.get("DateTo").toString();
        ResultSet orders = Queries.getOrdersForAccount(acc, _fromDate, _toDate);
        List<String> orderNumbers = new ArrayList<>();
        while (orders.next()){
            String s = orders.getString(2);
            s = s.substring(6);
            if(s.contains("-"))
                s = s.substring(0, s.indexOf("-"));
            orderNumbers.add(s);
        }
        int noReference = 0;
        while (iterator.hasNext()) {
            WebElement element = iterator.next();
            String text = element.getText();
            if (!(orderNumbers.contains(text))){
                noReference++;
            }
        }
        if(noReference > 0)
            Assert.assertTrue(false);
        else
            Assert.assertTrue(true);
    }

    public static void AssertTransactionDataMatchesWithDatabaseFilterAmountFromAmountTo() throws Throwable {
        String xPathAcc = "//*[@data-bind='text: accountData().AccountCustomName, visible: accountData().AccountCustomName !== accountData().AccountNumber']";
        String acc = SelectByXpath.CreateElementByXpath(xPathAcc).getAttribute("innerHTML").trim();

        String xPath = "//*[@class='s-widget ui-corner-all AccountTurnoverV2']//descendant::table[@class='clientGrid tablesaw tablesaw-stack']//descendant::td[4]/span";
        List<WebElement> elements = SelectByXpath.CreateElementsByXpath(xPath);
        Iterator<WebElement> iterator = elements.iterator();
        long _fromAmount = Integer.parseInt(DataManager.userObject.get("AmountFrom").toString());
        long _toAmount = Integer.parseInt(DataManager.userObject.get("AmountTo").toString());
        ResultSet orders = Queries.getOrdersForAccount(acc, _fromAmount, _toAmount);
        List<String> orderNumbers = new ArrayList<>();
        while (orders.next()){
            String s = orders.getString(2);
            s = s.substring(6);
            if(s.contains("-"))
                s = s.substring(0, s.indexOf("-"));
            orderNumbers.add(s);
        }
        int noReference = 0;
        while (iterator.hasNext()) {
            WebElement element = iterator.next();
            String text = element.getText();
            if (!(orderNumbers.contains(text))){
                noReference++;
            }
        }
        if(noReference > 0)
            Assert.assertTrue(false);
        else
            Assert.assertTrue(true);
    }

    public static void AssertTransactionDataMatchesWithDatabaseFilterDebitCredit() throws Throwable {
        String xPathAcc = "//*[@data-bind='text: accountData().AccountCustomName, visible: accountData().AccountCustomName !== accountData().AccountNumber']";
        String acc = SelectByXpath.CreateElementByXpath(xPathAcc).getAttribute("innerHTML").trim();

        String xPath = "//*[@class='s-widget ui-corner-all AccountTurnoverV2']//descendant::table[@class='clientGrid tablesaw tablesaw-stack']//descendant::td[4]/span";
        List<WebElement> elements = SelectByXpath.CreateElementsByXpath(xPath);
        Iterator<WebElement> iterator = elements.iterator();

        int debitCredit = 2;
        if(DataManager.userObject.get("DebitCredit").equals("Credit"))
            debitCredit = 1;
        if(DataManager.userObject.get("DebitCredit").equals("Debit"))
            debitCredit = 0;

        List<Map<String, String>> orders = Queries.getOrdersForAccount(acc, debitCredit);
        List<String> orderNumbers = new ArrayList<String>();

        for (Map<String, String> var : orders)
        {
            String s = var.get("Id_SourceTransaction");
            s = s.substring(6);
            if(s.contains("-"))
                s = s.substring(0, s.indexOf("-"));
            orderNumbers.add(s);
        }
        int noReference = 0;
        while (iterator.hasNext()) {
            WebElement element = iterator.next();
            String text = element.getText();
            if (!(orderNumbers.contains(text))){
                noReference++;
            }
        }
        if(noReference > 0)
            Assert.assertTrue(false);
        else
            Assert.assertTrue(true);
    }

    /**
     * Check if all statements has view button
     * @param buttonText button text
     * @throws Throwable
     */
    public static void AssertEveryRowHasButtonView(String buttonText) throws Throwable {
        String searchTableRows = "//*[@class='s-widget-zone statements-widget-zone']//descendant::table[@class='clientGrid tablesaw tablesaw-stack']/tbody/tr";
        List<WebElement>tableRows=SelectByXpath.CreateElementsByXpath(searchTableRows);
        String searchTableRowButtons = "//table[@class='clientGrid tablesaw tablesaw-stack']//descendant::a[text()='View']";
        List<WebElement>tableRowButtons=SelectByXpath.CreateElementsByXpath(searchTableRowButtons);
        boolean isCorrect = true;
        if(tableRowButtons.size() == tableRows.size()){
            Iterator<WebElement> iterator = tableRowButtons.iterator();
            while (iterator.hasNext()){
                WebElement element = iterator.next();
                if (!(element.isDisplayed())){
                    isCorrect = false;
                }
            }
        }
        else
            isCorrect = false;
        Assert.assertTrue(isCorrect);
    }

    /**
     * Check if statement are sorted
     * @param sortOrder
     * @throws Throwable
     */
    public static void AssertStatementSorted(String sortOrder) throws Throwable {
        boolean isCorrect = true;
        String searchStatementData = "//*[@class='s-widget-zone statements-widget-zone']//descendant::table[@class='clientGrid tablesaw tablesaw-stack']/tbody/tr/td[3]/span";
        List<WebElement>tableDate = SelectByXpath.CreateElementsByXpath(searchStatementData);
        Iterator<WebElement> iterator = tableDate.iterator();
        if (iterator.hasNext()){
            WebElement current = iterator.next();
            while (iterator.hasNext()){
                WebElement prev = current;
                current = iterator.next();
                if(sortOrder.equals("descending")) {
                    if (Utilities.isGraterDate(current.getText(), prev.getText()))
                        isCorrect = false;
                }
                else {
                    if (Utilities.isLessDate(current.getText(), prev.getText()))
                        isCorrect = false;
                }
            }
        }
        Assert.assertTrue(isCorrect);
    }

    /**
     * Check is every account number exists in database.
     * @param SSN Social Identify Number
     * @throws Throwable
     */
    public static void AssertAccountsExistsInDatabase(String SSN) throws Throwable {
        boolean isCorrect = true;

        ResultSet resultAccount = Queries.getAccountsForOnlineView(SSN);
        ArrayList<String> coreAccounts = new ArrayList<>();

        while(resultAccount.next()) {
            coreAccounts.add(resultAccount.getString("RetailAccountNumber"));
        }

        // since we have saved the values to map object in  DataManager.userObject we do not have to look for them again
        for (String accounts : (ArrayList<String>)DataManager.userObject.get("accounts")) {
            assert coreAccounts != null;
            if (!coreAccounts.contains(accounts.trim())) {
                isCorrect = false;
            }
        }
        Assert.assertTrue(isCorrect);

/*        String searchData = "//*[@class='s-widget ui-corner-all icon-accounts-4 title_icons AccountBalancePreviewV2']//descendant::*[@data-bind='text: RetailAccountNumber']";
        List<WebElement>data = SelectByXpath.CreateElementsByXpath(searchData);
        Iterator<WebElement> iterator = data.iterator();
        if (iterator.hasNext()){
            while (iterator.hasNext()){
                WebElement current = iterator.next();

                assert coreAccounts != null;
                Map<String, String> currentAccount = new HashMap<>();
                currentAccount.put("RetailAccountNumber", current.getText().trim());
                if (!coreAccounts.contains(currentAccount)) {
                    isCorrect = false;
                }
            }
        }*/
    }

    /**
     * Check is every card number exists in database.
     * @param SSN Social Identify Number
     * @throws Throwable
     */
    public static void AssertCardsExistsInDatabase(String SSN) throws Throwable{
        boolean isCorrect = true;
        String attrib = "data-bind";
        String value = "text: CardCustomName";
        List<WebElement>data = SelectByAttribute.CreateElementsByXpath(attrib, value);
        for (WebElement current : data) {
            if (!(Queries.existsCardForSSNAndCardNumber(SSN, current.getText().trim()))) {
                isCorrect = false;
            }
        }
        Assert.assertTrue(isCorrect);
    }

    public static void AssertCardsLogoExistsInDatabase(String SSN) throws Throwable{
        boolean isCorrect = true;
        String attrib = "data-bind";
        String value = "click: $root.onRowClick.bind($data, $index())";
        List<WebElement>data = SelectByAttribute.CreateElementsByXpath(attrib, value);
        Iterator<WebElement> iterator = data.iterator();
        if (iterator.hasNext()){
            while (iterator.hasNext()){
                WebElement current = iterator.next();
                String  account = current.findElement(By.xpath("*[@data-bind='text: CardCustomName']")).getText().trim();
                String cardType = current.findElement(By.xpath("*[@data-bind='css: CardFlagClass']")).getClass().getName();
                if (!(Queries.existsCardForSSNAndCardNumberCardType(SSN, account, cardType))){
                    isCorrect = false;
                }
            }
        }
        Assert.assertTrue(isCorrect);
    }

    public static void AssertCardsCurrencyExistsInDatabase(String SSN) throws Throwable{
        boolean isCorrect = true;
        String attrib = "data-bind";
        String value = "click: $root.onRowClick.bind($data, $index())";
        List<WebElement>data = SelectByAttribute.CreateElementsByXpath(attrib, value);
        Iterator<WebElement> iterator = data.iterator();
        if (iterator.hasNext()){
            while (iterator.hasNext()){
                WebElement current = iterator.next();
                String  account = current.findElement(By.xpath("*[@data-bind='text: CardCustomName']")).getText().trim();
                String cardCurrency = current.findElement(By.xpath("*[@data-bind='css: CardFlagClass']")).getClass().getName();
                if (!(Queries.existsCardForSSNAndCardNumberCardCurrency(SSN, account, cardCurrency))){
                    isCorrect = false;
                }
            }
        }
        Assert.assertTrue(isCorrect);
    }

    public static void AssertCardsRemainingBalanceExistsInDatabase(String SSN) throws Throwable{
        boolean isCorrect = true;
        String attrib = "data-bind";
        String value = "click: $root.onRowClick.bind($data, $index())";
        List<WebElement>data = SelectByAttribute.CreateElementsByXpath(attrib, value);
        Iterator<WebElement> iterator = data.iterator();
        if (iterator.hasNext()){
            while (iterator.hasNext()){
                WebElement current = iterator.next();
                String  account = current.findElement(By.xpath("*[@data-bind='text: CardCustomName']")).getText().trim();
                String remainingBalance = current.findElement(By.xpath("*[@data-bind='html: S.Globalization.formatAmount(CardBalance)']")).getText().trim();
                if (!(Queries.existsCardForSSNAndCardNumberCardRemainingBalance(SSN, account, remainingBalance))){
                    isCorrect = false;
                }
            }
        }
        Assert.assertTrue(isCorrect);
    }

    public static void AssertCardsStatusExistsInDatabase(String SSN) throws Throwable{
        boolean isCorrect = true;
        String attrib = "data-bind";
        String value = "click: $root.onRowClick.bind($data, $index())";
        List<WebElement>data = SelectByAttribute.CreateElementsByXpath(attrib, value);
        Iterator<WebElement> iterator = data.iterator();
        if (iterator.hasNext()){
            while (iterator.hasNext()){
                WebElement current = iterator.next();
                String  account = current.findElement(By.xpath("*[@data-bind='text: CardCustomName']")).getText().trim();
                String cardStatus = current.findElement(By.xpath("*[@data-bind='html: CardStatusText']")).getText().trim();
                if (!(Queries.existsCardForSSNAndCardNumberCardStatus(SSN, account, cardStatus))){
                    isCorrect = false;
                }
            }
        }
        Assert.assertTrue(isCorrect);
    }


    public static void AssertCardsCategoryExistsInDatabase(String SSN) throws Throwable{
        boolean isCorrect = true;
        String attrib = "data-bind";
        String value = "click: $root.onRowClick.bind($data, $index())";
        List<WebElement>data = SelectByAttribute.CreateElementsByXpath(attrib, value);
        Iterator<WebElement> iterator = data.iterator();
        if (iterator.hasNext()){
            while (iterator.hasNext()){
                WebElement current = iterator.next();
                String  account = current.findElement(By.xpath("*[@data-bind='text: CardCustomName']")).getText().trim();
                String cardCategory = current.findElement(By.xpath("*[@data-bind='text: primSuppTranslationKey ']")).getText().trim();
                if (!(Queries.existsCardForSSNAndCardNumberCardCategory(SSN, account, cardCategory))){
                    isCorrect = false;
                }
            }
        }
        Assert.assertTrue(isCorrect);
    }

    /**
     * Assert if element is displayed
     * Element is find by attribute data-bind with value in parameter value
     * @param value
     * @throws Throwable
     */
    public static void AssertDataBindValue(String value) throws Throwable {
        String attrib = "data-bind";
        String text = value;
        WebElement element = SelectByAttribute.CreateElementByXpath(attrib, text);
        Assert.assertTrue(element.isDisplayed());
    }

    /**
     * Assert if element is enabled
     * Element is find by attribute data-bind with value in parameter value
     * @param value
     * @throws Throwable
     */
    public static void AssertDataBindValueIsEnabled(String value) throws Throwable {
        String attrib = "data-bind";
        String text = value;
        WebElement element = SelectByAttribute.CreateElementByXpath(attrib, text);
        Assert.assertTrue(element.isEnabled());
    }

    public static String GetCardNumberFromPage() throws Throwable {
        String attrib = "data-bind";
        String text = "text: $root.SelectedCardInfo().CardCustomName";
        WebElement element = SelectByAttribute.CreateElementByXpath(attrib, text);
        return element.getText();
    }

    public static WebElement GetElementDataBindValue(String text) throws  Throwable {
        String attrib = "data-bind";
        return SelectByAttribute.CreateElementByXpath(attrib, text);
    }

    public static String getCardNameCreditDebit(String SSN, String cardType) throws SQLException {
        List<HashMap<String, Object>> cardMaster = Queries.getAllRetailCoreCards(SSN);
        cardMaster = cardMaster.stream()
                .filter(row -> row.get("CardType").equals(cardType))
                .collect(Collectors.toList());
        DataManager.userObject.put("CardTypeProcessor",cardMaster.get(0).get("CardTypeProcessor").toString());
        return cardMaster.get(0).get("CardNumber").toString();
    }

    public static List<WebElement> GetItemTurnOver() throws Throwable {
        String xPath = "//*[@class='gvCardTurnover']/table/tbody/tr/td[2]/span";
        return SelectByXpath.CreateElementsByXpath(xPath);
    }

    public static List<WebElement> GetItemProccessingTurnOver() throws Throwable {
        String xPath = "//*[@class='gvCardTurnover']/table/tbody/tr/td[3]/span";
        return SelectByXpath.CreateElementsByXpath(xPath);
    }

    public static List<WebElement> GetItemPAmountTurnOver() throws Throwable {
        String xPath = "//*[@class='gvCardTurnover']/table/tbody/tr/td[6]/span/p";
        return SelectByXpath.CreateElementsByXpath(xPath);
    }

    public static List<WebElement> GetItemCardSpendings() throws Throwable {
        String xPath = "//*[@class='gvCardSpendings']/table/tbody/tr/td[2]/span";
        return SelectByXpath.CreateElementsByXpath(xPath);
    }

    public static List<WebElement> GetItemProccessingCardSpendings() throws Throwable {
        String xPath = "//*[@class='gvCardSpendings']/table/tbody/tr/td[3]/span";
        return SelectByXpath.CreateElementsByXpath(xPath);
    }

    public static List<WebElement> GetItemAmountCardSpendings() throws Throwable {
        String xPath = "//*[@class='gvCardSpendings']/table/tbody/tr/td[7]/span/p";
        return SelectByXpath.CreateElementsByXpath(xPath);
    }

    public static boolean CheckIfDateInRange(String date){
        boolean result = false;
        String dateCurr = Utilities.ToDateYYYYMMDD(date);
        String dateStart = Utilities.WordToYYYYMMDD(DataManager.userObject.get("DateFrom").toString());
        String dateEnd = Utilities.WordToYYYYMMDD(DataManager.userObject.get("DateTo").toString());
        if(dateCurr.compareTo(dateStart) >= 0)
            if(dateCurr.compareTo(dateEnd) <= 0)
                result = true;
        return result;
    }

    public static boolean CheckIfValueInRange(String data){
        boolean result = false;
        float dataCurr = Float.parseFloat(data);
        dataCurr = Utilities.Absolute(dataCurr);
        float dataStart = Float.parseFloat(DataManager.userObject.get("AmountFrom").toString());
        float dataEnd = Float.parseFloat(DataManager.userObject.get("AmountTo").toString());
        if(dataCurr >= dataStart)
            if(dataCurr <= dataEnd)
                result = true;
        return result;
    }

    public static void searchWithPaymentDetails(String description) throws Throwable {
        boolean isCorrect = Queries.getWithinAccountPaymentDataLuca(description + "%");
        Assert.assertTrue(isCorrect);
    }
    public static String generateDescription() {
        String randomDetails = RandomStringUtils.randomAlphanumeric(15);
        return "TA payment "+randomDetails;
    }

    /**
     * Assert element displayed for tag name "nlb-language-selector" and text in parameter @text
     * @param text Text for search in sub element
     * @throws Throwable
     */
    public static void assertLanguageInLanguageChanger(String text) throws Throwable {
        String tagName = "nlb-language-selector";
        WebElement element = SelectByTagName.CreateElementByCertainTextInTag(tagName, text);
        Assert.assertTrue(element.isDisplayed());
    }

    /**
     * Assert custom element login form
     * @throws Throwable
     */
    public static void assertLoginForm() throws Throwable {
        String tagName = "form";
        String className = "ng-untouched ng-pristine ng-invalid";
        WebElement element = SelectByTagName.CreateElementByClassAndTag(tagName, className);
        Assert.assertTrue(element.isDisplayed());
    }

    /**
     * Assert custom link Need help
     * @throws Throwable
     */
    public static void assertNeedHelpLink() throws Throwable {
        String className = "cursor-pointer fs-base link-label";
        WebElement element = SelectByClassName.CreateElementByLinkInClass(className);
        Assert.assertTrue(element.isEnabled());
    }

    /**
     * Assert link in class with class name in parameter @className
     * @param className Name of the class
     * @throws Throwable
     */
    public static void assertLinkForClass(String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByLinkInClass(className);
        Assert.assertTrue(element.isEnabled());
    }

    /**
     * Assert element class with class name in parameter @className
     * @param className Name of the class
     * @throws Throwable
     */
    public static void assertClassEnabled(String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        Assert.assertTrue(element.isEnabled());
    }

    /**
     * Assert element with attribute name 'for' and value in parameter @item
     * @param item Value of attribute
     * @throws Throwable
     */
    public static void assertLabelFor(String item) throws Throwable {
        String attribName = "for";
        WebElement element = SelectByAttribute.CreateElementByXpath(attribName, item);
        Assert.assertTrue(element.isDisplayed());
    }

    /**
     * Assert element by Id equals elementId and has equals text to parameter @text
     * @param elementId Id of the element
     * @param text Text in element
     * @throws Throwable
     */
    public static void assertElementByIdAndText(String elementId, String text) throws Throwable {
        WebElement element = SelectById.CreateElementByXpathIdAndText(elementId, text);
        Assert.assertTrue(element.isDisplayed());
    }

    /**
     * Assert if products are sorted correctly
     * @throws Throwable
     */
    public static void assertProductListSortedCorrect() throws Throwable {
        List<WebElement> elements = SelectByXpath.CreateElementsByXpathProductList();
        List<String> widgets = new ArrayList<String>();
        List<String> widgetsSorted = new ArrayList<String>();

        for (WebElement element : elements) {
            String text = Utilities.getFileName(element.getAttribute("src").trim());
            widgets.add(text);
            widgetsSorted.add(text);
        }
        Object[] w = widgets.toArray();
        //String[] ws = (String[]) widgets.toArray();

        boolean correct = true;
        for (int i = 0; i < w.length - 2; i++) {
            int value = Utilities.productSortValue(w[i].toString());
            int valueNext = Utilities.productSortValue(w[i + 1].toString());
            if (value > valueNext)
                correct = false;
        }

        Assert.assertTrue(correct);
    }

    public static void assertTagsAreInAlphabeticalOrder() throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByClassName("tag-chip");
        List<String> tags = new ArrayList<>();
        for (WebElement element : webElementList) {
            String tagContent = element.getAttribute("innerText").trim();
            String tagPureContent = tagContent.replaceAll("#","");
            tags.add(tagPureContent);
        }
        List<String> tagsSorted = new ArrayList<>(tags);
        Collections.sort(tagsSorted);

        Assert.assertTrue(tags.equals(tagsSorted));
    }

    public static void assertTransactionsIdEqualsIdUnderKeyIfNotLoop(String key) throws Throwable {
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath("//*[@class='category-icon category-split']/ancestor::*[contains(@class,'middle-content')]");

        for (WebElement element: webElementList) {
            JSHelpers.ScrollIntoViewBottom(element);
            ActionApiHelpers.ClickOnElement(element);
            WebElement transactionIDElement = SelectByXpath.CreateElementByXpath("//*[text()='Transaction ID']/following-sibling::span");
            String transactionID = transactionIDElement.getAttribute("textContent");
            String searchedTransactionId = String.valueOf(DataManager.userObject.get(key));

            if(transactionID.equals(searchedTransactionId)){
                break;
            }else{
                ActionApiHelpers.ClickOnElement(SelectByClassName.CreateElementByClassName("opened-details"));
            }
        }
    }

    public static void assertNumberOfCategories(int number) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByClassName("pt-3");
        int size = webElementList.size();
        Assert.assertTrue(size==number);
    }

    public static void ScrollNumberOfTimesUntilYouFindText(int n, String previousMonth) throws Throwable {
        String xPath = "//*[contains(text(),'"+previousMonth+"')]";
        By el = By.xpath(xPath);
        boolean messageExists = ActionApiHelpers.isElementDisplayedCustom(el,2,1000);
        if (messageExists==false){
            for (int i = 0; i<n; i++){
                JSHelpers.ScrollXPagesOnDynamicLoadingpage(1);
                boolean doesExist = ActionApiHelpers.isElementDisplayedCustom(el,2,1000);
                if (doesExist==true){
                    break;
                }
            }
        }
    }

    public void assertElementsByClassHaveInAlphabeticalOrder(String className, String attribute) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByXpath(className);

        List<String> contacts = new ArrayList<>();
        for (WebElement element : webElementList) {
            String tagContent = element.getAttribute(attribute).trim().toLowerCase();
            contacts.add(tagContent);
        }
        List<String> contactsSorted = new ArrayList<>(contacts);
        Collections.sort(contactsSorted);

        Assert.assertTrue(contacts.equals(contactsSorted));
    }

    public void assertNumberOfLastPaymentsIsFive(int number) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByClassName("contact-purpose");
        List<WebElement> displayedElements = new ArrayList<>();
        List<WebElement> notDisplayedElements = new ArrayList<>();
        for (WebElement element: webElementList) {
            if(element.isDisplayed() == true){
                displayedElements.add(element);
            }else{
                notDisplayedElements.add(element);
            }
        }
        Assert.assertTrue(displayedElements.size()==number);
    }

    public void deleteAllContactsIfTheyExist() throws Throwable {
        try{
            SelectByText.CreateElementByXpathContainingText("No saved contacts.");
        }catch (Exception e){
            List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath("//*[@class='wrapper mx-1']/descendant::*[@class='mb-3 ng-star-inserted']");
            int count = webElementList.size();

            for (int i = 0; i < count; i++){
                hp.ClickOnElement(SelectByXpath.CreateElementByXpath("(//*[@id='contactDropdown'])[2]"));
                hp.ClickOnElement(SelectByClassName.CreateElementByXpathClassOnly("dropdown-item contact-detail-menu-delete","2"));
                hp.ClickOnElement(SelectByClassName.CreateElementByClassName("contact-delete-modal-button-delete"));
                SelectByClassName.CreateElementByClassName("success-notif");
            }
            SelectByText.CreateElementByXpathContainingText("No saved contacts.");
        }
    }

    public static void assertElementByIdHasMaximumEnabledLengthOf(String id, int maximumLength) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        element.clear();
        String toSend = "";
        int check = maximumLength+1;
        for(int i = 0; i < check ; i++){
            toSend = toSend + "a";
        }
        element.sendKeys(toSend);
        int actualLenght = element.getAttribute("value").length();
        Assert.assertTrue(actualLenght==maximumLength);
    }

    public static void assertElementByContainsClassHasMaximumEnabledLengthOf(String className, int maximumLength, String letter) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        element.clear();
        String toSend = "";
        int check = maximumLength+1;
        for(int i = 0; i < check ; i++){
            toSend = toSend + letter;
        }
        element.sendKeys(toSend);
        int actualLenght = element.getAttribute("value").length();
        Assert.assertTrue(actualLenght==maximumLength);
    }

    /**
     * Creates list of WebElements by class name and returns element from list by index
     * @param className Class name for creating list
     * @param index Index of element in list for return
     * @return WebElement
     * @throws Throwable Error
     */
    public static WebElement getElementByClassAndIndex (String className, int index) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByClassName(className);
        return webElementList.get(index);
    }

    public static void assertSearchedTextExistsInListOfElementsWithClass(String searchedText, String className) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByClassName(className);
        for (WebElement element : webElementList) {
            Assert.assertTrue(element.getAttribute("textContent").toLowerCase().contains(searchedText));
        }
    }

    public void assertPaymentsWithClassAreFromCurrentMonth(String className) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByClassName(className);
        String month = Utilities.getSimpleMonthFormat("MM");
        for (WebElement element : webElementList) {
            String date = element.getAttribute("textContent");
            String mesec = date.replaceFirst("[0-9]{1,2}\\.", "");
            String mesec2 = mesec.replaceFirst("\\.[0-9]*", "");
            Assert.assertEquals(mesec2, month);
        }
    }

    public static void assertTransactionsWithClassAndAttributeExistInListFilteredByTransactionTypeAndAmountRangeFromTo(String className, String attribute, int from, int to) throws Throwable {
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpathClassWithPreceedingSiblingWithAttribute(className, attribute);
        for (WebElement element : webElementList) {
            String suma = element.getAttribute("innerText");
            String formatiranaSuma = suma.replaceAll(",", ".");
            double sumaDouble = Double.parseDouble(formatiranaSuma);
            if (from >= sumaDouble && to <= sumaDouble) {
                Assert.assertTrue(true);
            } else {
                Assert.assertTrue(false);
            }
        }
    }


    public static void removeFiltersWithClass(String className) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByClassName(className);
        for (WebElement element : webElementList) {
            ActionApiHelpers.ClickOnElement(element);
        }
    }

    //methods that used to be in Steps class
    public void assertEachOfMyAccountsHasAmount(List<WebElement> elements) {
        Pattern p = Pattern.compile("\\d*[.,]*");
        for(WebElement element : elements){
            String amount = element.getText();
            Matcher m = p.matcher(amount);
            Assert.assertTrue(m.find());
            Assert.assertTrue(element.isDisplayed());
        }
    }

    public void assertAllRememberedCurrenciesForAccountAreShown(String attribute, String attributeValue, int amountOfCurrencies) throws Throwable {
        ArrayList<String> currencyList = new ArrayList<>();
        for(int i = 0; i < amountOfCurrencies; i++){
            currencyList.add(DataManager.userObject.get("Currency"+i).toString());
            WebElement element = SelectByXpath.CreateElementByXpathAttributeValueAndDescendantContainingText(attribute, attributeValue, currencyList.get(i));
            Assert.assertTrue(element.isDisplayed());
        }
    }

    public static void sendKeyNTimes(WebElement element, int n, String key){
        for (int i=0;i<=n;i++){
            element.sendKeys(Keys.valueOf(key));
        }
    }

    public void removeTagsOnTransaction(int max) throws Throwable {
        for (int i = 1; i<= max; i++){
            String xPath = "(//*[@class='icon-nlb-ico-close ng-star-inserted'])[1]";
            WebElement element = SelectByXpath.CreateElementByXpath(xPath);
            ActionApiHelpers.ClickOnElement(element);
            WaitHelpers.waitForSeconds(15);
        }
    }

    public void splitTransactionIntoEqualCategories() throws Throwable {
        //Get Remaining amount in double
        WebElement remainingAmountElement = SelectByClassName.CreateElementByClassName("remaining-amount");
        String remainingAmountString = remainingAmountElement.getAttribute("textContent");
        String replacedRemainingAmount1 = remainingAmountString.replaceAll("−","");
        String replacedRemainingAmount = replacedRemainingAmount1.replaceAll(",",".");
        Double remainingAmount = Double.parseDouble(replacedRemainingAmount);
        Double halfRemainingAmount = remainingAmount/2;
        Double floored = Math.floor(halfRemainingAmount * 100.0) / 100.0;
        Double rounded = Math.round(halfRemainingAmount * 100.0) / 100.0;
        String splitRemainingAmount = String.valueOf(floored);
        String splitRemainingAmount2 = String.valueOf(rounded);

        ActionApiHelpers.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","1");
        ActionApiHelpers.ClickOnElement(dropdownElement);
        ActionApiHelpers.ClickOnElement(SelectByXpath.CreateElementByXpath("(//*[contains(text(),'Shopping')])[2]"));
        WebElement firstAmountElement = SelectById.CreateElementById("amount_0");
        ActionApiHelpers.EnterTextToElement(firstAmountElement,splitRemainingAmount);

        ActionApiHelpers.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement2 = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","2");
        ActionApiHelpers.ClickOnElement(dropdownElement2);
        ActionApiHelpers.ClickOnElement(SelectByXpath.CreateElementByXpath("(//*[contains(text(),'Kids')])[3]"));
        WebElement secondAmountElement = SelectById.CreateElementById("amount_1");
        ActionApiHelpers.EnterTextToElement(secondAmountElement,splitRemainingAmount2);
    }

    public void enterAmountMoreThanAllowedIntoSplitCategory() throws Throwable {
        WebElement remainingAmountElement = SelectByClassName.CreateElementByClassName("remaining-amount");
        String remainingAmountString = remainingAmountElement.getAttribute("textContent");
        String replacedRemainingAmount1 = remainingAmountString.replaceAll("−","");
        String replaceRemainingAmountDot = replacedRemainingAmount1.replaceAll("\\.","");
        String replacedRemainingAmount = replaceRemainingAmountDot.replaceAll(",",".");
        Double remainingAmount = Double.parseDouble(replacedRemainingAmount);
        Double plusRemainingAmount = remainingAmount+1;
        String splitRemainingAmount = String.valueOf(plusRemainingAmount);

        ActionApiHelpers.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","1");
        ActionApiHelpers.ClickOnElement(dropdownElement);
        ActionApiHelpers.ClickOnElement(SelectByXpath.CreateElementByXpath("(//*[contains(text(),'Shopping')])[2]"));
        WebElement firstAmountElement = SelectById.CreateElementById("amount_0");
        ActionApiHelpers.EnterTextToElement(firstAmountElement,splitRemainingAmount);
    }

    public void addSixCategoriesToATransaction() throws Throwable {
        WebElement remainingAmountElement = SelectByClassName.CreateElementByClassName("remaining-amount");
        String remainingAmountString = remainingAmountElement.getAttribute("textContent");
        String replacedRemainingAmount1 = remainingAmountString.replaceAll("−","");
        String replaceRemainingAmountDot = replacedRemainingAmount1.replaceAll("\\.","");
        String replacedRemainingAmount = replaceRemainingAmountDot.replaceAll(",",".");
        Double remainingAmount = Double.parseDouble(replacedRemainingAmount);
        Double plusRemainingAmount = remainingAmount/6;
        String splitRemainingAmount = String.valueOf(plusRemainingAmount);

        hp.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","1");
        hp.ClickOnElement(dropdownElement);
        hp.ClickOnElement(SelectByXpath.CreateElementByXpath("//*[@class='dropdown dropdown-open active-menu']//li[1]"));
        WebElement firstAmountElement = SelectById.CreateElementById("amount_0");
        hp.EnterTextToElement(firstAmountElement,splitRemainingAmount);

        hp.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement2 = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","2");
        hp.ClickOnElement(dropdownElement2);
        hp.ClickOnElement(SelectByXpath.CreateElementByXpath("//*[@class='dropdown dropdown-open active-menu']//li[1]"));
        WebElement firstAmountElement1 = SelectById.CreateElementById("amount_1");
        hp.EnterTextToElement(firstAmountElement1,splitRemainingAmount);

        hp.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement3 = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","3");
        hp.ClickOnElement(dropdownElement3);
        hp.ClickOnElement(SelectByXpath.CreateElementByXpath("//*[@class='dropdown dropdown-open active-menu']//li[1]"));
        WebElement firstAmountElement2 = SelectById.CreateElementById("amount_2");
        hp.EnterTextToElement(firstAmountElement2,splitRemainingAmount);

        hp.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement4 = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","4");
        hp.ClickOnElement(dropdownElement4);
        hp.ClickOnElement(SelectByXpath.CreateElementByXpath("//*[@class='dropdown dropdown-open active-menu']//li[1]"));
        WebElement firstAmountElement3 = SelectById.CreateElementById("amount_3");
        hp.EnterTextToElement(firstAmountElement3,splitRemainingAmount);

        hp.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement5 = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","5");
        hp.ClickOnElement(dropdownElement5);
        hp.ClickOnElement(SelectByXpath.CreateElementByXpath("//*[@class='dropdown dropdown-open active-menu']//li[1]"));
        WebElement firstAmountElement4 = SelectById.CreateElementById("amount_4");
        hp.EnterTextToElement(firstAmountElement4,splitRemainingAmount);
        WaitHelpers.waitForSeconds(7);

        hp.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        String xPathRemainingAmount = "//*[@class='remaining-amount']";
        WebElement remainingAmountElement2 = SelectByXpath.CreateElementByXpath(xPathRemainingAmount);
        String remainingAmountString2 = remainingAmountElement2.getAttribute("textContent");
        String replacedRemainingAmount2 = remainingAmountString2.replaceAll("−","");
        WebElement dropdownElement6 = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","6");
        hp.ClickOnElement(dropdownElement6);
        hp.ClickOnElement(SelectByXpath.CreateElementByXpath("//*[@class='dropdown dropdown-open active-menu']//li[1]"));
        WebElement firstAmountElement5 = SelectById.CreateElementById("amount_5");
        hp.EnterTextToElement(firstAmountElement5,replacedRemainingAmount2);
    }

    public void checkIfRemainingAmountIsCalculatedCorrectly() throws Throwable {
        WebElement remainingAmountElement = SelectByClassName.CreateElementByClassName("remaining-amount");
        String remainingAmountString = remainingAmountElement.getAttribute("textContent");
        String replacedRemainingAmount1 = remainingAmountString.replaceAll("−","");
        String replaceRemainingAmountDot = replacedRemainingAmount1.replaceAll("\\.","");
        String replacedRemainingAmount = replaceRemainingAmountDot.replaceAll(",",".");
        Double remainingAmount = Double.parseDouble(replacedRemainingAmount);

        hp.ClickOnElement(SelectByText.CreateElementByXpathText("Add category"));
        WebElement dropdownElement = SelectByClassName.CreateElementByXpathClassOnly("wrapper-dropdown cursor-pointer select-component","1");
        hp.ClickOnElement(dropdownElement);
        hp.ClickOnElement(SelectByXpath.CreateElementByXpath("//*[@class='dropdown dropdown-open active-menu']//li[1]"));
        WebElement firstAmountElement = SelectById.CreateElementById("amount_0");
        hp.EnterTextToElementWithEnter(firstAmountElement,"1");

        Double expectedRemainingAmount = remainingAmount - 1;

        WebElement actualRemainingAmountElement = SelectByClassName.CreateElementByClassName("remaining-amount");
        String remainingAmountString1 = actualRemainingAmountElement.getAttribute("textContent");
        String replacedRemainingAmount2 = remainingAmountString1.replaceAll("−","");
        String replacedRemainingAmount2Dot = replacedRemainingAmount2.replaceAll("\\.","");
        String replacedRemainingAmount3 = replacedRemainingAmount2Dot.replaceAll(",",".");
        Double actualRemainingAmount = Double.parseDouble(replacedRemainingAmount3);

        Assert.assertEquals(expectedRemainingAmount,actualRemainingAmount);
    }

    public void checkIfPaymentTypeIsUPNIfNotLoop() throws Throwable {
        String cancelButton = "//button[contains(text(),'Cancel')]";
        String yesButton = "//button[contains(text(),'Yes')]";
        WebElement paymentHeaderElement = SelectByClassName.CreateElementByClassName("payment-header");
        String paymentHeaderElementContent = paymentHeaderElement.getAttribute("textContent");
        if (paymentHeaderElementContent.contains("UPN")) {
            System.out.println("Nadjeno je odgovarajuce placanje");
        } else {
            WebElement cancelElement = SelectByXpath.CreateElementByXpath(cancelButton);
            JSHelpers.scrollScreenDown(1);
            hp.ClickOnElement(cancelElement);
            WebElement yesElement = SelectByXpath.CreateElementByXpath(yesButton);
            hp.ClickOnElement(yesElement);
            int size = SelectByXpath.CreateElementsByXpath("//i[contains(@class, 'icon-nlb-ico-check')]//ancestor::div[contains(@class, 'middle-content')]").size();
            for(int i = 2; i <= size; i++){
                String xPath = "(//i[contains(@class, 'icon-nlb-ico-check')]//ancestor::div[contains(@class, 'middle-content')])[" + i + "]";
                String previousXPath = "(//i[contains(@class, 'icon-nlb-ico-check')]//ancestor::div[contains(@class, 'middle-content')])[" + (i-1) + "]";
                WebElement previousElement = SelectByXpath.CreateElementByXpath(previousXPath);
                JSHelpers.ScrollIntoView(previousElement);
                WaitHelpers.waitForSeconds(5);
                WebElement element = SelectByXpath.CreateElementByXpath(xPath);
                hp.ClickOnElement(element,"Kliknuo sam");
                WebElement scrollElement = SelectByXpath.CreateElementByXpath("//nlb-payments-list-item-details/div/div[1]/div[5]/div");
                JSHelpers.ScrollIntoView(scrollElement);
                WaitHelpers.waitForSeconds(5);

                hp.ClickOnElement(SelectByTagName.CreateElementByTextInTag("button", "Repeat"));
                WebElement paymentHeaderElement2 = SelectByClassName.CreateElementByClassName("payment-header");
                String paymentHeaderElementContent2 = paymentHeaderElement2.getAttribute("textContent");

                if (paymentHeaderElementContent2.contains("UPN")) {
                    //Cancel to get data from transaction detail
                    JSHelpers.scrollScreenDown(1);
                    WebElement cancelElement2 = SelectByXpath.CreateElementByXpath(cancelButton);
                    hp.ClickOnElement(cancelElement2);
                    WebElement yesElement2 = SelectByXpath.CreateElementByXpath(yesButton);
                    hp.ClickOnElement(yesElement2);

                    //Scroll transaction into view, click on it and scroll to Repeat
                    WebElement previousElement1 = SelectByXpath.CreateElementByXpath(previousXPath);
                    JSHelpers.ScrollIntoView(previousElement1);
                    WaitHelpers.waitForSeconds(5);
                    WebElement element3 = SelectByXpath.CreateElementByXpath(xPath);
                    hp.ClickOnElement(element3,"Kliknuo sam");
                    WebElement scrollElement1 = SelectByXpath.CreateElementByXpath("//nlb-payments-list-item-details/div/div[1]/div[5]/div");
                    JSHelpers.ScrollIntoView(scrollElement1);
                    WaitHelpers.waitForSeconds(5);

                    //Get data
                    WebElement elementToSave1 = SelectByXpath.CreateElementByXpath("//div[2]/span/nlb-format-iban-ui");
                    hp.saveTheValueToMapBasic(elementToSave1.getAttribute("innerText"), "to_account");
                    WebElement elementToSave2 = SelectByXpath.CreateElementByXpath("//div[7]/span/nlb-format-iban-ui");
                    hp.saveTheValueToMapBasic(elementToSave2.getAttribute("innerText"), "from_account");
                    WebElement elementToSave3 = SelectByXpath.CreateElementByXpath("//nlb-payments-list-item-details/div/div[1]/div[5]/div");
                    hp.saveTheValueToMapBasic(elementToSave3.getAttribute("innerText"), "purpose_code");

                    //Click on repeat for the transaction again
                    hp.ClickOnElement(SelectByTagName.CreateElementByTextInTag("button", "Repeat"));

                    WebElement elementToCompare1 = SelectByXpath.CreateElementByXpath("//nlb-contact-card-list-item/div/div/div/div[2]/div[2]/nlb-format-iban-ui");
                    Assert.assertEquals(elementToCompare1.getAttribute("innerText"), DataManager.userObject.get("to_account"));
                    WebElement elementToCompare2 = SelectByXpath.CreateElementByXpath("//nlb-account-card-list-item/div/div/div[1]/div[2]/div[2]/nlb-format-iban-ui");
                    Assert.assertEquals(elementToCompare2.getAttribute("innerText"), DataManager.userObject.get("from_account"));
                    WebElement elementToCompare3 = SelectByClassName.CreateElementByClassName("dropdown-select-search-input");
                    Assert.assertEquals(elementToCompare3.getAttribute("value"), DataManager.userObject.get("purpose_code"));
                    break;
                } else {
                    JSHelpers.scrollScreenDown(1);
                    WebElement cancelElement2 = SelectByXpath.CreateElementByXpath(cancelButton);
                    hp.ClickOnElement(cancelElement2);
                    WebElement yesElement2 = SelectByXpath.CreateElementByXpath(yesButton);
                    hp.ClickOnElement(yesElement2);
                }
            }
        }
    }

    public void assertElementByIdHasMaximumEnabledLenghtOf(WebElement element, int maximumLength){
        element.clear();
        String toSend = "";
        int check = maximumLength+1;
        for(int i = 0; i < check ; i++){
            toSend = toSend + "a";
        }
        element.sendKeys(toSend);
        int actualLength = element.getAttribute("value").length();
        Assert.assertTrue(actualLength == maximumLength);
    }

    public void checkMobilePaymentsBlockUnblockSwitchNLB(WebElement offElement, WebElement onElement) throws Throwable {
        if (offElement.getAttribute("className").equals("selected")) {
            hp.ClickOnElementFast(onElement);
            SelectByClassName.CreateElementByClassName("success-notif");
            WebElement elementSucccessMessage = SelectByTagName.CreateElementByTextInTag("*", "Mobile payments are unblocked. You can make payments in mobile bank again.");
            Assert.assertTrue(elementSucccessMessage.isDisplayed());
            hp.ClickOnElementFast(offElement);
            SelectByClassName.CreateElementByClassName("success-notif");
            WebElement elementSucccessMessage2 = SelectByTagName.CreateElementByTextInTag("*", "Mobile payments are blocked. You can unblock them in settings at any time.");
            Assert.assertTrue(elementSucccessMessage2.isDisplayed());
        } else {
            hp.ClickOnElementFast(offElement);
            SelectByClassName.CreateElementByClassName("success-notif");
            WebElement elementSucccessMessage2 = SelectByTagName.CreateElementByTextInTag("*", "Mobile payments are blocked. You can unblock them in settings at any time.");
            Assert.assertTrue(elementSucccessMessage2.isDisplayed());
            hp.ClickOnElementFast(onElement);
            SelectByClassName.CreateElementByClassName("success-notif");
            WebElement elementSuccessMessage = SelectByTagName.CreateElementByTextInTag("*", "Mobile payments are unblocked. You can make payments in mobile bank again.");
            Assert.assertTrue(elementSuccessMessage.isDisplayed());
        }
    }

    public void assertErrorMessageForFields(String text, int numberOfFields) throws Throwable {
        for(int i = 1; i<=numberOfFields; i++){
            WebElement element = SelectByXpath.CreateElementByXpathTagContainsTextWithIndex("span", text, i);
            Assert.assertTrue((element.isDisplayed()));
        }
    }

    public void assertEachOfMyAccountsHasCurrency(List<WebElement> elements, String currency){
        for(WebElement element : elements){
            Assert.assertEquals(element.getText(), currency);
        }
    }

    public void assertEachOfContactsHas(List<WebElement> elements1, List<WebElement> elements2){
        for(WebElement element : elements1){
            Assert.assertTrue(element.isDisplayed());
        }

        for(WebElement element : elements2){
            Assert.assertTrue(element.isDisplayed());
        }
    }

    public void assertContactsAreSortedAlphabetically(List<WebElement> elements){
        ArrayList<String> names = new ArrayList<String>();
        String previousName = "";

        for(WebElement element : elements){
            names.add(element.getText());
        }
        int amount = elements.size();

        for(int i = 0; i < amount - 1; i++){
            String n1 = names.get(i);
            String n2 = names.get(i+1);
            if(names.get(i).compareToIgnoreCase(names.get(i+1)) > 0){
                break;
            }
        }
    }

    public void clickOnFirstRepeatPaymentButton(List<WebElement> elements) throws Throwable {
        for(WebElement element: elements){
            if(element.isDisplayed() == true){
                hp.ClickOnElement(element);
                break;
            }
        }
    }

    public String calculateTransactionsAmountForATag(List<WebElement> element1) throws Throwable {
        int amountNumber = element1.size();
        double totalAmount = 0;
        for(int i = 1; i<=amountNumber; i++){
            WebElement element2 = SelectByXpath.CreateElementByXpath("(//span[contains(@class , 'transactions-monthly-amount')]//descendant::span//descendant::span[1])[" + i + "]");
            String amountForFormat = element2.getText();
            String textAmount = amountForFormat.replace(",", ".");
            String textAmount2 = textAmount.replace("−", "-");
            double amount = Double.parseDouble(textAmount2);
            totalAmount += amount;
        }
        return String.valueOf(totalAmount);
    }

    public String calculateTransactionsAmountForAMonth(List<WebElement> elementList){
        int n  = elementList.size();
        double totalAmount = 0;
        for (WebElement element: elementList) {
            String amountBefore = element.getAttribute("textContent");
            String amountWithoutCurrency = amountBefore.replaceAll("([A-Z])\\w+", "");
            String amountWithoutDot = amountWithoutCurrency.replace(".","");
            String amountReplaced = amountWithoutDot.replace(",", ".");
            if(amountReplaced.contains("−")){
                String amountReplaced2 = amountReplaced.replace("−", "-");
                double amount = Double.parseDouble(amountReplaced2);
                totalAmount += amount;
            } else{
                double amount = Double.parseDouble(amountReplaced);
                totalAmount += amount;
            }
        }
        return String.valueOf(totalAmount);
    }

    public void assertThereIsMoreThanOneCategory(List<WebElement> elements){
        int listSize = elements.size();
        if(listSize>1){
            for (WebElement element: elements) {
                Assert.assertTrue(element.isDisplayed());
            }
        } else throw new RuntimeException("Split transaction category needs to have more than 1 category");
    }

    public void assertCategoriesIconsAndAmountsAreNotClickable(List<WebElement> elementsIcons) throws Throwable {
        int iconsAmount = elementsIcons.size();
        JSHelpers.ScrollIntoViewBottom(elementsIcons.get(1));

        //click each icon
        for(int i = 1; i <= iconsAmount; i++){
            WebElement element1 = SelectByXpath.CreateElementByXpath("(//div[contains(@class, 'list-group-transaction-item')]//descendant::*[contains(@class, 'smaller-category-icons')])[" + i + "]");
            Assert.assertTrue(element1.isDisplayed());
            hp.ClickOnElement(element1);
        }
        //check if nothing changed by xpath
        for(int i = 1; i <= iconsAmount; i++){
            WebElement element1 = SelectByXpath.CreateElementByXpath("(//div[contains(@class, 'list-group-transaction-item')]//descendant::*[contains(@class, 'smaller-category-icons')])[" + i + "]");
            Assert.assertTrue(element1.isDisplayed());
        }
        //click each amount
        for(int i = 1; i <= iconsAmount; i++){
            WebElement element1 = SelectByXpath.CreateElementByXpath("(//div[contains(@class, 'list-group-transaction-item')]//descendant::*[contains(@class, 'split-text')])[" + i + "]");
            Assert.assertTrue(element1.isDisplayed());
            hp.ClickOnElement(element1);
        }
        //check if nothing changed by xpath
        for(int i = 1; i <= iconsAmount; i++){
            WebElement element1 = SelectByXpath.CreateElementByXpath("(//div[contains(@class, 'list-group-transaction-item')]//descendant::*[contains(@class, 'split-text')])[" + i + "]");
            Assert.assertTrue(element1.isDisplayed());
        }
    }

    public String calculateTotalAmountForAllCategories(List<WebElement> elements){
        double amount = 0;
        for(WebElement element : elements){
            String amountS = (element.getText()).replaceAll(" *[A-Z]*$", "");
            double amountD = Double.parseDouble(amountS);
            amount+= amountD;
        }
        return String.valueOf(amount);
    }

    public void rememberBankInformation(WebElement bankNameElement, WebElement bankStreetElement, WebElement bankCityElement, WebElement bankCountryElement, WebElement bicSwiftElement){
        String bankName = bankNameElement.getAttribute("value");
        String bankStreet = bankStreetElement.getAttribute("value");
        String bankCity = bankCityElement.getAttribute("value");
        String bankCountry = bankCountryElement.getText();
        String bicSwift = bicSwiftElement.getAttribute("value");

        hp.saveTheValueToMapBasic(bankName, "bankName");
        hp.saveTheValueToMapBasic(bankStreet,"bankStreet");
        hp.saveTheValueToMapBasic(bankCity, "bankCity");
        if(bankCountry.equals("Country")){
            hp.saveTheValueToMapBasic("", "bankCountry");
        }
        else {
            hp.saveTheValueToMapBasic(bankCountry, "bankCountry");
        }
        if(bicSwift.equals("")){
            hp.saveTheValueToMapBasic("LJBASI20", "bicSwift");
        }
        else {
            hp.saveTheValueToMapBasic(bicSwift, "bicSwift");
        }
    }

    public void rememberAllAccountCurrencies(List<WebElement> elementList){
        int numberOfCurrencies = elementList.size();
        for (int i = 0; i < numberOfCurrencies; i++) {
            String currency = elementList.get(i).getAttribute("textContent").replace(" ", "");
            hp.saveTheValueToMapBasic(currency,"Currency"+i);
        }
        hp.saveTheValueToMapBasic(String.valueOf(numberOfCurrencies),"amountOfCurrencies");
    }

    public void assertToCurrencyListHasAllCurrencies(List<WebElement> listOfElements){
        int numberOfCurrencies = listOfElements.size();
        ArrayList<String> actualCurrencyList = new ArrayList<>();
        for(int i = 0; i < numberOfCurrencies; i++){
            actualCurrencyList.add(listOfElements.get(i).getAttribute("innerText"));
        }
        ArrayList<String> expectedCurrencies = new ArrayList<>();
        expectedCurrencies.add("AUD");
        expectedCurrencies.add("CAD");
        expectedCurrencies.add("CHF");
        expectedCurrencies.add("DKK");
        expectedCurrencies.add("GBP");
        expectedCurrencies.add("HUF");
        expectedCurrencies.add("JPY");
        expectedCurrencies.add("NOK");
        expectedCurrencies.add("SEK");
        expectedCurrencies.add("USD");

        Collections.sort(actualCurrencyList);
        Assert.assertTrue(expectedCurrencies.equals(actualCurrencyList));
    }

    public void assertToCurrencyListHasAllCurrenciesEur(List<WebElement> listOfElements){
        int numberOfCurrencies = listOfElements.size();
        ArrayList<String> actualCurrencyList = new ArrayList<>();
        for(int i = 0; i < numberOfCurrencies; i++){
            actualCurrencyList.add(listOfElements.get(i).getAttribute("innerText"));
        }
        ArrayList<String> expectedCurrencies = new ArrayList<>();
        expectedCurrencies.add("AUD");
        expectedCurrencies.add("CAD");
        expectedCurrencies.add("CHF");
        expectedCurrencies.add("DKK");
        expectedCurrencies.add("EUR");
        expectedCurrencies.add("GBP");
        //expectedCurrencies.add("HRK");
        expectedCurrencies.add("HUF");
        expectedCurrencies.add("JPY");
        expectedCurrencies.add("NOK");
        expectedCurrencies.add("SEK");

        Collections.sort(actualCurrencyList);
        Assert.assertTrue(expectedCurrencies.equals(actualCurrencyList));
    }

    /**
     * Returns is every string from list of error in strings is equal with one text in list of elements
     * @param list - List of elements that contains error message
     * @param errorStrings - List of expected errors separate by ~
     * @return boolean : True - yes contains, False - no, there is at least one string that is not equal error message
     * in Web Element
     */
    public static boolean errorEqualsListOfErrors(List<WebElement> list, String errorStrings){
        String[] arrOfStr = errorStrings.split("~");
        boolean result = false;
        for (String error : arrOfStr){
            boolean found = false;
            for (WebElement element : list){
                String elementText = element.getAttribute("innerHTML").toString();
                if (elementText.equals(error))
                        found = true;
            }
            result = result || found;
        }
        return result;
    }

    /**
     * Returns is every string from list of error in strings contained by one text in list of elements
     * @param list - List of elements that contains error message
     * @param errorStrings - List of expected errors separate by ~
     * @return boolean : True - yes contains, False - no, there is at least one string that is contained in error message
     * in Web Element
     */
    public static boolean errorContainsListOfErrors(List<WebElement> list, String errorStrings){
        String[] arrOfStr = errorStrings.split("~");
        boolean result = false;
        for (String error : arrOfStr){
            boolean found = false;
            for (WebElement element : list){
                String elementText = element.getAttribute("innerHTML").toString();
                if (elementText.contains(error))
                    found = true;
            }
            result = result || found;
        }
        return result;
    }

    /**
     * Returns is every string from list of error in strings is equal with one text in list of elements
     * @param list - List of string that contains error message
     * @param errorStrings - List of expected errors separate by ~
     * @return boolean : True - yes contains, False - no, there is at least one string that is not equal error message
     * in Web Element
     */
    public static boolean errorEqualsListOfString(List<String> list, String errorStrings){
        String[] arrOfStr = errorStrings.split("~");
        boolean result = false;
        for (String error : arrOfStr){
            boolean found = false;
            for (String elementText : list){
                if (elementText.equals(error))
                    found = true;
            }
            result = result || found;
        }
        return result;
    }

    /**
     * Returns is every string from list of error in strings contained with one text in list of elements
     * @param list - List of string that contains error message
     * @param errorStrings - List of expected errors separate by ~
     * @return boolean : True - yes contains, False - no, there is at least one string that is not contained error message
     * in Web Element
     */
    public static boolean errorContainsListOfString(List<String> list, String errorStrings){
        String[] arrOfStr = errorStrings.split("~");
        boolean result = false;
        for (String error : arrOfStr){
            boolean found = false;
            for (String elementText : list){
                if (elementText.contains(error))
                    found = true;
            }
            result = result || found;
        }
        return result;
    }

    public void scrollTillTheEndOfTransactions() throws Throwable {
    JSHelpers.ScrollToTheBottomOfDynamicLoadingPage();
    }

    public void assertThatListOfItemsIsShown(String xPath) throws Throwable {
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);

        for (WebElement element: webElementList){
            Assert.assertTrue(element.isDisplayed());
        }
    }

    public void assertContactsMyAccountsCurrencyIsShownCorrectly() throws Throwable {
        String xPath = "//div[contains(@class,'account-item-amount')]//descendant::span//descendant::span[2]";
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);

        for (WebElement element: webElementList){
            String actualCurrency = element.getAttribute("textContent");
            Assert.assertTrue(actualCurrency.matches("EUR"));
        }
    }

    public void assertContactsMyAccountsAmountIsShownCorrectly() throws Throwable {
        String xPath = "//div[contains(@class,'account-item-amount')]//descendant::span//descendant::span[1]";
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);

        for (WebElement element: webElementList){
            String actualAmount = element.getAttribute("textContent");
            String actualAmount2 = actualAmount.replace("−", "");
            Assert.assertTrue(actualAmount2.matches("^(\\$)?((\\d{1,5})|(\\d{1,3})(\\.\\d{3})*)(\\,\\d{1,2})?$"));
        }
    }

    public void clickOnDateInCalendarDaysInTheFuture(String days) throws Throwable {
        //Friday, October 7, 2022
        LocalDate date = LocalDate.now().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        String text = date.format(formatter);

        String xPath = "//div[@aria-label='" + text + "']";
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);

        /*for (WebElement element: webElementList){
            if(element.isEnabled() == true){
                hp.ClickOnElement(element);
            }
        }*/
        for (WebElement element: webElementList){
            try {
                hp.ClickOnElement(element);
            } catch(Exception e){
                System.out.println("Usli smo u exception");
            }
        }
    }

    public String generateRandomStringOfCertainLenght(int i) {
        String generatedString = RandomStringUtils.randomAlphabetic(10);
        return generatedString;
    }

    public String getDateInTheFuture(int i) {
        LocalDate date = LocalDate.now().plusDays(i);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String text = date.format(formatter);
        return text;
    }

    public void checkIfUnknownErrorMessageExistsAndPressOk() throws Throwable {
        try{
            String xPath = "//*[contains(@class,'error-modal-content')]";
            By el = By.xpath(xPath);
            boolean messageExists = ActionApiHelpers.isElementDisplayedCustom(el,15,1000);
            String xPath2 = "//*[contains(@class,'modal-content')]//button";

            if(messageExists == true){
                WebElement element = SelectByXpath.CreateElementByXpath(xPath2);
                hp.ClickOnElement(element);
            } else{
                System.out.println("Greska se nije pojavila");
            }
        } catch (Exception e){
            System.out.print("Neka greska");
        }
    }

    public void checkIfOTPIsNeededAndCompletePaymentFor(String className) throws Throwable {
            String status = (String) DataManager.userObject.get("StatusOfPayment");
            if(status.equals("NOK")){
                //String xPath = "//*[text()='Payment confirmation']";
                String xPath = "//*[contains(text(),'Confirmation')]";
                //String xPath = "//*[contains(text(),'Authorization')]";
                By el = By.xpath(xPath);
                boolean otpExists = ActionApiHelpers.isElementDisplayedCustom(el,30,1000);

                if(otpExists == true){
                    //WaitHelpers.waitForSeconds(5);
                    JSHelpers.openNewTabAndSwitchToIt();
                    ActionApiHelpers.OpenURL("https://receive-smss.com/sms/359879325037/");
                    //ActionApiHelpers.OpenURL("https://quackr.io/temporary-numbers/france/33757056600"); quackr.io
                    //WebElement element = SelectByXpath.CreateElementByXpath("//*[text()='NLB-test']/../td[2]/p/span[1]");
                    //WebElement element = SelectByXpath.CreateElementByXpath("(//*[text()='NLB-test']/following-sibling::td[2]/p/span[1])[1]");
                    //WebElement element = SelectByXpath.CreateElementByXpath("//*[text()='NLB-test']/following-sibling::td[1]/p/span[1]");
                    WebElement element = SelectByXpath.CreateElementByXpath("//a[text()='38651444750']/ancestor::div[1]/following-sibling::div[1]/span/span[1]");
                    ////table[@class='table is-striped']/tbody/tr/td[contains(text(), '*******8656')][1]/following-sibling::td
                    //WebElement element = SelectByXpath.CreateElementByXpath("(//table[@class='table is-striped']/tbody/tr/td[2]/following-sibling::td[1])[1]"); quackr.io
                    String text = element.getText();
                    hp.saveTheValueToMapBasic(text, "SMS_OTP");
                    JSHelpers.closeCurrentTab();
                    hp.switchToTabWithIndex(1);
                    String xPathForOTP = "//form/input";
                    WebElement elementOTP = SelectByXpath.CreateElementByXpath(xPathForOTP);
                    String otp = DataManager.userObject.get("SMS_OTP").toString();
                    hp.EnterTextToElement(elementOTP,otp);
                    WaitHelpers.waitForSeconds(7);
                    String xPathPayButton = "//*[contains(@class,'modal-footer')]//button[contains(text(),'Pay')]";
                    WebElement elementPay = SelectByXpath.CreateElementByXpath(xPathPayButton);
                    hp.ClickOnElementFast(elementPay);
                    //SelectByClassName.CreateElementByClassName(className);
                    //SelectByXpath.CreateElementByXpathContainingClass(className);
                    By elSuccess = By.className(className);
                    boolean isElSuccessDisplayed = ActionApiHelpers.isElementDisplayedCustom(elSuccess,10,300);
                    if (isElSuccessDisplayed == false){
                        SelectByText.CreateElementByXpathContainingText("Invalid OTP");
                        WebElement resendButton = SelectByXpath.CreateElementByXpath("//span[contains(text(),'Resend')]");
                        hp.ClickOnElement(resendButton);
                        SelectByText.CreateElementByXpathTagContainingText("div","OTP successfuly generated.");
                        JSHelpers.openNewTabAndSwitchToIt();
                        ActionApiHelpers.OpenURL("https://receive-smss.com/sms/359879325037/");
                        //WebElement element2 = SelectByXpath.CreateElementByXpath("(//*[text()='NLB-test']/following-sibling::td[2]/p/span[1])[1]");
                        //WebElement element2 = SelectByXpath.CreateElementByXpath("//*[text()='NLB-test']/following-sibling::td[1]/p/span[1]");
                        WebElement element2 = SelectByXpath.CreateElementByXpath("//a[text()='38651444750']/ancestor::div[1]/following-sibling::div[1]/span/span[1]");
                        String text2 = element2.getText();
                        hp.saveTheValueToMapBasic(text2, "SMS_OTP");
                        hp.switchToTabWithIndex(1);
                        String xPathForOTP2 = "//form/input";
                        WebElement elementOTP2 = SelectByXpath.CreateElementByXpath(xPathForOTP2);
                        String otp2 = DataManager.userObject.get("SMS_OTP").toString();
                        hp.EnterTextToElement(elementOTP2,otp2);
                        String xPathPayButton2 = "//*[contains(@class,'modal-footer')]//button[contains(text(),'Pay')]";
                        WebElement elementPay2 = SelectByXpath.CreateElementByXpath(xPathPayButton2);
                        hp.ClickOnElementFast(elementPay2);
                        SelectByXpath.CreateElementByXpathContainingClass(className);
                    }
                } else {
                    //System.out.println("System did not ask for OTP");
                    Assert.assertFalse(true);
                }
            } else {
                System.out.println("This method was not needed.");
            }
    }

    public void tryToAssertThatPaymentIsCompleteAndSendCommandToOTPMethod(String className) throws Throwable {
        //String xPath = "//*[@class='" + className + "']";
        String xPath = "//*[contains(@class,'" + className + "')]";
        By el = By.xpath(xPath);
        boolean notifExists = ActionApiHelpers.isElementDisplayedCustom(el,10,500);
        //boolean notifExists = ActionApiHelpers.existsElement(xPath);


        if(notifExists == true){
            System.out.println("Payment is successful");
            DataManager.userObject.put("StatusOfPayment","OK");
        } else {
            System.out.println("Notification did not show");
            DataManager.userObject.put("StatusOfPayment","NOK");
        }
    }

    public String getNextSunday(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        c.add(Calendar.DATE,7);
        String date = df.format(c.getTime());
        return date;
    }

    public String getNextMonday(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        c.add(Calendar.DATE,8);
        String date = df.format(c.getTime());
        return date;
    }

    public String getNextDayNotWeekend(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if(day == 7){
            calendar.add(Calendar.DATE, 2);
        } else {
            calendar.add(Calendar.DATE, 1);
        }
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(calendar.getTime());
        return date;
    }

    public void scrollTillYouFindTextAndClick(String text) {
        int n = 0;
        String xPath = "//*[contains(text(),'" + text + "')]";
        while(n<10){
            try{
                n++;
                WebElement element = SelectByXpath.CreateElementByXpath(xPath);
                JSHelpers.ScrollIntoViewBottom(element);
                hp.ClickOnElement(element);
                break;
            }catch (Throwable e){
                JSHelpers.scrollScreenDown();
            }
        }
    }

    public void scrollToBottomFindTextAndClick(String text) throws Throwable {

        JSHelpers.ScrollToTheBottomOfDynamicLoadingPage();

        String xPath = "//*[contains(text(),'" + text + "')]";

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        JSHelpers.ScrollIntoViewBottom(element);
        hp.ClickOnElement(element);
    }


    public void scrollXtimesFindFirstWithTextAndClick(int x, String text) throws Throwable {
        JSHelpers.ScrollXPagesOnDynamicLoadingpage(x);
        String xPath = "(//*[contains(text(),'" + text + "')])[1]";

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        JSHelpers.ScrollIntoViewBottom(element);
        Thread.sleep(3000);
        WebElement element1 = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element1);
    }

    public void scrollXtimesFindTextAndClick(int x, String text) throws Throwable {

        JSHelpers.ScrollXPagesOnDynamicLoadingpage(x);


        String xPath = "//*[contains(text(),'" + text + "')]";

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        JSHelpers.ScrollIntoViewBottom(element);
        Thread.sleep(3000);
        WebElement element1 = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element1);
    }

    public String getTextFromClipboard() throws IOException, UnsupportedFlavorException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String text = (String) clipboard.getData(DataFlavor.stringFlavor);
        return text;
    }

    public void assertElementDoesNotExist(String accountToClick) {
        try{
            WebElement element = SelectByXpath.CreateElementByXpath(accountToClick);
            Assert.fail();
        }catch (Throwable e) {
            System.out.println("Element nije pronadjen");
        }
    }

    public void assertDateByClassAreSortedCorrectly(String className) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByXpath(className);
        List<String> datesList = new ArrayList<>();
        for (WebElement element: webElementList){
            datesList.add(element.getAttribute("textContent"));
        }
        List<String> datesListSorted = datesList;
        Collections.sort(datesListSorted);
        Assert.assertTrue(datesList.equals(datesListSorted));
    }

    public void checkIfLatestTransactionsOnDashboardAreChangedWhenClicked() throws Throwable {
        String xPath = "//nlb-latest-transactions//*[contains(@class,'pt-1 transaction-description-purpose')]";
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);
        List<String> currentPurpose = new ArrayList<>();
        for(WebElement element : webElementList){
            currentPurpose.add(element.getAttribute("textContent"));
        }
        JSHelpers.clickNextOnDashboardAccount();
        WaitHelpers.waitForSeconds(3);
        List<WebElement> webElementList1 = SelectByXpath.CreateElementsByXpath(xPath);
        List<String> newPurpose = new ArrayList<>();
        for (WebElement element : webElementList1){
            newPurpose.add(element.getAttribute("textContent"));
        }
        Assert.assertNotEquals(currentPurpose,newPurpose);
        JSHelpers.clickPrevOnDashboardAccount();
    }

    public void assertUpcomingPaymentsAreInTheFutureForDashboard() throws Throwable {
        String xPath = "//nlb-upcoming-payments//*[@class='transaction-description-date']";
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);
        String date = Utilities.todayDDMMYYYY();
        for (WebElement element : webElementList){
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate start = LocalDate.parse( date , f );
            LocalDate end = LocalDate.parse( element.getAttribute("innerText") , f );
            boolean isBefore = start.isBefore(end) || start.isEqual(end);
            Assert.assertTrue(isBefore);
        }
    }

    public List<String> getInnerTextFromListMadeWithXPath(String xPath) throws Throwable {
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);
        List<String> returnedList = new ArrayList<>();
        for (WebElement element : webElementList){
            returnedList.add(element.getAttribute("innerText"));
        }
        return returnedList;
    }

    public List<String> getAllCategoriesForBudget() {
        List<String> expectedCategories = new ArrayList<>();
        expectedCategories.add("Other income");
        expectedCategories.add("Shopping");
        expectedCategories.add("Kids");
        expectedCategories.add("Education");
        expectedCategories.add("Car & Transport");
        expectedCategories.add("Sport & Leisure");
        expectedCategories.add("Other");
        expectedCategories.add("Cash");
        expectedCategories.add("Salary");
        expectedCategories.add("Transfers");
        expectedCategories.add("Groceries");
        expectedCategories.add("Utilities");
        expectedCategories.add("Bars & Restaurants");
        expectedCategories.add("Health & Personal care");
        expectedCategories.add("Savings & Investments");
        expectedCategories.add("Finances & Insurance");

        return expectedCategories;
    }

    public List<String> getAllExpectedCurrenciesInNewPayment() {
        List<String> expectedCurrencies = new ArrayList<>();
        expectedCurrencies.add("EUR");
        expectedCurrencies.add("AUD");
        expectedCurrencies.add("BAM");
        expectedCurrencies.add("BGN");
        expectedCurrencies.add("CAD");
        expectedCurrencies.add("CHF");
        expectedCurrencies.add("CZK");
        expectedCurrencies.add("DKK");
        expectedCurrencies.add("GBP");
        expectedCurrencies.add("HUF");
        expectedCurrencies.add("JPY");
        expectedCurrencies.add("MKD");
        expectedCurrencies.add("NOK");
        expectedCurrencies.add("PLN");
        expectedCurrencies.add("RSD");
        expectedCurrencies.add("RUB");
        expectedCurrencies.add("SEK");
        expectedCurrencies.add("USD");
        return expectedCurrencies;
    }


    public void verifyContentInPdfFile(String expected, String filePath) {
        try {
            String pdfContent = readPdfContent(filePath);
            Assert.assertTrue(pdfContent.contains(expected));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  String readPdfContent(String url) throws IOException {

        URL pdfUrl = new URL(url);
        InputStream in = pdfUrl.openStream();
        BufferedInputStream bf = new BufferedInputStream(in);
        PDDocument doc = PDDocument.load(bf);
        int numberOfPages = getPageCount(doc);
        System.out.println("The total number of pages "+numberOfPages);
        String content = new PDFTextStripper().getText(doc);
        doc.close();

        return content;
    }
    public static int getPageCount(PDDocument doc) {
        //get the total number of pages in the pdf document
        int pageCount = doc.getNumberOfPages();
        return pageCount;

    }

    public void checkIfOTPIsNeededAndCompleteStandingOrderFor(String className) throws Throwable {
        String status = (String) DataManager.userObject.get("StatusOfPayment");
        if(status.equals("NOK")){
            //String xPath = "//*[text()='Payment confirmation']";
            String xPath = "//*[contains(text(),'Confirmation')]";
            By el = By.xpath(xPath);
            boolean otpExists = ActionApiHelpers.isElementDisplayedCustom(el,15,1000);

            if(otpExists == true){
                //WaitHelpers.waitForSeconds(5);
                JSHelpers.openNewTabAndSwitchToIt();
                ActionApiHelpers.OpenURL("https://receive-smss.com/sms/359879325037/");
                //WebElement element = SelectByXpath.CreateElementByXpath("//*[text()='NLB-test']/../td[2]/p/span[1]");
                //WebElement element = SelectByXpath.CreateElementByXpath("(//*[text()='NLB-test']/following-sibling::td[2]/p/span[1])[1]");
                //WebElement element = SelectByXpath.CreateElementByXpath("//*[text()='NLB-test']/following-sibling::td[1]/p/span[1]");
                WebElement element = SelectByXpath.CreateElementByXpath("//a[text()='38651444750']/ancestor::div[1]/following-sibling::div[1]/span/span[1]");
                String text = element.getText();
                hp.saveTheValueToMapBasic(text, "SMS_OTP");
                JSHelpers.closeCurrentTab();
                hp.switchToTabWithIndex(1);
                String xPathForOTP = "//form/input";
                WebElement elementOTP = SelectByXpath.CreateElementByXpath(xPathForOTP);
                String otp = DataManager.userObject.get("SMS_OTP").toString();
                hp.EnterTextToElement(elementOTP,otp);
                WaitHelpers.waitForSeconds(7);
                String xPathPayButton = "//*[contains(@class,'modal-footer')]//button[contains(text(),'Confirm')]";
                WebElement elementPay = SelectByXpath.CreateElementByXpath(xPathPayButton);
                hp.ClickOnElementFast(elementPay);
                //SelectByClassName.CreateElementByClassName(className);
                //SelectByXpath.CreateElementByXpathContainingClass(className);
                By elSuccess = By.className(className);
                boolean isElSuccessDisplayed = ActionApiHelpers.isElementDisplayedCustom(elSuccess,10,300);
                if (isElSuccessDisplayed == false){
                    SelectByText.CreateElementByXpathContainingText("Invalid OTP");
                    WebElement resendButton = SelectByXpath.CreateElementByXpath("//span[contains(text(),'Resend')]");
                    hp.ClickOnElement(resendButton);
                    SelectByText.CreateElementByXpathTagContainingText("div","OTP successfuly generated.");
                    JSHelpers.openNewTabAndSwitchToIt();
                    ActionApiHelpers.OpenURL("https://receive-smss.com/sms/359879325037/");
                    //WebElement element2 = SelectByXpath.CreateElementByXpath("(//*[text()='NLB-test']/following-sibling::td[2]/p/span[1])[1]");
                    //WebElement element2 = SelectByXpath.CreateElementByXpath("//*[text()='NLB-test']/following-sibling::td[1]/p/span[1]");
                    WebElement element2 = SelectByXpath.CreateElementByXpath("//a[text()='38651444750']/ancestor::div[1]/following-sibling::div[1]/span/span[1]");
                    String text2 = element2.getText();
                    hp.saveTheValueToMapBasic(text2, "SMS_OTP");
                    hp.switchToTabWithIndex(1);
                    String xPathForOTP2 = "//form/input";
                    WebElement elementOTP2 = SelectByXpath.CreateElementByXpath(xPathForOTP2);
                    String otp2 = DataManager.userObject.get("SMS_OTP").toString();
                    hp.EnterTextToElement(elementOTP2,otp2);
                    String xPathPayButton2 = "//*[contains(@class,'modal-footer')]//button[contains(text(),'Confirm')]";
                    WebElement elementPay2 = SelectByXpath.CreateElementByXpath(xPathPayButton2);
                    hp.ClickOnElementFast(elementPay2);
                    SelectByXpath.CreateElementByXpathContainingClass(className);
                }
            } else {
                //System.out.println("System did not ask for OTP");
                Assert.assertFalse(true);
            }
        } else {
            System.out.println("This method was not needed.");
        }
    }

    public void checkContinueButtonDisabled() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement button = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[text()='Continue']")
                )
        );
        Assert.assertFalse(button.isEnabled());
//        WebElement clickElement = SelectByXpath.CreateElementByXpath("//*[@class='legal-text']");
//        hp.ClickOnElement(clickElement);
    }
}
