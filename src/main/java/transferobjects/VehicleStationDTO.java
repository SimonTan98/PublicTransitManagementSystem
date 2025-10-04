package transferobjects;

import java.sql.Timestamp;

/**
 * Data Transfer Object for vehicle-station relationship.
 * Contains details about vehicle arrivals and departures at stations.
 * @author Simon
 */
public class VehicleStationDTO {
    private int vehicleId;
    private int stationId;
    private Timestamp arrivalTime;
    private Timestamp departureTime;

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
     * @return the station ID
     */
    public int getStationId() {
        return stationId;
    }

    /**
     * @param stationId the station ID to set
     */
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    /**
     * @return the arrival time at station
     */
    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the departure time from station
     */
    public Timestamp getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }
}