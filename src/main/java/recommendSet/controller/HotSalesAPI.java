package recommendSet.controller;

import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import recommendSet.model.AppearTimesCounter;
import recommendSet.model.FrequentPattern;
import recommendSet.model.MSCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HotSalesAPI", urlPatterns = "/HotSales")
@MultipartConfig
public class HotSalesAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        MongoDatabase mongoDatabase = (MongoDatabase)getServletContext().getAttribute("mongoDatabase");
        DB db = (DB)request.getServletContext().getAttribute("db");
        String s = request.getParameter("action");
        MSCreator mSCreator = new MSCreator(mongoDatabase, db);

        switch (s) {
            case "update":
                mSCreator.create();
                AppearTimesCounter appearTimesCounter = new AppearTimesCounter(mongoDatabase);
                break;
            case "loading":
                response.getWriter().print(mSCreator.getAppearTimes());
                break;
            case "probabilitySet":
                FrequentPattern fp = new FrequentPattern(mongoDatabase);
                response.getWriter().print(fp.getResult());
                break;
        }

        response.getWriter().flush();
    }
}
