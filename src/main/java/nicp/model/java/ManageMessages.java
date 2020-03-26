package nicp.model.java;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.util.concurrent.ConcurrentLinkedQueue;
import nicp.model.javabean.MessageRecord;

public class ManageMessages {

  private MongoClient mongoClient;
  private DB db;

  public ManageMessages(MongoClient mongoClient, DB db) {
    this.mongoClient = mongoClient;
    this.db = db;
  }

  public ManageMessages(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public ManageMessages(DB db) {
    this.db = db;
  }

  public void saveMessages(ConcurrentLinkedQueue<MessageRecord> messageRecords) {
    DBCollection collection = db.getCollection("Message");

    for (MessageRecord s : messageRecords) {
      BasicDBObject document = new BasicDBObject();

      document.put("recipientId", s.getRecipientId());
      document.put("getOriginalSenderId", s.getOriginalSenderId());
      document.put("Boss", s.getBoss());
      document.put("Chatbot", s.getChatbot());
      document.put("Date", s.getDate());
      //System.out.println(document.toJson());
      collection.insert(document);
    }
  }
}
