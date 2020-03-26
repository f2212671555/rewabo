let protocol;
let hostname;
let port;
let bossOrderEndpoint;
let endpoint;

let rewabo_base_url;
let menuCategoryAPI_url;
let menuItemAPI_url;
let addPushInfoServlet_url;
let notificationAPI_url;
let customerServiceServlet_url;
let deletePushInfoServlet_url;
let pushInfoServlet_url;
let notificationSettingAPI_url;
let showMessageServlet_url;
let intent_list_url;
let intent_create_url;
let intent_delete_url;
let intent_addTrainingPhrases_url;
$(document).ready(function() {
    $.ajax({
        async: false,
        url: "static/json/settings.json",
        type: "GET",
        dataType: "json",
        success: function(data) {
            console.log(data)
            protocol = data.rewabo.protocol;
            hostname = data.rewabo.hostname;
            port = data.rewabo.port;
            bossOrderEndpoint = data.rewabo.urls.endpoint.bossOrderEndpoint;
            endpoint = data.rewabo.urls.endpoint.endpoint;

            rewabo_base_url = data.rewabo.urls.baseUrl;
            menuCategoryAPI_url = rewabo_base_url +
                data.rewabo.urls.services.menuCategoryAPI_url;
            menuItemAPI_url = rewabo_base_url +
                data.rewabo.urls.services.menuItemAPI_url;
            addPushInfoServlet_url = rewabo_base_url +
                data.rewabo.urls.services.addPushInfoServlet_url;
            notificationAPI_url = rewabo_base_url +
                data.rewabo.urls.services.notificationAPI_url;
            customerServiceServlet_url = rewabo_base_url +
                data.rewabo.urls.services.customerServiceServlet_url;
            deletePushInfoServlet_url = rewabo_base_url +
                data.rewabo.urls.services.deletePushInfoServlet_url;
            pushInfoServlet_url = rewabo_base_url +
                data.rewabo.urls.services.pushInfoServlet_url;
            notificationSettingAPI_url = rewabo_base_url +
                data.rewabo.urls.services.notificationSettingAPI_url;
            showMessageServlet_url = rewabo_base_url +
                data.rewabo.urls.services.showMessageServlet_url;
            intent_list_url = rewabo_base_url +
                data.rewabo.urls.dialogFlow.intent_list_url;
            intent_create_url = rewabo_base_url +
                data.rewabo.urls.dialogFlow.intent_create_url;
            intent_delete_url = rewabo_base_url +
                data.rewabo.urls.dialogFlow.intent_delete_url;
            intent_addTrainingPhrases_url = rewabo_base_url +
                data.rewabo.urls.dialogFlow.intent_addTrainingPhrases_url;



        },
        error: function() {

        }
    });
});
var itemNum = 0;
$(document).ready(function() {
    $.ajax({
        //抓菜單種類
        type: "GET",
        url: menuCategoryAPI_url,
        dataType: "json",
        success: function(data) {
            console.log(data);
            for (var q = 0; q < data.length; q++) {

                var foodClassname = data[q].ClassName;

                // nav bar class name
                if (q === 0) {
                    $('#menuClass').append(
                        '<li class="nav-item">' +
                        '<a class="nav-link active show" href="#' + foodClassname +
                        '" data-toggle="tab"><h4>' + foodClassname + '</h4></a>' +
                        '<div class="ripple-container"></div>' +
                        '</li>'
                    );
                } else {
                    $('#menuClass').append(
                        '<li class="nav-item">' +
                        '<a class="nav-link" href="#' + foodClassname +
                        '" data-toggle="tab"><h4>' + foodClassname + '</h4></a>' +
                        '<div class="ripple-container"></div>' +
                        '</li>'
                    );
                }

                // menu total class name
                if (q === 0) {
                    $("#center").append(
                        '<div class="row tab-pane active show" id="' + foodClassname +
                        '"></div>'
                    );
                } else {
                    $("#center").append(
                        '<div class="row tab-pane" id="' + foodClassname +
                        '"></div>'
                    );
                }
                // set food of each class
            }

            $('#menuClass').append(
                '<li class="nav-item">' +
                '<a class="nav-link" href="#" data-toggle="modal" data-target="#exampleModal"><h4>新增餐點種類</h4></a>' +
                '<div class="ripple-container"></div>' +
                '</li>'
            );
        },
        error: function() {
            console.log("can not GET");
        }
    });
});

