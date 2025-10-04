package viewlayer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.UserDTO;
import constants.OperatorType;

/**
 * Servlet that displays a form for the user to enter a vehicle ID 
 * and search for the corresponding vehicle.
 * Access is restricted based on user session and type.
 * 
 * @author Simon Tan
 * 
 */
public class GetVehicleByIdServlet extends HttpServlet {
    
    /**
     * Processes both GET and POST requests by displaying the vehicle ID input form.
     * Redirects to login if no active session is found.
     * 
     * @param request  the HttpServletRequest containing client request data
     * @param response the HttpServletResponse used to send the response back to client
     * @throws ServletException if a servlet error occurs during request processing
     * @throws IOException      if an input or output error occurs while handling the request
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("/LoginServlet");
        }
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;
        
        String userType = user.getUserType();
        
        response.setContentType("text/html;charset=UTF-8");

        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Get Vehicle By Id</title>");
            out.println("</head>");
            out.println("<body BGCOLOR=\"#FDF5E6\">");
            out.println("<center>");
            out.println("<h2>Search for A Vehicle</h2>");
            out.println("<form action=\"ShowVehicleController\" method=\"GET\">");
            out.println("<label for=\"vehicleId\">Enter Vehicle ID</label>");
            out.println("<input type=\"number\" id=\"vehicleId\" name=\"vehicleId\">");
            out.println("<input type=\"submit\" value=\"Submit\">");
            out.println("</form>");
            out.println("<br><form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
            out.println("<a href=\"/\">");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }

    }

    /**
     * Handles the HTTP GET method by delegating to processRequest.
     * 
     * @param request  the HttpServletRequest containing client request data
     * @param response the HttpServletResponse used to send the response back to client
     * @throws ServletException if a servlet error occurs during request processing
     * @throws IOException      if an input or output error occurs while handling the request
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
     * @param response the HttpServletResponse used to send the response back to client
     * @throws ServletException if a servlet error occurs during request processing
     * @throws IOException      if an input or output error occurs while handling the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
}
