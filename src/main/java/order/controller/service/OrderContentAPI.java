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
import order.model.java.OrderManager;
import order.model.javabean.Order;
import order.model.javabean.OrderContent;
import tool.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.CommonRequest;
import translate.model.javabean.nicp.request.OrderRequest;

@WebServlet(name = "/OrderContentAPI", urlPatterns = {"/OrderContentAPI","/OrderContentServlet","/SingleOrderContentServlet"})
public class OrderContentAPI extends HttpServlet {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");

        String json = null;
        switch (request.getServletPath()) {
            case "/OrderContentAPI": {
                OrderContent javab = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), OrderContent.class);

                MyOrder myOrder = new MyOrder();
                if(!javab.getCustomerID().equals("")&&!javab.getStatus().equals("")){
                    myOrder.getCustomerOrder(javab.getCustomerID(),javab.getStatus());

                    response.getWriter().print(myOrder.getCustomerOrder(javab.getCustomerID(),javab.getStatus()));
                    response.getWriter().flush();
                }else if (!javab.getOid().equals("")&&!javab.getStatus().equals("")){
                    response.getWriter().print(myOrder.getCustomerOrderByID(javab.getOid(),javab.getStatus()));
                    response.getWriter().flush();
                }else if (!javab.getFromDate().equals("")&&!javab.getToDate().equals("")&&!javab.getCustomerID().equals("")){

                    response.getWriter().print(myOrder.getOrderByDate(javab.getCustomerID(),javab.getFromDate(),javab.getToDate()));
                    response.getWriter().flush();

                }else if(!javab.getStatus().equals("")){
                    response.getWriter().print(myOrder.getOrder(javab.getStatus()));
                    response.getWriter().flush();
                }
                break;
            }
            case "/OrderContentServlet":{
                CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), CommonRequest.class);
                String chatId = commonRequest.getUserData().getChatId();

                OrderManager orderManager = new OrderManager(mongoDatabase,db);
                Translate2NICP translate2NICP = new Translate2NICP();
                List<Order> orderList = orderManager.getCustomerOrder(chatId,"other");
                JsonObject jsonObject = translate2NICP.handleOrderContent(chatId,orderList);
                json = gson.toJson(jsonObject);

                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
                break;
            }
            case "/SingleOrderContentServlet":{
                OrderRequest orderRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), OrderRequest.class);
                String chatId = orderRequest.getUserData().getChatId();
                String orderId = orderRequest.getQuery().getOrderID();

                OrderManager orderManager = new OrderManager(mongoDatabase,db);
                Translate2NICP translate2NICP = new Translate2NICP();
                Order order = orderManager.getCustomerOrderByID(orderId,"other");
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
