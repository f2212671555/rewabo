package order.model.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/bossOrderEndpoint")
public class BossOrderSocket {
    public static Session bossOrderWsSession;//這是Websocket的Session物件
//    private HttpSession httpSession;//這是Servlet的Session物件

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.bossOrderWsSession = session;
//        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//        System.out.println("在open");
//        System.out.println(this.httpSession.getId());
        System.out.println("onOpen::" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
//        this.httpSession.invalidate();
//        try {
//            session.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("onClose::" + session.getId());
    }

    @OnMessage
    public void onMessageSession(String msg) throws IOException {
        System.out.println("Websocket客戶端傳來的訊息：" + msg);

    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }

}
