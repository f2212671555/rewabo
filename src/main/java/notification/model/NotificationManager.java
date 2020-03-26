package notification.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import notification.javabean.Notification;
import notification.javabean.OfferInfoJavabean;
import notification.javabean.ScheduleJob;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Filters.eq;
import static pojo.QkConn.*;


public class NotificationManager {

    private MongoDatabase mongoDatabase;
    private DB db;
    public NotificationManager(MongoDatabase mongoDatabase, DB db){
        this.mongoDatabase = mongoDatabase;
        this.db = db;
    }

    public void addNotification(Notification notification){


        DBCollection collection  = db.getCollection("PushInfo");
        DBCollection collection2 = db.getCollection("Menu");
        DBCollection collection4= db.getCollection("SetPrice");
        MongoCollection collection3  = mongoDatabase.getCollection("Menu");

        BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
                .add("date",notification.getDate())
                .add("Name",notification.getName())
                .add("Price",notification.getPrice())
                .add("PushInfo",notification.getPushInfo())
                .add("Time",notification.getTime())
                .add("type",notification.gettype());
        collection.insert(documentBuilder.get());
        //先將要推播的資訊新增

        BasicDBObject query = new BasicDBObject();
        query.put("Name",notification.getName());//比對要推播的餐點

        FindIterable<Document> findIterable = collection3.find(query);
        MongoCursor<Document> cursorPrice = findIterable.iterator();
        Document SetPrice = cursorPrice.next();
        String Price1 = (String) SetPrice.get("Price");//get("Price")的value -> 會是 套餐的價錢
        //選擇要推播餐點原本的價錢

        BasicDBObjectBuilder documentBuilder2 = BasicDBObjectBuilder.start()
                .add("Name",notification.getName())
                .add("Price",Price1)
                .add("date",notification.getDate());

        collection4.insert(documentBuilder2.get());
        //將原價記在資料庫 OK
        BasicDBObject newDocument =
                new BasicDBObject().append("$set",
                        new BasicDBObject().append("Price", notification.getPrice()));

        collection2.update(query, newDocument);
        //再將要推播的餐點的價錢改為現在所推播的價錢


        ScheduleJob scheduleJob = scheduleHandle(notification);

        QuartzManager.addJob(scheduleJob,notification);
        System.out.println("新增成功");
    }
    public ScheduleJob scheduleHandle(Notification notification){
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setJobName(notification.getName());
        scheduleJob.setJobGroupName(notification.getName());
        scheduleJob.setTriggerName(notification.getName());
        scheduleJob.setTriggerGroupName(notification.getName());
        scheduleJob.setBeanClass("cn.zzs.test.MyTest");
        scheduleJob.setMethodName("show");
        int time =  5*notification.getTime();//time*5秒
        //notification.getTime()時間要改為串流
        // 以下要改為 scheduleJob.setCronExpression("0/1 * * * * ?");// 每多久執行一次執行一次
        scheduleJob.setCronExpression("0/"+time+" * * * * ?");// 每time秒鐘執行一次

        return scheduleJob;
    }

    public void deleteNotification(Notification notification){
        DBCollection collection = db.getCollection("PushInfo");
        DBCollection collectionMenu = db.getCollection("Menu");
        DBCollection collectionSetPrice = db.getCollection("SetPrice");
        MongoCollection collection2  = mongoDatabase.getCollection("SetPrice");
        MongoCollection collection3  = mongoDatabase.getCollection("PushInfo");

        BasicDBObject query = new BasicDBObject();
        query.put("_id",new ObjectId(notification.getoid()));//比對要推播的_id
        FindIterable<Document> findIterable3 = collection3.find(query);
        MongoCursor<Document> cursorPrice3 = findIterable3.iterator();
        Document PushInfName = cursorPrice3.next();
        String Name = (String) PushInfName.get("Name");//get("Name")的value -> 會是名稱


        BasicDBObject document = new BasicDBObject();
        document.put("Name",Name);//比對要推播的餐點
        collection.remove(document);


        FindIterable<Document> findIterable = collection2.find(document);
        MongoCursor<Document> cursorPrice = findIterable.iterator();
        Document SetPrice = cursorPrice.next();
        String Price = (String) SetPrice.get("Price");//get("Price")的value -> 會是 原價

        BasicDBObject newDocument =
                new BasicDBObject().append("$set",
                        new BasicDBObject().append("Price", Price));

        collectionMenu.update(document, newDocument);

        collectionSetPrice.remove(document);

        ScheduleJob scheduleJob = scheduleHandle(notification);
        QuartzManager.removeJob(scheduleJob);
        System.out.println("刪除成功");
    }



    public JSONArray ShowPush() {

        JSONArray jsonArray = new JSONArray();
        try {
            MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);

            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = mongoDatabase.getCollection("PushInfo");
            Bson match  = new BasicDBObject();
            match = eq("type","定期推播");

            BasicDBObject order_id = new BasicDBObject();
            order_id.put("_id", "$_id");
            order_id.put("PushInfo", "$PushInfo");
            order_id.put("date", "$date");
            order_id.put("Time", "$Time");

            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    Aggregates.match(Filters.and(match)),
                    Aggregates.lookup("Menu", "Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id, push("MyMenu", "$Info"))
            ));

            MongoCursor<Document> cursor = output.iterator();


            while (cursor.hasNext()) {

                JSONObject jsonObect = new JSONObject(cursor.next().toJson());
                jsonArray.put(jsonObect);
                //System.out.println(cursor.next().toJson(writerSettings));
            }


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return jsonArray;

    }

    //優惠資訊
    public ArrayList<OfferInfoJavabean> ShowPush2fb() {
        ArrayList<OfferInfoJavabean> notifications = new ArrayList<>();
        Gson gson = new Gson();
        OfferInfoJavabean tmp;
        try {
            MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);

            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = mongoDatabase.getCollection("PushInfo");


            BasicDBObject order_id = new BasicDBObject();
            order_id.put("_id", "$_id");
            order_id.put("PushInfo", "$PushInfo");
            order_id.put("date", "$date");
            order_id.put("Time", "$Time");
            BasicDBObject match =  new BasicDBObject("type", "定期推播") ;

            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    Aggregates.match(match),
                    Aggregates.lookup("Menu", "Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id, push("MyMenu", "$Info"))
            ));

            MongoCursor<Document> cursor = output.iterator();


            while (cursor.hasNext()) {


                Document document = cursor.next();

                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(document.toJson());




                tmp = gson.fromJson(mJson.getAsJsonObject(), OfferInfoJavabean.class);
                System.out.println(tmp);
                notifications.add(tmp);

            }


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return notifications;

    }
}
