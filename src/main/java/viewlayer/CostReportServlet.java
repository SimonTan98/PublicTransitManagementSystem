package viewlayer;

import businesslayer.ReportsBusinessLogic;
import dataaccesslayer.*;
import transferobjects.FuelDTO;
import transferobjects.MaintenanceDTO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet for generating and displaying cost reports for maintenance and fuel usage.
 * @author Santiago
 * @author jaces
 */
public class CostReportServlet extends HttpServlet {

    private ReportsBusinessLogic reportsLogic;

    /**
     * Initializes the servlet and sets up the business logic with required DAOs.
     *
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        reportsLogic = new ReportsBusinessLogic(
            new MaintenanceDAOImpl(),
            new TripDAOImpl(),
            new FuelDAOImpl(),
            new VehicleDAOImpl()
        );
    }

    /**
     * Handles GET requests and shows either the maintenance costs or fuel usage report.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            showMaintenanceCosts(response);
        } else {
            switch (action) {
                case "fuel":
                    showFuelUsage(response);
                    break;
                default:
                    showMaintenanceCosts(response);
            }
        }
    }
    /**
     * Displays the maintenance cost report including total cost.
     *
     * @param response the HTTP response
     * @throws IOException if an input/output error occurs
     */
    private void showMaintenanceCosts(HttpServletResponse response) throws IOException {
        List<MaintenanceDTO> maintenanceList = reportsLogic.getAllMaintenance();
        double totalMaintenanceCost = reportsLogic.getTotalMaintenanceCost();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Maintenance Costs</title></head><body BGCOLOR=\"#FDF5E6\">");
        out.println("<center><h2>Fleet Cost Report</h2>");

        // Button panel
        out.println("<div style='display: flex; gap: 10px; justify-content: center; margin-bottom: 20px;'>");
        out.println("<form method='get'><input type='hidden' name='action' value='fuel'><button style='width: 180px;'>Fuel Usage</button></form>");
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
        out.println("</div>");

        // Maintenance Table
        out.println("<h3>Maintenance Expenses</h3>");
        if (maintenanceList.isEmpty()) {
            out.println("<p>No maintenance records found.</p>");
        } else {
            out.println("<table border='1' style='margin: auto;'>");
            out.println("<tr><th>Maintenance ID</th><th>Vehicle ID</th><th>Purpose</th><th>Cost</th><th>Start Time</th><th>End Time</th></tr>");
            for (MaintenanceDTO m : maintenanceList) {
                out.println("<tr><td>" + m.getMaintenanceId() + "</td><td>" + m.getVehicleId() + "</td><td>" +
                        m.getPurpose() + "</td><td>$" + String.format("%.2f", m.getCost()) + "</td><td>" +
                        m.getStartTime() + "</td><td>" + (m.getEndTime() != null ? m.getEndTime() : "Ongoing") + "</td></tr>");
            }
            out.println("</table>");
        }

        // Total Cost
        out.println("<p><strong>Total Maintenance Cost:</strong> $" + String.format("%.2f", totalMaintenanceCost) + "</p>");
        out.println("</center></body></html>");
    }
    /**
     * Displays the fuel usage report including average efficiency.
     *
     * @param response the HTTP response
     * @throws IOException if an input/output error occurs
     */
    private void showFuelUsage(HttpServletResponse response) throws IOException {
        List<FuelDTO> fuelReport = reportsLogic.getFuelConsumptionReport();
        double avgEfficiency = reportsLogic.getAverageFuelEfficiency();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Fuel Usage</title></head><body BGCOLOR=\"#FDF5E6\">");
        out.println("<center><h2>Fleet Cost Report</h2>");

        // Button panel
        out.println("<div style='display: flex; gap: 10px; justify-content: center; margin-bottom: 20px;'>");
        out.println("<form method='get'><input type='hidden' name='action' value=''><button style='width: 180px;'>Maintenance Costs</button></form>");
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
        out.println("</div>");

        // Fuel Table
        out.println("<h3>Fuel/Energy Usage</h3>");
        if (fuelReport.isEmpty()) {
            out.println("<p>No fuel consumption data available.</p>");
        } else {
            out.println("<table border='1' style='margin: auto;'>");
            out.println("<tr><th>Vehicle ID</th><th>Fuel Used</th><th>Efficiency</th></tr>");
            for (FuelDTO fuel : fuelReport) {
                out.println("<tr><td>" + fuel.getVehicleId() + "</td><td>" +
                        fuel.getFuelUsed() + "</td><td>" +
                        fuel.getActualFuelEfficiency() + "</td></tr>");
            }
            out.println("</table>");
        }

        out.println("<p>Average Fuel Efficiency: " + String.format("%.2f", avgEfficiency) + " km/l</p>");
        out.println("</center></body></html>");
    }

}