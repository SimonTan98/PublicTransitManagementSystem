
package command;

import constants.FuelTank;

/**
 * File Name: RefuelElectricLightRailCommand.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Concrete command for refueling an ELR train.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class RefuelElectricLightRailCommand implements Command {
    
    /**
     * Method that specifies the amount of fuel to refill for an ELR train.
     * 
     * @return a double specifying the fuel level to refill for an ELR train.
     */
    @Override
    public double refuel() {
        return FuelTank.ELECTRIC_LIGHT_RAIL_TANK;
    }
    
}
