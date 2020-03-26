package translate.model.java;

import Menu.model.javabean.MenuClass;
import com.google.gson.JsonObject;
import customerService.model.javabean.rewabo.CustomerService;
import java.util.List;
import notification.javabean.OfferInfoJavabean;
import order.model.javabean.Order;

public interface DataTranslate {

  // 食物種類
  JsonObject getFoodJson(String chatId, List<MenuClass> menuClassList);

  // 一個食物種類下的餐點
  JsonObject getFoodKindsJson(String chatId, List<MenuClass> menuClassList);

  // 餐廳優惠資訊
  JsonObject handleOfferInfo(String chatId, List<OfferInfoJavabean> offerInfoJavabeanList);

  JsonObject getOfferInfoJson(String chatId, List<OfferInfoJavabean> offerInfoJavabeanList);

  JsonObject getPushInfoJson(List<String> userIdList, OfferInfoJavabean offerInfoJavabean);

  JsonObject getOrderOnlineJson(String chatId);

  JsonObject handleOrderContent(String chatId, List<Order> orderList);

  JsonObject handleOrderProgress(String chatId, List<Order> orderList);

  JsonObject handleOrderHistory(String chatId, List<Order> orderList);

  JsonObject getOrderReceiptJson(String chatId, Order order);

  JsonObject getReceiveOrderJson(String chatId, Order order);

  JsonObject getOrderProgressJson(String chatId, String orderId, String message);

  JsonObject getRecommendFoodJson(String chatId, Menu.model.javabean.Set menuSet);

  JsonObject getWelcomeJson(String chatId, List<CustomerService> customerServices);

  JsonObject getFixedJson(String chatId, String filePath);
}
