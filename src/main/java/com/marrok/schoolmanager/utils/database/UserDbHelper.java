package com.marrok.schoolmanager.utils.database;

import com.marrok.schoolmanager.utils.DatabaseConnection;
import com.marrok.schoolmanager.utils.GeneralUtil;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDbHelper {
    Logger logger = Logger.getLogger(UserDbHelper.class);
    public Connection cnn;

    public UserDbHelper() throws SQLException {
        logger.info("UserDbHelper started getConnection Instance() ");
        this.cnn = DatabaseConnection.getInstance().getConnection();
    }
    public String getUserNameById(int userId) {
        logger.info("getUserNameById");
        String query = "SELECT username FROM user WHERE id = ?";
        String username = null;

        try (PreparedStatement preparedStatement = this.cnn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException e) {
          logger.error(e);
        }

        return username;
    }

    public int getUserIdByName(String username) {
        logger.info("getUserIdByName");
        String query = "SELECT id FROM user WHERE username = ?";
        int userId = -1; // Return -1 if the user is not found

        try (PreparedStatement preparedStatement = this.cnn.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
           logger.error(e);
        }

        return userId;
    }
    /**
     * Authenticate the user based on username and password.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the username and password are valid, false otherwise.
     */
    public boolean authenticateUser(String username, String password) {


        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(query)) {
            // Hash the password if needed
            String hashedPassword = GeneralUtil.hash(password); // If you're storing hashed passwords
            logger.info("Hashed password: " + hashedPassword);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword); // If you're using hashed passwords

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // User exists with the given credentials
                return true;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
           logger.error(e);
        }

        return false;  // No matching user found
    }



}
