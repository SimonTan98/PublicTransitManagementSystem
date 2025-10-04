package dataaccesslayer;

import transferobjects.FuelDTO;
import java.util.List;

/**
 * File Name: FuelDAO.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an interface for defining DAO methods related to 
 * fuel reports.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface FuelDAO {
    /**
     * Defines a method for retrieving information required for a fuel
     * consumption report.
     * 
     * @return a List of FuelDTOs containing fuel used, actual fuel efficiency,
     * and expected fuel efficiency on a trip.
     */
    List<FuelDTO> getFuelConsumptionReport();
    
    /**
     * Defines a method to get the average fuel efficiency across all trips.
     * 
     * @return a double representing the average fuel efficiency across all trips.
     */
    double getAverageFuelEfficiency();
    
    /**
     * Defines a method to get the total amount of fuel used on all trips.
     * 
     * @return the total amount of fuel used on all trips.
     */
    double getTotalFuelUsed();
}