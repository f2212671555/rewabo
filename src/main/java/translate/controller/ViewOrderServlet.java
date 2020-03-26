package translate.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pojo.JsonPath;
import tool.HttpCommonAction;
import translate.model.java.Translate2NICP;
import translate.model.javabean.nicp.request.CommonRequest;

@WebServlet("/ViewOrderServlet")
public class ViewOrderServlet extends HttpServlet {

    private Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        CommonRequest commonRequest = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), CommonRequest.class);
        String chatId = commonRequest.getUserData().getChatId();

        Translate2NICP translate2NICP = new Translate2NICP();
        JsonObject jsonObject =  translate2NICP.getFixedJson(chatId, JsonPath.viewOrderJson);
        String json = gson.toJson(jsonObject);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
