package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import businesslayer.ReportsBusinessLogic;
import dataaccesslayer.*;

import transferobjects.RouteDTO;
import transferobjects.TripDTO;
import transferobjects.VehicleDTO;
import transferobjects.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Servlet that handles generating a fuel and energy usage report by vehicle type.
 * Only accessible to users with the "TRANSIT MANAGER" role.
 * 
 * The report compares expected fuel/energy consumption with actual usage for vehicles
 * of a selected type.
 * 
 * Provides a dropdown to filter by vehicle type and displays results in a table
 * with color-coded rows for matches/mismatches.
 * 
 * @author @Thuvarahan Ragunathan
 */
@WebServlet("/FuelEnergyReportServlet")
public class FuelEnergyReportServlet extends HttpServlet {

    private FleetManagementBusinessLogic fleetLogic;
    private ReportsBusinessLogic reportsLogic;

    /**
     * Initializes business logic components required for data access and report generation.
     *
     * @throws ServletException if initialization fails
     */
    @Override
    public void init() throws ServletException {
        fleetLogic = new FleetManagementBusinessLogic();

        MaintenanceDAO maintenanceDAO = new MaintenanceDAOImpl();
        TripDAO tripDAO = new TripDAOImpl();
        FuelDAO fuelDAO = new FuelDAOImpl();
        VehicleDAO vehicleDAO = new VehicleDAOImpl();

        reportsLogic = new ReportsBusinessLogic(maintenanceDAO, tripDAO, fuelDAO, vehicleDAO);
    }
    /**
     * Handles HTTP GET requests. Displays a form to select vehicle type and
     * renders a table showing expected vs actual fuel/energy usage.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        response.setContentType("text/html");
        response.getWriter().println("<html><head><title>Vehicle Fuel by Type</title>");
        response.getWriter().println("<style>");
        response.getWriter().println("body { font-family: Arial; background-color: #FDF5E6; padding: 20px; }");
        response.getWriter().println(".container { max-width: 1000px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        response.getWriter().println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        response.getWriter().println("th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }");
        response.getWriter().println("th { background-color: #f2f2f2; }");
        response.getWriter().println(".green-button { background-color: #4CAF50; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; font-size: 16px; }");
        response.getWriter().println(".green-button:hover { background-color: #45a049; }");
        response.getWriter().println(".match { background-color: #d4edda; }");   // green
        response.getWriter().println(".mismatch { background-color: #f8d7da; }"); // red
        response.getWriter().println("</style>");
        response.getWriter().println("</head><body>");
        response.getWriter().println("<div class='container'>");
        response.getWriter().println("<h2>Search Vehicle Fuel Usage by Type</h2>");

        if (user == null || !"TRANSIT MANAGER".equalsIgnoreCase(user.getUserType())) {
            response.getWriter().println("<p><strong>Access Denied. Only Transit Managers can view this page.</strong></p>");
            response.getWriter().println("<form action='ManagerDashboardServlet'><button class='green-button'>Back</button></form>");
            return;
        }

        String selectedType = request.getParameter("vehicleType");

        // Dropdown Form
        response.getWriter().println("<form method='get'>");
        response.getWriter().println("<label for='vehicleType'><strong>Select Vehicle Type:</strong></label><br>");
        response.getWriter().println("<select name='vehicleType' id='vehicleType' required>");

        Set<String> vehicleTypes = new HashSet<>();
        List<VehicleDTO> vehicles = fleetLogic.getAllVehicles();
        for (VehicleDTO vehicle : vehicles) {
            vehicleTypes.add(vehicle.getVehicleType());
        }

        for (String type : vehicleTypes) {
            if (type.equalsIgnoreCase(selectedType)) {
                response.getWriter().println("<option selected>" + type + "</option>");
            } else {
                response.getWriter().println("<option>" + type + "</option>");
            }
        }

        response.getWriter().println("</select><br><br>");
        response.getWriter().println("<button type='submit' class='green-button'>Search</button>");
        response.getWriter().println("</form>");

        if (selectedType != null) {
            displayFuelTableByType(response, selectedType, vehicles);
        }

        response.getWriter().println("</div></body></html>");
    }

    /**
     * Displays the fuel usage comparison table for a given vehicle type.
     * Compares actual trip fuel usage to expected consumption based on duration and rate.
     * Colors the row green if expected and actual match, red otherwise.
     *
     * @param response the HttpServletResponse used to write HTML
     * @param type     the selected vehicle type to filter on
     * @param vehicles list of all vehicles to search within
     * @throws IOException if writing to the response fails
     */
    private void displayFuelTableByType(HttpServletResponse response, String type, List<VehicleDTO> vehicles) throws IOException {
        DecimalFormat df = new DecimalFormat("#.##");

        List<RouteDTO> routes = fleetLogic.getAllRoutes();
        List<TripDTO> trips = reportsLogic.getAllTrips();

        boolean found = false;

        response.getWriter().println("<h3>Fuel Usage for Type: " + type + "</h3>");
        response.getWriter().println("<table>");
        response.getWriter().println("<tr><th>Vehicle Type</th><th>Name</th><th>Route ID</th><th>Expected Fuel</th><th>Total Fuel Used</th></tr>");

        for (VehicleDTO vehicle : vehicles) {
            if (!vehicle.getVehicleType().equalsIgnoreCase(type)) continue;

            double totalUsed = 0;
            double expectedFuel = 0;

            for (TripDTO trip : trips) {
                if (trip.getVehicleId() == vehicle.getVehicleID()) {
                    totalUsed += trip.getFuelUsed();

                    for (RouteDTO route : routes) {
                        if (route.getRouteID() == trip.getRouteId()) {
                            double durationHours = route.getExpectedDuration() / 60.0;
                            expectedFuel += vehicle.getConsumptionRate() * durationHours;
                            break;
                        }
                    }
                }
            }

            // Round both values to 2 decimals for comparison
            String usedFormatted = df.format(totalUsed);
            String expectedFormatted = df.format(expectedFuel);

            String rowClass = usedFormatted.equals(expectedFormatted) ? "match" : "mismatch";
            String unit = vehicle.getFuelType().equalsIgnoreCase("Electric") ? " kWh" : " L";

            response.getWriter().println("<tr class='" + rowClass + "'>");
            response.getWriter().println("<td>" + vehicle.getVehicleType() + "</td>");
            response.getWriter().println("<td>" + vehicle.getVehicleName() + "</td>");
            response.getWriter().println("<td>" + (vehicle.getCurrentRouteID() != 0 ? vehicle.getCurrentRouteID() : "N/A") + "</td>");
            response.getWriter().println("<td>" + expectedFormatted + unit + "</td>");
            response.getWriter().println("<td>" + usedFormatted + unit + "</td>");
            response.getWriter().println("</tr>");

            found = true;
        }

        response.getWriter().println("</table>");

        if (!found) {
            response.getWriter().println("<p>No vehicles found for this type.</p>");
        }
    }
}