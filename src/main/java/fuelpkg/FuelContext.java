package fuelpkg;

/**
 * File Name: FuelContext.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Context class for calculating fuel efficiency using different consumption 
 * strategies.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class FuelContext {
    
    /**
     * The consumption strategy to use for calculating fuel efficiency.
     */
    private IConsumptionStrategy strategy;
    
    /**
     * Method used to set the consumption strategy for this context.
     * 
     * @param strategy the new consumption strategy to use.
     */
    public void setConsumptionStrategy(IConsumptionStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Method used to calculate a vehicle's fuel efficiency based on the set
     * strategy.
     * 
     * @param distance the distance the vehicle has traveled
     * @param fuelAmount The amount of fuel/energy consumed 
     * @param hours the hours taken to travel this distance
     * @return a double representing the fuel efficiency of this trip.
     */
    public double calculateConsumption(double distance, double fuelAmount, double hours) {
        return strategy.getConsumption(distance, fuelAmount, hours);
    }
    
}
