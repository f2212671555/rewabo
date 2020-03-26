
package Login.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import order.model.javabean.ResponseStatus;

@WebServlet(name = "/LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    //response.setContentType("text/html");

    HttpSession session = request.getSession();
    session.invalidate();

    request.getRequestDispatcher("/login.jsp").forward(request, response);

    ResponseStatus responseStatus = new ResponseStatus();
    Date date = new Date();
    responseStatus.setDate(date.toString());
    responseStatus.setStatus(HttpServletResponse.SC_OK);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.print(gson.toJson(responseStatus));
    out.flush();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {
    doPost(request, response);
    // throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
  }
}
