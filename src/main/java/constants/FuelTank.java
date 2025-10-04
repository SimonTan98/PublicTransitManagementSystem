package constants;

/**
 * File Name: FuelTank.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class provides constant values for the full tank levels of 
 * different vehicles.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class FuelTank {
    
    /**
     * Full tank fuel level for a bus.
     */
    public final static double BUS_FULL_TANK = 950.0;
    
    /**
     * Full tank fuel level for a diesel train.
     */
    public final static double DIESEL_TRAIN_FULL_TANK = 15000.0;
    
    /**
     * Full tank fuel level for an ELR train. 0 since it runs on electricity.
     */
    public final static double ELECTRIC_LIGHT_RAIL_TANK = 0;
}
