package dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * File Name: DataSource.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class creates and provides the connection between the application and
 * the database. Is designed with the singleton design pattern to ensure that
 * only one connection exists at a time.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public enum DataSource {
 
    /**
     * Single static instance for this enum.
     */
    DATASOURCE;
 
    /**
     * The Connection to the database.
     */
    private static Connection connection;
  
    /**
     * Creates and returns a Connection to the database held by this enum. 
     * 
     * @return a Connection to the database.
     * @throws SQLException if there is an error connecting to the database.
     */
    public static synchronized Connection getConnection() 
            throws SQLException {
        
        if(connection == null || connection.isClosed()) {
            try {
                String[] properties = getProperties();
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(properties[0], 
                                                         properties[1], 
                                                         properties[2]);
            }
            catch(SQLException e) {
                e.printStackTrace();
                throw e;
            }
            catch (ClassNotFoundException ex) {
                Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }
    
    /**
     * Private worker method used to access the database.properties file.
     * 
     * @return a String array containing the jdbc url, username, and password
     * information recorded in database.properties.
     */
    private static String[] getProperties(){
        String[] properties = new String[3];
        
        try(InputStream input = DataSource.class.getClassLoader().getResourceAsStream("database.properties")){
            Properties props = new Properties();
            props.load(input);
            properties[0] = props.getProperty("jdbc.url");
            properties[1] = props.getProperty("jdbc.username");
            properties[2] = props.getProperty("jdbc.password");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        return properties;
    }
}
