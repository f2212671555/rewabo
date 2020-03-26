package order.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import java.io.PrintWriter;
import java.util.List;
import order.model.java.MyOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import order.model.java.OrderManager;
import order.model.javabean.Order;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.CommonRequest;
import translate.model.javabean.nicp.request.OrderRequest;

@WebServlet(name = "OrderHistoryAPI", urlPatterns = {"/OrderHistoryAPI","/HistoryOrderServlet","/SingleHistoryOrderServlet"})
public class OrderHistoryAPI extends HttpServlet {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(
        FieldNamingPolicy.IDENTITY).create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");

        String json = null;
        switch (request.getServletPath()) {
            case "/OrderHistoryAPI": {
                String CustomerID = request.getParameter("CustomerID");
                String Status = request.getParameter("Status");
                ////////////////////////////////////////// 上面這裡要填老闆端給的參數 決定要拿的菜單狀態 或是 拿到客人ID
                //String MyStatus ="已完成";
                MyOrder myOrder = new MyOrder();

                //JSONArray jsonArray = connection.getJson();
                myOrder.getCustomerOrder(CustomerID,Status);

                response.getWriter().print(myOrder.getCustomerOrder(CustomerID,Status));
                break;
            }
            case "/HistoryOrderServlet":{
                CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), CommonRequest.class);
                String chatId = commonRequest.getUserData().getChatId();

                OrderManager orderManager = new OrderManager(mongoDatabase,db);
                Translate2NICP translate2NICP = new Translate2NICP();
                List<Order> orderList = orderManager.getCustomerOrder(chatId,"已結帳");

                JsonObject jsonObject = translate2NICP.handleOrderHistory(chatId,orderList);
                json = gson.toJson(jsonObject);
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
                break;
            }
            case "/SingleHistoryOrderServlet":{
                OrderRequest orderRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), OrderRequest.class);
                String chatId = orderRequest.getUserData().getChatId();
                String orderId = orderRequest.getQuery().getOrderID();

                OrderManager orderManager = new OrderManager(mongoDatabase,db);
                Translate2NICP translate2NICP = new Translate2NICP();
                Order order = orderManager.getCustomerOrderByID(orderId,"已結帳");
                JsonObject jsonObject = translate2NICP.getOrderReceiptJson(chatId,order);

                json = gson.toJson(jsonObject);
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
