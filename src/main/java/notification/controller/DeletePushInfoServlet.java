package notification.controller;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.model.javabean.ResponseStatus;
import notification.model.PushInfoManager;
import notification.javabean.PushInfo;
import tool.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "/DeletePushInfoServlet", urlPatterns = { "/DeletePushInfoServlet" })
public class DeletePushInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        PushInfo javab = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),PushInfo.class);

        String oid = javab.getoid();
        PushInfoManager deletePushInfo = new PushInfoManager();
        deletePushInfo.deletePushInfo(oid);

        ResponseStatus responseStatus = new ResponseStatus();
        Date date =new Date();
        responseStatus.setDate(date.toString());
        responseStatus.setStatus(HttpServletResponse.SC_OK);


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(responseStatus));
        response.getWriter().flush();

    }

}
