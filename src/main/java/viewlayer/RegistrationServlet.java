package viewlayer;

import businesslayer.FleetManagementBusinessLogic;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet to handle user registration requests.
 * 
 * @author Santiago Castro
 */
public class RegistrationServlet extends HttpServlet {

    private FleetManagementBusinessLogic businessLogic;

    /**
     * Initializes the business logic instance.
     */
    @Override
    public void init() throws ServletException {
        businessLogic = new FleetManagementBusinessLogic();
    }
    /**
     * Handles POST requests to register a new user.
     * Reads user details from the request and attempts to add the operator.
     * Redirects to home on success, or shows an error message on failure.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        boolean success = businessLogic.addOperator(name, email, password, userType);
        

        if (success) {
            response.sendRedirect("/FrontController");
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head><title>Registration Failed</title></head>");
                out.println("<body BGCOLOR=\"#FDF5E6\">");
                out.println("<center>");
                out.println("<h3 style='color:red;'>Registration Failed, Please Try Again</h3>");
                out.println("<form action='FrontController' method='get'>");
                out.println("<input type='submit' value='Return to Home'>");
                out.println("</form>");
                out.println("</center>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}
