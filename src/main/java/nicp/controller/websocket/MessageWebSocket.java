/*參考資料
Websocket範例 https://bit.ly/2Uf3zXb
於@ServerEndpoint取得HttpSession物件 Google關鍵字 "serverendpoint HttpSession" https://reurl.cc/GpMy3
Accessing HttpSession from HttpServletRequest in a Web Socket @ServerEndpoint(StackoverFlow) https://reurl.cc/E6OlA
*/
package nicp.controller.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DB;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import nicp.config.websocket.GetHttpSessionConfigurator;
import nicp.model.java.ManageMessages;
import nicp.model.javabean.Message;
import nicp.model.javabean.MessageRecord;
import nicp.model.javabean.NicpWebhook;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pojo.Settings.nicp.shopKeeper;
import pojo.Settings.nicp.urls.services;
import tool.HttpCommonAction;

@ServerEndpoint(value = "/endpoint", configurator = GetHttpSessionConfigurator.class)//設定websocket端點
//@ServerEndpoint(value = "/endpoint")
public class MessageWebSocket {

  private static final int BUFFERSIZE = 3;
  //public static Session wsSession;//這是Websocket的Session物件
  public static ConcurrentHashMap<String, Session> wsSessions = new ConcurrentHashMap<>();
  private HttpSession httpSession;//這是Servlet的Session物件
  private Gson gson = new GsonBuilder().disableHtmlEscaping()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
  public static ConcurrentLinkedQueue<MessageRecord> chatbotMessageBuffer = new ConcurrentLinkedQueue<>();
  private static ConcurrentLinkedQueue<MessageRecord> bossMessageBuffer = new ConcurrentLinkedQueue<>();

  @OnOpen
  public void open(Session session, EndpointConfig config) {
    this.wsSessions.put(session.getId(), session);
    this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

    //System.out.println("在open");
    //System.out.println(this.httpSession.getId());
    //System.out.println("onOpen::" + session.getId());
  }

  @OnClose
  public void onClose(Session session) {
    //this.httpSession.invalidate();
    try {
      session.close();
      wsSessions.remove(session.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    saveMessagesToDB(bossMessageBuffer);
    saveMessagesToDB(chatbotMessageBuffer);
    //System.out.println("onClose::" + session.getId());
  }

  @OnMessage
  public void onMessageSession(String msg) throws IOException {

    //System.out.println("Websocket客戶端傳來的訊息：" + msg);
    MessageRecord messageRecord = new MessageRecord();
    messageRecord.setBoss(msg);
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formatDate = df.format(date);
    messageRecord.setDate(formatDate);

    bossMessageBuffer.offer(messageRecord);
    System.out.println(bossMessageBuffer);

    if (bossMessageBuffer.size() >= BUFFERSIZE
        || chatbotMessageBuffer.size() >= BUFFERSIZE) {// 累積 BUFFERSIZE 組對話 再傳入DB
      saveMessagesToDB(bossMessageBuffer);
      saveMessagesToDB(chatbotMessageBuffer);

      //System.out.println(chatbotMessageBuffer);
      //System.out.println(bossMessageBuffer);
    }

    sendMessagesToNICPWebhook(msg);

//        String input1 = (String) httpSession.getAttribute("input1");
//        wsSession.getBasicRemote().sendText(input1);
//        System.out.println("wsSession的ID是:" + wsSession.getId());
//        System.out.println("httpSession的ID是:" + httpSession.getId());
  }

  @OnError
  public void onError(Throwable t) {
    saveMessagesToDB(bossMessageBuffer);
    saveMessagesToDB(chatbotMessageBuffer);
    //System.out.println("onError::" + t.getMessage());
  }

  private void saveMessagesToDB(ConcurrentLinkedQueue<MessageRecord> messageBuffer) {

    if(!messageBuffer.isEmpty()) {
      //MessageRecord[] messageRecords = gson.fromJson(messageBuffer,MessageRecord[].class);
      //MongoClient mongoClient = (MongoClient)httpSession.getServletContext().getAttribute("mongoDatabase");
      DB db = (DB) httpSession.getServletContext().getAttribute("db");
      //MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
      //DB db = mongoClient.getDB(dbName);
      ManageMessages manageMessages = new ManageMessages(db);
      manageMessages.saveMessages(messageBuffer);
      messageBuffer.clear();
    }else {
      System.out.println("messageBuffer is empty");
    }
  }

  private void sendMessagesToNICPWebhook(String msg) throws IOException{
    // 準備設置並呼叫NICP的Webhook服務
    //String url = "https://xanxus-node-red.cf/nicp/rewabo/webhook";// 正式
    //String testUrl = "https://ai-rest.cse.ntou.edu.tw/test/nicp/rewabo/webhook";// 測試
    String url = services.webhookUrl;
    String senderId = shopKeeper.id;
    NicpWebhook hookRequestData = new NicpWebhook("shopKeeper", senderId, new Message(msg.trim()));
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(HttpCommonAction.stringParser(url));
    post.setHeader("Content-type", "application/json;charset=utf-8");
    post.setEntity(new StringEntity(gson.toJson(hookRequestData), "UTF-8"));
    HttpResponse theResponse = httpClient.execute(post);//送出請求
    HttpEntity entity = theResponse.getEntity();//取得回應
    String responseString = EntityUtils.toString(entity, "UTF-8");//轉個編碼
    System.out.println("Webhook回應的資料：" + responseString);
  }
}
