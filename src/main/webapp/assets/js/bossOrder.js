let protocol;
let hostname;
let port;
let bossOrderEndpoint;
let endpoint;

let rewabo_base_url;
let showOrder_by_status_waiting_url;
let showOrder_by_status_making_url;
let showOrder_by_status_finished_url;
let setOrderStatusServlet_url;
let deleteOrderServlet_url;
let showMessageServlet_url;
let logoutServlet_url;
let nicp_base_url;
let orderStatusChange_url;

$(document).ready(function () {
    $.ajax({
        async: false,
        url: "static/json/settings.json",
        type: "GET",
        dataType: "json",
        success: function (data) {
            console.log(data);
            protocol = data.rewabo.protocol;
            hostname = data.rewabo.hostname;
            port = data.rewabo.port;
            bossOrderEndpoint = data.rewabo.urls.endpoint.bossOrderEndpoint;
            endpoint = data.rewabo.urls.endpoint.endpoint;

            rewabo_base_url = data.rewabo.urls.baseUrl;
            showOrder_by_status_waiting_url = rewabo_base_url
                + data.rewabo.urls.services.showOrder_by_status_waiting_url;
            showOrder_by_status_making_url = rewabo_base_url
                + data.rewabo.urls.services.showOrder_by_status_making_url;
            showOrder_by_status_finished_url = rewabo_base_url
                + data.rewabo.urls.services.showOrder_by_status_finished_url;
            setOrderStatusServlet_url = rewabo_base_url
                + data.rewabo.urls.services.setOrderStatusServlet_url;
            deleteOrderServlet_url = rewabo_base_url
                + data.rewabo.urls.services.deleteOrderServlet_url;
            showMessageServlet_url = rewabo_base_url
                + data.rewabo.urls.services.showMessageServlet_url;
            logoutServlet_url = rewabo_base_url
                + data.rewabo.urls.services.logoutServlet_url;
            nicp_base_url = data.nicp.urls.baseUrl;
            orderStatusChange_url = nicp_base_url
                + data.nicp.urls.services.orderStatusChange_url;
        },
        error: function () {

        }
    })
});

