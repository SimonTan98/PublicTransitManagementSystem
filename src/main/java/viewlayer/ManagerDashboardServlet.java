package viewlayer;

import transferobjects.UserDTO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for displaying the Manager Dashboard page with options.
 * 
 * @Santiago Castro
 */
public class ManagerDashboardServlet extends HttpServlet {

    /**
     * Handles GET requests to display the manager dashboard.
     * 
     * @param request  HttpServletRequest from client
     * @param response HttpServletResponse to send back the page
     * @throws ServletException on servlet error
     * @throws IOException      on I/O error
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        response.setContentType("text/html");

        try(PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Manager Dashboard</title></head><body BGCOLOR=\"#FDF5E6\">");

            String userName = user.getName();
            out.println("<h2>Welcome, " + userName + "</h2>");

            out.println("<center>");
            out.println("<h2>Manager Dashboard</h2>");

            out.println("<div style='display: flex; flex-wrap: wrap; justify-content: center; gap: 10px; max-width: 800px;'>");

            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='addVehicle' style='width: 180px;'>Add Vehicle</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='vehicleStatus' style='width: 180px;'>Set Vehicle Status</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='getVehicleById' style='width: 180px;'>Get Vehicle By ID</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='costReport' style='width: 180px;'>Cost Reports</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='updateVehicleLocation' style='width: 180px;'>Update Vehicle Location</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='logStationVisit' style='width: 180px;'>Log Station Visit</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='stationVisitReport' style='width: 180px;'>Station Visit Reports</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='refuel' style='width: 180px;'>Refuel a Vehicle</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='fuelUsageReport' style='width: 180px;'>Fuel/Energy Usage Report</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='maintenance' style='width: 180px;'>Maintenance Dashboard</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='logBreak' style='width: 180px;'>Log Break</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='operatorPerformance' style='width: 180px;'>Review Operator Performance</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='seeAlerts' style='width: 180px;'>View Alerts</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='logTrip' style='width: 180px;'>Log a Trip</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='getAllVehicles' style='width: 180px;'>Vehicles Table</button></form>");
            out.println("<form action='LogoutServlet' method='get'><button style='width: 180px;'>Logout</button></form>");

            out.println("</div>");
            out.println("</center>");
            out.println("</body></html>");
        }
    }
}