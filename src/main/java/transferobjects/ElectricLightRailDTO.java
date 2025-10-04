
package transferobjects;

/**
 * Data Transfer Object for electric light rail.
 * Extends VehicleDTO with electric light rail-specific attributes.
 * @author Simon
 */
public class ElectricLightRailDTO extends VehicleDTO {
    private double catenaryCondition;
    private double pantographCondition;
    private double circuitBreakerCondition;
    
    /**
     * Default constructor.
     */
    public ElectricLightRailDTO() {
        super();
    }

    /**
     * Constructs an ElectricLightRailDTO with specified parameters.
     *
     * @param vehicleNumber the vehicle identification number
     * @param vehicleName the name of the vehicle
     * @param vehicleType the type of vehicle
     * @param fuelType the type of fuel used
     * @param consumptionRate the fuel consumption rate
     * @param maxCapacity the maximum passenger capacity
     * @param currentRouteId the ID of the current route
     * @param axleBearingsCondition number of hours used of axle bearings
     * @param brakesCondition number of hours used of brakes 
     * @param wheelsCondition number of hours used of wheels 
     * @param status current status of the vehicle
     * @param latitude current latitude position
     * @param longitude current longitude position
     * @param catenaryConditionnumber of hours used of catenary system 
     * @param pantographCondition number of hours used of pantograph
     * @param circuitBreakerCondition number of hours used of circuit breaker
     */
    public ElectricLightRailDTO(int vehicleNumber, String vehicleName, 
            String vehicleType, String fuelType, 
            double consumptionRate, int maxCapacity, int currentRouteId, 
            double axleBearingsCondition, double brakesCondition, 
            double wheelsCondition, String status, double latitude, 
            double longitude, double catenaryCondition, 
            double pantographCondition, double circuitBreakerCondition) {
        
        super(vehicleNumber, vehicleName, vehicleType, fuelType, 
            consumptionRate, maxCapacity, currentRouteId, 
            axleBearingsCondition, brakesCondition, 
            wheelsCondition, status, latitude, 
            longitude);
        
            this.catenaryCondition = catenaryCondition;
            this.pantographCondition = pantographCondition;
            this.circuitBreakerCondition = circuitBreakerCondition;
    }
    
    /**
     * @return the condition of catenary system (0-100%)
     */
    public double getCatenaryCondition() {
        return catenaryCondition;
    }

    /**
     * @param catenaryCondition the catenary condition to set
     */
    public void setCatenaryCondition(double catenaryCondition) {
        this.catenaryCondition = catenaryCondition;
    }

    /**
     * @return the condition of pantograph (0-100%)
     */
    public double getPantographCondition() {
        return pantographCondition;
    }

    /**
     * @param pantographCondition the pantograph condition to set
     */
    public void setPantographCondition(double pantographCondition) {
        this.pantographCondition = pantographCondition;
    }

    /**
     * @return the condition of circuit breaker (0-100%)
     */
    public double getCircuitBreakerCondition() {
        return circuitBreakerCondition;
    }

    /**
     * @param circuitBreakerCondition the circuit breaker condition to set
     */
    public void setCircuitBreakerCondition(double circuitBreakerCondition) {
        this.circuitBreakerCondition = circuitBreakerCondition;
    }
}
