package dataaccesslayer;

import org.junit.jupiter.api.*;
import transferobjects.MaintenanceDTO;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for MaintenanceDAOImpl to verify maintenance record management.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MaintenanceDAOImplTest {

    private MaintenanceDAOImpl maintenanceDAO;
    /**
     * Sets up database tables required for tests.
     * 
     * @throws SQLException if a database error occurs
     */
    @BeforeAll
    void setupDatabase() throws SQLException {
        Connection connection = DataSource.getConnection();
        Statement stmt = connection.createStatement();

        // Create Route table (needed for Vehicle foreign key)
        stmt.execute("CREATE TABLE IF NOT EXISTS Route (" +
                "Route_ID INT AUTO_INCREMENT NOT NULL," +
                "Distance DECIMAL(10,2) NOT NULL," +
                "Expected_Duration INT NOT NULL," +
                "CONSTRAINT Route_IDPK PRIMARY KEY (Route_ID))");

        // Create Vehicle table
        stmt.execute("CREATE TABLE IF NOT EXISTS Vehicle (" +
                "Vehicle_ID INT AUTO_INCREMENT NOT NULL," +
                "Vehicle_Name VARCHAR(100) NOT NULL," +
                "Vehicle_Type VARCHAR(50) NOT NULL," +
                "Max_capacity INT NOT NULL," +
                "Current_Route_ID INT," +
                "axle_bearings DECIMAL(5,2)," +
                "fuel_type VARCHAR(50)," +
                "consumption_rate DECIMAL(5,2)," +
                "Status VARCHAR(30) NOT NULL," +
                "Wheels DECIMAL(5,2)," +
                "Brakes DECIMAL(5,2)," +
                "Fuel_level DECIMAL(8,2)," +
                "CONSTRAINT Vehicle_IDPK PRIMARY KEY (Vehicle_ID)," +
                "FOREIGN KEY (Current_Route_ID) REFERENCES Route(Route_ID))");

        // Create Maintenance table
        stmt.execute("CREATE TABLE IF NOT EXISTS Maintenance (" +
                "Maintenance_ID INT AUTO_INCREMENT NOT NULL," +
                "Vehicle_ID INT," +
                "Purpose VARCHAR(50) NOT NULL," +
                "Cost DECIMAL(10,2)," +
                "Start_Time TIMESTAMP NOT NULL," +
                "End_Time TIMESTAMP NULL," +
                "CONSTRAINT Maintenance_IDPK PRIMARY KEY (Maintenance_ID)," +
                "FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)," +
                "CONSTRAINT chk_time_order_maintenance CHECK (Start_Time < End_Time OR End_Time IS NULL))");

        stmt.close();
        connection.close();
    }
    /**
     * Cleans database tables before each test.
     * 
     * @throws SQLException if a database error occurs
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

        maintenanceDAO = new MaintenanceDAOImpl();
    }
    /**
     * Tests retrieval of all maintenance records.
     * 
     * @throws SQLException if a database error occurs
     */
    @Test
    void testGetAllMaintenance() throws SQLException {
        insertMaintenance(1, "Engine Check", 250.0, "2025-08-08 09:00:00", null);
        insertMaintenance(2, "Brake Replacement", 400.0, "2025-08-08 10:00:00", "2025-08-08 12:00:00");

        List<MaintenanceDTO> list = maintenanceDAO.getAllMaintenance();
        assertEquals(2, list.size());
        assertEquals("Brake Replacement", list.get(0).getPurpose()); // Ordered by Start_Time DESC
    }
    /**
     * Tests retrieval of maintenance records with status 'completed'.
     * 
     * @throws SQLException if a database error occurs
     */
    @Test
    void testGetMaintenanceByStatus_completed() throws SQLException {
        insertMaintenance(3, "Oil Change", 100.0, "2025-08-08 08:00:00", "2025-08-08 08:30:00");

        List<MaintenanceDTO> completed = maintenanceDAO.getMaintenanceByStatus("completed");
        assertEquals(1, completed.size());
        assertNotNull(completed.get(0).getEndTime());
    }
    /**
     * Tests retrieval of maintenance records with status 'pending'.
     * 
     * @throws SQLException if a database error occurs
     */
    @Test
    void testGetMaintenanceByStatus_pending() throws SQLException {
        insertMaintenance(4, "Tire Rotation", 80.0, "2025-08-08 11:00:00", null);

        List<MaintenanceDTO> pending = maintenanceDAO.getMaintenanceByStatus("pending");
        assertEquals(1, pending.size());
        assertNull(pending.get(0).getEndTime());
    }
    /**
     * Tests calculation of total maintenance cost.
     * 
     * @throws SQLException if a database error occurs
     */
    @Test
    void testGetTotalMaintenanceCost() throws SQLException {
        insertMaintenance(5, "Battery Replacement", 150.0, "2025-08-08 13:00:00", null);
        insertMaintenance(6, "AC Repair", 300.0, "2025-08-08 14:00:00", "2025-08-08 15:30:00");

        double total = maintenanceDAO.getTotalMaintenanceCost();
        assertEquals(450.0, total);
    }

    /**
     * Inserts a vehicle and associated route if they don't exist.
     * 
     * @param vehicleId the ID of the vehicle to insert
     * @throws SQLException if a database error occurs
     */
    private void insertVehicleIfNotExists(int vehicleId) throws SQLException {
        Connection connection = DataSource.getConnection();

        // Insert dummy route
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

        // Insert vehicle
        PreparedStatement vehicleStmt = connection.prepareStatement(
                "INSERT INTO Vehicle (Vehicle_ID, Vehicle_Name, Vehicle_Type, Max_capacity, Current_Route_ID, axle_bearings, fuel_type, consumption_rate, Status, Wheels, Brakes, Fuel_level) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        vehicleStmt.setInt(1, vehicleId);
        vehicleStmt.setString(2, "Vehicle " + vehicleId);
        vehicleStmt.setString(3, "Bus");
        vehicleStmt.setInt(4, 50);
        vehicleStmt.setInt(5, routeId);
        vehicleStmt.setBigDecimal(6, new java.math.BigDecimal("1.0"));
        vehicleStmt.setString(7, "Diesel");
        vehicleStmt.setBigDecimal(8, new java.math.BigDecimal("5.0"));
        vehicleStmt.setString(9, "Active");
        vehicleStmt.setBigDecimal(10, new java.math.BigDecimal("4.0"));
        vehicleStmt.setBigDecimal(11, new java.math.BigDecimal("3.0"));
        vehicleStmt.setBigDecimal(12, new java.math.BigDecimal("75.0"));
        vehicleStmt.executeUpdate();
        vehicleStmt.close();
    }

    /**
     * Inserts a maintenance record linked to a vehicle.
     * 
     * @param vehicleId the vehicle ID
     * @param purpose the purpose of maintenance
     * @param cost the cost of maintenance
     * @param start the start time as a timestamp string
     * @param end the end time as a timestamp string or null if ongoing
     * @throws SQLException if a database error occurs
     */
    private void insertMaintenance(int vehicleId, String purpose, double cost, String start, String end) throws SQLException {
        insertVehicleIfNotExists(vehicleId); // Ensure vehicle exists

        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Maintenance (Vehicle_ID, Purpose, Cost, Start_Time, End_Time) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, vehicleId);
        stmt.setString(2, purpose);
        stmt.setBigDecimal(3, new java.math.BigDecimal(cost));
        stmt.setTimestamp(4, Timestamp.valueOf(start));
        if (end != null) {
            stmt.setTimestamp(5, Timestamp.valueOf(end));
        } else {
            stmt.setNull(5, Types.TIMESTAMP);
        }
        stmt.executeUpdate();
        stmt.close();
    }
}