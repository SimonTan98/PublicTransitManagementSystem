package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import constants.VehicleStatus;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet to display a form for setting vehicle status and handle status updates.
 * 
 * @author Santiago Castro
 */
public class SetVehicleStatusServlet extends HttpServlet {

    private final FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
    /**
     * Handles the HTTP GET method.
     * Sends an HTML form allowing users to input a vehicle ID and select a status.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println("<html><head><title>Set Vehicle Status</title></head>");
    out.println("<body style='background-color:#FDF5E6; font-family:Arial, sans-serif; color:#333; padding-top: 50px;'>");
    out.println("<div style='max-width:500px; margin:auto; padding:30px;'>");

    out.println("<h2 style='text-align:center; color:#2F4F4F;'>Set Vehicle Status</h2>");

    out.println("<form method='post' style='background:#fff; padding:20px; border:1px solid #ccc; border-radius:10px; box-shadow:2px 2px 10px #ccc;'>");

    out.println("<label style='font-weight:bold;'>Vehicle ID:</label><br>");
    out.println("<input type='number' name='vehicleId' min='1' required style='width:100%; padding:8px; margin-bottom:15px; border:1px solid #ccc; border-radius:4px;'><br>");

    out.println("<label style='font-weight:bold;'>Status:</label><br>");
    out.println("<select name='vehicleStatus' style='width:100%; padding:8px; border:1px solid #ccc; border-radius:4px; margin-bottom:15px;'>");
    out.println("<option value='" + VehicleStatus.ACTIVE + "'>" + VehicleStatus.ACTIVE + "</option>");
    out.println("<option value='" + VehicleStatus.IN_MAINTENANCE + "'>" + VehicleStatus.IN_MAINTENANCE + "</option>");
    out.println("<option value='" + VehicleStatus.OUT_OF_SERVICE + "'>" + VehicleStatus.OUT_OF_SERVICE + "</option>");
    out.println("</select><br>");

    out.println("<input type='submit' value='Update Status' " +
                "style='background-color:#4CAF50; color:white; padding:10px 20px; border:none; border-radius:4px; cursor:pointer;'>");

    out.println("</form>");

    out.println("<div style='margin-top:20px;'>");
    out.println("<form action='FrontController' method='get'>");
    out.println("<button type='submit' name='action' value='dashboard' " +
                "style='padding:8px 16px; background-color:#ccc; border:none; border-radius:4px;'>Back to Dashboard</button>");
    out.println("</form>");
    out.println("</div>");

    out.println("</div></body></html>");
}

    /**
     * Handles the HTTP POST method.
     * Receives the vehicle ID and status from the form, updates the vehicle status
     * via business logic, and returns a success or error message in HTML.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println("<html><head><title>Status Update Result</title></head>");
    out.println("<body style='background-color:#FDF5E6; font-family:Arial, sans-serif; color:#333;'>");
    out.println("<div style='max-width:600px; margin:auto; padding:30px; text-align:center;'>");

    try {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        String vehicleStatus = request.getParameter("vehicleStatus");

        boolean success = logic.setVehicleStatus(vehicleId, vehicleStatus);

        out.println(success
            ? "<h3 style='color:green;'>Vehicle status updated to '" + vehicleStatus + "' successfully.</h3>"
            : "<h3 style='color:red;'>Failed to update vehicle status.</h3>");
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
    }

    out.println("<div style='margin-top:40px;'>");
    out.println("<form action='FrontController' method='get'>");
    out.println("<button type='submit' name='action' value='dashboard' " +
                "style='padding:8px 16px; background-color:#ccc; border:none; border-radius:4px;'>Back to Dashboard</button>");
    out.println("</form>");
    out.println("</div>");

    out.println("</div></body></html>");
    }
}