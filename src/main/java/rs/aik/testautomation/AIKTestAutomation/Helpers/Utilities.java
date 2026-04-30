package rs.aik.testautomation.AIKTestAutomation.Helpers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Action.Queries;
import rs.aik.testautomation.AIKTestAutomation.Selectors.SelectByXpath;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Data.ExcelFactory;
import rs.aik.testautomation.AIKTestAutomation.Test.RunTest;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Collectors;

/**
 * Class contains various usefully methods.
 */
public class Utilities {
    //region - Regex utility -
    /**
     * Function for checking regular expression
     * @param pattern - Regex pattern for checking excpression
     * @param value - text to be checked
     * @return true or false
     */
    public static boolean regExCheck(String pattern, String value){
        boolean result = Pattern.compile(pattern).matcher(value).matches();
        return result;
    }
    /**
     * Function for checking has value on 4 digits
     * @param text - text to be checked
     * @return true or false
     */
    public static boolean regExCurrency4digits(String text) {
        boolean result = Pattern.compile("^(\\d{1,3})(\\.[0-9]{4})?$").matcher(text).matches();
        return result;
    }

    /**
     * ^[0-9]{10} - 10 digit account
     *  space
     *  \( - starts bracket
     *  [a-zA-Z]{3} - currency string length 3
     *  space
     *  \$?(([1-9]\d{0,2}(,\d{3})*)|0)?\.\d{1,2} - amount in format 99,999.99
     *  \)$ - ends bracket
     * @param text Text that should be checked.
     * @return Is text match regex.
     */
    public static boolean regExNumberCurrencyAvailableBalance(String text) {
        boolean result = Pattern.compile("^[0-9]{10} \\([a-zA-Z]{3} \\$?(([1-9]\\d{0,2}(,\\d{3})*)|0)?\\.\\d{1,2}\\)$").matcher(text).matches();
        return result;
    }
    //endregion - Regex utility -

    //region - Date/Time formatting utility -

    /**
     * Function returns custom formatted date time
     * @param localDate Date/time for format
     * @param format String - format
     * @return String
     */
    public static String getFormattedDateTime(LocalDateTime localDate, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        //LocalDateTime d = LocalDateTime.now();
        //d = d.plusMinutes(-min);
        date = Timestamp.valueOf(localDate);
        return formatter.format(date);
    }

    /**
     * Returns string between 7 and 10 characters (length 4)
     * @param date Date as string in format DD.MM.YYYY
     * @return String
     */
    public static String getYear(String date){
        String result = date.substring(6,10);
        return result;
    }

    /**
     * Returns string between 7 and 10 characters (length 4)
     * @param date Date as string in format DD.MM.YYYY
     * @return String
     */
    public static String getYearWord(String date){
        String result = date.substring(7,11);
        return result;
    }

    /**
     * Returns string between 4 and 6 characters (length 2)
     * @param date Date as string in format DD.MM.YYYY
     * @return String
     */
    public static String getMonth(String date){
        String result = date.substring(3,5);
        if (result.substring(0,1).equals("0"))
            result = result.substring(1,2);
        int i = Integer.parseInt(result);
        result = String.valueOf(i-1);
        return result;
    }

    /**
     * Returns DD-Mon-YYYY format (e.g. 28-Dec-2021) for date parameter @date in format (28.12.2021 or (28-12-2021)
     * @param date Date as String in format DD.MM.YYYY or DD-MM-YYYY
     * @return String in format DD-Mon-YYYY
     */
    public static String WordToYYYYMMDD(String date){
        String yyyy = date.substring(7,11);
        String mon = date.substring(3,6);
        String result = "";
        switch(mon) {
            case "Jan":
                result = "01";
                break;
            case "Feb":
                result = "02";
                break;
            case "Mar":
                result = "03";
                break;
            case "Apr":
                result = "04";
                break;
            case "May":
                result = "05";
                break;
            case "Jun":
                result = "06";
                break;
            case "Jul":
                result = "07";
                break;
            case "Aug":
                result = "08";
                break;
            case "Sep":
                result = "09";
                break;
            case "Oct":
                result = "10";
                break;
            case "Nov":
                result = "11";
                break;
            case "Dec":
                result = "12";
                break;
        }
        String dd = date.substring(0,2);
        return yyyy.concat("-").concat(result).concat("-").concat(dd);
    }

