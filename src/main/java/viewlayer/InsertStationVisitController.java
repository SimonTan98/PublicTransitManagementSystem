
package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import constants.OperatorType;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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
 * Servlet for logging a station visit by a vehicle.
 * Checks user session and handles input parameters to log the visit.
 * Displays success or failure message and shows all stations in a table.
 * 
 * @author Simon Tan
 */
public class InsertStationVisitController extends HttpServlet {
    
    /**
     * Processes both GET and POST requests to log a station visit and display all stations.
     * Redirects to login page if no valid session exists.
     *
     * @param request  the HttpServletRequest containing client request data
     * @param response the HttpServletResponse used to send the response back to the client
     * @throws ServletException if a servlet-specific error occurs during processing
     * @throws IOException      if an I/O error occurs during processing
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
        int stationId = Integer.valueOf(request.getParameter("stationId"));
        LocalDateTime arrivalTime = LocalDateTime.parse(request.getParameter("arrivalTime"));
        LocalDateTime departureTime = LocalDateTime.parse(request.getParameter("departureTime"));
        
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        List<StationDTO> stations = logic.getAllStations();
        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Log Station Visit</title>");
            out.println("</head>");
            out.println("<body BGCOLOR=\"#FDF5E6\"><center>");
            out.println("<h1>Log a Station Visit</h1>");
            if(logic.logStationVisit(vehicleId, stationId, arrivalTime, departureTime)) {
                out.println("<h2>Station visit entered successfully.</h2>");
            }
            else {
                out.println("<h2>Unable to enter station visit. Please try again.</h2>");
            }
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
     * Handles the HTTP GET method by delegating to processRequest.
     *
     * @param request  the HttpServletRequest containing client request data
     * @param response the HttpServletResponse used to send the response back to the client
     * @throws ServletException if a servlet-specific error occurs during processing
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    /**
     * Handles the HTTP POST method by delegating to processRequest.
     *
     * @param request  the HttpServletRequest containing client request data
     * @param response the HttpServletResponse used to send the response back to the client
     * @throws ServletException if a servlet-specific error occurs during processing
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
}
