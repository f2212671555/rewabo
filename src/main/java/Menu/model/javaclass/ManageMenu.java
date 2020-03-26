package Menu.model.javaclass;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

/* 餐點的新增修改查詢刪除 */

public class ManageMenu {

    private MongoDatabase mongoDatabase;
    private DB db;

    public ManageMenu(MongoDatabase mongoDatabase, DB db){
        this.mongoDatabase = mongoDatabase;
        this.db = db;
    }

    private void init(String filePath) {
        // init
        int maxFileSize = 250 * 1024;
        int maxMemSize = 4 * 1024;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);
        upload.setHeaderEncoding("utf-8");

        File uploadDir = new File(filePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    public void addMenu(String className, String name , String price, Part filePart, String filePath) {

        init(filePath);

        String filenameExtension = FilenameUtils.getExtension(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

        try {
            // 把圖片新增到server
            try(
                    InputStream in = filePart.getInputStream();
                    OutputStream output = new FileOutputStream(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +name+"."+filenameExtension)) {
                byte[] buffer = new byte[1024];
                int length = -1;
                while ((length = in.read(buffer)) != -1) {
                    output.write(buffer,0, length);
                }
            } catch(IOException ex) {
                throw new UncheckedIOException(ex);
            }

            // 把json寫進db
            DBCollection collection = db.getCollection("Menu");
            BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
                    .add("ClassName", className)
                    .add("Name", name)
                    .add("Price", price)
                    .add("Sold-out" ,true)
                    .add("url", "https://ai-rest.cse.ntou.edu.tw/" + name + "." + filenameExtension);
            collection.insert(documentBuilder.get());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void addSet(String name , String price, ArrayList<String> itemArray, Part filePart, String filePath) {

        init(filePath);

        String filenameExtension = FilenameUtils.getExtension(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

        try {
            // 把圖片新增到server
            try(
                    InputStream in = filePart.getInputStream();
                    OutputStream output = new FileOutputStream(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +name+"."+filenameExtension)) {
                byte[] buffer = new byte[1024];
                int length = -1;
                while ((length = in.read(buffer)) != -1) {
                    output.write(buffer,0, length);
                }
            } catch(IOException ex) {
                throw new UncheckedIOException(ex);
            }
            // 把json寫進db
            DBCollection collection  = db.getCollection("Menu");
            BasicDBObject document = new BasicDBObject();
            document.put("ClassName", "套餐");
            document.put("Name", name);
            document.put("Price", price);
            document.put("Item",itemArray);
            document.put("Sold-out" ,true);
            document.put("url", "https://ai-rest.cse.ntou.edu.tw/" + name + "." + filenameExtension);

            collection.insert(document);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void deleteMenu(String name) {

        DBCollection collection  = db.getCollection("Menu");

        try {
            // 刪除server的圖片
            File deleteFile = new File(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +name+".jpg" );
            deleteFile.delete();

            //刪除db的json
            BasicDBObject document = new BasicDBObject();
            document.put("Name", name);
            collection.remove(document);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void update(String ClassName, String Name, String OldName, String Price, Part filePart, String filePath) {

        // init
        int maxFileSize = 250 * 1024;
        int maxMemSize = 4 * 1024;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);
        upload.setHeaderEncoding("utf-8");

        MongoCollection<Document> collection = mongoDatabase.getCollection("Menu");

        BasicDBObject document = new BasicDBObject();
        document.put("Name", OldName);      //比對要修改的餐點
        FindIterable<Document> findIterable = collection.find(document);
        MongoCursor<Document> cursor = findIterable.iterator();
        Document oMenu = cursor.next();

        if (Name.equals("")) {
            Name = OldName;
        }
        if (Price.equals("")) {
            Price = (String) oMenu.get("Price");
        }

        File uploadDir = new File(filePath);
        Document query = new Document();
        query.append("Name", OldName);

        if (filePart.getSubmittedFileName() == null) {

            Document setData = new Document();
            setData.append("Name",Name);
            setData.append("ClassName", ClassName);
            setData.append("Price", Price);
            setData.append("url", "https://ai-rest.cse.ntou.edu.tw/" + Name + ".jpg");
            Document update = new Document();
            update.append("$set", setData);
            collection.updateMany(query, update);

            File oldFile = new File(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +OldName+".jpg");
            File newFile = new File(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +Name+".jpg");
            oldFile.renameTo(newFile);

        } else {

            File deleteFile = new File(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +Name+".jpg" );
            deleteFile.delete();
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filenameExtension = FilenameUtils.getExtension(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

            try(
                    InputStream in = filePart.getInputStream();
                    OutputStream output = new FileOutputStream(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +Name+"."+filenameExtension)) {
                byte[] buffer = new byte[1024];
                int length = -1;
                while ((length = in.read(buffer)) != -1) {
                    output.write(buffer,0, length);
                }
            } catch(IOException ex) {
                throw new UncheckedIOException(ex);
            }

            Document setData = new Document();
            setData.append("Name",Name);
            setData.append("ClassName", ClassName);
            setData.append("Price", Price);
            setData.append("url", "https://ai-rest.cse.ntou.edu.tw/" + Name + ".jpg");
            Document update = new Document();
            update.append("$set", setData);
            collection.updateMany(query, update);
        }
    }

    public JSONArray getMenuJSONArray() {

        JSONArray jsonArray = new JSONArray();
        try {

            MongoCollection<Document> collection = mongoDatabase.getCollection("Menu");
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> cursor1 = findIterable.iterator();

            while (cursor1.hasNext()) {
                JSONObject jsonObect = new JSONObject(cursor1.next().toJson());
                jsonArray.put(jsonObect);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray getMealInfo(String name){
        JSONArray jsonArray = new JSONArray();
        try {

            MongoCollection<Document> collection = mongoDatabase.getCollection("Menu");
            BasicDBObject query = new BasicDBObject();
            query.append("Name",name);
            FindIterable<Document> findIterable = collection.find(query);
            MongoCursor<Document> cursor1 = findIterable.iterator();

            while (cursor1.hasNext()) {
                JSONObject jsonObect = new JSONObject(cursor1.next().toJson());
                jsonArray.put(jsonObect);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return jsonArray;


    }

    public void soldOut(String name){
        MongoCollection<Document> collection = mongoDatabase.getCollection("Menu");
        BasicDBObject query = new BasicDBObject();
        query.append("Name",name);
        FindIterable<Document> findIterable = collection.find(query);
        MongoCursor<Document> cursor = findIterable.iterator();
        Document soldOutCursor = cursor.next();
        boolean soldOut = (boolean) soldOutCursor.get("SoldOut");
        Document setData = new Document();
        Document update = new Document();
        if(soldOut) {
            setData.append("SoldOut", false);
        }else{
            setData.append("SoldOut", true);
        }
        update.append("$set", setData);
        collection.updateMany(query, update);
    }

    public boolean detection(String name) {
        DBCollection collection = db.getCollection("Menu");
        BasicDBObject query = new BasicDBObject();
        query.put("Name", name);
        int number = collection.find(query).count();
        if (number >= 1) {
            return true;
        } else {
            return false;
        }
    }
}