$(document).ready(function () {
    loadAccept();
    $('#waiting').click(function (e) {
        loadAccept();
    });
    $('#making').click(function (e) {

        $.ajax({
            type: "GET",
            url: showOrder_by_status_making_url,
            dataType: "json",
            success: function (data) {
                console.log(data);

                var allOrder = [];
                for (var i = 0; i < data.length; i++) {
                    allOrder.push(data[i]);
                }

                // $('#makingOrderClass').empty();

                for (var j = 0; j < allOrder.length; j++) {
                    var oid = allOrder[j]._id.$oid;
                    var isAppended = false;
                    var makingCards = $('#makingOrderClass').find(
                        '[id^=Makingcard]').each(function () {
                        var currentId = this.id.split("Makingcard")[1];
                        if (oid === currentId) {
                            isAppended = true;
                        }
                    });
                    if (!isAppended) {
                        var psid = allOrder[j].CustomerID;
                        var date = allOrder[j].date.split(" ")[0] + "@"
                            + allOrder[j].date.split(" ")[1];
                        var type = allOrder[j].Type;
                        var number = allOrder[j].Number;

                        $('#makingOrderClass').append(
                            '<div class= "col-md-12" id="Makingcard' + oid + '">' +
                            '<div class="card" id="card' + oid + '">' +
                            '<div class="card-header card-header-info"><h4 class="card-title">'
                            + allOrder[j].Type + '訂單 第' + allOrder[j].Number
                            + '號</h4>' +
                            '<p class="card-category">' + allOrder[j].date + '</p>' +
                            '</div><div class = "card-body table-responsive"><table class="table table-hover" id = "table'
                            + oid + '"><thead class="text-info">' +
                            '<tr><th>已完成</th><th>餐點名稱</th><th>數量</th><th>價錢</th></tr></thead><tbody>'
                        );

                        for (var p = 0; p < allOrder[j].MyMenu.length; p++) {
                            var itemNumberForTr = oid + p;
                            $('#table' + oid).append(
                                '<tr id="MakingTr' + itemNumberForTr
                                + '" onclick=largeFormCheck("' + itemNumberForTr
                                + '") >' +
                                '<td><div class="form-check"><label class="form-check-label"><input class="form-check-input" type="checkbox" value=""><span class="form-check-sign"><span class="check"></span></span></label></div></td>'
                                +
                                '<td>' + allOrder[j].MyMenu[p].Name + '</td>' +
                                '<td>' + allOrder[j].MyMenu[p].Amount + '</td>' +
                                '<td>' + allOrder[j].MyMenu[p].Price + '</td>' +
                                '</tr></tbody></table></div>'
                            );
                        }

                        if (allOrder[j].Note != null) {
                            $('#card' + oid).append(
                                '<div class="card-footer"><div class="stats">' +
                                '<h5>備註：</h5><p>' + allOrder[j].Note + '</p>' +
                                '</div></div><button id= "button' + oid
                                + '" type="submit" class="btn btn-success pull-right" onClick =makingClick("'
                                + psid + '","' + date + '","' + type + '","' + number
                                + '","' + oid + '") disabled>製作完成</button>'
                            );
                        } else {
                            $('#card' + oid).append(
                                '<div class="card-footer"><div class="stats">' +
                                '<h5>備註：</h5><p>無</p>' +
                                '</div></div><button id= "button' + oid
                                + '" type="submit" class="btn btn-success pull-right" onClick =makingClick("'
                                + psid + '","' + date + '","' + type + '","' + number
                                + '","' + oid + '") disabled>製作完成</button>'
                            );
                        }
                    }
                }
                listenCheclbox();
            }
        });
    });

    $('#finished').click(function (e) {
        $.ajax({
            type: "GET",
            url: showOrder_by_status_finished_url,
            dataType: "json",
            success: function (data) {
                console.log(data);
                var allOrder = [];
                for (var i = 0; i < data.length; i++) {
                    allOrder.push(data[i]);
                    // order
                }

                $('#finishOrderClass').empty();
                for (var j = 0; j < allOrder.length; j++) {
                    var oid = allOrder[j]._id.$oid;
                    var people = allOrder[j].People;
                    var psid = allOrder[j].CustomerID;
                    var date = allOrder[j].date.split(" ")[0] + "@"
                        + allOrder[j].date.split(" ")[1];
                    var type = allOrder[j].Type;
                    var number = allOrder[j].Number;
                    $('#finishOrderClass').append(
                        '<div class= "col-md-12" >' +
                        '<div class="card" id="card' + oid + '">' +
                        '<div class="card-header card-header-info"><h4 class="card-title">'
                        + allOrder[j].Type + '訂單 第' + allOrder[j].Number + '號</h4>'
                        +
                        '<p class="card-category">' + allOrder[j].date + '</p>' +
                        '</div><div class = "card-body table-responsive"><div class="float-right"><h4>總價：'
                        + allOrder[j].TotalPrice
                        + '</h4></div><table class="table table-hover" id = "table'
                        + oid + '"><thead class="text-info">' +
                        '<tr><th>餐點名稱</th><th>數量</th><th>價錢</th></tr></thead><tbody>'
                    );

                    for (var p = 0; p < allOrder[j].MyMenu.length; p++) {
                        $('#table' + oid).append(
                            '<tr>' +
                            '<td>' + allOrder[j].MyMenu[p].Name + '</td>' +
                            '<td>' + allOrder[j].MyMenu[p].Amount + '</td>' +
                            '<td>' + allOrder[j].MyMenu[p].Price + '</td>' +
                            '</tr></tbody></table>'
                        );
                    }

                    if (allOrder[j].Note != null) {
                        $('#card' + oid).append(
                            '<div class="card-footer"><div class="stats">' +
                            '<h5>備註：</h5><p>' + allOrder[j].Note + '</p>' +
                            '</div></div><button id= "button' + oid
                            + '" type="submit" class="btn btn-success pull-right" onClick =finishedClick("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid + '","' + people + '")>已結帳</button>'
                        );
                    } else {
                        $('#card' + oid).append(
                            '<div class="card-footer"><div class="stats">' +
                            '<h5>備註：</h5><p>無</p>' +
                            '</div></div><button id= "button' + oid
                            + '" type="submit" class="btn btn-success pull-right" onClick =finishedClick("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid + '","' + people + '")>已結帳</button>'
                        );
                    }

                }
            }
        });
    });

    class WebSocketClient {

        constructor(protocol, hostname, port, endpoint) {
            this.webSocket = null;
            this.protocol = protocol;
            this.hostname = hostname;
            this.port = port;
            this.endpoint = endpoint;
        }

        getServerUrl() {
            return this.protocol + "://" + this.hostname + ":" + this.port
                + this.endpoint;
        }

        connect() {
            try {
                this.webSocket = new ReconnectingWebSocket(this.getServerUrl());
                
                // Implement WebSocket event handlers!
                this.webSocket.onopen = function (event) {
                    console.log("onopen::" + JSON.stringify(event, null, 4));
                };

                // 接收Websocket Server傳來的資料----> 訂單json <-----------
                this.webSocket.onmessage = function (event) {
                    var app = event.data;
                    var msg = JSON.parse(app)[0];
                    // get order json object!!
                    console.log(app);
                    var oid = msg._id.$oid;
                    console.log("_______");
                    console.log(oid);
                    console.log("_______");
                    var psid = msg.CustomerId;
                    var date = msg.date.split(" ")[0] + "@" + msg.date.split(" ")[1];
                    var type = msg.Type;
                    var number = msg.Number;
                    // 機器人也會跳通知
                    var showDialogue = $("#showDialogue");
                    showDialogue.append('<div onclick=gotToTargetOrder("' + oid
                        + '") class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">您有新訂單 '
                        + msg.Type + msg.Number + '</div>');
                    playAudio("您有新訂單");
                    //scrollbar置底
                    $("#chatBox").collapse("show");
                    showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
                    console.log(msg._id.$oid);
                    $('#waitingOrderClass').append(
                        '<div class= "col-md-12" >' +
                        '<div class="card" id="card' + oid + '">' +
                        '<div class="card-header card-header-info"><h4 class="card-title">'
                        + msg.Type + '訂單 第' + msg.Number + '號</h4>' +
                        '<p class="card-category">' + msg.date + '</p>' +
                        '</div><div class = "card-body table-responsive"><table class="table table-hover" id = "table'
                        + oid + '"><thead class="text-info">' +
                        '<tr><th>餐點名稱</th><th>數量</th><th>價錢</th></tr></thead><tbody>'
                    );
                    for (var p = 0; p < msg.MyMenu.length; p++) {
                        $('#table' + oid).append(
                            '<tr>' +
                            '<td>' + msg.MyMenu[p].Name + '</td>' +
                            '<td>' + msg.MyMenu[p].Amount + '</td>' +
                            '<td>' + msg.MyMenu[p].Price + '</td>' +
                            '</tr></tbody></table></div>'
                        );
                    }

                    if (msg.Note != null) {
                        $('#card' + oid).append(
                            '<div class="card-footer"><div class="stats">' +
                            '<h5>備註：</h5><p>' + msg.Note + '</p>' +
                            '</div></div><button id= "button' + oid
                            + '" type="submit" class="btn btn-success pull-right"  onClick =waitingClick("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid
                            + '")>開始製作</button><button class="btn btn-danger" data-toggle="modal" data-target="#confirm-delete'
                            + oid + '">刪除訂單</button>'
                        );
                        if (msg.Type === "外帶") {
                            var target = "#button" + oid;
                            $(target).attr('onClick', false);
                            var dataTarget = "#confirm-predictTime" + oid;
                            $(target).attr('data-toggle', 'modal');
                            $(target).attr('data-target', dataTarget);
                            $('#predictTime').append(
                                '<div class="modal fade" id="confirm-predictTime' + oid
                                + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h5>設定預計取餐時間</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button></div><div class="modal-body"><div id="button-group'
                                + oid
                                + '"><button type="button" class="btn bg-secondary btn-sm">5分</button><button type="button" class="btn bg-secondary btn-sm">10分</button><button type="button" class="btn bg-secondary btn-sm">15分</button><button type="button" class="btn bg-secondary btn-sm">20分</button><button type="button" class="btn bg-secondary btn-sm">25分</button><button type="button" class="btn bg-secondary btn-sm">30分</button></div></div><div class="modal-footer" id="predictTimeTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                                +
                                '<button class="btn btn-success btn-ok"  onClick =exWaitingClick("'
                                + psid + '","' + date + '","' + type + '","' + number
                                + '","' + oid
                                + '") data-dismiss="modal" disabled>開始製作</button></div></div></div></div>'
                            );
                        }
                        $('#delete').append(
                            '<div class="modal fade" id="confirm-delete' + oid
                            + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-body"><h3>確定要刪除此餐點嗎？</h3></div><div class="modal-footer" id="deleteTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            +
                            '<button class="btn btn-danger btn-ok"  onClick =deleteOrder("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid
                            + '") data-dismiss="modal">刪除</button></div></div></div></div>'
                        );
                    } else {
                        $('#card' + oid).append(
                            '<div class="card-footer"><div class="stats">' +
                            '<h5>備註：</h5><p>無</p>' +
                            '</div></div><button id= "button' + oid
                            + '" type="submit" class="btn btn-success pull-right" onClick =waitingClick("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid
                            + '") >開始製作</button><button class="btn btn-danger" data-toggle="modal" data-target="#confirm-delete'
                            + oid + '">刪除訂單</button>'
                        );
                        if (msg.Type === "外帶") {
                            var target = "#button" + oid;
                            $(target).attr('onClick', false);
                            var dataTarget = "#confirm-predictTime" + oid;
                            $(target).attr('data-toggle', 'modal');
                            $(target).attr('data-target', dataTarget);
                            $('#predictTime').append(
                                '<div class="modal fade" id="confirm-predictTime' + oid
                                + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h5>設定預計取餐時間</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button></div><div class="modal-body"><div id="button-group'
                                + oid
                                + '"><button type="button" class="btn bg-secondary btn-sm">5分</button><button type="button" class="btn bg-secondary btn-sm">10分</button><button type="button" class="btn bg-secondary btn-sm">15分</button><button type="button" class="btn bg-secondary btn-sm">20分</button><button type="button" class="btn bg-secondary btn-sm">25分</button><button type="button" class="btn bg-secondary btn-sm">30分</button></div></div><div class="modal-footer" id="predictTimeTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                                +
                                '<button class="btn btn-success btn-ok"  onClick =exWaitingClick("'
                                + psid + '","' + date + '","' + type + '","' + number
                                + '","' + oid
                                + '") data-dismiss="modal" disabled>開始製作</button></div></div></div></div>'
                            );
                        }
                        $('#delete').append(
                            '<div class="modal fade" id="confirm-delete' + oid
                            + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-body"><h3>確定要刪除此餐點嗎？</h3></div><div class="modal-footer" id="deleteTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            +
                            '<button class="btn btn-danger btn-ok"  onClick =deleteOrder("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid
                            + '") data-dismiss="modal">刪除</button></div></div></div></div>'
                        );
                    }
                    changeButton(oid);
                    // order

                };
                // console.log("onmessage::" + JSON.stringify(msg, null, 4));
                this.webSocket.onclose = function (event) {
                    console.log("onclose::" + JSON.stringify(event, null, 4));
                };
                this.webSocket.onerror = function (event) {
                    console.log("onerror::" + JSON.stringify(event, null, 4));
                }

            } catch (exception) {
                console.error(exception);
            }
        }

        getStatus() {
            return this.webSocket.readyState;
        }

        send(message) { //將資料傳給Websocket Server

            if (this.webSocket.readyState == WebSocket.OPEN) {
                this.webSocket.send(message);

            } else {
                console.error("webSocket is not open. readyState="
                    + this.webSocket.readyState);
            }
        }

        disconnect() {
            if (this.webSocket.readyState == WebSocket.OPEN) {
                this.webSocket.close();

            } else {
                console.error("webSocket is not open. readyState="
                    + this.webSocket.readyState);
            }
        }
    }

    var client = new WebSocketClient(protocol, hostname, port,
        bossOrderEndpoint);
    client.connect();
});

