package order.model.java;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import order.model.javabean.OrderContent;
import org.bson.types.ObjectId;

import java.util.Calendar;
import java.util.Date;

import static pojo.QkConn.*;
import static pojo.QkConn.dbName;

public class SetOrderStatus {

    public void SetStatus(OrderContent javab){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,0); // number represents number of days

        // set today
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();
        System.out.println("today's date is: " + today);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE,+1); // number represents number of days

        // set tomorrow
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        Date tomorrow = cal2.getTime();

        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        DB db = mongoClient.getDB(dbName);
        DBCollection collection  = db.getCollection("Order");
        DBCollection collectionPeople  = db.getCollection("People");
        System.out.println("_id:"+javab.getOid());
        System.out.println("Status:"+javab.getStatus());
        BasicDBObject MyNewOrderStatus = new BasicDBObject().append("$set", new BasicDBObject().append("Status", javab.getStatus()));
        System.out.println(MyNewOrderStatus.toJson());
        collection.update(new BasicDBObject().append("_id", new ObjectId(javab.getOid())), MyNewOrderStatus);
        ////OrderID 要是相符合 就會把該筆訂單改成 老闆端傳來的MyStatus的狀態

        BasicDBObject queryPeople = new BasicDBObject();
        queryPeople.put("date", BasicDBObjectBuilder.start("$gt", today).add("$lt", tomorrow).get());//日期在today-tomorrow之間

        if(javab.getStatus().equals("已結帳")){
            BasicDBObject newDocument =
                    new BasicDBObject().append("$inc",
                            new BasicDBObject().append("People", (-1)*javab.getPeople()));//把People 設定加上現在的人數
            collectionPeople.update(queryPeople, newDocument);//使用update 將date 符合的 People 加上現在的人數
        }
    }
}
