package dataaccesslayer;

import org.junit.jupiter.api.*;
import transferobjects.AlertDTO;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AlertDAOImpl to verify alert management functionality.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlertDAOImplTest {

    private AlertDAOImpl alertDAO;

    /**
     * Sets up the database schema before all tests run.
     * 
     * @throws SQLException if a database access error occurs
     */
    @BeforeAll
    void setupDatabase() throws SQLException {
        Connection connection = DataSource.getConnection();
        Statement stmt = connection.createStatement();

        // Create Route table (required by Vehicle)
        stmt.execute("CREATE TABLE IF NOT EXISTS Route (" +
                "Route_ID INT AUTO_INCREMENT PRIMARY KEY," +
                "Distance DECIMAL(10,2)," +
                "Expected_Duration INT)");

        // Create Vehicle table
        stmt.execute("CREATE TABLE IF NOT EXISTS Vehicle (" +
                "Vehicle_ID INT AUTO_INCREMENT PRIMARY KEY," +
                "Vehicle_Name VARCHAR(100)," +
                "Vehicle_Type VARCHAR(50)," +
                "Max_capacity INT," +
                "Current_Route_ID INT," +
                "axle_bearings DECIMAL(5,2)," +
                "fuel_type VARCHAR(50)," +
                "consumption_rate DECIMAL(5,2)," +
                "Status VARCHAR(30)," +
                "Wheels DECIMAL(5,2)," +
                "Brakes DECIMAL(5,2)," +
                "Fuel_level DECIMAL(8,2)," +
                "FOREIGN KEY (Current_Route_ID) REFERENCES Route(Route_ID))");

        // Create Alert table
        stmt.execute("CREATE TABLE IF NOT EXISTS Alert (" +
                "Alert_ID INT AUTO_INCREMENT PRIMARY KEY," +
                "Vehicle_ID INT," +
                "Alert_Type VARCHAR(100)," +
                "Alert_Reason TEXT," +
                "Status VARCHAR(50)," +
                "Alert_Time TIMESTAMP," +
                "FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID))");

        stmt.close();
        connection.close();
    }

    /**
     * Cleans the database tables before each test to ensure isolation.
     * 
     * @throws SQLException if a database access error occurs
     */
    @BeforeEach
    void cleanDatabase() throws SQLException {
        Connection connection = DataSource.getConnection();
        Statement stmt = connection.createStatement();

        // Disable foreign key checks temporarily
        stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

        // Delete from child tables first
        stmt.execute("DELETE FROM Location");
        stmt.execute("DELETE FROM Trip");
        stmt.execute("DELETE FROM Alert");
        stmt.execute("DELETE FROM Maintenance");
        stmt.execute("DELETE FROM Vehicles_Station");
        stmt.execute("DELETE FROM Routes_Station");
        stmt.execute("DELETE FROM Diesel_Train");
        stmt.execute("DELETE FROM Electric_light_rail");
        stmt.execute("DELETE FROM Buses");
        stmt.execute("DELETE FROM Break");

        // Then delete from parent tables
        stmt.execute("DELETE FROM Vehicle");
        stmt.execute("DELETE FROM Route");
        stmt.execute("DELETE FROM Station");
        stmt.execute("DELETE FROM User");

        // Re-enable foreign key checks
        stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        stmt.close();
        connection.close();

        alertDAO = new AlertDAOImpl();
    }

    /**
     * Tests adding an alert and retrieving it by ID.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testAddAndGetAlertById() throws SQLException {
        int vehicleId = insertVehicle();
        AlertDTO alert = new AlertDTO();
        alert.setVehicleID(vehicleId);
        alert.setAlertType("Engine");
        alert.setAlertReason("Overheating");
        alert.setStatus("ACTIVE");
        alert.setAlertTime(Timestamp.valueOf("2025-08-08 10:00:00"));

        alertDAO.addAlert(alert);
        assertTrue(alert.getAlertID() > 0);

        AlertDTO fetched = alertDAO.getAlertById(alert.getAlertID());
        assertNotNull(fetched);
        assertEquals("Engine", fetched.getAlertType());
    }
    /**
     * Tests updating an alert's status.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testUpdateAlert() throws SQLException {
        int vehicleId = insertVehicle();
        AlertDTO alert = new AlertDTO();
        alert.setVehicleID(vehicleId);
        alert.setAlertType("Battery");
        alert.setAlertReason("Low voltage");
        alert.setStatus("ACTIVE");
        alert.setAlertTime(Timestamp.valueOf("2025-08-08 11:00:00"));

        alertDAO.addAlert(alert);
        alert.setStatus("RESOLVED");
        alertDAO.updateAlert(alert);

        AlertDTO updated = alertDAO.getAlertById(alert.getAlertID());
        assertEquals("RESOLVED", updated.getStatus());
    }
    /**
     * Tests deleting an alert and verifying its removal.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testDeleteAlert() throws SQLException {
        int vehicleId = insertVehicle();
        AlertDTO alert = new AlertDTO();
        alert.setVehicleID(vehicleId);
        alert.setAlertType("Brake");
        alert.setAlertReason("Wear detected");
        alert.setStatus("ACTIVE");
        alert.setAlertTime(Timestamp.valueOf("2025-08-08 12:00:00"));

        alertDAO.addAlert(alert);
        int id = alert.getAlertID();
        alertDAO.deleteAlert(id);

        AlertDTO deleted = alertDAO.getAlertById(id);
        assertNull(deleted);
    }
    /**
     * Tests retrieving all alerts.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetAllAlerts() throws SQLException {
        int vehicleId = insertVehicle();
        insertAlert(vehicleId, "Engine", "Overheating", "ACTIVE", "2025-08-08 10:00:00");
        insertAlert(vehicleId, "Battery", "Low voltage", "ACTIVE", "2025-08-08 11:00:00");

        List<AlertDTO> alerts = alertDAO.getAllAlerts();
        assertEquals(2, alerts.size());
        assertEquals("Battery", alerts.get(0).getAlertType()); // Ordered by time DESC
    }
    /**
     * Tests retrieving alerts filtered by vehicle ID.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetAlertsByVehicle() throws SQLException {
        int vehicleId = insertVehicle();
        insertAlert(vehicleId, "Brake", "Wear", "ACTIVE", "2025-08-08 12:00:00");

        List<AlertDTO> alerts = alertDAO.getAlertsByVehicle(vehicleId);
        assertEquals(1, alerts.size());
        assertEquals("Brake", alerts.get(0).getAlertType());
    }
    /**
     * Tests retrieving only active alerts.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetActiveAlerts() throws SQLException {
        int vehicleId = insertVehicle();
        insertAlert(vehicleId, "Engine", "Overheating", "ACTIVE", "2025-08-08 10:00:00");
        insertAlert(vehicleId, "Battery", "Low voltage", "RESOLVED", "2025-08-08 11:00:00");

        List<AlertDTO> activeAlerts = alertDAO.getActiveAlerts();
        assertEquals(1, activeAlerts.size());
        assertEquals("Engine", activeAlerts.get(0).getAlertType());
    }
    /**
     * Tests retrieving alerts filtered by alert type.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetAlertsByType() throws SQLException {
        int vehicleId = insertVehicle();
        insertAlert(vehicleId, "Engine", "Overheating", "ACTIVE", "2025-08-08 10:00:00");
        insertAlert(vehicleId, "Battery", "Low voltage", "ACTIVE", "2025-08-08 11:00:00");

        List<AlertDTO> engineAlerts = alertDAO.getAlertsByType("Engine");
        assertEquals(1, engineAlerts.size());
        assertEquals("Overheating", engineAlerts.get(0).getAlertReason());
    }

    /**
     * Inserts a vehicle and its route into the database for testing.
     * 
     * @return the generated vehicle ID
     * @throws SQLException if a database access error occurs
     */
    private int insertVehicle() throws SQLException {
        Connection connection = DataSource.getConnection();

        PreparedStatement routeStmt = connection.prepareStatement(
                "INSERT INTO Route (Distance, Expected_Duration) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        routeStmt.setBigDecimal(1, new java.math.BigDecimal("10.00"));
        routeStmt.setInt(2, 30);
        routeStmt.executeUpdate();
        ResultSet routeKeys = routeStmt.getGeneratedKeys();
        routeKeys.next();
        int routeId = routeKeys.getInt(1);
        routeStmt.close();

        PreparedStatement vehicleStmt = connection.prepareStatement(
                "INSERT INTO Vehicle (Vehicle_Name, Vehicle_Type, Max_capacity, Current_Route_ID, axle_bearings, fuel_type, consumption_rate, Status, Wheels, Brakes, Fuel_level) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        vehicleStmt.setString(1, "Test Vehicle");
        vehicleStmt.setString(2, "Bus");
        vehicleStmt.setInt(3, 50);
        vehicleStmt.setInt(4, routeId);
        vehicleStmt.setBigDecimal(5, new java.math.BigDecimal("1.0"));
        vehicleStmt.setString(6, "Diesel");
        vehicleStmt.setBigDecimal(7, new java.math.BigDecimal("5.0"));
        vehicleStmt.setString(8, "Active");
        vehicleStmt.setBigDecimal(9, new java.math.BigDecimal("4.0"));
        vehicleStmt.setBigDecimal(10, new java.math.BigDecimal("3.0"));
        vehicleStmt.setBigDecimal(11, new java.math.BigDecimal("75.0"));
        vehicleStmt.executeUpdate();
        ResultSet vehicleKeys = vehicleStmt.getGeneratedKeys();
        vehicleKeys.next();
        int vehicleId = vehicleKeys.getInt(1);
        vehicleStmt.close();

        return vehicleId;
    }

    /**
     * Inserts an alert associated with a vehicle.
     * 
     * @param vehicleId the vehicle ID to associate with the alert
     * @param type the alert type
     * @param reason the alert reason
     * @param status the alert status
     * @param time the alert timestamp in string format
     * @throws SQLException if a database access error occurs
     */
    private void insertAlert(int vehicleId, String type, String reason, String status, String time) throws SQLException {
        AlertDTO alert = new AlertDTO();
        alert.setVehicleID(vehicleId);
        alert.setAlertType(type);
        alert.setAlertReason(reason);
        alert.setStatus(status);
        alert.setAlertTime(Timestamp.valueOf(time));
        alertDAO.addAlert(alert);
    }
}