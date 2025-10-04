package dataaccesslayer;

import transferobjects.MaintenanceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * File Name: MaintenanceDAOImpl.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Concrete class implementing DAO methods related to maintenance.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class MaintenanceDAOImpl implements MaintenanceDAO {
    
    /**
     * Method to get all maintenance requests.
     * 
     * @return a List of MaintenanceDTOs containing all maintenance requests.
     */
    @Override
    public List<MaintenanceDTO> getAllMaintenance() {
        List<MaintenanceDTO> maintenanceList = new ArrayList<>();
        String query = "SELECT * FROM Maintenance ORDER BY Start_Time DESC";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                MaintenanceDTO maintenance = new MaintenanceDTO();
                maintenance.setMaintenanceId(resultSet.getInt("Maintenance_ID"));
                maintenance.setVehicleId(resultSet.getInt("Vehicle_ID"));
                maintenance.setPurpose(resultSet.getString("Purpose"));
                maintenance.setCost(resultSet.getDouble("Cost"));
                maintenance.setStartTime(resultSet.getTimestamp("Start_Time"));
                maintenance.setEndTime(resultSet.getTimestamp("End_Time"));
                maintenanceList.add(maintenance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maintenanceList;
    }

    /**
     * Method to get all maintenance requests by status.
     * 
     * @param status the status of the desired requests.
     * @return a List of MaintenanceDTOs containing all maintenance requests with
     * a given status.
     */
    @Override
    public List<MaintenanceDTO> getMaintenanceByStatus(String status) {
        List<MaintenanceDTO> maintenanceList = new ArrayList<>();
        String query = "SELECT * FROM Maintenance WHERE End_Time " + 
                      (status.equalsIgnoreCase("completed") ? "IS NOT NULL" : "IS NULL") + 
                      " ORDER BY Start_Time DESC";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                MaintenanceDTO maintenance = new MaintenanceDTO();
                maintenance.setMaintenanceId(resultSet.getInt("Maintenance_ID"));
                maintenance.setVehicleId(resultSet.getInt("Vehicle_ID"));
                maintenance.setPurpose(resultSet.getString("Purpose"));
                maintenance.setCost(resultSet.getDouble("Cost"));
                maintenance.setStartTime(resultSet.getTimestamp("Start_Time"));
                maintenance.setEndTime(resultSet.getTimestamp("End_Time"));
                maintenanceList.add(maintenance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maintenanceList;
    }

     /**
     * Method to retrieve the total cost of all maintenance requests.
     * 
     * @return a price of all maintenance requests.
     */
    @Override
    public double getTotalMaintenanceCost() {
        double totalCost = 0;
        String query = "SELECT SUM(Cost) AS TotalCost FROM Maintenance";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                totalCost = resultSet.getDouble("TotalCost");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return totalCost;
    }
}