function loadAccept() {
    $.ajax({
        type: "GET",
        url: showOrder_by_status_waiting_url,
        dataType: "json",
        success: function (data) {
            var allOrder = [];

            for (var i = 0; i < data.length; i++) {
                allOrder.push(data[i]);
                // order
            }

            $('#waitingOrderClass').empty();
            for (var j = 0; j < allOrder.length; j++) {
                var oid = allOrder[j]._id.$oid;
                var psid = allOrder[j].CustomerID;
                var date = allOrder[j].date.split(" ")[0] + "@" + allOrder[j].date.split(" ")[1];
                var type = allOrder[j].Type;
                var number = allOrder[j].Number;
                $('#waitingOrderClass').append(
                    '<div class= "col-md-12" >' +
                    '<div class="card" id="card' + oid + '">' +
                    '<div class="card-header card-header-info"><h4 class="card-title">'
                    + allOrder[j].Type + '訂單 第' + allOrder[j].Number + '號</h4>' +
                    '<p class="card-category">' + allOrder[j].date + '</p>' +
                    '</div><div class = "card-body table-responsive"><table class="table table-hover" id = "table'
                    + oid + '"><thead class="text-info">' +
                    '<tr><th>餐點名稱</th><th>數量</th><th>價錢</th></tr></thead><tbody>'
                );
                for (var p = 0; p < allOrder[j].MyMenu.length; p++) {
                    $('#table' + oid).append(
                        '<tr>' +
                        '<td>' + allOrder[j].MyMenu[p].Name + '</td>' +
                        '<td>' + allOrder[j].MyMenu[p].Amount + '</td>' +
                        '<td>' + allOrder[j].MyMenu[p].Price + '</td>' +
                        '</tr></tbody></table></div>'
                    );
                }

                if (allOrder[j].Note != null) {
                    $('#card' + oid).append(
                        '<div class="card-footer"><div class="stats">' +
                        '<h5>備註：</h5><p>' + allOrder[j].Note + '</p>' +
                        '</div></div><button id= "button' + oid
                        + '" type="submit" class="btn btn-success pull-right"  onClick =waitingClick("'
                        + psid + '","' + date + '","' + type + '","' + number
                        + '","' + oid
                        + '")>開始製作</button><button class="btn btn-danger" data-toggle="modal" data-target="#confirm-delete'
                        + oid + '">刪除訂單</button>'
                    );
                    if (allOrder[j].Type === "外帶") {
                        let target = "#button" + oid;
                        $(target).attr('onClick', false);
                        let dataTarget = "#confirm-predictTime" + oid;
                        $(target).attr('data-toggle', 'modal');
                        $(target).attr('data-target', dataTarget);
                        $('#predictTime').append(
                            '<div class="modal fade" id="confirm-predictTime' + oid
                            + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h5>設定預計取餐時間</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button></div><div class="modal-body"><div id="button-group'
                            + oid
                            + '"><button type="button" class="btn bg-secondary btn-sm">5分</button><button type="button" class="btn bg-secondary btn-sm">10分</button><button type="button" class="btn bg-secondary btn-sm">15分</button><button type="button" class="btn bg-secondary btn-sm">20分</button><button type="button" class="btn bg-secondary btn-sm">25分</button><button type="button" class="btn bg-secondary btn-sm">30分</button></div></div><div class="modal-footer" id="predictTimeTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            +
                            '<button class="btn btn-success btn-ok"  onClick =exWaitingClick("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid
                            + '") data-dismiss="modal" disabled>開始製作</button></div></div></div></div>'
                        );
                    }
                    $('#delete').append(
                        '<div class="modal fade" id="confirm-delete' + oid
                        + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-body"><h3>確定要刪除此餐點嗎？</h3></div><div class="modal-footer" id="deleteTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        +
                        '<button class="btn btn-danger btn-ok"  onClick =deleteOrder("'
                        + psid + '","' + date + '","' + type + '","' + number
                        + '","' + oid
                        + '") data-dismiss="modal">刪除</button></div></div></div></div>'
                    );
                } else {
                    $('#card' + oid).append(
                        '<div class="card-footer"><div class="stats">' +
                        '<h5>備註：</h5><p>無</p>' +
                        '</div></div><button id= "button' + oid
                        + '" type="submit" class="btn btn-success pull-right" onClick =waitingClick("'
                        + psid + '","' + date + '","' + type + '","' + number
                        + '","' + oid
                        + '")>開始製作</button><button class="btn btn-danger" data-toggle="modal" data-target="#confirm-delete'
                        + oid + '">刪除訂單</button>'
                    );
                    if (allOrder[j].Type === "外帶") {
                        let target = "#button" + oid;
                        $(target).attr('onClick', false);
                        let dataTarget = "#confirm-predictTime" + oid;
                        $(target).attr('data-toggle', 'modal');
                        $(target).attr('data-target', dataTarget);
                        $('#predictTime').append(
                            '<div class="modal fade" id="confirm-predictTime' + oid
                            + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h5>設定預計取餐時間</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button></div><div class="modal-body"><div id="button-group'
                            + oid
                            + '"><button type="button" class="btn bg-secondary btn-sm">5分</button><button type="button" class="btn bg-secondary btn-sm">10分</button><button type="button" class="btn bg-secondary btn-sm">15分</button><button type="button" class="btn bg-secondary btn-sm">20分</button><button type="button" class="btn bg-secondary btn-sm">25分</button><button type="button" class="btn bg-secondary btn-sm">30分</button></div></div><div class="modal-footer" id="predictTimeTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            +
                            '<button class="btn btn-success btn-ok"  onClick =exWaitingClick("'
                            + psid + '","' + date + '","' + type + '","' + number
                            + '","' + oid
                            + '") data-dismiss="modal" disabled>開始製作</button></div></div></div></div>'
                        );
                    }
                    $('#delete').append(
                        '<div class="modal fade" id="confirm-delete' + oid
                        + '" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-body"><h3>確定要刪除此餐點嗎？</h3></div><div class="modal-footer" id="deleteTest"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        +
                        '<button class="btn btn-danger btn-ok"  onClick =deleteOrder("'
                        + psid + '","' + date + '","' + type + '","' + number
                        + '","' + oid
                        + '") data-dismiss="modal">刪除</button></div></div></div></div>'
                    );
                }
            }
            changeButton();
        }
    });
}

