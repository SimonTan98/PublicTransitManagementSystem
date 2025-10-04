package dataaccesslayer;

import transferobjects.TripDTO;
import java.util.List;
import java.util.Map;

/**
 * File Name: TripDAO.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an interface for defining DAO methods related to 
 * trips.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface TripDAO {
    
    /**
     * Defines a method to get all trips from the database.
     * 
     * @return a List of TripDTOs containing info from all trips.
     */
    List<TripDTO> getAllTrips();
    
    /**
     * Defines a method to calculate the percentage of trips being on time.
     * 
     * @return a double representing the percentage of trips that are on time.
     */
    double getOnTimePercentage();
    
    /**
     * Defines a method to get the average delay between a a vehicle's expected
     * trip duration and actual trip duration.
     * 
     * @return a double representing the average delay in minutes.
     */
    double getAverageDelay();
    
    /**
     * Defines a method to retrieve all trips grouped by operators.
     * 
     * @return a Map containing a List of TripDTOs mapped to user Ids.
     */
    Map<Integer, List<TripDTO>> getTripsByOperator();
    
    /**
     * Defines a method to return the on-time percentages of operators.
     * 
     * @return a Map of doubles representing on-time percentages mapped to user Ids.
     */
    Map<Integer, Double> getOperatorOnTimePercentages();
}