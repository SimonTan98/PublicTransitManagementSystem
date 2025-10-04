
package viewlayer;

import businesslayer.ReportsBusinessLogic;
import dataaccesslayer.FuelDAOImpl;
import dataaccesslayer.MaintenanceDAOImpl;
import dataaccesslayer.TripDAOImpl;
import dataaccesslayer.VehicleDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.TripDTO;
import transferobjects.UserDTO;

/**
 * Servlet that shows an operator's performance report.
 * Displays summary and trip details for the logged-in user.
 * 
 * @author jaces
 */
@WebServlet(name = "OperatorPerformanceIndividualServlet", urlPatterns = {"/OperatorPerformanceIndividualServlet"})
public class OperatorPerformanceIndividualServlet extends HttpServlet {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            ReportsBusinessLogic reportsLogic = new ReportsBusinessLogic(
                new MaintenanceDAOImpl(),
                new TripDAOImpl(),
                new FuelDAOImpl(),
                new VehicleDAOImpl()
            );
            
            List<TripDTO> operatorTrips = reportsLogic.getTripsForOperator(user.getUserID());
            double onTimePercentage = reportsLogic.getOnTimePercentageForOperator(user.getUserID());
            
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>My Performance Report</title>");
                out.println("<style>");
                out.println("body { background-color: #FDF5E6; font-family: Arial, sans-serif; color: #333; padding: 20px; }");
                out.println("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
                out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
                out.println("th { background-color: #f2f2f2; position: sticky; top: 0; }");
                out.println(".on-time { color: green; }");
                out.println(".late { color: red; }");
                out.println(".summary { margin-bottom: 30px; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>My Performance Report</h1>");
                
                out.println("<div class='summary'>");
                out.println("<h2>Performance Summary</h2>");
                out.println("<table>");
                out.println("<tr><th>Operator ID</th><th>Name</th><th>On-Time Percentage</th><th>Total Trips</th></tr>");
                
                out.println("<tr>");
                out.println("<td>" + user.getUserID() + "</td>");
                out.println("<td>" + user.getName() + "</td>");
                out.println("<td>" + String.format("%.2f%%", onTimePercentage) + "</td>");
                out.println("<td>" + operatorTrips.size() + "</td>");
                out.println("</tr>");
                
                out.println("</table>");
                out.println("</div>");
                
                out.println("<h2>My Trip Details</h2>");
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
                
                for (TripDTO trip : operatorTrips) {
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
                out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error generating operator report: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     * Handles the HTTP <code>POST</code> method.
     *
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
