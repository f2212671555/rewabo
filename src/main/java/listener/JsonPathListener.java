package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import pojo.JsonPath;

@WebListener()
public class JsonPathListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

  public JsonPathListener() {
  }

  public void contextInitialized(ServletContextEvent sce) {

    ServletContext sc  = sce.getServletContext();
    JsonPath.foodJson = generateFilePath(sc,"FoodJson");
    JsonPath.foodKindsJson = generateFilePath(sc,"FoodKindsJson");
    JsonPath.offerInfoJson = generateFilePath(sc,"OfferInfoJson");
    JsonPath.orderOnlineJson = generateFilePath(sc,"OrderOnlineJson");
    JsonPath.orderProgressJson = generateFilePath(sc,"OrderProgressJson");
    JsonPath.orderReceiptJson = generateFilePath(sc,"OrderReceiptJson");
    JsonPath.orderRecordJson = generateFilePath(sc,"OrderRecordJson");
    JsonPath.pushInfoJson = generateFilePath(sc,"PushInfoJson");
    JsonPath.receiveOrderJson = generateFilePath(sc,"ReceiveOrderJson");
    JsonPath.recommendFoodJson = generateFilePath(sc,"RecommendFoodJson");
    JsonPath.restInfoJson = generateFilePath(sc,"RestInfoJson");
    JsonPath.viewOrderJson = generateFilePath(sc,"ViewOrderJson");
    JsonPath.welcomeJson = generateFilePath(sc,"WelcomeJson");
    JsonPath.dialogFlowJson = generateFilePath(sc,"DialogFlowJson");
    JsonPath.createIntentJson = generateFilePath(sc,"CreateIntentJson");
    JsonPath.updateIntentJson = generateFilePath(sc,"UpdateIntentJson");
  }

  public void contextDestroyed(ServletContextEvent sce) {
  }

  private String generateFilePath(ServletContext sc, String param) {
    String roughPath = sc.getInitParameter(param);//從web.xml取得檔案粗略位置
    String filePath = sc.getRealPath(roughPath);//取得完整路徑
    return  filePath;
  }
}
