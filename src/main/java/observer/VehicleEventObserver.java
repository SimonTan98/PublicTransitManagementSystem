package observer;

import transferobjects.AlertDTO;

/**
 * Concrete implementation of the Listener interface that observes vehicle events.
 * Prints notifications to the console when vehicle events occur.
 * @author jaces
 */
public class VehicleEventObserver implements Listener {
    /**
     * Handles vehicle alert events by formatting and printing a notification message.
     *
     * @param eventType the type of vehicle event that occurred
     * @param alert the AlertDTO containing details about the alert
     */
    @Override
    public void onAlert(VehicleEvent eventType, AlertDTO alert) {
        String message = String.format(
            "[Notification] %s: Vehicle %d - %s (Time: %s)",
            eventType.name(),                 
            alert.getVehicleID(),              
            alert.getAlertReason(),           
            alert.getAlertTime()               
        );
        System.out.println(message);
    }
}