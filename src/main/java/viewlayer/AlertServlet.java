package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import businesslayer.VehicleAlertBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transferobjects.AlertDTO;

/**
 * Servlet for displaying and managing vehicle alerts.
 * Supports viewing active or all alerts and resolving them.
 * @author jaces
 */
@WebServlet(name = "AlertServlet", urlPatterns = {"/AlertServlet"})
public class AlertServlet extends HttpServlet {

    private FleetManagementBusinessLogic fleetLogic;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Initializes the servlet and sets up business logic.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        super.init();
        fleetLogic = new FleetManagementBusinessLogic();
    }
    /**
     * Processes both GET and POST requests to view or resolve alerts.
     * Shows active alerts if specified, otherwise all alerts.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    
    String showActive = request.getParameter("showActive");
    boolean activeOnly = "true".equals(showActive);
    
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String alertIdParam = request.getParameter("alertId");
        if (alertIdParam != null && !alertIdParam.isEmpty()) {
            int alertId = Integer.parseInt(alertIdParam);
            VehicleAlertBusinessLogic alertLogic = fleetLogic.getAlertLogic();
            alertLogic.processAlert(alertId, "RESOLVE");
        }
    }

    PrintWriter out = response.getWriter();
    try {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Alerts Management</title>");
        out.println("<style>");
        out.println("body { background-color: #FDF5E6; font-family: Arial, sans-serif; color: #333; padding: 20px; }");
        out.println("h1, h2, h3 { color: #2F4F4F; }");
        out.println("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println("th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }");
        out.println("th { background-color: #f9f9f9; position: sticky; top: 0; }");
        out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
        out.println(".active { color: red; font-weight: bold; }");

        out.println(".resolve-btn { padding: 5px 10px; background-color: #f44336; color: white; border: none; border-radius: 3px; cursor: pointer; }");
        out.println(".resolve-btn:hover { background-color: #d32f2f; }");
        out.println(".resolve-btn:disabled { background-color: #cccccc; cursor: not-allowed; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body bgcolor=\"#FDF5E6\"><center>");
        
        out.println("<h1>" + (activeOnly ? "Active Alerts" : "All Alerts") + "</h1><br>");
        
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
        
        VehicleAlertBusinessLogic alertLogic = fleetLogic.getAlertLogic();
        List<AlertDTO> alerts = activeOnly ? alertLogic.getActiveAlerts() : alertLogic.getAllAlerts();
        
        alerts.sort((a1, a2) -> {
            boolean a1Active = "ACTIVE".equals(a1.getStatus());
            boolean a2Active = "ACTIVE".equals(a2.getStatus());
            return Boolean.compare(a2Active, a1Active);
        });
        
        out.println("<br><table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Vehicle ID</th>");
        out.println("<th>Type</th>");
        out.println("<th>Reason</th>");
        out.println("<th>Time</th>");
        out.println("<th>Status</th>");
        out.println("<th>Action</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        
        if (alerts.isEmpty()) {
            out.println("<tr><td colspan='7'>No alerts found</td></tr>");
        } else {
            for (AlertDTO alert : alerts) {
                boolean isActive = "ACTIVE".equals(alert.getStatus());
                
                out.println("<tr class='" + (isActive ? "active" : "") + "'>");
                out.println("<td>" + alert.getAlertID() + "</td>");
                out.println("<td>" + alert.getVehicleID() + "</td>");
                out.println("<td>" + alert.getAlertType() + "</td>");
                out.println("<td>" + alert.getAlertReason() + "</td>");
                out.println("<td>" + dateFormat.format(alert.getAlertTime()) + "</td>");
                out.println("<td>" + alert.getStatus() + "</td>");
                
                out.println("<td>");
                if (isActive) {
                    out.println("<form method='post' action='AlertServlet'>");
                    out.println("<input type='hidden' name='alertId' value='" + alert.getAlertID() + "'>");
                    out.println("<input type='hidden' name='showActive' value='" + activeOnly + "'>");
                    out.println("<button type='submit' class='resolve-btn'>Resolve</button>");
                    out.println("</form>");
                } else {
                    out.println("Resolved");
                }
                out.println("</td>");
                
                out.println("</tr>");
            }
        }
        
        out.println("</tbody>");
        out.println("</table>");
        out.println("</center></body>");
        out.println("</html>");
    } finally {
        out.close();
    }
}

    /**
     * Handles the HTTP GET request.
     * Forwards to processRequest to display the alert list.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST request.
     * Forwards to processRequest to resolve an alert and refresh view.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a short string describing the servlet
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
