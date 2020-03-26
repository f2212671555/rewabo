<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <html>

    <head>
        <%--<% //把account 的值傳過來了--%>
            <%--Object name=session.getAttribute("name");--%>
                <%--String username = "";--%>
                    <%--if(name!=null){--%>
                            <%--username = name.toString();--%>
                            <%--}--%>
                            <%--%>--%>
            <!-- Required meta tags -->
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <link href="assets/image/boticon.png" rel="SHORTCUT ICON">

            <!-- Bootstrap CSS -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

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
                .c{
                    display:inline;
                }
            </style>
            
            <%--<script>--%>
                <%--var var1 = "";--%>
                <%--var1 = "<%=username%>";--%>
                <%--console.log(var1);--%>
                <%--if (var1 == "") {--%>
                    <%--console.log("!2345fdws");--%>
                    <%--$(document).ready(function() {--%>
                        <%--window.location.href = "https://ai-rest.cse.ntou.edu.tw/rewabo/login.jsp";--%>
                        <%--//  window.location.href="http://localhost:8080/rewabo/logintest.jsp";--%>
                    <%--});--%>
                <%--}--%>
            <%--</script>--%>
            <!-- Chart.js 畫長條圖用 -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>

            <script src="assets/js/testReport.js" type="text/javascript"></script>
            <style>
                html,
                body {
                    font-family: 'Noto Sans TC', Meiryo, '微軟正黑體', 'Microsoft JhengHei', sans-serif;
                }
                
                hr {
                    border: 0;
                    border-bottom: 0.5px solid black;
                    margin-top: 12px;
                }
            </style>
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
                        <li class="nav-item active">
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
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header card-header-danger">
                                        <h2 class="card-title ">訂單分析</h2>
                                    </div>
                                    <div class="card-body">
                                        <button type="button" class="btn btn-outline-secondary" style="display:inline-flex;margin-bottom:14px" onclick="updateDatabase()">更新資料庫</button>

                                        <div style="margin-left: 2px">
                                            <div style="display: inline-block;width:32%">
                                                <h3 style="margin-top:8px">主餐</h3>
                                                <hr>
                                            </div>
                                            <div style="display: inline-block;width:32%">
                                                <h3 style="margin-top:8px">配餐</h3>
                                                <hr>
                                            </div>
                                            <div style="display: inline-block;width:32%">
                                                <h3 style="margin-top:8px">飲料</h3>
                                                <hr>
                                            </div>
                                        </div>

                                        <div>
                                            <nav aria-label="breadcrumb" style="width:32%;display:inline-block">
                                                <ol class="breadcrumb">
                                                    <li id="main1" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">1</span> 起司牛肉堡</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="main2" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">2</span> 培根蛋餅</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="main3" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">3</span> 鐵板麵</li>
                                                </ol>
                                            </nav>
                                            <nav aria-label="breadcrumb" style="width:32%;display:inline-block">
                                                <ol class="breadcrumb">
                                                    <li id="side1" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">1</span> 雞塊</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="side2" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">2</span> 熱狗</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="side3" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">3</span> 蘿蔔糕</li>
                                                </ol>
                                            </nav>
                                            <nav aria-label="breadcrumb" style="width:32%;display:inline-block">
                                                <ol class="breadcrumb">
                                                    <li id="drink1" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">1</span> 紅茶</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="drink2" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">2</span> 奶茶</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="drink3" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">3</span> 豆漿</li>
                                                </ol>
                                            </nav>
                                        </div>

                                        <div style="display:block">
                                            <div class="card" id="mainCanvasDiv" style="width:32%;display:inline-flex">
                                            </div>
                                            <div class="card" id="sideCanvasDiv" style="width:32%;display:inline-flex">
                                            </div>
                                            <div class="card" id="drinkCanvasDiv" style="width:32%;display:inline-flex">
                                            </div>
                                            <br>
                                            <div style="display: inline-block;width:96%">
                                                <h3 style="margin-top:8px">套餐</h3>
                                                <hr>
                                            </div>
                                            <nav aria-label="breadcrumb" style="width:96%;display:inline-block">
                                                <ol class="breadcrumb">
                                                    <li id="set1" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">1</span> 好健康</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="set2" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">2</span> 好美麗</li>
                                                </ol>
                                                <ol class="breadcrumb">
                                                    <li id="set3" class="breadcrumb-item" aria-current="page"><span class="badge badge-info">3</span> 好滿足</li>
                                                </ol>
                                            </nav>
                                            <div class="card" id="setCanvasDiv" style="width:100%;display:inline-flex">
                                            </div>
                                        </div>

                                        <div >
                                            <h3>可能套餐</h3>
                                            <table class="table">
                                            <tbody id = "ps">
                                            </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
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
                                                <tbody id="mybody">
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    </html>