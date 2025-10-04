package dataaccesslayer;

/**
 * File Name: UserDAO.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Custom exception class that should be thrown when invalid credentials
 * are provided when logging in to the application.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class InvalidCredentialsException extends Exception {
    
    /**
     * Overridden method that returns an explanation of why this exception 
     * was thrown.
     * 
     * @return the reason the exception was thrown.
     */
    @Override
    public String getMessage(){ 
        return "Invalid credentials entered";
    }
    
}
