package order.controller.service;

import order.model.java.MyOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/OrderStatusServlet")
public class OrderStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String oid = request.getParameter("oid");
        String CustomerID = request.getParameter("CustomerID");

        MyOrder order = new MyOrder();

        if(CustomerID.equals("")){

            order.getOrderByoid(oid);

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(order.getOrderByoid(oid));
            response.getWriter().flush();
        }else if(oid.equals("")){



            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(order.getCustomerStatus(CustomerID));
            response.getWriter().flush();

        }




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
