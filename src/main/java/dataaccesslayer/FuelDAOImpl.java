package dataaccesslayer;

import transferobjects.FuelDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * File Name: FuelDAOImpl.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Concrete method implementing DAO methods related to fuel reports.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class FuelDAOImpl implements FuelDAO {
    
    /**
     * Method for retrieving information required for a fuel consumption report.
     * 
     * @return a List of FuelDTOs containing fuel used, actual fuel efficiency,
     * and expected fuel efficiency on a trip.
     */
    @Override
    public List<FuelDTO> getFuelConsumptionReport() {
        List<FuelDTO> fuelList = new ArrayList<>();
        String query = "SELECT t.Trip_ID, v.Vehicle_ID, v.Vehicle_Name, v.Vehicle_Type, " +
                      "v.fuel_type, t.Fuel_Used, t.Fuel_Efficiency, v.consumption_rate AS Expected_Efficiency " +
                      "FROM Trip t JOIN Vehicle v ON t.Vehicle_ID = v.Vehicle_ID " +
                      "ORDER BY t.End_Time DESC";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                FuelDTO fuel = new FuelDTO();
                fuel.setTripId(resultSet.getInt("Trip_ID"));
                fuel.setVehicleId(resultSet.getInt("Vehicle_ID"));
                fuel.setVehicleName(resultSet.getString("Vehicle_Name"));
                fuel.setVehicleType(resultSet.getString("Vehicle_Type"));
                fuel.setFuelUsed(resultSet.getDouble("Fuel_Used"));
                fuel.setActualFuelEfficiency(resultSet.getDouble("Fuel_Efficiency"));
                fuel.setExpectedFuelEfficiency(resultSet.getDouble("Expected_Efficiency"));
                fuelList.add(fuel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return fuelList;
    }

    /**
     * Method to get the average fuel efficiency across all trips.
     * 
     * @return a double representing the average fuel efficiency across all trips.
     */
    @Override
    public double getAverageFuelEfficiency() {
        double efficiency = 0;
        String query = "SELECT AVG(Fuel_Efficiency) AS AvgEfficiency FROM Trip";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                efficiency = resultSet.getDouble("AvgEfficiency");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return efficiency;
    }

    /**
     * Method to get the total amount of fuel used on all trips.
     * 
     * @return the total amount of fuel used on all trips.
     */
    @Override
    public double getTotalFuelUsed() {
        double totalFuel = 0;
        String query = "SELECT SUM(Fuel_Used) AS TotalFuel FROM Trip";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                totalFuel = resultSet.getDouble("TotalFuel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return totalFuel;
    }
}