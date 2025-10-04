package viewlayer;

import transferobjects.VehicleDTO;
import businesslayer.FleetManagementBusinessLogic;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet that retrieves and displays a list of all vehicles in the system.
 * Shows detailed vehicle information in an HTML table.
 * 
 * @author Santiago Castro
 */
public class GetAllVehiclesServlet extends HttpServlet {

    private final FleetManagementBusinessLogic businessLogic = new FleetManagementBusinessLogic();

    /**
     * Handles the HTTP GET method.
     * Retrieves all vehicles and generates an HTML table displaying their details.
     * 
     * @param request  the HttpServletRequest object containing client request data
     * @param response the HttpServletResponse object used to send response data
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs while writing the response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        List<VehicleDTO> vehicles = businessLogic.getAllVehicles();

        response.setContentType("text/html");
        var out = response.getWriter();
        out.println("<html><head><title>Vehicles Table</title></head><body BGCOLOR=\"#FDF5E6\">");

        out.println("<center><h3>All Vehicles</h3>");
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");

        if (vehicles.isEmpty()) {
            out.println("<p>No vehicles found.</p>");
        } else {
            out.println("<table border='1' style='margin: auto; border-collapse: collapse;'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Type</th>");
            out.println("<th>Fuel Type</th>");
            out.println("<th>Consumption Rate</th>");
            out.println("<th>Max Capacity</th>");
            out.println("<th>Current Route ID</th>");
            out.println("<th>Axle Bearings</th>");
            out.println("<th>Brakes</th>");
            out.println("<th>Wheels</th>");
            out.println("<th>Status</th>");
            out.println("<th>Fuel Level</th>");
            out.println("</tr>");

            for (VehicleDTO v : vehicles) {
                out.println("<tr>");
                out.println("<td>" + v.getVehicleID() + "</td>");
                out.println("<td>" + v.getVehicleName() + "</td>");
                out.println("<td>" + v.getVehicleType() + "</td>");
                out.println("<td>" + v.getFuelType() + "</td>");
                out.println("<td>" + v.getConsumptionRate() + "</td>");
                out.println("<td>" + v.getMaxCapacity() + "</td>");
                out.println("<td>" + v.getCurrentRouteID() + "</td>");
                out.println("<td>" + v.getAxleBearingsCondition() + "</td>");
                out.println("<td>" + v.getBrakesCondition() + "</td>");
                out.println("<td>" + v.getWheelsCondition() + "</td>");
                out.println("<td>" + v.getStatus() + "</td>");
                out.println("<td>" + v.getFuelLevel() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }
        out.println("</center></body></html>");

    }
}