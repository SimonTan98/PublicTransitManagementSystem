package transferobjects;

/**
 * Data Transfer Object for user.
 * Contains user details including credentials and type.
 * @author Simon Tan
 */
public class UserDTO {
    private int userID;
    private String name;
    private String email;
    private String password;
    private String userType; 

    /**
     * Default constructor.
     */
    public UserDTO() {}

    /**
     * Constructs a UserDTO with specified parameters.
     *
     * @param userID the unique identifier for the user
     * @param name the full name of the user
     * @param email the email address of the user
     * @param password the user's password 
     * @param userType the type/role of the user
     */
    public UserDTO(int userID, String name, String email, String password, String userType) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    /**
     * @return the user ID
     */
    public int getUserID() { return userID; }
    
    /**
     * @param userID the user ID to set
     */
    public void setUserID(int userID) { this.userID = userID; }
    
    /**
     * @return the user's full name
     */
    public String getName() { return name; }
    
    /**
     * @param name the user's name to set
     */
    public void setName(String name) { this.name = name; }
    
    /**
     * @return the user's email address
     */
    public String getEmail() { return email; }
    
    /**
     * @param email the user's email to set
     */
    public void setEmail(String email) { this.email = email; }
    
    /**
     * @return the user's password
     */
    public String getPassword() { return password; }
    
    /**
     * @param password the user's password to set
     */
    public void setPassword(String password) { this.password = password; }
    
    /**
     * @return the user's type/role
     */
    public String getUserType() { return userType; }
    
    /**
     * @param userType the user's type/role to set
     */
    public void setUserType(String userType) { this.userType = userType; }
}