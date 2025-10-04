package fuelpkg;

/**
 * File Name: IConsumptionStrategy.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Interface that defines methods for ConsumptionStrategies.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface IConsumptionStrategy {
    
    /**
     * Defines a method to get the fuel efficiency for a vehicle's trip.
     * 
     * @param distance the distance the vehicle has traveled
     * @param fuelAmount The amount of fuel/energy consumed 
     * @param hours the hours taken to travel this distance
     * @return a double representing the fuel efficiency of this trip.
     */
    public double getConsumption(double distance, double fuelAmount, double hours);
    
}
