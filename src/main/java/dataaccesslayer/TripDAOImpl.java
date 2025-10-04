package dataaccesslayer;

import transferobjects.TripDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import transferobjects.RouteDTO;

/**
 * File Name: TripDAOImpl.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Concrete class implementing DAO methods related to trips.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class TripDAOImpl implements TripDAO {
    /**
     * Method to get all trips from the database.
     * 
     * @return a List of TripDTOs containing info from all trips.
     */
    @Override
    public List<TripDTO> getAllTrips() {
        List<TripDTO> tripList = new ArrayList<>();
        String query = "SELECT * FROM Trip ORDER BY Start_Time DESC";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                TripDTO trip = new TripDTO();
                trip.setTripId(resultSet.getInt("Trip_ID"));
                trip.setUserId(resultSet.getInt("User_ID"));
                trip.setVehicleId(resultSet.getInt("Vehicle_ID"));
                trip.setRouteId(resultSet.getInt("Route_ID"));
                trip.setStartTime(resultSet.getTimestamp("Start_Time"));
                trip.setEndTime(resultSet.getTimestamp("End_Time"));
                trip.setFuelUsed(resultSet.getDouble("Fuel_Used"));
                trip.setFuelEfficiency(resultSet.getDouble("Fuel_Efficiency"));
                trip.setOnTime(resultSet.getBoolean("On_Time"));
                tripList.add(trip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return tripList;
    }

    /**
     * Method to calculate the percentage of trips being on time.
     * 
     * @return a double representing the percentage of trips that are on time.
     */
    @Override
    public double getOnTimePercentage() {
        double percentage = 0;
        String query = "SELECT (COUNT(CASE WHEN On_Time = TRUE THEN 1 END) * 100.0 / COUNT(*)) AS Percentage FROM Trip";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                percentage = resultSet.getDouble("Percentage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return percentage;
    }

    /**
     * Method to get the average delay between a a vehicle's expected
     * trip duration and actual trip duration.
     * 
     * @return a double representing the average delay in minutes.
     */
    @Override
    public double getAverageDelay() {
        double averageDelay = 0;
        String query = "SELECT AVG(TIMESTAMPDIFF(MINUTE, t.Start_Time + INTERVAL r.Expected_Duration MINUTE, t.End_Time)) " +
                      "AS AvgDelay FROM Trip t JOIN Route r ON t.Route_ID = r.Route_ID " +
                      "WHERE t.On_Time = FALSE";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                averageDelay = resultSet.getDouble("AvgDelay");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return averageDelay;
    }
    
    /**
     * Method to retrieve all trips grouped by operators.
     * 
     * @return a Map containing a List of TripDTOs mapped to user Ids.
     */
    @Override
    public Map<Integer, List<TripDTO>> getTripsByOperator() {
        Map<Integer, List<TripDTO>> operatorTrips = new HashMap<>();
        String query = "SELECT * FROM Trip ORDER BY User_ID, Start_Time DESC";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                TripDTO trip = new TripDTO();
                trip.setTripId(resultSet.getInt("Trip_ID"));
                trip.setUserId(resultSet.getInt("User_ID"));
                trip.setVehicleId(resultSet.getInt("Vehicle_ID"));
                trip.setRouteId(resultSet.getInt("Route_ID"));
                trip.setStartTime(resultSet.getTimestamp("Start_Time"));
                trip.setEndTime(resultSet.getTimestamp("End_Time"));
                trip.setFuelUsed(resultSet.getDouble("Fuel_Used"));
                trip.setFuelEfficiency(resultSet.getDouble("Fuel_Efficiency"));
                trip.setOnTime(resultSet.getBoolean("On_Time"));
                
                int userId = trip.getUserId();
                operatorTrips.computeIfAbsent(userId, k -> new ArrayList<>()).add(trip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return operatorTrips;
    }

    /**
     * Method to return the on-time percentages of operators.
     * 
     * @return a Map of doubles representing on-time percentages mapped to user Ids.
     */
    @Override
    public Map<Integer, Double> getOperatorOnTimePercentages() {
        Map<Integer, Double> operatorPercentages = new HashMap<>();
        String query = "SELECT User_ID, " +
                      "(COUNT(CASE WHEN On_Time = TRUE THEN 1 END) * 100.0 / COUNT(*)) AS Percentage " +
                      "FROM Trip GROUP BY User_ID";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                double percentage = resultSet.getDouble("Percentage");
                operatorPercentages.put(userId, percentage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return operatorPercentages;
    }
    
    /**
     * Method to calculate if a trip is on time.
     * 
     * @param trip a TripDTO containing the start and end times of the trip.
     * @param route a RouteDTO containing the expected duration of a route.
     * @return true if a trip is on time, else false.
     */
    public static boolean isTripOnTime(TripDTO trip, RouteDTO route) {

        long expectedEndTimeMillis = trip.getStartTime().getTime() + 
                                   (route.getExpectedDuration() * 60 * 1000);
        long actualEndTimeMillis = trip.getEndTime().getTime();
        
       
        return actualEndTimeMillis <= expectedEndTimeMillis;
    }
}