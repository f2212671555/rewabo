package notification.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import java.io.PrintWriter;
import java.util.List;
import notification.javabean.Notification;
import notification.javabean.OfferInfoJavabean;
import notification.model.NotificationManager;
import order.model.javabean.ResponseStatus;
import tool.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.CommonRequest;

@WebServlet(name = "NotificationAPI", urlPatterns = {"/NotificationAPI", "/OfferInfoServlet"})
@MultipartConfig
public class NotificationAPI extends HttpServlet {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext().getAttribute("mongoDatabase");
    DB db = (DB) request.getServletContext().getAttribute("db");

    NotificationManager notificationManager = new NotificationManager(mongoDatabase, db);
    String json = null;
    switch (request.getServletPath()) {
      case "/NotificationAPI": {
        Notification notification = new Notification();

        String date = request.getParameter("date");
        String Name = request.getParameter("Name");
        String Price = request.getParameter("Price");
        String PushInfo = request.getParameter("PushInfo");
        String Time = request.getParameter("Time");
        String oid = request.getParameter("oid");
        String action = request.getParameter("action");
        String type = request.getParameter("type");

        notification.setDate(date);
        notification.setName(Name);
        notification.setPrice(Price);
        notification.setPushInfo(PushInfo);
        notification.setTime(Integer.valueOf(Time));
        notification.setoid(oid);
        notification.settype(type);
        notification.setAction(action);

        ResponseStatus responseStatus = new ResponseStatus();
        Date date2 = new Date();

        switch (action) {
          case "新增":
            notificationManager.addNotification(notification);
            responseStatus.setDate(date2.toString());
            responseStatus.setStatus(HttpServletResponse.SC_OK);
            json = gson.toJson(responseStatus);
            break;
          case "刪除":
            notificationManager.deleteNotification(notification);
            responseStatus.setDate(date2.toString());
            responseStatus.setStatus(HttpServletResponse.SC_OK);
            json = gson.toJson(responseStatus);
            break;
        }
        break;
      }
      case "/OfferInfoServlet": {
        CommonRequest commonRequest = gson
            .fromJson(HttpCommonAction.getRequestBody(request.getReader()), CommonRequest.class);
        String chatId = commonRequest.getUserData().getChatId();

        List<OfferInfoJavabean> offerInfoList = notificationManager.ShowPush2fb();
        Translate2NICP translate2NICP = new Translate2NICP();
        JsonObject jsonObject = translate2NICP.handleOfferInfo(chatId, offerInfoList);
        json = gson.toJson(jsonObject);
        break;
      }
    }

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext().getAttribute("mongoDatabase");
    DB db = (DB) request.getServletContext().getAttribute("db");
    NotificationManager notificationManager = new NotificationManager(mongoDatabase, db);
    notificationManager.ShowPush2fb();
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().print(notificationManager.ShowPush());
    response.getWriter().flush();
  }
}
