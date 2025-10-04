package fuelpkg;

/**
 * File Name: DieselTrainConsumptionStrategy.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Strategy class for calculating fuel efficiency of a diesel train.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class DieselTrainConsumptionStrategy implements IConsumptionStrategy {
    
    /**
     * Calculates fuel efficiency for a diesel train. Diesel Train fuel 
     * consumption is measured in liters/km
     * 
     * @param distance the distance the diesel train has traveled in kilometers.
     * @param fuelAmount the amount of gas the diesel train has used in liters
     * @param hours not used in this implementation
     * @return the fuel efficiency for this diesel train in liters/km
     */
    @Override
    public double getConsumption(double distance, double fuelAmount, double hours) {
        return fuelAmount / distance;
    }
    
}
