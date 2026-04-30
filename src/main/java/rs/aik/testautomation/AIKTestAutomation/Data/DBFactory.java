package rs.aik.testautomation.AIKTestAutomation.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract class to define all abstract method that communication with various databases needs
 */
public abstract class DBFactory {

    // define interface methods here
    // *** defining interface using abstract class provides extensibility easy way regarding backward compatibility
    public abstract Connection createConnection();
    public abstract Boolean pingDB(Connection conn);
    public abstract Object getScalar(String SQLText, String[] parameters, Connection conn);
    public abstract Object getScalar(String SQLText, Connection conn, Object... parameters);
    public abstract ResultSet getResultSet(String SQLText, String[] parameters, Connection conn);
    public abstract ResultSet getResultSet(String SQLText, Connection conn, Object... parameters);
    public abstract boolean executeSQL(String SQLText, Connection conn, Object... parameters);
    public abstract ArrayList<HashMap<String, Object>> getObjectFromDB(String SQLText, Connection conn) throws SQLException;
    public abstract ArrayList<HashMap<String, Object>> getObjectFromDB(String SQLText, Connection conn, Object... parameters) throws SQLException;
    public abstract ArrayList<HashMap<String, String>> getDataFromDB(String SQLText, Connection conn) throws SQLException;
    public abstract ArrayList<HashMap<String, String>> getDataFromDB(String SQLText, Connection conn, Object... parameters) throws SQLException;
    // todo other commands

    // static initialization
    public static DBFactory getDBFactory(DatabaseType dbEngine){
        switch (dbEngine){
            case MSSQL: return new DBFactoryMSSQL();
            case ORACLE: return new DBFactoryOracle();
            case POSTGRESQL: return new DBFactoryPostgreSQL();
            case MYSQL: return new DBFactoryMySQL();
            default: return null;
        }

    }
}

