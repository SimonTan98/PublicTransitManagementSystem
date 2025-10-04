package command;

import transferobjects.VehicleDTO;

/**
 * File Name: FuelStation.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an invoker class in the Command design pattern. Accepts
 * an instance of a vehicle and can top up the tank of a vehicle using the 
 * appropriate command.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class FuelStation {
    
    /**
     * The vehicle to be refueled.
     */
    private VehicleDTO vehicle;
    
    /**
     * Constructor for FuelStation that accepts a vehicle.
     * 
     * @param vehicle the vehicle to be refueled.
     */
    public FuelStation(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
    
    /**
     * Method that tops up a vehicle with an amount given by a command.
     * 
     * @param command a Command that specifies the amount of fuel to refill.
     */
    public void topUp(Command command) {
        this.vehicle.setFuelLevel(command.refuel());
    }    
}