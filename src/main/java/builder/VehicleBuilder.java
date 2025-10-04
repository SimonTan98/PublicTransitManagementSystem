package builder;

import transferobjects.VehicleDTO;

/**
 * File Name: VehicleBuilder.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an abstract builder class for constructing vehicles.
 * 
 * @author Simon Tan
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public abstract class VehicleBuilder<E extends VehicleDTO> {
    
    /**
     * The vehicle ID
     */
    protected int vehicleNumber;
    
    /**
     * The vehicle name
     */
    protected String vehicleName;
    
    /**
     * The vehicle type.
     */
    protected String vehicleType;
    
    /**
     * The fuel type of the vehicle.
     */
    protected String fuelType;
    
    /**
     * The consumption rate of the vehicle.
     */
    protected double consumptionRate;
    
    /**
     * Max capacity of the vehicle.
     */
    protected int maxCapacity;
    
    /**
     * The ID of the current assigned route.
     */
    protected int currentRouteId;
    
    /**
     * Hours of use of the axle bearings.
     */
    protected double axleBearingsCondition;
    
    /**
     * Hours of use of the brakes.
     */
    protected double brakesCondition;
    
    /**
     * Hours of use of the wheels.
     */
    protected double wheelsCondition;
    
    /**
     * The status of the vehicle (ACTIVE, IN MAINTENANCE, etc...).
     */
    protected String status;
    
    /**
     * Current longitude of the vehicle
     */
    protected double longitude;
    
    /**
     * Current latitude of the vehicle.
     */
    protected double latitude;
    
    /**
     * The amount of fuel the vehicle has in the tank in litres.
     */
    protected double fuelLevel;

    /**
     * Setter for vehicle ID
     * 
     * @param vehicleNumber the vehicle ID
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        return this;
    }
    
    /**
     * Setter for vehicle name.
     * 
     * @param vehicleName the name of the vehicle.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addVehicleName(   String vehicleName) {
        this.vehicleName = vehicleName;
        return this;
    }

    /**
     * Setter for vehicle type.
     * 
     * @param vehicleType the type of the vehicle.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    /**
     * Setter for fuel type.
     * 
     * @param fuelType the fuel type of the vehicle.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addFuelType(String fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    /**
     * Setter for consumption rate
     * 
     * @param consumptionRate the consumption rate of the vehicle
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addConsumptionRate(double consumptionRate) {
        this.consumptionRate = consumptionRate;
        return this;
    }

    /**
     * Setter for max capacity
     * 
     * @param maxCapacity the max capacity of the vehicle
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        return this;
    }

    /**
     * Setter for current route id.
     * 
     * @param currentRouteId the id of the currently assigned route.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addCurrentRouteId(int currentRouteId) {
        this.currentRouteId = currentRouteId;
        return this;
    }

    /**
     * Setter for axle bearings
     * 
     * @param axleBearingsCondition the hours of use for axle bearings.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addAxleBearingsCondition(double axleBearingsCondition) {
        this.axleBearingsCondition = axleBearingsCondition;
        return this;
    }

    /**
     * Setter for brakes.
     * 
     * @param brakesCondition the hours of use for brakes.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addBrakesCondition(double brakesCondition) {
        this.brakesCondition = brakesCondition;
        return this;
    }
    
    /**
     * Setter for brakes
     * 
     * @param wheelsCondition the hours of use for wheels.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addWheelsCondition(double wheelsCondition) {
        this.wheelsCondition = wheelsCondition;
        return this;
    }

    /**
     * Setter for status.
     * 
     * @param status the current status of the vehicle.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Setter for longitude.
     * 
     * @param longitude the current longitude of the vehicle.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     * Setter for latitude.
     * 
     * @param latitude the current latitude of the vehicle.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }
    
    /**
     * Setter for fuel level.
     * 
     * @param fuelLevel the current fuel level of the vehicle.
     * @return this vehiclebuilder.
     */
    public VehicleBuilder addFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
        return this;
    }
    
    /**
     * Abstract method for setting emission rate
     * 
     * @param emissionRate the emission rate this vehicle
     * @return this vehiclebuilder.
     */
    public abstract VehicleBuilder addEmissionRate(double emissionRate);
    
    /**
     * Abstract method for setting oil life.
     * 
     * @param oilStatus the oil life percentage of this vehicle.
     * @return this vehiclebuilder.
     */
    public abstract VehicleBuilder addOilStatus(double oilStatus);
    
    /**
     * Abstract method for setting catenary condition.
     * 
     * @param catenaryCondition the hours of use for the catenary of this vehicle.
     * @return this vehiclebuilder.
     */
    public abstract VehicleBuilder addCatenaryCondition(double catenaryCondition);
    
    /**
     * Abstract method for setting pantograph condition.
     * 
     * @param pantographCondition the hours of use for the pantograph of this vehicle.
     * @return this vehiclebuilder.
     */
    public abstract VehicleBuilder addPantographCondition(double pantographCondition);
    
    /**
     * Abstract method for setting circuit breaker condition.
     * 
     * @param circuitBreakerCondition the hours of use for the circuit breaker of this vehicle.
     * @return this vehiclebuilder.
     */
    public abstract VehicleBuilder addCircuitBreakerCondition(double circuitBreakerCondition);
    
    /**
     * Abstract method for building the vehicle with the desired attributes.
     * 
     * @return a subclass of VehicleDTO.
     */
    public abstract E build();
    
}