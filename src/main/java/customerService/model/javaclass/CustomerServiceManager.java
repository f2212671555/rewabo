package customerService.model.javaclass;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import customerService.model.javabean.rewabo.CustomerService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerServiceManager {


    private MongoDatabase mongoDatabase;
    private DB db;
    private Gson gson = new Gson();
    public CustomerServiceManager(MongoDatabase mongoDatabase, DB db){
        this.mongoDatabase = mongoDatabase;
        this.db = db;
    }
    public JSONArray showCustomerService() {

        JSONArray jsonArray = new JSONArray();
        try {


            MongoCollection<Document> collection = mongoDatabase.getCollection("button");

            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> cursor1 = findIterable.iterator();

            while (cursor1.hasNext()) {
                JSONObject jsonObject = new JSONObject(cursor1.next().toJson());
                jsonArray.put(jsonObject);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return jsonArray;

    }

    public ArrayList<CustomerService> getCustomerService() {

        ArrayList<CustomerService> customerServices  = new  ArrayList<>();
        CustomerService tmp ;
        try {


            MongoCollection<Document> collection = mongoDatabase.getCollection("button");

            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> cursor1 = findIterable.iterator();

            while (cursor1.hasNext()) {
                Document document = cursor1.next();

                JsonParser parser = new JsonParser();
                JsonElement mJson =  parser.parse(document.toJson());

                tmp = gson.fromJson(mJson.getAsJsonObject(), CustomerService.class);
                customerServices.add(tmp);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return customerServices;

    }

    public void addCustomerService(String label,String value,String Intent_Id){
        DBCollection collection = db.getCollection("button");
        BasicDBObject document = new BasicDBObject();
        document.put("label", label);
        document.put("value", value);
        document.put("Intent_Id", Intent_Id);
        collection.insert(document);
    }


    public void deleteCustomerService(String IntentId) {

        DBCollection collection  = db.getCollection("button");

        try {
            //刪除db的json
            BasicDBObject document = new BasicDBObject();
            document.put("Intent_Id", IntentId);
            collection.remove(document);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }



}
