package listener;

import com.google.gson.*;
import pojo.QkConn;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigConnectDataListener implements ServletContextListener {

    // 定義當整個WebAPP一部署(啟動)時會做的事
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        QkConn.rewaboAppContextPath = sce.getServletContext().getContextPath();
        ServletContext sc = sce.getServletContext();
        String roughPath = sc.getInitParameter("ServerConnectDataJson");//從web.xml取得檔案粗略位置
        String fullPath = sc.getRealPath(roughPath);//取得完整路徑
        setQkConn(fullPath);
    }

    // 定義當整個WebAPP銷毀、結束時會做的事
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void setQkConn(String fullPath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fullPath)));
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(content);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                JsonElement rewaboServerLocation = jsonObject.get("rewaboServerLocation");
                JsonElement rewaboServerIP = jsonObject.get("rewaboServerIP");
                JsonElement tomcatPort = jsonObject.get("tomcatPort");
                JsonElement mqttPort = jsonObject.get("mqttPort");
                JsonElement mongoDBPort = jsonObject.get("mongoDBPort");
                JsonElement nicpServerLocation = jsonObject.get("nicpServerLocation");
                JsonElement rewaboCloudBaseURL = jsonObject.get("rewaboCloudBaseURL");
                JsonElement rewaboLocalBaseURL = jsonObject.get("rewaboLocalBaseURL");
                JsonElement nicpCloudBaseURL = jsonObject.get("nicpCloudBaseURL");
                JsonElement dbHost = jsonObject.get("dbHost");
                JsonElement dbName = jsonObject.get("dbName");
                QkConn.rewaboServerLocation = rewaboServerLocation.getAsString();
                QkConn.rewaboServerIP = rewaboServerIP.getAsString();
                QkConn.tomcatPort = tomcatPort.getAsInt();
                QkConn.mqttPort = mqttPort.getAsInt();
                QkConn.mongoDBPort = mongoDBPort.getAsInt();
                QkConn.nicpServerLocation = nicpServerLocation.getAsString();
                QkConn.rewaboCloudBaseURL = rewaboCloudBaseURL.getAsString();
                QkConn.rewaboLocalBaseURL = rewaboLocalBaseURL.getAsString();
                QkConn.nicpCloudBaseURL = nicpCloudBaseURL.getAsString();
                QkConn.dbHost = dbHost.getAsString();
                QkConn.dbName = dbName.getAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
