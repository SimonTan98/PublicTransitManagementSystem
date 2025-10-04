package transferobjects;

import java.sql.Timestamp;

/**
 * Data Transfer Object for alerts.
 * Contains details about vehicle alerts including type, reason, and status.
 * @author jaces
 * 
 */
public class AlertDTO {
    private int alertID;
    private int vehicleID;
    private String status;  
    private String alertType; 
    private String alertReason;
    private Timestamp alertTime;

    /**
     * Default constructor.
     */
    public AlertDTO() {}

    /**
     * Constructs an AlertDTO with specified parameters.
     *
     * @param alertID the unique identifier for the alert
     * @param vehicleID the ID of the vehicle associated with the alert
     * @param status the current status of the alert
     * @param alertType the type/category of the alert
     * @param alertReason the reason/description of the alert
     * @param alertTime the timestamp when the alert was generated
     */
    public AlertDTO(int alertID, int vehicleID, String status, String alertType, 
                   String alertReason, Timestamp alertTime) {
        this.alertID = alertID;
        this.vehicleID = vehicleID;
        this.status = status;
        this.alertType = alertType;
        this.alertReason = alertReason;
        this.alertTime = alertTime;
    }

    /**
     * @return the alert ID
     */
    public int getAlertID() { return alertID; }
    
    /**
     * @param alertID the alert ID to set
     */
    public void setAlertID(int alertID) { this.alertID = alertID; }

    /**
     * @return the vehicle ID associated with the alert
     */
    public int getVehicleID() { return vehicleID; }
    
    /**
     * @param vehicleID the vehicle ID to set
     */
    public void setVehicleID(int vehicleID) { this.vehicleID = vehicleID; }

    /**
     * @return the current status of the alert
     */
    public String getStatus() { return status; }
    
    /**
     * @param status the alert status to set
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * @return the type/category of the alert
     */
    public String getAlertType() { return alertType; }
    
    /**
     * @param alertType the alert type to set
     */
    public void setAlertType(String alertType) { this.alertType = alertType; }

    /**
     * @return the reason/description of the alert
     */
    public String getAlertReason() { return alertReason; }
    
    /**
     * @param alertReason the alert reason to set
     */
    public void setAlertReason(String alertReason) { this.alertReason = alertReason; }

    /**
     * @return the timestamp when the alert was generated
     */
    public Timestamp getAlertTime() { return alertTime; }
    
    /**
     * @param alertTime the alert timestamp to set
     */
    public void setAlertTime(Timestamp alertTime) { this.alertTime = alertTime; }
}