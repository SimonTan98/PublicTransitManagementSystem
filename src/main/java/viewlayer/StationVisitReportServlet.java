
package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import constants.OperatorType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.StationDTO;
import transferobjects.UserDTO;
import transferobjects.VehicleDTO;
import transferobjects.VehicleStationDTO;

/**
 * Servlet that displays a list of vehicles and allows viewing their station visit reports.
 * 
 * @author Simon Tan
 */
public class StationVisitReportServlet extends HttpServlet {
    
    /**
     * Handles both GET and POST requests.
     * Retrieves all vehicles and displays them in a table with buttons to view each vehicle's station visits.
     * Redirects to login if no active session.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("/LoginServlet");
        }
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null; 
        String userType = user.getUserType();
        
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        List<VehicleDTO> vehicles = logic.getAllVehicles();
        
//        List<VehicleStationDTO> stationVisits = logic.getStationVisitsByVehicleId(vehicleId);
        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Station Visit Reports</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"#FDF5E6\">");
            out.println("<center>");
            out.println("<h1>Station Visit Reports</h1>");
            out.println("<form action=\"StationVisitReportController\">");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Vehicle ID</th>");
            out.println("<th>Vehicle Name</th>");
            out.println("<th>View Report</th>");
            out.println("</tr>");
            ListIterator<VehicleDTO> iter = vehicles.listIterator();
            while(iter.hasNext()) {
                VehicleDTO vehicle = iter.next();
                out.println("<tr>");            
                out.println("<td>" + vehicle.getVehicleID() + "</td>");
                out.println("<td>" + vehicle.getVehicleName() + "</td>");
                out.println("<td><button type=\"submit\" name=\"vehicleId\" value=\"" + vehicle.getVehicleID() + "\">View</button></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</form>");
            
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
            out.println("</center></body>");
        }
        
    }
    /**
     * Handles GET requests by forwarding to processRequest.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    /**
     * Handles POST requests by forwarding to processRequest.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
}
