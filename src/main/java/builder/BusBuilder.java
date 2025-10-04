package builder;

import transferobjects.BusDTO;

/**
 * File Name: BusBuilder.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Concrete class for building BusDTOs.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class BusBuilder extends VehicleBuilder<BusDTO> {
    
    /**
     * The emission rate of the engine for this bus.
     */
    private double emissionRate;
    
    /**
     * Setter for emission rate.
     * 
     * @param emissionRate the emission rate of this bus.
     * @return this busbuilder.
     */
    @Override
    public VehicleBuilder addEmissionRate(double emissionRate) {
        this.emissionRate = emissionRate;
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param oilStatus not used in this class.
     * @return this busbuilder.
     */
    @Override
    public VehicleBuilder addOilStatus(double oilStatus) {
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param catenaryCondition not used in this class.
     * @return this busbuilder.
     */
    @Override
    public VehicleBuilder addCatenaryCondition(double catenaryCondition) {
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param pantographCondition not used in this class.
     * @return this busbuilder.
     */
    @Override
    public VehicleBuilder addPantographCondition(double pantographCondition) {
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param circuitBreakerCondition not used in this class.
     * @return this busbuilder.
     */
    @Override
    public VehicleBuilder addCircuitBreakerCondition(double circuitBreakerCondition) {
        return this;
    }
    
    /**
     * Method to create a bus with the attributes set in this builder.
     * 
     * @return a BusDTO with the attributes set in this builder.
     */
    @Override
    public BusDTO build() {
        return new BusDTO(vehicleNumber, vehicleName, vehicleType, fuelType, 
            consumptionRate, maxCapacity, currentRouteId, 
            axleBearingsCondition, brakesCondition, 
            wheelsCondition, status, latitude, 
            longitude, emissionRate);
    }
    
}
