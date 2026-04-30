package rs.aik.testautomation.AIKTestAutomation.Data;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for communication with core DB
 */
public class DBCore {
    //region - static connection to database
    private static String dbEngine = DataManager.dbEngineCore;
    private static String dbConnectionString = DataManager.dbConnectionStringCore;
    private static String dbUser = DataManager.dbUserCore;
    private static String dbPassword = DataManager.dbPasswordCore;
    //endregion - static connection to database

    //region - DB tool for all databases default connection -

    //region - dbQuery -
    /**
     * Overloaded method for execution sql scripts on default database from config
     * SQLText - SQL script for execution
     * Parameters - List of values for filling parameters in SQL script
     * Returns ResultSet - expecting single result set
     */
    public static ResultSet dbQuery(String SQLText, Object... parameters) throws SQLException {
        ResultSet resultSet;

        String connectionString = dbConnectionString;

        Connection connection;
        DBFactory dbServer;
        switch (dbEngine){
            case "MSSQL":
                dbServer = DBFactory.getDBFactory(DatabaseType.MSSQL);
                connection = DriverManager.getConnection(connectionString);
                resultSet = dbServer.getResultSet(SQLText, connection, parameters);
                break;
            case "ORACLE":
                dbServer= DBFactory.getDBFactory(DatabaseType.ORACLE);
                connection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
                resultSet = dbServer.getResultSet(SQLText, connection, parameters);
                break;
            case "POSTGRESQL":
                dbServer= DBFactory.getDBFactory(DatabaseType.POSTGRESQL);
                connection = DriverManager.getConnection(connectionString);
                resultSet = dbServer.getResultSet(SQLText, connection, parameters);
                break;
            case "MYSQL":
                dbServer= DBFactory.getDBFactory(DatabaseType.MYSQL);
                connection = DriverManager.getConnection(connectionString);
                resultSet = dbServer.getResultSet(SQLText, connection, parameters);
                break;
            default: resultSet = null;
        }
        return resultSet;
    }

    /**
     Method for execution sql scripts on default database from config without parameters
     * SQLText - SQL script for execution
     * Returns ResultSet - expecting single result set
     */
    public static ResultSet dbQuery(String SQLText) throws SQLException {
        ResultSet resultSet;
        Object[] empty = {};
        resultSet = dbQuery(SQLText, empty);
        return resultSet;
    }
    //endregion - dbQuery

    //region - execute without result -
    /**
     * Overloaded method for execution sql scripts on default database from config
     * SQLText - SQL script for execution
     * Parameters - List of values for filling parameters in SQL script
     * Returns ResultSet - expecting single result set
     */
    public static boolean dbExecuteSQL(String SQLText, Object... parameters) throws SQLException {
        boolean result;
        Connection connection;
        DBFactory dbServer;
        switch (dbEngine){
            case "MSSQL":
                dbServer = DBFactory.getDBFactory(DatabaseType.MSSQL);
                connection = DriverManager.getConnection(dbConnectionString);
                result = dbServer.executeSQL(SQLText, connection, parameters);
                break;
            case "ORACLE":
                dbServer= DBFactory.getDBFactory(DatabaseType.ORACLE);
                connection = DriverManager.getConnection(dbConnectionString, dbUser, dbPassword);
                result = dbServer.executeSQL(SQLText, connection, parameters);
                break;
            case "POSTGRESQL":
                dbServer= DBFactory.getDBFactory(DatabaseType.POSTGRESQL);
                connection = DriverManager.getConnection(dbConnectionString);
                result = dbServer.executeSQL(SQLText, connection, parameters);
                break;
            case "MYSQL":
                dbServer= DBFactory.getDBFactory(DatabaseType.MYSQL);
                connection = DriverManager.getConnection(dbConnectionString);
                result = dbServer.executeSQL(SQLText, connection, parameters);
                break;
            default: result = false;
        }
        return result;
    }
    //endregion - execute without result -

    //region
    /**
     * Method for execution SQL scripts with parameters from Default DB from config
     * @param SQL - SQL text for execution
     * @param parameters - array of parameters from SQL text. Parameters should be passed to query
     * @return Single object from DB
     * @throws SQLException
     */

