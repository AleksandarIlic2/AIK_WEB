package rs.aik.testautomation.AIKTestAutomation.Data;

import rs.aik.testautomation.AIKTestAutomation.Action.HTTPAction;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Test.Hooks;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataManager {
    //region - Static Variables
    public static String env;
    public static List<String> envList = new ArrayList<>();
    public static String testWebSite;
    public static ArrayList<HashMap<String, String>> datamap = null;
    public static ArrayList<HashMap<String, String>> configurationMap = null;
    public static ArrayList<HashMap<String, Object>> objectmap = null;
    public static HashMap<String,Object> userObject = new HashMap<>();
    public static Map<String,Object> userApiResponse = new HashMap<>();
    public static List<Map<String,Object>> userApiResponseList = new ArrayList<>();
    public static HashMap<String,Object> mapForProcedures = null;
    public static HashMap<String,Object> mapForOneProcedures = null;
    public static HashMap<String,Object> mapForProcedureUpdateVisible = null;
    public static String dbEngine;
    public static String dbConnectionString;
    public static String dtcShema;
    public static String dbUser;
    public static String dbPassword;

    public static String dbEngineCore;
    public static String dbConnectionStringCore;
    public static String dtcShemaCore;
    public static String dbUserCore;
    public static String dbPasswordCore;

    public static String dbEngineReport;
    public static String dbConnectionStringReport;
    public static String dtcShemaReport;
    public static String dbUserReport;
    public static String dbPasswordReport;

    public static String dbEngineLuca;
    public static String dbConnectionStringLuca;
    public static String dtcShemaLuca;
    public static String dbUserLuca;
    public static String dbPasswordLuca;

    public static String dbEngineRbalOne;
    public static String dbConnectionStringRbalOne;
    public static String dtcSchemaRbalOne;
    public static String dbUserRbalOne;
    public static String dbPasswordRbalOne;

    public static String envName;
    public static String webBrowser="";
    public static List<String> webBrowsersList = new ArrayList<>();
    public static String dbWriteResults;
    public static String iKes;
    public static String UserActiveApiURL;
    public static String UserDeatailsApiUrl;
    public static String webServiceBaseURL;
    public static String testrailBaseURL;
    public static String testrailUsername;
    public static String testrailPassword;
    public static String serverLogFolder;
    public static Map<String, Map<String, String>> dataForEnv = new HashMap<>();
    public static Map<String, ArrayList<String>> listForExecutionByEnv = new HashMap<>();
    public static List<HashMap<String, Object>> resultContainer = new ArrayList<>();
    public static ArrayList<String> environmentsInListForExecution = ExcelFactory.getEnvironmentsFromListFOrExecution("testdata/listForExecution.xlsx");

    public static int TestrailProjectId = 30;
    public static boolean logTestrail;

    //isDeviceVirtual

    public static String isDeviceVirtual;

    //emulator android
    public static String mobDeviceUdid = "emulator-5554";
    public static String mobDeviceDeviceName = "device";
    public static String mobDevicePlatformName = "Android";
    public static String mobDevicePlatformVersion = "11";
    public static String mobDeviceAVD = "Pixel_4_API_30_3";

    //huawei device
    public static String mobDeviceUdidPhysical = "APSBB22C22006168";
    public static String mobDeviceDeviceNamePhysical = "HUAWEI EVE-LX9N";
    public static String mobDevicePlatformVersionPhysical = "10";

    //public static String mobDeviceAPP = "C:\\Applications\\Apk\\app-slovenia-uat-release_3.3.89_20220901.apk";
    public static String mobDevicePackage;
    //public static String mobDevicePackage = DataManager.getDataFromConfiguration("1", "mobDevicePackage");
    //public static String mobDevicePackage = "co.infinum.nlb.uat";
    //public static String mobDevicePackage = "co.infinum.nlb.tst.playstore";
    public static String mobDeviceActivity = "com.backbase.model.view.activity.EnrollmentActivity";
    public static String mobDevicePIN = "1379";;


    static {
        try {
            ArrayList<HashMap<String, String>> sheet1 = ExcelFactory.getDataFromExcel("testdata/configuration.xlsx", "Sheet1");
            for (HashMap<String, String> envMap : sheet1) {
                 String envName = envMap.get("Environment");
                if(!envMap.get("Order").isEmpty()) {
                    envList.add(envName);
                    dataForEnv.put(envName, envMap);
                    listForExecutionByEnv.put(envName, ExcelFactory.GetListOfTestForExecution(
                            "testdata/listForExecution.xlsx", "TestName", envName));

                }
            }

            setDataForEnv(null);

        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    public static void setDataForEnv(String env) {
        Map<String, String> envData;
        if(env == null) {
            envData = dataForEnv.get(environmentsInListForExecution.get(0));
        } else {
            envData = dataForEnv.get(env);
        }
        String[] webBrowsersArray = envData.get("WebBrowser").split(",");
        webBrowsersList = Arrays.asList(webBrowsersArray);
        envName = envData.get("Environment");
        testWebSite = envData.get("WebSite");
        dbEngine  = envData.get("dbEngine");
        dbConnectionString  = envData.get("dbConnectionString");
        dbWriteResults = envData.get("dbWriteResults");
        iKes = envData.get("iKes");
        UserActiveApiURL =  envData.get("UserActiveApiURL");
        UserDeatailsApiUrl = envData.get("UserDeatailsApiUrl");
        mobDevicePackage = envData.get("mobDevicePackage");
        String s = envData.get("dtcShema");
        if((s != "") && (s != null))
            dtcShema = s + ".";
        else
            dtcShema = "";
        dbUser = envData.get("dbUser");
        dbPassword = envData.get("dbPassword");

        // ------------------------------------------------------------------------------------------ //
        // TODO this is disturbing we need to offer better config for databases in configuration exel //
        // ------------------------------------------------------------------------------------------ //

        // TODO we need to create better way of retrieving configuration from exel based on index
        //  so that we can have multiple connection string in the future if needed
        // TODO check also for this in the future may not need connection for core
        dbEngineCore  = envData.get("dbEngineCore");
        dbConnectionStringCore  = envData.get("dbConnectionStringCore");
        String s1 = envData.get("dtcShemaCore");
        if((s1 != "") && (s1 != null))
            dtcShemaCore = s1 + ".";
        else
            dtcShemaCore = "";
        dbUserCore = envData.get("dbUserCore");
        dbPasswordCore = envData.get("dbPasswordCore");

        // TODO we need to create better way of retrieving configuration from exel based on index
        //  so that we can have multiple connection string in the future if needed
        // TODO check also for this in the future may not need connection for core
        dbEngineReport  = envData.get("dbEngineReport");
        dbConnectionStringReport  = envData.get("dbConnectionStringReport");
        String s_rep = envData.get("dtcShemaReport");
        if((s_rep != "") && (s_rep != null))
            dtcShemaReport = s_rep + ".";
        else
            dtcShemaReport = "";
        dbUserReport = envData.get("dbUserReport");
        dbPasswordReport = envData.get("dbPasswordReport");

        // TODO check for this in the future may not need connection for luka
        dbEngineLuca  = envData.get("dbEngineLuca");
        dbConnectionStringLuca  = envData.get("dbConnectionStringLuca");
        String s2 = envData.get("dtcShemaLuca");
        if((s2 != "") && (s2 != null))
            dtcShemaLuca = s2 + ".";
        else
            dtcShemaLuca = "";
        dbUserLuca = envData.get("dbUserLuca");
        dbPasswordLuca = envData.get("dbPasswordLuca");

        dbEngineRbalOne = envData.get("dbEngineRbalOne");
        dbConnectionStringRbalOne = envData.get("dbConnectionStringRbalOne");
        String rbalOneSchema = envData.get("dtcSchemaRbalOne");

        if (rbalOneSchema != "" && rbalOneSchema != null) {
            dtcSchemaRbalOne = rbalOneSchema + ".";
        }else {
            dtcSchemaRbalOne = "";
        }
        dbUserRbalOne = envData.get("dbUserRbalOne");
        dbPasswordRbalOne = envData.get("dbPasswordRbalOne");

        webServiceBaseURL = envData.get("webServiceBaseURL");
        testrailBaseURL= envData.get("testrailBaseURL");
        testrailUsername= envData.get("testrailUsername");
        testrailPassword= envData.get("testrailPassword");
        serverLogFolder= envData.get("serverLogFolder");

        listForExecution = listForExecutionByEnv.get(envData.get("Environment"));
        Hooks.resourceId = envData.get("resource_id");

        //region - Get configuration for mobile

        //android emulator
        mobDeviceUdid = envData.get("mobDeviceUdid");
        mobDeviceDeviceName = envData.get("mobDeviceDeviceName");//"device";
        mobDevicePlatformName = envData.get("mobDevicePlatformName");//"Android";
        mobDevicePlatformVersion = envData.get("mobDevicePlatformVersion");//"11";
        mobDeviceAVD = envData.get("mobDeviceAVD");//"PixelXL";
        //mobDeviceAPP = envData.get("mobDeviceAPP");//"C:\\Applications\\Apk\\app-slovenia-uat-release_3.3.89_20220901.apk";
        mobDevicePIN = envData.get("mobDevicePIN");//"1379";;

        //huawei

        mobDeviceUdidPhysical = envData.get("mobDeviceUdidPhysical");
        mobDeviceDeviceNamePhysical = envData.get("mobDeviceDeviceNamePhysical");
        mobDevicePlatformVersionPhysical = envData.get("mobDevicePlatformVersionPhysical");

        //is device virtual

         isDeviceVirtual = envData.get("isDeviceVirtual");

        //endregion - Get configuration for mobile

    }
    public static ArrayList<String> listForExecution = ExcelFactory.GetListOfTestForExecution("testdata/listForExecution.xlsx","TestName","dev");

    //endregion - Static Variables -

    //region - Misc -
    /**
     * Method for finding Computer name
     * @return String
     */
    public static String getHostname()
    {
        String result = "";
        String OS = System.getProperty("os.name").toLowerCase();
        if(OS.contains("win")){
            result = System.getenv("COMPUTERNAME");
        }
        else if(OS.contains("mac")){
            //SCDynamicStore store = new SCDynamicStore();
            //result = store.copyComputerName();
            result ="MacBook Pro";

        }
        else if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix")){
            try {
                result = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                Log.error(e.getMessage());
            }

        }else{
            try {
                result = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                Log.error(e.getMessage());
            }
        }
        return result;
    }

    public static String getDataFromHashDatamap(String arg1, String columnName) {
        int index = Integer.parseInt(arg1) - 1;
        String myData = datamap.get(index).get(columnName);
        return myData;
    }
    public static String getDataFromConfiguration(String arg1, String columnName) {
        int index = Integer.parseInt(arg1) - 1;
        String myData = configurationMap.get(index).get(columnName);
        return myData;
    }
    //endregion - Misc -

    //region - DB tools only for MSSQL
    /**
     Method for execution sql scripts on MSSQL database
     * SQLText - SQL script for execution
     * Parameters - array of values for filling parameters in SQL script
     * Returns ResultSet - expecting single result set
     */
    public static ResultSet MSSQLQuery(String SQLText, String[] parameters) throws SQLException {
        ResultSet resultSet = null;
        String connectionString = dbConnectionString;
        Connection connection = DriverManager.getConnection(connectionString);
        DBFactory sqlServer =  DBFactory.getDBFactory(DatabaseType.MSSQL);
        resultSet = sqlServer.getResultSet(SQLText, parameters, connection);
        return resultSet;
    }

    /**
     Method for execution sql scripts on MSSQL database
     * SQLText - SQL script for execution
     * Parameters - list of values for filling parameters in SQL script
     * Returns ResultSet - expecting single result set
     */
    public static ResultSet MSSQLQuery(String SQLText, Object... parameters) throws SQLException {
        ResultSet resultSet = null;
        String[] list = new String[parameters.length];
        for(int i = 0; i<parameters.length; i++) {
            list[i] = parameters[i].toString();
        }
        resultSet = MSSQLQuery(SQLText, list);
        return resultSet;
    }

    /**
     Method for execution sql scripts on Oracle database
     * SQLText - SQL script for execution
     * Parameters - array of values for filling parameters in SQL script
     * Returns ResultSet - expecting single result set
     */
    public static ResultSet OracleQuery(String SQLText, String[] parameters) throws SQLException {
        ResultSet resultSet = null;
        String connectionString = dbConnectionString;
        String un = dbUser;
        String pass = dbPassword;

        Connection connection = DriverManager.getConnection(connectionString, un, pass );
        // Create prepared statement
        PreparedStatement statement = connection.prepareStatement(SQLText);
        int n = parameters.length;
        for(int i=1; i<= n; i++)
        {
            // Set all parameters
            statement.setString(i, parameters[i-1]);
        }
        // Create and execute a SQL statement.
        resultSet = statement.executeQuery();
        return resultSet;
    }

    /**
     * Method for execution SQL script
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @return Single result set
     * @throws SQLException
     */
    public static ResultSet GetDataMSSQL(String SQL, String[] parameters) throws SQLException {
        return DataManager.MSSQLQuery(SQL, parameters);
    }

    /**
     * Overloaded method. Without list of parameters
     * @param - SQL text for execution
     * @return Single result set
     * @throws SQLException
     */
    public static ResultSet GetDataMSSQL(String SQL) throws SQLException {
        String[] emptylist = {};
        return DataManager.MSSQLQuery(SQL, emptylist);
    }

    /**
     * Overloaded method. Without list of parameters
     * @param - SQL text for execution
     * @param parameters - list of parameters from SQL text. Parameters should be passed to query
     * @return Single result set
     * @throws SQLException
     */
    public static ResultSet GetDataMSSQL(String SQL, Object... parameters) throws SQLException {
        String[] list = new String[parameters.length];
        for(int i = 0; i<parameters.length; i++) {
            list[i] = parameters[i].toString();
        }
        return DataManager.MSSQLQuery(SQL, list);
    }

    /**
     * Method for execution SQL scripts with parameters
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @return Single object from DB
     * @throws SQLException
     */
    public static Object getScalar(String SQL, String[] parameters) throws SQLException {
        Object data = null;
        String connectionString = dbConnectionString;
        Connection connection = DriverManager.getConnection(connectionString);
        DBFactory sqlServer =  DBFactory.getDBFactory(DatabaseType.MSSQL);
        data = sqlServer.getScalar(SQL, parameters, connection);
        return data;
    }

    /**
     * Method for execution SQL scripts with parameters as list of parameters
     * @param SQL - SQL text for execution
     * @param parameters - List of parameters from SQL text
     * @return Single object from DB
     * @throws SQLException
     */
    public static Object getScalar(String SQL, Object... parameters) throws SQLException {
        Object data = null;
        String[] list = new String[parameters.length];
        for(int i = 0; i<parameters.length; i++) {
            list[i] = parameters[i].toString();
        }
        data = getScalar(SQL, list);
        return data;
    }

    /**
     * Method for execution SQL scripts without parameters
     * @param SQL - SQL text for execution
     * @return Single object from DB
     * @throws SQLException
     */
    public static Object getScalar(String SQL) throws SQLException {
        Object data = null;
        String[] emptylist = {};
        data = getScalar(SQL, emptylist);
        return data;
    }

    public static ResultSet GetDataOracle(String SQL, String[] parameters) throws SQLException {
        return DataManager.OracleQuery(SQL, parameters);
    }

    public static ResultSet GetDataOracle(String SQL) throws SQLException {
        String[] emptylist = {};
        return DataManager.OracleQuery(SQL, emptylist);
    }


    /**
     * Static method returns single int from DB
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @return Single int from DB
     * @throws SQLException
     */
    public static int GetIntMSSQL(String SQL, String[] parameters) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        int result = Integer.parseInt(rs.toString());
        return result;
    }

    /**
     * Static method returns single int from DB without parameters
     * @param SQL - SQL text for execution
     * @return Single int from DB
     * @throws SQLException
     */
    public static int GetIntMSSQL(String SQL) throws SQLException {
        String[] emptylist = {};
        int result = GetIntMSSQL(SQL, emptylist);
        return result;
    }

    /**
     * Overloaded method returns single int from DB
     * @param SQL - SQL text for execution
     * @param parameters - List of parameters from SQL text. Parameters should be passed to query
     * @return Single int from DB
     * @throws SQLException
     */
    public static int GetIntMSSQL(String SQL, Object... parameters) throws SQLException {
        String[] list = new String[parameters.length];
        for(int i = 0; i<parameters.length; i++) {
            list[i] = parameters[i].toString();
        }
        Object rs = GetIntMSSQL(SQL, list);
        int result = Integer.parseInt(rs.toString());
        return result;
    }


    /**
     * Static method returns single String from DB with parameters
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @return Single int from DB
     * @throws SQLException
     */
    public static String GetStringMSSQL(String SQL, String[] parameters) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        String result = rs.toString();
        return result;
    }

    /**
     * Static method returns single String from DB with parameters
     * @param SQL - SQL text for execution
     * @param parameters - List of parameters from SQL text
     * @return Single int from DB
     * @throws SQLException
     */
    public static String GetStringMSSQL(String SQL, Object... parameters) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        String result = rs.toString();
        return result;
    }

    /**
     * Static method returns single String from DB without parameters
     * @return Single String from DB
     * @throws SQLException
     */
    public static String GetStringMSSQL(String SQL) throws SQLException {
        String[] emptylist = {};
        String result = GetStringMSSQL(SQL, emptylist);
        return result;
    }



    /**
     * Static method returns single double from DB with parameters
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @return Single double from DB
     * @throws SQLException
     */
    public static double GetDoubleMSSQL(String SQL, String[] parameters) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        double result = Double.parseDouble(rs.toString());
        return result;
    }

    /**
     * Static method returns single double from DB without parameters
     * @return Single double from DB
     * @throws SQLException
     */
    public static double GetDoubleMSSQL(String SQL) throws SQLException {
        String[] emptylist = {};
        double result = GetDoubleMSSQL(SQL, emptylist);
        return result;
    }

    /**
     * Overloaded method returns single double from DB with parameters
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @return Single double from DB
     * @throws SQLException
     */
    public static double GetDoubleMSSQL(String SQL, Object... parameters) throws SQLException {
        String[] list = new String[parameters.length];
        for(int i = 0; i<parameters.length; i++) {
            list[i] = parameters[i].toString();
        }
        Object rs = GetDoubleMSSQL(SQL, list);
        double result = Double.parseDouble(rs.toString());
        return result;
    }


    /**
     * Static method returns single Date from DB with parameters
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @param pattern - Pattern of date data from which expects from database
     * @return Single Date from DB
     * @throws SQLException
     */
    public static LocalDateTime GetDateMSSQL(String SQL, String[] parameters, String pattern) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        if(pattern != "yyyy-MM-dd HH:mm:ss.SSS" )
            pattern = pattern;
        DateTimeFormatter patternObj = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime result = LocalDateTime.parse(rs.toString(), patternObj);
        return result;
    }

    /**
     * Static method returns single Date from DB without parameters
     * @param SQL - SQL text for execution
     * @param pattern - Pattern of date data from which expects from database
     * @return Date float from DB
     * @throws SQLException
     */
    public static LocalDateTime GetDateMSSQL(String SQL, String pattern) throws SQLException {
        String[] emptylist = {};
        LocalDateTime result = GetDateMSSQL(SQL, emptylist, pattern);
        return result;
    }

    /**
     * Overloaded method returns single Date from DB with parameters
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @param pattern - Pattern of date data from which expects from database
     * @return Single Date from DB
     * @throws SQLException
     */
    public static LocalDateTime GetDateMSSQL(String SQL, String pattern, Object... parameters) throws SQLException {
        String[] list = new String[parameters.length];
        for(int i = 0; i<parameters.length; i++) {
            list[i] = parameters[i].toString();
        }
        LocalDateTime result = GetDateMSSQL(SQL, list, pattern);
        return result;
    }

    //endregion

    //region - Write to log -
    /**
     * Method for entry log into database
     * @param App - Application name
     * @param Test_Name - Test name
     * @param Result - Result SUCCESS or FAIL
     * @param Logdate - Date time of log
     * @param Environment - On wich environment is tested
     * @param Iteration - Number of iteration
     * @param HostName - Computer name
     * @param Browser - Tested with browser
     * @param TestCaseId - Test Case ID
     * @param HostName - Computer name
     * @param PlatformName - Platform (Web, Android, iOS) in this case always Web
     * @throws SQLException
     */
    public static void WriteToTestResult(String App, String Test_Name, String Result, String Logdate, String Environment,
                                         String Iteration, String HostName, String Browser, String TestCaseId, String PlatformName,
                                         String Description) throws SQLException {
        //String _prefix = dtcShema;
        String _prefix = "";
        String SQLText = "INSERT INTO " + _prefix + "TestResult (App, Test_Name, Result, LogDateTime, Environment, Iteration, HostName, Browser, TestCaseId, PlatformName, Description)"
                + "VALUES (?, ?, ?, cast(? AS DATETIME), ?, ?, ?, ?, ?, ?, ?) ";
        boolean rs = DBReport.dbExecuteSQL(SQLText, App, Test_Name, Result, Logdate, Environment, Iteration, HostName, Browser, TestCaseId, PlatformName, Description);
    }

    /**
     * Method for log with same parameters as Excel
     * @param testName - Test name
     * @param state - Success or faile
     * @param description - Description
     * @throws SQLException
     */
    public static void WriteTestResult(String testName, String state, String description) throws SQLException {
        String App = testWebSite;
        if(App == null) App = "";
        String testCaseId = "";
        try{
            testCaseId = testName.split("[\\[\\]]")[1];
        }
        catch (Exception ex){}
        WriteToTestResult(
                App,
                testName,
                state,
                DateHHMMSS(),
                envName,
                "",
                getHostname(),
                webBrowser,
                testCaseId,
                "Web",
                description
        );
    }
    public static String DateHHMMSS() {
        SimpleDateFormat outputFormatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS");
        return outputFormatter.format(new Date());
    }
    //endregion - Write to log -

    //region - call testrail -
    public static void tryCallTestrail(){
        if(logTestrail){
            try {
                callTestrail();
            }
            catch (Exception e){}
        }
    }

    public static void callTestrail() throws SQLException {
        List<String> l = new ArrayList<String>();
        for(HashMap<String, Object> item : DataManager.resultContainer){
            String Fid = item.get("id").toString();
            String[] parts = Fid.split(",");
            for(String part : parts){
                String trimPart = part.trim();
                if (!(l.contains(trimPart)) && (!(l.equals("")))){
                    l.add(trimPart);
                }
            }

        }
        int n = 0;
        for(String s : l){
            try {
                String c = s.substring(1);
                int newid = Integer.valueOf(c);
                if (newid > 0)
                    n++;
            }
            catch (Exception e){}
        }
        //int[] listf = new int[l.size()];
        int[] listf = new int[n];
        int i = 0;
        for(String s : l){
            try {
                String c = s.substring(1);
                int newid = Integer.valueOf(c);
                listf[i++] = newid;
            }
            catch (Exception e){}

        }
        //HTTPAction.addRun(TestrailProjectId, listf);

        HTTPAction.addResultsForCasesByOne(listf);

        //HTTPAction.addAttachment();
    }


    //endregion - call testrail -

}
