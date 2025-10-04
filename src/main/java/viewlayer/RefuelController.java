
package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that handles refueling a vehicle.
 * Processes refuel requests and shows success or failure message.
 * 
 * @author Simon Tan
 */
public class RefuelController extends HttpServlet {
    
    /**
     * Processes both GET and POST requests for refueling a vehicle.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("/LoginServlet");
        }
        
        int vehicleId = Integer.valueOf(request.getParameter("vehicleId"));
        double cost = Double.valueOf(request.getParameter("cost"));
        response.setContentType("text/html;charset=UTF-8");
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();       
        if(logic.refuel(vehicleId, cost)) {
            try(PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("<title>Refuel a Vehicle</title>");
                out.println("</head>");
                out.println("<body BGCOLOR=\"#FDF5E6\">");
                out.println("<center>");
                out.println("<h2>Vehicle Refueled Successfully</h2>");
                out.println("<br>");
                out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
                out.println("<a href=\"/\">");
                out.println("</center>");
                out.println("</body>");
                out.println("</html>");
            }
        }
        else {
            try(PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("<title>Refuel a Vehicle</title>");
                out.println("</head>");
                out.println("<body BGCOLOR=\"#FDF5E6\">");
                out.println("<center>");
                out.println("<h2>Unable to refuel. Please try again.</h2>");
                out.println("<br>");
                out.println("<form action='FrontController' method='get'><button type='submit' name='action' value='dashboard'>Back to Dashboard</button></form>");
                out.println("<a href=\"/\">");
                out.println("</center>");
                out.println("</body>");
                out.println("</html>");
            }
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