    public static Object getScalar(String SQL, Object... parameters) throws SQLException {
        Object data;
        DBFactory dbServer;
        Connection connection = null;
        switch (dbEngine){
            case "MSSQL":
                dbServer = DBFactory.getDBFactory(DatabaseType.MSSQL);
                connection = DriverManager.getConnection(dbConnectionString);
                break;
            case "ORACLE":
                dbServer = DBFactory.getDBFactory(DatabaseType.ORACLE);
                connection = DriverManager.getConnection(dbConnectionString, dbUser, dbPassword);
                break;
            case "POSTGRESQL":
                dbServer = DBFactory.getDBFactory(DatabaseType.POSTGRESQL);
                connection = DriverManager.getConnection(dbConnectionString);
                break;
            case "MYSQL":
                dbServer = DBFactory.getDBFactory(DatabaseType.MYSQL);
                connection = DriverManager.getConnection(dbConnectionString);
                break;
            default: dbServer = null;
        }
        data = dbServer.getScalar(SQL, connection, parameters);
        return data;
    }

    //region - get Integer scalar -
    /**
     * Overloaded method returns single int from Default DB from config
     * @param SQL - SQL text for execution
     * @param parameters - List of parameters from SQL text. Parameters should be passed to query
     * @return Single int from DB
     * @throws SQLException
     */
    public static int GetInt(String SQL, Object... parameters) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        return Integer.parseInt(rs.toString());
    }

    /**
     * Static method returns single int from Default DB from config without parameters
     * @param SQL - SQL text for execution
     * @return Single int from DB
     * @throws SQLException
     */
    public static int GetInt(String SQL) throws SQLException {
        Object[] empty = {};
        int result = GetInt(SQL, empty);
        return result;
    }
    //endregion - get int scalar -

    //region - get String scalar -
    /**
     * Overloaded method returns single String from Default DB from config
     * @param SQL - SQL text for execution
     * @param parameters - List of parameters from SQL text. Parameters should be passed to query
     * @return Single String from DB
     * @throws SQLException
     */
    public static String GetString(String SQL, Object... parameters) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        return rs.toString();
    }

    /**
     * Static method returns single String from Default DB from config without parameters
     * @return Single String from DB
     * @throws SQLException
     */
    public static String GetString(String SQL) throws SQLException {
        Object[] empty = {};
        String result = GetString(SQL, empty);
        return result;
    }
    //endregion - get String scalar -

    //region - get Double scalar -
    /**
     * Overloaded method returns single double from Default DB from config
     * @param SQL - SQL text for execution
     * @param parameters - List of parameters from SQL text. Parameters should be passed to query
     * @return Single double from DB
     * @throws SQLException
     */
    public static double GetDouble(String SQL, Object... parameters) throws SQLException {
        Object rs = GetInt(SQL, parameters);
        return Double.parseDouble(rs.toString());
    }
    /**
     * Static method returns single double from Default DB from config without parameters
     * @param SQL - SQL text for execution
     * @return Single double from DB
     * @throws SQLException
     */
    public static double GetDouble(String SQL) throws SQLException {
        Object[] empty = {};
        double result = GetDouble(SQL, empty);
        return result;
    }
    //endregion - get Double scalar -

    //region - get Date scalar -
    /**
     * Overloaded method returns single Date from Default DB from config
     * @param SQL - SQL text for execution
     * @param pattern - Pattern of date data from which expects from database
     * @param parameters - List of parameters from SQL text. Parameters should be passed to query
     * @return Single Date from DB
     * @throws SQLException
     */
    public static LocalDateTime GetDate(String SQL, String pattern, Object... parameters) throws SQLException {
        Object rs = getScalar(SQL, parameters);
        DateTimeFormatter patternObj = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime result = LocalDateTime.parse(rs.toString(),patternObj);
        return result;
    }

    /**
     * Static method returns single Date from Default DB from config without parameters
     * @param SQL - SQL text for execution
     * @param pattern - Pattern of date data from which expects from database
     * @return Date float from DB
     * @throws SQLException
     */
    public static LocalDateTime GetDate(String SQL, String pattern) throws SQLException {
        Object[] empty = {};
        LocalDateTime result = GetDate(SQL, pattern, empty);
        return result;
    }
    //endregion - get Date scalar -

    //region - Result as HashMap -
    /**
     * Method for getting Hashmap from Default DB
     * @param SQLText - Query text to get data from database
     * @return List of pairs (String, String)
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, String>> getDataSingle(String SQLText) throws SQLException {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        ResultSet rs = dbQuery(SQLText);
        ResultSetMetaData md = rs.getMetaData();

        while(rs.next()){
            HashMap<String, String> currentHash = new HashMap<String, String>();
            for(int j = 1; j < md.getColumnCount(); j++){
                String cell =  rs.getString(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result.add(currentHash);
        }
        return result;
    }

    /**
     * Method for getting Hashmap from Default DB with parameters
     * @param SQLText - Query text to get data from database
     * @param parameters - List of parameters for query
     * @return List of pairs (String, String)
     * @throws SQLException
     */
    public static HashMap<String, String> getDataSingle(String SQLText, Object... parameters) throws SQLException {
        HashMap<String, String> result = new HashMap<String, String>();
        ResultSet rs = dbQuery(SQLText, parameters);
        ResultSetMetaData md = rs.getMetaData();

        if(rs.next()){
            HashMap<String, String> currentHash = new HashMap<String, String>();
            for(int j = 1; j < md.getColumnCount(); j++){
                String cell =  rs.getString(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result = currentHash;
        }
        return result;
    }

    /**
     * Method for getting Hashmap from Default DB
     * @param SQLText- Query text to get data from database
     * @return ArrayList<HashMap<String, Object>>
     * @throws SQLException
     */
    public static HashMap<String, Object> getObjectSingle(String SQLText) throws SQLException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ResultSet rs = dbQuery(SQLText);
        ResultSetMetaData md = rs.getMetaData();
        if(rs.next()){
            HashMap<String, Object> currentHash = new HashMap<String, Object>();
            for(int j = 1; j < md.getColumnCount(); j++){
                Object cell =  rs.getObject(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result = currentHash;
        }
        return result;
    }

    /**
     * Method for getting Hashmap from Default DB with parameters
     * @param SQLText - Query text to get data from database
     * @param parameters - List of parameters for query
     * @return List of pairs (String, Object)
     * @throws SQLException
     */
    public static HashMap<String, Object> getObjectSingle(String SQLText, Object... parameters) throws SQLException {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ResultSet rs = dbQuery(SQLText, parameters);
        ResultSetMetaData md = rs.getMetaData();

        if(rs.next()){
            HashMap<String, Object> currentHash = new HashMap<String, Object>();
            for(int j = 1; j < md.getColumnCount(); j++){
                String cell =  rs.getString(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result = currentHash;
        }
        return result;
    }
    //endregion - HashMap

    //region - Result as Array of HashMap -
    /**
     * Method for getting list of Hashmap from Default DB
     * @param SQLText - Query text to get data from database
     * @return List of pairs (String, String)
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, String>> getData(String SQLText) throws SQLException {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        ResultSet rs = dbQuery(SQLText);
        ResultSetMetaData md = rs.getMetaData();

        while(rs.next()){
            HashMap<String, String> currentHash = new HashMap<String, String>();
            for(int j = 1; j < md.getColumnCount(); j++){
                String cell =  rs.getString(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result.add(currentHash);
        }
        return result;
    }

    /**
     * Method for getting list of Hashmap from Default DB with parameters
     * @param SQLText - Query text to get data from database
     * @param parameters - List of parameters for query
     * @return List of pairs (String, String)
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, String>> getData(String SQLText, Object... parameters) throws SQLException {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        ResultSet rs = dbQuery(SQLText, parameters);
        ResultSetMetaData md = rs.getMetaData();

        while(rs.next()){
            HashMap<String, String> currentHash = new HashMap<String, String>();
            for(int j = 1; j < md.getColumnCount(); j++){
                String cell =  rs.getString(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result.add(currentHash);
        }
        return result;
    }

    /**
     * Method for getting list of Hashmap from Default DB
     * @param SQLText- Query text to get data from database
     * @return ArrayList<HashMap<String, Object>>
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> getObject(String SQLText) throws SQLException {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        ResultSet rs = dbQuery(SQLText);
        ResultSetMetaData md = rs.getMetaData();
        while(rs.next()){
            HashMap<String, Object> currentHash = new HashMap<String, Object>();
            for(int j = 1; j < md.getColumnCount(); j++){
                Object cell =  rs.getObject(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result.add(currentHash);
        }
        return result;
    }

    /**
     * Method for getting list of Hashmap from Default DB with parameters
     * @param SQLText - Query text to get data from database
     * @param parameters - List of parameters for query
     * @return List of pairs (String, Object)
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> getObject(String SQLText, Object... parameters) throws SQLException {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        ResultSet rs = dbQuery(SQLText, parameters);
        ResultSetMetaData md = rs.getMetaData();

        while(rs.next()){
            HashMap<String, Object> currentHash = new HashMap<String, Object>();
            for(int j = 1; j < md.getColumnCount(); j++){
                String cell =  rs.getString(j);
                currentHash.put(md.getColumnName(j), cell);
            }
            result.add(currentHash);
        }
        return result;
    }
    //endregion - Array of HashMap
    //endregion DB tool for all databases
}
