package order.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.model.java.DeleteOrder;
import order.model.javabean.OrderContent;
import order.model.javabean.ResponseStatus;
import tool.HttpCommonAction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "/DeleteOrderServlet", urlPatterns = { "/DeleteOrderServlet" })
public class DeleteOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        OrderContent javab = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), OrderContent.class);

        DeleteOrder delete =new DeleteOrder();
        delete.delete(javab);

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }



}