$(document).ready(function() {
    $.ajax({
        //抓菜單
        type: "GET",
        url: menuItemAPI_url,
        dataType: "json",
        success: function(data) {
            console.log(data);
            for (var j = 0; j < data.length; j++) {
                // food
                var food = data[j];
                // food class name
                var className = food.ClassName;
                // food name
                var name = food.Name;
                // food price
                var price = food.Price;
                // food img url
                var imgUrl = food.url;
                // set each food id
                var goal = "#" + className;

                if (className !== "套餐") {

                    $(goal).append(
                        '<div class= "col-md-12" id="' + name +
                        '"><button type="button" class="close" aria-label="Close" onclick="deleteMenu(\'' +
                        name +
                        '\')" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>' +
                        '<div class="card md-3">' +
                        '<div class="row no-gutters">' +
                        '<div class="col-md-4">' +
                        '<img src="' + imgUrl +
                        '" class="card-img img-fluid img-thumbnail" alt="' + name +
                        '">' +
                        '</div>' +
                        '<div class="col-md-8">' +
                        '<div class="card-body">' +
                        '<h3 class="card-title">' + name + '</h3>' +
                        '<h4 class="card-text">' + "NT$" + price + '</h4>' +
                        ' <a  id = "sold' + name + '"href="#" class="btn btn-outline-secondary" onclick="soldOutStatus(\'' +
                        name +
                        '\')">向顧客顯示售完</a></div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                    );
                    if (food.SoldOut == true) {
                        $("#sold" + name).text("向顧客顯示銷售中");
                    }
                } else {

                    $(goal).append(
                        '<div class= "col-md-12" id="' + name +
                        '"><button type="button" class="close" aria-label="Close" onclick="deleteMenu(\'' +
                        name +
                        '\')" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>' +
                        '<div class="card md-3">' +
                        '<div class="row no-gutters">' +
                        '<div class="col-md-4">' +
                        '<img src="' + imgUrl +
                        '" class="card-img img-fluid img-thumbnail" alt="' + name +
                        '">' +
                        '</div>' +
                        '<div class="col-md-8">' +
                        '<div class="card-body" id = "set' + name + '">' +
                        '<h3 class="card-title">' + name + '</h3>' +
                        '<h4 class="card-text">' + "NT$" + price + '</h4><br><br>' +
                        ' <a  id = "sold' + name + '"href="#" class="btn btn-outline-secondary" onclick="soldOutStatus(\'' +
                        name +
                        '\')">售完</a></div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                    );
                    if (food.SoldOut == true) {
                        $("#sold" + name).text("取消售完");
                    }

                    var setCollect = "";
                    for (var t = 0; t < food.Item.length; t++) {
                        setCollect += food.Item[t];
                        if (t < food.Item.length - 1) {
                            setCollect += ",";
                        }
                    }
                    $("#set" + name).append('<h5>' + setCollect + '</h5>');
                }

            }
        },
        error: function() {
            console.log("can not GET");
        }
    });
});

function soldOutStatus(name) {
    var answer = confirm("確定要更動" + name + "的狀態嗎？");
    if (answer) {
        var soldOutItemName = name;
        var soldOutItem = new FormData(); //建構new FormData()
        soldOutItem.append("Name", soldOutItemName);
        soldOutItem.append("action", "SoldOut");

        $.ajax({
            url: menuItemAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: soldOutItem, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("更動成功！");
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }
        });
    }

}
$(document).ready(function() {
    //新增種類
    $('#upload').click(function(e) {
        var newClassPic = $('#myFile').prop('files')[0]; //取得上傳檔案屬性
        var newClassName = $('#myText').val();
        var newClassType = $("#addTypeSelect :selected").text();
        var newClass = new FormData(); //建構new FormData()
        newClass.append('file', newClassPic); //吧物件加到file後面
        newClass.append("ClassName", newClassName);
        newClass.append("Type", newClassType);
        newClass.append("action", "addClass");
        for (var pair of newClass.entries()) {
            console.log(pair[0] + ', ' + pair[1]);
        }
        $.ajax({
            url: menuCategoryAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: newClass, //data只能指定單一物件
            type: 'post',
            success: function(data) {
                alert("新增成功！");
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }
        });
    });
});

$(document).ready(function() {
    $.ajax({
        //抓餐點種類select
        type: "GET",
        url: menuCategoryAPI_url,
        dataType: "json",
        success: function(data) {
            var foodClassnameArray = [];
            for (var i = 0; i < data.length; i++) {
                // food
                var food = data[i];
                // food class name
                var className = food.ClassName;
                foodClassnameArray.push(className);
            }
            // filt same class name
            var foodClassnames = foodClassnameArray.filter(
                function(element, index, arr) {
                    return arr.indexOf(element) === index;
                });

            for (var q = 0; q < foodClassnames.length; q++) {
                var foodClassname = foodClassnames[q];
                if (foodClassname != "套餐") {
                    $('#addClassSelect').append(
                        '<option value="na' + foodClassname + '" >' + foodClassname +
                        '</option>'
                    );
                }
                $('#deleteClassSelect').append(
                    '<option value="na' + foodClassname + '" >' + foodClassname +
                    '</option>'
                );
                $('#editClassSelect').append(
                    '<option value="na' + foodClassname + '" >' + foodClassname +
                    '</option>'
                );
                $('#selectNewKind').append(
                    '<option value="na' + foodClassname + '" >' + foodClassname +
                    '</option>'
                );
            }
        }
    });
});

$(document).ready(function() {
    $.ajax({
        //抓餐點select
        type: "GET",
        url: menuItemAPI_url,
        dataType: "json",
        success: function(data) {
            var foodClassnameArray = [];
            for (var i = 0; i < data.length; i++) {
                // food
                var food = data[i];
                // food class name
                var className = food.ClassName;

                foodClassnameArray.push(className);
            }

            for (var j = 0; j < data.length; j++) {

                // food
                var food2 = data[j];

                // food name
                var name2 = food2.Name;
                $('#notiItemSelect').append(
                    '<option value="na' + name2 + '" >' + name2 + '</option>'
                );
                $('#regNotiItemSelect').append(
                    '<option value="na' + name2 + '" >' + name2 + '</option>'
                );
                if (food2.ClassName != "套餐") {
                    $('#selectChangeMenu').append(
                        '<option value="' + name2 + '" >' + name2 + '</option>'
                    );
                }
            }
        },
        error: function() {
            console.log("can not GET");
        }
    });
});
$(document).ready(function() {
    //新增餐點
    $('#uploadNew').click(function(e) {
        var newItemPic = $('#newItemPic').prop('files')[0]; //取得上傳檔案屬性
        var newItemName = $('#newItemName').val();
        var newItemPrice = $('#newItemPrice').val();
        var newItemClass = $("#addClassSelect :selected").text();
        var newItem = new FormData();
        newItem.append('file', newItemPic); //吧物件加到file後面
        newItem.append("Name", newItemName);
        newItem.append("Price", newItemPrice);
        newItem.append("ClassName", newItemClass);
        newItem.append("_id", "3a45sdqwegjeu");
        newItem.append("action", "addItem");

        $.ajax({
            url: menuItemAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: newItem, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("新增成功！");
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }

        });
    });
});
$(document).ready(function() {
    //刪除種類
    $('#deleteNew').click(function(e) {
        var deleteClassName = $("#deleteClassSelect :selected").text();
        var deleteClass = new FormData(); //建構new FormData()
        deleteClass.append("ClassName", deleteClassName);
        deleteClass.append("action", "deleteClass");
        console.log(deleteClass);
        $.ajax({
            url: menuCategoryAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: deleteClass, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("刪除成功！");
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }

        });
    });
});

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 +
        strDate +
        " " + date.getHours() + seperator2 + date.getMinutes() +
        seperator2 + date.getSeconds();
    return currentdate;
}

