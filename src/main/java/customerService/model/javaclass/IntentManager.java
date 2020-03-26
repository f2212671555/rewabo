package customerService.model.javaclass;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import customerService.model.javabean.dialogflow.Intent;
import customerService.model.javabean.dialogflow.TrainingPhrase;
import customerService.model.javabean.dialogflow.TrainingPhrase.PartsBean;
import customerService.model.javabean.rewabo.CustomerService;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pojo.JsonPath;
import pojo.Settings.google.api.dialogFlow;
import pojo.Settings.google.api.dialogFlow.intents;
import tool.HttpCommonAction;

public class IntentManager {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  public String listIntents() throws Exception {
    String url = intents.listUrl;
    return doGet(url);
  }

  public List<Intent> listIntents(List<CustomerService> customerServiceList) throws Exception {
    List<Intent> intents = new ArrayList<>();
    for(CustomerService customerService : customerServiceList) {
      intents.add(listIntent(customerService.getIntentId()));
    }
    return intents;
  }

  public Intent listIntent(String intentId) throws Exception {
    String url = intents.getUrl;
    url = url.split("<Intent_ID>")[0] + intentId + url.split("<Intent_ID>")[1];
    return gson.fromJson(doGet(url),Intent.class);
  }

  public String createIntent(String name) throws Exception {
    String url = intents.createUrl;
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("displayName", name);
    System.out.println(jsonObject);
    Intent intent = gson.fromJson(doPost(url, gson.toJson(jsonObject)),Intent.class);
    String intentId = intent.getName().split("/agent/intents/")[1];
    return intentId;
  }

  public String deleteIntent(String intentId) throws Exception {
    String url = intents.deleteUrl;
    url = url.split("<Intent_ID>")[0] + intentId;
    return doDelete(url);
  }

  public String addTrainingPhrases(customerService.model.javabean.rewabo.Intent intent) throws Exception {
    String url = intents.addTrainingPhrasesUrl;
    url = url.split("<Intent_ID>")[0] + intent.getIntent_Id() + url.split("<Intent_ID>")[1];
    JsonObject trainingPhrases = new JsonObject();
    List<TrainingPhrase> trainingPhraseList = new ArrayList<>();
    for (String s : intent.getTrainingPhrase()) {
      TrainingPhrase trainingPhrase = new TrainingPhrase();
      trainingPhrase.setName(UUID.randomUUID().toString());
      trainingPhrase.setType("EXAMPLE");
      List<PartsBean> partsBeanList = new ArrayList<>();
      PartsBean partsBean = new PartsBean();
      partsBean.setText(s);
      partsBeanList.add(partsBean);
      trainingPhrase.setParts(partsBeanList);
      trainingPhraseList.add(trainingPhrase);
    }
    trainingPhrases.add("trainingPhrases", gson.toJsonTree(trainingPhraseList));
    //System.out.println(trainingPhrases);
    return doPatch(url, gson.toJson(trainingPhrases));
  }


  private String doGet(String url) throws Exception {
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(HttpCommonAction.stringParser(url));
    httpGet.setHeader("Content-type", "application/json;charset=utf-8");
    httpGet.setHeader("Authorization", "Bearer " + getToken());
    HttpResponse theResponse = httpClient.execute(httpGet);//送出請求
    HttpEntity entity = theResponse.getEntity();//取得回應
    String responseString = EntityUtils.toString(entity, "UTF-8");//轉個編碼
    //System.out.println("google dialogflow 回應的資料：" + responseString);
    return responseString;
  }

  private String doPost(String url, String requestJson) throws Exception {
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost httpPost = new HttpPost(HttpCommonAction.stringParser(url));
    httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
    httpPost.setHeader("Authorization", "Bearer " + getToken());
    httpPost.setEntity(new StringEntity(requestJson,"utf-8"));
    System.out.println(requestJson);
    HttpResponse theResponse = httpClient.execute(httpPost);//送出請求
    HttpEntity entity = theResponse.getEntity();//取得回應
    String responseString = EntityUtils.toString(entity, "UTF-8");//轉個編碼
    //System.out.println("google dialogflow 回應的資料：" + responseString);
    return responseString;
  }

  private String doPatch(String url, String requestJson) throws Exception {
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPatch httpPatch = new HttpPatch(HttpCommonAction.stringParser(url));
    httpPatch.setHeader("Content-type", "application/json;charset=utf-8");
    httpPatch.setHeader("Authorization", "Bearer " + getToken());
    httpPatch.setEntity(new StringEntity(requestJson,"utf-8"));
    HttpResponse theResponse = httpClient.execute(httpPatch);//送出請求
    HttpEntity entity = theResponse.getEntity();//取得回應
    String responseString = EntityUtils.toString(entity, "UTF-8");//轉個編碼
    //System.out.println("google dialogflow 回應的資料：" + responseString);
    return responseString;
  }

  private String doDelete(String url) throws Exception {
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpDelete httpDelete = new HttpDelete(HttpCommonAction.stringParser(url));
    httpDelete.setHeader("Content-type", "application/json;charset=utf-8");
    httpDelete.setHeader("Authorization", "Bearer " + getToken());
    HttpResponse theResponse = httpClient.execute(httpDelete);//送出請求
    HttpEntity entity = theResponse.getEntity();//取得回應
    String responseString = EntityUtils.toString(entity, "UTF-8");//轉個編碼
    //System.out.println("google dialogflow 回應的資料：" + responseString);
    return responseString;
  }

  private String getToken() throws Exception {
    GoogleCredential credential =
        GoogleCredential.fromStream(new FileInputStream(new File(JsonPath.dialogFlowJson)));

    if (credential.createScopedRequired()) {
      credential = credential
          .createScoped(Collections.singletonList(dialogFlow.authUrl));
    }
    try {
      credential.refreshToken();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String token = credential.getAccessToken();
    //System.out.println(token);
    return token;
  }





}
