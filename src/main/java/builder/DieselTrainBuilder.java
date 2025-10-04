package builder;

import transferobjects.DieselTrainDTO;

/**
 * File Name: DieselTrainBuilder.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an abstract builder class for constructing diesel trains.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class DieselTrainBuilder extends VehicleBuilder<DieselTrainDTO> {
    
    /**
     * The oil life of this diesel train.
     */
    private double oilStatus;
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param emissionRate not used in this class.
     * @return this dieseltrainbuilder.
     */
    @Override
    public VehicleBuilder addEmissionRate(double emissionRate) {
        return this;
    }
    
    /**
     * Setter for oil life.
     * 
     * @param oilStatus the oil life percentage of this diesel train.
     * @return this dieseltrainbuilder.
     */
    @Override
    public VehicleBuilder addOilStatus(double oilStatus) {
        this.oilStatus = oilStatus;
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param catenaryCondition not used in this class.
     * @return this dieseltrainbuilder.
     */
    @Override
    public VehicleBuilder addCatenaryCondition(double catenaryCondition) {
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param pantographCondition not used in this class.
     * @return this dieseltrainbuilder.
     */
    @Override
    public VehicleBuilder addPantographCondition(double pantographCondition) {
        return this;
    }
    
    /**
     * Empty method that does not affect this builder.
     * 
     * @param circuitBreakerCondition not used in this class.
     * @return this dieseltrainbuilder.
     */
    @Override
    public VehicleBuilder addCircuitBreakerCondition(double circuitBreakerCondition) {
        return this;
    }
    
    /**
     * Method to create a diesel train with the attributes set in this builder.
     * 
     * @return a DieselTrainDTO with the attributes set in this builder.
     */
    @Override
    public DieselTrainDTO build() {
        return new DieselTrainDTO(vehicleNumber, vehicleName, vehicleType, fuelType, 
            consumptionRate, maxCapacity, currentRouteId, 
            axleBearingsCondition, brakesCondition, 
            wheelsCondition, status, latitude, 
            longitude, oilStatus);
    }
    
}
