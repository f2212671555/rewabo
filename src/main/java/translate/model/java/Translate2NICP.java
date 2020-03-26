package translate.model.java;

import Menu.model.javabean.MenuClass;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import customerService.model.javabean.rewabo.CustomerService;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import notification.javabean.OfferInfoJavabean;
import order.model.javabean.Order;
import pojo.JsonPath;
import pojo.Settings.rewabo.platform;
import pojo.Settings.rewabo.urls.webs;
import translate.model.tools.Modify;

public class Translate2NICP implements DataTranslate {

  public boolean checkCustomerService(List<CustomerService> customerServices, String checkingCustomerService) {
    for(CustomerService customerService : customerServices) {
      if(customerService.getLabel().equals(checkingCustomerService)) {
        return true;
      }
    }
    return false;
  }

  public JsonObject getFoodJson(String chatId, List<MenuClass> menuClassList) {

    JsonObject jsonObject = Modify.getJsonObject(JsonPath.foodJson); // get NICP Food.json

    // generate elements-----BEGIN
    JsonArray elements = new JsonArray();
    int count = 0;
    for (MenuClass menuClass : menuClassList) {
      count++;
      String price = menuClass.getPrice();
      String url = menuClass.getUrl();
      String name = menuClass.getName();

      JsonObject element = new JsonObject();
      element.addProperty("title", name);
      element.addProperty("subtitle", price);
      element.addProperty("imageUrl", url);
      elements.add(element);

      if (count == 10)// messenger limit "element" at most 10
      {
        JsonArray buttons = new JsonArray();
        buttons.add(generateUrlButton(webs.orderUrl, "點我查看更多"));
        element.add("buttons", buttons);
        break;
      }

    }
    // generate elements-----END
    String messageId = generateMessageId();
    jsonObject = Modify.setMessageId(messageId, jsonObject);
    jsonObject = Modify.setElements(jsonObject, elements);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  public JsonObject getFoodKindsJson(String chatId, List<MenuClass> menuClassList) {
    JsonObject jsonObject = Modify.getJsonObject(JsonPath.foodKindsJson); // get NICP Food.json

    // generate elements-----BEGIN
    JsonArray elements = new JsonArray();
    int count = 0;
    for (MenuClass menuClass : menuClassList) {
      count++;
      String url = menuClass.getUrl();
      String title = menuClass.getClassName();

      JsonObject element = new JsonObject();
      element.addProperty("title", title);
      element.addProperty("imageUrl", url);
      JsonArray buttons = new JsonArray();
      buttons.add(generatePostBackButton("查看", title));
      element.add("buttons", buttons);
      elements.add(element);

      if (count == 10)// messenger limit "element" at most 10
      {
        buttons.add(generateUrlButton(webs.orderUrl, "點我查看更多"));
        break;
      }

    }
    // generate elements-----END
    String messageId = generateMessageId();
    jsonObject = Modify.setMessageId(messageId, jsonObject);
    jsonObject = Modify.setElements(jsonObject, elements);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  // 多一個號碼牌（零時...
  private JsonObject getOrderContent(String chatId, List<Order> orderList) {

    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.orderRecordJson); // get NICP OrderRecordJson.json

    // generate elements-----BEGIN
    JsonArray elements = new JsonArray();
    int count = 0;
    for (Order order : orderList) {
      count++;
      String value = order.getId().getOid();
      String date = order.getDate();
      String title = date.split(" ")[0];
      String subtitle = date.split(" ")[1];

      JsonObject element = new JsonObject();
      element.addProperty("title", title);
      element.addProperty("subtitle", subtitle);
      JsonArray buttons = new JsonArray();
      buttons.add(generatePostBackButton("查看", value));
      if (order.getType().equals("外帶")) {
        String imgUrl = "https://ai-rest.cse.ntou.edu.tw/" + order.getNumber() + ".jpg";
        buttons.add(generatePostBackButton("號碼牌", imgUrl));
      }
      element.add("buttons", buttons);
      elements.add(element);

      if (count == 10)// messenger limit "element" at most 10
      {
        buttons.add(generateUrlButton(webs.historyOrderUrl, "點我查看更多"));
        break;
      }

    }
    // generate elements-----END

    jsonObject = Modify.setElements(jsonObject, elements);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  private JsonObject getOrderRecordJson(String chatId, List<Order> orderList) {

    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.orderRecordJson); // get NICP OrderRecordJson.json

    // generate elements-----BEGIN
    JsonArray elements = new JsonArray();
    int count = 0;
    for (Order order : orderList) {
      count++;
      String value = order.getId().getOid();
      String date = order.getDate();
      String title = date.split(" ")[0];
      String subtitle = date.split(" ")[1];

      JsonObject element = new JsonObject();
      element.addProperty("title", title);
      element.addProperty("subtitle", subtitle);
      JsonArray buttons = new JsonArray();
      buttons.add(generatePostBackButton("查看", value));
      element.add("buttons", buttons);
      elements.add(element);

      if (count == 10)// messenger limit "element" at most 10
      {
        buttons.add(generateUrlButton(webs.historyOrderUrl, "點我查看更多"));
        break;
      }

    }
    // generate elements-----END

    jsonObject = Modify.setElements(jsonObject, elements);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  public JsonObject handleOfferInfo(String chatId, List<OfferInfoJavabean> offerInfoJavabeanList) {

    JsonObject jsonObject;
    if (offerInfoJavabeanList.size() > 0) // 有優惠資訊
    {
      jsonObject = getOfferInfoJson(chatId, offerInfoJavabeanList);

    } else // 沒有優惠資訊
    {
      jsonObject = generateOrderProgressJson("目前沒有提供優惠喔");
    }
    return jsonObject;
  }

  public JsonObject getOfferInfoJson(String chatId, List<OfferInfoJavabean> offerInfoJavabeanList) {
    JsonObject jsonObject = Modify.getJsonObject(JsonPath.offerInfoJson); // get NICP OfferInfo.json

    // generate elements-----BEGIN
    JsonArray elements = new JsonArray();
    for (OfferInfoJavabean offerInfoJavabean : offerInfoJavabeanList) {

      String title = offerInfoJavabean.getId().getPushInfo();
      String subtitle = "";
      OfferInfoJavabean.MyMenuBean myMenuBean = offerInfoJavabean.getMyMenu().get(0);
      String className = myMenuBean.getClassName();
      String price = myMenuBean.getPrice();
      if (className.equals("套餐")) {

        int count = 0;
        for (String food : myMenuBean.getItem()) {
          if (count == 0) {
            subtitle += food;
          } else {
            subtitle += "、" + food;
          }
          count++;
        }
        subtitle += "\n只要" + price + "元";
      } else {
        String name = myMenuBean.getName();
        subtitle += name + "\n只要" + price + "元";
      }

      String imageUrl = myMenuBean.getUrl();
      String oid = myMenuBean.getId().getOid();
      String webUrl = webs.orderTargetContentUrl + oid;
      JsonObject element = new JsonObject();
      element.addProperty("title", title);
      element.addProperty("subtitle", subtitle);
      element.addProperty("imageUrl", imageUrl);
      JsonArray urlButtons = new JsonArray();
      urlButtons.add(generateUrlButton(webUrl, "前往並加入餐點"));
      element.add("buttons", urlButtons);
      elements.add(element);


    }
    // generate elements-----END

    jsonObject = Modify.setElements(jsonObject, elements);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  public JsonObject getPushInfoJson(List<String> userIdList, OfferInfoJavabean offerInfoJavabean) {
    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.pushInfoJson); // get NICP PushInfoJson.json

    JsonObject payload = jsonObject.getAsJsonObject("result").getAsJsonObject("payload");

    String content = offerInfoJavabean.getId().getPushInfo();
    String oid = offerInfoJavabean.getId().getId().getOid();
    String url = webs.orderTargetContentUrl + oid;

    payload.addProperty("content", content);

    JsonArray buttons = new JsonArray();
    buttons.add(generateUrlButtonWithAnswer("前往並加入餐點", url));

    payload.add("buttons", buttons);

    JsonArray userIDs = new JsonArray();
    for (int i = 0; i < userIdList.size(); i++) {
      String userID = userIdList.get(i);
      userIDs.add(userID);
    }
    jsonObject.add("userID", userIDs);

    return jsonObject;
  }

  public JsonObject getOrderOnlineJson(String chatId) {
    JsonObject button = generateUrlButtonWithAnswer("前往點餐", webs.orderUrl);
    JsonObject jsonObject = generateButtonTemplate(chatId, "來點餐吧~", button);
    return jsonObject;
  }

  public JsonObject getAddressJson(String chatId, String address, String url) {
    JsonObject button = generateUrlButtonWithAnswer("開啟地圖", url);
    JsonObject jsonObject = generateButtonTemplate(chatId, address, button);
    return jsonObject;
  }

  // handle 訂單內容(日期...)
  public JsonObject handleOrderContent(String chatId, List<Order> orderList) {
    JsonObject jsonObject;
    if (orderList.size() > 1) // 有一個以上的(非已結帳)訂單
    {
      jsonObject = getOrderContent(chatId, orderList); // tmp

    } else if (orderList.size() == 1) // 有一個(非已結帳)訂單
    {
      jsonObject = getOrderReceiptJson(chatId, orderList.get(0));
    } else // 沒有任何(非已結帳)訂單
    {
      jsonObject = generateOrderProgressJson("您目前沒有訂單");
    }
    return jsonObject;
  }

  // handle 訂單進度(日期...)
  public JsonObject handleOrderProgress(String chatId, List<Order> orderList) {
    JsonObject jsonObject;
    if (orderList.size() > 1) // 有一個以上的(非已結帳)訂單
    {
      jsonObject = getOrderRecordJson(chatId, orderList);

    } else if (orderList.size() == 1) // 有一個(非已結帳)訂單
    {
      jsonObject = generateOrderProgressJson(orderList.get(0).getStatus());
    } else // 沒有任何(非已結帳)訂單
    {
      jsonObject = generateOrderProgressJson("您目前沒有訂單");
    }
    return jsonObject;
  }

  public JsonObject handleOrderHistory(String chatId, List<Order> orderList) {

    JsonObject jsonObject;
    if (orderList.size() > 0) // 有(已結帳)訂單
    {
      jsonObject = getOrderRecordJson(chatId, orderList);

    } else // 沒有任何(已結帳)訂單
    {
      jsonObject = generateOrderProgressJson("您還沒有歷史訂單喔");
    }
    return jsonObject;
  }

  // one order when 查看訂單內容、歷史訂單時，回傳收據
  public JsonObject getOrderReceiptJson(String chatId, Order order) {
    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.orderReceiptJson); // get NICP OrderReceipt.json
    jsonObject = generateOrderReceipt(jsonObject, chatId, order);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  // one order when 點餐完畢時，回傳收據
  public JsonObject getReceiveOrderJson(String chatId, Order order) {
    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.receiveOrderJson); // get NICP ReceiveOrderJson.json
    jsonObject = generateOrderReceipt(jsonObject, chatId, order);
    String messageId = generateMessageId();
    jsonObject = Modify.setMessageId(messageId, jsonObject);
    return jsonObject;
  }

  // 訂單收據-收據範本
  private JsonObject generateOrderReceipt(JsonObject jsonObject, String chatId, Order order) {

    // generate elements-----BEGIN
    JsonArray elements = new JsonArray();

    for (Order.MyMenuBean meal : order.getMyMenu()) {
      String title = meal.getName();

      String quantity = meal.getAmount();
      String price = meal.getPrice();
      String imageUrl = meal.getUrl();
      elements.add(generateReceiptItem(title, quantity, price, imageUrl));

    }
    // generate elements-----END
    String timestamp = Modify.dateToTimeStamp(order.getDate(), "yyyy-MM-dd HH:mm:ss");
    jsonObject = Modify
        .setHistoryOrder(jsonObject, order.getNumber(), timestamp, order.getTotalPrice());
    jsonObject = Modify.setElements(jsonObject, elements);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    return jsonObject;
  }

  private JsonObject generateOrderProgressJson(String message) {
    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.orderProgressJson); // get NICP OrderProgress.json

    jsonObject.addProperty("result", "");
    jsonObject.addProperty("message", message);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  public JsonObject getOrderProgressJson(String chatId, String orderId, String message) {
    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.orderProgressJson); // get NICP OrderProgress.json

    jsonObject.getAsJsonObject("result").addProperty("chatId", chatId);
    jsonObject.getAsJsonObject("result").addProperty("orderID", orderId);
    jsonObject.addProperty("message", message);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  public JsonObject getRecommendFoodJson(String chatId, Menu.model.javabean.Set menuSet) {
    JsonObject jsonObject = Modify
        .getJsonObject(JsonPath.recommendFoodJson); // get NICP OrderRecordJson.json

    JsonObject result = jsonObject.getAsJsonObject("result");
    JsonObject payload = result.getAsJsonObject("payload");
    JsonArray buttons = payload.getAsJsonArray("buttons");
    JsonObject button = buttons.get(0).getAsJsonObject();

    payload.addProperty("content", menuSet.getName());
    String oid = menuSet.get_id().get$oid();
    String webUrl = webs.orderTargetContentUrl + oid;
    button.addProperty("url", webUrl);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    return jsonObject;
  }

  public JsonObject getWelcomeJson(String chatId, List<CustomerService> customerServices) {
    JsonObject jsonObject = Modify.getJsonObject(JsonPath.welcomeJson); // get NICP WelcomeJson.json

    JsonObject result = jsonObject.getAsJsonObject("result");
    JsonObject payload = result.getAsJsonObject("payload");
    if (customerServices.size() > 0) {
      JsonArray buttons = payload.getAsJsonArray("buttons");
      for (CustomerService customerService : customerServices) {
        JsonObject button = generateWelcomeButton(customerService.getLabel(),
            customerService.getValue());
        buttons.add(button);
      }
    }
    String content = "歡迎來到" + platform.name + platform.type + "~";
    payload.addProperty("content", content);
    String messageId = generateMessageId();
    jsonObject = Modify.setMessageId(messageId, jsonObject);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    return jsonObject;
  }

  public JsonObject getFixedJson(String chatId, String filePath) {
    JsonObject jsonObject = Modify.getJsonObject(filePath); // get NICP OrderRecordJson.json...

    String messageId = generateMessageId();
    jsonObject = Modify.setMessageId(messageId, jsonObject);
    jsonObject = Modify.setChatId(chatId, jsonObject);
    jsonObject.addProperty("status", HttpServletResponse.SC_OK);
    return jsonObject;
  }

  private JsonObject generateButtonTemplate(String chatId, String content, JsonObject button) {
    JsonObject buttonTemplate = Modify
        .getJsonObject(JsonPath.orderOnlineJson); // get NICP OrderRecordJson.json
    JsonObject payload = buttonTemplate.getAsJsonObject("result").getAsJsonObject("payload");
    payload.addProperty("content", content);
    JsonArray buttons = new JsonArray();
    buttons.add(button);
    payload.add("buttons", buttons);
    buttonTemplate = Modify.setChatId(chatId, buttonTemplate);
    buttonTemplate.addProperty("status", HttpServletResponse.SC_OK);
    return buttonTemplate;
  }

  private JsonObject generatePostBackButton(String label, String value) {
    JsonObject button = new JsonObject();
    button.addProperty("type", "postback");
    button.addProperty("label", label);
    button.addProperty("value", value);
    button.addProperty("answer", "");
    button.addProperty("alert", false);
    button.addProperty("style", "default");
    return button;
  }

  private JsonObject generateUrlButton(String url, String msg) {
    JsonObject button = new JsonObject();
    button.addProperty("type", "url");
    button.addProperty("label", msg);
    button.addProperty("url", url);
    button.addProperty("webViewHeightRatio", "full");
    button.addProperty("extensions", true);
    button.addProperty("alert", false);
    return button;
  }

  private JsonObject generateUrlButtonWithAnswer(String label, String url) {
    JsonObject button = new JsonObject();
    button.addProperty("type", "url");
    button.addProperty("label", label);
    button.addProperty("url", url);
    button.addProperty("webViewHeightRatio", "full");
    button.addProperty("extensions", true);
    button.addProperty("answer", "");
    button.addProperty("alert", false);
    return button;
  }

  private JsonObject generateWelcomeButton(String label, String value) {
    JsonObject button = new JsonObject();
    button.addProperty("type", "quick-reply");
    button.addProperty("label", label);
    button.addProperty("value", value);
    return button;
  }

  private JsonObject generateReceiptItem(String title, String quantity, String price,
      String imageUrl) {
    JsonObject element = new JsonObject();
    element.addProperty("title", title);
    element.addProperty("subtitle", "店長推薦");
    element.addProperty("quantity", quantity);
    element.addProperty("price", price);
    element.addProperty("currency", "TWD");
    element.addProperty("imageUrl", imageUrl);
    return element;
  }

  private String generateMessageId() {
    return UUID.randomUUID().toString();
  }

}
