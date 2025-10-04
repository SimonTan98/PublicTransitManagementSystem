package viewlayer;


import javax.servlet.annotation.WebServlet;
import businesslayer.ReportsBusinessLogic;
import dataaccesslayer.FuelDAOImpl;
import dataaccesslayer.MaintenanceDAOImpl;
import dataaccesslayer.TripDAOImpl;
import dataaccesslayer.VehicleDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transferobjects.TripDTO;

/**
 * Servlet that generates performance reports for all operators.
 * Displays summary and detailed trip info grouped by operator.
 * 
 * @author jaces
 */
public class OperatorPerformanceServlet extends HttpServlet {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
 
    /**
     * Processes requests for both HTTP GET and POST methods.
     * Retrieves all authors from the database and displays them in a table.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    
    try {
        ReportsBusinessLogic reportsLogic = new ReportsBusinessLogic(
            new MaintenanceDAOImpl(),
            new TripDAOImpl(),
            new FuelDAOImpl(),
            new VehicleDAOImpl()
        );
        
        Map<Integer, List<TripDTO>> tripsByOperator = reportsLogic.getTripsByOperator();
        Map<Integer, Double> onTimePercentages = reportsLogic.getOperatorOnTimePercentages();
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Operator Trip Reports</title>");
            out.println("<style>");
            out.println("body { background-color: #FDF5E6; font-family: Arial, sans-serif; color: #333; padding: 20px; }");
            out.println("h1, h2, h3 { color: #2F4F4F; }");
            out.println("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
            out.println("th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }");
            out.println("th { background-color: #f9f9f9; position: sticky; top: 0; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println(".on-time { color: green; font-weight: bold; }");
            out.println(".late { color: red; font-weight: bold; }");
            out.println(".summary { margin-bottom: 40px; padding: 20px; background-color: #fff; border-radius: 8px; }");
            out.println(".operator-section { margin-bottom: 50px; padding: 20px; background-color: #ffffff; border-radius: 8px; border: 1px solid #ccc; }");
            out.println("</style>");

            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Operator Trip Reports</h1>");
            
            out.println("<div class='summary'>");
            out.println("<h2>On-Time Performance Summary</h2>");
            out.println("<table>");
            out.println("<tr><th>Operator ID</th><th>On-Time Percentage</th><th>Total Trips</th></tr>");
            
            for (Map.Entry<Integer, List<TripDTO>> entry : tripsByOperator.entrySet()) {
                int operatorId = entry.getKey();
                List<TripDTO> trips = entry.getValue();
                double percentage = onTimePercentages.getOrDefault(operatorId, 0.0);
                
                out.println("<tr>");
                out.println("<td>" + operatorId + "</td>");
                out.println("<td>" + String.format("%.2f%%", percentage) + "</td>");
                out.println("<td>" + trips.size() + "</td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
            out.println("</div>");

            out.println("<h2>Detailed Trip Information</h2>");
            
            for (Map.Entry<Integer, List<TripDTO>> entry : tripsByOperator.entrySet()) {
                int operatorId = entry.getKey();
                List<TripDTO> trips = entry.getValue();
                
                out.println("<div class='operator-section'>");
                out.println("<h3>Operator ID: " + operatorId + "</h3>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Trip ID</th>");
                out.println("<th>Vehicle ID</th>");
                out.println("<th>Route ID</th>");
                out.println("<th>Start Time</th>");
                out.println("<th>End Time</th>");
                out.println("<th>Duration (min)</th>");
                out.println("<th>Status</th>");
                out.println("<th>Fuel Used (L)</th>");
                out.println("</tr>");
                
                for (TripDTO trip : trips) {

                    Long durationMinutes = null;
                    if (trip.getStartTime() != null && trip.getEndTime() != null) {
                        durationMinutes = (trip.getEndTime().getTime() - trip.getStartTime().getTime()) / (60 * 1000);
                    }
                    
                    out.println("<tr>");
                    out.println("<td>" + trip.getTripId() + "</td>");
                    out.println("<td>" + trip.getVehicleId() + "</td>");
                    out.println("<td>" + trip.getRouteId() + "</td>");
                    out.println("<td>" + dateFormat.format(trip.getStartTime()) + "</td>");
                    out.println("<td>" + (trip.getEndTime() != null ? dateFormat.format(trip.getEndTime()) : "N/A") + "</td>");
                    out.println("<td>" + (durationMinutes != null ? durationMinutes : "N/A") + "</td>");

                    out.println("<td class='" + (trip.isOnTime() ? "on-time" : "late") + "'>");
                    out.println(trip.isOnTime() ? "On Time" : "Late");
                    out.println("</td>");
                    
                    out.println("<td>" + String.format("%.2f", trip.getFuelUsed()) + "</td>");
                    out.println("</tr>");
                }
                
                out.println("</table>");
                out.println("</div>");
            }
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
            out.println("</center></body>");
            out.println("</html>");
        }
    } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
            "Error generating operator reports: " + e.getMessage());
    }
}
    /**
     * Handles the HTTP GET method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles the HTTP POST method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