function changeButton() {

    var targetID = $('#predictTime').find('[id^=button-group]').each(
        function () {
            var oid = this.innerHTML.split("button-group")[1];
            var target = $(this).find('[type=button]');
            $(this).find('[type=button]').each(function () {

                $(this).on('click', function () {
                    var now = this;

                    $(this).siblings().each(function () { // 去除 除了自己之外的同級 的記號
                        if ($(this).hasClass("bg-primary")) {
                            $(this).removeClass("bg-primary");
                            $(this).addClass("bg-secondary");
                        }
                    });
                    // btn bg-secondary btn-sm
                    $(this).removeClass("bg-secondary");
                    $(this).addClass("bg-primary"); // 新的按鈕加記號

                    let predictTimeButton = $(
                        this).parent().parent().parent().find(
                        '.modal-footer button.btn.btn-success.btn-ok');

                    $(predictTimeButton).attr("disabled", false);
                })
            });
            $(this).find('[type=button]').each(function () {
                if ($(this).hasClass("bg-primary")) {
                    let predictTimeButton = $(
                        this).parent().parent().parent().find(
                        '.modal-footer button.btn.btn-success.btn-ok');

                    $(predictTimeButton).attr("disabled", false);
                }
            })
        });
}

function waitingClick(psid, date, type, number, oid) {
    var data123 = {
        "Status": "製作中",
        "oid": oid
    };
    console.log(data123);

    var $target = $("#card" + oid);

    $target.hide('slow', function () {
        $target.remove();
    });

    var jsonData = JSON.stringify(data123);
    $.ajax({
        type: "POST",
        url: setOrderStatusServlet_url,
        dataType: "json",
        data: jsonData,
        success: function (data) {

        },
        error: function () {
            console.log("gg");
        }
    });
    // send message to Nicp
    var message = orderMakingMessage(psid, date, type, number);
    sendMessageToNicp(message);
}

