package iot.websocket;

import java.io.IOException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/NoderedEndpoint")

public class NoderedWebsocket {

    //public static Session noderedWsSession;//這是Websocket的Session物件
    public static Session noderedWsSession;
    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.noderedWsSession = session;
        System.out.println("onOpen::" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" + session.getId());
    }

    @OnMessage
    public void onMessageSession(String msg) throws IOException {
        System.out.println("Node-red拿到：" + msg);
        IoTWebsocket.iotWsSession.getBasicRemote().sendText(msg);
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("Node-red onError::" + t.getMessage());
    }
}
