package Menu.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import order.model.javabean.ResponseStatus;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import tool.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pojo.QkConn.*;
import static pojo.QkConn.dbName;

@WebServlet(name = "/SetMenuOperationServlet", urlPatterns = { "/SetMenuOperationServlet" })
@MultipartConfig
public class SetMenuOperationServlet extends HttpServlet {
    private String filePath;
    private int maxFileSize = 250 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;
    private static final String UPLOAD_DIRECTORY = "upload";

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        DB db = mongoClient.getDB(dbName);
        DBCollection collection  = db.getCollection("Menu");

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        ServletFileUpload upload = new ServletFileUpload(factory);

        upload.setSizeMax(maxFileSize);
        upload.setHeaderEncoding("utf-8");

        filePath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(filePath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {

            String Price = request.getParameter("Price");
            String Name = request.getParameter("Name"); // Retrieves <input type="text" name="description">
            int itemNum = Integer.parseInt(request.getParameter("itemNum"));
            ArrayList<String> ItemArray = new ArrayList<>();
            for(int i = 0 ;i<=itemNum;i++)
                ItemArray.add(request.getParameter("Item"+"["+i+"]"));

            Part filePart = request.getPart("File"); // Retrieves <input type="file" name="file">
            //String fileName2 = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            String second = FilenameUtils.getExtension(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

            try(
                    InputStream in = filePart.getInputStream();
                    OutputStream output = new FileOutputStream(File.separator+"opt"+File.separator+"tomcat"+File.separator+"webapps"+File.separator+"ROOT"+File.separator +Name+"."+second)) {
                byte[] buffer = new byte[1024];
                int length = -1;
                while ((length = in.read(buffer)) != -1) {
                    output.write(buffer,0, length);
                }
            } catch(IOException ex) {
                throw new UncheckedIOException(ex);
            }

            BasicDBObject document = new BasicDBObject();
            document.put("ClassName", "套餐");
            document.put("Name", Name);
            document.put("Price", Price);
            document.put("Item",ItemArray);
            document.put("url", "https://ai-rest.cse.ntou.edu.tw/" + Name + "." + second);

            collection.insert(document);
        } catch (Exception ex) {
            System.out.println(ex);

        }

        ResponseStatus responseStatus = new ResponseStatus();
        Date date =new Date();
        responseStatus.setDate(date.toString());
        responseStatus.setStatus(HttpServletResponse.SC_OK);



        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setCharacterEncoding("UTF-8");
        System.out.println("");
        response.setContentType("application/json");
        System.out.println("");
        response.getWriter().print(gson.toJson(responseStatus));
        response.getWriter().flush();




    }




}