$(document).ready(function() {
    //  新增即時推播餐點
    $('#notiNow').click(function(e) {
        var kindData = $("#notiItemSelect :selected").text();
        var notiPrice = $('#notiPrice').val();
        var notiText = $('#notiText').val();
        var notiData = new FormData(); //建構new FormData()
        notiData.append("Name", kindData);
        notiData.append("Price", notiPrice);
        notiData.append("PushInfo", notiText);
        notiData.append("date", getNowFormatDate());
        notiData.append("_id", "3a45sdqwegjeu");
        notiData.append("action", "addNoti");
        console.log(notiData);
        $.ajax({
            url: addPushInfoServlet_url,
            cache: false,
            contentType: false,
            processData: false,
            data: notiData, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("新增成功！");
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }

        });
    });
});

$(document).ready(function() {
    //  新增定期推播餐點
    $('#newRegNoti').click(function(e) {
        var kindData = $("#regNotiItemSelect :selected").text();
        var regNotiPrice = $('#regNotiPrice').val();
        var regNotiText = $('#regNotiText').val();
        var regNotiDays = $('#regNotiDays').val();
        var date = getNowFormatDate();
        var regNoti = new FormData(); //建構new FormData()
        regNoti.append("Name", kindData);
        regNoti.append("Price", regNotiPrice);
        regNoti.append("PushInfo", regNotiText);
        regNoti.append("Time", regNotiDays);
        regNoti.append("date", getNowFormatDate());
        regNoti.append("action", "新增");
        regNoti.append("oid", "123456");
        regNoti.append("type", "定期推播");
        console.log(regNoti);
        $.ajax({
            url: notificationAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: regNoti, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("新增成功！");
                console.log(data);
                window.location.reload();
            },
            error: function(data) {
                console.log(data);
                alert("失敗");
                alert(data);
            }

        });
    });
});

