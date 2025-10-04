package constants;

/**
 * File Name: VehicleStatus.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class provides constant values for the status of vehicles.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class VehicleStatus {
    
    /**
     * For active vehicles.
     */
    public final static String ACTIVE = "ACTIVE";
    
    /**
     * For vehicles in maintenance.
     */
    public final static String IN_MAINTENANCE = "IN MAINTENANCE";
    
    /**
     * For vehicles that are out of service.
     */
    public final static String OUT_OF_SERVICE = "OUT OF SERVICE";
}
