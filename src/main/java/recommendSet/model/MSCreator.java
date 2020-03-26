package recommendSet.model;

import Menu.model.javaclass.SingleCategory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MSCreator {

    private MongoDatabase mongoDatabase;
    private DB dataBase;

    public MSCreator(MongoDatabase mdb, DB db) {
        mongoDatabase = mdb;
        dataBase = db;
    }

    public void create() {
        MongoCollection<Document> menuClassColl = mongoDatabase.getCollection("MenuClass");
        FindIterable<Document> findIterable = menuClassColl.find();
        MongoCursor<Document> cursor = findIterable.iterator();
        SingleCategory sc;      // 可以回傳一個class內的所有餐點
        String type;        // class的type
        JSONArray jsonArray;    // 用來接所有餐點
        while (cursor.hasNext()) {      // 遍歷所有class
            Document document = cursor.next();
            JSONObject jsonObject = new JSONObject(document.toJson());
            String className = jsonObject.get("ClassName").toString();
            /*if (className.equals("套餐")) {   // 跳過套餐
                jsonObject = null;
                continue;
            }*/

            sc = new SingleCategory(className);    // 把classname丟進去sc
            type = jsonObject.get("Type").toString();   // 拿到type
            jsonArray = sc.getJson();   // 拿到該class內的所有餐點

            store(type, jsonArray);    // 將餐點和type丟給store

            sc = null;
            jsonObject = null;
            jsonArray = null;
        }
    }

    // 存進db
    private void store(String type, JSONArray array) {
        DBCollection collection = dataBase.getCollection("MealsStatistics");

        for (int i=0;i<array.length();i++) {    // 遍歷傳來的class內所有的餐點
            JSONObject jsObj = array.getJSONObject(i);
            String name = jsObj.getString("Name");
            BasicDBObject query = new BasicDBObject();
            query.put("Name", name);
            int count = collection.find(query).count();     // 計算該餐點出現在資料庫的次數

            if (count < 1) {    // 沒出現過代表為新的餐點，才會新增進資料庫，否則跳過
                BasicDBObject obj = new BasicDBObject();
                obj.put("Type", type);
                obj.put("Name", name);   // name
                obj.put("AppearTimes", 0);  // appear times
                collection.insert(obj);
            }
        }
    }

    public JSONArray getAppearTimes() {
        JSONArray jsonArray = new JSONArray();
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("MealsStatistics");
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> cursor = findIterable.iterator();

            while (cursor.hasNext()) {
                JSONObject jsonObject = new JSONObject(cursor.next().toJson());
                jsonArray.put(jsonObject);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return jsonArray;
    }
}