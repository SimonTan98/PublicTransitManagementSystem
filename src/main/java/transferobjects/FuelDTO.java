package transferobjects;


/**
 * Data Transfer Object for fuel information.
 * Contains details about fuel usage and efficiency for vehicles.
 * @author Simon
 */
public class FuelDTO {
    
    private int vehicleId;
    private String vehicleName;
    private String vehicleType;
    private int tripId;
    private double fuelUsed;
    private double expectedFuelEfficiency;
    private double actualFuelEfficiency;

    /**
     * @return the vehicle ID
     */
    public int getVehicleId() {
        return vehicleId;
    }

    /**
     * @param vehicleId the vehicle ID to set
     */
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * @return the vehicle name
     */
    public String getVehicleName() {
        return vehicleName;
    }

    /**
     * @param vehicleName the vehicle name to set
     */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    /**
     * @return the vehicle type
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType the vehicle type to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @return the trip ID
     */
    public int getTripId() {
        return tripId;
    }

    /**
     * @param tripId the trip ID to set
     */
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    /**
     * @return the expected fuel efficiency
     */
    public double getExpectedFuelEfficiency() {
        return expectedFuelEfficiency;
    }

    /**
     * @param expectedFuelEfficiency the expected fuel efficiency to set
     */
    public void setExpectedFuelEfficiency(double expectedFuelEfficiency) {
        this.expectedFuelEfficiency = expectedFuelEfficiency;
    }

    /**
     * @return the actual fuel efficiency
     */
    public double getActualFuelEfficiency() {
        return actualFuelEfficiency;
    }

    /**
     * @param actualFuelEfficiency the actual fuel efficiency to set
     */
    public void setActualFuelEfficiency(double actualFuelEfficiency) {
        this.actualFuelEfficiency = actualFuelEfficiency;
    }

    /**
     * @return the amount of fuel used
     */
    public double getFuelUsed() {
        return fuelUsed;
    }

    /**
     * @param fuelUsed the amount of fuel used to set
     */
    public void setFuelUsed(double fuelUsed) {
        this.fuelUsed = fuelUsed;
    }

    /**
     * @return the total fuel used (not implemented)
     * @throws UnsupportedOperationException as this method is not implemented
     */
    public double getTotalFuelUsed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}