package rs.aik.testautomation.AIKTestAutomation.Data;

public enum DatabaseType {
    MSSQL("MSSqlServer"),
    ORACLE("Oracle"),
    POSTGRESQL("PostgreSQL"),
    MYSQL("MySQL");

    DatabaseType(String type) { this.dbEngineType = type; }

    private String dbEngineType;
    public String getEngineType() { return this.dbEngineType; }
}
