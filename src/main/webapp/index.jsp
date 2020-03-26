<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="assets/image/boticon.png" rel="SHORTCUT ICON">

    <head>
                <% //把account 的值傳過來了
                        Object name=session.getAttribute("name");
                            String username = "";
                                if(name!=null){
                                        username = name.toString();
                                        }
                                        %>
                                    <title>REWABO</title>
                                    <!-- Required meta tags -->
                                    <meta charset="utf-8">
                                    <meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate">
                                    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
                                    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
                                    <link href="assets/css/material-dashboard.css?v=2.1.0" rel="stylesheet" />
                                    <meta name="viewport" content="width=device-width, initial-scale=1">

                                    <!--   Core JS Files   -->
                                    <script src="assets/js/core/jquery.min.js" type="text/javascript"></script>
                                    <script src="assets/js/core/popper.min.js" type="text/javascript"></script>
                                    <script src="assets/js/core/bootstrap-material-design.min.js" type="text/javascript"></script>
                                    <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>

                                    <!--reconnect websocket-->
                                    <script src="assets/js/websocket/reconnecting-websocket.min.js"></script>

                                    <!-- Chartist JS -->
                                    <script src="assets/js/plugins/chartist.min.js"></script>

                                    <!--  Notifications Plugin    -->
                                    <script src="assets/js/plugins/bootstrap-notify.js"></script>

                                    <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
                                    <script src="assets/js/material-dashboard.min.js?v=2.1.0" type="text/javascript"></script>

                                    <!--     Fonts and icons     -->
                                    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
                                    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
                                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
                                    <script src="assets/js/bossOrder.js" type="text/javascript"></script>
                                    <style>
                                        .scroll {
                                            height: auto;
                                            max-height: 450px;
                                            overflow-y: auto;
                                        }
                                        
                                        button.chatbot {
                                            display: inline;
                                            height: 45px;
                                            width: 40%;
                                            color: lightgray;
                                            background-color: #999999;
                                        }
                                    </style>
                                    <script>
                                        var var1 = "";
                                        var1 = "<%=username%>";
                                        console.log(var1);
                                        if (var1 == "") {
                                            console.log("!2345fdws");
                                            $(document).ready(function() {
                                                window.location.href = "https://ai-rest.cse.ntou.edu.tw/rewabo/login.jsp";
                                                //  window.location.href="http://localhost:8080/rewabo/logintest.jsp";
                                            });
                                        }
                                    </script>
    </head>

    <body id="head1">

        <div class="wrapper ">
            <div class="sidebar" data-color="azure" data-background-color="black">
                <div class="logo">
                    <a class="simple-text logo-normal">
                    REWABO
                </a>
                </div>
                <div class="sidebar-wrapper">
                    <ul class="nav">
                        <li class="nav-item active  ">
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
                        <li class="nav-item">
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

                    <div class="container-fluid">

                        <!-- your content here -->
                        <div class="col-lg-12">
                            <div class="card">
                                <div id="orderStatusBar" class="card-header card-header-tabs bg-secondary">
                                    <div class="nav-tabs-navigation">
                                        <div class="nav-tabs-wrapper">

                                            <span class="nav-tabs-title"><h4>請選擇:</h4></span>
                                            <ul class="nav nav-tabs nav-fill" data-tabs="tabs">
                                                <li class="nav-item" id="waiting">
                                                    <a class="nav-link active" href="#profile" data-toggle="tab">
                                                        <h4><i class="material-icons">refresh</i> 待製作</h4>
                                                        <div class="ripple-container"></div>
                                                    </a>
                                                </li>
                                                <li class="nav-item" id="making">
                                                    <a class="nav-link" href="#messages" data-toggle="tab">
                                                        <h4><i class="material-icons">hourglass_full</i> 製作中</h4>
                                                        <div class="ripple-container"></div>
                                                    </a>
                                                </li>
                                                <li class="nav-item" id="finished">
                                                    <a class="nav-link" href="#settings" data-toggle="tab">
                                                        <h4><i class="material-icons">check_circle</i> 完成訂單</h4>
                                                        <div class="ripple-container"></div>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="content ">
                                        <div class="container-fluid">
                                            <div class="row scroll tab_content" id="waitingOrderClass" style="display: block; ">
                                                <!-- ---------   待製作   ---------- -->
                                            </div>
                                            <div class="row scroll tab_content" id="makingOrderClass" style="display: none; ">
                                                <!-- ---------   製作中   ---------- -->
                                            </div>
                                            <div class="row scroll tab_content" id="finishOrderClass" style="display: none; ">
                                                <!-- ---------   已完成   ---------- -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <footer class="footer">
                                <div class="container-fluid">

                                    <!-- ---------   chatBox   ---------- -->

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
                                    <input id="smallTwo" type="image" src="assets/image/chatBotIcon.png" alt="小二" data-toggle="collapse" data-target="#chatBox" aria-expanded="false" aria-controls="chatBox" style="height:150px; position:fixed; bottom:60px; right:20px;z-index:3" />

                                    <!-- ---------   chatBox   ---------- -->

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
                                <div id="predictTime"></div>
                                <div id="delete"></div>
                                <script type="text/javascript">
                                    $("#waiting").click(function() {
                                        $(".tab_content").hide();
                                        $("#waitingOrderClass").show();
                                    });

                                    $("#making").click(function() {
                                        $(".tab_content").hide();
                                        $("#makingOrderClass").show();
                                    });

                                    $("#finished").click(function() {
                                        $(".tab_content").hide();
                                        $("#finishOrderClass").show();
                                    });
                                </script>
                            </footer>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>