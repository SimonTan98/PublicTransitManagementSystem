package transferobjects;

import java.sql.Timestamp;

/**
 * Data Transfer Object for maintenance.
 * Contains details about vehicle maintenance activities.
 * @author Simon
 */
public class MaintenanceDTO {
    
    private int maintenanceId;
    private int vehicleId;
    private String purpose;
    private double cost;
    private Timestamp startTime;
    private Timestamp endTime;

    /**
     * @return the maintenance ID
     */
    public int getMaintenanceId() {
        return maintenanceId;
    }

    /**
     * @param maintenanceId the maintenance ID to set
     */
    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
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
     * @return the purpose/description of maintenance
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose the maintenance purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the cost of maintenance
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the maintenance cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * @return the start time of maintenance
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the maintenance start time to set
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the end time of maintenance
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the maintenance end time to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}