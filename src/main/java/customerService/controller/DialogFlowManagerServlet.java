package customerService.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import customerService.model.javabean.rewabo.CustomerService;
import customerService.model.javabean.rewabo.Intent;
import customerService.model.javaclass.CustomerServiceManager;
import customerService.model.javaclass.IntentManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tool.HttpCommonAction;

@WebServlet(
    name = "DialogFlowManagerServlet",
    urlPatterns = {
        "/DialogFlowManagerServlet/intents/list",
        "/DialogFlowManagerServlet/intents/get",
        "/DialogFlowManagerServlet/intents/create",
        "/DialogFlowManagerServlet/intents/delete",
        "/DialogFlowManagerServlet/intents/addTrainingPhrases"
    })
public class DialogFlowManagerServlet extends HttpServlet {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json;charset=utf-8");

    Intent intent = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),
        Intent.class);

    String json = null;
    switch (request.getServletPath()) {
      case "/DialogFlowManagerServlet/intents/addTrainingPhrases": {
        IntentManager intentManager = new IntentManager();
        try {
          json = intentManager.addTrainingPhrases(intent);
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      }
      default: {
        break;
      }
    }

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json;charset=utf-8");

    String json = null;
    switch (request.getServletPath()) {
      case "/DialogFlowManagerServlet/intents/list": {
        IntentManager intentManager = new IntentManager();
        MongoDatabase mongoDatabase = (MongoDatabase) getServletContext()
            .getAttribute("mongoDatabase");
        DB db = (DB) request.getServletContext().getAttribute("db");
        CustomerServiceManager customerServiceManager = new CustomerServiceManager(mongoDatabase,
            db);

        List<CustomerService> customerServices = customerServiceManager.getCustomerService();
        System.out.println(customerServices);
        try {
          json = gson.toJson(intentManager.listIntents(customerServices));
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      }
      case "/DialogFlowManagerServlet/intents/get": {
        String intentId = request.getParameter("Intent_Id");
        IntentManager intentManager = new IntentManager();
        try {
          json = gson.toJson(intentManager.listIntent(intentId));
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      }
      default: {
        break;
      }
    }

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }


}