// 外帶訂單 待製作
function exWaitingClick(psid, date, type, number, oid) {
    var data123 = {
        "Status": "製作中",
        "oid": oid
    };
    console.log(data123);

    // send message to Nicp
    let target2 = "#button-group" + oid + " button[type=button]";
    var time;
    $(target2).each(function () {
        if ($(this).hasClass("bg-primary")) {
            time = $(this)[0].innerHTML.split("分")[0];
            var message = exOrderMakingMessage(psid, date, time, type, number);
            sendMessageToNicp(message);
        }
    });

    var $target = $("#card" + oid);
    $target.hide('slow', function () {
        $target.remove();
    });

    var jsonData = JSON.stringify(data123);
    $.ajax({
        type: "POST",
        url: setOrderStatusServlet_url,
        dataType: "json",
        data: jsonData,
        success: function (data) {

        },
        error: function () {
            console.log("gg");
        }
    });

}

function deleteOrder(psid, date, type, number, oid) {
    var data1234 = {
        "oid": oid
    };
    console.log(oid);
    console.log(data1234);
    var url = deleteOrderServlet_url;
    var json_Data2 = JSON.stringify(data1234);
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        data: json_Data2,
        success: function (data) {
            console.log("刪除成功");
            var $target2 = $("#card" + oid);
            $target2.hide('slow', function () {
                $target2.remove();
            });
        },
        error: function () {
            console.log("gg");
        }
    });
    // send message to Nicp
    var message = orderRejectMessage(psid, date, type, number);
    sendMessageToNicp(message);
}

