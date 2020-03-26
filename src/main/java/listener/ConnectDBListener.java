package listener;

import static pojo.QkConn.dbHost;
import static pojo.QkConn.dbName;
import static pojo.QkConn.mongoDBPort;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class ConnectDBListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

  MongoClient mongoClient;

  public ConnectDBListener() {
  }

  public void contextInitialized(ServletContextEvent sce) {
    mongoClient = new MongoClient(dbHost, mongoDBPort);
    MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
    sce.getServletContext().setAttribute("mongoDatabase", mongoDatabase);
    DB db = mongoClient.getDB(dbName);
    sce.getServletContext().setAttribute("db", db);
  }

  public void contextDestroyed(ServletContextEvent sce) {
    sce.getServletContext().removeAttribute("mongoDatabase");
    sce.getServletContext().removeAttribute("db");
    mongoClient.close();
  }
}
