package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import constants.VehicleFuelType;
import constants.VehicleType;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.CredentialsDTO;

/**
 * Servlet for displaying the Add Vehicle form and handling form submissions.
 * @author Simon Tan
 */
public class AddVehicleServlet extends HttpServlet {
    
    /**
     * Handles GET requests by showing the Add Vehicle form.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles POST requests by processing the form and adding a vehicle.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles both GET and POST requests for showing the form or processing input.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        CredentialsDTO creds = (CredentialsDTO) session.getAttribute("credentials");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Add Vehicle</title>");            
            out.println("<style>");
            out.println("button { margin: 5px; padding: 8px 15px; }");
            out.println("input { margin: 5px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body bgcolor=\"#FDF5E6\">");
            out.println("<center>");
            out.println("<h1>Vehicle Management</h1>");

            out.println("<div style='display: flex; gap: 10px; justify-content: center; margin-bottom: 20px;'>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='getAllVehicles'>Vehicle Table</button></form>");
            out.println("</div>");
                
            out.println("<h2>Add New Vehicle</h2>");
            
            if ("POST".equals(request.getMethod())) {
                try {
                    
                    String vehicleType = request.getParameter("vehicleType");

                    Integer emissionRate = null;
                    Double oilLife = null;
                    Integer catenaryCondition = null;
                    Integer pantographCondition = null;
                    Integer circuitBreakersCondition = null;

                    if ("BUS".equals(vehicleType)) {
                        String emissionStr = request.getParameter("emissionRate");
                        if (emissionStr != null && !emissionStr.trim().isEmpty()) {
                            emissionRate = Integer.parseInt(emissionStr);
                        }
                    } else if ("DIESELTRAIN".equals(vehicleType)) {
                        String oilStr = request.getParameter("oilLife");
                        if (oilStr != null && !oilStr.trim().isEmpty()) {
                            oilLife = Double.parseDouble(oilStr);
                        }
                    } else if ("ELECTRICLIGHTRAIL".equals(vehicleType)) {
                        String catStr = request.getParameter("catenaryCondition");
                        if (catStr != null && !catStr.trim().isEmpty()) {
                            catenaryCondition = Integer.parseInt(catStr);
                        }
                        String panStr = request.getParameter("pantographCondition");
                        if (panStr != null && !panStr.trim().isEmpty()) {
                            pantographCondition = Integer.parseInt(panStr);
                        }
                        String cbStr = request.getParameter("circuitBreakersCondition");
                        if (cbStr != null && !cbStr.trim().isEmpty()) {
                            circuitBreakersCondition = Integer.parseInt(cbStr);
                        }
                    }
                    
                    FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();

                    boolean error = logic.addVehicle(
                        vehicleType, 
                        request.getParameter("vehicleName"), 
                        request.getParameter("fuelType"),
                        Double.parseDouble(request.getParameter("consumptionRate")), 
                        Integer.parseInt(request.getParameter("maxCapacity")),
                        Integer.parseInt(request.getParameter("currentRouteId")), 
                        Integer.parseInt(request.getParameter("axleBearingsCondition")), 
                        Integer.parseInt(request.getParameter("brakesCondition")), 
                        Integer.parseInt(request.getParameter("wheelsCondition")), 
                        emissionRate != null ? emissionRate : 0,
                        oilLife != null ? oilLife : 0.0,
                        catenaryCondition != null ? catenaryCondition : 0,
                        pantographCondition != null ? pantographCondition : 0,
                        circuitBreakersCondition != null ? circuitBreakersCondition : 0);

                    
                    out.println("<p style='color:green'>Vehicle added successfully!</p>");
                    
                } catch(Exception e) {
                    out.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
                    out.println("<form method='POST'>");

                    out.println("Vehicle Type: <input type='text' name='vehicleType' value='" + 
                        (request.getParameter("vehicleType") != null ? request.getParameter("vehicleType") : "") + "' required><br>");
                    out.println("Vehicle Name: <input type='text' name='vehicleName' value='" + 
                        (request.getParameter("vehicleName") != null ? request.getParameter("vehicleName") : "") + "' required><br>");
                    out.println("Fuel Type: <input type='text' name='fuelType' value='" + 
                        (request.getParameter("fuelType") != null ? request.getParameter("fuelType") : "") + "' required><br>");
                    out.println("Consumption Rate: <input type='text' name='consumptionRate' value='" + 
                        (request.getParameter("consumptionRate") != null ? request.getParameter("consumptionRate") : "") + "' required><br><br>");
                    out.println("Max Capacity: <input type='text' name='maxCapacity' value='" + 
                        (request.getParameter("maxCapacity") != null ? request.getParameter("maxCapacity") : "") + "' required><br>");
                    out.println("Current Route ID: <input type='text' name='currentRouteId' value='" + 
                        (request.getParameter("currentRouteId") != null ? request.getParameter("currentRouteId") : "") + "' required><br><br>");
                    
                    out.println("Axle Bearing Condition: <input type='text' name='axleBearingsCondition' value='" + 
                        (request.getParameter("axleBearingsCondition") != null ? request.getParameter("axleBearingsCondition") : "") + "' required><br><br>");
                    out.println("Brakes: <input type='text' name='brakesCondition' value='" + 
                        (request.getParameter("brakesCondition") != null ? request.getParameter("brakesCondition") : "") + "' required><br>");
                    out.println("Wheels: <input type='text' name='wheelsCondition' value='" + 
                        (request.getParameter("wheelsCondition") != null ? request.getParameter("wheelsCondition") : "") + "' required><br>");
                    
                    out.println("<input type='submit' value='Add Vehicle'>");
                    out.println("</form>");
                    out.println("</center>");
                    out.println("</body>");
                    out.println("</html>");
                    return;
                }
            }

            
            out.println("<form method='POST' style='font-family: Arial; max-width: 600px;'>");

            // JavaScript for dynamic visibility
            out.println("<script>");
            out.println("function updateSections() {");
            out.println("  var type = document.getElementsByName('vehicleType')[0].value;");
            out.println("  document.getElementById('envSection').style.display = (type === '" + VehicleType.BUS + "' || type === '" + VehicleType.DIESELTRAIN + "') ? 'block' : 'none';");
            out.println("  document.getElementById('emissionRateField').style.display = (type === '" + VehicleType.BUS + "') ? 'block' : 'none';");
            out.println("  document.getElementById('oilLifeField').style.display = (type === '" + VehicleType.DIESELTRAIN + "') ? 'block' : 'none';");
            out.println("  document.getElementById('electricalSection').style.display = (type === '" + VehicleType.ELECTRICLIGHTRAIL + "') ? 'block' : 'none';");
            out.println("}");
            out.println("window.onload = updateSections;");
            out.println("</script>");

            // Section: Basic Vehicle Info
            out.println("<fieldset><legend><strong>Basic Vehicle Info</strong></legend>");
            out.println("Vehicle Type: <select name='vehicleType' onchange='updateSections()'>");
            out.println("<option value='" + VehicleType.BUS + "'>" + VehicleType.BUS + "</option>");
            out.println("<option value='" + VehicleType.DIESELTRAIN + "'>" + VehicleType.DIESELTRAIN + "</option>");
            out.println("<option value='" + VehicleType.ELECTRICLIGHTRAIL + "'>" + VehicleType.ELECTRICLIGHTRAIL + "</option>");
            out.println("</select><br>");
            out.println("Vehicle Name: <input type='text' name='vehicleName' required><br>");
            out.println("Fuel Type: <select name='fuelType'>");
            out.println("<option value='" + VehicleFuelType.DIESEL + "'>" + VehicleFuelType.DIESEL + "</option>");
            out.println("<option value='" + VehicleFuelType.CNG + "'>" + VehicleFuelType.CNG + "</option>");
            out.println("<option value='" + VehicleFuelType.ENERGY + "'>" + VehicleFuelType.ENERGY + "</option>");
            out.println("</select><br>");
            out.println("Consumption Rate: <input type='text' name='consumptionRate' required><br>");
            out.println("Max Capacity: <input type='text' name='maxCapacity' required><br>");
            out.println("Current Route ID: <input type='text' name='currentRouteId' required><br>");
            out.println("</fieldset><br>");

            // Section: Mechanical Conditions
            out.println("<fieldset><legend><strong>Mechanical Conditions</strong></legend>");
            out.println("Axle Bearings Condition: <input type='text' name='axleBearingsCondition' required><br>");
            out.println("Brakes Condition: <input type='text' name='brakesCondition' required><br>");
            out.println("Wheels Condition: <input type='text' name='wheelsCondition' required><br>");
            out.println("</fieldset><br>");

            // Section: Environmental & Maintenance
            out.println("<fieldset id='envSection'><legend><strong>Environmental & Maintenance</strong></legend>");
            out.println("<div id='emissionRateField'>Emission Rate: <input type='text' name='emissionRate'><br></div>");
            out.println("<div id='oilLifeField'>Oil Life: <input type='text' name='oilLife'><br></div>");
            out.println("</fieldset><br>");

            // Section: Electrical Components
            out.println("<fieldset id='electricalSection'><legend><strong>Electrical Components</strong></legend>");
            out.println("Catenary Condition: <input type='text' name='catenaryCondition'><br>");
            out.println("Pantograph Condition: <input type='text' name='pantographCondition'><br>");
            out.println("Circuit Breakers Condition: <input type='text' name='circuitBreakersCondition'><br>");
            out.println("</fieldset><br>");

            // Submit Button
            out.println("<input type='submit' value='Add Vehicle'>");

            out.println("</form>");
            

            out.println("</center>");
            out.println("</body>");
            out.println("</html>");

        }
    }

}