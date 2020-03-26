package order.controller.service;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import order.model.java.AddOrder;
import order.model.java.OrderManager;
import order.model.javabean.Order;
import order.model.javabean.ResponseStatus;
import order.model.websocket.BossOrderSocket;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import pojo.Settings.nicp.urls.services;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;

@WebServlet("/AddOrderServlet")
public class AddOrderServlet extends HttpServlet {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException, NumberFormatException {

    Order order = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), Order.class);
    AddOrder addOrder = new AddOrder();
    addOrder.add(order);
    MongoDatabase mongoDatabase = (MongoDatabase) getServletContext().getAttribute("mongoDatabase");
    DB db = (DB) request.getServletContext().getAttribute("db");

    // 訂單收據範本 send to NICP-----BEGIN
    Translate2NICP translate2NICP = new Translate2NICP();
    sendReceiptTemplate2NICP(translate2NICP.getReceiveOrderJson(order.getCustomerID(), order));
    // 訂單收據範本 send to NICP-----END

    // 透過websocket 傳送 訂單給老闆-----BEGIN

    OrderManager orderManager = new OrderManager(mongoDatabase, db);
    sendOrder2Boss(orderManager.getoneOrder(order));
    //sendOrder2Boss(order);
    // 透過websocket 傳送 訂單給老闆-----END

    Date date = new Date();
    ResponseStatus responseStatus = new ResponseStatus();
    responseStatus.setDate(date.toString());
    responseStatus.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.print(gson.toJson(responseStatus));
    out.flush();

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }

  private void sendReceiptTemplate2NICP(JsonObject receiveOrderObject) throws IOException {
    //String url = "https://xanxus-node-red.cf/nicp/rewabo/ReceiveCustomerOrder";
    //String testurl = "https://ai-rest.cse.ntou.edu.tw/test/nicp/rewabo/ReceiveCustomerOrder";
    String url = services.receiveCustomerOrderUrl;
    System.out.println(receiveOrderObject);
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(HttpCommonAction.stringParser(url));
    post.setHeader("Content-type", "application/json;charset=utf-8");
    post.setEntity(new StringEntity(gson.toJson(receiveOrderObject), "UTF-8"));
    HttpResponse theResponse = httpClient.execute(post);//送出請求
    HttpEntity entity = theResponse.getEntity();//取得回應
    String responseString = EntityUtils.toString(entity, "UTF-8");//轉個編碼
    System.out.println("NICP回應的資料：" + responseString);
  }

  private void sendOrder2Boss(JSONArray jsonObject) {

    try {
//            BossOrderSocket.bossOrderWsSession.getBasicRemote().sendText(gson.toJson(order));
      BossOrderSocket.bossOrderWsSession.getBasicRemote().sendText(jsonObject.toString());

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      System.out.println("----後端收到訂單----但沒連接websocket----");
    }
  }
}

