package translate.controller;

import com.google.gson.*;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import order.model.java.OrderManager;
import order.model.javabean.Order;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.OrderRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/OrderProgressServlet")
public class OrderProgressServlet extends HttpServlet {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        OrderRequest orderRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), OrderRequest.class);
        String chatId = orderRequest.getUserData().getChatId();
        //String orderID = orderRequest.getQuery().getOrderID();

        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");

        Translate2NICP translate2NICP = new Translate2NICP();
        OrderManager orderManager = new OrderManager(mongoDatabase,db);
        List<Order> orderList = orderManager.getCustomerOrder(chatId,"other");
        JsonObject jsonObject = translate2NICP.handleOrderProgress(chatId,orderList);
        String json = gson.toJson(jsonObject);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
