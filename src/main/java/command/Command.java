package command;

/**
 * File Name: Command.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an interface for defining the refuel command to operate 
 * on FuelStation.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface Command {
    
    /**
     * Defines a command to refuel a vehicle.
     * 
     * @return a double representing the new fuel level of the vehicle.
     */
    double refuel();

}