function deleteMenu(name) {
    var answer = confirm("確定要刪除" + name + "嗎？");
    if (answer) {
        var deleteItemName = name;
        var deleteItem = new FormData(); //建構new FormData()
        deleteItem.append("Name", deleteItemName);
        deleteItem.append("action", "deleteItem");

        $.ajax({
            url: menuItemAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: deleteItem, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("刪除成功！");
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }
        });
    }
}

$(document).ready(function() {
    $('#changeNewKind').click(function(e) {
        var classname = $("#editClassSelect :selected").text();
        var newclassname = $("#newClassName").val();
        var form_dataNew = new FormData();
        form_dataNew.append("ClassName", classname);
        form_dataNew.append("NewClassName", newclassname);
        form_dataNew.append("action", "updateClass");
        for (var pair of form_dataNew.entries()) {
            console.log(pair[0] + ', ' + pair[1]);
        }

        $.ajax({
            url: menuCategoryAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: form_dataNew, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("更新成功！");
                console.log(data);
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }

        });
    });
});
$(document).ready(function() {
    var counter = 0;
    var option;
    $.ajax({
        type: "GET",
        url: menuItemAPI_url,
        dataType: "json",
        success: function(data) {
            var foodClassnameArray = [];
            for (var i = 0; i < data.length; i++) {
                // food
                var food = data[i];
                // food class name
                var className = food.ClassName;

                foodClassnameArray.push(className);
            }
            option = "";

            for (var j = 0; j < data.length; j++) {

                // food
                var food2 = data[j];
                if (food2.ClassName != "套餐") {
                    // food name
                    var name2 = food2.Name;

                    $('#selectname50').append(
                        '<option value="na' + name2 + '" >' + name2 + '</option>'
                    );
                    option += '<option value="na' + name2 + '" >' + name2 +
                        '</option>';
                }
            }
        },
        error: function() {
            console.log("can not GET");
        }
    });
    $("#addrow").on("click", function() {
        $.ajax({
            type: "GET",
            url: menuItemAPI_url,
            dataType: "json",
            success: function(data) {
                var foodClassnameArray = [];
                for (var i = 0; i < data.length; i++) {
                    // food
                    var food = data[i];
                    // food class name
                    var className = food.ClassName;

                    foodClassnameArray.push(className);
                }
                option = "";
                for (var j = 0; j < data.length; j++) {

                    // food
                    var food2 = data[j];
                    if (food2.ClassName != "套餐") {
                        // food name
                        var name2 = food2.Name;

                        option += '<option value="na' + name2 + '" >' + name2 +
                            '</option>';
                    }
                }
                var newRow = $("<tr>");
                var cols = "";
                itemNum++;
                cols += '<td><select id="selectname5' + itemNum + '" name = "name' +
                    counter + '" class="form-control">' + option +
                    '</select></td>';
                cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger "  value="Delete"></td>';
                newRow.append(cols);
                $("table.order-list").append(newRow);
                counter++;
            },
            error: function() {
                console.log("can not GET");
            }
        });
    });
    $("table.order-list").on("click", ".ibtnDel", function(event) {
        $(this).closest("tr").remove();
        counter -= 1;
    });
});

function calculateRow(row) {
    var price = +row.find('input[name^="price"]').val();

}

function calculateGrandTotal() {
    var grandTotal = 0;
    $("table.order-list").find('input[name^="price"]').each(function() {
        grandTotal += +$(this).val();
    });
    $("#grandtotal").text(grandTotal.toFixed(2));
}