$(document).ready(function () {

});

function listenCheclbox() {
    $("#makingOrderClass div[id^='Makingcard']").each(function () {
        var tmp = this;
        let checkboxs = $(this).find("input[type=checkbox]");
        checkboxs.change(function () {
            var maked = true;
            for (var i = 0; i < checkboxs.length; i++) {
                if (!checkboxs[i].checked) {
                    maked = false;
                }
            }
            var oid = $(tmp).attr("id").toString().split("Makingcard")[1];
            var target = "#button" + oid;
            if (maked) // 如果該訂單的checkbox 都勾選了
            {
                $(target).attr("disabled", false);
            } else {
                $(target).attr("disabled", true);
            }
        });
    });
}

function largeFormCheck(itemNumberForTr) {

    let target = "#MakingTr" + itemNumberForTr;
    let isClick = $(target).find("input[type=checkbox]")[0].checked;
    if (isClick) {
        $(target).find("input[type=checkbox]").prop("checked",
            false).change();
    } else {
        $(target).find("input[type=checkbox]").prop("checked", true).change();
    }

}

function makingClick(psid, date, type, number, oid) {
    var data123 = {
        "Status": "已完成",
        "oid": oid
    };
    console.log(data123);
    var jsonData = JSON.stringify(data123);
    $.ajax({
        type: "POST",
        url: setOrderStatusServlet_url,
        dataType: "json",
        data: jsonData,
        success: function (data) {
            var $target = $("#card" + oid);
            $target.hide('slow', function () {
                $target.remove();
            });

        },
        error: function () {
            console.log("gg");
        }
    });
    // send message to Nicp
    var message = orderFinishMessage(psid, date, type, number);
    sendMessageToNicp(message);
}

function finishedClick(psid, date, type, number, oid, people) {
    var data123 = {
        "Status": "已結帳",
        "oid": oid,
        "People": people
    };
    console.log(data123);
    var jsonData = JSON.stringify(data123);
    $.ajax({
        type: "POST",
        url: setOrderStatusServlet_url,
        dataType: "json",
        data: jsonData,
        success: function (data) {
            var $target = $("#card" + oid);
            $target.hide('slow', function () {
                $target.remove();
            });

        },
        error: function () {
            console.log("gg");
        }
    });
    // send message to Nicp
    var message = orderPaidMessage(psid);
    sendMessageToNicp(message);
}

function playAudio(text) {
    var audio = document.getElementById("audio1"); // 取得 audio 控制項
    audio.setAttribute("src",
        "http://translate.google.com/translate_tts?ie=utf-8&tl=zh&q=" + text
        + "&client=tw-ob"); // 設定語音為 google TTS。
    audio.addEventListener('ended', function () {
        this.currentTime = 0;
    }, false); // 當播放完畢，強制回到開頭。
    audio.play(); // 播放語音。
    console.log("我來了");
}

function gotToTargetOrder(oid) {
    let targetID = "#card" + oid;
    let target = $(targetID);
    if (target.length) {
        let target_top = target.offset().top;
        let offsetHeight = document.getElementById(
            "waitingOrderClass").scrollTop;
        let goalTop = target_top - offsetHeight;
        console.log(offsetHeight);

        $('#orderStatusBar').find('.nav-link').removeClass('active');
        $('#waiting').find('.nav-link').addClass('active');
        $(".tab_content").hide();
        $("#waitingOrderClass").show();
        $('#waitingOrderClass').animate({scrollTop: goalTop}, 800,
            function () {
            });
    }
}

