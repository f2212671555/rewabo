<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <html>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="assets/image/boticon.png" rel="SHORTCUT ICON">

    <head>
<%--                <% //把account 的值傳過來了--%>
<%--                        Object name=session.getAttribute("name");--%>
<%--                            String username = "";--%>
<%--                                if(name!=null){--%>
<%--                                        username = name.toString();--%>
<%--                                        }--%>
<%--                                        %>--%>
                                    <title>REWABO——歷史訂單</title>

                                    <!-- Required meta tags -->
                                    <meta charset="utf-8">
                                    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
                                    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
                                    <link href="assets/css/material-dashboard.css?v=2.1.0" rel="stylesheet" />
                                    <meta name="viewport" content="width=device-width, initial-scale=1">

                                    <!--   Core JS Files   -->
                                    <script src="assets/js/core/jquery.min.js" type="text/javascript"></script>
                                    <script src="assets/js/core/popper.min.js" type="text/javascript"></script>
                                    <script src="assets/js/core/bootstrap-material-design.min.js" type="text/javascript"></script>
                                    <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>

                                    <!--  Google Maps Plugin    -->
                                    <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

                                    <!-- Chartist JS -->
                                    <script src="assets/js/plugins/chartist.min.js"></script>

                                    <!--  Notifications Plugin    -->
                                    <script src="assets/js/plugins/bootstrap-notify.js"></script>

                                    <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
                                    <script src="assets/js/material-dashboard.min.js?v=2.1.0" type="text/javascript"></script>

                                    <!--reconnect websocket-->
                                    <script src="assets/js/websocket/reconnecting-websocket.min.js"></script>

                                    <!--     Fonts and icons     -->
                                    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
                                    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
                                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
                                    <script src="assets/js/bossHistory.js" type="text/javascript"></script>
<%--                                    <script>--%>
<%--                                        var var1 = "";--%>
<%--                                        var1 = "<%=username%>";--%>
<%--                                        console.log(var1);--%>
<%--                                        if (var1 == "") {--%>
<%--                                            console.log("!2345fdws");--%>
<%--                                            $(document).ready(function() {--%>
<%--                                                window.location.href = "https://ai-rest.cse.ntou.edu.tw/rewabo/login.jsp";--%>
<%--                                                //  window.location.href="http://localhost:8080/rewabo/logintest.jsp";--%>
<%--                                            });--%>
<%--                                        }--%>
<%--                                    </script>--%>
                                    <style>
                                        button.chatbot {
                                            display: inline;
                                            height: 45px;
                                            width: 40%;
                                            color: lightgray;
                                            background-color: #999999;
                                        }
                                    </style>
    </head>

    <body>
        <div class="wrapper ">
            <div class="sidebar" data-color="azure" data-background-color="black">
                <div class="logo">
                    <a class="simple-text logo-normal">
                    REWABO
                </a>
                </div>
                <div class="sidebar-wrapper">
                    <ul class="nav">
                        <li class="nav-item">
                            <a class="nav-link" href="index.jsp">
                                <i class="material-icons">home</i>
                                <p>首頁</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="status.jsp">
                                <i class="material-icons">table_chart</i>
                                <p>環境狀態</p>
                            </a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="history.jsp">
                                <i class="material-icons">history</i>
                                <p>歷史訂單</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="report.jsp">
                                <i class="material-icons">sort</i>
                                <p>訂單分析</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="settings.jsp">
                                <i class="material-icons">settings</i>
                                <p>設定</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="main-panel">
                <!-- Navbar -->
                <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
                    <div class="container-fluid">
                        <div class="navbar-wrapper">
                            <a class="navbar-brand" href="#pablo">吃啥廚房      老闆您好</a>
                        </div>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
          </button>
                        <div class="collapse navbar-collapse justify-content-end">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <form action="https://ai-rest.cse.ntou.edu.tw/rewabo/LogoutServlet" method="post">
                                        <button id="submit_btn" type="submit" class="btn btn-info"><i class="fas fa-sign-out-alt"></i>登出</button>
                                    </form>
                                </li>
                                <!-- your navbar here -->
                            </ul>
                        </div>
                    </div>
                </nav>
                <!-- End Navbar -->
                <div class="content">
                    <div class="container-fluid" id="historyHIHI">
                        <!-- your content here -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header card-header-warning">
                                        <h2 class="card-title ">歷史訂單</h2>
                                    </div>
                                    <div class="card-body">
                                        <div id="historyTables" class="table-responsive table-hover">
<%--                                            <table class="table" id="historyTable_0" style="display: flow">--%>
<%--                                                <thead class=" text-warning">--%>
<%--                                                    <tr>--%>
<%--                                                        <th>--%>
<%--                                                            日期--%>
<%--                                                        </th>--%>
<%--                                                        <th>--%>
<%--                                                            訂單編號--%>
<%--                                                        </th>--%>
<%--                                                        <th>--%>
<%--                                                            用餐方式--%>
<%--                                                        </th>--%>
<%--                                                        <th>--%>
<%--                                                            總價--%>
<%--                                                        </th>--%>
<%--                                                        <th>--%>

