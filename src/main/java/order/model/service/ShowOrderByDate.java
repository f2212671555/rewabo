package order.model.service;


import order.model.java.MyOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ShowOrderByDate", urlPatterns = { "/ShowOrderByDate" })
public class ShowOrderByDate extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String CustomerID = request.getParameter("CustomerID");
        String FromDate = request.getParameter("FromDate");
        String ToDate = request.getParameter("ToDate");

        MyOrder myOrder =new MyOrder();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(myOrder.getOrderByDate(CustomerID,FromDate,ToDate));
        response.getWriter().flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }
}
