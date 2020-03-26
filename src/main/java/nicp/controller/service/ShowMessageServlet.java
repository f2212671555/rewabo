package nicp.controller.service;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pojo.QkConn.*;

@WebServlet(name = "/ShowMessageServlet", urlPatterns = { "/ShowMessageServlet" })
public class ShowMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = mongoDatabase.getCollection("Message");


        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> cursor = findIterable.iterator();

        JSONArray jsonArray = new JSONArray();

        while (cursor.hasNext()) {
            JSONObject jsonObject = new JSONObject(cursor.next().toJson());
            jsonArray.put(jsonObject);
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);
        response.getWriter().flush();

    }



}
