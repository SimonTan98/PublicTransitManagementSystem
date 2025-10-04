package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import constants.OperatorType;
import transferobjects.UserDTO;
import transferobjects.VehicleDTO;
import transferobjects.RouteDTO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet for assigning routes to vehicles.
 * Only accessible by users with Transit Manager privileges.
 */
@WebServlet("/AssignRouteServlet")
public class AssignRouteServlet extends HttpServlet {

    private FleetManagementBusinessLogic businessLogic;

/**
 * Initializes the servlet and sets up business logic layer.
 *
 * @throws ServletException if servlet initialization fails
 */
    @Override
    public void init() throws ServletException {
        businessLogic = new FleetManagementBusinessLogic();
    }

/**
 * Handles HTTP GET requests to display the route assignment form.
 *
 * @param request HTTP request
 * @param response HTTP response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        response.setContentType("text/html");
        response.getWriter().println("<html><head><title>Assign Route</title></head><body BGCOLOR=\"#FDF5E6\">");
        response.getWriter().println("<center><h2>Assign Route to Vehicle</h2>");

        if (user == null || !OperatorType.TRANSITMANAGER.equalsIgnoreCase(user.getUserType())) {
            response.getWriter().println("<h3>Error: Access denied. Manager privileges required.</h3>");
            response.getWriter().println("<form action='ManagerDashboardServlet' method='get'><button>Back to Dashboard</button></form>");
        } else {
            displayAssignRouteForm(response);
        }

        response.getWriter().println("</center></body></html>");
    }

/**
 * Handles HTTP POST requests to process route assignment.
 *
 * @param request HTTP request
 * @param response HTTP response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        response.setContentType("text/html");
        response.getWriter().println("<html><head><title>Route Assignment Result</title></head><body BGCOLOR=\"#FDF5E6\"><center>");

        if (user == null || !OperatorType.TRANSITMANAGER.equalsIgnoreCase(user.getUserType())) {
            response.getWriter().println("<h3>Error: Access denied. Manager privileges required.</h3>");
        } else {
            String vehicleId = request.getParameter("vehicleId");
            String routeId = request.getParameter("routeId");

            if (vehicleId == null || routeId == null) {
                response.getWriter().println("<h3>Error: Vehicle and Route selection are required.</h3>");
            } else {
                try {
                    int vId = Integer.parseInt(vehicleId);
                    int rId = Integer.parseInt(routeId);

                    VehicleDTO vehicle = businessLogic.getVehicleById(vId);
                    List<RouteDTO> routes = businessLogic.getAllRoutes();
                    RouteDTO selectedRoute = routes.stream()
                        .filter(r -> r.getRouteID() == rId)
                        .findFirst().orElse(null);

                    if (vehicle != null && selectedRoute != null) {
                        // TODO: Update vehicle's current route in DB if needed
                        response.getWriter().println("<h3>Route assigned successfully!</h3>");
                        response.getWriter().println("<p><strong>Vehicle:</strong> " + vehicle.getVehicleName() + " (" + vehicle.getVehicleType() + ")</p>");
                        response.getWriter().println("<p><strong>Route ID:</strong> " + selectedRoute.getRouteID() + "</p>");
                        response.getWriter().println("<p><strong>Distance:</strong> " + selectedRoute.getDistance() + " km</p>");
                        response.getWriter().println("<p><strong>Expected Duration:</strong> " + selectedRoute.getExpectedDuration() + " minutes</p>");
                    } else {
                        response.getWriter().println("<h3>Failed to assign route. Vehicle or route not found.</h3>");
                    }
                } catch (NumberFormatException e) {
                    response.getWriter().println("<h3>Error: Invalid vehicle or route ID format.</h3>");
                }
            }
        }

        response.getWriter().println("<br><form action='AssignRouteServlet' method='get'><button>Assign Another Route</button></form>");
        response.getWriter().println("<form action='ManagerDashboardServlet' method='get'><button>Back to Dashboard</button></form>");
        response.getWriter().println("</center></body></html>");
    }
/**
 * Displays the route assignment form with available vehicles and routes.
 *
 * @param response HTTP response to write the form HTML
 * @throws IOException if writing to response fails
 */
    private void displayAssignRouteForm(HttpServletResponse response) throws IOException {
        try {
            List<VehicleDTO> vehicles = businessLogic.getAllVehicles();
            List<RouteDTO> routes = businessLogic.getAllRoutes();

            response.getWriter().println("<form action='AssignRouteServlet' method='post'>");
            response.getWriter().println("<table cellpadding='10'>");

            // Vehicle selection
            response.getWriter().println("<tr><td><strong>Select Vehicle:</strong></td><td>");
            response.getWriter().println("<select name='vehicleId' required style='width: 300px;'>");
            response.getWriter().println("<option value=''>-- Choose a vehicle --</option>");
            for (VehicleDTO vehicle : vehicles) {
                String currentRoute = (vehicle.getCurrentRouteID() != 0)
                    ? " (Currently on Route " + vehicle.getCurrentRouteID() + ")"
                    : " (No current route)";
                response.getWriter().println("<option value='" + vehicle.getVehicleID() + "'>"
                    + vehicle.getVehicleName() + " - " + vehicle.getVehicleType() + currentRoute + "</option>");
            }
            response.getWriter().println("</select></td></tr>");

            // Route selection
            response.getWriter().println("<tr><td><strong>Select Route:</strong></td><td>");
            response.getWriter().println("<select name='routeId' required style='width: 300px;'>");
            response.getWriter().println("<option value=''>-- Choose a route --</option>");
            for (RouteDTO route : routes) {
                response.getWriter().println("<option value='" + route.getRouteID() + "'>Route "
                    + route.getRouteID() + " (" + route.getDistance() + " km, "
                    + route.getExpectedDuration() + " min)</option>");
            }
            response.getWriter().println("</select></td></tr>");

            response.getWriter().println("<tr><td colspan='2' style='text-align: center;'>");
            response.getWriter().println("<input type='submit' value='Assign Route' style='padding: 10px 20px; font-size: 16px;'>");
            response.getWriter().println("</td></tr>");

            response.getWriter().println("</table>");
            response.getWriter().println("</form>");

            // Display current assignments
            response.getWriter().println("<br><hr><br>");
            response.getWriter().println("<h3>Current Vehicle-Route Assignments</h3>");
            displayCurrentAssignments(response, vehicles, routes);

            response.getWriter().println("<br><form action='ManagerDashboardServlet' method='get'>");
            response.getWriter().println("<button style='padding: 10px 20px;'>Back to Dashboard</button>");
            response.getWriter().println("</form>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<p>Error loading assignment form: " + e.getMessage() + "</p>");
            response.getWriter().println("<br><form action='ManagerDashboardServlet' method='get'>");
            response.getWriter().println("<button style='padding: 10px 20px;'>Back to Dashboard</button>");
            response.getWriter().println("</form>");
        }
    }
/**
 * Displays the list of vehicles with their currently assigned routes.
 *
 * @param response HTTP response to write the HTML
 * @param vehicles list of all vehicles
 * @param routes list of all routes
 * @throws IOException if writing to response fails
 */
    private void displayCurrentAssignments(HttpServletResponse response, List<VehicleDTO> vehicles, List<RouteDTO> routes) throws IOException {
        response.getWriter().println("<table border='1' cellpadding='8' style='border-collapse: collapse;'>");
        response.getWriter().println("<tr style='background-color: #e6e6e6;'>");
        response.getWriter().println("<th>Vehicle ID</th><th>Vehicle Name</th><th>Type</th>");
        response.getWriter().println("<th>Current Route</th><th>Distance (km)</th><th>Duration (min)</th>");
        response.getWriter().println("</tr>");

        for (VehicleDTO vehicle : vehicles) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + vehicle.getVehicleID() + "</td>");
            response.getWriter().println("<td>" + vehicle.getVehicleName() + "</td>");
            response.getWriter().println("<td>" + vehicle.getVehicleType() + "</td>");

            if (vehicle.getCurrentRouteID() == 0) {
                response.getWriter().println("<td>No Route Assigned</td><td>-</td><td>-</td>");
            } else {
                response.getWriter().println("<td>Route " + vehicle.getCurrentRouteID() + "</td>");

                RouteDTO currentRoute = routes.stream()
                    .filter(r -> r.getRouteID() == vehicle.getCurrentRouteID())
                    .findFirst().orElse(null);

                if (currentRoute != null) {
                    response.getWriter().println("<td>" + currentRoute.getDistance() + "</td>");
                    response.getWriter().println("<td>" + currentRoute.getExpectedDuration() + "</td>");
                } else {
                    response.getWriter().println("<td>N/A</td><td>N/A</td>");
                }
            }
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</table>");
    }
}
