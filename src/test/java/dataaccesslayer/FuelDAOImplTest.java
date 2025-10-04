package dataaccesslayer;

import org.junit.jupiter.api.*;
import transferobjects.FuelDTO;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the FuelDAOImpl class.
 * 
 * This test class sets up an in-memory database schema and verifies
 * methods related to fuel consumption reports, average efficiency,
 * and total fuel usage calculations.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FuelDAOImplTest {

    private FuelDAOImpl fuelDAO;
    /**
     * Sets up the database schema before all tests run.
     * 
     * @throws SQLException if a database access error occurs
     */
    @BeforeAll
    void setupDatabase() throws SQLException {
        Connection connection = DataSource.getConnection();
        Statement stmt = connection.createStatement();

        stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

        stmt.execute("CREATE TABLE IF NOT EXISTS Route (" +
                "Route_ID INT AUTO_INCREMENT PRIMARY KEY," +
                "Distance DECIMAL(10,2)," +
                "Expected_Duration INT)");

        stmt.execute("CREATE TABLE IF NOT EXISTS User (" +
                "User_ID INT AUTO_INCREMENT PRIMARY KEY," +
                "Name VARCHAR(100)," +
                "Password VARCHAR(255)," +
                "Email VARCHAR(150)," +
                "User_Type VARCHAR(50))");

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

        stmt.execute("CREATE TABLE IF NOT EXISTS Trip (" +
                "Trip_ID INT AUTO_INCREMENT PRIMARY KEY," +
                "User_ID INT," +
                "Vehicle_ID INT," +
                "Route_ID INT," +
                "Start_Time TIMESTAMP," +
                "End_Time TIMESTAMP," +
                "Fuel_Used DECIMAL(5,2)," +
                "Fuel_Efficiency DECIMAL(5,2)," +
                "On_Time BOOLEAN," +
                "FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)," +
                "FOREIGN KEY (Route_ID) REFERENCES Route(Route_ID)," +
                "FOREIGN KEY (User_ID) REFERENCES User(User_ID))");

        stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
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

        stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
        stmt.execute("DELETE FROM Trip");
        stmt.execute("DELETE FROM Vehicle");
        stmt.execute("DELETE FROM Route");
        stmt.execute("DELETE FROM User");
        stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

        stmt.close();
        connection.close();

        fuelDAO = new FuelDAOImpl();
    }
    /**
     * Tests retrieval of the fuel consumption report.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetFuelConsumptionReport() throws SQLException {
        int routeId = insertRoute();
        int userId = insertUser();
        int vehicleId = insertVehicle(routeId);

        insertTrip(vehicleId, userId, routeId, 15.0, 3.0, "2025-08-08 09:00:00");
        insertTrip(vehicleId, userId, routeId, 10.0, 4.0, "2025-08-08 10:00:00");

        List<FuelDTO> report = fuelDAO.getFuelConsumptionReport();
        assertEquals(2, report.size());

        boolean foundFirst = report.stream().anyMatch(f ->
                f.getFuelUsed() == 15.0 &&
                f.getActualFuelEfficiency() == 3.0 &&
                f.getExpectedFuelEfficiency() == 5.0);

        boolean foundSecond = report.stream().anyMatch(f ->
                f.getFuelUsed() == 10.0 &&
                f.getActualFuelEfficiency() == 4.0 &&
                f.getExpectedFuelEfficiency() == 5.0);

        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }
    /**
     * Tests calculation of average fuel efficiency.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetAverageFuelEfficiency() throws SQLException {
        int routeId = insertRoute();
        int userId = insertUser();
        int vehicleId = insertVehicle(routeId);

        insertTrip(vehicleId, userId, routeId, 12.0, 3.0, "2025-08-08 08:00:00");
        insertTrip(vehicleId, userId, routeId, 18.0, 5.0, "2025-08-08 09:00:00");

        double avg = fuelDAO.getAverageFuelEfficiency();
        assertEquals(4.0, avg, 0.01);
    }
    /**
     * Tests calculation of total fuel used.
     * 
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetTotalFuelUsed() throws SQLException {
        int routeId = insertRoute();
        int userId = insertUser();
        int vehicleId = insertVehicle(routeId);

        insertTrip(vehicleId, userId, routeId, 10.0, 3.0, "2025-08-08 08:00:00");
        insertTrip(vehicleId, userId, routeId, 20.0, 4.0, "2025-08-08 09:00:00");

        double total = fuelDAO.getTotalFuelUsed();
        assertEquals(30.0, total, 0.01);
    }

    /**
     * Inserts a route into the database for test purposes.
     * 
     * @return the generated route ID
     * @throws SQLException if a database access error occurs
     */
    private int insertRoute() throws SQLException {
        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Route (Distance, Expected_Duration) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setBigDecimal(1, new java.math.BigDecimal("10.0"));
        stmt.setInt(2, 15);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        stmt.close();
        return id;
    }
    /**
     * Inserts a user into the database for test purposes.
     * 
     * @return the generated user ID
     * @throws SQLException if a database access error occurs
     */
    private int insertUser() throws SQLException {
        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO User (Name, Password, Email, User_Type) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, "Test User");
        stmt.setString(2, "pass123");
        stmt.setString(3, "test@example.com");
        stmt.setString(4, "OPERATOR");
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        stmt.close();
        return id;
    }
    /**
     * Inserts a vehicle linked to a route into the database for test purposes.
     * 
     * @param routeId the route ID to link the vehicle to
     * @return the generated vehicle ID
     * @throws SQLException if a database access error occurs
     */
    private int insertVehicle(int routeId) throws SQLException {
        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Vehicle (Vehicle_Name, Vehicle_Type, Max_capacity, Current_Route_ID, axle_bearings, fuel_type, consumption_rate, Status, Wheels, Brakes, Fuel_level) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, "Test Vehicle");
        stmt.setString(2, "Bus");
        stmt.setInt(3, 50);
        stmt.setInt(4, routeId);
        stmt.setBigDecimal(5, new java.math.BigDecimal("1.0"));
        stmt.setString(6, "Diesel");
        stmt.setBigDecimal(7, new java.math.BigDecimal("5.0"));
        stmt.setString(8, "ACTIVE");
        stmt.setBigDecimal(9, new java.math.BigDecimal("4.0"));
        stmt.setBigDecimal(10, new java.math.BigDecimal("3.0"));
        stmt.setBigDecimal(11, new java.math.BigDecimal("75.0"));
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        stmt.close();
        return id;
    }
    /**
     * Inserts a trip with specified fuel data and timestamps for testing.
     * 
     * @param vehicleId the vehicle ID for the trip
     * @param userId the user ID for the trip
     * @param routeId the route ID for the trip
     * @param fuelUsed the amount of fuel used
     * @param efficiency the fuel efficiency during the trip
     * @param endTime the trip end timestamp as a string
     * @throws SQLException if a database access error occurs
     */
    private void insertTrip(int vehicleId, int userId, int routeId, double fuelUsed, double efficiency, String endTime) throws SQLException {
        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Trip (Vehicle_ID, User_ID, Route_ID, Start_Time, End_Time, Fuel_Used, Fuel_Efficiency, On_Time) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, vehicleId);
        stmt.setInt(2, userId);
        stmt.setInt(3, routeId);
        stmt.setTimestamp(4, Timestamp.valueOf("2025-08-08 07:00:00"));
        stmt.setTimestamp(5, Timestamp.valueOf(endTime));
        stmt.setDouble(6, fuelUsed);
        stmt.setDouble(7, efficiency);
        stmt.setBoolean(8, true);
        stmt.executeUpdate();
        stmt.close();
    }
}