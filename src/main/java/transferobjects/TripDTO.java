package transferobjects;

import java.sql.Timestamp;

/**
 * Data Transfer Object for trip.
 * Contains details about vehicle trips including timing and fuel usage.
 * @author Simon
 */
public class TripDTO {
    
    private int tripId;
    private int userId;
    private int vehicleId;
    private int routeId;
    private Timestamp startTime;
    private Timestamp endTime;
    private double fuelUsed;
    private double fuelEfficiency;
    private boolean onTime;

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
     * @return the user ID who conducted the trip
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the vehicle ID used for the trip
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
     * @return the route ID for the trip
     */
    public int getRouteId() {
        return routeId;
    }

    /**
     * @param routeId the route ID to set
     */
    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    /**
     * @return the trip start time
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the trip start time to set
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the trip end time
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the trip end time to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the amount of fuel used during the trip
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
     * @return the fuel efficiency achieved during the trip
     */
    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    /**
     * @param fuelEfficiency the fuel efficiency to set
     */
    public void setFuelEfficiency(double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    /**
     * @return true if the trip was completed on time, false otherwise
     */
    public boolean isOnTime() {
        return onTime;
    }

    /**
     * @param onTime the on-time status to set
     */
    public void setOnTime(boolean onTime) {
        this.onTime = onTime;
    }
}