$(document).ready(function() {
    $('#addNewKind').click(function(e) {
        var item = [];
        for (var i = 0; i <= itemNum; i++) {
            item[i] = $("#selectname5" + i + " :selected").text();
        }
        var newSet = $("#newSet").val();
        var newSetPrice = $("#newSetPrice").val();
        var newSetFile = $('#newSetFile').prop('files')[0];
        var SetForm = new FormData(); //建構new FormData()
        if (newSetFile != null && newSet != null && newSetPrice != null) {
            SetForm.append("Name", newSet);
            SetForm.append("Price", newSetPrice);
            SetForm.append("File", newSetFile);
            SetForm.append("itemNum", itemNum.toString());
            SetForm.append("action", "addSet");
            for (var j = 0; j <= itemNum; j++) {
                var v = "Item[" + j + "]";
                SetForm.append(v, item[j]);
            }
            $.ajax({
                url: menuItemAPI_url,
                cache: false,
                contentType: false,
                processData: false,
                data: SetForm, //data只能指定單一物件
                type: 'Post',

                success: function(data) {
                    alert("更新成功！");
                    window.location.reload();
                    //window.location.reload();
                },
                error: function(data) {
                    alert("失敗");
                }

            });
        } else {
            alert("請輸入所有表格及圖片");
        }
    });
});
$(document).ready(function() {
    class WebSocketClient {

        constructor(protocol, hostname, port, endpoint) {

            this.webSocket = null;

            this.protocol = protocol;
            this.hostname = hostname;
            this.port = port;
            this.endpoint = endpoint;
        }

        getServerUrl() {
            return this.protocol + "://" + this.hostname + ":" + this.port +
                this.endpoint;
        }

        connect() {
            try {
                this.webSocket = new ReconnectingWebSocket(this.getServerUrl());

                //
                // Implement WebSocket event handlers!
                //
                this.webSocket.onopen = function(event) {
                    console.log("onopen::" + JSON.stringify(event, null, 4));
                };

                // 接收Websocket Server傳來的資料----> 訂單json <-----------
                this.webSocket.onmessage = function(event) {
                    var app = event.data;
                    var msg = JSON.parse(app)[0];
                    // get order json object!!
                    alert("您有新訂單");

                    // 機器人也會跳通知
                    var showDialogue = $("#showDialogue");
                    showDialogue.append(
                        '<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">您有新訂單 ' +
                        msg.Type + msg.Number + '</div>');
                    playAudio("您有新訂單");
                    //scrollbar置底
                    showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
                    $("#chatBox").collapse("show");

                    var oid = msg._id.$oid;
                    $('#orderClass').append(
                        '<div class= "col-md-12" >' +
                        '<div class="card" id="card' + oid + '">' +
                        '<div class="card-header card-header-info"><h4 class="card-title">' +
                        msg.Type + '訂單 第' + msg.Number + '號</h4>' +
                        '<p class="card-category">' + msg.date + '</p>' +
                        '</div><div class = "card-body table-responsive"><table class="table table-hover" id = "table' +
                        oid + '"><thead class="text-info">' +
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
                            '</div></div><button id= "button' + oid +
                            '" type="submit" class="btn btn-success pull-right" onclick ="waitingClick(\'' +
                            oid + '\')">開始製作</button>'
                        );
                    } else {
                        $('#card' + oid).append(
                            '<div class="card-footer"><div class="stats">' +
                            '<h5>備註：</h5><p>無</p>' +
                            '</div></div><button id= "button' + oid +
                            '" type="submit" class="btn btn-success pull-right" onclick ="waitingClick(\'' +
                            oid + '\')">開始製作</button>'
                        );
                    }

                    // order

                };
                // console.log("onmessage::" + JSON.stringify(msg, null, 4));
                this.webSocket.onclose = function(event) {
                    console.log("onclose::" + JSON.stringify(event, null, 4));
                };
                this.webSocket.onerror = function(event) {
                    console.log("onerror::" + JSON.stringify(event, null, 4));
                };

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
                console.error("webSocket is not open. readyState=" +
                    this.webSocket.readyState);
            }
        }

        disconnect() {
            if (this.webSocket.readyState == WebSocket.OPEN) {
                this.webSocket.close();

            } else {
                console.error("webSocket is not open. readyState=" +
                    this.webSocket.readyState);
            }
        }
    }

    var client = new WebSocketClient(protocol, hostname, port,
        bossOrderEndpoint);
    client.connect();
});

function playAudio(text) {
    var audio = document.getElementById("audio1"); // 取得 audio 控制項
    audio.setAttribute("src",
        "http://translate.google.com/translate_tts?ie=utf-8&tl=zh&q=" + text +
        "&client=tw-ob"); // 設定語音為 google TTS。
    audio.addEventListener('ended', function() {
        this.currentTime = 0;
    }, false); // 當播放完畢，強制回到開頭。
    audio.play(); // 播放語音。
    console.log("我來了");
}

