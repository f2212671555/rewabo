package recommendSet.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoDatabase;
import java.io.PrintWriter;
import recommendSet.model.RecommendSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.CommonRequest;

@WebServlet(name = "SetRecommenderAPI", urlPatterns = {"/SetRecommenderAPI", "/RecommendFoodServlet"})
public class SetRecommenderAPI extends HttpServlet {

  private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(
      FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext()
        .getAttribute("mongoDatabase");

    String json = null;
    switch (request.getServletPath()) {
      case "/SetRecommenderAPI": {
        String s = request.getParameter("_id");
        RecommendSet recommendSet = new RecommendSet(s,mongoDatabase);
        //response.getWriter().print(connection.getSetJavabean());
        response.getWriter().flush();
        break;
      }
      case "/RecommendFoodServlet": {
        CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), CommonRequest.class);
        String chatId = commonRequest.getUserData().getChatId();
        RecommendSet recommendSet = new RecommendSet(chatId,mongoDatabase);
        Translate2NICP translate2NICP = new Translate2NICP();
        JsonObject jsonObject = translate2NICP.getRecommendFoodJson(chatId,recommendSet.getSetJavabean());
        json = gson.toJson(jsonObject);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        break;
      }
    }

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }
}
