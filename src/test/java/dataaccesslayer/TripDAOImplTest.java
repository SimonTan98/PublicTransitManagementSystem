package dataaccesslayer;

import org.junit.jupiter.api.*;
import transferobjects.RouteDTO;
import transferobjects.TripDTO;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the TripDAOImpl class.
 * 
 * This test class sets up an in-memory database schema and verifies
 * various TripDAOImpl methods related to trip retrieval, delay calculations,
 * on-time percentages, and grouping trips by operator.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TripDAOImplTest {

    private TripDAOImpl tripDAO;

    /**
     * Sets up the database schema before all tests run.
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
     * Cleans database tables before each test and initializes DAO.
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

        tripDAO = new TripDAOImpl();
    }

    /**
     * Tests retrieval of all trips.
     */
    @Test
    void testGetAllTrips() throws SQLException {
        int routeId = insertRoute(30);
        int userId = insertUser("Simon");
        int vehicleId = insertVehicle(routeId);

        insertTrip(userId, vehicleId, routeId, 0);  // on time
        insertTrip(userId, vehicleId, routeId, 20); // late

        List<TripDTO> trips = tripDAO.getAllTrips();
        assertEquals(2, trips.size());
    }

    /**
     * Tests calculation of on-time trip percentage.
     */
    @Test
    void testGetOnTimePercentage() throws SQLException {
        int routeId = insertRoute(20);
        int userId = insertUser("Ace");
        int vehicleId = insertVehicle(routeId);

        insertTrip(userId, vehicleId, routeId, 0);  // on time
        insertTrip(userId, vehicleId, routeId, 20); // late

        double percentage = tripDAO.getOnTimePercentage();
        assertEquals(50.0, percentage, 0.01);
    }
    /**
     * Tests calculation of average delay for trips.
     */
    @Test
    void testGetAverageDelay() throws SQLException {
        int routeId = insertRoute(15);
        int userId = insertUser("Thuva");
        int vehicleId = insertVehicle(routeId);

        insertTrip(userId, vehicleId, routeId, 10); // 10 min late
        insertTrip(userId, vehicleId, routeId, 20); // 20 min late

        double avgDelay = tripDAO.getAverageDelay();
        assertEquals(15.0, avgDelay, 0.01);
    }
    /**
     * Tests retrieval of trips grouped by operator.
     */
    @Test
    void testGetTripsByOperator() throws SQLException {
        int routeId = insertRoute(25);
        int user1 = insertUser("Felix");
        int user2 = insertUser("Julie");
        int vehicleId = insertVehicle(routeId);

        insertTrip(user1, vehicleId, routeId, 0);
        insertTrip(user2, vehicleId, routeId, 0);

        Map<Integer, List<TripDTO>> map = tripDAO.getTripsByOperator();
        assertEquals(2, map.size());
        assertTrue(map.containsKey(user1));
        assertTrue(map.containsKey(user2));
    }
    /**
     * Tests calculation of on-time percentages per operator.
     */
    @Test
    void testGetOperatorOnTimePercentages() throws SQLException {
        int routeId = insertRoute(20);
        int user1 = insertUser("Wei");
        int user2 = insertUser("Santiago");
        int vehicleId = insertVehicle(routeId);

        insertTrip(user1, vehicleId, routeId, 0);
        insertTrip(user1, vehicleId, routeId, 20);
        insertTrip(user2, vehicleId, routeId, 0);

        Map<Integer, Double> percentages = tripDAO.getOperatorOnTimePercentages();
        assertEquals(2, percentages.size());
        assertEquals(50.0, percentages.get(user1), 0.01);
        assertEquals(100.0, percentages.get(user2), 0.01);
    }
    /**
     * Tests the static method that checks if a trip is on time.
     */
    @Test
    void testIsTripOnTime() {
        TripDTO trip = new TripDTO();
        RouteDTO route = new RouteDTO();

        trip.setStartTime(Timestamp.valueOf("2025-08-08 08:00:00"));
        trip.setEndTime(Timestamp.valueOf("2025-08-08 08:30:00"));
        route.setExpectedDuration(30);

        assertTrue(TripDAOImpl.isTripOnTime(trip, route));

        trip.setEndTime(Timestamp.valueOf("2025-08-08 08:45:00"));
        assertFalse(TripDAOImpl.isTripOnTime(trip, route));
    }

    /**
     * Inserts a new route with given expected duration.
     *
     * @param expectedDuration expected duration in minutes
     * @return generated route ID
     * @throws SQLException if database error occurs
     */
    private int insertRoute(int expectedDuration) throws SQLException {
        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Route (Distance, Expected_Duration) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setBigDecimal(1, new java.math.BigDecimal("10.0"));
        stmt.setInt(2, expectedDuration);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        stmt.close();
        return id;
    }
    /**
     * Inserts a new user with the given name.
     *
     * @param name user name
     * @return generated user ID
     * @throws SQLException if database error occurs
     */
    private int insertUser(String name) throws SQLException {
        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO User (Name, Password, Email, User_Type) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, name);
        stmt.setString(2, "pass123");
        stmt.setString(3, name.toLowerCase() + "@example.com");
        stmt.setString(4, "OPERATOR");
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        stmt.close();
        return id;
    }
    /**
     * Inserts a new vehicle assigned to the specified route.
     *
     * @param routeId route ID to assign vehicle to
     * @return generated vehicle ID
     * @throws SQLException if database error occurs
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
     * Inserts a new trip with specified user, vehicle, route, and delay.
     *
     * @param userId user ID
     * @param vehicleId vehicle ID
     * @param routeId route ID
     * @param delayMinutes delay in minutes (0 for on time)
     * @throws SQLException if database error occurs
     */
    private void insertTrip(int userId, int vehicleId, int routeId, int delayMinutes) throws SQLException {
        Connection connection = DataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Trip (User_ID, Vehicle_ID, Route_ID, Start_Time, End_Time, Fuel_Used, Fuel_Efficiency, On_Time) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, userId);
        stmt.setInt(2, vehicleId);
        stmt.setInt(3, routeId);

        Timestamp start = Timestamp.valueOf("2025-08-08 08:00:00");
        int expectedDuration = 15;  // hardcoded or you could fetch from DB

        Timestamp end = new Timestamp(start.getTime() + (expectedDuration + delayMinutes) * 60 * 1000L);

        stmt.setTimestamp(4, start);
        stmt.setTimestamp(5, end);
        stmt.setDouble(6, 10.0);
        stmt.setDouble(7, 4.0);
        stmt.setBoolean(8, delayMinutes <= 0);

        stmt.executeUpdate();
        stmt.close();
    }
}
