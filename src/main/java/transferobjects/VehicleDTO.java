package transferobjects;

/**
 * Data Transfer Object for vehicle.
 * Contains common attributes for all vehicle types.
 * @author Simon Tan
 * @author jaces
 */
public class VehicleDTO {
    private int vehicleNumber;
    private String vehicleName;
    private String vehicleType;
    private String fuelType;
    private double consumptionRate;
    private int maxCapacity;
    private int currentRouteId;
    private double axleBearingsCondition;
    private double brakesCondition;
    private double wheelsCondition;
    private String status; 
    private double latitude;
    private double longitude;
    private double fuelLevel;
    
    /**
     * Default constructor.
     */
    public VehicleDTO() {}

    /**
     * Constructs a VehicleDTO with specified parameters.
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
     */
    public VehicleDTO(int vehicleNumber, String vehicleName, String vehicleType, 
            String fuelType, double consumptionRate, int maxCapacity, 
            int currentRouteId, double axleBearingsCondition, double brakesCondition, 
            double wheelsCondition, String status, double latitude, 
            double longitude) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.consumptionRate = consumptionRate;
        this.maxCapacity = maxCapacity;
        this.currentRouteId = currentRouteId;
        this.axleBearingsCondition = axleBearingsCondition;
        this.brakesCondition = brakesCondition;
        this.wheelsCondition = wheelsCondition;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * @return the vehicle ID
     */
    public int getVehicleID() { return vehicleNumber; }
    
    /**
     * @param vehicleNumber the vehicle ID to set
     */
    public void setVehicleID(int vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    
    /**
     * @return the vehicle name
     */
    public String getVehicleName() { return vehicleName; }
    
    /**
     * @param vehicleName the vehicle name to set
     */
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }
    
    /**
     * @return the vehicle type
     */
    public String getVehicleType() { return vehicleType; }
    
    /**
     * @param vehicleType the vehicle type to set
     */
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    
    /**
     * @return the fuel type
     */
    public String getFuelType() { return fuelType; }
    
    /**
     * @param fuelType the fuel type to set
     */
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    
    /**
     * @return the fuel consumption rate
     */
    public double getConsumptionRate() { return consumptionRate; }
    
    /**
     * @param consumptionRate the consumption rate to set
     */
    public void setConsumptionRate(double consumptionRate) { this.consumptionRate = consumptionRate; }
    
    /**
     * @return the maximum passenger capacity
     */
    public int getMaxCapacity() { return maxCapacity; }
    
    /**
     * @param maxCapacity the maximum capacity to set
     */
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    
    /**
     * @return the current route ID
     */
    public int getCurrentRouteID() { return currentRouteId; }
    
    /**
     * @param currentRouteId the current route ID to set
     */
    public void setCurrentRouteID(int currentRouteId) { this.currentRouteId = currentRouteId; }
    
    /**
     * @return the number of hours used of axle bearings (0-100%)
     */
    public double getAxleBearingsCondition() { return axleBearingsCondition; }
    
    /**
     * @param axleBearingsCondition the the number of hours used of axle bearings  to set
     */
    public void setAxleBearingsCondition(double axleBearingsCondition) { this.axleBearingsCondition = axleBearingsCondition; }
    
    /**
     * @return the number of hours used of brakes (0-100%)
     */
    public double getBrakesCondition() { return brakesCondition; }
    
    /**
     * @param brakesCondition the number of hours used of brakes to set
     */
    public void setBrakesCondition(double brakesCondition) { this.brakesCondition = brakesCondition; }
    
    /**
     * @return the number of hours used of wheels (0-100%)
     */
    public double getWheelsCondition() { return wheelsCondition; }
    
    /**
     * @param wheelsCondition the number of hours used of wheels to set
     */
    public void setWheelsCondition(double wheelsCondition) { this.wheelsCondition = wheelsCondition; }
    
    /**
     * @return the current status of the vehicle
     */
    public String getStatus() { return status; }
    
    /**
     * @param status the vehicle status to set
     */
    public void setStatus(String status) { this.status = status; }
    
    /**
     * @return the current latitude position
     */
    public double getLatitude() { return latitude; }
    
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    /**
     * @return the current longitude position
     */
    public double getLongitude() { return longitude; }
    
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) { this.longitude = longitude; }
    
    /**
     * @return the current fuel level
     */
    public double getFuelLevel() { return fuelLevel; }
    
    /**
     * @param fuelLevel the fuel level to set
     */
    public void setFuelLevel(double fuelLevel) { this.fuelLevel = fuelLevel; }
}