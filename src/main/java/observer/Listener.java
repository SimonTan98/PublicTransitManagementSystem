package observer;

import transferobjects.AlertDTO;

/**
 * Interface for objects that listen to vehicle events.
 * Implementations of this interface will receive notifications when vehicle events occur.
 * @author jaces
 */
public interface Listener {
    /**
     * Called when a vehicle alert event occurs.
     *
     * @param eventType the type of vehicle event that occurred
     * @param alert the AlertDTO containing details about the alert
     */
    void onAlert(VehicleEvent eventType, AlertDTO alert);
}