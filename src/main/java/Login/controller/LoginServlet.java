package Login.controller;

import static pojo.QkConn.dbHost;
import static pojo.QkConn.dbName;
import static pojo.QkConn.mongoDBPort;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.bson.Document;

@WebServlet(name = "/LoginServlet", urlPatterns = {"/LoginServlet"})

public class LoginServlet extends HttpServlet {


  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {
    doPost(request, response);
    // throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {

    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext().getAttribute("mongoDatabase");
    //MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
    //MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
    MongoCollection collection = mongoDatabase.getCollection("Login");

    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");

    FindIterable<Document> findIterable = collection.find();
    MongoCursor<Document> cursorlogin = findIterable.iterator();
    Document login = cursorlogin.next();//找到日期一樣的那個doc

    String username = request.getParameter("username");

    String password = request.getParameter("password");

    String dbusername = (String) login.get("username");//get("Account")的value

    String dbpassword = (String) login.get("password");//get("Password")的value
    //System.out.println(username);
    //System.out.println(password);
    //System.out.println(dbusername);
    //System.out.println(dbpassword);
    if (username.equals("") || username == null || password.equals("") || password == null) {
      request.setAttribute("error_message", "Please enter login id and password");
      request.setAttribute("error_message",
          "You are not an authorised user. Please check with administrator.");

      request.getRequestDispatcher("/login.jsp").forward(request, response);
      //System.out.println("空白");
    } else {

      if (dbusername.equals(username) && dbpassword.equals(password)) {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        HttpSession session = request.getSession();
        session.setAttribute("name", username);
      } else {
        //System.out.println("帳密錯誤");

        request.setAttribute("error_message",
            "You are not an authorised user. Please check with administrator.");

        request.getRequestDispatcher("/login.jsp").forward(request, response);

      }
    }
  }
}
