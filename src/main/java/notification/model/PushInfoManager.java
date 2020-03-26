package notification.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import notification.javabean.OfferInfoJavabean;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static pojo.QkConn.*;
import static pojo.QkConn.dbName;

public class PushInfoManager {
    private ArrayList<OfferInfoJavabean> OfferInfo ;


    public void add(String date,String Name,String Price,String PushInfo){

        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        DB db = mongoClient.getDB(dbName);
        DBCollection collection  = db.getCollection("PushInfo");
        DBCollection collection2 = db.getCollection("Menu");
        DBCollection collection4= db.getCollection("SetPrice");
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

        MongoCollection collection3  = mongoDatabase.getCollection("Menu");




        BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
                .add("date",date)
                .add("Name",Name)
                .add("Price",Price)
                .add("PushInfo",PushInfo);
        collection.insert(documentBuilder.get());
        //先將要推播的資訊新增

        BasicDBObject query = new BasicDBObject();
        query.put("Name",Name);//比對要推播的餐點

        FindIterable<Document> findIterable = collection3.find(query);
        MongoCursor<Document> cursorPrice = findIterable.iterator();
        Document SetPrice = cursorPrice.next();
        String Price1 = (String) SetPrice.get("Price");//get("Price")的value -> 會是 套餐的價錢
        //選擇要推播餐點原本的價錢

        BasicDBObjectBuilder documentBuilder2 = BasicDBObjectBuilder.start()
                .add("Name",Name)
                .add("Price",Price1)
                .add("date",date);

        collection4.insert(documentBuilder2.get());
        //將原價記在資料庫 OK
        BasicDBObject newDocument =
                new BasicDBObject().append("$set",
                        new BasicDBObject().append("Price", Price));

        collection2.update(query, newDocument);
        //再將要推播的餐點的價錢改為現在所推播的價錢
    }


    public  void deletePushInfo(String oid){
        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
//        MongoCollection<Document> collection = mongoDatabase.getCollection("PushInfo");


        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection("PushInfo");
        DBCollection collectionMenu = db.getCollection("Menu");
        DBCollection collectionSetPrice = db.getCollection("SetPrice");
        MongoCollection collection2  = mongoDatabase.getCollection("SetPrice");
        MongoCollection collection3  = mongoDatabase.getCollection("PushInfo");
        //將PushInfo刪除
        BasicDBObject query = new BasicDBObject();
        query.put("_id",new ObjectId(oid));//比對要推播的_id
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
    }

    //老闆前端
    public JSONArray ShowPush() {

        JSONArray jsonArray = new JSONArray();
        try {
            MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);

            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = mongoDatabase.getCollection("PushInfo");
            Bson match  = new BasicDBObject();
            match = ne("type","定期推播");

            BasicDBObject order_id = new BasicDBObject();
            order_id.put("_id","$_id");
            order_id.put("PushInfo","$PushInfo");
            order_id.put("date","$date");

            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    Aggregates.match(Filters.and(match)),
                    Aggregates.lookup("Menu", "Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id,push("MyMenu","$Info"))
            ));

            MongoCursor<Document> cursor = output.iterator();



            while (cursor.hasNext()) {

                //     Document document = cursor.next();

                //    Gson gson = new Gson();
                //  offerInfoJavabean = gson.fromJson(document.toJson(), OfferInfoJavabean.class);

                JSONObject jsonObect = new JSONObject(cursor.next().toJson());
                jsonArray.put(jsonObect);
                //System.out.println(cursor.next().toJson(writerSettings));
            }


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return jsonArray;

    }


    //to 轉譯
    public ArrayList<String> getCustomerID(){
        ArrayList<String> arrayList = new ArrayList<String>();

        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection2 = mongoDatabase.getCollection("CustomerID");
        FindIterable<Document> findIterable = collection2.find();

        MongoCursor<Document> cursor1 = findIterable.iterator();


        while (cursor1.hasNext()) {
            Document ID = cursor1.next();
            String CustomerID =(String)ID.get("CustomerID");
            arrayList.add(CustomerID);
        }

        return  arrayList;
    }
    //to 轉譯
    public OfferInfoJavabean getPushInfo(String Name) {

        OfferInfoJavabean tmp =null;

        try {
            MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);

            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = mongoDatabase.getCollection("PushInfo");



            Gson gson = new Gson();
            BasicDBObject order_id = new BasicDBObject();
            order_id.put("_id","$_id");
            order_id.put("PushInfo","$PushInfo");
            order_id.put("date","$date");
            BasicDBObject match =  new BasicDBObject("Name", Name) ;
            //	FindIterable<Document> findIterable = collection.find();
            //	MongoCursor<Document> mongoCursor = findIterable.iterator();
            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    Aggregates.match(match),
                    Aggregates.lookup("Menu", "Name", "Name", "Info"),
                    Aggregates.unwind("$Info"),
                    Aggregates.group(order_id,push("MyMenu","$Info"))
            ));
            System.out.println("123");
            MongoCursor<Document> cursor = output.iterator();
            while (cursor.hasNext()) {
//
//                JSONObject jsonObect = new JSONObject(cursor.next().toJson());
//                jsonArray.put(jsonObect);
//                System.out.println(cursor.next().toJson(writerSettings));
                Document document = cursor.next();

                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(document.toJson());

                tmp = gson.fromJson(mJson.getAsJsonObject(), OfferInfoJavabean.class);

            }



//            db.PushInfo.aggregate([
////                    {$lookup:{
////                        from:"ManageMenu",
////                        localField:"Name",
////                        foreignField:"Name",
////                        as:"info"
////            }
////            },
////            {$unwind : '$info'}
////,
////            {$group:{
////                "_id":{
////                    "_id":"$_id",
////                            "PushInfo":"$PushInfo",
////                            "date":"$date",
////                            "Name":"$Name",
////                            "Price":"$Price"
////                },
////                "MyMenu":{"$push":"$info"}
////            }
////            }
////])


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return tmp;
    }


}
