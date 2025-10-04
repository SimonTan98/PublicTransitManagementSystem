package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.AlertDTO;
import transferobjects.CredentialsDTO;
import transferobjects.UserDTO;

/**
 * Servlet for completing a trip and recording its details.
 * Allows a driver to submit vehicle, route, time, and fuel usage.
 * Also displays any active alerts for the vehicle after submission.
 * 
 * @author jaces
 */
public class CompleteTripServlet extends HttpServlet {

    
    /**
     * Handles HTTP GET requests.
     * Delegates to processRequest to display the form.
     * @param request  the HTTP request
     * @param response the HTTP response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles HTTP POST requests.
     * Delegates to processRequest to handle form submission and trip completion.
     * @param request  the HTTP request
     * @param response the HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
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

        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
            
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Complete Trip</title>");            
            out.println("<style>");
            out.println("button { margin: 5px; padding: 8px 15px; }");
            out.println("input { margin: 5px; }");
            out.println(".alert-container { margin-top: 20px; }");
            out.println(".alert { padding: 15px; margin: 10px 0; border: 1px solid; border-radius: 4px; }");
            out.println(".alert-warning { color: #856404; background-color: #fff3cd; border-color: #ffeeba; }");
            out.println(".alert-danger { color: #721c24; background-color: #f8d7da; border-color: #f5c6cb; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body bgcolor=\"#FDF5E6\">");
            out.println("<center>");
            out.println("<h1>Complete Trip</h1>");

            if ("POST".equals(request.getMethod())) {
                try {
                    int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
                    FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();

                    boolean success = logic.completeTrip(
                            user.getUserID(), 
                            Integer.parseInt(request.getParameter("vehicleId")), 
                            Integer.parseInt(request.getParameter("routeId")),
                            LocalDateTime.parse(request.getParameter("startTime")),
                            LocalDateTime.parse(request.getParameter("endTime")), 
                            Double.parseDouble(request.getParameter("fuelUsed"))
                    );

                    out.println("<p style='color:green'>Trip completed successfully!</p>");

                } catch(Exception e) {
                    out.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
                }
            }

            out.println("<form method='POST'>");
            out.println("Vehicle ID: <input type='text' name='vehicleId' " + 
                (request.getParameter("vehicleId") != null ? "value='" + request.getParameter("vehicleId") + "'" : "") + " required><br>");
            out.println("Route ID: <input type='text' name='routeId' " + 
                (request.getParameter("routeId") != null ? "value='" + request.getParameter("routeId") + "'" : "") + " required><br>");
            out.println("Start Time: <input type='datetime-local' name='startTime' " + 
                (request.getParameter("startTime") != null ? "value='" + request.getParameter("startTime") + "'" : "") + " required><br>");
            out.println("End Time: <input type='datetime-local' name='endTime' " + 
                (request.getParameter("endTime") != null ? "value='" + request.getParameter("endTime") + "'" : "") + " required><br>");
            out.println("Fuel Used: <input type='text' name='fuelUsed' " + 
                (request.getParameter("fuelUsed") != null ? "value='" + request.getParameter("fuelUsed") + "'" : "") + " required><br>");

            out.println("<input type='submit' value='Complete the Trip'>");
            out.println("</form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");

            if ("POST".equals(request.getMethod())) {
                try {
                    int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
                    FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
                    List<AlertDTO> alerts = logic.getAlertLogic().getAlertsByVehicle(vehicleId).stream()
                            .filter(alert -> "ACTIVE".equals(alert.getStatus()))
                            .toList();
                    if (!alerts.isEmpty()) {
                        out.println("<div style='margin-top: 20px;'>");
                        out.println("<h3>Vehicle Alerts</h3>");
                        for (AlertDTO alert : alerts) {
                            String alertClass = alert.getAlertType().equals("MAINTENANCE") ? 
                                               "alert-warning" : "alert-danger";
                            out.println("<div class='alert " + alertClass + "'>");
                            out.println("<strong>" + alert.getAlertType() + "</strong><br>");
                            out.println("Reason: " + alert.getAlertReason() + "<br>");
                            out.println("Time: " + alert.getAlertTime() + "<br>");
                            out.println("</div>");
                        }
                        out.println("</div>");
                    }
                } catch(Exception e) {
                    out.println("<p style='color:red'>Error loading alerts: " + e.getMessage() + "</p>");
                }
            }

            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
}
