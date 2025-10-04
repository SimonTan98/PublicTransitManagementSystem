package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import constants.OperatorType;
import transferobjects.UserDTO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Servlet to handle logging breaks for users.
 * Displays a form to log break times and processes submitted break data.
 * Requires user to be logged in.
 * 
 * @author Santiago Castro
 * @author Simon Tan
 */
public class LogBreakServlet extends HttpServlet {

    
    /**
     * Handles the HTTP GET method.
     * Displays the break logging form.
     * 
     * @param request  the HttpServletRequest object that contains the request the client made
     * @param response the HttpServletResponse object that contains the response the servlet sends
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        try(PrintWriter out = response.getWriter()) {
            // Display the break logging form
            
            out.println("<html><head><title>Log Break</title></head><body BGCOLOR=\"#FDF5E6\">");
            out.println("<center><h2>Log Your Break</h2>");

            out.println("<form action='LogBreakServlet' method='post'>");
            out.println("Start Time: <input type='datetime-local' name='startTime' required><br><br>");
            out.println("End Time: <input type='datetime-local' name='endTime' required><br><br>");
            out.println("<input type='submit' value='Submit Break'>");
            out.println("</form>");
            out.println("<br>");
            out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
            
            out.println("</center></body></html>");
        }
    }

    /**
     * Handles the HTTP POST method.
     * Processes the break logging request and displays confirmation or error message.
     * Redirects to login if session does not exist.
     * 
     * @param request  the HttpServletRequest object that contains the request the client made
     * @param response the HttpServletResponse object that contains the response the servlet sends
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("/LoginServlet");
        }
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;
        String userType = user.getUserType();
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        
        response.setContentType("text/html");
        
        try(PrintWriter out = response.getWriter()) {
        out.println("<html><head><title>Break Confirmation</title></head><body BGCOLOR=\"#FDF5E6\"><center>");

        if (user == null) {
            out.println("<h3>Error: You must be logged in to log a break.</h3>");
        } else {
            LocalDateTime startTime = LocalDateTime.parse(request.getParameter("startTime"));
            LocalDateTime endTime = LocalDateTime.parse(request.getParameter("endTime"));

            if (startTime == null || endTime == null) {
                out.println("<h3>Error: Start and End time are required.</h3>");
            } else {
                boolean success = logic.logBreak(user.getUserID(), startTime, endTime);
                if (success) {
                    out.println("<h3>Break logged successfully for " + user.getName() + "</h3>");
                } else {
                    out.println("<h3>Failed to log break. Please try again.</h3>");
                }
            }
        }
        out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
        out.println("</center></body></html>");
        }
    }
}