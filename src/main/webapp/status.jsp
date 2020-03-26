<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <html>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate">
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
    <title>REWABO——餐廳狀態</title>
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

    <!-- ajax -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="assets/js/iotdata.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">


    <style>
        .card {
            z-index: 9995;
        }
        
        #chatBox {
            z-index: 9997;
            position: relative;
        }
        
        .float-button {
            position: fixed;
            height: 90px;
            width: 40px;
            bottom: 40px;
            right: 100px;
        }
        
        button.chatbot {
            display: inline;
            height: 45px;
            width: 40%;
            color: lightgray;
            background-color: #999999;
        }
        
        hr {
            -moz-border-bottom-colors: none;
            -moz-border-image: none;
            -moz-border-left-colors: none;
            -moz-border-right-colors: none;
            -moz-border-top-colors: none;
            border-color: #EEEEEE -moz-use-text-color #FFFFFF;
            border-style: solid none;
            border-width: 1px 0;
            margin: 18px 0;
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
                // window.location.href="http://localhost:8080/rewabo/logintest.jsp";
            });
        }
    </script>
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
                    <li class="nav-item active">
                        <a class="nav-link actice" href="status.jsp">
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
                        <a class="navbar-brand" href="#pablo">吃啥廚房       老闆您好</a>
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
            <div class="content" style="padding-bottom: 0px">
                <div class="container-fluid">

                    <!-- your content here -->
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6" style="z-index:0">
                            <div class="card card-stats">
                                <div class="card-header card-header-danger card-header-icon">
                                    <div class="card-icon">
                                        <i class="material-icons">people</i>
                                    </div>
                                    <p class="card-category">餐廳人數</p>
                                    <h3 class="card-title" id="peo">沒人真可憐
                                    </h3>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 col-sm-6" style="z-index:0">
                            <div class="card card-stats">
                                <div class="card-header card-header-warning card-header-icon">
                                    <div class="card-icon">
                                        <i class="material-icons">brightness_high</i>
                                    </div>
                                    <p class="card-category">溫度</p>
                                    <h3 id="temp" class="card-title">偵測中...</h3>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 col-sm-6" style="z-index:0">
                            <div class="card card-stats">
                                <div class="card-header card-header-info card-header-icon">
                                    <div class="card-icon">
                                        <i class="material-icons">opacity</i>
                                    </div>
                                    <p class="card-category">溼度</p>
                                    <h3 id="hum" class="card-title">偵測中...</h3>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12" style="z-index:0">
                            <div class="card">
                                <div class="card-header card-header-tabs bg-secondary">
                                    <div class="nav-tabs-navigation">
                                        <div class="nav-tabs-wrapper">
                                            <ul class="nav nav-tabs nav-fill" data-tabs="tabs">
                                                <li class="nav-item">
                                                    <a class="nav-link active" href="#aircon" data-toggle="tab">
                                                        <h4>冷氣</h4>
                                                        <div class="ripple-container"></div>
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" href="#fan" data-toggle="tab">
                                                        <h4>風扇</h4>
                                                        <div class="ripple-container"></div>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">

                                    <div class="tab-content ">
                                        <div class="tab-pane active" id="aircon">
                                            <div class="col-md-12" id="">
                                                <div class="card md-3">
                                                    <div class="row no-gutters">
                                                        <div class="col-md-4">
                                                            <img src="assets/image/aircon.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                        </div>
                                                        <div class="col-md-8">
                                                            <div class="card-body">
                                                                <h1 id="airTempWords" class="card-title">大廳冷氣目前為關</h1>
                                                                <div class="row" style="height:300px;">
                                                                    <div class="col-md-12">
                                                                        <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                            <button id="airOnBtn" class="btn  btn-lg" style="font-size: 35pt" href="#">開</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                            <button id="tempUpBtn" class="btn btn-lg" style="font-size: 35pt"><i class="fas fa-chevron-up"></i></button>
                                                                            <br><br><button id="airOffBtn" class="btn btn-lg" style="font-size: 35pt" href="#">關</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                            <button id="tempDownBtn" class="btn btn-lg" style="font-size: 35pt"><i class="fas fa-chevron-down"></i></button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="fan">
                                            <div class="card lg-12">
                                                <h1 class="card-title" align="center">1F</h1>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg1" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus1" class="card-title">1號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn1" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg2" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus2" class="card-title">2號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn2" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg3" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus3" class="card-title">3號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn3" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg4" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus4" class="card-title">4號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn4" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card lg-12">
                                                <h1 class="card-title" align="center">2F</h1>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg5" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus5" class="card-title">1號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn5" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg6" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus6" class="card-title">2號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn6" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg7" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus7" class="card-title">3號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn7" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="card md-3">
                                                            <div class="row no-gutters">
                                                                <div class="col-md-4">
                                                                    <img id="fanImg8" src="assets/image/fanStop.png" class="card-img img-fluid img-thumbnail" alt="' + name2 + '">
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <div class="card-body">
                                                                        <h4 id="fanStatus8" class="card-title">4號風扇 : 關</h4>
                                                                        <div class="row" style="height:100px;">
                                                                            <div class="col-md-12">
                                                                                <div class="thumbnail" style="width:100%; height:100%;" align="center">
                                                                                    <button id="fanOnBtn8" class="btn btn-info btn" style="font-size: 20pt" href="#">開</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!------------- 聊天室頭 ------------->
                        <div id="chatBox" class="collapse" style="position:fixed;bottom:60px;right:110px;width:284px;height:351px;">
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
                        <!------------- 聊天室尾 ------------->
                    </div>
                    <footer class="footer">
                        <div class="container-fluid">
                            <div class="float-button input-group-append dropup">
                                <button class="btn dropdown-toggle" type="button" data-toggle="dropdown">
                                            編輯腳本<span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <li><a href="#" data-target="#exampleModal2" data-toggle="modal">新增腳本</a>
                                    </li>

                                </ul>
                            </div>
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
            <div class="modal fade" id="exampleModal2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">新增腳本</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                        </div>

                        <div class="modal-body">
                            <label class="col-form-label">請選擇裝置</label>
                            <select id='addFootDeviceSelect'><option value="aircon">冷氣</option><option value="fan">電扇</option></select>
                            <br>
                            <h3>溫度
                                <select id='addFootTempSelect'><option value="gt">></option><option value="gte">>=</option><option value="lt"><</option><option value="lte"><=</option><option value="eq">=</option></select>
                                <input type="text" id="addFootDeviceTemp" placeholder="請輸入溫度" /></h3>
                            <br>
                            <h3>人數
                                <select id='addFootPeopleSelect'><option value="gt">></option><option value="gte">>=</option><option value="lt"><</option><option value="lte"><=</option><option value="eq">=</option></select>
                                <input type="text" id="addFootDevicePeople" placeholder="請輸入人數" />
                                <select id='addFootOpenSelect'><option value = "open">開</option><option value = "close">關</option></select></h3>
                            <br>
                            <br>
                            <input data-dismiss="modal" id="uploadFoot" type="submit" />
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>