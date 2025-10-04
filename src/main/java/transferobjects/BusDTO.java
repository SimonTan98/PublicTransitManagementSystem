package transferobjects;

/**
 * Data Transfer Object for bus.
 * Extends VehicleDTO with bus-specific attributes like emission rate.
 * @author Simon
 */
public class BusDTO extends VehicleDTO {
    
    /**
     * Default constructor.
     */
    public BusDTO() {
        super();
    }
    
    /**
     * Constructs a BusDTO with specified parameters.
     *
     * @param vehicleNumber the vehicle identification number
     * @param vehicleName the name of the vehicle
     * @param vehicleType the type of vehicle
     * @param fuelType the type of fuel used
     * @param consumptionRate the fuel consumption rate
     * @param maxCapacity the maximum passenger capacity
     * @param currentRouteId the ID of the current route
     * @param axleBearingsCondition number of hours the brakes are used since maintenance
     * @param brakesCondition number of hours the brakes are used since maintenance
     * @param wheelsCondition number of hours the brakes are used since maintenance
     * @param status current status of the vehicle
     * @param latitude current latitude position
     * @param longitude current longitude position
     * @param emissionRate the emission rate of the vehicle
     */
    public BusDTO(int vehicleNumber, String vehicleName, 
            String vehicleType, String fuelType, 
            double consumptionRate, int maxCapacity, int currentRouteId, 
            double axleBearingsCondition, double brakesCondition, 
            double wheelsCondition, String status, double latitude, 
            double longitude, double emissionRate) {
        
        super(vehicleNumber, vehicleName, vehicleType, fuelType, 
            consumptionRate, maxCapacity, currentRouteId, 
            axleBearingsCondition, brakesCondition, 
            wheelsCondition, status, latitude, 
            longitude);
        
            this.emissionRate = emissionRate;
    }
    
    private Double emissionRate;       

    /**
     * @return the emission rate of the bus
     */
    public Double getEmissionRate() {
        return emissionRate;
    }

    /**
     * @param emissionRate the emission rate to set
     */
    public void setEmissionRate(Double emissionRate) {
        this.emissionRate = emissionRate;
    }
}