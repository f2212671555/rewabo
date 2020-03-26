package listener;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import pojo.Settings;
import pojo.Settings.google.api.dialogFlow;
import pojo.Settings.google.api.dialogFlow.intents;

@WebListener()
public class SettingsListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

  public SettingsListener() {
  }

  public void contextInitialized(ServletContextEvent sce) {
    ServletContext sc = sce.getServletContext();
    String roughPath = sc.getInitParameter("SettingsJson");//從web.xml取得檔案粗略位置
    String fullPath = sc.getRealPath(roughPath);//取得完整路徑
    setSettings(fullPath);
  }

  public void contextDestroyed(ServletContextEvent sce) {
  }

  private void setSettings(String fullPath) {
    try {
      String content = new String(Files.readAllBytes(Paths.get(fullPath)));
      JsonParser parser = new JsonParser();
      JsonElement jsonTree = parser.parse(content);
      if (jsonTree.isJsonObject()) {
        JsonObject jsonObject = jsonTree.getAsJsonObject();

        JsonObject nicp = jsonObject.getAsJsonObject("nicp");
        JsonObject nicpUrls = nicp.getAsJsonObject("urls");
        String nicpBase = nicpUrls.get("baseUrl").getAsString();
        JsonObject nicpServices = nicpUrls.getAsJsonObject("services");
        String webhookUrl = nicpServices.get("webhookUrl").getAsString();
        String receiveCustomerOrderUrl = nicpServices.get("receiveCustomerOrderUrl").getAsString();
        String pushUrl = nicpServices.get("pushUrl").getAsString();
        String shopKeeperId = nicp.getAsJsonObject("shopKeeper").get("id").getAsString();

        JsonObject rewabo = jsonObject.getAsJsonObject("rewabo");
        JsonObject rewaboPlatform = rewabo.getAsJsonObject("platform");
        String name = rewaboPlatform.get("name").getAsString();
        String type = rewaboPlatform.get("type").getAsString();
        String address = rewaboPlatform.get("address").getAsString();
        String googleMapUrl = rewaboPlatform.get("googleMapUrl").getAsString();
        JsonObject rewaboUrls = rewabo.getAsJsonObject("urls");
        String rewaboBase = rewaboUrls.get("baseUrl").getAsString();
        JsonObject rewaboWebs = rewaboUrls.getAsJsonObject("webs");
        String orderUrl = rewaboWebs.get("orderUrl").getAsString();
        String orderTargetContentUrl = rewaboWebs.get("orderTargetContentUrl").getAsString();
        String historyOrderUrl = rewaboWebs.get("historyOrderUrl").getAsString();

        JsonObject google = jsonObject.getAsJsonObject("google");
        JsonObject googleApi = google.getAsJsonObject("api");
        JsonObject dialogflow = googleApi.getAsJsonObject("dialogflow");
        String dialogFlowAuthUrl = dialogflow.get("authUrl").getAsString();
        String dialogFlowBaseUrl = dialogflow.get("baseUrl").getAsString();
        JsonObject intents = dialogflow.getAsJsonObject("intents");
        String dialogFlowListUrl = intents.get("listUrl").getAsString();
        String dialogFlowCreateUrl = intents.get("createUrl").getAsString();
        String dialogFlowAddTrainingPhrasesUrl = intents.get("addTrainingPhrasesUrl").getAsString();
        String dialogFlowDeleteUrl = intents.get("deleteUrl").getAsString();
        String dialogFlowGetUrl = intents.get("getUrl").getAsString();
        // set--BEGIN
        // set--nicp--BEGIN
        Settings.nicp.urls.baseUrl = nicpBase;
        Settings.nicp.urls.services.webhookUrl = nicpBase + webhookUrl;
        Settings.nicp.urls.services.receiveCustomerOrderUrl = nicpBase + receiveCustomerOrderUrl;
        Settings.nicp.urls.services.pushUrl = nicpBase + pushUrl;
        Settings.nicp.shopKeeper.id = shopKeeperId;
        // set--nicp--END
        // set--rewabo--BEGIN
        Settings.rewabo.platform.name = name;
        Settings.rewabo.platform.type = type;
        Settings.rewabo.platform.address = address;
        Settings.rewabo.platform.googleMapUrl = googleMapUrl;
        Settings.rewabo.urls.baseUrl = rewaboBase;
        Settings.rewabo.urls.webs.orderUrl = rewaboBase + orderUrl;
        Settings.rewabo.urls.webs.orderTargetContentUrl = rewaboBase + orderTargetContentUrl;
        Settings.rewabo.urls.webs.historyOrderUrl = rewaboBase + historyOrderUrl;
        // set--rewabo--BEGIN
        // set--google--BEGIN
        Settings.google.api.dialogFlow.authUrl = dialogFlowAuthUrl;
        Settings.google.api.dialogFlow.baseUrl = dialogFlowBaseUrl;
        Settings.google.api.dialogFlow.intents.listUrl = dialogFlowBaseUrl + dialogFlowListUrl;
        Settings.google.api.dialogFlow.intents.createUrl = dialogFlowBaseUrl + dialogFlowCreateUrl;
        Settings.google.api.dialogFlow.intents.addTrainingPhrasesUrl = dialogFlowBaseUrl + dialogFlowAddTrainingPhrasesUrl;
        Settings.google.api.dialogFlow.intents.deleteUrl = dialogFlowBaseUrl + dialogFlowDeleteUrl;
        dialogFlow.intents.getUrl = dialogFlowBaseUrl + dialogFlowGetUrl;
        // set--google--END
        //set--END

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
