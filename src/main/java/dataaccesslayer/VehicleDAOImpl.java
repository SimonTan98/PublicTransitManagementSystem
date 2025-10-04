package dataaccesslayer;

import transferobjects.FuelDTO;
import java.util.List;
import transferobjects.VehicleDTO;
import transferobjects.BusDTO;
import transferobjects.DieselTrainDTO;
import transferobjects.ElectricLightRailDTO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import transferobjects.RouteDTO;
import transferobjects.StationDTO;
import constants.VehicleType;
import constants.VehicleStatus;
import transferobjects.LocationDTO;
import transferobjects.MaintenanceDTO;
import transferobjects.TripDTO;
import transferobjects.VehicleStationDTO;

/**
 * File Name: VehicleDAOImpl.java
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
public class VehicleDAOImpl implements VehicleDAO {
    
    /**
     * Adds a bus to the database. Uses a transaction to insert information in 
     * two tables. Only commits changes if both operations succeed.
     * 
     * @param bus a BusDTO containing the information of the new bus.
     * @return true if the bus is successfully added, else false.
     */
    @Override
    public boolean addBus(BusDTO bus) {
        
        String vehicleQuery = "INSERT INTO VEHICLE (Vehicle_Name, Vehicle_Type, fuel_type, "
                + "consumption_rate, Max_capacity, Current_Route_ID, axle_bearings, "
                + "Brakes, Wheels, Fuel_level, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 300,?);";
        String busQuery = "INSERT INTO BUSES (Vehicle_ID, Emission_rate) "
                + "VALUES (?, ?);";
        int vehiclesRowsAffected = 0;
        int busesRowsAffected = 0;
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(vehicleQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement statement2 = connection.prepareStatement(busQuery)) {
            connection.setAutoCommit(false);
            statement1.setString(1, bus.getVehicleName());
            statement1.setString(2, bus.getVehicleType());
            statement1.setString(3, bus.getFuelType());
            statement1.setDouble(4, bus.getConsumptionRate());
            statement1.setInt(5, bus.getMaxCapacity());
            statement1.setInt(6, bus.getCurrentRouteID());
            statement1.setDouble(7, bus.getAxleBearingsCondition());
            statement1.setDouble(8, bus.getBrakesCondition());
            statement1.setDouble(9, bus.getWheelsCondition());
            statement1.setString(10, bus.getStatus());
            
            vehiclesRowsAffected = statement1.executeUpdate();
            
            if(vehiclesRowsAffected == 0) {
                connection.rollback();
                return false;
            }
            
            try(ResultSet newIdSet = statement1.getGeneratedKeys();) {
                if(newIdSet.next()) {
                    int newId = newIdSet.getInt(1);
                    statement2.setInt(1, newId);
                    statement2.setDouble(2, bus.getEmissionRate());
                    busesRowsAffected = statement2.executeUpdate();
                    if(busesRowsAffected == 0) {
                        connection.rollback();
                        return false;
                    }
                }
                else {
                    connection.rollback();
                    return false;
                }
            }
            
            connection.commit();
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Adds a Diesel Train to the database. Uses a transaction to insert information in 
     * two tables. Only commits changes if both operations succeed.
     * 
     * @param dieselTrain a DieselTrainDTO containing the information of the new train.
     * @return true if the train is successfully added, else false.
     */
    @Override 
    public boolean addDieselTrain(DieselTrainDTO dieselTrain) {
        
        String vehicleQuery = "INSERT INTO VEHICLE (Vehicle_Name, Vehicle_Type, fuel_type, "
                + "consumption_rate, Max_capacity, Current_Route_Id, axle_bearings, "
                + "Brakes, Wheels, Fuel_level, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 15000, ?);";
        String trainQuery = "INSERT INTO DIESEL_TRAIN (Vehicle_ID, Oil_life) "
                + "VALUES (?, ?);";
        int vehiclesRowsAffected = 0;
        int trainsRowsAffected = 0;
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(vehicleQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement statement2 = connection.prepareStatement(trainQuery)) {
            connection.setAutoCommit(false);
            statement1.setString(1, dieselTrain.getVehicleName());
            statement1.setString(2, dieselTrain.getVehicleType());
            statement1.setString(3, dieselTrain.getFuelType());
            statement1.setDouble(4, dieselTrain.getConsumptionRate());
            statement1.setInt(5, dieselTrain.getMaxCapacity());
            statement1.setInt(6, dieselTrain.getCurrentRouteID());
            statement1.setDouble(7, dieselTrain.getAxleBearingsCondition());
            statement1.setDouble(8, dieselTrain.getBrakesCondition());
            statement1.setDouble(9, dieselTrain.getWheelsCondition());
            statement1.setString(10, dieselTrain.getStatus());
            
            vehiclesRowsAffected = statement1.executeUpdate();
            
            if(vehiclesRowsAffected == 0) {
                connection.rollback();
                return false;
            }
            
            try(ResultSet newIdSet = statement1.getGeneratedKeys();) {
                if(newIdSet.next()) {
                    int newId = newIdSet.getInt(1);
                    statement2.setInt(1, newId);
                    statement2.setDouble(2, dieselTrain.getOilStatus());
                    trainsRowsAffected = statement2.executeUpdate();
                    if(trainsRowsAffected == 0) {
                        connection.rollback();
                        return false;
                    }
                }
                else {
                    connection.rollback();
                    return false;
                }
            }
            
            connection.commit();
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Adds an Electric Light Rail Train to the database. Uses a transaction to insert information in 
     * two tables. Only commits changes if both operations succeed.
     * 
     * @param elrTrain am ElectricLightRailDTO containing the information of the new train.
     * @return true if the train is successfully added, else false.
     */
    @Override 
    public boolean addElectricLightRail(ElectricLightRailDTO elrTrain) {
        
        String vehicleQuery = "INSERT INTO VEHICLE (Vehicle_Name, Vehicle_Type, Fuel_Type, "
                + "Consumption_Rate, Max_Capacity, Current_Route_Id, Axle_Bearings, "
                + "Brakes, Wheels, Fuel_level, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?);";
        String railQuery = "INSERT INTO ELECTRIC_LIGHT_RAIL (Vehicle_ID, "
                + "Catenary, Pantograph, Circuit_breaker) VALUES (?, ?, ?, ?);";
        int vehiclesRowsAffected = 0;
        int railsRowsAffected = 0;
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(vehicleQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement statement2 = connection.prepareStatement(railQuery)) {
            connection.setAutoCommit(false);
            statement1.setString(1, elrTrain.getVehicleName());
            statement1.setString(2, elrTrain.getVehicleType());
            statement1.setString(3, elrTrain.getFuelType());
            statement1.setDouble(4, elrTrain.getConsumptionRate());
            statement1.setInt(5, elrTrain.getMaxCapacity());
            statement1.setInt(6, elrTrain.getCurrentRouteID());
            statement1.setDouble(7, elrTrain.getAxleBearingsCondition());
            statement1.setDouble(8, elrTrain.getBrakesCondition());
            statement1.setDouble(9, elrTrain.getWheelsCondition());
            statement1.setString(10, elrTrain.getStatus());
            
            vehiclesRowsAffected = statement1.executeUpdate();
            
            if(vehiclesRowsAffected == 0) {
                connection.rollback();
                return false;
            }
            
            try(ResultSet newIdSet = statement1.getGeneratedKeys();) {
                if(newIdSet.next()) {
                    int newId = newIdSet.getInt(1);
                    statement2.setInt(1, newId);
                    statement2.setDouble(2, elrTrain.getCatenaryCondition());
                    statement2.setDouble(3, elrTrain.getPantographCondition());
                    statement2.setDouble(4, elrTrain.getCircuitBreakerCondition());
                    railsRowsAffected = statement2.executeUpdate();
                    if(railsRowsAffected == 0) {
                        connection.rollback();
                        return false;
                    }
                }
                else {
                    connection.rollback();
                    return false;
                }
            }
            
            connection.commit();
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Method to update a vehicle's information in the database.
     * 
     * @param vehicle a VehicleDTO containing the new information for the vehicle.
     * @return true if the vehicle is successfully updated, else false.
     */
    @Override
    public boolean updateVehicle(VehicleDTO vehicle) {
        int rowsAffected = 0;
        String query = "UPDATE VEHICLE SET Vehicle_Name = ?, Vehicle_Type = ?, "
                + "Max_capacity = ?, Current_Route_ID = ?, axle_bearings = ?, "
                + "fuel_type = ?, consumption_rate = ?, Wheels = ?, "
                + "Brakes = ?, Fuel_level = ? WHERE Vehicle_ID = ?";
        
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicle.getVehicleName());
            statement.setString(2, vehicle.getVehicleType());
            statement.setInt(3, vehicle.getMaxCapacity());
            statement.setInt(4, vehicle.getCurrentRouteID());
            statement.setDouble(5, vehicle.getAxleBearingsCondition());
            statement.setString(6, vehicle.getFuelType());
            statement.setDouble(7, vehicle.getConsumptionRate());
            statement.setDouble(8, vehicle.getWheelsCondition());
            statement.setDouble(9, vehicle.getBrakesCondition());
            statement.setDouble(10, vehicle.getFuelLevel());
            statement.setInt(11, vehicle.getVehicleID());
            
            rowsAffected = statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rowsAffected > 0;
    }

    /**
     * Method to update the oil life for a Diesel Train.
     * 
     * @param train a DieselTrainDTO containing the new oil life value.
     * @return true if the diesel train is successfully updated, else false.
     */
    @Override
    public boolean updateOilLife(DieselTrainDTO train) {
        int rowsAffected = 0;
        
        String query = "UPDATE DIESEL_TRAIN SET Oil_life = ? WHERE Vehicle_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, train.getOilStatus());
            statement.setInt(2, train.getVehicleID());
            
            rowsAffected = statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rowsAffected > 0;
    }
    
    /**
     * Method to update the unique components for Electric Light Rail trains.
     * 
     * @param elrTrain an ElectricLightRailDTO containing the new values for the ELR components.
     * @return true if the ELR train was successfully added, else false.
     */
    @Override
    public boolean updateELRComponents(ElectricLightRailDTO elrTrain) {
        int rowsAffected = 0;
        
        String query = "UPDATE ELECTRIC_LIGHT_RAIL SET Pantograph = ?, "
                + "Catenary = ?, Circuit_breaker = ? WHERE Vehicle_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, elrTrain.getPantographCondition());
            statement.setDouble(2, elrTrain.getCatenaryCondition());
            statement.setDouble(3, elrTrain.getCircuitBreakerCondition());
            statement.setInt(4, elrTrain.getVehicleID());
            
            rowsAffected = statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rowsAffected > 0;
    }
    
    /**
     * Method to retrieve a list of all routes in the database along with the list of
     * stations along the route.
     * 
     * @return a List of RouteDTOs containing the information of all routes.
     */
    @Override 
    public List<RouteDTO> getAllRoutes() {
        List<RouteDTO> routes = new ArrayList<>();
        String query = "SELECT Route.Route_ID, Route.Distance, Route.Expected_Duration, Station.Station_ID, "
                + "Station.StationName FROM ROUTE JOIN ROUTES_STATION ON "
                + "ROUTE.Route_ID = ROUTES_STATION.Route_ID JOIN STATION ON "
                + "ROUTES_STATION.Station_ID = STATION.Station_ID ORDER BY Route.Route_ID ASC;";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
           
            ResultSet results = statement.executeQuery();
            int currentRouteId = -1;
            RouteDTO currentRoute = null;
            List<StationDTO> stations = null;
            while(results.next()) {
                int routeId = results.getInt("Route_ID");
                int stationId = results.getInt("Station_ID");
                String stationName = results.getString("StationName");
                double distance = results.getDouble("Distance");
                int expectedDuration = results.getInt("Expected_Duration");
                
                if(routeId != currentRouteId) { 
                    if(currentRoute != null) {
                        currentRoute.setStationList(stations);
                        routes.add(currentRoute);
                    }
                    currentRouteId = routeId;
                    currentRoute = new RouteDTO();
                    currentRoute.setRouteID(routeId);
                    currentRoute.setDistance(distance);
                    currentRoute.setExpectedDuration(expectedDuration);
                    stations = new ArrayList<>();
                }
                
                StationDTO station = new StationDTO();
                station.setStationID(stationId);
                station.setStationName(stationName);
                stations.add(station);                
                
            }
            //add last route
            currentRoute.setStationList(stations);
            routes.add(currentRoute);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return routes;
    }

    /**
     * Method to get the information of a specific route given a route ID.
     * 
     * @param routeId an int representing the desired route's Id.
     * @return a RouteDTO containing the information of the desired route.
     */
    @Override
    public RouteDTO getRouteById(int routeId) {
        RouteDTO route = new RouteDTO();
        
        String query = "SELECT * FROM ROUTE WHERE Route_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, routeId);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                route.setRouteID(results.getInt("Route_ID"));
                route.setDistance(results.getDouble("Distance"));
                route.setExpectedDuration(results.getInt("Expected_Duration"));
            }
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return route;
    }
    
    /**
     * Method to retrieve all station information from the database.
     * 
     * @return a List of StationDTOs containing the information of all stations.
     */
    @Override
    public List<StationDTO> getAllStations() {
        List<StationDTO> stations = new ArrayList<>();
        String query = "SELECT * FROM STATION;";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                StationDTO station = new StationDTO();
                station.setStationID(results.getInt("Station_ID"));
                station.setStationName(results.getString("StationName"));
                stations.add(station);
            }
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }
    
    /**
     * Method to retrieve a vehicle's information using a given vehicle ID.
     * 
     * @param vehicleID an int representing the vehicle ID.
     * @return a VehicleDTO that contains the information of the retrieved vehicle.
     */
    @Override
    public VehicleDTO getVehicleById(int vehicleID) {

        VehicleDTO vehicle = new VehicleDTO();
        String query = "SELECT * FROM Vehicle WHERE Vehicle_ID = ?";
        String vehicleType = "";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleID);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                vehicleType = results.getString("Vehicle_Type");
            }
            else {
                return null;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        if(vehicleType.equals(VehicleType.BUS)) {
            vehicle = addBusDetails(vehicleID);
        }
        else if(vehicleType.equals(VehicleType.DIESELTRAIN)) {
            vehicle = addDieselTrainDetails(vehicleID);
        }
        else if(vehicleType.equals(VehicleType.ELECTRICLIGHTRAIL)) {
            vehicle = addELRDetails(vehicleID);
        }
        else {
            return null;
        }
        return vehicle;
    }
    
    /**
     * Worker method for getVehicleById that retrieves all information of a bus
     * from the vehicle table and the buses table.
     * 
     * @param vehicleId an int representing the bus's ID.
     * @return a BusDTO containing all information of a bus.
     */
    private BusDTO addBusDetails(int vehicleId) {
        BusDTO bus = new BusDTO();
        String query = "SELECT * FROM VEHICLE JOIN BUSES ON "
                + "VEHICLE.Vehicle_ID = BUSES.Vehicle_ID WHERE VEHICLE.Vehicle_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                bus.setVehicleID(results.getInt("Vehicle_ID"));
                bus.setVehicleName(results.getString("Vehicle_Name"));
                bus.setVehicleType(results.getString("Vehicle_Type"));
                bus.setMaxCapacity(results.getInt("Max_capacity"));
                bus.setCurrentRouteID(results.getInt("Current_Route_ID"));
                bus.setAxleBearingsCondition(results.getDouble("axle_bearings"));
                bus.setFuelType(results.getString("fuel_type"));
                bus.setConsumptionRate(results.getDouble("consumption_rate"));
                bus.setStatus(results.getString("Status"));
                bus.setWheelsCondition(results.getDouble("Wheels"));
                bus.setBrakesCondition(results.getDouble("Brakes"));
                bus.setFuelLevel(results.getDouble("Fuel_level"));
                bus.setEmissionRate(results.getDouble("Emission_rate"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return bus;
    }
    
    /**
     * Worker method for getVehicleById that retrieves all information of a diesel
     * train from the vehicle table and the diesel_train table.
     * 
     * @param vehicleId an int representing the diesel train's ID.
     * @return a DieselTrainDTO containing all the info of a diesel train.
     */
    private DieselTrainDTO addDieselTrainDetails(int vehicleId) {
        DieselTrainDTO train = new DieselTrainDTO();
        String query = "SELECT * FROM VEHICLE JOIN DIESEL_TRAIN ON "
                + "VEHICLE.Vehicle_ID = DIESEL_TRAIN.Vehicle_ID WHERE VEHICLE.Vehicle_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                train.setVehicleID(results.getInt("Vehicle_ID"));
                train.setVehicleName(results.getString("Vehicle_Name"));
                train.setVehicleType(results.getString("Vehicle_Type"));
                train.setMaxCapacity(results.getInt("Max_capacity"));
                train.setCurrentRouteID(results.getInt("Current_Route_ID"));
                train.setAxleBearingsCondition(results.getDouble("axle_bearings"));
                train.setFuelType(results.getString("fuel_type"));
                train.setConsumptionRate(results.getDouble("consumption_rate"));
                train.setStatus(results.getString("Status"));
                train.setWheelsCondition(results.getDouble("Wheels"));
                train.setBrakesCondition(results.getDouble("Brakes"));
                train.setFuelLevel(results.getDouble("Fuel_level"));
                train.setOilStatus(results.getDouble("Oil_life"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return train;
    }
    
    /**
     * Worker method for getVehicleById that retrieves all information of a 
     * Electric Light Rail train.
     * 
     * @param vehicleId an int representing the ELR train's ID.
     * @return an ElectricLightRailDTO containing all information of an ELR train.
     */
    private ElectricLightRailDTO addELRDetails(int vehicleId) {
        ElectricLightRailDTO elrTrain = new ElectricLightRailDTO();
        String query = "SELECT * FROM VEHICLE JOIN ELECTRIC_LIGHT_RAIL ON "
                + "VEHICLE.Vehicle_ID = ELECTRIC_LIGHT_RAIL.Vehicle_ID WHERE VEHICLE.Vehicle_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                elrTrain.setVehicleID(results.getInt("Vehicle_ID"));
                elrTrain.setVehicleName(results.getString("Vehicle_Name"));
                elrTrain.setVehicleType(results.getString("Vehicle_Type"));
                elrTrain.setMaxCapacity(results.getInt("Max_capacity"));
                elrTrain.setCurrentRouteID(results.getInt("Current_Route_ID"));
                elrTrain.setAxleBearingsCondition(results.getDouble("axle_bearings"));
                elrTrain.setFuelType(results.getString("fuel_type"));
                elrTrain.setConsumptionRate(results.getDouble("consumption_rate"));
                elrTrain.setStatus(results.getString("Status"));
                elrTrain.setWheelsCondition(results.getDouble("Wheels"));
                elrTrain.setBrakesCondition(results.getDouble("Brakes"));
                elrTrain.setFuelLevel(results.getDouble("Fuel_level"));
                elrTrain.setCatenaryCondition(results.getDouble("Catenary"));
                elrTrain.setPantographCondition(results.getDouble("Pantograph"));
                elrTrain.setCircuitBreakerCondition(results.getDouble("Circuit_breaker"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return elrTrain;
    }

    /**
     * Method to get a list of all vehicles.
     * 
     * @return a List of VehicleDTOs containing all the vehicle information from
     * the database
     */
    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<VehicleDTO> vehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicle";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                VehicleDTO vehicle = new VehicleDTO();
                vehicle.setVehicleID(results.getInt("Vehicle_ID"));
                vehicle.setVehicleName(results.getString("Vehicle_Name"));
                vehicle.setVehicleType(results.getString("Vehicle_Type"));
                vehicle.setMaxCapacity(results.getInt("Max_capacity"));
                vehicle.setCurrentRouteID(results.getInt("Current_Route_ID"));
                vehicle.setAxleBearingsCondition(results.getDouble("axle_bearings"));
                vehicle.setFuelType(results.getString("fuel_type"));
                vehicle.setConsumptionRate(results.getDouble("consumption_rate"));
                vehicle.setStatus(results.getString("Status"));
                vehicle.setWheelsCondition(results.getDouble("Wheels"));
                vehicle.setBrakesCondition(results.getDouble("Brakes"));
                vehicle.setFuelLevel(results.getDouble("Fuel_level"));
                vehicles.add(vehicle);
            }
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    /**
     * Method to get a list of all vehicles of a given vehicle type.
     * 
     * @param vehicleType a String representing the desired vehicle type
     * @return a List of VehicleDTOs containing all the vehicle information from
     * the database of a given vehicle type.
     */
    @Override
    public List<VehicleDTO> getVehiclesByType(String vehicleType) {
        List<VehicleDTO> vehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicles WHERE Vehicle_Type = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, vehicleType);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                VehicleDTO vehicle = new VehicleDTO();
                vehicle.setVehicleID(results.getInt("Vehicle_ID"));
                vehicle.setVehicleName(results.getString("Vehicle_Name"));
                vehicle.setVehicleType(results.getString("Vehicle_Type"));
                vehicle.setMaxCapacity(results.getInt("Max_capacity"));
                vehicle.setCurrentRouteID(results.getInt("Current_Route_ID"));
                vehicle.setAxleBearingsCondition(results.getDouble("axle_bearings"));
                vehicle.setFuelType(results.getString("fuel_type"));
                vehicle.setConsumptionRate(results.getDouble("consumption_rate"));
                vehicle.setWheelsCondition(results.getDouble("Wheels"));
                vehicle.setBrakesCondition(results.getDouble("Brakes"));
                vehicle.setFuelLevel(results.getDouble("Fuel_level"));
                vehicles.add(vehicle);
            }
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    
    /**
     * Method to start maintenance for a vehicle. Inserts a record with a null
     * end_time.
     * 
     * @param maintenance a MaintenanceDTO that contains the maintenance information.
     * @return true if the maintenance is successfully added in the database, else false.
     */
    @Override
    public boolean startMaintenance(MaintenanceDTO maintenance) {
        
        String vehicleQuery = "UPDATE VEHICLE SET Status = ? WHERE Vehicle_ID = ?;";
        String maintenanceQuery = "INSERT INTO Maintenance(Vehicle_ID, Purpose, Cost, Start_Time) "
                + "VALUES (?, ?, ?, ?)";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(vehicleQuery);
            PreparedStatement statement2 = connection.prepareStatement(maintenanceQuery)) {
            connection.setAutoCommit(false);
            
            statement1.setString(1, VehicleStatus.IN_MAINTENANCE);
            statement1.setInt(2, maintenance.getVehicleId());
                       
            int rowsAffected = statement1.executeUpdate();
            
            if(rowsAffected == 0) {
                connection.rollback();
                return false;
            }
            
            statement2.setInt(1, maintenance.getVehicleId());
            statement2.setString(2, maintenance.getPurpose());
            statement2.setDouble(3, maintenance.getCost());
            statement2.setTimestamp(4, maintenance.getStartTime());
            rowsAffected = statement2.executeUpdate();
            
            if(rowsAffected == 0) {
                connection.rollback();
                return false;
            }
            connection.commit();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return true; 
    }
    
    /**
     * Defines a method to end maintenance for a vehicle. Updates the hours of use
     * for a vehicle's components to 0 once maintenance is complete.
     * 
     * @param vehicleId an int representing the vehicle's ID.
     * @return true if maintenance is successfully ended for a vehicle, else false.
     */
    @Override
    public boolean endMaintenance(int vehicleId) {
        
        String vehicleQuery = "UPDATE VEHICLE SET Status = ?, Axle_bearings = 0, Wheels = 0, Brakes = 0 WHERE Vehicle_ID = ?;";
        String maintenanceQuery = "UPDATE MAINTENANCE SET End_Time = CURRENT_TIMESTAMP "
                + "WHERE Vehicle_ID = ? AND End_Time IS NULL";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(vehicleQuery);
            PreparedStatement statement2 = connection.prepareStatement(maintenanceQuery)) {
            connection.setAutoCommit(false);
            
            statement1.setString(1, VehicleStatus.ACTIVE);
            statement1.setInt(2, vehicleId);
                       
            int rowsAffected = statement1.executeUpdate();
            
            if(rowsAffected == 0) {
                connection.rollback();
                return false;
            }
            
            statement2.setInt(1, vehicleId);
            rowsAffected = statement2.executeUpdate();
            
            if(rowsAffected == 0) {
                connection.rollback();
                return false;
            }            
            connection.commit();            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Method to get all ongoing maintenance requests from the database.
     * 
     * @return a List of MaintenanceDTOs containing the information of ongoing
     * maintenance requests.
     */
    @Override
    public List<MaintenanceDTO> getOngoingMaintenanceRequests() {
        List<MaintenanceDTO> requests = new ArrayList<>();
        
        String query = "SELECT * FROM MAINTENANCE WHERE END_TIME IS NULL";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                MaintenanceDTO maintenance = new MaintenanceDTO();
                maintenance.setMaintenanceId(results.getInt("Maintenance_Id"));
                maintenance.setVehicleId(results.getInt("Vehicle_Id"));
                maintenance.setPurpose(results.getString("Purpose"));
                maintenance.setCost(results.getDouble("Cost"));
                maintenance.setStartTime(results.getTimestamp("Start_Time"));
                maintenance.setEndTime(results.getTimestamp("End_Time"));
                requests.add(maintenance);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    /**
     * Method to refresh the oil life of a diesel train.
     * 
     * @param vehicleId an int representing the diesel train's ID.
     * @return true if the oil life is successfully updated, else false.
     */
    @Override
    public boolean refreshOilLife(int vehicleId) {
        int rowsAffected = 0;
        
        String query = "UPDATE DIESEL_TRAIN SET OIL_LIFE = 100.0 WHERE Vehicle_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            
            rowsAffected = statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }
    
    /**
     * Method to reset the hours of use for Electric Light Rail components.
     * 
     * @param vehicleId an int representing the ELR train's ID.
     * @return true if the components are successfully updated, else false.
     */
    @Override
    public boolean refreshELRComponents(int vehicleId) {
        int rowsAffected = 0;
        
        String query = "UPDATE ELECTRIC_LIGHT_RAIL SET Pantograph = 0, "
                + "Catenary = 0, Circuit_Breaker = 0 WHERE Vehicle_ID = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            
            rowsAffected = statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }
    
    /**
     * Method to update a vehicle's location. Sets previous most recent location
     * to false and then inserts a new record.
     * 
     * @param location a LocationDTO containing the new coordinates for a vehicle.
     * @return true if the vehicle's location is successfully updated, else false.
     */
    @Override
    public boolean updateLocation(LocationDTO location) {
        
        String updateQuery = "UPDATE LOCATION SET Most_Recent = FALSE WHERE VEHICLE_ID = ? AND Most_Recent = TRUE";
        String insertQuery = "INSERT INTO LOCATION (Vehicle_ID, Latitude, Longitude, Updated, Most_Recent) "
                + "VALUES (?, ?, ?, ?, TRUE)";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(updateQuery);
            PreparedStatement statement2 = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(false);
            
            statement1.setInt(1, location.getVehicleId());
            
            int rowsAffected = statement1.executeUpdate();
            
            if(rowsAffected == 0) {
                connection.rollback();
                return false;
            }
            
            statement2.setInt(1, location.getVehicleId());
            statement2.setDouble(2, location.getLatitude());
            statement2.setDouble(3, location.getLongitude());
            statement2.setTimestamp(4, location.getUpdated());
            
            rowsAffected = statement2.executeUpdate();
            if(rowsAffected == 0) {
                connection.rollback();
                return false;
            }
            connection.commit();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    /**
     * Method to get all most recent vehicle locations from the database.
     * 
     * @return a List of LocationDTOs containing the location data from the database.
     */
    @Override
    public List<LocationDTO> getAllVehicleLocations() {
        List<LocationDTO> locations = new ArrayList<>();
        
        String query = "SELECT * FROM LOCATION WHERE Most_Recent = TRUE ORDER BY Vehicle_Id ASC";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                LocationDTO location = new LocationDTO();
                location.setLocationId(results.getInt("Location_ID"));
                location.setVehicleId(results.getInt("Vehicle_ID"));
                location.setLatitude(results.getDouble("Latitude"));
                location.setLongitude(results.getDouble("Longitude"));
                location.setUpdated(results.getTimestamp("Updated"));
                location.setMostRecent(results.getBoolean("Most_Recent"));
                locations.add(location);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return locations;
    }
    
    /**
     * Method to log a vehicle's visit to a station in the database.
     * 
     * @param visit a VehicleStationDTO containing the information of the vehicle's
     * visit to the station.
     * @return true if the visit was successfully added, else false.
     */
    @Override
    public boolean logStationVisit(VehicleStationDTO visit) {
        int rowsAffected = 0;
        
        String query = "INSERT INTO Vehicles_Station(Vehicle_Id, Station_Id, "
                + "Arrival_Time, Departure_Time) VALUES (?, ?, ?, ?)";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, visit.getVehicleId());
            statement.setInt(2, visit.getStationId());
            statement.setTimestamp(3, visit.getArrivalTime());
            statement.setTimestamp(4, visit.getDepartureTime());
            
            rowsAffected = statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rowsAffected > 0;
    }
    
    /**
     * Method to get the history of station visits for a given
     * vehicle.
     * 
     * @param vehicleId an int representing the desired vehicle's ID
     * @return a List of VehicleStationDTOs containing the information
     * of all station visits of the desired vehicle.
     */
    @Override
    public List<VehicleStationDTO> getStationVisitsByVehicleId(int vehicleId) {
        List<VehicleStationDTO> visits = new ArrayList<>();
        
        String query = "SELECT * FROM VEHICLES_STATION WHERE Vehicle_Id = ? ORDER BY Departure_Time DESC";
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                VehicleStationDTO visit = new VehicleStationDTO();
                visit.setVehicleId(results.getInt("Vehicle_Id"));
                visit.setStationId(results.getInt("Station_Id"));
                visit.setArrivalTime(results.getTimestamp("Arrival_Time"));
                visit.setDepartureTime(results.getTimestamp("Departure_Time"));
                visits.add(visit);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return visits;
    }
    
    /**
     * Method to update a vehicle's status.
     * 
     * @param vehicleId an int representing the vehicle's ID.
     * @param vehicleStatus a String representing the new status of the vehicle.
     * @return true if the vehicle's status is successfully added, else false.
     */
    @Override
    public boolean setVehicleStatus(int vehicleId, String vehicleStatus) {
        int rowsAffected = 0;
        
        String query = "UPDATE VEHICLE SET Status = ? WHERE Vehicle_Id = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, vehicleStatus);
            statement.setInt(2, vehicleId);
            
            rowsAffected = statement.executeUpdate();
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rowsAffected > 0;
    }
    
    /**
     * Method to retrieve fuel usage and efficiency information on trips given
     * a specific vehicle type.
     * 
     * @param vehicleType a String representing the vehicle type.
     * @return a List of FuelDTOs containing the fuel usage/efficiency information
     * from trips.
     */
    @Override
    public List<FuelDTO> getFuelReportByVehicleType(String vehicleType) {
        
        List<FuelDTO> fuelEfficiencies = new ArrayList<>();       
        String query = "SELECT VEHICLE.Vehicle_ID, VEHICLE.Vehicle_Name, VEHICLE.Vehicle_Type, "
                + "VEHICLE.consumption_rate, TRIP.Trip_Id, TRIP.Fuel_Used, "
                + "TRIP.Fuel_Efficiency FROM VEHICLE JOIN TRIP ON "
                + "VEHICLE.Vehicle_ID = TRIP.Vehicle_ID "
                + "WHERE Trip.End_Time IS NOT NULL AND Vehicle.Vehicle_Type = ? "
                + "ORDER BY Trip.Trip_Id DESC;";
        
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicleType);
            
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                FuelDTO fuelItem = new FuelDTO();
                fuelItem.setVehicleId(results.getInt("Vehicle_ID"));
                fuelItem.setVehicleName(results.getString("Vehicle_Name"));
                fuelItem.setVehicleType(results.getString("Vehicle_Type"));
                fuelItem.setExpectedFuelEfficiency(results.getDouble("consumption_rate"));
                fuelItem.setActualFuelEfficiency(results.getDouble("Fuel_Efficiency"));
                fuelItem.setFuelUsed(results.getDouble("FuelUsed"));
                fuelItem.setTripId(results.getInt("Trip_ID"));
                fuelEfficiencies.add(fuelItem);
            }
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return fuelEfficiencies;
    }
    
    /**
     * Method to log a trip's information in the database. 
     * 
     * @param trip a TripDTO containing the information of a completed trip.
     * @return true if the trip is successfully added to the database, else false.
     */
    @Override
    public boolean completeTrip(TripDTO trip) {
        int rowsAffected = 0;
        
        String query = "INSERT INTO TRIP(User_Id, Vehicle_ID, Route_ID, "
                + "Start_Time, End_Time, Fuel_Efficiency, On_Time, Fuel_used) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, trip.getUserId());
            statement.setInt(2, trip.getVehicleId());
            statement.setInt(3, trip.getRouteId());
            statement.setTimestamp(4, trip.getStartTime());
            statement.setTimestamp(5, trip.getEndTime());
            statement.setDouble(6, trip.getFuelEfficiency());
            statement.setBoolean(7, trip.isOnTime());
            statement.setDouble(8, trip.getFuelUsed());
            rowsAffected = statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        return rowsAffected > 0;
    }
    
    /**
     * Method to refuel a vehicle.
     * 
     * @param vehicle an int representing the vehicle's ID.
     * @param refuel a MaintenanceDTO containing the cost of refueling.
     * @return true if the vehicle's fuel level is updated, else false.
     */
    @Override
    public boolean refuel(VehicleDTO vehicle, MaintenanceDTO refuel) {
        boolean successfulTransaction = false;
        String refuelQuery = "UPDATE VEHICLE SET Fuel_level = ? WHERE Vehicle_ID = ?";
        String maintenanceQuery = "INSERT INTO MAINTENANCE(Vehicle_ID, Purpose, "
                + "Cost, Start_Time, End_Time) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(refuelQuery);
            PreparedStatement statement2 = connection.prepareStatement(maintenanceQuery)) {
            connection.setAutoCommit(false);
            statement1.setDouble(1, vehicle.getFuelLevel());
            statement1.setInt(2, vehicle.getVehicleID());
            
            int rowsAffected = statement1.executeUpdate();
            
            if(rowsAffected > 0) {
                statement2.setInt(1, vehicle.getVehicleID());
                statement2.setString(2, refuel.getPurpose());
                statement2.setDouble(3, refuel.getCost());
                statement2.setTimestamp(4, refuel.getStartTime());
                statement2.setTimestamp(5, refuel.getEndTime());
                successfulTransaction = statement2.executeUpdate() > 0;
                if(!successfulTransaction) {
                    connection.rollback();
                    return false;
                }
            }
            else {
                connection.rollback();
                return false;
            }
            connection.commit();
        } 
        catch(SQLException e) {
            e.printStackTrace();
        }
        return successfulTransaction;
    }
    
}
