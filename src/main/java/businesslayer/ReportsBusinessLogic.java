package businesslayer;

import dataaccesslayer.FuelDAO;
import dataaccesslayer.MaintenanceDAO;
import dataaccesslayer.TripDAO;
import dataaccesslayer.TripDAOImpl;
import dataaccesslayer.VehicleDAO;
import java.util.ArrayList;
import transferobjects.FuelDTO;
import transferobjects.MaintenanceDTO;
import transferobjects.TripDTO;
import java.util.List;
import java.util.Map;
import transferobjects.RouteDTO;

/**
 * Business logic class for generating and processing various reports
 * @author jaces
 */
public class ReportsBusinessLogic {
    private final MaintenanceDAO maintenanceDAO;
    private final TripDAO tripDAO;
    private final FuelDAO fuelDAO;
    private final VehicleDAO vehicleDAO;
    
    /**
     * Constructs a ReportsBusinessLogic instance with required DAO dependencies.
     *
     * @param maintenanceDAO data access object for maintenance records
     * @param tripDAO data access object for trip records
     * @param fuelDAO data access object for fuel records
     * @param vehicleDAO data access object for vehicle records
     * @throws IllegalArgumentException if any DAO parameter is null
     */
    public ReportsBusinessLogic(MaintenanceDAO maintenanceDAO, TripDAO tripDAO, 
            FuelDAO fuelDAO, VehicleDAO vehicleDAO) {
        if (maintenanceDAO == null || tripDAO == null || 
                fuelDAO == null || vehicleDAO == null) {
            throw new IllegalArgumentException("DAOs cannot be null");
        }
        this.maintenanceDAO = maintenanceDAO;
        this.tripDAO = tripDAO;
        this.fuelDAO = fuelDAO;
        this.vehicleDAO = vehicleDAO;
    }

    /**
     * Retrieves all maintenance records.
     *
     * @return list of all maintenance records
     */
    public List<MaintenanceDTO> getAllMaintenance() {
        return maintenanceDAO.getAllMaintenance();
    }

    /**
     * Retrieves maintenance records filtered by status.
     *
     * @param status the status to filter by (e.g., "PENDING", "COMPLETED")
     * @return list of maintenance records matching the status
     */
    public List<MaintenanceDTO> getMaintenanceByStatus(String status) {
        return maintenanceDAO.getMaintenanceByStatus(status);
    }

    /**
     * Calculates the total cost of all maintenance activities.
     *
     * @return total maintenance cost
     */
    public double getTotalMaintenanceCost() {
        return maintenanceDAO.getTotalMaintenanceCost();
    }
    
    /**
     * Retrieves all trip records.
     *
     * @return list of all trip records
     */
    public List<TripDTO> getAllTrips() {
        return tripDAO.getAllTrips();
    }

    /**
     * Calculates the percentage of trips completed on time.
     *
     * @return on-time completion percentage (0-100)
     */
    public double getOnTimePercentage() {
        return tripDAO.getOnTimePercentage();
    }

    /**
     * Calculates the average delay across all trips.
     *
     * @return average delay in minutes
     */
    public double getAverageDelay() {
        return tripDAO.getAverageDelay();
    }

    /**
     * Generates a fuel consumption report.
     *
     * @return list of fuel consumption records
     */
    public List<FuelDTO> getFuelConsumptionReport() {
        return fuelDAO.getFuelConsumptionReport();
    }

    /**
     * Calculates the average fuel efficiency across all vehicles.
     *
     * @return average fuel efficiency (distance per fuel unit)
     */
    public double getAverageFuelEfficiency() {
        return fuelDAO.getAverageFuelEfficiency();
    }

    /**
     * Calculates the total fuel cost based on current consumption and price.
     *
     * @param pricePerUnit the current price per fuel unit
     * @return total fuel cost
     */
    public double getTotalFuelCost(double pricePerUnit) {
        return fuelDAO.getTotalFuelUsed() * pricePerUnit;
    }
    
    /**
     * Retrieves trips grouped by operator.
     *
     * @return map of operator IDs to their associated trips
     */
    public Map<Integer, List<TripDTO>> getTripsByOperator() {
        return tripDAO.getTripsByOperator();
    }
    
    /**
     * Retrieves on-time percentages for all operators.
     *
     * @return map of operator IDs to their on-time percentages
     */
    public Map<Integer, Double> getOperatorOnTimePercentages() {
        return tripDAO.getOperatorOnTimePercentages();
    }
    
    /**
     * Retrieves trips for a specific operator.
     *
     * @param operatorId the ID of the operator
     * @return list of trips conducted by the specified operator
     */
    public List<TripDTO> getTripsForOperator(int operatorId) {
        Map<Integer, List<TripDTO>> allTrips = tripDAO.getTripsByOperator();
        return allTrips.getOrDefault(operatorId, new ArrayList<>());
    }
    
    /**
     * Retrieves the on-time percentage for a specific operator.
     *
     * @param operatorId the ID of the operator
     * @return on-time percentage for the specified operator
     */
    public double getOnTimePercentageForOperator(int operatorId) {
        Map<Integer, Double> allPercentages = tripDAO.getOperatorOnTimePercentages();
        return allPercentages.getOrDefault(operatorId, 0.0);
    }
    
    /**
     * Determines if a trip was completed on time.
     *
     * @param trip the trip to evaluate
     * @return true if the trip was on time, false otherwise
     */
    public boolean isTripOnTime(TripDTO trip) {
        RouteDTO route = vehicleDAO.getRouteById(trip.getRouteId());
        return TripDAOImpl.isTripOnTime(trip, route);
    }
}