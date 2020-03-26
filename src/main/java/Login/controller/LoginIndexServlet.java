package Login.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "/LoginIndexServlet", urlPatterns = {"/LoginIndexServlet"})
public class LoginIndexServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    //System.out.println("近Index判斷");

    HttpSession session = request.getSession(false);
    if (session.getAttribute("name") == null) {
      //System.out.println(session.getAttribute("name"));
      //System.out.println("name is null");
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {
    doPost(request, response);
    // throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
  }
}
