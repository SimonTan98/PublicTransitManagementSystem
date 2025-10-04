
package viewlayer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.UserDTO;
import constants.OperatorType;
import java.io.PrintWriter;

/**
 * Central servlet that handles routing based on user actions and roles.
 * Redirects to appropriate pages or shows login/register interface if not logged in.
 * 
 * @author Simon Tan
 */
public class FrontController extends HttpServlet {
    
    /**
     * Processes both GET and POST requests.
     * Routes the user to the appropriate servlet based on the 'action' parameter.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an input/output error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean loggedIn = false;
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        if(session != null) {
            loggedIn = true;
            UserDTO user = (UserDTO) session.getAttribute("user");
            String userType = user.getUserType();
            if(action.equals("dashboard")) {
                if(userType.equals(OperatorType.OPERATOR)) {
                    response.sendRedirect("/OperatorDashboardServlet");
                }
                else if(userType.equals(OperatorType.TRANSITMANAGER)) {
                    response.sendRedirect("/ManagerDashboardServlet");
                }
            } 
            else if(action.equals("addVehicle")) {
                if(userType.equals(OperatorType.OPERATOR)) {
                    response.sendRedirect("/OperatorDashboardServlet");
                }
                else if(userType.equals(OperatorType.TRANSITMANAGER)) {
                    response.sendRedirect("/AddVehicleServlet");
                }               
            }
            else if(action.equals("vehicleStatus")) {
                response.sendRedirect("/SetVehicleStatusServlet");
            }
            else if(action.equals("getVehicleById")) {
                response.sendRedirect("/GetVehicleByIdServlet");
            }
            else if(action.equals("costReport")) {
                if(userType.equals(OperatorType.OPERATOR)) {
                    response.sendRedirect("/OperatorDashboardServlet");
                }
                else if(userType.equals(OperatorType.TRANSITMANAGER)) {
                    response.sendRedirect("/CostReportServlet");
                }               
            }
            else if(action.equals("updateVehicleLocation")) {
                response.sendRedirect("/UpdateVehicleLocationServlet");
            }
            else if(action.equals("logStationVisit")) {
                response.sendRedirect("/LogStationVisitServlet");
            }
            else if(action.equals("stationVisitReport")) {
                if(userType.equals(OperatorType.OPERATOR)) {
                    response.sendRedirect("/OperatorDashboardServlet");
                }
                else if(userType.equals(OperatorType.TRANSITMANAGER)) {
                    response.sendRedirect("/StationVisitReportServlet");
                }    
            }
            else if(action.equals("fuelUsageReport")) {
                if(userType.equals(OperatorType.OPERATOR)) {
                    response.sendRedirect("/OperatorDashboardServlet");
                }
                else if(userType.equals(OperatorType.TRANSITMANAGER)) {
                    response.sendRedirect("/FuelEnergyReportServlet");
                }                   
            }
            else if(action.equals("maintenance")) {
                if(userType.equals(OperatorType.OPERATOR)) {
                    response.sendRedirect("/OperatorDashboardServlet");
                }
                else if(userType.equals(OperatorType.TRANSITMANAGER)) {
                    response.sendRedirect("/ScheduleMaintenanceServlet");
                }   
            }
            else if(action.equals("logBreak")) {
                response.sendRedirect("/LogBreakServlet");
            }
            else if(action.equals("operatorPerformance")) {
                if(userType.equals(OperatorType.OPERATOR)) {
                    response.sendRedirect("/OperatorPerformanceIndividualServlet");
                }
                else if(userType.equals(OperatorType.TRANSITMANAGER)) {
                    response.sendRedirect("/OperatorPerformanceServlet");
                }
            }
            else if(action.equals("seeAlerts")) {
                response.sendRedirect("/AlertServlet");
            }
            else if(action.equals("logTrip")) {
                response.sendRedirect("/CompleteTripServlet");

            }
            else if(action.equals("getAllVehicles")){
                response.sendRedirect("/GetAllVehiclesServlet");
            } 
            else if(action.equals("refuel")) {
                response.sendRedirect("/RefuelServlet");
            }
        }else {
            try(PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<title>PTFMS - Login or Register</title>");
                out.println("<script>");
                out.println("function showForm(formId) {");
                out.println("document.getElementById(\"loginForm\").style.display = \"none\";");
                out.println("document.getElementById(\"registerForm\").style.display = \"none\";");
                out.println("document.getElementById(formId).style.display = \"block\";");
                out.println("}");
                out.println("</script>");
                out.println("</head>");
                out.println("<body BGCOLOR=\"#FDF5E6\">");
                out.println("<center>");
                out.println("<h1>Welcome to the Public Transit Fleet Management System (PTFMS)</h1>");
                out.println("<button onclick=\"showForm('loginForm')\">Login</button>");
                out.println("<button onclick=\"showForm('registerForm')\">Register</button>");
                out.println("<div id=\"loginForm\" style=\"display:none;\">");
                out.println("<h2>Login</h2>");
                out.println("<form method=\"post\" action=\"login\">");
                out.println("<label for=\"email\">Email:</label>");
                out.println("<input type=\"email\" name=\"email\"><br><br>");
                out.println("<label for=\"password\">Password:</label>");
                out.println("<input type=\"password\" name=\"password\"><br><br>");
                out.println("<input type=\"submit\" value=\"Login\">");
                out.println("</form>");
                out.println("</div>");
                out.println("<div id=\"registerForm\" style=\"display:none;\">");
                out.println("<h2>Register</h2>");
                out.println("<form method=\"post\" action=\"register\">");
                out.println("<label for=\"name\">Full Name:</label>");
                out.println("<input type=\"text\" name=\"name\"><br><br>");
                out.println("<label for=\"email\">Email:</label>");
                out.println("<input type=\"email\" name=\"email\"><br><br>");
                out.println("<label for=\"password\">Password:</label>");
                out.println("<input type=\"password\" name=\"password\"><br><br>");
                out.println("<label for=\"userType\">User Type:</label>");
                out.println("<select name=\"userType\" required>");
                out.println("<option value=\"TRANSIT MANAGER\">Transit Manager</option>");
                out.println("<option value=\"OPERATOR\">Operator</option>");
                out.println("</select><br><br>");
                out.println("<input type=\"submit\" value=\"Register\">");
                out.println("</form>");
                out.println("</div>");
                out.println("</center>");
                out.println("</body>");
                out.println("</html>");
            }
        }  
    }
    
    /**
     * Handles GET requests by passing them to processRequest.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles POST requests by passing them to processRequest.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
