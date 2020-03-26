package order.model.java;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import static pojo.QkConn.*;

public class ShowPeople {
    static JSONArray order;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> collection;

    public ShowPeople(){
        connectDb();
    }

    public JSONArray getJson() {
        return order;
    }

    public void connectDb() {


        try {
            mongoClient = new MongoClient(dbHost, mongoDBPort);
            mongoDatabase = mongoClient.getDatabase(dbName);
            collection = mongoDatabase.getCollection("Order");




        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }



    public void getPeople() {
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
        DB db = mongoClient.getDB(dbName);

        DBCollection collectionPeople  = db.getCollection("People");
        BasicDBObject queryPeople = new BasicDBObject();
        queryPeople.put("date", BasicDBObjectBuilder.start("$gt", today).add("$lt", tomorrow).get());//日期在today-tomorrow之間

        DBCursor cursor = collectionPeople.find(queryPeople);
        System.out.println(queryPeople.toJson());

        JSONArray jsonArray = new JSONArray();

        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            JSONObject jsonobj = new JSONObject();
            int people = obj.getInt("People");
            if (people<0){
                people = 0;
            }
            jsonobj.put("People",people);

            System.out.println(jsonobj);
            jsonArray.put(jsonobj);
        }

        order = jsonArray;
    }
}
