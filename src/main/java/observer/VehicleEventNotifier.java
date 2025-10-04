package observer;

import transferobjects.AlertDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Subject class in the observer pattern that maintains a list of listeners
 * and notifies them when vehicle events occur.
 * @author jaces
 */
public class VehicleEventNotifier {
    private final List<Listener> listeners = new ArrayList<>();

    /**
     * Adds a listener to be notified of vehicle events.
     *
     * @param listener the listener to add
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the notification list.
     *
     * @param listener the listener to remove
     */
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all registered listeners about a vehicle event.
     *
     * @param eventType the type of vehicle event that occurred
     * @param alert the AlertDTO containing details about the event
     */
    public void notifyListeners(VehicleEvent eventType, AlertDTO alert) {
        for (Listener listener : listeners) {
            listener.onAlert(eventType, alert);
        }
    }
}