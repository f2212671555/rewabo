package Login.controller;

import Login.model.javabean.HtmlContent;
import Login.model.javabean.LoginResponse;
import Login.model.javabean.UserInfo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import tool.HttpCommonAction;

@WebServlet(name = "CheckIdentityServlet", value = "/CheckIdentityServlet")
public class CheckIdentityServlet extends HttpServlet {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext().getAttribute("mongoDatabase");
    MongoCollection collection = mongoDatabase.getCollection("Login");
    FindIterable<Document> findIterable = collection.find();
    Document login = findIterable.iterator().next();

    UserInfo userInfo = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),UserInfo.class);

    String username = (String) login.get("username");
    String password = (String) login.get("password");

    LoginResponse loginResponse = new LoginResponse();
    HtmlContent htmlContent = new HtmlContent();
    if (username.equals(userInfo.getUsername()) && password.equals(userInfo.getPassword())) {
      htmlContent.setHtml("<button type=\"button\" onclick=\"randomOrder()\" class=\"btn btn-primary\">隨機產生訂單</button>");
      loginResponse.setResult(true);
      loginResponse.setHtmlContent(htmlContent);
    }

    PrintWriter out = response.getWriter();
    out.print(gson.toJson(loginResponse));
    out.flush();
  }
}
