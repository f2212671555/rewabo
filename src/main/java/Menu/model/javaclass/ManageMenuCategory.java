package Menu.model.javaclass;

import Menu.model.javabean.MenuClass;
import com.google.gson.Gson;
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
import java.util.List;

/* 餐點類別的新增修改查詢刪除 */

public class ManageMenuCategory {

    private MongoDatabase mongoDatabase;
    private DB db;
    private JSONArray menuClassJsonArray;
    private List<MenuClass> menuClassJavabeanList;

    public ManageMenuCategory(MongoDatabase mongoDatabase, DB db) {
        this.mongoDatabase = mongoDatabase;
        this.db = db;
        menuClassJsonArray = new JSONArray();
        menuClassJavabeanList = new ArrayList<>();
        creatObject();
    }

    public List<MenuClass> getJavabean() {
        return menuClassJavabeanList;
    }

    public JSONArray getMenuClassJSONArray() {
        return menuClassJsonArray;
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

    public void addMenuClass(String className,String type,  String filePath, Part filePart){

        init(filePath);

        String filenameExtension = FilenameUtils.getExtension(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

        try {
            // 把圖片新增到server
            try(
                    InputStream in = filePart.getInputStream();
                    OutputStream output = new FileOutputStream(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator + className +"."+ filenameExtension)) {
                byte[] buffer = new byte[1024];
                int length = -1;
                while ((length = in.read(buffer)) != -1) {
                    output.write(buffer,0, length);
                }
            } catch(IOException ex) {
                throw new UncheckedIOException(ex);
            }
            // 把json寫進db
            DBCollection collection  = db.getCollection("MenuClass");
            BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
                    .add("ClassName", className)
                    .add("Type", type)
                    .add("url","https://ai-rest.cse.ntou.edu.tw/"+ className +"."+ filenameExtension);
            collection.insert(documentBuilder.get());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void deleteMenuClass(String ClassName){

        DBCollection collection = db.getCollection("MenuClass");

        try {
            // 刪除server的圖片
            File deleteFile = new File(File.separator + "opt" + File.separator + "tomcat" + File.separator + "webapps" + File.separator + "ROOT" + File.separator + ClassName + ".jpg");
            deleteFile.delete();

            //刪除db的json
            BasicDBObject document = new BasicDBObject();
            document.put("ClassName", ClassName);
            collection.remove(document);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void update(String ClassName, String NewClassName) {

        MongoCollection<Document> menuColl = mongoDatabase.getCollection("Menu");
        MongoCollection<Document> menuClassColl = mongoDatabase.getCollection("MenuClass");

        Document query = new Document();
        query.append("ClassName", ClassName);
        Document setData = new Document();
        setData.append("ClassName", NewClassName);
        Document update = new Document();
        update.append("$set", setData);

        //To update single Document
        menuClassColl.updateOne(query, update);
        menuColl.updateMany(query, update );
    }

    private void creatObject() {
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("MenuClass");
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> cursor1 = findIterable.iterator();
            Gson gson = new Gson();

            while (cursor1.hasNext()) {
                Document document = cursor1.next();
                MenuClass menuClassJavabean = gson.fromJson(document.toJson(), MenuClass.class);
                menuClassJavabeanList.add(menuClassJavabean);
                JSONObject jsonObject = new JSONObject(document.toJson());
                menuClassJsonArray.put(jsonObject);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }



    public boolean detection(String name) {
        DBCollection collection = db.getCollection("MenuClass");
        BasicDBObject query = new BasicDBObject();
        query.put("ClassName", name);
        int number = collection.find(query).count();
        if (number >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
