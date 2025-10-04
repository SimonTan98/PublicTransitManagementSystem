package businesslayer;

import dataaccesslayer.VehicleDAO;
import dataaccesslayer.UserDAO;
import dataaccesslayer.VehicleDAOImpl;
import dataaccesslayer.UserDAOImpl;
import transferobjects.CredentialsDTO;
import dataaccesslayer.InvalidCredentialsException;
import factory.VehicleBuilderFactory;
import constants.MaintenancePurpose;
import transferobjects.UserDTO;
import transferobjects.BusDTO;
import transferobjects.DieselTrainDTO;
import transferobjects.ElectricLightRailDTO;
import constants.VehicleType;
import fuelpkg.BusConsumptionStrategy;
import fuelpkg.DieselTrainConsumptionStrategy;
import fuelpkg.ElectricLightRailConsumptionStrategy;
import fuelpkg.FuelContext;
import transferobjects.FuelDTO;
import java.util.List;
import transferobjects.RouteDTO;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import transferobjects.BreakDTO;
import transferobjects.LocationDTO;
import transferobjects.MaintenanceDTO;
import transferobjects.StationDTO;
import transferobjects.TripDTO;
import transferobjects.VehicleDTO;
import transferobjects.VehicleStationDTO;
import command.FuelStation;
import command.RefuelBusCommand;
import command.RefuelDieselTrainCommand;
import command.RefuelElectricLightRailCommand;
import constants.VehicleStatus;
import dataaccesslayer.AlertDAO;
import dataaccesslayer.AlertDAOImpl;
import observer.VehicleEventNotifier;



/**
 * Business logic class for managing fleet operations including vehicles, users,
 * maintenance, trips, and fuel management. Acts as the central coordinator for
 * fleet-related business operations.
 * @author Simon
 */
public class FleetManagementBusinessLogic {
    
    private VehicleDAO vehicleDao;
    private UserDAO userDao;
    private VehicleAlertBusinessLogic alertLogic;
    
    /**
     * Constructs a FleetManagementBusinessLogic instance with default DAO implementations.
     * Initializes the alert business logic with required dependencies.
     */
    public FleetManagementBusinessLogic() {
        vehicleDao = new VehicleDAOImpl();
        userDao = new UserDAOImpl();
        AlertDAO alertDao = new AlertDAOImpl();
        VehicleEventNotifier notifier = new VehicleEventNotifier(); 
        alertLogic = new VehicleAlertBusinessLogic(alertDao, notifier);
    }
    
