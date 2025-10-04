package factory;

import builder.*;
import constants.VehicleType;

/**
 * File Name: VehicleBuilderFactory.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Simple Factory class that creates VehicleBuilder subclasses.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class VehicleBuilderFactory {
    
    /**
     * Method that creates a VehicleBuilder class for a given vehicle type.
     * 
     * @param vehicleType the type of vehicle to create
     * @return a VehicleBuilder for the given vehicle type.
     * @throws IllegalArgumentException if an invalid vehicle type is used.
     */
    public static VehicleBuilder createBuilder(String vehicleType) 
            throws IllegalArgumentException {
        
        VehicleBuilder builder = null;
        
        switch(vehicleType) {
        
            case VehicleType.BUS: 
                builder = new BusBuilder();
                break;
            case VehicleType.DIESELTRAIN:
                builder = new DieselTrainBuilder();
                break;
            case VehicleType.ELECTRICLIGHTRAIL:
                builder = new ElectricLightRailBuilder();
                break;
            default:
                throw new IllegalArgumentException("Invalid vehicle type");
        }
        
        return builder;
    }
    
}
