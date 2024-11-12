package model;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * <h2> Banquets Class </h2>
 * This class provides functionality to interact with the banquets table in the database.
 * It also initializes the banquets table.
 *
 * Implements basic operations {@code addBanquet}, {@code getAllBanquets}, {@code getBanquet}, {@code updateBanquet}, {@code deleteBanquet}
*/
public class BanquetsManager extends EntityManager {
    public BanquetsManager() {
        // Initialize database connection in constructor
        initializeBanquets();
    }

    private void initializeBanquets() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS BANQUETS (
                BIN INTEGER PRIMARY KEY AUTO_INCREMENT,
                Name VARCHAR(255) NOT NULL,
                DateTime DATETIME NOT NULL,
                Location VARCHAR(255) NOT NULL,
                Address VARCHAR(255) NOT NULL,
                Type VARCHAR(255) NOT NULL,
                ContactStaffName VARCHAR(255) NOT NULL,
                Available BOOLEAN NOT NULL,
                Quota INT NOT NULL,
            )
        """;
        dbConnection.executeUpdate(createTableSQL);
    }

    /** Retrieves all banquet records from the database.
     * <p>This method executes a SQL query to select all entries from the {@code banquets}
     * table and constructs a list of {@code Banquet} objects representing each record.</p>
      * @return A {@code List} containing all {@code Banquet} objects from the database.
     * @throws RuntimeException if a database access error occurs or the SQL query fails.
     */
    public List<Banquet> getAllBanquets() {
        String selectAllSQL = "SELECT * FROM BANQUETS";
        List<Banquet> banquets = new ArrayList<>();
        try(PreparedStatement pstmt = dbConnection.prepareStatement(selectAllSQL)) {
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                banquets.add(new Banquet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDate(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getBoolean(7), resultSet.getInt(8)));
            }
            return banquets;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a {@code Banquet} object from the database based on the provided banquet ID.
     *
     * @param BIN the unique identifier of the banquet to retrieve.
     * @return the {@code Banquet} object corresponding to the provided ID.
     * @throws NoSuchElementException if no banquet with the specified ID exists.
     * @throws RuntimeException       if a database access error occurs.
     */
    public Banquet getBanquet(int BIN) {
        String selectSQL = "SELECT * FROM BANQUETS WHERE BIN = " + BIN;

        try (PreparedStatement pstmt = dbConnection.prepareStatement(selectSQL)){
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return new Banquet(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getBoolean(7),
                        resultSet.getInt(8));
            } else {
                throw new NoSuchElementException("Banquet with ID " + BIN + " not found.");
            }
        } catch(SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a list of {@code Banquet} objects from the database based on a specified column and value.
     * <p>
     * This method executes a SQL query to select all entries from the {@code banquets}
     * table where the specified column matches the provided value.
     *
     * @param attribute the column name to filter the banquets by. Must be one of the allowed columns.
     * @param newValue  the value to match in the specified column.
     * @return a {@code List} containing {@code Banquet} objects that match the criteria.
     * @throws RuntimeException         if a database access error occurs.
     */

    public List<Banquet> getBanquet(String attribute, String newValue){
        String selectSQL = "SELECT * FROM BANQUETS WHERE " + attribute + " = ?";

        List<Banquet> banquets = new ArrayList<>();

        try (PreparedStatement pstmt = dbConnection.prepareStatement(selectSQL)) {
            pstmt.setString(1, newValue);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                banquets.add(new Banquet(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDate(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getBoolean(7), resultSet.getInt(8)));
            }

            return banquets;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    /**
     * Updates a specific field of a {@code Banquet} record in the database.
     *
     * <p>This method updates the value of a specified column for the banquet identified by
     * the provided banquet ID.</p>
     *
     * @param BIN the unique identifier of the banquet to update.
     * @param attribute    the column name to update. Must be one of the allowed columns.
     * @param newValue     the new value to set for the specified column.
     * @throws NoSuchElementException   if no banquet with the specified ID exists.
     * @throws RuntimeException         if a database access error occurs.
     */

    public void updateBanquet(int BIN, String attribute, String newValue) {
        String updateSQL = "UPDATE BANQUETS SET " + attribute + " = ? WHERE BIN = ?";

        try (PreparedStatement pstmt = dbConnection.prepareStatement(updateSQL)) {
            pstmt.setString(1, newValue);
            pstmt.setInt(2, BIN);

            int affectedRowsCnt = pstmt.executeUpdate();
            if (affectedRowsCnt == 0) {
                throw new NoSuchElementException("Banquet with BIN " + BIN + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a {@code Banquet} record from the database based on the provided banquet ID.
     *
     * @param BIN the unique identifier of the banquet to delete.
     * @throws NoSuchElementException if no banquet with the specified ID exists.
     * @throws RuntimeException       if a database access error occurs.
     */

    public void deleteBanquet(int BIN) {
        String deleteSQL = "DELETE FROM BANQUETS WHERE BIN = " + BIN;

        try (PreparedStatement pstmt = dbConnection.prepareStatement(deleteSQL)) {
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new NoSuchElementException("Banquet with BIN " + BIN + " not found.");
            }
        } catch(SQLException e){
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