<%--                                                        </th>--%>
<%--                                                    </tr>--%>
<%--                                                </thead>--%>
<%--                                                <tbody>--%>
<%--                                                <tr><td>2019-04-21 5:42:29</td><td>5cc44a58a5ed5405c696f684</td><td>外帶</td><td>200</td><td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class5cc44a58a5ed5405c696f684">檢視</button></td></tr>--%>
<%--                                                <tr><td>2019-04-21 5:42:29</td><td>5cc44a58a5ed5405c696f684</td><td>外帶</td><td>200</td><td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class5cc44a58a5ed5405c696f684">檢視</button></td></tr>--%>
<%--                                                <tr><td>2019-04-21 5:42:29</td><td>5cc44a58a5ed5405c696f684</td><td>外帶</td><td>200</td><td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class5cc44a58a5ed5405c696f684">檢視</button></td></tr>--%>
<%--                                                <tr><td>2019-04-21 5:42:29</td><td>5cc44a58a5ed5405c696f684</td><td>外帶</td><td>200</td><td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class5cc44a58a5ed5405c696f684">檢視</button></td></tr>--%>
<%--                                                </tbody>--%>
<%--                                            </table>--%>
<%--                                            <table class="table" id="historyTable_1" style="display: none">--%>
<%--                                                <thead class=" text-warning">--%>
<%--                                                <tr>--%>
<%--                                                    <th>--%>
<%--                                                        日期--%>
<%--                                                    </th>--%>
<%--                                                    <th>--%>
<%--                                                        訂單編號--%>
<%--                                                    </th>--%>
<%--                                                    <th>--%>
<%--                                                        用餐方式--%>
<%--                                                    </th>--%>
<%--                                                    <th>--%>
<%--                                                        總價--%>
<%--                                                    </th>--%>
<%--                                                    <th>--%>

<%--                                                    </th>--%>
<%--                                                </tr>--%>
<%--                                                </thead>--%>
<%--                                                <tbody>--%>
<%--                                                <tr><td>2019-04-21 5:42:29</td><td>5cc44a58a5ed5405c696f684</td><td>外帶</td><td>200</td><td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class5cc44a58a5ed5405c696f684">檢視</button></td></tr>--%>
<%--                                                <tr><td>2019-04-21 5:42:29</td><td>5cc44a58a5ed5405c696f684</td><td>外帶</td><td>200</td><td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class5cc44a58a5ed5405c696f684">檢視</button></td></tr>--%>
<%--                                                <tr><td>2019-04-21 5:42:29</td><td>5cc44a58a5ed5405c696f684</td><td>外帶</td><td>200</td><td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class5cc44a58a5ed5405c696f684">檢視</button></td></tr>--%>
<%--                                                </tbody>--%>
<%--                                            </table>--%>
                                        </div>
                                        <input id="currentHistoryTab_num" type="hidden" value="0"/>
                                        <input id="maxHistoryTab_num" type="hidden" value="100"/>
                                        <div class="row">
                                            <nav aria-label="Page navigation example">
                                                <ul class="pagination" id="historyTab_nav_ul">
    <%--                                                <li class="page-item">--%>
    <%--                                                    <a class="page-link" href="#" onclick="previousTab()" aria-label="Previous">--%>
    <%--                                                        <span aria-hidden="true">&laquo;</span>--%>
    <%--                                                    </a>--%>
    <%--                                                </li>--%>
    <%--                                                <li class="page-item"><a class="page-link" href="#">1</a></li>--%>
    <%--                                                <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
    <%--                                                <li class="page-item"><a class="page-link" href="#">...</a></li>--%>
    <%--                                                <li class="page-item">--%>
    <%--                                                    <a class="page-link" href="#" onclick="nextTab()" aria-label="Next">--%>
    <%--                                                        <span aria-hidden="true">&raquo;</span>--%>
    <%--                                                    </a>--%>
    <%--                                                </li>--%>
                                                </ul>
                                            </nav>
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" id="inputGroup-sizing-sm">跳至</span>
                                                </div>
                                                <input type="text" class="form-control" id="my_jump2TabId" aria-label="jump2Tab">
                                                <div class="input-group-append">
                                                    <span class="input-group-text">頁</span>
                                                    <button class="btn btn-outline-secondary btn-sm" onclick="jumpToTargetTab()" type="button">GOGO</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <footer class="footer">
                                    <div class="container-fluid">
                                        <div id="chatBox" class="collapse" style="position:fixed;bottom:60px;right:110px;width:284px;height:351px;z-index:3">
                                            <div class="card border-warning mb-3">
                                                <div class="card-header text-white bg-warning" style="height: 43px;">REWABO</div>
                                                <div class="card-body" id="showDialogue" style="height: 247px; overflow-y:scroll;"></div>
                                                <div class="card-footer border-top" style="height: 90px;">
                                                    <div class="input-group">
                                                        <input type="text" id="sendMessageInput" class="form-control" style="width:180px; margin-bottom:5px">
                                                        <div style="width:100%; text-align:center">
                                                            <button id="micBtn" class="chatbot">
                                                                <i class="material-icons" id="micIcon" style="font-size: 28pt">mic</i>
                                                            </button>
                                                            <button id="submitBtn" class="chatbot">
                                                                <i class="material-icons" style="font-size: 28pt">send</i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <input id="smallTwo" type="image" src="assets/image/chatBotIcon.png" alt="小二" data-toggle="collapse" data-target="#chatBox" aria-expanded="false" aria-controls="chatBox" style="height:150px; position:fixed; bottom:60px; right:20px;" />
                                        <nav class="float-left">
                                            <ul>
                                                <li>
                                                    祝您生意興隆
                                                </li>
                                            </ul>
                                        </nav>
                                        <div class="copyright float-right">
                                            &copy;
                                            <script>
                                                document.write(new Date().getFullYear())
                                            </script>, made by 搞頑皮
                                        </div>
                                        <!-- your footer here -->
                                        <audio id="audio1"></audio>
                                    </div>
                                </footer>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>