package businesslayer;

import dataaccesslayer.AlertDAO;
import observer.VehicleEvent;
import observer.VehicleEventNotifier;
import transferobjects.AlertDTO;
import transferobjects.BusDTO;
import transferobjects.DieselTrainDTO;
import transferobjects.ElectricLightRailDTO;
import transferobjects.VehicleDTO;
import java.sql.Timestamp;
import java.util.List;

/**
 * Business logic class for handling vehicle alerts and monitoring vehicle conditions.
 * Implements monitoring logic for different vehicle types and manages alert notifications.
 * @author jaces
 */
public class VehicleAlertBusinessLogic {
    private final AlertDAO alertDAO;
    private final VehicleEventNotifier notifier;
    
    // Threshold constants for maintenance checks
    private static final int BRAKES_THRESHOLD = 100;
    private static final int WHEELS_THRESHOLD = 100;
    private static final int AXLE_BEARINGS_THRESHOLD = 100;
    private static final int ELECTRICAL_COMPONENTS_THRESHOLD = 100;
    private static final int OIL_CHANGE_THRESHOLD = 100;
    private static final int EMISSION_CHECK_THRESHOLD = 100;
    private static final int FUEL_LEVEL_THRESHOLD = 100;
    
    /**
     * Constructs a VehicleAlertBusinessLogic instance with required dependencies.
     *
     * @param alertDAO data access object for alert records
     * @param notifier event notifier for alert notifications
     * @throws IllegalArgumentException if any parameter is null
     */
    public VehicleAlertBusinessLogic(AlertDAO alertDAO, VehicleEventNotifier notifier) {
        if (alertDAO == null || notifier == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.alertDAO = alertDAO;
        this.notifier = notifier;
    }
    
    /**
     * Monitors a vehicle's condition and generates alerts as needed.
     *
     * @param vehicle the vehicle to monitor
     * @throws IllegalArgumentException if vehicle is null
     */
    public void monitorVehicle(VehicleDTO vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        
        try {
            checkCommonComponents(vehicle);
            checkFuelLevel(vehicle);
            if (vehicle instanceof ElectricLightRailDTO) {
                checkElectricLightRailComponents((ElectricLightRailDTO) vehicle);
            } else if (vehicle instanceof DieselTrainDTO) {
                checkDieselTrainComponents((DieselTrainDTO) vehicle);
            } else if (vehicle instanceof BusDTO) {
                checkBusComponents((BusDTO) vehicle);
            }
            
            checkRouteCompletion(vehicle);
        } catch (Exception e) {
            System.err.println("Error monitoring vehicle: " + e.getMessage());
        }
    }
    
    /**
     * Checks common vehicle components that apply to all vehicle types.
     *
     * @param vehicle the vehicle to check
     */
    private void checkCommonComponents(VehicleDTO vehicle) {
        StringBuilder reason = new StringBuilder();
        boolean needsMaintenance = false;
        
        if (vehicle.getBrakesCondition() >= BRAKES_THRESHOLD) {
            reason.append("Brakes have reached service interval (").append(vehicle.getBrakesCondition()).append(" hours). ");
            needsMaintenance = true;
        }
        
        if (vehicle.getWheelsCondition() >= WHEELS_THRESHOLD) {
            reason.append("Wheels have reached service interval (").append(vehicle.getWheelsCondition()).append(" hours). ");
            needsMaintenance = true;
        }
        
        if (vehicle.getAxleBearingsCondition() >= AXLE_BEARINGS_THRESHOLD) {
            reason.append("Axle bearings have reached service interval (").append(vehicle.getAxleBearingsCondition()).append(" hours). ");
            needsMaintenance = true;
        }
        
        if (needsMaintenance) {
            createAndNotifyAlert(
                vehicle.getVehicleID(),
                VehicleEvent.MAINTENANCE,
                reason.toString()
            );
        }
    }
    
    /**
     * Checks components specific to electric light rail vehicles.
     *
     * @param elr the electric light rail vehicle to check
     */
    private void checkElectricLightRailComponents(ElectricLightRailDTO elr) {
        StringBuilder reason = new StringBuilder();
        boolean needsMaintenance = false;
        
        if (elr.getCatenaryCondition() >= ELECTRICAL_COMPONENTS_THRESHOLD) {
            reason.append("Catenary component have reached service interval (").append(elr.getCatenaryCondition()).append(" hours). ");
            needsMaintenance = true;
        } 
        if (elr.getPantographCondition() >= ELECTRICAL_COMPONENTS_THRESHOLD) {
            reason.append("Pantograph component have reached service interval (").append(elr.getCatenaryCondition()).append(" hours). ");
            needsMaintenance = true;
        }
        if (elr.getCircuitBreakerCondition() >= ELECTRICAL_COMPONENTS_THRESHOLD) {
            reason.append("Circuit breaker component have reached service interval (").append(elr.getCatenaryCondition()).append(" hours). ");
            needsMaintenance = true;
        }
        
        if (needsMaintenance) {
            createAndNotifyAlert(
                elr.getVehicleID(),
                VehicleEvent.MAINTENANCE,
                "Electrical system: " + reason.toString()
            );
        }
    }
    
    /**
     * Checks components specific to diesel train vehicles.
     *
     * @param train the diesel train vehicle to check
     */
    private void checkDieselTrainComponents(DieselTrainDTO train) {
        if (train.getOilStatus() >= OIL_CHANGE_THRESHOLD) {
            createAndNotifyAlert(
                train.getVehicleID(),
                VehicleEvent.MAINTENANCE,
                "Oil change required (" + train.getOilStatus() + " hours since last change)"
            );
        }
    }
    
    /**
     * Checks components specific to bus vehicles.
     *
     * @param bus the bus vehicle to check
     */
    private void checkBusComponents(BusDTO bus) {
        if (bus.getEmissionRate() >= EMISSION_CHECK_THRESHOLD) {
            createAndNotifyAlert(
                bus.getVehicleID(),
                VehicleEvent.MAINTENANCE,
                "Emission system check required (" + bus.getEmissionRate() + " hours since last check)"
            );
        }
    }
    
    /**
     * Checks if a vehicle has completed its route and generates an alert if so.
     *
     * @param vehicle the vehicle to check
     */
    private void checkRouteCompletion(VehicleDTO vehicle) {
        if ("COMPLETED".equalsIgnoreCase(vehicle.getStatus())) {
            createAndNotifyAlert(
                vehicle.getVehicleID(),
                VehicleEvent.ROUTE_END,
                "Vehicle has completed its route " + vehicle.getCurrentRouteID()
            );
        }
    }
    
    /**
     * Checks the fuel level of a vehicle and generates an alert if low.
     *
     * @param vehicle the vehicle to check
     */
    private void checkFuelLevel(VehicleDTO vehicle) {
        if (vehicle.getFuelLevel() <= FUEL_LEVEL_THRESHOLD) {
             createAndNotifyAlert(
                vehicle.getVehicleID(),
                VehicleEvent.REFUEL,
                "Vehicle needs refuel. Fuel level at " + vehicle.getFuelLevel()
            );
        }
    }
    
    /**
     * Creates a new alert and notifies registered listeners.
     *
     * @param vehicleID the ID of the vehicle associated with the alert
     * @param eventType the type of vehicle event
     * @param reason the reason/description for the alert
     */
    private void createAndNotifyAlert(int vehicleID, VehicleEvent eventType, String reason) {
        AlertDTO alert = new AlertDTO();
        alert.setVehicleID(vehicleID);
        alert.setAlertType(eventType.name());
        alert.setAlertReason(reason);
        alert.setAlertTime(new Timestamp(System.currentTimeMillis()));
        alert.setStatus("ACTIVE");
        
        try {
            alertDAO.addAlert(alert);
            notifier.notifyListeners(eventType, alert);
        } catch (Exception e) {
            System.err.println("Failed to create or notify alert: " + e.getMessage());
        }
    }
    
    /**
     * Processes an alert by updating its status and potentially notifying listeners.
     *
     * @param alertID the ID of the alert to process
     * @param action the action to take ("ACKNOWLEDGE", "RESOLVE", or "ESCALATE")
     * @throws IllegalArgumentException if parameters are invalid
     * @throws RuntimeException if alert processing fails
     */
    public void processAlert(int alertID, String action) {
        if (alertID <= 0) {
            throw new IllegalArgumentException("Invalid alert ID");
        }
        if (action == null || action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action cannot be null or empty");
        }
        
        try {
            AlertDTO alert = alertDAO.getAlertById(alertID);
            if (alert == null) {
                throw new IllegalArgumentException("Alert not found with ID: " + alertID);
            }
            
            String newStatus;
            switch (action.toUpperCase()) {
                case "ACKNOWLEDGE":
                    newStatus = "ACKNOWLEDGED";
                    break;
                case "RESOLVE":
                    newStatus = "RESOLVED";
                    break;
                case "ESCALATE":
                    newStatus = "ESCALATED";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid action: " + action);
            }
            
            alert.setStatus(newStatus);
            alertDAO.updateAlert(alert);
            
            if (!"RESOLVED".equals(newStatus)) {
                VehicleEvent eventType = VehicleEvent.valueOf(alert.getAlertType());
                notifier.notifyListeners(eventType, alert);
            }
        } catch (Exception e) {
            System.err.println("Error processing alert: " + e.getMessage());
            throw new RuntimeException("Failed to process alert", e);
        }
    }
    
    /**
     * Retrieves all active alerts.
     *
     * @return list of active alerts
     */
    public List<AlertDTO> getActiveAlerts() {
        return alertDAO.getActiveAlerts();
    }
    
    /**
     * Retrieves alerts for a specific vehicle.
     *
     * @param vehicleID the ID of the vehicle
     * @return list of alerts associated with the vehicle
     */
    public List<AlertDTO> getAlertsByVehicle(int vehicleID) {
        return alertDAO.getAlertsByVehicle(vehicleID);
    }
    
    /**
     * Retrieves alerts of a specific type.
     *
     * @param alertType the type of alert to filter by
     * @return list of alerts matching the type
     */
    public List<AlertDTO> getAlertsByType(String alertType) {
        return alertDAO.getAlertsByType(alertType);
    }
    
    /**
     * Retrieves all alerts.
     *
     * @return list of all alerts
     */
    public List<AlertDTO> getAllAlerts() {
        return alertDAO.getAllAlerts();
    }
}