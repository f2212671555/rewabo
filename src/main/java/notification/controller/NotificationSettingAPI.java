package notification.controller;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import notification.javabean.OfferInfoJavabean;
import notification.javabean.PushInfo;
import notification.model.PushInfoManager;
import order.model.javabean.ResponseStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pojo.Settings.nicp.urls.services;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;

@WebServlet(name = "/NotificationSettingAPI", urlPatterns = {"/NotificationSettingAPI",
    "/PushInfoServlet"})
public class NotificationSettingAPI extends HttpServlet {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    PushInfoManager pushInfoManager = new PushInfoManager();
    String json = null;
    switch (request.getServletPath()) {
      case "/NotificationSettingAPI": {
        response.getWriter().print(pushInfoManager.ShowPush());
        response.getWriter().flush();
        break;
      }
      case "/PushInfoServlet": {
        PushInfo pushInfo = gson
            .fromJson(HttpCommonAction.getRequestBody(request.getReader()), PushInfo.class);

        List<String> userIdList = pushInfoManager.getCustomerID();
        OfferInfoJavabean offerInfoJavabean = pushInfoManager.getPushInfo(pushInfo.getName());
        System.out.println(userIdList);
        Translate2NICP translate2NICP = new Translate2NICP();
        JsonObject jsonObject = translate2NICP.getPushInfoJson(userIdList, offerInfoJavabean);
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatus(HttpServletResponse.SC_OK);
        Date date = new Date();
        responseStatus.setDate(date.toString());
        sendPostRequest(gson, jsonObject);
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(jsonObject));
        out.flush();
        break;
      }
    }

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

  private void sendPostRequest(Gson gson, JsonObject jsonObject) throws IOException {
    System.out.println(jsonObject);
    //String url = "https://xanxus-node-red.cf/nicp/rewabo/Push";//正式
    //String testUrl = "https://ai-rest.cse.ntou.edu.tw/test/nicp/rewabo/Push";// 測試
    String url = services.pushUrl;
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(HttpCommonAction.stringParser(url));
    post.setHeader("Content-type", "application/json;charset=utf-8");
    post.setEntity(new StringEntity(gson.toJson(jsonObject), "UTF-8"));
    HttpResponse theResponse = httpClient.execute(post);//送出請求
    HttpEntity entity = theResponse.getEntity();//取得回應
    String responseString = EntityUtils.toString(entity, "UTF-8");//轉個編碼
    System.out.println("NICP回應的資料：" + responseString);
  }

}
