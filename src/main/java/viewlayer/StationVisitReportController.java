
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
import transferobjects.UserDTO;
import transferobjects.VehicleDTO;
import transferobjects.VehicleStationDTO;

/**
 * Servlet that shows detailed station visit reports for a specific vehicle.
 * 
 * @author Simon Tan
 */
public class StationVisitReportController extends HttpServlet {
    
    /**
     * Handles both GET and POST requests.
     * Retrieves the list of station visits for a given vehicle and displays them.
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
        
        int vehicleId = Integer.valueOf(request.getParameter("vehicleId"));
        
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        List<VehicleStationDTO> visits = logic.getStationVisitsByVehicleId(vehicleId);
        
        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Station Visit Reports</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"#FDF5E6\"><center>");
            out.println("<h1>Station Visit Reports for Vehicle " + vehicleId + "</h1>");
            out.println("<form action=\"StationVisitReportController\">");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Station ID</th>");
            out.println("<th>Arrival Time</th>");
            out.println("<th>Departure Time</th>");
            out.println("</tr>");
            ListIterator<VehicleStationDTO> iter = visits.listIterator();
            while(iter.hasNext()) {
                VehicleStationDTO visit = iter.next();
                out.println("<tr>");            
                out.println("<td>" + visit.getStationId() + "</td>");
                out.println("<td>" + visit.getArrivalTime() + "</td>");
                out.println("<td>" + visit.getDepartureTime() + "</td>");
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
