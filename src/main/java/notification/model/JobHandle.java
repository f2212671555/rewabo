package notification.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import notification.javabean.OfferInfoJavabean;
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

public class JobHandle {

  private Gson gson = new Gson();

  public void doJob(String Name) {

    PushInfoManager pushInfoManager = new PushInfoManager();
    OfferInfoJavabean offerInfoJavabean;
    offerInfoJavabean = pushInfoManager.getPushInfo(Name);
    Translate2NICP translate2NICP = new Translate2NICP();
    JsonObject jsonObject = translate2NICP
        .getPushInfoJson(pushInfoManager.getCustomerID(), offerInfoJavabean);
    try {
      sendPostRequest(jsonObject);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void sendPostRequest(JsonObject jsonObject) throws IOException {
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