    /**
     * Enables logging in of a user with provided credentials.
     *
     * @param credentials the user credentials
     * @return user the authenticated UserDTO if successful
     * @throws InvalidCredentialsException if authentication fails
     */
    public UserDTO login(CredentialsDTO credentials) throws InvalidCredentialsException {
        
        UserDTO user = userDao.getUserByCredentials(credentials);
        if (user == null) {
            throw new InvalidCredentialsException();
        }
        else {
            return user;
        }
    }
    /**
     * Adds a new operator to the system.
     *
     * @param name the operator's full name
     * @param email the operator's email add
     * @param password the operator's password
     * @param userType the type/role of the user
     * @return true if the operator was added successfully, false if not
     */    
   public boolean addOperator(String name, String email, String password, 
           String userType) {
//       if (loggedIn) {
        UserDTO newUser = new UserDTO();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUserType(userType);
        return userDao.addUser(newUser);
//       }
//       return false;
   }
    /**
     * Adds a new vehicle to the system with the specified parameters.
     * The method handles the adding of different vehicle types 
     * and sets appropriate properties for each type.
     *
     * @param vehicleType the type of vehicle to add
     * @param vehicleName the name of the vehicle
     * @param fuelType the type of fuel the vehicle uses
     * @param consumptionRate the fuel consumption rate
     * @param maxCapacity the maximum passenger capacity
     * @param currentRouteId the ID of the current route
     * @param axleBearingsCondition number of hours of use of axle bearings
     * @param brakesCondition number of hours of use of brakes
     * @param wheelsCondition number of hours of use of wheels
     * @param emissionRate emission rate 
     * @param oilLife oil life status 
     * @param catenaryCondition number of hours of use of catenary system 
     * @param pantographCondition number of hours of use of pantograph
     * @param circuitBreakersCondition number of hours of use of circuit breakers 
     * @return true if the vehicle was added successfully, false if not
     */   
   public boolean addVehicle(String vehicleType, String vehicleName, String fuelType, 
           double consumptionRate, int maxCapacity, int currentRouteId, 
           double axleBearingsCondition, double brakesCondition, 
           double wheelsCondition, double emissionRate, double oilLife,
           double catenaryCondition, double pantographCondition, 
           double circuitBreakersCondition) {
       
       if (vehicleType.equals(VehicleType.BUS)) {
           BusDTO bus = (BusDTO) VehicleBuilderFactory
                   .createBuilder(VehicleType.BUS)
                   .addVehicleName(vehicleName)
                   .addVehicleType(VehicleType.BUS)
                   .addVehicleName(vehicleName)
                   .addFuelType(fuelType)
                   .addConsumptionRate(consumptionRate)
                   .addMaxCapacity(maxCapacity)
                   .addCurrentRouteId(currentRouteId)
                   .addAxleBearingsCondition(axleBearingsCondition)
                   .addBrakesCondition(brakesCondition)
                   .addWheelsCondition(wheelsCondition)
                   .addEmissionRate(emissionRate)
                   .addStatus(VehicleStatus.ACTIVE)
                   .build();
           return vehicleDao.addBus(bus);
       }
       else if (vehicleType.equals(VehicleType.DIESELTRAIN)) {
           DieselTrainDTO train = (DieselTrainDTO) VehicleBuilderFactory
                   .createBuilder(VehicleType.DIESELTRAIN)
                   .addVehicleName(vehicleName)
                   .addVehicleType(VehicleType.DIESELTRAIN)
                   .addFuelType(fuelType)
                   .addVehicleName(vehicleName)
                   .addConsumptionRate(consumptionRate)
                   .addMaxCapacity(maxCapacity)
                   .addCurrentRouteId(currentRouteId)
                   .addAxleBearingsCondition(axleBearingsCondition)
                   .addBrakesCondition(brakesCondition)
                   .addWheelsCondition(wheelsCondition)
                   .addOilStatus(oilLife)
                   .addStatus(VehicleStatus.ACTIVE)
                   .build();
           return vehicleDao.addDieselTrain(train);
       }
       else {
           ElectricLightRailDTO elrTrain = (ElectricLightRailDTO) VehicleBuilderFactory
                   .createBuilder(VehicleType.ELECTRICLIGHTRAIL)
                   .addVehicleName(vehicleName)
                   .addVehicleType(VehicleType.ELECTRICLIGHTRAIL)
                   .addFuelType(fuelType)
                   .addVehicleName(vehicleName)
                   .addConsumptionRate(consumptionRate)
                   .addMaxCapacity(maxCapacity)
                   .addCurrentRouteId(currentRouteId)
                   .addAxleBearingsCondition(axleBearingsCondition)
                   .addBrakesCondition(brakesCondition)
                   .addWheelsCondition(wheelsCondition)
                   .addCatenaryCondition(catenaryCondition)
                   .addPantographCondition(pantographCondition)
                   .addCircuitBreakerCondition(circuitBreakersCondition)
                   .addStatus(VehicleStatus.ACTIVE)
                   .build();
           return vehicleDao.addElectricLightRail(elrTrain);
       }
       
   }
    
    /**
     * Retrieves a vehicle by its ID.
     *
     * @param vehicleId the ID of the vehicle to retrieve
     * @return the VehicleDTO 
     */
   public VehicleDTO getVehicleById(int vehicleId) {
       return vehicleDao.getVehicleById(vehicleId);
   }
   
    /**
     * Retrieves all vehicles in the system.
     *
     * @return list of all vehicles
     */
   public List<VehicleDTO> getAllVehicles() {
       return vehicleDao.getAllVehicles();
   }
    /**
     * Retrieves all routes in the system.
     *
     * @return list of all routes
     */
   public List<RouteDTO> getAllRoutes() {
       return vehicleDao.getAllRoutes();
   }
   
    /**
     * Retrieves all stations in the system.
     *
     * @return list of all stations
     */
   public List<StationDTO> getAllStations() {
       return vehicleDao.getAllStations();
   }
    /**
     * Logs a vehicle's visit to a station with arrival and departure times.
     *
     * @param vehicleId the ID of the visiting vehicle
     * @param stationId the ID of the visited station
     * @param arrivalTime the arrival time at the station
     * @param departureTime the departure time from the station
     * @return true if the visit was logged successfully, false if not
     */
   public boolean logStationVisit(int vehicleId, int stationId, LocalDateTime arrivalTime, LocalDateTime departureTime) {
       VehicleStationDTO visit = new VehicleStationDTO();
       visit.setVehicleId(vehicleId);
       visit.setStationId(stationId);
       visit.setArrivalTime(Timestamp.valueOf(arrivalTime));
       visit.setDepartureTime(Timestamp.valueOf(departureTime));
       return vehicleDao.logStationVisit(visit);
   }
    /**
     * Retrieves all station visits for a specific vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @return list of station visits for the vehicle
     */
   public List<VehicleStationDTO> getStationVisitsByVehicleId(int vehicleId) {
       return vehicleDao.getStationVisitsByVehicleId(vehicleId);
   }
    /**
     * Starts a maintenance activity for a vehicle.
     *
     * @param vehicleId the ID of the vehicle for maintenance
     * @param purpose the purpose/reason for maintenance
     * @param cost the estimated cost of maintenance
     * @return true if maintenance was started successfully, false if not
     */
   public boolean startMaintenance(int vehicleId, String purpose, double cost) {
       MaintenanceDTO maintenance = new MaintenanceDTO();
       maintenance.setVehicleId(vehicleId);
       maintenance.setPurpose(purpose);
       maintenance.setCost(cost);
       maintenance.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
       return vehicleDao.startMaintenance(maintenance);
   }
   
