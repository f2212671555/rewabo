package translate.model.javabean.nicp.originalMessage;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OriginalMessage {
  private String transport;
  private ChatBean chat;

  public String getTransport() {
    return transport;
  }

  public void setTransport(String transport) {
    this.transport = transport;
  }

  public ChatBean getChat() {
    return chat;
  }

  public void setChat(ChatBean chat) {
    this.chat = chat;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("transport", transport)
        .append("chat", chat)
        .toString();
  }

  public static class ChatBean {
    private String id;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("id", id)
          .toString();
    }
  }
}
