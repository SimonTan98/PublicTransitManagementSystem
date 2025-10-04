
package builder;

import transferobjects.ElectricLightRailDTO;

/**
 * File Name: ElectricLightRailBuilder.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an abstract builder class for constructing ELR trains.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class ElectricLightRailBuilder extends VehicleBuilder<ElectricLightRailDTO> {
    
    /**
     * The hours of use for the catenary of this ELR train.
     */
    private double catenaryCondition;
    
    /**
     * The hours of use for the pantograph of this ELR train.
     */
    private double pantographCondition;
    
    /**
     * The hours of use for the circuit breaker of this ELR train.
     */
    private double circuitBreakerCondition;

    /**
     * Empty method that does not affect this builder.
     * 
     * @param emissionRate not used in this class.
     * @return this electriclightrailbuilder.
     */
    @Override
    public VehicleBuilder addEmissionRate(double emissionRate) {
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param oilStatus not used in this class.
     * @return this electriclightrailbuilder.
     */
    @Override
    public VehicleBuilder addOilStatus(double oilStatus) {
        return this;
    }
    
    /**
     * Setter for catenary condition.
     * 
     * @param catenaryCondition hours of use for the catenary
     * @return this electriclightrailbuilder.
     */
    @Override
    public VehicleBuilder addCatenaryCondition(double catenaryCondition) {
        this.catenaryCondition = catenaryCondition;
        return this;
    }

    /**
     * Setter for pantograph condition.
     * 
     * @param pantographCondition hours of use for the pantograph
     * @return this electriclightrailbuilder.
     */
    @Override
    public VehicleBuilder addPantographCondition(double pantographCondition) {
        this.pantographCondition = pantographCondition;
        return this;
    }

    /**
     * Setter for circuit breaker condition.
     * 
     * @param circuitBreakerCondition hours of use for the circuit breaker
     * @return this electriclightrailbuilder.
     */
    @Override
    public VehicleBuilder addCircuitBreakerCondition(double circuitBreakerCondition) {
        this.circuitBreakerCondition = circuitBreakerCondition;
        return this;
    }
    
    /**
     * Method to create an ELR train with the attributes set in this builder.
     * 
     * @return a ElectricLightRailDTO with the attributes set in this builder.
     */
    @Override
    public ElectricLightRailDTO build() {
        return new ElectricLightRailDTO(vehicleNumber, vehicleName, vehicleType, fuelType, 
            consumptionRate, maxCapacity, currentRouteId, 
            axleBearingsCondition, brakesCondition, 
            wheelsCondition, status, latitude, 
            longitude, catenaryCondition, pantographCondition, circuitBreakerCondition);
    }
    
}