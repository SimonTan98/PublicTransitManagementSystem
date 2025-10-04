package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import constants.MaintenancePurpose;

import transferobjects.MaintenanceDTO;
import transferobjects.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet for scheduling and managing vehicle maintenance.
 * Displays dashboard, forms, and handles start/end actions.
 * 
 * @author Santiago Castro
 */
public class ScheduleMaintenanceServlet extends HttpServlet {

    private final FleetManagementBusinessLogic businessLogic = new FleetManagementBusinessLogic();

    /**
     * Handles GET requests to display the dashboard or forms based on the action.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            showDashboard(request, response);
        } else {
            switch (action) {
                case "start":
                    showStartForm(request, response);
                    break;
                case "end":
                    showEndForm(request, response);
                    break;
                default:
                    showDashboard(request, response);
            }
        }
    }

    /**
     * Displays the maintenance dashboard with ongoing maintenance data.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input/output error occurs
     */
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

    HttpSession session = request.getSession(false);
    UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;
    String userName = (user != null) ? user.getName() : "Manager";

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>Maintenance Dashboard</title></head><body BGCOLOR=\"#FDF5E6\">");
    out.println("<h2>Welcome, " + userName + "</h2>");
    out.println("<center><h2>Maintenance Dashboard</h2>");

    // Button panel
    out.println("<div style='display: flex; gap: 10px; justify-content: center; margin-bottom: 20px;'>");
    out.println("<form method='get'><input type='hidden' name='action' value='start'><button style='width: 180px;'>Start Maintenance</button></form>");
    out.println("<form method='get'><input type='hidden' name='action' value='end'><button style='width: 180px;'>End Maintenance</button></form>");
    out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
    out.println("</div>");

    // Embedded table of ongoing maintenance
    List<MaintenanceDTO> ongoing = businessLogic.getOngoingMaintenanceRequests();
    out.println("<h3>Ongoing Maintenance Requests</h3>");
    if (ongoing.isEmpty()) {
        out.println("<p>No ongoing maintenance.</p>");
    } else {
        out.println("<table border='1' style='margin: auto;'>");
        out.println("<tr><th>ID</th><th>Vehicle</th><th>Purpose</th><th>Cost</th><th>Start Time</th></tr>");
        for (MaintenanceDTO m : ongoing) {
            out.println("<tr><td>" + m.getMaintenanceId() + "</td><td>" + m.getVehicleId() + "</td><td>" +
                    m.getPurpose() + "</td><td>" + m.getCost() + "</td><td>" + m.getStartTime() + "</td></tr>");
        }
        out.println("</table>");
    }

    out.println("</center></body></html>");
}

    /**
     * Displays the form to start a new maintenance request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input/output error occurs
     */
    private void showStartForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><title>Start Maintenance</title></head><body BGCOLOR=\"#FDF5E6\"><center>");
        out.println("<h2>Start Maintenance</h2>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='action' value='start'>");
        out.println("Vehicle ID: <input type='number' name='vehicleId'><br><br>");
        out.println("Purpose: <select name='purpose'>");
        out.println("<option value='" + MaintenancePurpose.REPAIRS + "'>" + MaintenancePurpose.REPAIRS + "</option>");
        out.println("<option value='" + MaintenancePurpose.CLEANING + "'>" + MaintenancePurpose.CLEANING + "</option>");
        out.println("<option value='" + MaintenancePurpose.INSPECTIONS + "'>" + MaintenancePurpose.INSPECTIONS + "</option>");
        out.println("</select><br><br>");
        out.println("Cost: <input type='number' step='0.01' name='cost'><br><br>");
        out.println("Start Time: <input type='datetime-local' name='startTime'><br><br>");
        out.println("<input type='submit' value='Start Maintenance'>");
        out.println("</form>");
        out.println("<br>");
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
        out.println("</center></body></html>");
    }

    /**
     * Displays the form to end an ongoing maintenance request.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an input/output error occurs
     */
    private void showEndForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><title>End Maintenance</title></head><body BGCOLOR=\"#FDF5E6\"><center>");
        out.println("<h2>End Maintenance</h2>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='action' value='end'>");
        out.println("Vehicle ID: <input type='number' name='vehicleId'><br><br>");
        out.println("<input type='submit' value='End Maintenance'>");
        out.println("</form>");
        out.println("<br>");
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
        out.println("</center></body></html>");
    }

    /**
     * Handles POST requests to start or end maintenance based on the action.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><title>Maintenance Result</title></head><body BGCOLOR=\"#FDF5E6\"><center>");

        try {
            if ("start".equals(action)) {
                int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
                String purpose = request.getParameter("purpose");
                double cost = Double.parseDouble(request.getParameter("cost"));

                boolean success = businessLogic.startMaintenance(vehicleId, purpose, cost);
                out.println(success ? "<h3>Maintenance started successfully.</h3>" :
                                      "<h3>Failed to start maintenance.</h3>");

            } else if ("end".equals(action)) {
                int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));

                boolean success = businessLogic.endMaintenance(vehicleId);
                out.println(success ? "<h3>Maintenance ended and vehicle refreshed successfully.</h3>" :
                                      "<h3>Failed to end maintenance or refresh vehicle.</h3>");

            } else {
                out.println("<h3>Unknown action.</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }

        out.println("<br>");
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
        out.println("</center></body></html>");
    }
}