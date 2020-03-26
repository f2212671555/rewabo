package translate.model.javabean.nicp.result;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import translate.model.javabean.nicp.originalMessage.OriginalMessage;
import translate.model.javabean.nicp.payload.Payload;

public class Result {
  private Payload payload;
  private OriginalMessage originalMessage;
  @SerializedName("_msgid")
  private String msgid;
  private String chatId;
  private String orderID;

  public Payload getPayload() {
    return payload;
  }

  public void setPayload(Payload payload) {
    this.payload = payload;
  }

  public OriginalMessage getOriginalMessage() {
    return originalMessage;
  }

  public void setOriginalMessage(
      OriginalMessage originalMessage) {
    this.originalMessage = originalMessage;
  }

  public String getMsgid() {
    return msgid;
  }

  public void setMsgid(String msgid) {
    this.msgid = msgid;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public String getOrderID() {
    return orderID;
  }

  public void setOrderID(String orderID) {
    this.orderID = orderID;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("payload", payload)
        .append("originalMessage", originalMessage)
        .append("msgid", msgid)
        .append("chatId", chatId)
        .append("orderID", orderID)
        .toString();
  }
}