$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: showMessageServlet_url,
        dataType: "json",
        success: function (data) {
            var showDialogue = $("#showDialogue");
            var messageArray = data;
            for (var j = 0; j < messageArray.length; j++) {
                messageArray[j].Date = new Date(messageArray[j].Date);
            }
            messageArray.sort(function (a, b) {
                return a.Date > b.Date ? 1 : -1;
            });
            for (var i = 0; i < messageArray.length; i++) {
                if (messageArray[i].Boss == null) {
                    var msg = messageArray[i].Chatbot;

                    showDialogue.append(
                        '<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">機器人：<br/>'
                        + msg + '</div>');

                } else {
                    var msgboss = data[i].Boss;
                    showDialogue.append(
                        '<div class="card text-dark bg-light m-2" style="line-height:20px;padding:3px;float:right;max-width: 70%;text-align:left">老闆：<br/>'
                        + msgboss + '</div>');
                }
            }
            console.log("opop2");
        },
        error: function () {
            console.log("GG");
        }

    });
});
$(document).ready(function () {

    $('#smallTwo').click(function (e) {
        var showDialogue = $("#showDialogue");
        console.log("opop");
        showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
    });

    class WebSocketClient {

        constructor(protocol, hostname, port, endpoint) {

            this.webSocket = null;
            this.protocol = protocol;
            this.hostname = hostname;
            this.port = port;
            this.endpoint = endpoint;
        }

        getServerUrl() {
            return this.protocol + "://" + this.hostname + ":" + this.port
                + this.endpoint;
        }

        connect() {
            try {
                this.webSocket = new ReconnectingWebSocket(this.getServerUrl());

                //
                // Implement WebSocket event handlers!
                //
                this.webSocket.onopen = function (event) {
                    console.log("聊天室onopen::" + JSON.stringify(event, null, 4));
                };

                // 接收Websocket Server傳來的資料----> 訂單json <-----------
                this.webSocket.onmessage = function (event) {
                    var msg = event.data;
                    var showDialogue = $("#showDialogue");
                    showDialogue.append(
                        '<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">機器人：<br/>'
                        + msg + '</div>');
                    playAudio(msg);
                    //scrollbar置底
                    showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
                    console.log("onmessage::" + JSON.stringify(msg, null, 4));
                    $("#chatBox").collapse("show");
                };
                this.webSocket.onclose = function (event) {
                    console.log("onclose::" + JSON.stringify(event, null, 4));
                };
                this.webSocket.onerror = function (event) {
                    console.log("onerror::" + JSON.stringify(event, null, 4));
                }

            } catch (exception) {
                console.error(exception);
            }
        }

        getStatus() {
            return this.webSocket.readyState;
        }

        send(message) { //將資料傳給Websocket Server

            if (this.webSocket.readyState == WebSocket.OPEN) {
                this.webSocket.send(message);

            } else {
                console.error("webSocket is not open. readyState="
                    + this.webSocket.readyState);
            }
        }

        disconnect() {
            if (this.webSocket.readyState == WebSocket.OPEN) {
                this.webSocket.close();

            } else {
                console.error("webSocket is not open. readyState="
                    + this.webSocket.readyState);
            }
        }
    }

    var client = new WebSocketClient(protocol, hostname, port, endpoint);
    client.connect();

    var sendMessageInput = $("#sendMessageInput");

    function submit() {
        var yoursMessage = sendMessageInput.val();
        var showDialogue = $("#showDialogue");
        showDialogue.append(
            '<div class="card text-dark bg-light m-2" style="line-height:20px;padding:3px;float:right;max-width: 70%;text-align:left">老闆：<br/>'
            + yoursMessage + '</div>');
        client.send(yoursMessage);
        showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
        sendMessageInput.val("");
    }

    $("#submitBtn").click(function () {
        submit();
    });

    // 按下enter就能送出
    sendMessageInput.keypress(function (e) {
        code = (e.keyCode ? e.keyCode : e.which);
        if (code === 13) {
            submit();
        }
    });

    //語音辨識
    var micBtn = $("#micBtn");
    var micIcon = document.getElementById("micIcon"); // 麥克風按鈕icon
    var final_transcript = ''; // 最終的辨識訊息的變數
    var recognizing = false; // 是否辨識中

    micBtn.click(function () {
        if (recognizing) { // 如果正在辨識，則停止。
            recognition.stop();
        } else { // 否則就開始辨識
            sendMessageInput.val(''); // 清除最終的辨識訊息
            final_transcript = ''; // 最終的辨識訊息變數
            recognition.lang = "cmn-Hant-TW"; // 設定辨識語言
            recognition.start(); // 開始辨識
        }
    });

    if (!('webkitSpeechRecognition' in window)) { // 如果找不到 window.webkitSpeechRecognition 這個屬性
        // 就是不支援語音辨識，要求使用者更新瀏覽器。
        alert("wrong browser");
    } else {
        var recognition = new webkitSpeechRecognition(); // 建立語音辨識物件 webkitSpeechRecognition
        recognition.continuous = true; // 設定連續辨識模式
        recognition.interimResults = true; // 設定輸出中先結果。

        recognition.onstart = function () { // 開始辨識
            recognizing = true; // 設定為辨識中
            micIcon.innerHTML = "settings_voice"; // 辨識中...按鈕「停止使用」。
            console.log("here we come");
        };

        recognition.onend = function () { // 辨識完成
            recognizing = false; // 設定為「非辨識中」
            micIcon.innerHTML = "mic"; // 辨識完成...按鈕改為「開始辨識」。
            console.log("here we go");
        };

        recognition.onresult = function (event) { // 辨識有任何結果時
            for (var i = event.resultIndex; i < event.results.length; ++i) { // 對於每一個辨識結果
                if (event.results[i].isFinal) { // 如果是最終結果
                    final_transcript += event.results[i][0].transcript; // 將其加入最終結果中
                }
            }
            if (final_transcript.trim().length > 0) // 如果有最終辨識文字
            {
                sendMessageInput.val(final_transcript);
            } // 顯示最終辨識文字
        };
    }
});

