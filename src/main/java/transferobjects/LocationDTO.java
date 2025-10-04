
package transferobjects;

import java.sql.Timestamp;

/**
 * Data Transfer Object for location.
 * Contains details about vehicle locations including coordinates and timestamps.
 * @author Simon
 */
public class LocationDTO {
    private int locationId;
    private int vehicleId;
    private double latitude;
    private double longitude;
    private Timestamp updated;
    private boolean mostRecent;

    /**
     * @return the location ID
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the location ID to set
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

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
     * @return the latitude coordinate
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude coordinate
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the timestamp when location was updated
     */
    public Timestamp getUpdated() {
        return updated;
    }

    /**
     * @param updated the update timestamp to set
     */
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    /**
     * @return true if this is the most recent location, false otherwise
     */
    public boolean isMostRecent() {
        return mostRecent;
    }

    /**
     * @param mostRecent flag indicating if this is the most recent location
     */
    public void setMostRecent(boolean mostRecent) {
        this.mostRecent = mostRecent;
    }
}
