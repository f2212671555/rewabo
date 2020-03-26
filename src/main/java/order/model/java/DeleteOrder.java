package order.model.java;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import order.model.javabean.OrderContent;
import org.bson.types.ObjectId;

import static pojo.QkConn.*;

public class DeleteOrder {


    public void delete(OrderContent javab){

        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        DB db = mongoClient.getDB(dbName);
        DBCollection collection  = db.getCollection("Order");
        System.out.println("_id:"+javab.getOid());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", new ObjectId(javab.getOid()));
        System.out.println(document.toJson());
        collection.remove(document);
    }
}
