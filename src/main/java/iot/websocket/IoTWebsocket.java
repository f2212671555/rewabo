package iot.websocket;

import static iot.websocket.NoderedWebsocket.noderedWsSession;

import java.io.IOException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/iotEndpoint")

public class IoTWebsocket {

  public static Session iotWsSession;//這是Websocket的Session物件

  @OnOpen
  public void open(Session session, EndpointConfig config) {
    this.iotWsSession = session;
    System.out.println("onOpen::" + session.getId());
  }

  @OnClose
  public void onClose(Session session) {
    System.out.println("onClose::" + session.getId());
  }

  @OnMessage
  public void onMessageSession(String msg) throws IOException {

    noderedWsSession.getBasicRemote().sendText(msg);
  }

  @OnError
  public void onError(Throwable t) {
    System.out.println("onError::" + t.getMessage());
  }
}