$(document).ready(function () {
    $('#logout').click(function (e) {
        $.ajax({
            type: "GET",
            url: logoutServlet_url,
            // dataType: "json",
            success: function (data) {
                alert("登出成功");
            }
        });
    });
});

// messenger begin
function sendMessageToNicp(message) {
    console.log(message);
    let url = orderStatusChange_url;
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        data: message,
        success: function (data) {
            console.log("POST to Nicp success");
            console.log(data);
        },
        error: function (data) {
            console.log("POST to Nicp error");
            console.log(data);
        }
    });
}

function orderRejectMessage(psid, date, type, number) {
    var nDate = date.split("@")[0] + " " + date.split("@")[1];
    var str;
    if (type === "內用") {
        str = `${type} ${number}桌的訂單\n因為店內繁忙\n所以被老闆取消囉！！`;
    } else {
        str = `${type} ${number}的訂單\n因為店內繁忙\n所以被老闆取消囉！！`;
    }
    var message = {
        "userID": [
            "2227048243983599"
        ],
        "message": "您的訂單",
        "result": {},
        "config": {},
        "originalPlatform": "bossWebUI"
    };
    var idArray = [];
    idArray.push(psid);
    message.userID = idArray;
    message.message = str;
    return message;
}

function orderMakingMessage(psid, date, type, number) {
    var nDate = date.split("@")[0] + " " + date.split("@")[1];
    var str;
    if (type === "內用") {
        str = `${type} ${number}桌的訂單已經開始製作了喔～`;
    } else {
        str = `${type} ${number}的訂單已經開始製作了喔～`;
    }
    var message = {
        "userID": [
            "2227048243983599"
        ],
        "message": "您的訂單",
        "result": {},
        "config": {},
        "originalPlatform": "bossWebUI"
    };
    var idArray = [];
    idArray.push(psid);
    message.userID = idArray;
    message.message = str;
    return message;
}

function exOrderMakingMessage(psid, date, time, type, number) {
    var nDate = date.split("@")[0] + " " + date.split("@")[1];
    var str;
    if (type === "內用") {
        str = `${type} ${number}桌的訂單已經開始製作了喔～\n預計在${time}分鐘內完成`;
    } else {
        str = `${type} ${number}的訂單已經開始製作了喔～\n預計在${time}分鐘內完成`;
    }
    var message = {
        "userID": [
            "2227048243983599"
        ],
        "message": "您的訂單",
        "result": {},
        "config": {},
        "originalPlatform": "bossWebUI"
    };
    var idArray = [];
    idArray.push(psid);
    message.userID = idArray;
    message.message = str;
    return message;
}

function orderFinishMessage(psid, date, type, number) {
    var nDate = date.split("@")[0] + " " + date.split("@")[1];
    var str;
    if (type === "內用") {
        str = `${type} ${number}桌的訂單已經完成了喔～`;
    } else {
        str = `${type} ${number}的訂單已經完成了喔～`;
    }
    var message = {
        "userID": [
            "2227048243983599"
        ],
        "message": "您的訂單",
        "result": {},
        "config": {},
        "originalPlatform": "bossWebUI"
    };
    var idArray = [];
    idArray.push(psid);
    message.userID = idArray;
    message.message = str;
    return message;
}

function orderPaidMessage(psid) {
    var str = "謝謝您的蒞臨!!歡迎下次再來～";
    var message = {
        "userID": [
            "2227048243983599"
        ],
        "message": "您的訂單",
        "result": {},
        "config": {},
        "originalPlatform": "bossWebUI"
    };
    var idArray = [];
    idArray.push(psid);
    message.userID = idArray;
    message.message = str;
    return message;
}

//messenger end
