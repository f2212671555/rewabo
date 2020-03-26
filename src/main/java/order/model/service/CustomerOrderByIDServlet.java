package order.model.service;

import order.model.java.MyOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CustomerOrderByIDServlet", urlPatterns = { "/CustomerOrderByIDServlet" })
public class CustomerOrderByIDServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        String Status = request.getParameter("Status");
        ////////////////////////////////////////// 上面這裡要填老闆端給的參數 決定要拿的菜單狀態 或是 拿到客人ID
        //String MyStatus ="已完成";
        MyOrder myOrder = new MyOrder();

        //JSONArray jsonArray = connection.getJson();


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(myOrder.getCustomerOrderByID(oid,Status));
        response.getWriter().flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }
}
