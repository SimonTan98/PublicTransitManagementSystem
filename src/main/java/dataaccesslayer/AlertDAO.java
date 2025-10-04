package dataaccesslayer;

import java.util.List;
import transferobjects.AlertDTO;

/**
 * File Name: AlertDAO.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an interface for defining DAO methods related to 
 * alerts.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface AlertDAO {
    /**
     * Defines a method to add an alert to the database.
     * 
     * @param alert an AlertDTO containing the information of an alert.
     */
    void addAlert(AlertDTO alert);
    
    /**
     * Defines a method to update an alert in the database.
     * 
     * @param alert an AlertDTO containing the new information of an alert.
     */
    void updateAlert(AlertDTO alert);
    
    /**
     * Defines a method to delete an alert in the database.
     * 
     * @param alertID an int representing the alert's ID
     */
    void deleteAlert(int alertID);
    
    /**
     * Defines a method to retrieve a specific alert given an alert ID.
     * 
     * @param alertID an int representing the alert id.
     * @return an AlertDTO that contains the alert's information.
     */
    AlertDTO getAlertById(int alertID);
    
    /**
     * Defines a method to get all alerts from the database.
     * 
     * @return a List of AlertDTOs containing the information of all alerts in 
     * the database.
     */
    List<AlertDTO> getAllAlerts();
    
    /**
     * Defines a method to get alerts for a specific vehicle.
     * 
     * @param vehicleID the vehicle's ID.
     * @return a List of AlertDTOs for the given vehicle.
     */
    List<AlertDTO> getAlertsByVehicle(int vehicleID);
    
    /**
     * Defines a method to get all active alerts.
     * 
     * @return a List of AlertDTOs containing information of all active alerts.
     */
    List<AlertDTO> getActiveAlerts();
    
    /**
     * Defines a method to retrieve alerts by alert type.
     * 
     * @param alertType a String representing the type of alert.
     * @return a List of AlertDTOs containing information of all alerts of the
     * given type.
     */
    List<AlertDTO> getAlertsByType(String alertType);
}
