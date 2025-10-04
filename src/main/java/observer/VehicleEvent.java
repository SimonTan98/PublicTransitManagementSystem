package observer;

/**
 * Enumeration of possible vehicle events that can be observed.
 * Defines the types of events that listeners can be notified about.
 * @author jaces
 */
public enum VehicleEvent {
    /** Event indicating a vehicle has reached the end of its route */
    ROUTE_END,
    
    /** Event indicating a vehicle has started a new route */
    ROUTE_START,
    
    /** Event indicating a vehicle requires maintenance */
    MAINTENANCE,
    
    /** Event indicating a vehicle has been involved in an accident */
    ACCIDENT,
    
    /** Event indicating a vehicle has been refueled */
    REFUEL
}