package viewlayer;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet to handle user logout.
 * Invalidates the current session if it exists
 * and redirects to the FrontController (home).
 * 
 * @author Santiago Castro
 */
public class LogoutServlet extends HttpServlet {
    /**
     * Handles the HTTP GET method to perform logout.
     * Invalidates the current user session if present,
     * then redirects to the FrontController.
     * 
     * @param request  the HttpServletRequest object that contains the request from the client
     * @param response the HttpServletResponse object to send the response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */    
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        response.setContentType("text/html");
        response.sendRedirect("FrontController");

    }
}
