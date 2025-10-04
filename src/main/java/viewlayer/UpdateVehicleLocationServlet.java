
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
 * Servlet to display and update vehicle locations.
 * Shows a form for updating a vehicle's location and lists current locations.
 * 
 * @author Simon Tan
 */
public class UpdateVehicleLocationServlet extends HttpServlet {
    
    /**
     * Handles both GET and POST requests.
     * Displays the update form and current vehicle locations.
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
        
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        List<LocationDTO> locations = logic.getAllVehicleLocations();
        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Update Vehicle Location</title>");
            out.println("</head>");
            out.println("<body BGCOLOR=\"#FDF5E6\">");
            out.println("<center>");
            out.println("<h1>Update Vehicle Location</h1>");
            out.println("<form action=\"UpdateLocationController\">");
            out.println("<label for=\"vehicleId\">Enter Vehicle ID</label>");
            out.println("<input type=\"number\" id=\"vehicleId\" name=\"vehicleId\">");
            out.println("<label for=\"latitude\">Latitude</label>");
            out.println("<input type=\"text\" id=\"latitude\" name=\"latitude\">");
            out.println("<label for=\"longitude\">Longitude</label>");
            out.println("<input type=\"text\" id=\"longitude\" name=\"longitude\">");
            out.println("<input type=\"submit\" value=\"Submit\">");
            out.println("</form>");
            out.println("<div>");
            out.println("<h2>Current Vehicle Locations</h2>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Vehicle ID</th>");
            out.println("<th>Latitude</th>");
            out.println("<th>Longitude</th>");
            out.println("<th>Last Updated</th>");
            out.println("</tr>");
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
            out.println("</div>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
            out.println("</center>");
            out.println("</body>");
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


