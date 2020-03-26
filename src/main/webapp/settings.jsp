<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link href="assets/image/boticon.png" rel="SHORTCUT ICON">

<head>
    <% //把account 的值傳過來了
        Object name = session.getAttribute("name");
        String username = "";
        if (name != null) {
            username = name.toString();
        }
    %>
    <title>REWABO——設定</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link href="assets/css/material-dashboard.css?v=2.1.0" rel="stylesheet"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--   Core JS Files   -->
    <script src="assets/js/core/jquery.min.js" type="text/javascript"></script>
    <script src="assets/js/core/popper.min.js" type="text/javascript"></script>
    <script src="assets/js/core/bootstrap-material-design.min.js" type="text/javascript"></script>
    <script src="assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>

    <!--  Google Maps Plugin    -->
<%--    <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>--%>

    <!-- Chartist JS -->
    <script src="assets/js/plugins/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="assets/js/plugins/bootstrap-notify.js"></script>

    <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
    <script src="assets/js/material-dashboard.min.js?v=2.1.0" type="text/javascript"></script>

    <!--reconnect websocket-->
    <script src="assets/js/websocket/reconnecting-websocket.min.js"></script>

    <!--     Fonts and icons     -->
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <script src="assets/js/bossMenu.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <style>
        .card-img {
            width: 100%;
            height: 15vw;
            object-fit: cover;
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
    </style>
    <script>
        var var1 = ""
        var1 = "<%=username%>"
        console.log(var1);
        if (var1 == "") {
            $(document).ready(function () {
                window.location.href = "https://ai-rest.cse.ntou.edu.tw/rewabo/login.jsp";
                //   window.location.href="http://localhost:8080/rewabo/logintest.jsp";
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
                <li class="nav-item active  ">
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
                    <a class="navbar-brand" href="#pablo">吃啥廚房 老闆您好</a>
                </div>
                <button class="navbar-toggler" type="button" data-toggle="collapse" aria-controls="navigation-index"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="navbar-toggler-icon icon-bar"></span>
                    <span class="navbar-toggler-icon icon-bar"></span>
                    <span class="navbar-toggler-icon icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <form action="https://ai-rest.cse.ntou.edu.tw/rewabo/LogoutServlet" method="post">
                                <button id="submit_btn" type="submit" class="btn btn-info"><i
                                        class="fas fa-sign-out-alt"></i>登出
                                </button>
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
                <div class="row">
                    <div class="col-lg-12" id ="ModalTest">
                        <div class="card">
                            <div class="card-header card-header-tabs bg-secondary">
                                <div class="nav-tabs-navigation">
                                    <div class="nav-tabs-wrapper">
                                        <span class="nav-tabs-title">請選擇:</span>
                                        <ul class="nav nav-tabs nav-fill" data-tabs="tabs">
                                            <li class="nav-item">
                                                <a class="nav-link active" href="#menu" onclick="controlMenuTools(true)" data-toggle="tab">
                                                    <h4><i class="material-icons">restaurant_menu</i> 菜單</h4>
                                                    <div class="ripple-container"></div>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="#noti" onclick="controlMenuTools(false)" data-toggle="tab">
                                                    <h4><i class="material-icons">notifications_active</i> 推播</h4>
                                                    <div class="ripple-container"></div>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="#cust" onclick="controlMenuTools(false)" data-toggle="tab">
                                                    <h4><i class="material-icons">record_voice_over</i> 客服</h4>
                                                    <div class="ripple-container"></div>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="tab-content ">
                                    <div class="tab-pane active" id="menu">
                                        <div class="container-fluid">
                                            <div class="card">
                                                <div class="card-header card-header-tabs bg-primary">
                                                    <div class="nav-tabs-navigation">
                                                        <div class="nav-tabs-wrapper">
                                                            <ul id="menuClass" class="nav nav-tabs nav-fill"
                                                                data-tabs="tabs">
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="card-body">
                                                    <div class="tab-content" id="center">

                                                    </div>
                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                    <div class="tab-pane " id="noti">
                                        <div class="container-fluid">
                                            <div class="card">
                                                <div class="card-header card-header-tabs bg-primary">
                                                    <div class="nav-tabs-navigation">
                                                        <div class="nav-tabs-wrapper">
                                                            <span class="nav-tabs-title">請選擇:</span>
                                                            <ul class="nav nav-tabs nav-fill" data-tabs="tabs">
                                                                <li class="nav-item">
                                                                    <a class="nav-link active" href="#instant"
                                                                       data-toggle="tab">
                                                                        <h4>
                                                                            <i class="material-icons">notification_important</i>
                                                                            即時推播</h4>
                                                                        <div class="ripple-container"></div>
                                                                    </a>
                                                                </li>
                                                                <li class="nav-item">
                                                                    <a class="nav-link" href="#regular" data-toggle="tab">
                                                                        <h4><i class="material-icons">alarm</i>
                                                                            定期推播</h4>
                                                                        <div class="ripple-container"></div>
                                                                    </a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="card-body">
                                                    <div class="tab-content">
                                                        <div class="row tab-pane active show" id="instant">
                                                            <div class="col-lg-12 ">
                                                                <table class="table table-hover" id="instantList">
                                                                    <thead class="text-warning">
                                                                    <tr>
                                                                        <th>即時推播餐點</th>
                                                                        <th>即時推播建立時間</th>
                                                                        <th>即時推播內容</th>
                                                                        <th>推播！</th>
                                                                        <th>刪除</th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    </tbody>
                                                                </table>
                                                                <div class="card-footer ml-auto mr-auto">
                                                                    <button type="button" class="btn btn-info"
                                                                            data-toggle="modal"
                                                                            data-target="#exampleModal4">新增即時推播
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row tab-pane " id="regular">
                                                            <div class="col-lg-12 ">
                                                                <table class="table table-hover" id="regularList">
                                                                    <thead class="text-warning">
                                                                    <tr>
                                                                        <th>定期推播餐點</th>
                                                                        <th>定期推播建立時間</th>
                                                                        <th>定期推播內容</th>
                                                                        <th>推播時間頻率</th>
                                                                        <th>刪除</th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    </tbody>
                                                                </table>
                                                                <div class="card-footer ml-auto mr-auto">
                                                                    <button type="button" class="btn btn-info"
                                                                            data-toggle="modal"
                                                                            data-target="#regularNoti">新增定期推播
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>


                                        </div>
                                    </div>
                                    <div class="tab-pane" id="cust">
                                        <div class="container-fluid">
                                            
                                                        <table class="table table-hover" id="buttonList">
                                                            <thead class="text-warning">
                                                                <tr>
                                                                <th>客服按鈕名稱</th>
                                                                <th align="right" ></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>
                                                        <button type="button" class="btn btn-info"
                                                        data-toggle="modal"
                                                        data-target="#customerService">新增客服按鈕
                                                        </button>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <footer class="footer">
                                    <div id="chatBox" class="collapse"
                                         style="position:fixed;bottom:60px;right:110px;width:284px;height:351px;z-index:3">
                                        <div class="card border-warning mb-3">
                                            <div class="card-header text-white bg-warning" style="height: 43px;">REWABO
                                            </div>
                                            <div class="card-body" id="showDialogue"
                                                 style="height: 247px; overflow-y:scroll;"></div>
                                            <div class="card-footer border-top" style="height: 90px;">
                                                <div class="input-group">
                                                    <input type="text" id="sendMessageInput" class="form-control"
                                                           style="width:180px; margin-bottom:5px">
                                                    <div style="width:100%; text-align:center">
                                                        <button id="micBtn" class="chatbot">
                                                            <i class="material-icons" id="micIcon"
                                                               style="font-size: 28pt">mic</i>
                                                        </button>
                                                        <button id="submitBtn" class="chatbot">
                                                            <i class="material-icons" style="font-size: 28pt">send</i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <input id="smallTwo" type="image" src="assets/image/chatBotIcon.png" alt="小二"
                                           data-toggle="collapse" data-target="#chatBox" aria-expanded="false"
                                           aria-controls="chatBox"
                                           style="height:150px; position:fixed; bottom:60px; right:20px;" data-container="body" data-placement="top" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus."/>
                                    <div class="container-fluid">

                                        <div id="menuTools" class="float-button input-group-append dropup" style="display: block; ">
                                            <button class="btn dropdown-toggle" type="button" data-toggle="dropdown">
                                                編輯菜單<span class="caret"></span></button>
                                            <ul class="dropdown-menu">
                                                <li><a href="#" data-target="#exampleModal2"
                                                       data-toggle="modal">新增餐點</a>
                                                </li>
                                                <li><a href="#" data-target="#exampleModal6"
                                                       data-toggle="modal">新增套餐</a>
                                                </li>
                                                <li><a href="#" data-target="#exampleModal5"
                                                       data-toggle="modal">修改餐點種類</a>
                                                </li>
                                                <li><a href="#" data-target="#changeMenu" data-toggle="modal">修改餐點</a>
                                                </li>
                                                <li><a href="#" data-target="#exampleModal3"
                                                       data-toggle="modal">刪除餐點種類</a>
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
                                            </script>
                                            , made by 搞頑皮
                                        </div>
                                        <!-- your footer here -->
                                        <audio id="audio1"></audio>
                                    </div>
                                </footer>
                            </div>
                        </div>
                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">新增餐點種類</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請輸入種類名稱：</label>
                                        <br>
                                        <input type="text" id="myText"/>
                                        <br>
                                        <label class="col-form-label">請選擇種類：</label>
                                        <select id='addTypeSelect'><option value ="主餐">主餐</option>
  <option value ="配餐">配餐</option></select>
                                        <br>
                                        <input type="file" id="myFile" accept="image/*"/>
                                        <br />
                                        <div class="col-md-6" id="image-holder"> </div>

                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="upload" type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="exampleModal2" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">新增餐點</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請選擇要新增的餐點種類：</label>
                                        <select id='addClassSelect'></select>
                                        <br>
                                        <label class="col-form-label">請輸入要新增的餐點名稱：</label>
                                        <br>
                                        <input type="text" id="newItemName"/>
                                        <br>
                                        <label class="col-form-label">請輸入要新增的餐點價錢：</label>
                                        <br>
                                        <input type="text" id="newItemPrice"
                                               onkeyup="value=value.replace(/[^\d]/g,'') "/>
                                        <br>
                                        <br>
                                        <input type="file" id="newItemPic" accept="image/*"/>

                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="uploadNew"
                                               type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">刪除餐點種類</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請選擇要刪除的餐點種類：</label>
                                        <select id='deleteClassSelect'></select>
                                        <br>

                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="deleteNew"
                                               type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="exampleModal4" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">新增即時推播</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請選擇要推播的餐點：</label>
                                        <select id='notiItemSelect'></select>
                                        <br>
                                        <label class="col-form-label">請輸入要推播的餐點價錢：</label>
                                        <br>
                                        <input type="text" id="notiPrice" onkeyup="value=value.replace(/[^\d]/g,'') "/>
                                        <br>
                                        <label class="col-form-label">請輸入要推播的訊息：</label>
                                        <br>
                                        <input type="text" id="notiText"/>


                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="notiNow" type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="exampleModal5" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">修改餐點種類</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請選擇要修改的餐點種類：</label>
                                        <select id='editClassSelect'></select>
                                        <br>
                                        <label class="col-form-label">請輸入新的餐點種類名稱：</label>
                                        <br>
                                        <input type="text" id="newClassName"/>
                                        <br>

                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="changeNewKind"
                                               type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="exampleModal6" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">新增套餐</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">

                                        <label class="col-form-label">請輸入要新增的套餐名稱：</label>
                                        <input type="text" id="newSet"/>
                                        <br>
                                        <label class="col-form-label">請輸入要新增的套餐價錢：</label>
                                        <br>
                                        <input type="text" id="newSetPrice"
                                               onkeyup="value=value.replace(/[^\d]/g,'') "/>
                                        <br>
                                        <input type="file" id="newSetFile" accept="image/*"/>


                                        <table id="myTable" class=" table order-list" border="0">
                                            <thead>
                                            <tr>
                                                <td>套餐餐點</td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-sm-4">
                                                    <select id='selectname50' name="name "
                                                            class="form-control"></select>
                                                </td>
                                                <td class="col-sm-2">
                                                    <a class="deleteRow"></a>

                                                </td>
                                            </tr>
                                            </tbody>
                                            <tfoot>
                                            <tr>
                                                <td colspan="5" style="text-align: left;">
                                                    <input type="button" class="btn btn-lg btn-block " id="addrow"
                                                           value="新增餐點"/>
                                                </td>
                                            </tr>
                                            <tr>
                                            </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="addNewKind"
                                               type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="changeMenu" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">修改餐點</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請選擇要修改的餐點：</label>
                                        <select id='selectChangeMenu'></select>
                                        <br>
                                        <label class="col-form-label">請選擇修改後的餐點種類：</label>
                                        <select id='selectNewKind'></select>
                                        <br>
                                        <label class="col-form-label">請輸入修改後的餐點名稱：</label>
                                        <br>
                                        <input type="text" id="newMenuName"/>
                                        <br>
                                        <label class="col-form-label">請輸入修改後的餐點價錢：</label>
                                        <br>
                                        <input type="text" id="newMenuPrice"
                                               onkeyup="value=value.replace(/[^\d]/g,'') "/>
                                        <br>
                                        <br>
                                        <input type="file" id="newMenuImage" accept="image/*"/>

                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="newMenuChange"
                                               type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="regularNoti" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" >新增定期推播</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請選擇要推播的餐點：</label>
                                        <select id='regNotiItemSelect'></select>
                                        <br>
                                        <label class="col-form-label">請輸入要推播的餐點價錢：</label>
                                        <br>
                                        <input type="text" id="regNotiPrice" onkeyup="value=value.replace(/[^\d]/g,'') "/>
                                        <br>
                                        <label class="col-form-label">請輸入要推播的訊息：</label>
                                        <br>
                                        <input type="text" id="regNotiText"/>
                                        <br>
                                        <label class="col-form-label">請選擇推播頻率</label>
                                        <br>
                                        <input type="text" id="regNotiDays" onkeyup="value=value.replace(/[^\d]/g,'') "/>


                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="newRegNoti"
                                               type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="customerService" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" >新增客服</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <label class="col-form-label">請輸入要新增的客服按鈕名稱：</label>
                                        <br>
                                        <input type="text" id="customerNewButtonName"/>

                                    </div>
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" data-dismiss="modal" id="newCusButton"
                                               type="submit"/>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <!-- <button type="submit" class="btn bg-primary" id="upload">新增</button> -->
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
<script>
  function controlMenuTools(show) {
    if(show){
      $('#menuTools').show();
    }else {
      $('#menuTools').hide();
    }
  }
</script>
</body>

</html>