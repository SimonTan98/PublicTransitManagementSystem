package dataaccesslayer;

import transferobjects.UserDTO;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import transferobjects.BreakDTO;

/**
 * File Name: UserDAOImpl.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as a concrete implementation of the UserDAO interface.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class UserDAOImpl implements UserDAO {
    
    /**
     * This method retrieves a user from the database using the given
     * credentials.
     * 
     * @param credentials a CredentialsDTO containing the login credentials for a user.
     * @return a UserDTO containing the retrieved user's information from the database.
     * Returns null if not found.
     */
    @Override
    public UserDTO getUserByCredentials(CredentialsDTO credentials) {

        UserDTO user = null;
        String query = "SELECT * FROM User WHERE Email = ? AND Password = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, credentials.getEmail().trim());
                statement.setString(2, credentials.getPassword().trim());

            ResultSet results = statement.executeQuery();
            
            if(results.next()) {
                user = new UserDTO();
                user.setUserID(results.getInt("User_id"));
                user.setName(results.getString("Name"));
                user.setEmail(results.getString("Email"));
                user.setPassword(results.getString("Password"));
                user.setUserType(results.getString("User_Type"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
    
    /**
     * Adds a user to the database.
     * 
     * @param user a UserDTO containing the information of the new user
     * @return a boolean that is true if the user is successfully added, else false.
     */
    @Override
    public boolean addUser(UserDTO user) {
        
        int rowsAffected = 0;
        String query = "INSERT INTO User (Name, Email, "
                + "Password, User_Type) VALUES (?, ?, ?, ?);";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getUserType());
            rowsAffected = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    /**
     * Inserts a break into the database.
     * 
     * @param breakInfo a BreakDTO containing the information of a break.
     * @return a boolean that is true if the break is successfully logged, else false.
     */
    @Override
    public boolean logBreak(BreakDTO breakInfo) {
        int rowsAffected = 0;
        String query = "INSERT INTO BREAK (User_id, Start_Time, End_Time) "
                + "VALUES (?, ?, ?)";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, breakInfo.getUserId());
            statement.setTimestamp(2, breakInfo.getStartTime());
            statement.setTimestamp(3, breakInfo.getEndTime());
            
            rowsAffected = statement.executeUpdate();
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }
}
