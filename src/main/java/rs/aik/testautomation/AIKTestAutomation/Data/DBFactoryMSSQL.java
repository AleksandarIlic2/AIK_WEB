package rs.aik.testautomation.AIKTestAutomation.Data;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;

public class DBFactoryMSSQL extends DBFactory{
    //private static ConfigurationManager config = null;
    private static String connectionString = "";
    private final int timeOut = 30;


    @Override
    public Connection createConnection() {
        connectionString = DataManager.dbConnectionString;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException ex){
            // Log exception here
            Log.error(ex.getMessage());
        }
        return conn;
    }

    @Override
    public Object getScalar(String SQLText, String[] parameters, Connection conn) {
        Object data = null;
        PreparedStatement statement;
        try{
            if(conn.isValid(timeOut)) {
                statement = conn.prepareStatement(SQLText);
                for(int i=1; i<= parameters.length; i++)
                {
                    statement.setString(i, parameters[i-1]);
                }
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                data = resultSet.getObject(1);
            }
        }
        catch(SQLException ex) {
            Log.error(ex.getMessage());
        }
        return data;
    }

    @Override
    public Object getScalar(String SQLText, Connection conn, Object... parameters) {
        Object data = null;
        PreparedStatement statement;
        try{
            if(conn.isValid(timeOut)) {
                statement = conn.prepareStatement(SQLText);
                for(int i=1; i<= parameters.length; i++)
                {
                    if(parameters[i-1] instanceof Integer)
                        statement.setInt(i, (Integer)parameters[i-1]);
                    else if (parameters[i-1] instanceof Double)
                        statement.setDouble(i, (Double)parameters[i-1]);
                    else if (parameters[i-1] instanceof Float)
                        statement.setFloat(i, (Float)parameters[i-1]);
                    else if (parameters[i-1] instanceof Date)
                        statement.setDate(i, (Date)parameters[i-1]);
                    else if (parameters[i-1] instanceof Time)
                        statement.setTime(i, (Time)parameters[i-1]);
                    else if (parameters[i-1] instanceof Boolean)
                        statement.setBoolean(i, (Boolean)parameters[i]);
                    else if (parameters[i-1] instanceof URL)
                        statement.setURL(i, (URL)parameters[i-1]);
                    else if (parameters[i-1] instanceof Short)
                        statement.setShort(i, (Short)parameters[i-1]);
                    else if (parameters[i-1] instanceof Long)
                        statement.setLong(i, (Long)parameters[i-1]);
                    else if (parameters[i-1] instanceof SQLXML)
                        statement.setSQLXML(i, (SQLXML)parameters[i-1]);
                    else if (parameters[i-1] instanceof String)
                        statement.setString(i, parameters[i-1].toString());
                    else
                        statement.setString(i, parameters[i-1].toString());
                }
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                data = resultSet.getObject(1);
            }
        }
        catch(SQLException ex) {
            Log.error(ex.getMessage());
        }
        return data;
    }

    @Override
    public ResultSet getResultSet(String SQLText, String[] parameters, Connection conn) {
        ResultSet data = null;
        PreparedStatement statement = null;
        try{
            if(conn.isValid(timeOut)) {
                statement = conn.prepareStatement(SQLText);
                for(int i=1; i<= parameters.length; i++)
                {
                    statement.setString(i, parameters[i-1]);
                }
                data = statement.executeQuery();
            }
        }
        catch(SQLException ex) {
            Log.error(ex.getMessage());
        }
        return data;
    }

    @Override
    public ResultSet getResultSet(String SQLText, Connection conn, Object... parameters) {
        ResultSet data = null;
        PreparedStatement statement = null;
        try{
            if(conn.isValid(timeOut)) {
                statement = conn.prepareStatement(SQLText);
                for(int i=1; i<= parameters.length; i++)
                {
                    if(parameters[i-1] instanceof Integer)
                        statement.setInt(i, (Integer)parameters[i-1]);
                    else if (parameters[i-1] instanceof Double)
                        statement.setDouble(i, (Double)parameters[i-1]);
                    else if (parameters[i-1] instanceof Float)
                        statement.setFloat(i, (Float)parameters[i-1]);
                    else if (parameters[i-1] instanceof Date)
                        statement.setDate(i, (Date)parameters[i-1]);
                    else if (parameters[i-1] instanceof Time)
                        statement.setTime(i, (Time)parameters[i-1]);
                    else if (parameters[i-1] instanceof Boolean)
                        statement.setBoolean(i, (Boolean)parameters[i]);
                    else if (parameters[i-1] instanceof URL)
                        statement.setURL(i, (URL)parameters[i-1]);
                    else if (parameters[i-1] instanceof Short)
                        statement.setShort(i, (Short)parameters[i-1]);
                    else if (parameters[i-1] instanceof Long)
                        statement.setLong(i, (Long)parameters[i-1]);
                    else if (parameters[i-1] instanceof SQLXML)
                        statement.setSQLXML(i, (SQLXML)parameters[i-1]);
                    else if (parameters[i-1] instanceof String)
                        statement.setString(i, parameters[i-1].toString());
                    else
                        statement.setString(i, parameters[i-1].toString());
                }
                data = statement.executeQuery();

            }
        }
        catch(SQLException ex) {
            Log.error(ex.getMessage());
        }
        return data;
    }

    /**
     * Method for execution SQL with parameters
     * @param SQLText - SQL query for execution
     * @param conn - Connection to Database
     * @param parameters - List of parameters for query
     * @return
     */
    @Override
    public boolean executeSQL(String SQLText, Connection conn, Object... parameters) {
        boolean data = false;
        PreparedStatement statement = null;
        try{
            if(conn.isValid(timeOut)) {
                statement = conn.prepareStatement(SQLText);
                for(int i=1; i<= parameters.length; i++)
                {
                    if(parameters[i-1] instanceof Integer)
                        statement.setInt(i, (Integer)parameters[i-1]);
                    else if (parameters[i-1] instanceof Double)
                        statement.setDouble(i, (Double)parameters[i-1]);
                    else if (parameters[i-1] instanceof Float)
                        statement.setFloat(i, (Float)parameters[i-1]);
                    else if (parameters[i-1] instanceof Date)
                        statement.setDate(i, (Date)parameters[i-1]);
                    else if (parameters[i-1] instanceof Time)
                        statement.setTime(i, (Time)parameters[i-1]);
                    else if (parameters[i-1] instanceof Boolean)
                        statement.setBoolean(i, (Boolean)parameters[i]);
                    else if (parameters[i-1] instanceof URL)
                        statement.setURL(i, (URL)parameters[i-1]);
                    else if (parameters[i-1] instanceof Short)
                        statement.setShort(i, (Short)parameters[i-1]);
                    else if (parameters[i-1] instanceof Long)
                        statement.setLong(i, (Long)parameters[i-1]);
                    else if (parameters[i-1] instanceof SQLXML)
                        statement.setSQLXML(i, (SQLXML)parameters[i-1]);
                    else if (parameters[i-1] instanceof String)
                        statement.setString(i, parameters[i-1].toString());
                    else
                        statement.setString(i, parameters[i-1].toString());

                }
                data = statement.execute();
            }
        }
        catch(SQLException ex) {
            Log.error(ex.getMessage());
        }
        return data;
    }

    /**
     * Method to check if MSSQL database response
     * @param conn Connection to database
     * @return
     */
    @Override
    public Boolean pingDB(Connection conn) {
        Boolean ping = false;
        Statement statement = null;
        try{
            if(conn.isValid(timeOut)){
                statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT @@VERSION");
                ping = true;
                rs.close();
                Log.info("Connection to [" + connectionString + "] is succesful!"); // log this
            }
        }
        catch(SQLException ex) {
            Log.error(ex.getMessage());
        }
        finally {
            try {
                statement.close();
            } catch (SQLException ex) { Log.error(ex.getMessage());  }
        }
        return ping;
    }

    @Override
    /**
     * Method for getting Hashmap from DB
     * @param SQLText - Query text to get data from database
     * @param conn - DB connection
     * @return List of pairs (String, String)
     * @throws SQLException
     */

    public ArrayList<HashMap<String, String>> getDataFromDB(String SQLText, Connection conn) throws SQLException {
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        ResultSet rs = getResultSet(SQLText, conn);
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()){
            HashMap<String, String> currentHash = new HashMap<String, String>();
            for(int j = 1; j < rsmd.getColumnCount(); j++){
                String celija =  rs.getString(j);
                currentHash.put(rsmd.getColumnName(j), celija);
            }
            result.add(currentHash);
        }
        return result;
    }

    @Override
    /**
     * Method for getting Hashmap from DB with parameters
     * @param SQLText - Query text to get data from database
     * @param conn - DB connection
     * @param parameters - List of parameters for query
     * @return List of pairs (String, String)
     * @throws SQLException
     */
    public ArrayList<HashMap<String, String>> getDataFromDB(String SQLText, Connection conn, Object... parameters) throws SQLException {
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        ResultSet rs = getResultSet(SQLText, conn, parameters);
        ResultSetMetaData rsmd = rs.getMetaData();

        while(rs.next()){
            HashMap<String, String> currentHash = new HashMap<String, String>();
            for(int j = 1; j < rsmd.getColumnCount(); j++){
                String celija =  rs.getString(j);
                currentHash.put(rsmd.getColumnName(j), celija);
            }
            result.add(currentHash);
        }
        return result;
    }

    @Override
    /**
     * Method for getting Hashmap from DB
     * @param SQLText- Query text to get data from database
     * @param conn - DB connection
     * @return
     * @throws SQLException
     */
    public ArrayList<HashMap<String, Object>> getObjectFromDB(String SQLText, Connection conn) throws SQLException{
        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        ResultSet rs = getResultSet(SQLText, conn);
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()){
            HashMap<String, Object> currentHash = new HashMap<String, Object>();
            for(int j = 1; j < rsmd.getColumnCount(); j++){
                Object celija =  rs.getObject(j);
                currentHash.put(rsmd.getColumnName(j), celija);
            }
            result.add(currentHash);
        }
        return result;
    }

    @Override
    /**
     * Method for getting Hashmap from DB with parameters
     * @param SQLText - Query text to get data from database
     * @param conn - DB connection
     * @param parameters - List of parameters for query
     * @return List of pairs (String, Object)
     * @throws SQLException
     */
    public ArrayList<HashMap<String, Object>> getObjectFromDB(String SQLText, Connection conn, Object... parameters) throws SQLException {
        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        ResultSet rs = getResultSet(SQLText, conn, parameters);
        ResultSetMetaData rsmd = rs.getMetaData();

        while(rs.next()){
            HashMap<String, Object> currentHash = new HashMap<String, Object>();
            for(int j = 1; j < rsmd.getColumnCount(); j++){
                String celija =  rs.getString(j);
                currentHash.put(rsmd.getColumnName(j), celija);
            }
            result.add(currentHash);
        }
        return result;
    }
}
