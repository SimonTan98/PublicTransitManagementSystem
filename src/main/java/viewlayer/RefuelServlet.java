
package viewlayer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import transferobjects.UserDTO;

/**
 * Servlet that displays the form to refuel a vehicle.
 * 
 * @author Simon Tan
 */
public class RefuelServlet extends HttpServlet {
    
    /**
     * Processes GET and POST requests to show the refuel form.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("/LoginServlet");
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Refuel a Vehicle</title>");
            out.println("</head>");
            out.println("<body BGCOLOR=\"#FDF5E6\">");
            out.println("<center>");
            out.println("<h2>Refuel a Vehicle</h2>");
            out.println("<form action=\"RefuelController\" method=\"GET\">");
            out.println("<label for=\"vehicleId\">Enter Vehicle ID</label>");
            out.println("<input type=\"number\" id=\"vehicleId\" name=\"vehicleId\">");
            out.println("<label for=\"cost\">Enter Cost</label>");
            out.println("<input type=\"text\" id=\"cost\" name=\"cost\">");
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
