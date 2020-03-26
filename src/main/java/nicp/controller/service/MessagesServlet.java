package nicp.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import nicp.controller.websocket.MessageWebSocket;
import nicp.model.javabean.MessageRecord;
import nicp.model.javabean.Messages;
import nicp.model.javabean.MessagesResponse;
import tool.HttpCommonAction;

// 機器人和老闆說的話，NICP呼叫這個服務來傳
// URL-Pattern http://127.0.0.1:8080/rewabo/messages
// http://127.0.0.1:8080/rewabo/WebsocketWebTest.html
public class MessagesServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");

    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    Messages s = gson
        .fromJson(HttpCommonAction.getRequestBody(request.getReader()), Messages.class);
//        System.out.println(s.getMessage().getText());

    MessageRecord messageRecord = new MessageRecord();
    messageRecord.setRecipientId(s.getRecipientId());
    messageRecord.setChatbot(s.getMessage().getText());
    //JsonObject messageObject = new JsonObject();
    //messageObject.addProperty("recipientId", s.getRecipientId());
    //messageObject.addProperty("chatbot", s.getMessage().getText());
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formatDate = df.format(date);
    messageRecord.setDate(formatDate);
    messageRecord.setOriginalSenderId(s.getOriginalSenderId());
    //messageObject.addProperty("date", formatDate);
    //messageObject.addProperty("originalSenderId", s.getOriginalSenderId());
    MessageWebSocket.chatbotMessageBuffer.offer(messageRecord);
    System.out.println(MessageWebSocket.chatbotMessageBuffer);

    for (Session session : MessageWebSocket.wsSessions.values()) {
      session.getBasicRemote()
          .sendText(s.getMessage().getText());//呼叫Websocket物件將訊息傳給老闆端
    }

    MessagesResponse mr = new MessagesResponse(Integer.toString(response.getStatus()));
    PrintWriter out = response.getWriter();
    out.print(gson.toJson(mr));//回應
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
//        HttpSession session = request.getSession();
//        response.setHeader("Cache-Control", "no-store");
//        System.out.println(session.getId());
  }
}
