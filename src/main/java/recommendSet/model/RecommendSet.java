package recommendSet.model;

import Menu.model.javabean.Set;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class RecommendSet {

    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> menuColl, orderColl;
    private String id;
    private Set setJavabean;
    private ArrayList<String> mealList = new ArrayList<>();
    private ArrayList<Document> setMealList = new ArrayList<>();

    public RecommendSet(String s, MongoDatabase mongoDatabase) {
        id = s;
        this.mongoDatabase = mongoDatabase;
        connectDb();
    }

    public Set getSetJavabean() {
        return setJavabean;
    }

    private void connectDb() {

        try {
            menuColl = mongoDatabase.getCollection("Menu");
            orderColl = mongoDatabase.getCollection("Order");

            getCustomerOrder();
            getRecommendSet();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void getCustomerOrder() {
        BasicDBObject query = new BasicDBObject();
        query.put("CustomerID", id);
        FindIterable<Document> findIterable = orderColl.find(query);
        MongoCursor<Document> cursor1 = findIterable.iterator();
        JSONArray j;
        boolean ordered = false;

        while (cursor1.hasNext()) {     // 遍歷該位顧客所有已完成訂單
            JSONObject jsonObject = new JSONObject(cursor1.next().toJson());
            j = new JSONArray(jsonObject.get("MyMenu").toString());

            for (int i=0;i<j.length();i++) {    // 遍歷該筆訂單的所有餐點
                String setName = j.getJSONObject(i).get("Name").toString();

                for (String s:mealList) {
                    if (setName.equals(s)) {    // 檢查是否點過該項餐點
                        ordered = true;         // 有點過就結束迴圈
                        break;
                    }
                }

                if (!ordered)                   // 沒點過就加進List裡
                    mealList.add(setName);
            }
        }
    }

    private void getRecommendSet() {
        BasicDBObject query = new BasicDBObject();
        query.put("ClassName", "套餐");
        FindIterable<Document> findIterable = menuColl.find(query);
        MongoCursor<Document> cursor1 = findIterable.iterator();
        ArrayList<String> setMeal = new ArrayList<>();

        while (cursor1.hasNext()) {
            Document d = cursor1.next();
            setMealList.add(d);
            JSONObject jsonObject = new JSONObject(d.toJson());
            setMeal.add(jsonObject.get("Name").toString());
        }

        Random ran = new Random();
        int recommendSetIndex = 0;
        int count = setMeal.size();     // 控制亂數範圍，初始值為setMeal的size
        boolean notFound = true;        // 控制迴圈是否繼續

        while (notFound && count > 0) {     // 若所有餐點皆點過也結束迴圈
            notFound = false;
            recommendSetIndex = ran.nextInt(count);

            for (String s:mealList) {
                if (setMeal.get(recommendSetIndex).equals(s)) {     // 若有點過該項餐點，將該餐點與未交換過的最後項交換
                    notFound = true;    // 讓迴圈繼續執行

                    // setMeal swap
                    String temp = setMeal.get(recommendSetIndex);
                    setMeal.set(recommendSetIndex, setMeal.get(count - 1));
                    setMeal.set(count - 1, temp);

                    // setMealList swap
                    Document t = setMealList.get(recommendSetIndex);
                    setMealList.set(recommendSetIndex, setMealList.get(count - 1));
                    setMealList.set(count - 1, t);

                    count--;    // 亂數範圍-1，也控制下次交換項
                }
            }
        }

        Gson gson = new Gson();
        setJavabean = gson.fromJson(setMealList.get(recommendSetIndex).toJson(), Set.class);
    }
}