$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: showMessageServlet_url,
        dataType: "json",
        success: function(data) {
            var showDialogue = $("#showDialogue");
            var messageArray = data;
            for (var j = 0; j < messageArray.length; j++) {
                messageArray[j].Date = new Date(messageArray[j].Date);
            }
            messageArray.sort(function(a, b) {
                return a.Date > b.Date ? 1 : -1;
            });
            for (var i = 0; i < messageArray.length; i++) {
                if (messageArray[i].Boss == null) {
                    var msg = messageArray[i].Chatbot;

                    showDialogue.append(
                        '<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">機器人：<br/>' +
                        msg + '</div>');

                } else {
                    var msgboss = data[i].Boss;
                    showDialogue.append(
                        '<div class="card text-dark bg-light m-2" style="line-height:20px;padding:3px;float:right;max-width: 70%;text-align:left">老闆：<br/>' +
                        msgboss + '</div>');
                }
            }
            console.log("opop2");
        },
        error: function() {
            console.log("GG");
        }

    });
});
$(document).ready(function() {

    $('#smallTwo').click(function(e) {
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
            return this.protocol + "://" + this.hostname + ":" + this.port +
                this.endpoint;
        }

        connect() {
            try {
                this.webSocket = new ReconnectingWebSocket(this.getServerUrl());

                //
                // Implement WebSocket event handlers!
                //
                this.webSocket.onopen = function(event) {
                    console.log("聊天室onopen::" + JSON.stringify(event, null, 4));
                };

                // 接收Websocket Server傳來的資料----> 訂單json <-----------
                this.webSocket.onmessage = function(event) {
                    var msg = event.data;
                    var showDialogue = $("#showDialogue");
                    showDialogue.append(
                        '<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">機器人：<br/>' +
                        msg + '</div>');
                    playAudio(msg);
                    //scrollbar置底
                    showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
                    console.log("onmessage::" + JSON.stringify(msg, null, 4));
                    $("#chatBox").collapse("show");
                };
                this.webSocket.onclose = function(event) {
                    console.log("onclose::" + JSON.stringify(event, null, 4));
                };
                this.webSocket.onerror = function(event) {
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
                console.error("webSocket is not open. readyState=" +
                    this.webSocket.readyState);
            }
        }

        disconnect() {
            if (this.webSocket.readyState == WebSocket.OPEN) {
                this.webSocket.close();

            } else {
                console.error("webSocket is not open. readyState=" +
                    this.webSocket.readyState);
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
            '<div class="card text-dark bg-light m-2" style="line-height:20px;padding:3px;float:right;max-width: 70%;text-align:left">老闆：<br/>' +
            yoursMessage + '</div>');
        client.send(yoursMessage);
        showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
        sendMessageInput.val("");
    }

    $("#submitBtn").click(function() {
        submit();
    });

    // 按下enter就能送出
    sendMessageInput.keypress(function(e) {
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

    micBtn.click(function() {
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

        recognition.onstart = function() { // 開始辨識
            recognizing = true; // 設定為辨識中
            micIcon.innerHTML = "settings_voice"; // 辨識中...按鈕「停止使用」。
            console.log("here we come");
        };

        recognition.onend = function() { // 辨識完成
            recognizing = false; // 設定為「非辨識中」
            micIcon.innerHTML = "mic"; // 辨識完成...按鈕改為「開始辨識」。
            console.log("here we go");
        };

        recognition.onresult = function(event) { // 辨識有任何結果時
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

$(document).ready(function() {
    //  新增客服指令
    $('#newCusButton').click(function(e) {
        var cusButton = $('#customerNewButtonName').val();
        var cusData = new FormData(); //建構new FormData()
        cusData.append("label", cusButton);
        cusData.append("action", "新增");
        console.log(cusData);
        $.ajax({
            url: customerServiceServlet_url,
            cache: false,
            contentType: false,
            processData: false,
            data: cusData, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("新增成功！");
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }

        });
    });
});

$(document).ready(function() {
    //show即時推播
    $.ajax({
        type: "GET",
        url: notificationSettingAPI_url,
        dataType: "json",
        success: function(data) {
            console.log(data);

            for (var j = 0; j < data.length; j++) {
                for (var p = 0; p < data[j].MyMenu.length; p++) {
                    $('#instantList').append(
                        '<tr id = "delete' + data[j]._id._id.$oid + '">' +
                        '<td>' + data[j].MyMenu[p].Name + '</td>' +
                        '<td>' + data[j]._id.date + '</td>' +
                        '<td>' + data[j]._id.PushInfo + '</td>' +
                        '<td><button type="button" rel="tooltip" title="" class="btn btn-danger btn-link btn-sm" data-original-title="Push" aria-describedby="tooltip831011" onclick="PushInstantNoti(\'' +
                        data[j].MyMenu[p].Name +
                        '\')"><i class="material-icons">cloud_upload</i></button></td>' +
                        '<td><button type="button" rel="tooltip" title="" class="btn btn-danger btn-link btn-sm" data-original-title="Remove" aria-describedby="tooltip272746" onclick="deleteInstantNoti(\'' +
                        data[j]._id._id.$oid +
                        '\')"><i class="material-icons">close</i></button></td>' +
                        '</tr>'
                    );
                }
            }

        }
    });
});

$(document).ready(function() {
    //show定期推播
    $.ajax({
        type: "GET",
        url: notificationAPI_url,
        dataType: "json",
        success: function(data) {
            console.log(data);

            for (var j = 0; j < data.length; j++) {
                for (var p = 0; p < data[j].MyMenu.length; p++) {
                    $('#regularList').append(
                        '<tr id = "delete' + data[j]._id._id.$oid + '">' +
                        '<td>' + data[j].MyMenu[p].Name + '</td>' +
                        '<td>' + data[j]._id.date + '</td>' +
                        '<td>' + data[j]._id.PushInfo + '</td>' +
                        '<td>' + data[j]._id.Time + '</td>' +
                        '<td><button type="button" rel="tooltip" title="" class="btn btn-danger btn-link btn-sm" data-original-title="Remove" aria-describedby="tooltip272746" onclick="deleteRegularNoti(\'' +
                        data[j]._id._id.$oid +
                        '\')"><i class="material-icons">close</i></button></td>' +
                        '</tr>'
                    );
                }
            }

        }
    });
});

function deleteRegularNoti(oid) {
    var answer = confirm("確定要刪除這個推播嗎？");
    if (answer) {
        var regNotiDel = new FormData(); //建構new FormData()
        regNotiDel.append("Name", "23");
        regNotiDel.append("Price", "123");
        regNotiDel.append("PushInfo", "123");
        regNotiDel.append("Time", "123");
        regNotiDel.append("date", getNowFormatDate());
        regNotiDel.append("action", "刪除");
        regNotiDel.append("oid", oid);
        regNotiDel.append("type", "定期推播");
        console.log(regNotiDel);
        $.ajax({
            url: notificationAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: regNotiDel, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                var $target2 = $("#delete" + oid);
                $target2.hide('slow', function() {
                    $target2.remove();
                });
                window.location.reload();
            },
            error: function(data) {
                console.log("gg");
            }
        });
    }
}

function deleteInstantNoti(oid) {
    var answer = confirm("確定要刪除這個推播嗎？");
    if (answer) {
        var data1234 = {
            "oid": oid
        };

        var json_Data2 = JSON.stringify(data1234);
        $.ajax({
            type: "POST",
            url: deletePushInfoServlet_url,
            dataType: "json",
            data: json_Data2,
            success: function(data) {
                var $target2 = $("#delete" + oid);
                $target2.hide('slow', function() {
                    $target2.remove();
                });
                window.location.reload();
            },
            error: function() {
                console.log("gg");
            }
        });
    }
}

$(document).ready(function() {
    //show客服按鈕
    $.ajax({
        type: "GET",
        url: intent_list_url,
        dataType: "json",
        success: function(data) {

            for (var j = 0; j < data.length; j++) {

                var nameNum = data[j].name.split("/agent/intents/");
                $('#buttonList').append(
                    '<tr data-toggle="modal" data-target="#ServiceModal' + nameNum[1] + '" id = "delete' + nameNum[1] + '">' +
                    '<td>' + data[j].displayName + '</td>' +
                    '<td align="right"><button type="button" rel="tooltip" title=""  class="ibtnDel btn btn-md btn-danger " data-original-title="Remove" aria-describedby="tooltip272746" onclick="deleteCustomerService(\'' +
                    nameNum[1] +
                    '\')">Delete</button></td>' +
                    '</tr>'
                );
                $('#ModalTest').append(
                    '<div class="modal fade" id="ServiceModal' + nameNum[1] + '" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">' +
                    '<div class="modal-dialog" role="document">' +
                    '<div class="modal-content">' +
                    '<div class="modal-header">' +
                    '<h5 class="modal-title">' + data[j].displayName + '</h5>' +
                    '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
                    '<span aria-hidden="true">&times;</span>' +
                    '</button>' +
                    '</div>' +
                    '<div class="modal-body"><table align="center" class=" table table-hover order-list" id="ModelTestB' + nameNum[1] + '"><thead class="text-warning"><tr><th>客服訓練語句</th><th></th></tr></table><button type="button" class="btn btn-lg btn-block " id = "addTestModalRow" data-value="' + nameNum[1] + '">新增訓練語句</button></div>' +
                    '<br>' +
                    '<div class="modal-footer">' +
                    '<input class="btn btn-primary" data-dismiss="modal" onclick="generateTrainingPhares(\'' + ("ModelTestB" + nameNum[1]) + '\')" value="儲存" />' +
                    '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
                    '</div></div></div></div>'
                );
                if (data[j].trainingPhrases) {
                    for (var l = 0; l < data[j].trainingPhrases.length; l++) {
                        var tableName = nameNum[1];
                        var name = data[j].trainingPhrases[l].name;
                        var text = data[j].trainingPhrases[l].parts[0].text;
                        $('#ModelTestB' + nameNum[1]).append(
                            '<tr id = "' + tableName + l + '">' +
                            '<td>' + text + '</td>' +
                            '<td align="right"><input type="button" class="ibtnDel btn btn-md btn-danger " onclick="deleteTrainingTr(\'' + (tableName + l) + '\')" value="Delete"></td>' +
                            '</tr>'
                        );
                    }
                }
            }

            $("button[id^='addTestModalRow']").each(function() {
                $(this).on('click', function(e) {
                    var name = $(this).data("value");
                    $('#ModelTestB' + name);
                    $('#ModelTestB' + name).append(
                        '<tr><td><input class="form-control" placeholder="請輸入訓練語句" type="text" ></td><td><button class="ibtnDel btn btn-block" onclick="input2Tr(\'' + name + '\')">確認</button></td></tr>'
                    );
                });
            });
        }
    });
});

function input2Tr(name) {
    //新增訓練語句
    var count = $('#ModelTestB' + name + ' > tr').length;
    count++;
    $('#ModelTestB' + name).on("click", ".ibtnDel", function(event) {
        $(this).closest("tr").remove();

        $('#ModelTestB' + name).off("click", ".ibtnDel");
        var trainingPhrase = $(this).closest("td").prev().find('input').val();
        var id = name + count;
        $('#ModelTestB' + name).append(
            '<tr id = "' + id + '"><td>' + trainingPhrase + '</td><td align="right"><input type="button" class="ibtnDel btn btn-md btn-danger " onclick="deleteTrainingTr(\'' + id + '\')" value="Delete"></td></tr>'
        );
    });

}

function deleteTrainingTr(id) {
    $('#' + id).remove();
}

function trainingPhare(params) {

}

function generateTrainingPhares(modalName) {
    var name = modalName.split('ModelTestB')[1];

    var textArray = [];
    $('#' + modalName + ' > tr[id^=' + name + ']').each(function() {
        var text = $(this).children('td')[0].innerHTML;
        textArray.push(text);
    });
    var data = {
        "Intent_Id": name,
        "trainingPhrase": textArray
    };
    var jsonText = JSON.stringify(data);
    $.ajax({
        url: intent_addTrainingPhrases_url,
        type: "POST",
        dataType: "json",
        data: jsonText,
        success: function(data) {
            console.log(jsonText);
            alert("儲存成功！");
            window.location.reload();
        },
        error: function() {
            console.log("gg");
        }
    });
}

function deleteCustomerService(cusButton) {
    var answer = confirm("確定要刪除這個服務嗎？");
    if (answer) {
        var cusDelData = new FormData(); //建構new FormData()
        cusDelData.append("Intent_Id", cusButton);
        cusDelData.append("action", "刪除");
        $.ajax({
            url: customerServiceServlet_url,
            cache: false,
            contentType: false,
            processData: false,
            data: cusDelData, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                var $target2 = $("#delete" + cusButton);
                $target2.hide('slow', function() {
                    $target2.remove();
                });
                window.location.reload();
            },
            error: function(data) {
                console.log("gg");
            }
        });
    }
}

function PushInstantNoti(name) {
    var pushName = {
        "Name": name
    };
    var jsonName = JSON.stringify(pushName);
    $.ajax({
        type: "POST",
        url: pushInfoServlet_url,
        dataType: "json",
        data: jsonName,
        success: function(data) {
            alert("已送出推播！");
            window.location.reload();
        },
        error: function() {
            console.log("gg");
        }
    });
}

$(document).ready(function() {

    $('#newMenuChange').click(function(e) {
        var changeMenu = $("#selectChangeMenu :selected").text();
        var newKind = $("#selectNewKind :selected").text();
        var newMenuName = $('#newMenuName').val();
        var newMenuPrice = $('#newMenuPrice').val();
        var newMenuImage = $('#newMenuImage').prop('files')[0];
        if (newKind === undefined) {
            newKind = null;
        }
        if (newMenuName == undefined) {
            newMenuName = null;
        }
        if (newMenuPrice == undefined) {
            newMenuPrice = null;
        }
        if (newMenuImage == undefined) {
            newMenuImage = null;
        }

        var newMenu = new FormData(); //建構new FormData()
        newMenu.append("OldName", changeMenu);
        newMenu.append("Name", newMenuName);
        newMenu.append("ClassName", newKind);
        newMenu.append("Price", newMenuPrice);
        newMenu.append("file", newMenuImage);
        newMenu.append("action", "updateMenu");
        for (var pair of newMenu.entries()) {
            console.log(pair[0] + ', ' + pair[1]);
        }
        $.ajax({
            url: menuItemAPI_url,
            cache: false,
            contentType: false,
            processData: false,
            data: newMenu, //data只能指定單一物件
            type: 'Post',
            success: function(data) {
                alert("修改成功！");
                console.log(data);
                window.location.reload();
            },
            error: function(data) {
                alert("失敗");
            }

        });
    });
});
$(document).ready(function() {
    $("#myFile").on('change', function() {

        if (typeof(FileReader) != "undefined") {
            console.log("cum");
            var image_holder = $("#image-holder");
            image_holder.empty();

            var reader = new FileReader();
            reader.onload = function(e) {
                $("<img />", {
                    "src": e.target.result,
                    "class": "thumb-image"
                }).appendTo(image_holder);

            }
            image_holder.show();
            reader.readAsDataURL($(this)[0].files[0]);
        } else {
            alert("你的浏览器不支持FileReader.");
        }
    });
});