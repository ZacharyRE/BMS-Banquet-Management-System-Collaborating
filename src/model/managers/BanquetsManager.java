package model.managers;
import model.ModelException;
import model.entities.Banquet;

import java.sql.*;
import java.util.*;

/**
 * <h2> Banquets Class </h2>
 * This class provides functionality to interact with the banquets table in the database.
 * It also initializes the banquets table.
 *
 * Implements basic operations {@code addBanquet}, {@code getAllBanquets}, {@code getBanquet}, {@code updateBanquet}, {@code deleteBanquet}
*/
public class BanquetsManager {
    private final Connection con;

    public BanquetsManager(Connection con) throws ModelException {
        this.con = con;
        initializeBanquets();
    }

    private void initializeBanquets() throws ModelException {
        String stmt = """
            CREATE TABLE IF NOT EXISTS BANQUETS (
                BIN INTEGER PRIMARY KEY AUTO_INCREMENT,
                Name VARCHAR(255) NOT NULL,
                DateTime DATETIME NOT NULL,
                Address VARCHAR(255) NOT NULL,
                Location VARCHAR(255) NOT NULL,
                ContactStaffName VARCHAR(255) NOT NULL,
                Available BOOLEAN NOT NULL,
                Quota INTEGER NOT NULL
            )
        """;

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException("Cannot initialize the database table.");
        }
    }

    /** Retrieves all banquet records from the database.
     * <p>This method executes a SQL query to select all entries from the {@code banquets}
     * table and constructs a list of {@code Banquet} objects representing each record.</p>
     * @return A {@code List} containing all {@code Banquet} objects from the database.
     * @throws ModelException if any errors encountered.
     */
    public List<Banquet> getAllBanquets() throws ModelException {
        String selectAllSQL = "SELECT * FROM BANQUETS";

        List<Banquet> banquets = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(selectAllSQL)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                banquets.add(new Banquet(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getBoolean(7),
                        resultSet.getInt(8)));
            }

            return banquets;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a {@code Banquet} object from the database based on the provided banquet ID.
     *
     * @param BIN the unique identifier of the banquet to retrieve.
     * @return the {@code Banquet} object corresponding to the provided ID.
     * @throws ModelException if any errors encountered.
     */
    public Banquet getBanquet(int BIN) throws ModelException {
        String selectSQL = "SELECT * FROM BANQUETS WHERE BIN = " + BIN;

        try (PreparedStatement pstmt = con.prepareStatement(selectSQL)) {
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
                throw new ModelException("Banquet with ID " + BIN + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of {@code Banquet} objects from the database based on a specified column and value.
     * <p>
     * This method executes a SQL query to select all entries from the {@code banquets}
     * table where the specified column matches the provided value.
     *
     * <h4>Usage examples</h4>
     * <blockquote><pre>
     *     List<Banquet> testBanquets = getBanquet("Name", "TestBanquet");
     * </pre></blockquote>
     *
     * @param attribute the column name to filter the banquets by. Must be one of the allowed columns.
     * @param value the value to match in the specified column.
     * @return a {@code List} containing {@code Banquet} objects that match the criteria.
     * @throws ModelException if any errors encountered.
     */
    public List<Banquet> getBanquet(String attribute, String value) throws ModelException {
        String stmt = "SELECT * FROM BANQUETS WHERE " + attribute + " = " + value;

        List<Banquet> banquets = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                banquets.add(new Banquet(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getBoolean(7),
                        resultSet.getInt(8)));
            }
            return banquets;
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
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
     * @throws ModelException if any errors encountered.
     */
    public void updateBanquet(int BIN, String attribute, String newValue) throws ModelException { // This method should be improved later.
        String stmt = "UPDATE BANQUETS SET " + attribute + " = " + newValue + " WHERE BIN = " + BIN; // should this be adopted?

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            if (/* affectedRowCnt = */ pstmt.executeUpdate() == 0) {
                throw new NoSuchElementException("Banquet with BIN " + BIN + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }

    /**
     * Deletes a {@code Banquet} record from the database based on the provided banquet ID.
     *
     * @param BIN the unique identifier of the banquet to delete.
     * @throws ModelException if any errors encountered.
     */
    public void deleteBanquet(int BIN) throws ModelException {
        String stmt = "DELETE FROM BANQUETS WHERE BIN = " + BIN;

        try (PreparedStatement pstmt = con.prepareStatement(stmt)) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new ModelException("Banquet with BIN " + BIN + " not found.");
            }
        } catch (SQLException e) {
            throw new ModelException("Database error: " + e.getMessage());
        }
    }
}
