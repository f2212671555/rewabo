package Menu.model.javaclass;

import Menu.model.javabean.MenuClass;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static pojo.QkConn.*;

public class SingleCategory {
    private JSONArray singleCategoryJson;
    private MongoCollection<Document> menuColl;
    private List<MenuClass> menuClassJavabeanList;

    public SingleCategory(String className) {
        menuClassJavabeanList = new ArrayList<>();
        connectDB(className);
    }

    public JSONArray getJson() {
        return singleCategoryJson;
    }

    public List<MenuClass> getJavabean() {
        return menuClassJavabeanList;
    }

    private void connectDB(String className) {

        String menuName = "Menu";

        try {
            MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            menuColl = mongoDatabase.getCollection(menuName);
            getMealsOfOneKind(className);
            mongoClient = null;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void getMealsOfOneKind(String className) {
        Gson gson = new Gson();
        FindIterable<Document> findIterable = menuColl.find();
        MongoCursor<Document> cursor = findIterable.iterator();
        JSONArray jsonArray = new JSONArray();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            JSONObject jsonObject = new JSONObject(document.toJson());
            MenuClass menuClassJavabean = gson.fromJson(document.toJson(), MenuClass.class);
            if (jsonObject.get("ClassName").equals(className)) {
                menuClassJavabeanList.add(menuClassJavabean);
                jsonArray.put(jsonObject);
            }

            jsonObject = null;
        }
        singleCategoryJson = jsonArray;
        jsonArray = null;
    }
}
