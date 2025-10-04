
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
import transferobjects.LocationDTO;
import transferobjects.UserDTO;

/**
 * Servlet to handle updating a vehicle's location.
 * Processes location update requests and displays current vehicle locations.
 * 
 * @author Simon Tan
 */
public class UpdateLocationController extends HttpServlet {
    
    /**
     * Handles both GET and POST requests.
     * Updates vehicle location with given parameters and displays all locations.
     * Redirects to login if no session exists.
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
        double latitude = Double.valueOf(request.getParameter("latitude"));
        double longitude = Double.valueOf(request.getParameter("longitude"));
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        
        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Update Vehicle Location</title>");
            out.println("</head>");
            out.println("<body BGCOLOR=\"#FDF5E6\"><center>");
            out.println("<h1>Update Vehicle Location</h1>");
            if(logic.updateLocation(vehicleId, latitude, longitude)) {
                out.println("Location updated successfully");
            }
            else {
                out.println("Unable to update location. Please try again.");
            }
            out.println("<h2>Current Vehicle Locations</h2>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Vehicle ID</th>");
            out.println("<th>Latitude</th>");
            out.println("<th>Longitude</th>");
            out.println("<th>Last Updated</th>");
            out.println("</tr>");
            List<LocationDTO> locations = logic.getAllVehicleLocations();
            if(locations.size() != 0) {
                ListIterator<LocationDTO> iter = locations.listIterator();
                while(iter.hasNext()) {
                    LocationDTO location = iter.next();
                    out.println("<tr>");
                    out.println("<td>" + location.getVehicleId() + "</td>");
                    out.println("<td>" + location.getLatitude() + "</td>");
                    out.println("<td>" + location.getLongitude() + "</td>");
                    out.println("<td>" + location.getUpdated() + "</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
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


