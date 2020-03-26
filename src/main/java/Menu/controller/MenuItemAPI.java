package Menu.controller;

import Menu.model.javaclass.ManageMenu;
import Menu.model.javaclass.SingleCategory;
import Menu.model.javaclass.OneSet;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.SingleFoodRequest;
import translate.model.tools.Generator;

@WebServlet(name = "MenuItemAPI", urlPatterns = {"/MenuItemAPI","/FoodServlet"})
@MultipartConfig
public class MenuItemAPI extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "upload";
    private Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 設定回傳值
        response.setContentType("application/json");

        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");
        String json = null;
        PrintWriter out = response.getWriter();
        switch (request.getServletPath()) {
            case "/MenuItemAPI": {
                String action = request.getParameter("action");

                ManageMenu menu = new ManageMenu(mongoDatabase, db);
                ResponseStatus responseStatus = new ResponseStatus();
                boolean bool = true;

                switch (action) {
                    case "addItem": {


                        String name = request.getParameter("Name");
                        String className = request.getParameter("ClassName");
                        String price = request.getParameter("Price");
                        Part filePart = request.getPart("file");
                        String filePath =
                            getServletContext().getRealPath("./") + File.separator
                                + UPLOAD_DIRECTORY;

                        if(menu.detection(name)){
                            responseStatus.setkey(true);
                        }else {
                            responseStatus.setkey(false);
                            menu.addMenu(className, name, price, filePart, filePath);

                        }


                        break;
                    }
                    case "deleteItem": {
                        String name = request.getParameter("Name");
                        menu.deleteMenu(name);
                        break;
                    }
                    case "updateMenu": {

                        String oldName = request.getParameter("OldName");
                        String name = request.getParameter("Name");
                        String className = request.getParameter("ClassName");
                        String price = request.getParameter("Price");
                        Part filePart = request.getPart("file");
                        String filePath =
                            getServletContext().getRealPath("./") + File.separator
                                + UPLOAD_DIRECTORY;
                        menu.update(className, name, oldName, price, filePart, filePath);
                        break;
                    }
                    case "addSet": {
                        String name = request.getParameter("Name");
                        String price = request.getParameter("Price");
                        int itemNum = Integer.parseInt(request.getParameter("itemNum"));
                        ArrayList<String> itemArray = new ArrayList<>();
                        for (int i = 0; i <= itemNum; i++)
                            itemArray.add(request.getParameter("Item" + "[" + i + "]"));
                        Part filePart = request.getPart("File");
                        String filePath =
                            getServletContext().getRealPath("./") + File.separator
                                + UPLOAD_DIRECTORY;

                        if(menu.detection(name)){
                            responseStatus.setkey(true);
                        }else {
                            responseStatus.setkey(false);
                            menu.addSet(name, price, itemArray, filePart, filePath);

                        }

                        break;
                    }
                    case "oneSet": {
                        String s = request.getParameter("_id");
                        OneSet oneSet = new OneSet(s);
                        out.print(oneSet.getJson());
                        bool = false;
                        break;
                    }
                    case "singleCategory": {
                        String className = request.getParameter("ClassName");
                        SingleCategory singleCategory = new SingleCategory(className);
                        out.print(singleCategory.getJson());
                        bool = false;
                        break;
                    }
                    case "search": {
                        String Name = request.getParameter("Name");
                        out.print(menu.getMealInfo(Name));
                        break;
                    }
                    case "SoldOut": {
                        String soldName = request.getParameter("Name");
                        menu.soldOut(soldName);
                        break;
                    }
                }

                if (bool) {

                    Date date = new Date();
                    responseStatus.setDate(date.toString());
                    responseStatus.setStatus(HttpServletResponse.SC_OK);
                    json = gson.toJson(responseStatus);
                    out.print(json);
                }
                break;
            }
            case "/FoodServlet":{
                SingleFoodRequest singleFoodRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), SingleFoodRequest.class);
                String chatId = singleFoodRequest.getUserData().getChatId();
                String className = singleFoodRequest.getQuery().getClassName();

                SingleCategory singleCategory = new SingleCategory(className);

                Translate2NICP translate2NICP = new Translate2NICP();
                json = gson.toJson(translate2NICP.getFoodJson(chatId,singleCategory.getJavabean()));
                //json = gson.toJson(Generator.getFoodJson(chatId,singleCategory.getJavabean()));
                out.print(json);
                break;
            }

        }
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");
        ManageMenu menu = new ManageMenu(mongoDatabase, db);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(menu.getMenuJSONArray());
        response.getWriter().flush();
    }
}
