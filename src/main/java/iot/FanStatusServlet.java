package iot;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tool.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FanStatusServlet", urlPatterns = { "/1fFanStatus", "/2fFanStatus"})
public class FanStatusServlet extends HttpServlet {

    private FanStatusJavabean jb1f, jb2f;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        if (request.getServletPath().equals("/1fFanStatus"))
            jb1f = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),FanStatusJavabean.class);
        else
            jb2f = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),FanStatusJavabean.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        if (request.getServletPath().equals("/1fFanStatus"))
            response.getWriter().print(gson.toJson(jb1f));
        else
            response.getWriter().print(gson.toJson(jb2f));
        response.getWriter().flush();
    }
}
