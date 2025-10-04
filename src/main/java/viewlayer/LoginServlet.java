package viewlayer;

import businesslayer.FleetManagementBusinessLogic;
import transferobjects.UserDTO;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import transferobjects.CredentialsDTO;
import constants.OperatorType;
import dataaccesslayer.InvalidCredentialsException;
import java.io.PrintWriter;

/**
 * Servlet that handles user login.
 * Accepts email and password, authenticates user,
 * and initiates user session on successful login.
 * Displays error message on login failure.
 * 
 * @author Santiago Castro
 * @author Simon Tan
 */
public class LoginServlet extends HttpServlet {
   
    /**
     * Handles the HTTP POST method for login.
     * Authenticates user credentials, creates session on success,
     * and redirects to dashboard.
     * On failure, displays login error message.
     * 
     * @param request  the HttpServletRequest object that contains the client's request
     * @param response the HttpServletResponse object to send the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        CredentialsDTO credentials = new CredentialsDTO();
        credentials.setEmail(email);
        credentials.setPassword(password);
        
        FleetManagementBusinessLogic logic = new FleetManagementBusinessLogic();
        
        try {
            
            UserDTO user = logic.login(credentials);   
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            String userType = user.getUserType();

            response.setContentType("text/html; charset=UTF-8");
            response.sendRedirect("/FrontController?action=dashboard");
            
        } 
        catch(InvalidCredentialsException e) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head><title>Login Failed</title></head>");
                out.println("<body BGCOLOR=\"#FDF5E6\">");
                out.println("<center>");
                out.println("<h3 style='color:red;'>Login failed. Invalid credentials.</h3>");
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
