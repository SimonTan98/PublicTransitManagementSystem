package fuelpkg;

/**
 * File Name: ElectricLightRailConsumptionStrategy.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Strategy class for calculating fuel efficiency of an Electric Light Rail train.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class ElectricLightRailConsumptionStrategy implements IConsumptionStrategy{
    
    /**
     * Calculates energy efficiency for a diesel train.Electric light rail 
     * energy efficiency is calculated in kWh/km.
     * 
     * @param distance the distance the vehicle has traveled in kilometers.
     * @param energy the amount of energy the vehicle has consumed for this distance in kiloWatts.
     * @param hours the amount of time spent in hours
     * @return the energy efficiency of this ELR train in kWh/km
     */
    public double getConsumption(double distance, double energy, double hours) {
        return energy * hours / distance;
    }
    
}
