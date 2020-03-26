package Menu.model.javaclass;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import static pojo.QkConn.*;

public class OneSet {
    private JSONArray setMenuJson;
    private MongoCollection<Document> menuColl;

    public OneSet(String s) {
        connectDb(s);
    }

    public JSONArray getJson() {
        return setMenuJson;
    }

    private void connectDb(String s) {

        String menuName = "Menu";

        try {
            MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            menuColl = mongoDatabase.getCollection(menuName);

            getFoodKind(s);

            mongoClient = null;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void getFoodKind(String s) {
        FindIterable<Document> findIterable;
        MongoCursor<Document> cursor;
        JSONArray jsonArray = new JSONArray();

        findIterable = menuColl.find();
        cursor = findIterable.iterator();

        while (cursor.hasNext()) {
            JSONObject jsonObject = new JSONObject(cursor.next().toJson());

            if (jsonObject.getJSONObject("_id").getString("$oid").equals(s))
                jsonArray.put(jsonObject);

            jsonObject = null;
        }

        setMenuJson = jsonArray;
        jsonArray = null;
    }
}
