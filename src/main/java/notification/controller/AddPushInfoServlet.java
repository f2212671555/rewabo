package notification.controller;



import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.model.javabean.ResponseStatus;
import notification.model.PushInfoManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "/AddPushInfoServlet", urlPatterns = { "/AddPushInfoServlet" })
@MultipartConfig
public class AddPushInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
  //      PushInfo javab = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), PushInfo.class);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String date = request.getParameter("date");
        String Name = request.getParameter("Name");
        String Price = request.getParameter("Price");
        String PushInfo = request.getParameter("PushInfo");
        PushInfoManager pushInfoManager =new PushInfoManager();
        pushInfoManager.add(date,Name,Price,PushInfo);

        ResponseStatus responseStatus = new ResponseStatus();
        Date date2 =new Date();
        responseStatus.setDate(date2.toString());
        responseStatus.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(responseStatus));
        response.getWriter().flush();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }



}
