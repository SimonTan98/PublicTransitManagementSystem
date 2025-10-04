package dataaccesslayer;

import transferobjects.MaintenanceDTO;
import java.util.List;

/**
 * File Name: MaintenanceDAO.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an interface for defining DAO methods related to 
 * maintenance.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface MaintenanceDAO {
    
    /**
     * Defines a method to get all maintenance requests.
     * 
     * @return a List of MaintenanceDTOs containing all maintenance requests.
     */
    List<MaintenanceDTO> getAllMaintenance();
    
    /**
     * Defines a method to get all maintenance requests by status.
     * 
     * @param status the status of the desired requests.
     * @return a List of MaintenanceDTOs containing all maintenance requests with
     * a given status.
     */
    List<MaintenanceDTO> getMaintenanceByStatus(String status);
    
    /**
     * Defines a method to retrieve the total cost of all maintenance requests.
     * 
     * @return a price of all maintenance requests.
     */
    double getTotalMaintenanceCost();
}