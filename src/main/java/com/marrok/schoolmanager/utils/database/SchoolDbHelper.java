package com.marrok.schoolmanager.utils.database;

import com.marrok.schoolmanager.Main;
import com.marrok.schoolmanager.utils.DatabaseConnection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolDbHelper {
    private static final Logger logger = LogManager.getLogger(SchoolDbHelper.class);
    public Connection cnn;

    public SchoolDbHelper() throws SQLException {
        logger.info("SchoolDbHelper started getConnection Instance() ");
        this.cnn = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Checks if a school with the given name, address, and email already exists in the database.
     *
     * @return true if the school exists, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean isSchoolInfoExists() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM school";
        try (PreparedStatement pstmt = cnn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    logger.info("School info check count: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Error checking school info existence: ", e);
            throw e;
        }
        return false;
    }
    /**
     * Creates a new school record in the database.
     *
     * @param name    School name.
     * @param address School address.
     * @param email   School email.
     * @throws SQLException if a database access error occurs.
     */
    public void createSchool(String name, String address, String email) throws SQLException {
        String query = "INSERT INTO school (name, address, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = cnn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, email);

            int rows = pstmt.executeUpdate();
            logger.info("School created, rows affected: " + rows);
        } catch (SQLException e) {
            logger.error("Error creating school: ", e);
            throw e;
        }
    }

    /**
     * Reads all school records from the database.
     *
     * @return ResultSet containing all school records.
     * @throws SQLException if a database access error occurs.
     */
    public ResultSet readAllSchools() throws SQLException {
        String query = "SELECT * FROM school";
        try (PreparedStatement pstmt = cnn.prepareStatement(query)) {
            return pstmt.executeQuery();
        } catch (SQLException e) {
            logger.error("Error reading schools: ", e);
            throw e;
        }
    }

    /**
     * Updates the first school record.
     *
     * @param name    New school name.
     * @param address New school address.
     * @param email   New school email.
     * @throws SQLException if a database access error occurs.
     */
    public void updateSchool(String name, String address, String email) throws SQLException {
        String query = "UPDATE school SET name = ?, address = ?, email = ? LIMIT 1";
        try (PreparedStatement pstmt = cnn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, email);

            int rows = pstmt.executeUpdate();
            logger.info("School updated, rows affected: " + rows);
        } catch (SQLException e) {
            logger.error("Error updating school: ", e);
            throw e;
        }
    }

    /**
     * Deletes a school record by ID.
     *
     * @param id School ID.
     * @throws SQLException if a database access error occurs.
     */
    public void deleteSchool(int id) throws SQLException {
        String query = "DELETE FROM school WHERE id = ?";
        try (PreparedStatement pstmt = cnn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            logger.info("School deleted, rows affected: " + rows);
        } catch (SQLException e) {
            logger.error("Error deleting school: ", e);
            throw e;
        }
    }

    /**
     * Retrieves the current school information from the database.
     *
     * @return An array of Strings containing school name, address, and email, or null if no record exists.
     * @throws SQLException if a database access error occurs.
     */
    public String[] getSchoolInfo() throws SQLException {
        String query = "SELECT name, address, email FROM school LIMIT 1";
        try (PreparedStatement pstmt = cnn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");

                logger.info("School info retrieved: " + name + ", " + address + ", " + email);

                return new String[]{name, address, email};
            } else {
                logger.info("No school information found in the database.");
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error retrieving school information: ", e);
            throw e;
        }
    }
}
