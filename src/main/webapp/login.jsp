<%@ page import="com.mongodb.MongoClient" %>
<%@ page import="static pojo.QkConn.dbHost" %>
<%@ page import="static pojo.QkConn.mongoDBPort" %>
<%@ page import="static pojo.QkConn.*" %>
<%@ page import="com.mongodb.client.MongoCollection" %>
<%@ page import="com.mongodb.client.MongoDatabase" %>
<%@ page import="org.bson.Document" %>
<%@ page import="com.mongodb.client.FindIterable" %>
<%@ page import="com.mongodb.client.MongoCursor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <% //把account 的值傳過來了
            MongoClient mongoClient = new MongoClient(dbHost, mongoDBPort);
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection collection  = mongoDatabase.getCollection("Login");

            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");


            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> cursorlogin = findIterable.iterator();
            Document login = cursorlogin.next();//找到日期一樣的那個doc

            String username = request.getParameter("username");

            String password = request.getParameter("password");

            String dbusername = (String) login.get("username");//get("Account")的value

            String dbpassword = (String) login.get("password");//get("Password")的value




                if (dbusername.equals(username) && dbpassword.equals(password)) {
                    request.getRequestDispatcher("/indexTemp.jsp").forward(request, response);
                    HttpSession session1=request.getSession();
                    session1.setAttribute("name",username);
                }else if (username==null&&password==null){

                    request.setAttribute("error_message", "請先登入");

                }else{
                    request.setAttribute("error_message", "帳號或密碼錯誤，請重新輸入");

                }



        %>
        <title>歡迎來到REWABO</title>
        <meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- jQuery Files -->
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


        <!-- CSS File -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <style>
            html,
            body {
                position: relative;
                height: 100%;
            }

            .login-container {
                position: relative;
                width: 300px;
                margin: 80px auto;
                padding: 20px 40px 40px;
                text-align: center;
                background: #fff;
                border: 1px solid #ccc;
            }

            #output {
                position: absolute;
                width: 300px;
                top: -75px;
                left: 0;
                color: #fff;
            }

            #output.alert-success {
                background: rgb(25, 204, 25);
            }

            #output.alert-danger {
                background: rgb(228, 105, 105);
            }

            .login-container::before,
            .login-container::after {
                content: "";
                position: absolute;
                width: 100%;
                height: 100%;
                top: 3.5px;
                left: 0;
                background: #fff;
                z-index: -1;
                -webkit-transform: rotateZ(4deg);
                -moz-transform: rotateZ(4deg);
                -ms-transform: rotateZ(4deg);
                border: 1px solid #ccc;
            }

            .login-container::after {
                top: 5px;
                z-index: -2;
                -webkit-transform: rotateZ(-2deg);
                -moz-transform: rotateZ(-2deg);
                -ms-transform: rotateZ(-2deg);
            }

            .avatar {
                width: 100px;
                height: 100px;
                margin: 10px auto 30px;
                border-radius: 100%;
                border: 2px solid #aaa;
                background-size: cover;
                background-image: url('assets/image/login.png');
            }

            .form-box input {
                width: 100%;
                padding: 10px;
                text-align: center;
                height: 40px;
                border: 1px solid #ccc;
                ;
                background: #fafafa;
                transition: 0.2s ease-in-out;
            }

            .form-box input:focus {
                outline: 0;
                background: #eee;
            }

            .form-box input[type="text"] {
                border-radius: 5px 5px 0 0;
                text-transform: lowercase;
            }

            .form-box input[type="password"] {
                border-radius: 0 0 5px 5px;
                border-top: 0;
            }

            .form-box button.login {
                margin-top: 15px;
                padding: 10px 20px;
            }

            .animated {
                -webkit-animation-duration: 1s;
                animation-duration: 1s;
                -webkit-animation-fill-mode: both;
                animation-fill-mode: both;
            }

            @-webkit-keyframes fadeInUp {
                0% {
                    opacity: 0;
                    -webkit-transform: translateY(20px);
                    transform: translateY(20px);
                }
                100% {
                    opacity: 1;
                    -webkit-transform: translateY(0);
                    transform: translateY(0);
                }
            }

            @keyframes fadeInUp {
                0% {
                    opacity: 0;
                    -webkit-transform: translateY(20px);
                    -ms-transform: translateY(20px);
                    transform: translateY(20px);
                }
                100% {
                    opacity: 1;
                    -webkit-transform: translateY(0);
                    -ms-transform: translateY(0);
                    transform: translateY(0);
                }
            }

            .fadeInUp {
                -webkit-animation-name: fadeInUp;
                animation-name: fadeInUp;
            }
        </style>
        <script>

//                $(document).ready(function () {
//                    console.log("我進來了");
//
//                });


        </script>
    </head>

    <body>

        <div id="mongoDbServlet" class="container">
            <div class="login-container">
                <hr />
                <div id="output"></div>
                <div class="avatar"></div>
                <!------ MONGODB JSP & SERVLET EXAMPLE ------>
                <div id="login_form" class="form-box">
                    <h4>REWABO</h4>
                    <form id="user_login_form" name="loginForm" method="post" action="login.jsp">
                        <!----- LOGIN FORM ------>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" class="form-control" id="username" placeholder="帳號" name="username">
                        </div>
                        <div>&nbsp;</div>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input type="password" class="form-control" id="password" placeholder="密碼" name="password">
                        </div>

                        <!----- SUBMIT BUTTON ------>
                        <div>&nbsp;</div>
                        <button id="submit_btn" type="submit" class="btn btn-info">登入</button>
                    </form>
                </div>

                <h4 id="errMsg" class="text-danger" align="center">${error_message}</h4>
            </div>
        </div>
    </body>

    </html>