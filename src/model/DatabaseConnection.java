package model;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static Statement stmt = null;
    private Connection con = null;

    DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //Corresponding Database Driver
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Executes the given SQL query and returns a ResultSet object.
     *
     * @param query the SQL query string to be executed
     * @return ResultSet containing the results of the query
     * @throws RuntimeException if a database access error occurs during the query execution
     */
    public ResultSet executeQuery(String query) {
        try {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the given SQL statement and returns a ResultSet object.
     *
     * @param sqlUpdate the SQL statement string to be executed, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement.
     * @return Either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws RuntimeException if a database access error occurs during the query execution
     */

    public int executeUpdate(String sqlUpdate) {
        try {
            return stmt.executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closese the current Connection to the remote database
     *
     * @throws RuntimeException if a database access error occurs during the query execution
     */

    public void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a PreparedStatement for the given SQL query
     *
     * @param query the SQL query with ? placeholders
     * @return PreparedStatement object
     * @throws RuntimeException if a database access error occurs
     */
    public PreparedStatement prepareStatement(String query) {
        try {
            return con.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}