    /**
     * Returns abbreviation (short form) of month
     * @param date Date as String in format DD.MM.YYYY or DD-MM-YYYY
     * @return Sting length 3 characters
     */
    public static String getMonthWord(String date){
        String mon = date.substring(3,6);
        String result = "";
        switch(mon){
            case "Jan" :
                result = "0";
                break;
            case "Feb" :
                result = "1";
                break;
            case "Mar" :
                result = "2";
                break;
            case "Apr" :
                result = "3";
                break;
            case "May" :
                result = "4";
                break;
            case "Jun" :
                result = "5";
                break;
            case "Jul" :
                result = "6";
                break;
            case "Aug" :
                result = "7";
                break;
            case "Sep" :
                result = "8";
                break;
            case "Oct" :
                result = "9";
                break;
            case "Nov" :
                result = "10";
                break;
            case "Dec" :
                result = "11";
                break;
        }
        return result;
    }

    /**
     * Method returns day as number without zero as string from date as string in format DD.MM.YYYY or DD-MM-YYYY or
     * DD-Mon-YYYY
     * @param date String day number without zero
     * @return
     */
    public static String getDay(String date){
        String result = date.substring(0,2);
        if (result.substring(0,1).equals("0"))
            result = result.substring(1,2);
        return result;
    }

    /**
     * Transforms YYYY.MM.DD..... to DD.MM.YYYY
     * Example 2021-10-04 21:13:27.857 -> 04.10.2021
     * @param date Data in YYYY.MM.DD... format
     * @return String
     */
    public static String ToDateDDMMYYYY(String date){
        return date.substring(8,10).concat(".").concat(date.substring(5,7)).concat(".").concat(date.substring(0,4));
    }

    /**
     * Transforms DD.MM.YYYY..... to YYYY-MM-DD
     * Example 04.10.2021 -> 2021-10-04 21:13:27.857
     * @param date Data in DD.MM.YYYY... format
     * @return String in format "YYYY-MM-DD"
     */
    public static String ToDateYYYYMMDD(String date){
        return date.substring(6,10).concat("-").concat(date.substring(3,5)).concat("-").concat(date.substring(0,2));
    }

    /**
     * Returns date and time as string in format yyyy-MM-dd'T'HH:mm:ss.SSSS
     * Value is Now minus minutes in parameter min
     * @param min Number of minutes that should be subtracted from current date time
     * @return String with value in format "yyyy-MM-dd'T'HH:mm:ss.SSSS"
     */
    public static String GetDateTimeNowMinusMinutes(int min){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
        Date date = new Date();
        LocalDateTime d = LocalDateTime.now();
        d = d.plusMinutes(-min);
        date = Timestamp.valueOf(d);
        return formatter.format(date);
    }

    public static String getSimpleMonthFormat(String formatString){
        return new SimpleDateFormat(formatString, Locale.ENGLISH).format(new Date());
    }

    public static String getSimpleMonthFormatEnglish(String formatString){
        return new SimpleDateFormat(formatString, Locale.ENGLISH).format(new Date());
    }

    public static LocalDateTime now(){
        LocalDateTime d = LocalDateTime.now();
        return d;
    }

    public static String getDDMMYYYYDate(LocalDateTime d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
        Date date = Timestamp.valueOf(d);
        return formatter.format(date);
    }

    public static String timeBetween(LocalDateTime newTime, LocalDateTime oldTime){
        long h = oldTime.getHour();
        long m = oldTime.getMinute();
        long s = oldTime.getSecond();
        long oldSec =  (h * 3600) + (m * 60) + s;
        h = newTime.getHour();
        m = newTime.getMinute();
        s = newTime.getSecond();
        long newSec =  (h * 3600) + (m * 60) + s - oldSec;
        if(newSec < 0)
            return "0";
        else
            return String.valueOf(newSec/60).concat("m ").concat(String.valueOf(newSec % 60)).concat("s");
    }
    //endregion - Date/Time formatting utility -

    //region - Data/Time compare utility -
    /**
     * Return true if date first greater then second
     * @param first Date in format DD.MM.YYYY
     * @param second Date in format DD.MM.YYYY
     * @return Boolean true - first is greater, false - second is equal or greater
     */
    public static boolean isGraterDate(String first, String second){
        boolean res = false;
        String firstT = first.substring(6,10).concat(first.substring(3,5)).concat(first.substring(0,2));
        String secondT = second.substring(6,10).concat(second.substring(3,5)).concat(second.substring(0,2));
        int r = firstT.compareTo(secondT);
        if(r > 0)
            res = true;
        return res;
    }

