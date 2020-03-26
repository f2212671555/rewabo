package Menu.controller;

import Menu.model.javaclass.ManageMenu;
import Menu.model.javaclass.ManageMenuCategory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import java.io.PrintWriter;
import order.model.javabean.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.CommonRequest;

@WebServlet(name = "MenuCategoryAPI", urlPatterns = {"/MenuCategoryAPI","/FoodKindsServlet"})
@MultipartConfig
public class MenuCategoryAPI extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "upload";
    private Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");
        ManageMenuCategory menuCategory = new ManageMenuCategory(mongoDatabase, db);

        ResponseStatus responseStatus = new ResponseStatus();
        String json = null;
        switch (request.getServletPath()) {
            case "/MenuCategoryAPI": {
                String action = request.getParameter("action");
                switch (action) {
                    case "addClass":
                        String className = request.getParameter("ClassName");
                        String type = request.getParameter("Type");
                        String filePath =
                            getServletContext().getRealPath("./") + File.separator
                                + UPLOAD_DIRECTORY;
                        Part filePart = request.getPart("file");

                        if(menuCategory.detection(className)){
                            responseStatus.setkey(true);
                        }else {
                            responseStatus.setkey(false);
                            menuCategory.addMenuClass(className, type ,filePath, filePart);

                        }

                        break;

                    case "deleteClass":
                        className = request.getParameter("ClassName");
                        menuCategory.deleteMenuClass(className);
                        break;

                    case "updateClass":
                        className = request.getParameter("ClassName");
                        String newClassName = request.getParameter("NewClassName");
                        menuCategory.update(className, newClassName);
                        break;
                }

                // 設定回傳值

                Date date = new Date();
                responseStatus.setDate(date.toString());
                responseStatus.setStatus(HttpServletResponse.SC_OK);
                json = gson.toJson(responseStatus);
                break;
            }
            case "/FoodKindsServlet":{
                CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), CommonRequest.class);
                String chatId = commonRequest.getUserData().getChatId();
                Translate2NICP translate2NICP = new Translate2NICP();
                json = gson.toJson(translate2NICP.getFoodKindsJson(chatId,menuCategory.getJavabean()));
                break;
            }
        }
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");
        ManageMenuCategory menuCategory = new ManageMenuCategory(mongoDatabase, db);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(menuCategory.getMenuClassJSONArray());
        response.getWriter().flush();
    }
}
