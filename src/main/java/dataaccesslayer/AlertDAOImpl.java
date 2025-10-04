package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import transferobjects.AlertDTO;

/**
 * File Name: AlertDAOImpl.java
 * Author: Justine Ace Santos
 * Course: CST8288 - Object Oriented Programming with Design Patterns
 * Section: Lab section 12
 * Assignment: Final Group Project
 * Date: August 8th, 2025
 * Professor: Teddy Yap
 * 
 * Concrete class that implements DAO methods related to alerts.
 * 
 * @author jaces
 * @version 1.0, 08/08/2025
 * @see dataaccesslayer
 * @since JDK 21.0.4
 */
public class AlertDAOImpl implements AlertDAO {
    
    /**
     * Method to add an alert to the database.
     * 
     * @param alert an AlertDTO containing the information of an alert.
     */
    @Override
    public void addAlert(AlertDTO alert) {
        String sql = "INSERT INTO Alert (Vehicle_ID, Alert_Type, Alert_Reason, Status, Alert_Time) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, alert.getVehicleID());
            pstmt.setString(2, alert.getAlertType());
            pstmt.setString(3, alert.getAlertReason());
            pstmt.setString(4, alert.getStatus());
            pstmt.setTimestamp(5, alert.getAlertTime());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    alert.setAlertID(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add alert", e);
        }
    }
    
    /**
     * Method to update an alert in the database.
     * 
     * @param alert an AlertDTO containing the new information of an alert.
     */
    @Override
    public void updateAlert(AlertDTO alert) {
        String sql = "UPDATE Alert SET Status = ? WHERE Alert_ID = ?";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, alert.getStatus());
            pstmt.setInt(2, alert.getAlertID());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update alert", e);
        }
    }

    /**
     * Method to delete an alert in the database.
     * 
     * @param alertID an int representing the alert's ID
     */
    @Override
    public void deleteAlert(int alertID) {
        String sql = "DELETE FROM Alert WHERE Alert_ID = ?";
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, alertID);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete alert", e);
        }
    }

    /**
     * Method to retrieve a specific alert given an alert ID.
     * 
     * @param alertID an int representing the alert id.
     * @return an AlertDTO that contains the alert's information.
     */
    @Override
    public AlertDTO getAlertById(int alertID) {
        String sql = "SELECT * FROM Alert WHERE Alert_ID = ?";
        AlertDTO alert = null;
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, alertID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    alert = new AlertDTO();
                    alert.setAlertID(rs.getInt("Alert_ID"));
                    alert.setVehicleID(rs.getInt("Vehicle_ID"));
                    alert.setAlertType(rs.getString("Alert_Type"));
                    alert.setAlertReason(rs.getString("Alert_Reason"));
                    alert.setStatus(rs.getString("Status"));
                    alert.setAlertTime(rs.getTimestamp("Alert_Time"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get alert by ID", e);
        }
        return alert;
    }

    /**
     * Method to get all alerts from the database.
     * 
     * @return a List of AlertDTOs containing the information of all alerts in 
     * the database.
     */
    @Override
    public List<AlertDTO> getAllAlerts() {
        String sql = "SELECT * FROM Alert ORDER BY Alert_Time DESC";
        List<AlertDTO> alerts = new ArrayList<>();
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                AlertDTO alert = new AlertDTO();
                alert.setAlertID(rs.getInt("Alert_ID"));
                alert.setVehicleID(rs.getInt("Vehicle_ID"));
                alert.setAlertType(rs.getString("Alert_Type"));
                alert.setAlertReason(rs.getString("Alert_Reason"));
                alert.setStatus(rs.getString("Status"));
                alert.setAlertTime(rs.getTimestamp("Alert_Time"));
                alerts.add(alert);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all alerts", e);
        }
        return alerts;
    }

    /**
     * Method to get alerts for a specific vehicle.
     * 
     * @param vehicleID the vehicle's ID.
     * @return a List of AlertDTOs for the given vehicle.
     */
    @Override
    public List<AlertDTO> getAlertsByVehicle(int vehicleID) {
        String sql = "SELECT * FROM Alert WHERE Vehicle_ID = ? ORDER BY Alert_Time DESC";
        List<AlertDTO> alerts = new ArrayList<>();
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, vehicleID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AlertDTO alert = new AlertDTO();
                    alert.setAlertID(rs.getInt("Alert_ID"));
                    alert.setVehicleID(rs.getInt("Vehicle_ID"));
                    alert.setAlertType(rs.getString("Alert_Type"));
                    alert.setAlertReason(rs.getString("Alert_Reason"));
                    alert.setStatus(rs.getString("Status"));
                    alert.setAlertTime(rs.getTimestamp("Alert_Time"));
                    alerts.add(alert);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get alerts by vehicle", e);
        }
        return alerts;
    }

    /**
     * Method to get all active alerts.
     * 
     * @return a List of AlertDTOs containing information of all active alerts.
     */
    @Override
    public List<AlertDTO> getActiveAlerts() {
        String sql = "SELECT * FROM Alert WHERE Status = 'ACTIVE' ORDER BY Alert_Time DESC";
        List<AlertDTO> alerts = new ArrayList<>();
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                AlertDTO alert = new AlertDTO();
                alert.setAlertID(rs.getInt("Alert_ID"));
                alert.setVehicleID(rs.getInt("Vehicle_ID"));
                alert.setAlertType(rs.getString("Alert_Type"));
                alert.setAlertReason(rs.getString("Alert_Reason"));
                alert.setStatus(rs.getString("Status"));
                alert.setAlertTime(rs.getTimestamp("Alert_Time"));
                alerts.add(alert);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get active alerts", e);
        }
        return alerts;
    }
    
    /**
     * Method to retrieve alerts by alert type.
     * 
     * @param alertType a String representing the type of alert.
     * @return a List of AlertDTOs containing information of all alerts of the
     * given type.
     */
    @Override
    public List<AlertDTO> getAlertsByType(String alertType) {
        String sql = "SELECT * FROM Alert WHERE Alert_Type = ? ORDER BY Alert_Time DESC";
        List<AlertDTO> alerts = new ArrayList<>();
        
        try (Connection connection = DataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, alertType);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AlertDTO alert = new AlertDTO();
                    alert.setAlertID(rs.getInt("Alert_ID"));
                    alert.setVehicleID(rs.getInt("Vehicle_ID"));
                    alert.setAlertType(rs.getString("Alert_Type"));
                    alert.setAlertReason(rs.getString("Alert_Reason"));
                    alert.setStatus(rs.getString("Status"));
                    alert.setAlertTime(rs.getTimestamp("Alert_Time"));
                    alerts.add(alert);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get alerts by type", e);
        }
        return alerts;
    }
}