    /**
     * Ends a maintenance for a vehicle and performs reset operations.
     *
     * @param vehicleId the ID of the vehicle
     * @return true if maintenance was ended successfully 
     * and components were reset, false if not
     */
   public boolean endMaintenance(int vehicleId) {
       VehicleDTO vehicle = vehicleDao.getVehicleById(vehicleId);
       boolean success = false;
       
       if(vehicleDao.endMaintenance(vehicleId)) {
           if(vehicle instanceof DieselTrainDTO) {
               success = vehicleDao.refreshOilLife(vehicleId);
           }
           else if (vehicle instanceof ElectricLightRailDTO) {
               success = vehicleDao.refreshELRComponents(vehicleId);
           }
           else if(vehicle instanceof BusDTO) {
               success = true;
           }
       }
       return success;
   }
    /**
     * Retrieves all ongoing maintenance requests.
     *
     * @return list of ongoing maintenance activities
     */
   public List<MaintenanceDTO> getOngoingMaintenanceRequests() {
       return vehicleDao.getOngoingMaintenanceRequests();
   }
    /**
     * Updates a vehicle's current location.
     *
     * @param vehicleId the ID of the vehicle
     * @param latitude the new latitude coordinate
     * @param longitude the new longitude coordinate
     * @return true if the location was updated successfully, false if not
     */
   public boolean updateLocation(int vehicleId, double latitude, double longitude) {
       LocationDTO location = new LocationDTO();
       location.setVehicleId(vehicleId);
       location.setLatitude(latitude);
       location.setLongitude(longitude);
       location.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
       return vehicleDao.updateLocation(location);
   }
    /**
     * Retrieves current locations of all vehicles.
     *
     * @return list of all vehicle locations
     */
   public List<LocationDTO> getAllVehicleLocations() {
       return vehicleDao.getAllVehicleLocations();
   }
    /**
     * Logs a break period for a user.
     *
     * @param userId the ID of the user
     * @param startTime the start time of the break
     * @param endTime the end time of the break
     * @return true if the break was logged successfully, false if niot
     */
   public boolean logBreak(int userId, LocalDateTime startTime, LocalDateTime endTime) {
       BreakDTO breakInfo = new BreakDTO();
       breakInfo.setUserId(userId);
       breakInfo.setStartTime(Timestamp.valueOf(startTime));
       breakInfo.setEndTime(Timestamp.valueOf(endTime));
       return userDao.logBreak(breakInfo);
   }
    /**
     * Updates the status of a vehicle.
     *
     * @param vehicleId the ID of the vehicle
     * @param vehicleStatus the new status to set
     * @return true if the status was updated successfully, false if not
     */
   public boolean setVehicleStatus(int vehicleId, String vehicleStatus) {
       return vehicleDao.setVehicleStatus(vehicleId, vehicleStatus);
   }
    /**
     * Generates a fuel consumption report for a specific vehicle type.
     *
     * @param vehicleType the type of vehicle to report on
     * @return list of fuel consumption records for the specified vehicle type
     */
   public List<FuelDTO> getFuelReportByVehicleType(String vehicleType) {
       return vehicleDao.getFuelReportByVehicleType(vehicleType);
   }
    /**
     * Completes a trip and updates all related vehicle metrics.
     * Calculates fuel efficiency, updates component wear, and logs the trip.
     *
     * @param userId the ID of the operator conducting the trip
     * @param vehicleId the ID of the vehicle used for the trip
     * @param routeId the ID of the route taken in this trip
     * @param startTime the trip start time
     * @param endTime the trip end time
     * @param fuelUsed the amount of fuel consumed in this trip
     * @return true if the trip was completed and the updates were successful, false if not
     */
   public boolean completeTrip(int userId, int vehicleId, int routeId, 
           LocalDateTime startTime, LocalDateTime endTime, double fuelUsed) {
       
       VehicleDTO vehicle = vehicleDao.getVehicleById(vehicleId);
       RouteDTO route = vehicleDao.getRouteById(routeId);
       //calculate fuel efficiency
       int durationInMinutes = (int) ChronoUnit.MINUTES.between(startTime, endTime);
       double durationInHours = durationInMinutes / 60.0;
       double expectedFuelEfficiency = vehicle.getConsumptionRate();
       double distanceTravelled = route.getDistance();
       
       FuelContext context = new FuelContext();
       String vehicleType = vehicle.getVehicleType();
       if(vehicle instanceof BusDTO) {
           context.setConsumptionStrategy(new BusConsumptionStrategy());
       }
       else if(vehicle instanceof DieselTrainDTO) {
           context.setConsumptionStrategy(new DieselTrainConsumptionStrategy());
       }
       else if (vehicle instanceof ElectricLightRailDTO) {
           context.setConsumptionStrategy(new ElectricLightRailConsumptionStrategy());
       }
       double actualFuelEfficiency = context.calculateConsumption(distanceTravelled, fuelUsed, durationInHours);
       
       //update maintenance parts and alert if needed
       double axleBearings = vehicle.getAxleBearingsCondition() + durationInHours;
       double brakes = vehicle.getBrakesCondition() + durationInHours;
       double wheels = vehicle.getWheelsCondition() + durationInHours;
       double fuelLevel = vehicle.getFuelLevel() - fuelUsed;
       
       //update with new wear and tear
       vehicle.setAxleBearingsCondition(axleBearings);
       vehicle.setBrakesCondition(brakes);
       vehicle.setWheelsCondition(wheels);
       vehicle.setFuelLevel(fuelLevel);
       
       vehicleDao.updateVehicle(vehicle);

       
       boolean success = false;
       if(vehicle instanceof DieselTrainDTO) {
           DieselTrainDTO train = (DieselTrainDTO) vehicle;
           double oilLife = train.getOilStatus() - 5.0;
           train.setOilStatus(oilLife);
           success = vehicleDao.updateOilLife(train);
       }
       else if (vehicle instanceof ElectricLightRailDTO) {
           ElectricLightRailDTO elrTrain = (ElectricLightRailDTO) vehicle;
           double pantograph = elrTrain.getPantographCondition() + durationInHours;
           double catenary = elrTrain.getCatenaryCondition() + durationInHours;
           double circuitBreaker = elrTrain.getCircuitBreakerCondition() + durationInHours;
           elrTrain.setPantographCondition(pantograph);
           elrTrain.setCatenaryCondition(catenary);
           elrTrain.setCircuitBreakerCondition(circuitBreaker);
           success = vehicleDao.updateELRComponents(elrTrain);
       }
       
       //insert trip
       if(success) {
            TripDTO trip = new TripDTO();
            trip.setUserId(userId);
            trip.setVehicleId(vehicleId);
            trip.setRouteId(routeId);
            trip.setStartTime(Timestamp.valueOf(startTime));
            trip.setEndTime(Timestamp.valueOf(endTime));
            trip.setFuelUsed(fuelUsed);
            trip.setFuelEfficiency(actualFuelEfficiency);
            //On time if route was completed within 30 minutes
            int timeDifference = durationInMinutes - route.getExpectedDuration();
            trip.setOnTime(timeDifference <= 30);
            success = vehicleDao.completeTrip(trip);
            alertLogic.monitorVehicle(vehicle);
       }
       else {
           return false;
       }
       
       return success;
   }
    /**
     * Refuels a vehicle
     * Uses the Command pattern to handle different refueling procedures for different vehicle types.
     *
     * @param vehicleId the ID of the vehicle to refuel
     * @param cost the cost of refueling
     * @return true if refueling was successful, false if not
     */
   public boolean refuel(int vehicleId, double cost) {
       MaintenanceDTO refuel = new MaintenanceDTO();
       VehicleDTO vehicle = vehicleDao.getVehicleById(vehicleId);
       FuelStation station = new FuelStation(vehicle);
       if(vehicle instanceof BusDTO) {
           station.topUp(new RefuelBusCommand());
       }
       else if(vehicle instanceof DieselTrainDTO) {
           station.topUp(new RefuelDieselTrainCommand());
       }
       else if(vehicle instanceof ElectricLightRailDTO) {
           station.topUp(new RefuelElectricLightRailCommand());
       }
       refuel.setVehicleId(vehicleId);
       refuel.setCost(cost);
       refuel.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
       refuel.setEndTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
       refuel.setPurpose(MaintenancePurpose.REFUEL);
       return vehicleDao.refuel(vehicle, refuel);
   }
   
    public VehicleAlertBusinessLogic getAlertLogic() {
        return alertLogic;
    }

}
