package order.model.service;

import order.model.java.MyOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "CustomerStatusByID", urlPatterns = { "/CustomerStatusByID" })
public class CustomerStatusByID extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");

        MyOrder order = new MyOrder();
        order.getOrderByoid(oid);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(order.getOrderByoid(oid));
        response.getWriter().flush();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }
}
