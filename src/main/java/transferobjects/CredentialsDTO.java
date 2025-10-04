package transferobjects;

/**
 * Data Transfer Object for user credentials.
 * Contains email and password for authentication purposes.
 * @author Simon
 */
public class CredentialsDTO {
    
    private String email;
    private String password;
    
    /**
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}