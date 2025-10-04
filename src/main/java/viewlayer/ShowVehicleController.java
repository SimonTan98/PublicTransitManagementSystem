
package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.BusDTO;
import transferobjects.DieselTrainDTO;
import transferobjects.ElectricLightRailDTO;
import transferobjects.VehicleDTO;

/**
 * Servlet that displays detailed information about a specific vehicle.
 * Supports different vehicle types with their specific details.
 * 
 * @author Simon Tan
 */
public class ShowVehicleController extends HttpServlet {
    
    /**
     * Handles both GET and POST requests.
     * Retrieves vehicle info by ID and displays it in HTML.
     * Redirects to login if no active session.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("/LoginServlet");
        }
        
        int vehicleId = Integer.valueOf(request.getParameter("vehicleId"));
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        VehicleDTO vehicle = logic.getVehicleById(vehicleId);
        response.setContentType("text/html;charset=UTF-8");
        if(vehicle != null) {
            try(PrintWriter out = response.getWriter()) {
    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<title>Vehicle Information</title>");
    out.println("</head>");
    out.println("<body style='background-color:#FDF5E6; font-family:Arial, sans-serif; color:#333;'>");

    out.println("<div style='max-width:900px; margin:auto; padding:30px;'>");

    out.println("<h2 style='text-align:center; color:#2F4F4F;'>" + vehicle.getVehicleName() + "</h2>");

    out.println("<table style='width:100%; border-collapse:collapse; margin-bottom:30px;'>");
    out.println("<tr style='background-color:#ddd;'>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Vehicle ID</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Vehicle Name</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Vehicle Type</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Max Capacity</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Route</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Axle Bearings</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Fuel Type</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Consumption Rate</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Wheels</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Brakes</th>");
    out.println("<th style='border:1px solid #999; padding:8px;'>Fuel Level (L)</th>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getVehicleID() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getVehicleName() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getVehicleType() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getMaxCapacity() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getCurrentRouteID() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getAxleBearingsCondition() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getFuelType() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getConsumptionRate() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getWheelsCondition() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getBrakesCondition() + "</td>");
    out.println("<td style='border:1px solid #ccc; padding:8px;'>" + vehicle.getFuelLevel() + "</td>");
    out.println("</tr>");
    out.println("</table>");

    if(vehicle instanceof BusDTO) {
        BusDTO bus = (BusDTO) vehicle;
        out.println("<h3>Bus Specific Details</h3>");
        out.println("<table style='width:100%; border-collapse:collapse; margin-bottom:30px;'>");
        out.println("<tr style='background-color:#ddd;'><th style='border:1px solid #999; padding:8px;'>Emission Rate (kg/h)</th></tr>");
        out.println("<tr><td style='border:1px solid #ccc; padding:8px; text-align:center;'>\" + bus.getEmissionRate() + \"</td></tr>");
        out.println("</table>");
    } else if(vehicle instanceof DieselTrainDTO) {
        DieselTrainDTO train = (DieselTrainDTO) vehicle;
        out.println("<h3>Diesel Train Specific Details</h3>");
        out.println("<table style='width:100%; border-collapse:collapse; margin-bottom:30px;'>");
        out.println("<tr style='background-color:#ddd;'><th style='border:1px solid #999; padding:8px;'>Oil Life (%)</th></tr>");
        out.println("<tr><td style='border:1px solid #ccc; padding:8px;'>" + train.getOilStatus() + "</td></tr>");
        out.println("</table>");
    } else if(vehicle instanceof ElectricLightRailDTO) {
        ElectricLightRailDTO elr = (ElectricLightRailDTO) vehicle;
        out.println("<h3>Electric Light Rail Details</h3>");
        out.println("<table style='width:100%; border-collapse:collapse; margin-bottom:30px;'>");
        out.println("<tr style='background-color:#ddd;'>");
        out.println("<th style='border:1px solid #999; padding:8px;'>Catenary</th>");
        out.println("<th style='border:1px solid #999; padding:8px;'>Pantograph</th>");
        out.println("<th style='border:1px solid #999; padding:8px;'>Circuit Breaker</th>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td style='border:1px solid #ccc; padding:8px;'>" + elr.getCatenaryCondition() + "</td>");
        out.println("<td style='border:1px solid #ccc; padding:8px;'>" + elr.getPantographCondition() + "</td>");
        out.println("<td style='border:1px solid #ccc; padding:8px;'>" + elr.getCircuitBreakerCondition() + "</td>");
        out.println("</tr>");
        out.println("</table>");
    }

    out.println("<form action='FrontController' method='get' style='text-align:center; margin-top:40px;'>");
    out.println("<button type='submit' name='action' value='dashboard' style='padding:10px 20px; background-color:#ccc; border:none; border-radius:4px;'>Back to Dashboard</button>");
    out.println("</form>");

    out.println("</div>");
    out.println("</body>");
    out.println("</html>");

            }
        }
    }
    /**
     * Handles GET requests by delegating to processRequest.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    /**
     * Handles POST requests by delegating to processRequest.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
}

