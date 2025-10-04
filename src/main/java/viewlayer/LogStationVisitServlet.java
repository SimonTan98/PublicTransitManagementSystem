
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

/**
 * Servlet to display a form for logging station visits
 * and to show a list of all stations.
 * 
 * @author Simon Tan
 */
public class LogStationVisitServlet extends HttpServlet {
    
    /**
     * Processes both GET and POST requests to show the station visit form
     * and list of stations.
     * 
     * @param request  the HttpServletRequest object that contains the request the client made
     * @param response the HttpServletResponse object to assist in sending a response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
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
        List<StationDTO> stations = logic.getAllStations();
        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Log Station Visit</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"#FDF5E6\">");
            out.println("<center>");
            out.println("<h1>Log a Station Visit</h1>");
            out.println("<form action=\"InsertStationVisitController\">");
            out.println("<label for=\"vehicleId\">Enter Vehicle ID</label>");
            out.println("<input type=\"number\" id=\"vehicleId\" name=\"vehicleId\">");
            out.println("<label for=\"stationId\">Enter Station ID</label>");
            out.println("<input type=\"number\" id=\"stationId\" name=\"stationId\">");
            out.println("<label for=\"arrivalTime\">Arrival Time</label>");
            out.println("<input type=\"datetime-local\" name=\"arrivalTime\" id=\"arrivalTime\">");
            out.println("<label for=\"departureTime\">Departure Time</label>");
            out.println("<input type=\"datetime-local\" name=\"departureTime\" id=\"departureTime\">");
            out.println("<input type=\"submit\" value=\"Submit\">");
            out.println("</form>");
            out.println("<div>");
            out.println("<h2>All Stations</h2>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Station ID</th>");
            out.println("<th>Name</th>");
            out.println("</tr>");
            if(stations.size() != 0) {
                ListIterator<StationDTO> iter = stations.listIterator();
                while(iter.hasNext()) {
                    StationDTO station = iter.next();
                    out.println("<tr>");
                    out.println("<td>" + station.getStationID() + "</td>");
                    out.println("<td>" + station.getStationName() + "</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("</div>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
            out.println("</center></body>");
        }
        
    }
    /**
     * Handles the HTTP GET method by forwarding to processRequest.
     * 
     * @param request  the HttpServletRequest from the client
     * @param response the HttpServletResponse to send the result
     * @throws ServletException on servlet error
     * @throws IOException on I/O error
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    /**
     * Handles the HTTP POST method by forwarding to processRequest.
     * 
     * @param request  the HttpServletRequest from the client
     * @param response the HttpServletResponse to send the result
     * @throws ServletException on servlet error
     * @throws IOException on I/O error
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
}