    /**
     * Return true if date first less than second
     * @param first Date in format DD.MM.YYYY
     * @param second Date in format DD.MM.YYYY
     * @return Boolean true - first is less, false - second is less or equal
     */
    public static boolean isLessDate(String first, String second){
        boolean res = false;
        String firstT = first.substring(6,10).concat(first.substring(3,5)).concat(first.substring(0,2));
        String secondT = second.substring(6,10).concat(second.substring(3,5)).concat(second.substring(0,2));
        int r = firstT.compareTo(secondT);
        if(r < 0)
            res = true;
        return res;
    }

    public static String getNowYYYYMMDD(){
        LocalDateTime d = LocalDateTime.now();
        return getFormattedDateTime(d, "yyyy-MM-dd");
    }

    public static String getNowYYYYMMDD_HHMMSS(){
        LocalDateTime d = LocalDateTime.now();
        return getFormattedDateTime(d, "yyyy-MM-dd hh:mm:ss");
    }

    public static String getNowYYYYMMDD_HHMMSSPlus(){
        return getNowYYYYMMDD_HHMMSS().concat(".000001");
    }
    //endregion - Data/Time compare utility -

    //region - Various util -
    /**
     * Method replaces text newLine with characters for new line
     * @param data
     * @return
     */
    public static String ConvertMultiline(String data){
        return data.replace("newLine", Keys.chord(Keys.SHIFT, Keys.ENTER));
    }

    /**
     * Provides method to get absolute value for float data as parameter
     * @param data Data to evaluate
     * @return float
     */
    public static float Absolute(float data){
        return Math.abs(data);
    }

    /**
     * Provides getting random GUID as string
     * @return String random GUID
     */
    public static String GetGUID(){
        return java.util.UUID.randomUUID().toString();
    }
    //endregion - Various util -

    //region - Files -

    /**
     * Upload file to folder testdata with path in parameter @path
     * @param element WebElement for uploading
     * @param path String path for set into element
     */
    public static void uploadFileToTestData(WebElement element, String path){
        File forUploadFile = new File("testdata/" + path);
        element.sendKeys(forUploadFile.getAbsolutePath());
        Log.info("!!!!! File " + path + " successfully uploaded !!!!!!!");
    }

