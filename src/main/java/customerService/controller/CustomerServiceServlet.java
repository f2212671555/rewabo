package customerService.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import customerService.model.javabean.rewabo.CustomerService;
import customerService.model.javaclass.CustomerServiceManager;
import customerService.model.javaclass.IntentManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import order.model.javabean.ResponseStatus;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.CheckCustomerServiceRequest;
import translate.model.javabean.nicp.response.CheckCustomerServiceResponse;

@WebServlet(name = "CustomerServiceServlet",
    urlPatterns = {"/CustomerServiceServlet", "/CustomerServiceServlet/check"})
@MultipartConfig
public class CustomerServiceServlet extends HttpServlet {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext().getAttribute("mongoDatabase");
    DB db = (DB) request.getServletContext().getAttribute("db");
    CustomerServiceManager customerServiceManager = new CustomerServiceManager(mongoDatabase, db);

    String json = null;
    switch (request.getServletPath()) {
      case "/CustomerServiceServlet": {
        String action = request.getParameter("action");
        IntentManager intentManager = new IntentManager();
        ResponseStatus responseStatus = new ResponseStatus();
        Date date2 = new Date();

        switch (action) {
          case "新增":
            String label = request.getParameter("label");
            CustomerService customerService = new CustomerService();
            customerService.setLabel(label);
            responseStatus.setDate(date2.toString());
            responseStatus.setStatus(HttpServletResponse.SC_OK);
            json = gson.toJson(responseStatus);
            try {
              customerServiceManager
                  .addCustomerService(label, label, intentManager.createIntent(label));
            } catch (Exception e) {
              e.printStackTrace();
            }
            break;
          case "刪除":
            String intentId = request.getParameter("Intent_Id");
            customerServiceManager.deleteCustomerService(intentId);
            responseStatus.setDate(date2.toString());
            responseStatus.setStatus(HttpServletResponse.SC_OK);
            json = gson.toJson(responseStatus);

            try {
              intentManager.deleteIntent(intentId);
            } catch (Exception e) {
              e.printStackTrace();
            }
            break;
        }
        break;
      }
      case "/CustomerServiceServlet/check": {
        CheckCustomerServiceRequest checkCustomerServiceRequest = gson
            .fromJson(HttpCommonAction.getRequestBody(request.getReader()),
                CheckCustomerServiceRequest.class);
        Translate2NICP translate2NICP = new Translate2NICP();
        boolean exist = translate2NICP
            .checkCustomerService(customerServiceManager.getCustomerService(),
                checkCustomerServiceRequest.getQuery().getIntentName());
        CheckCustomerServiceResponse checkCustomerServiceResponse = new CheckCustomerServiceResponse();
        checkCustomerServiceResponse.setResult(exist);
        checkCustomerServiceResponse.setMessage("");
        checkCustomerServiceResponse.setStatus(HttpServletResponse.SC_OK);
        json = gson.toJson(checkCustomerServiceResponse);
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
    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext().getAttribute("mongoDatabase");
    DB db = (DB) request.getServletContext().getAttribute("db");
    CustomerServiceManager customerServiceManager = new CustomerServiceManager(mongoDatabase, db);
    response.setContentType("application/json");
    response.getWriter().print(customerServiceManager.showCustomerService());
    response.getWriter().flush();
  }
}
