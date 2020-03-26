package recommendSet.model;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppearTimesCounter {

    private MongoDatabase mongoDatabase;
    private ArrayList<String> mealList;
    private ArrayList<Integer> appearTimes;

    public AppearTimesCounter(MongoDatabase mdb) {
        mongoDatabase = mdb;
        mealList = new ArrayList<>();
        appearTimes = new ArrayList<>();
        analysis();
    }

    private void analysis() {
        try {
            // 抓所有餐點放到mealList
            MongoCollection<Document> menuColl = mongoDatabase.getCollection("Menu");
            FindIterable<Document> findIterable = menuColl.find();
            MongoCursor<Document> cursor = findIterable.iterator();
            while (cursor.hasNext()) {
                JSONObject jsonObject = new JSONObject(cursor.next().toJson());
                mealList.add(jsonObject.get("Name").toString());
                appearTimes.add(0);
            }

            // 統計各餐點出現次數
            MongoCollection<Document> orderColl = mongoDatabase.getCollection("Order");
            BasicDBObject query = new BasicDBObject();
            query.put("Status", "已結帳");
            findIterable = orderColl.find(query);
            cursor = findIterable.iterator();
            String name;    // 暫存MyMenu裡的餐點名稱

            while (cursor.hasNext()) {      // 遍歷所有已結帳訂單
                JSONObject jsonObject = new JSONObject(cursor.next().toJson());
                JSONArray ja = jsonObject.getJSONArray("MyMenu");
                int index;

                for (int i=0;i<ja.length();i++) {       // 遍歷各筆訂單的餐點
                    name = ja.getJSONObject(i).get("Name").toString();
                    index = mealList.indexOf(name); // 出現過的餐點在List的位置
                    appearTimes.set(index, appearTimes.get(index)+1);
                }
            }

            // 存入MealsStatistics
            MongoCollection msColl = mongoDatabase.getCollection("MealsStatistics");
            for (int i=0;i<mealList.size();i++) {
                Document updateQuery = new Document();
                updateQuery.append("Name", mealList.get(i));

                Document docToInsert  = new Document();
                docToInsert.append("AppearTimes", appearTimes.get(i));

                Document update = new Document();
                update.append("$set", docToInsert);

                //To update single Document
                msColl.updateOne(updateQuery, update);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
