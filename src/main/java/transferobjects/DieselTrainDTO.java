
package transferobjects;

/**
 * Data Transfer Object for diesel train.
 * Extends VehicleDTO with diesel train-specific attributes like oil status.
 * @author Simon
 */
public class DieselTrainDTO extends VehicleDTO {
    
    private double oilStatus;

    /**
     * Default constructor.
     */
    public DieselTrainDTO() {
        super();
    }
    
    /**
     * Constructs a DieselTrainDTO with specified parameters.
     *
     * @param vehicleNumber the vehicle identification number
     * @param vehicleName the name of the vehicle
     * @param vehicleType the type of vehicle
     * @param fuelType the type of fuel used
     * @param consumptionRate the fuel consumption rate
     * @param maxCapacity the maximum passenger capacity
     * @param currentRouteId the ID of the current route
     * @param axleBearingsCondition number of hours used of bearings 
     * @param brakesCondition number of hours used of brakes 
     * @param wheelsCondition number of hours used of wheels 
     * @param status current status of the vehicle
     * @param latitude current latitude position
     * @param longitude current longitude position
     * @param oilStatus the current oil status/level
     */
    public DieselTrainDTO(int vehicleNumber, String vehicleName, 
            String vehicleType, String fuelType, 
            double consumptionRate, int maxCapacity, int currentRouteId, 
            double axleBearingsCondition, double brakesCondition, 
            double wheelsCondition, String status, double latitude, 
            double longitude, double oilStatus) {
        
        super(vehicleNumber, vehicleName, vehicleType, fuelType, 
            consumptionRate, maxCapacity, currentRouteId, 
            axleBearingsCondition, brakesCondition, 
            wheelsCondition, status, latitude, 
            longitude);
        
            this.oilStatus = oilStatus;
    }
    
    /**
     * @return the current oil status/level
     */
    public double getOilStatus() {
        return oilStatus;
    }

    /**
     * @param oilStatus the oil status to set
     */
    public void setOilStatus(Double oilStatus) {
        this.oilStatus = oilStatus;
    }
}
