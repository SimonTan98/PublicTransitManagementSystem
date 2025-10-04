package dataaccesslayer;

import java.util.List;
import transferobjects.UserDTO;
import transferobjects.CredentialsDTO;
import transferobjects.BreakDTO;

/**
 * File Name: UserDAO.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an interface for defining DAO methods related to 
 * users/operators. 
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface UserDAO {
    /**
     * Defines a method to get a user from the database using their login
     * credentials.
     * 
     * @param credentials a CredentialsDTO containing a user's login information.
     * @return a UserDTO containing the operator's information.
     */
    UserDTO getUserByCredentials(CredentialsDTO credentials);
    
    /**
     * Defines a method to add a user to the database.
     * 
     * @param user a UserDTO containing all information for a new user.
     * @return a boolean that returns true if the user is successfully added, else false.
     */
    boolean addUser(UserDTO user);
    
    /**
     * Defines a method to log a break for a user.
     * 
     * @param breakInfo a BreakDTO containing the information of a break.
     * @return a boolean that returns true if the break is successfully added, else false.
     */
    boolean logBreak(BreakDTO breakInfo);
}