    /**
     * method isFileDownloadName checks if file is download in last minute
     * @param downloadPath path to the folder 'download' as string
     * @return true - if element is downloaded, false - otherwise
     */
    public static boolean isFileDownloadInLastMinute(String downloadPath) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].lastModified() > System.currentTimeMillis() - 60 * 1000) {
                Log.info("!!!!!!!!!!!!File has been found in folder: " +downloadPath+ " and deleted !!!!!!!!!!");
                //File has been found, it can now be deleted
                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    /**
     * method isFileDownloadName checks if file is download
     *
     * @param downloadPath path to the folder 'download' as string
     * @param fileName full file name
     * @return true - if element is downloaded, false - otherwise
     */

    public static boolean isFileDownloadName(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName)) {
                //File has been found, it can now be deleted
                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    /**
     * method isFileDownloadName checks if file is download
     *
     * @param downloadPath path to the folder 'download' as string
     * @param fileName the name starts with fileName
     * @return true - if element is downloaded, false - otherwise
     */

    public static boolean isFileDownloadStartName(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().startsWith(fileName)) {
                //File has been found, it can now be deleted
                dirContents[i].delete();
                return true;
            }

        }
        return false;
    }

    public static boolean replaceTestrailExcelFile() throws IOException {
        boolean res = false;
        URL rootResource = RunTest.class.getClassLoader().getResource(".");
        String targetFolder;
        if(rootResource == null) {
            targetFolder = new File(".").getAbsolutePath().replace("%20", " ");
        } else {
            File currentDir = new File(RunTest.class.getClassLoader().getResource(".").getFile());
            targetFolder = currentDir.getParentFile().getParent();
        }
        File resultFile = new File(targetFolder.replace("%20", " "), "results");
        File testData =  new File(targetFolder.replace("%20", " "), "testdata");
        File destinationFile = new File(resultFile, "SanityStatus.xlsx");
        if(destinationFile.exists()) {
            destinationFile.delete();
        }

        Files.copy(Paths.get(new File(testData, "SanityStatus.xlsx").getAbsolutePath().replace("%20", " ")),
                Paths.get(destinationFile.getAbsolutePath().replace("%20", " ")));
        res = true;
        return res;
    }

    public static boolean replaceTestExcelFile() throws IOException {
        boolean res = false;
        URL rootResource = RunTest.class.getClassLoader().getResource(".");
        String targetFolder;
        if(rootResource == null) {
            targetFolder = new File(".").getAbsolutePath().replace("%20", " ");
        } else {
            File currentDir = new File(RunTest.class.getClassLoader().getResource(".").getFile());
            targetFolder = currentDir.getParentFile().getParent();
        }
        File resultFile = new File(targetFolder.replace("%20", " "), "results");
        File testData =  new File(targetFolder.replace("%20", " "), "testdata");
        File destinationFile = new File(resultFile, "TestResults.xlsx");
        if(destinationFile.exists()) {
            destinationFile.delete();
        }

        Files.copy(Paths.get(new File(testData, "TestResults.xlsx").getAbsolutePath().replace("%20", " ")),
                Paths.get(destinationFile.getAbsolutePath().replace("%20", " ")));
        res = true;
        return res;
    }

    public static void cleanScreenShotFolder() throws IOException {
        URL rootResource = RunTest.class.getClassLoader().getResource(".");
        String targetFolder;
        if(rootResource == null) {
            targetFolder = new File(".").getAbsolutePath().replace("%20", " ");
        } else {
            File currentDir = new File(RunTest.class.getClassLoader().getResource(".").getFile());
            targetFolder = currentDir.getParentFile().getParent();
        }
        File folder = new File(targetFolder.replace("%20", " "), "results/LastSession");
        FileUtils.cleanDirectory(folder);

    }

    public static void testSaveResults(List<HashMap<String, Object>> arr) throws IOException {
        HashMap<String, Object> a = new HashMap<>();
        a.put("name", "Test one");
        a.put("result", "pass");
        arr.add(a);
        HashMap<String, Object> b = new HashMap<>();
        b.put("name", "Test two");
        b.put("result", "pass");
        arr.add(b);
        HashMap<String, Object> c = new HashMap<>();
        c.put("name", "Test three");
        c.put("result", "fail");
        arr.add(c);
        saveResults(arr);
    }

    public static void saveStringToFile(String text, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(text);
        writer.close();
    }

    public static void addStringToFile(String text, String fileName) throws IOException {
        try {
            Files.write(Paths.get(fileName), text.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public static void trySaveResults (List<HashMap<String, Object>> arr){
        try {
            Utilities.saveResults(arr);
        }
        catch (Exception e){}
    }

    public static void saveResults(List<HashMap<String, Object>> arr) throws IOException {
        FileWriter writer = new FileWriter("results/fail.txt");
        int pass = 0;
        int fail = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(dtf);

        writer.write("<TABLE id=\"table-1\" class=\"table table-hover\">" + System.lineSeparator());
        writer.write("<THead>" + System.lineSeparator());
        writer.write("<TR><TH>Scenario name</TH><TH>Result</TH></TR>" + System.lineSeparator());
        writer.write("</THead>" + System.lineSeparator());
        writer.write("<TBody>" + System.lineSeparator());

        /*for(HashMap<String, Object> item: arr) {
            if (item.get("result") != null) {
                String s = "<TR><TD>" + item.get("name") + "</TD><TD>" + item.get("result") + "</TD></TR>";
                writer.write(s + System.lineSeparator());
                if (item != null)
                    if (item.get("result").equals("FAIL"))
                        fail++;
                    else
                        pass++;
            }
        }*/

        for(HashMap<String, Object> item: arr) {
            if (item.get("result") != null) {
                String s = "<TR><TD>" + item.get("name") + "</TD><TD>" + item.get("result") + "</TD></TR>";
                if (item != null)
                    if (item.get("result").equals("FAIL")){
                        writer.write(s + System.lineSeparator());
                        fail++;}
                    else
                        pass++;
            }
        }

        writer.write("</TBody>" + System.lineSeparator());
        writer.write("</TABLE>" + System.lineSeparator());
        writer.close();
        FileWriter wRes = new FileWriter("results/res.txt");
        wRes.write("<TABLE id=\"table-2\" class=\"table table-hover\"><THead>" + System.lineSeparator());
        wRes.write("<TR><TH>Status</TH><TH>" + today + "</TH></TR>" + System.lineSeparator());
        wRes.write("</THead>" + System.lineSeparator());
        wRes.write("<TBody>" + System.lineSeparator());
        wRes.write("<TR><TD>PASSED</TD><TD>" + String.valueOf(pass) + "</TD></TR>" + System.lineSeparator());
        wRes.write("<TR><TD>FAIL</TD><TD>" + String.valueOf(fail) + "</TD></TR>" + System.lineSeparator());
        wRes.write("</TBody>" + System.lineSeparator());
        wRes.write("</TABLE>" + System.lineSeparator());
        wRes.close();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("results/days.txt"));
            List<String> dani = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                // read next line
                line = reader.readLine();
                dani.add(line);
            }
            reader.close();

            boolean ima = false;
            for (String item : dani) {
                if(item != null)
                    if(item.contains(today)){
                        item = today.concat(";").concat(String.valueOf(pass).concat(";").concat(String.valueOf(fail)));
                        ima = true;
                    }
            }
            if(!(ima))
                dani.add(today.concat(";").concat(String.valueOf(pass).concat(";").concat(String.valueOf(fail))));
            //dani.sort(String.CASE_INSENSITIVE_ORDER);
            while (dani.remove(null));
            Collections.sort(dani);
            List<String> last7 = new ArrayList<>();
            if (dani.size()> 7) {
                last7 = dani.subList(dani.size() - 7, dani.size());
            }
            else {
                last7 = dani;
            }
            FileWriter sDays = new FileWriter("results/days.txt");
            for(String s : last7){
                sDays.write(s + System.lineSeparator());
            }
            sDays.close();

            FileWriter wDays = new FileWriter("results/daysRes.txt");
            wDays.write("<TABLE id=\"table-3\" class=\"table table-hover\">" + System.lineSeparator() + "<TR><TH>Date</TH>");
            for (String item : last7) {
                String[] arrOfStr = item.split(";");
                wDays.write("<TH>" + arrOfStr[0] +"</TH>");
            }
            wDays.write("</TR>" + System.lineSeparator() + "<TR><TD>Passed</TD>");
            for (String item : last7) {
                String[] arrOfStr = item.split(";");
                wDays.write("<TD>" + arrOfStr[1] +"</TD>");
            }
            wDays.write("</TR>" + System.lineSeparator() + "<TR><TD>Fail</TD>");
            for (String item : last7) {
                String[] arrOfStr = item.split(";");
                wDays.write("<TD>" + arrOfStr[2] +"</TD>");
            }
            wDays.write("<TR>" + System.lineSeparator());
            wDays.write("</TABLE>" + System.lineSeparator());
            wDays.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveTheValueToFile(String randomPurpose, String key) {
        //final String outputFilePath = "C:\\Users\\Jovan Jacov\\Downloads\\DTC Test Repo\\testdata\\assertkeys.txt";
        final String outputFilePath = "testdata/assertkeys.txt";
        HashMap<String, String> map = new HashMap<String, String>();

        File file = new File(outputFilePath);

        BufferedWriter bf = null;
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String name = parts[0].trim();
                String purpose = parts[1].trim();

                if (!name.equals("") && !purpose.equals("")){
                    map.put(name,purpose);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        map.put(key,randomPurpose);
        try{
            bf = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String,String> entry : map.entrySet()){
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }

            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bf.close();
            } catch (Exception e) {
            }
        }
    }

    public static String getDataFromTxtFileUnderKey(String key){
        //final String filePath = "C:\\Users\\Jovan Jacov\\Downloads\\DTC Test Repo\\testdata\\assertkeys.txt";
        final String filePath = "testdata/assertkeys.txt";

        HashMap<String,String> map = new HashMap<String, String>();
        BufferedReader br = null;

        try{
            File file = new File(filePath);

            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null){
                String[] parts = line.split(":");

                String name = parts[0].trim();
                String purpose = parts[1].trim();

                if (!name.equals("") && !purpose.equals("")){
                    map.put(name,purpose);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(br != null){
                try{
                    br.close();
                }
                catch (Exception e){

                }
            }
        }
        String returnedValue = map.get(key);
        return returnedValue;
    }


    //endregion - Files -

    //region - Testrail -
    public static String getBasic64(String username, String password){
        String userpass = username + ":" + password;
        return "Basic :" + new String(Base64.getEncoder().encode(userpass.getBytes()));
    }

    public static String getOneCase(int n) throws SQLException {
        String result = "";
        for(HashMap<String, Object> item : DataManager.resultContainer){
            String c = item.get("id").toString();
            String cmp = "C".concat(String.valueOf(n));
            if (c.contains(cmp)){
                String[] idColumn =  c.split(",");
                String priorityColumn = item.get("priority").toString();
                String[] priority = priorityColumn.split(",");
                int i = 0;
                for (String s : idColumn){
                    if (s.equals(cmp)){
                        break;
                    }
                    i++;
                }
                String priorityOfTest = priority[i];
                String r = "";
                if (item.get("result").toString().equals("PASSED")){
                    r = "{\n" +
                            "            \"case_id\": " + String.valueOf(n) + ",\n" +
                            "            \"status_id\": 1,\n" +
                            "            \"comment\": \"Test completed successful!\",\n" +
                            "            \"elapsed\": \"" + item.get("time").toString() + "\"\n" +
                            "        }";
                    ExcelFactory.addRowInExcel("results/SanityStatus.xlsx", "Sheet1", cmp, priorityOfTest, "1", "Web");
                    Queries.writeToTestRailImportResultsTable(cmp, priorityOfTest, "1", "Web", DataManager.DateHHMMSS());
                }
                else {
                    String err;
                    try{
                        err = item.get("message").toString();
                    }
                    catch (Exception e){
                        err = "No error message!";
                    }
                    if (err.length()>255)
                        err = err.substring(0,254);
                    err = err.replace('"', '?');
                    err = err.replace('{', '(');
                    err = err.replace('}', ')');
                    try{
                        err = err.replace('\n', ' ');
                    }
                    catch (Exception ex) {}

                    r = "{\n" +
                            "            \"case_id\": " + String.valueOf(n) + ",\n" +
                            "            \"status_id\": 5,\n" +
                            //"            \"comment\": \"This test failed\",\n" +
                            "            \"comment\": \"" + err + "\"\n" +
                            "        }";
                    ExcelFactory.addRowInExcel("results/SanityStatus.xlsx", "Sheet1", cmp, priorityOfTest, "5", "Web");
                    Queries.writeToTestRailImportResultsTable(cmp, priorityOfTest, "5", "Web", DataManager.DateHHMMSS());
                }
                result = r;
            }
        }
        return result;
    }
    //endregion - Testrail -

    //region - Examples from old code -
    public static String ToCustomAmount(String text){
        Float amount = Float.parseFloat(text);
        DecimalFormat twoPlacesAmount = new DecimalFormat("#,##0.00");
        return twoPlacesAmount.format(amount);
    }

    public static String AddPercentage10(String text){
        Float amount = Float.parseFloat(text);
        amount = amount * 10;
        DecimalFormat twoPlacesPercent = new DecimalFormat("##0.00%");
        return twoPlacesPercent.format(amount);
    }

    public static String AddPercentage(String text){
        return text.concat("%");
    }

    public static String ValidMMYYYY(String value){
        String v = value.substring(5,7).concat(".").concat(value.substring(0,4));
        return v;
    }

    public static String ValidMMYYYYSpec(String value, String pm){
        String yyyy = value.substring(0,4);
        String mm = value.substring(5,7);
        int y = Integer.parseInt(yyyy);

        switch (pm) {
            case "12":
                y = y - 1;
                break;
            case "24":
                y = y - 2;
                break;
            case "36":
                y = y - 3;
                break;
        }
        String v = mm.concat(".").concat(String.valueOf(y));
        return v;
    }

    public static String getFileName(String filePath){
        return FilenameUtils.getName(filePath);
    }

    public static int productSortValue(String item){
        int result = -1;
        switch (item) {
            case "CurrAcc-Icon.svg":
                result = 10;
                break;
            case "CreditCard-Inactive-Icon.svg":
                result = 20;
                break;
            case "SavingsAcc-Icon.svg":
                result = 30;
                break;
            case "Savings-Icon.svg":
                result = 40;
                break;
            case "TermDepositAcc-Icon.svg":
                result = 50;
                break;
            case "SecuritiesAcc-Icon.svg":
                result = 60;
                break;
            case "InvestmentAcc-Icon.svg":
                result = 70;
                break;
            case "Vita.svg":
                result = 80;
                break;
            case "Loans-Icon.svg":
                result = 90;
                break;
        }
        return result;
    }
    //endregion - Examples from old code -

    //region - Just test -
    public static void addTestrail() throws SQLException {
        HashMap<String, Object> nh = new HashMap<>();
        nh.put("name", "Test 1");
        nh.put("id", "C63631");
        nh.put("message", "Test Error");
        nh.put("result", "FAIL");
        //DataManager.resultContainer.add(nh);
        HashMap<String, Object> nh1 = new HashMap<>();
        nh1.put("name", "Test 2");
        nh1.put("id", "");
        nh1.put("time", "2m 12s");
        nh1.put("result", "PASS");
        //DataManager.resultContainer.add(nh1);

        DataManager.datamap = ExcelFactory.getDataFromExcel("testdata/listForExecution.xlsx","Sheet1");

        int i = 0;
        for (HashMap<String, String> item : DataManager.datamap)
            if (!(item.get("ID").equals(""))){
                String myID = item.get("ID");
                if(DataManager.resultContainer.stream().filter(c -> c.get("id").equals(myID)).collect(Collectors.toList()).isEmpty()){
                    if ((i%4) != 0){
                        HashMap<String, Object> nhf = new HashMap<>();
                        nhf.put("name", item.get("TestName"));
                        nhf.put("id", item.get("ID"));
                        nhf.put("message", "Test Error 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 END");
                        nhf.put("result", "FAIL");
                        DataManager.resultContainer.add(nhf);
                        i++;
                    }
                    else{
                        HashMap<String, Object> nhf = new HashMap<>();
                        nhf.put("name", item.get("TestName"));
                        nhf.put("id", item.get("ID"));
                        nhf.put("time", "2m 12s");
                        nhf.put("result", "PASSED");
                        DataManager.resultContainer.add(nhf);
                        i++;
                    }
                }
            }


        DataManager.callTestrail();

    }
    //endregion - Just test -

    public static String removeSpecials(String text){
        return text.replace("#", "_").replace(":","_").replace(",","_").replace("?","_");
    }

    public static void replaceExecutionListWithFailedExecutionList(){
        FileSystem system = FileSystems.getDefault();
        Path original = system.getPath("results/failedExecutionList.xlsx");
        Path target = system.getPath("testdata/listForExecution.xlsx");

        try {
            // Throws an exception if the original file is not found.
            Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
            deleteAllContentInFailedExecutionListNewMethod();
        } catch (IOException ex) {
            System.out.println("ERROR");
        }

    }

    public static void deleteAllContentInFailedExecutionListNewMethod() throws IOException {
        FileInputStream file = null;
        //HSSFWorkbook wb = null;
        XSSFWorkbook wb = null;
        FileOutputStream out = null;
        try{
            file = new FileInputStream(new File("results/failedExecutionList.xlsx"));

            wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);

            for(int i = sheet.getLastRowNum(); i> sheet.getFirstRowNum(); i--){
                sheet.removeRow(sheet.getRow(i));
            }

            out = new FileOutputStream(new File("results/failedExecutionList.xlsx"));
            wb.write(out);


        }
        catch(Exception e){}
        finally{
            if(file!=null)
                file.close();
            if(out!=null)
                out.close();
            if(wb!=null)
                wb.close();
        }
    }

    public static void deleteAllContentInFailedExecutionList() throws IOException {
        FileInputStream file = null;
        //HSSFWorkbook wb = null;
        XSSFWorkbook wb = null;
        FileOutputStream out = null;
        try{
            file = new FileInputStream(new File("results/failedExecutionList.xlsx"));

            wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);

            for(int i=1; i<= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);
                deleteRow(sheet, row);
            }

            out = new FileOutputStream(new File("results/failedExecutionList.xlsx"));
            wb.write(out);


        }
        catch(Exception e){}
        finally{
            if(file!=null)
                file.close();
            if(out!=null)
                out.close();
            if(wb!=null)
                wb.close();
        }

    }

    public static void deleteRow1(XSSFSheet sheet, Row row) {
        int lastRowNum = sheet.getLastRowNum();
        int rowIndex = row.getRowNum();
        if(rowIndex >= 0 && rowIndex < lastRowNum){
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if(rowIndex == lastRowNum){
            Row removingRow = sheet.getRow(rowIndex);
            if(removingRow != null){
                sheet.removeRow(removingRow);
                System.out.println("Deleting.... ");
            }
        }
    }

    public static void deleteRow(XSSFSheet sheet, Row row) {
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum !=0 && lastRowNum >0){
            int rowIndex = row.getRowNum();
            Row removingRow = sheet.getRow(rowIndex);
            if(removingRow != null){
                sheet.removeRow(removingRow);
                System.out.println("Deleting.... ");
            }
        }
    }

    public static void savePreviousTestResults(){
        String source = "results/LastSession";
        File srcDir = new File(source);

        String sourceCucumber = "target/cucumber";
        File srcDirCucumber = new File(sourceCucumber);

        //String sourceTestResults = "C:\\Users\\Jovan Jacov\\IdeaProjects\\DTC Test Repo\\results\\TestResults.xlsx";
        String sourceTestResults = "results/TestResults.xlsx";
        String sourceSanityStatus = "results/SanityStatus.xlsx";
        File srcTestResults = new File(sourceTestResults);
        File srcSanityStatus = new File(sourceSanityStatus);
        //Path src = Paths.get(sourceTestResults);

        String date = ActionApiHelpers.getTodayDateInFormat("dd.MM.YYYY HHmmss");
        String folderName = "C:\\Rezultati\\"+date;
        File theDir = new File(folderName);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        //Path dest = Paths.get(folderName);
        //Files.copy(src.toFile(), dest.toFile());

        try {
            FileUtils.copyDirectory(srcDir, theDir);
            FileUtils.copyDirectory(srcDirCucumber,theDir);
            FileUtils.copyFileToDirectory(srcTestResults,theDir);
            FileUtils.copyFileToDirectory(srcSanityStatus,theDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String todayDDMMYYYY() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return sdf.format(new Date());
    }
    public static String getPreviousMonthInFormat(String dateFormat, int previous) {
        String dateInString = new SimpleDateFormat(dateFormat, Locale.ENGLISH).format(DateUtils.addMonths(new Date(),-previous));
        return dateInString;
    }

    public static String getPreviousMonthInFormatEnglish(String dateFormat, int previous) {
        String dateInString = new SimpleDateFormat(dateFormat, Locale.ENGLISH).format(DateUtils.addMonths(new Date(),-previous));
        return dateInString;
    }

    /**
     * Method for saving results from several executions
     * @throws Exception Custom Error
     */
    public static void archiveStatusFile() throws Exception {
        String targetStr = "results/SanityStatus.xlsx";
        List<HashMap<String, String>> delta = ExcelFactory.setListData(targetStr, "Sheet1");
        List<HashMap<String, String>> current;// = ExcelFactory.setListData("results/SanityStatus.xlsx", "Shhet1");
        FileSystem system = FileSystems.getDefault();
        Path original = system.getPath(targetStr);
        String targetString = "results/StatusPriorityWeb";

        String targetStrOld;
        //Path target = system.getPath("results/listForExecution.xlsx");
        boolean toCopy = true;
        int i = 0;
        while (toCopy) {
            i++;
            targetStrOld = targetStr;
            targetStr = targetString.concat(String.valueOf(i)).concat(".xlsx");
            try {
                // Throws an exception if the original file is not found.
                Path target = system.getPath(targetStr);
                DataManager.userObject.put("StatusResultPath", targetStr);
                //Path targetOld = system.getPath(targetStrOld);
                //Files.copy(targetOld, target);
                Files.copy(original, target);
                current = ExcelFactory.setListData(targetStrOld, "Sheet1");
                for (HashMap<String, String> item : current) {
                    boolean isNotInNew = true;
                    for (HashMap<String, String> change : delta)
                        if (change.get("Case ID").equals(item.get("Case ID"))) {
                            isNotInNew = false;
                        }
                    if (isNotInNew) {
                        String caseId = item.get("Case ID");
                        String priority = item.get("Priority");
                        String result = item.get("Result");
                        String platform = item.get("Platform");
                        item.put("result", item.get("Result"));
                        ExcelFactory.addRowInExcel(targetStr, "Sheet1", caseId, priority, result, platform);
                    }
                }
                toCopy = false;
            } catch (IOException ex) {
                if (i > 8) {
                    //toCopy = false;
                    throw new Exception("Files already exists!");
                }
            }
        }
    }

    public static void copyFileToServer() {
        FileSystem system = FileSystems.getDefault();
        String source = DataManager.userObject.get("StatusResultPath").toString();
        String target = DataManager.serverLogFolder.concat("\\StatusPriorityWeb.xlsx");
        Path sourcePath = system.getPath(source);
        Path targetPath = system.getPath(target);
        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkIfPopupInterruptTesting() {
        try{
            String xPath = "//*[contains(@class,'error-modal-content')]";
            By el = By.xpath(xPath);
            boolean messageExists = ActionApiHelpers.isElementDisplayedCustom(el,1,1000);
            String xPath2 = "//*[contains(@class,'modal-content')]//button";

            if(messageExists == true){
                WebElement element = SelectByXpath.CreateElementByXpath(xPath2);
                ActionApiHelpers.ClickOnElement(element);
                for (int i = 0; i<=2;i++){
                    By el2 = By.xpath(xPath);
                    boolean messageExists2 = ActionApiHelpers.isElementDisplayedCustom(el2,1,1000);
                    if (messageExists2 == true){
                        WebElement element2 = SelectByXpath.CreateElementByXpath(xPath2);
                        ActionApiHelpers.ClickOnElement(element2);
                    }
                }
            } else{
                System.out.println("Greska se nije pojavila");
            }
        } catch (Exception e){
            System.out.print("Neka greska");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
