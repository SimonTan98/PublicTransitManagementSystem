package dataaccesslayer;

import transferobjects.FuelDTO;
import java.util.List;
import transferobjects.VehicleDTO;
import transferobjects.BusDTO;
import transferobjects.DieselTrainDTO;
import transferobjects.ElectricLightRailDTO;
import transferobjects.LocationDTO;
import transferobjects.MaintenanceDTO;
import transferobjects.RouteDTO;
import transferobjects.StationDTO;
import transferobjects.TripDTO;
import transferobjects.VehicleStationDTO;

/**
 * File Name: VehicleDAO.java
 * Author: Simon Tan, 041161622
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * This class serves as an interface for defining DAO methods related to 
 * vehicles and maintenance. 
 * 
 * @author Simon Tan
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public interface VehicleDAO {
    
    /**
     * Defines a method to add a bus to the database.
     * 
     * @param bus a BusDTO containing the information of the new bus.
     * @return true if the bus is successfully added, else false.
     */
    boolean addBus(BusDTO bus);
    
    /**
     * Defines a method to add a Diesel Train to the database.
     * 
     * @param dieselTrain a DieselTrainDTO containing the information of the new train.
     * @return true if the train is successfully added, else false.
     */
    boolean addDieselTrain(DieselTrainDTO dieselTrain);
    
    /**
     * Defines a method to add a Electric Light Rail Train to the database.
     * 
     * @param elrTrain am ElectricLightRailDTO containing the information of the new train.
     * @return true if the train is successfully added, else false.
     */
    boolean addElectricLightRail(ElectricLightRailDTO elrTrain);
    
    /**
     * Defines a method to update a vehicle's information in the database.
     * 
     * @param vehicle a VehicleDTO containing the new information for the vehicle.
     * @return true if the vehicle is successfully updated, else false.
     */
    boolean updateVehicle(VehicleDTO vehicle);
    
    /**
     * Defines a method to update the oil life for a Diesel Train.
     * 
     * @param train a DieselTrainDTO containing the new oil life value.
     * @return true if the diesel train is successfully updated, else false.
     */
    boolean updateOilLife(DieselTrainDTO train);
    
    /**
     * Defines a method to update the unique components for Electric Light Rail 
     * trains.
     * 
     * @param elrTrain an ElectricLightRailDTO containing the new values for the ELR components.
     * @return true if the ELR train was successfully added, else false.
     */
    boolean updateELRComponents(ElectricLightRailDTO elrTrain);
    
    /**
     * Defines a method to retrieve a vehicle's information using a given
     * vehicle ID.
     * 
     * @param vehicleID an int representing the vehicle ID.
     * @return a VehicleDTO that contains the information of the retrieved vehicle.
     */
    VehicleDTO getVehicleById(int vehicleID);
    
    /**
     * Defines a method to get a list of all vehicles.
     * 
     * @return a List of VehicleDTOs containing all the vehicle information from
     * the database
     */
    List<VehicleDTO> getAllVehicles();
    
    /**
     * Defines a method to get a list of all vehicles of a given vehicle type.
     * 
     * @param vehicleType a String representing the desired vehicle type
     * @return a List of VehicleDTOs containing all the vehicle information from
     * the database of a given vehicle type.
     */
    List<VehicleDTO> getVehiclesByType(String vehicleType);
    
    /**
     * Defines a method to retrieve a list of all routes in the database.
     * 
     * @return a List of RouteDTOs containing the information of all routes.
     */
    List<RouteDTO> getAllRoutes();
    
    /**
     * Defines a method to get the information of a specific route given a 
     * route ID.
     * 
     * @param routeId an int representing the desired route's Id.
     * @return a RouteDTO containing the information of the desired route.
     */
    RouteDTO getRouteById(int routeId);
    
    /**
     * Defines a method to retrieve all station information from the database.
     * 
     * @return a List of StationDTOs containing the information of all stations.
     */
    List<StationDTO> getAllStations();
    
    /**
     * Defines a method to log a vehicle's visit to a station in the database.
     * 
     * @param visit a VehicleStationDTO containing the information of the vehicle's
     * visit to the station.
     * @return true if the visit was successfully added, else false.
     */
    boolean logStationVisit(VehicleStationDTO visit);
    
    /**
     * Defines a method to get the history of station visits for a given
     * vehicle.
     * 
     * @param vehicleId an int representing the desired vehicle's ID
     * @return a List of VehicleStationDTOs containing the information
     * of all station visits of the desired vehicle.
     */
    List<VehicleStationDTO> getStationVisitsByVehicleId(int vehicleId);
    
    /**
     * Defines a method to start maintenance for a vehicle.
     * 
     * @param maintenance a MaintenanceDTO that contains the maintenance information.
     * @return true if the maintenance is successfully added in the database, else false.
     */
    boolean startMaintenance(MaintenanceDTO maintenance);
    
    /**
     * Defines a method to end maintenance for a vehicle.
     * 
     * @param vehicleId an int representing the vehicle's ID.
     * @return true if maintenance is successfully ended for a vehicle, else false.
     */
    boolean endMaintenance(int vehicleId);
    
    /**
     * Defines a method to refresh the oil life of a diesel train.
     * 
     * @param vehicleId an int representing the diesel train's ID.
     * @return true if the oil life is successfully updated, else false.
     */
    boolean refreshOilLife(int vehicleId);
    
    /**
     * Defines a method to reset the hours of use for Electric Light Rail components.
     * 
     * @param vehicleId an int representing the ELR train's ID.
     * @return true if the components are successfully updated, else false.
     */
    boolean refreshELRComponents(int vehicleId);
    
    /**
     * Defines a method to update a vehicle's location.
     * 
     * @param location a LocationDTO containing the new coordinates for a vehicle.
     * @return true if the vehicle's location is successfully updated, else false.
     */
    boolean updateLocation(LocationDTO location);
    
    /**
     * Defines a method to get all vehicle locations from the database.
     * 
     * @return a List of LocationDTOs containing the location data from the database.
     */
    List<LocationDTO> getAllVehicleLocations();
    
    /**
     * Defines a method to update a vehicle's status.
     * 
     * @param vehicleId an int representing the vehicle's ID.
     * @param vehicleStatus a String representing the new status of the vehicle.
     * @return true if the vehicle's status is successfully added, else false.
     */
    boolean setVehicleStatus(int vehicleId, String vehicleStatus);
    
    /**
     * Defines a method to retrieve fuel usage and efficiency information given
     * a specific vehicle type.
     * 
     * @param vehicleType a String representing the vehicle type.
     * @return a List of FuelDTOs containing the fuel usage/efficiency information
     * from trips.
     */
    List<FuelDTO> getFuelReportByVehicleType(String vehicleType);
    
    /**
     * Defines a method to log a trip's information in the database.
     * 
     * @param trip a TripDTO containing the information of a completed trip.
     * @return true if the trip is successfully added to the database, else false.
     */
    boolean completeTrip(TripDTO trip);
    
    /**
     * Defines a method to refuel a vehicle.
     * 
     * @param vehicle an int representing the vehicle's ID.
     * @param refuel a MaintenanceDTO containing the cost of refueling.
     * @return true if the vehicle's fuel level is updated, else false.
     */
    boolean refuel(VehicleDTO vehicle, MaintenanceDTO refuel);
    
    /**
     * Defines a method to get all ongoing maintenance requests from the database.
     * 
     * @return a List of MaintenanceDTOs containing the information of ongoing
     * maintenance requests.
     */
    List<MaintenanceDTO> getOngoingMaintenanceRequests();
}
