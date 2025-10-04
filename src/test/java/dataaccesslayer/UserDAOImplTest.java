package dataaccesslayer;

import org.junit.jupiter.api.*;
import transferobjects.UserDTO;
import transferobjects.CredentialsDTO;
import transferobjects.BreakDTO;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserDAOImpl to verify user and break management functionality.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDAOImplTest {

    private UserDAOImpl userDAO;

    /**
     * Sets up the database schema before all tests run.
     * 
     * @throws SQLException if a database access error occurs
     */
    @BeforeAll
    void setupDatabase() throws SQLException {
        Connection connection = DataSource.getConnection();
        Statement stmt = connection.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS User (" +
                "User_id INT AUTO_INCREMENT PRIMARY KEY," +
                "Name VARCHAR(255)," +
                "Email VARCHAR(255)," +
                "Password VARCHAR(255)," +
                "User_Type VARCHAR(50))");

        stmt.execute("CREATE TABLE IF NOT EXISTS BREAK (" +
                "Break_id INT AUTO_INCREMENT PRIMARY KEY," +
                "User_id INT," +
                "Start_Time TIMESTAMP," +
                "End_Time TIMESTAMP)");

        stmt.close();
        connection.close();
    }
    /**
     * Initializes the UserDAOImpl instance before each test.
     */
    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
    }
    /**
     * Tests adding a user and retrieving the user by credentials.
     */
    @Test
    void testAddUser_andGetUserByCredentials() {
        UserDTO user = new UserDTO();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("secure123");
        user.setUserType("Admin");

        boolean added = userDAO.addUser(user);
        assertTrue(added);

        CredentialsDTO credentials = new CredentialsDTO();
        credentials.setEmail("alice@example.com");
        credentials.setPassword("secure123");

        UserDTO fetched = userDAO.getUserByCredentials(credentials);
        assertNotNull(fetched);
        assertEquals("Alice", fetched.getName());
        assertEquals("Admin", fetched.getUserType());
    }
    /**
     * Tests logging a break for a user.
     */
    @Test
    void testLogBreak() {
        // Add user first
        UserDTO user = new UserDTO();
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setPassword("pass456");
        user.setUserType("Employee");
        userDAO.addUser(user);

        CredentialsDTO credentials = new CredentialsDTO();
        credentials.setEmail("bob@example.com");
        credentials.setPassword("pass456");

        UserDTO fetched = userDAO.getUserByCredentials(credentials);
        assertNotNull(fetched);

        BreakDTO breakDTO = new BreakDTO();
        breakDTO.setUserId(fetched.getUserID());
        breakDTO.setStartTime(Timestamp.valueOf("2025-08-08 14:00:00"));
        breakDTO.setEndTime(Timestamp.valueOf("2025-08-08 14:30:00"));

        boolean logged = userDAO.logBreak(breakDTO);
        assertTrue(logged);
    }
}