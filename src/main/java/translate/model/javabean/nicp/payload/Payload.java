package translate.model.javabean.nicp.payload;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import translate.model.javabean.nicp.button.Button;
import translate.model.javabean.nicp.element.Element;

public class Payload {
  public String type;
  public String name;
  public String content;
  public String aspectRatio;
  public boolean sharable;
  public List<Element> elements;
  public List<Button> buttons;
  public String chatId;
  public String messageId;
  public String recipientName;
  public String orderNumber;
  public String currency;
  public String timestamp;
  public SummaryBean summary;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAspectRatio() {
    return aspectRatio;
  }

  public void setAspectRatio(String aspectRatio) {
    this.aspectRatio = aspectRatio;
  }

  public boolean isSharable() {
    return sharable;
  }

  public void setSharable(boolean sharable) {
    this.sharable = sharable;
  }

  public List<Element> getElements() {
    return elements;
  }

  public void setElements(List<Element> elements) {
    this.elements = elements;
  }

  public List<Button> getButtons() {
    return buttons;
  }

  public void setButtons(List<Button> buttons) {
    this.buttons = buttons;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public SummaryBean getSummary() {
    return summary;
  }

  public void setSummary(SummaryBean summary) {
    this.summary = summary;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("name", name)
        .append("content", content)
        .append("aspectRatio", aspectRatio)
        .append("sharable", sharable)
        .append("elements", elements)
        .append("buttons", buttons)
        .append("chatId", chatId)
        .append("messageId", messageId)
        .append("recipientName", recipientName)
        .append("orderNumber", orderNumber)
        .append("currency", currency)
        .append("timestamp", timestamp)
        .append("summary", summary)
        .toString();
  }

  public static class SummaryBean {
    public String totalCost;

    public String getTotalCost() {
      return totalCost;
    }

    public void setTotalCost(String totalCost) {
      this.totalCost = totalCost;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("totalCost", totalCost)
          .toString();
    }
  }
}
