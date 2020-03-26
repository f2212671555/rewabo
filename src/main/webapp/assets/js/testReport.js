// $(document).ready(function() {
//     $.ajax({
//         async: false,
//         url: "static/json/settings.json",
//         type: "GET",
//         dataType: "json",
//         success: function(data) {
//             console.log(data)
//             protocol = data.rewabo.protocol;
//             hostname = data.rewabo.hostname;
//             port = data.rewabo.port;
//             bossOrderEndpoint = data.rewabo.urls.endpoint.bossOrderEndpoint;
//             endpoint = data.rewabo.urls.endpoint.endpoint;

//             rewabo_base_url = data.rewabo.urls.baseUrl;
//             menuCategoryAPI_url = rewabo_base_url +
//                 data.rewabo.urls.services.menuCategoryAPI_url;
//             menuItemAPI_url = rewabo_base_url +
//                 data.rewabo.urls.services.menuItemAPI_url;
//             addPushInfoServlet_url = rewabo_base_url +
//                 data.rewabo.urls.services.addPushInfoServlet_url;
//             notificationAPI_url = rewabo_base_url +
//                 data.rewabo.urls.services.notificationAPI_url;
//             customerServiceServlet_url = rewabo_base_url +
//                 data.rewabo.urls.services.customerServiceServlet_url;
//             deletePushInfoServlet_url = rewabo_base_url +
//                 data.rewabo.urls.services.deletePushInfoServlet_url;
//             pushInfoServlet_url = rewabo_base_url +
//                 data.rewabo.urls.services.pushInfoServlet_url;
//             notificationSettingAPI_url = rewabo_base_url +
//                 data.rewabo.urls.services.notificationSettingAPI_url;
//             showMessageServlet_url = rewabo_base_url +
//                 data.rewabo.urls.services.showMessageServlet_url;
//             intent_list_url = rewabo_base_url +
//                 data.rewabo.urls.dialogFlow.intent_list_url;
//             intent_create_url = rewabo_base_url +
//                 data.rewabo.urls.dialogFlow.intent_create_url;
//             intent_delete_url = rewabo_base_url +
//                 data.rewabo.urls.dialogFlow.intent_delete_url;
//             intent_addTrainingPhrases_url = rewabo_base_url +
//                 data.rewabo.urls.dialogFlow.intent_addTrainingPhrases_url;



//         },
//         error: function() {

//         }
//     });
// });
var mainMeal = [];
var mainAPT = [];
var sideMeal = [];
var sideAPT = [];
var drink = [];
var drinkAPT = [];
var set = [];
var setAPT = [];
var label = [mainMeal, sideMeal, drink, set];
var data = [mainAPT, sideAPT, drinkAPT, setAPT];
var prefix = ["main", "side", "drink", "set"];
$(document).ready(function() {
    $.ajax({
        // 統計Order
        type: "GET",
        //url: "http://localhost:8080/rewabo/HotSales?action=loading",
        url: "https://ai-rest.cse.ntou.edu.tw/rewabo/HotSales?action=loading",
        dataType: "json",
        success: function(data) {
            //console.log(data);
            for (var q = 0; q < data.length; q++) {
                var type = data[q].Type;
                var name = data[q].Name;
                var times = data[q].AppearTimes;
                if (type === "主餐") {
                    mainMeal.push(name);
                    mainAPT.push(times);
                } else if (type === "配餐") {
                    sideMeal.push(name);
                    sideAPT.push(times);
                } else if (type === "飲料") {
                    drink.push(name);
                    drinkAPT.push(times);
                } else {
                    set.push(name);
                    setAPT.push(times);
                }
            }
            sort();
            hotSaleBread();
            creatChart();
            //console.log(drinkAPT);
        },
        error: function() {
            console.log("can not GET");
        }
    });
});

$(document).ready(function() {
    $.ajax({
        // 可能套餐
        type: "GET",
        //url: "http://localhost:8080/rewabo/HotSales?action=loading",
        url: "https://ai-rest.cse.ntou.edu.tw/rewabo/HotSales?action=probabilitySet",
        dataType: "json",
        success: function(data) {
            //console.log(data);
            for (let q = 0; q < data.length; q++) {
                let set = data[q];
                if (set.ItemNum == 2) {
                    $("#ps").append(

                        '<tr>' +
                        '<td class="breadcrumb-item" aria-current="page">' + set.one + '</td>' +
                        '<td class="breadcrumb-item" aria-current="page">' + set.two + '</td>' +
                        '<td> </td>' +
                        '<td><button type="button" class="c btn btn-outline-secondary" style="display:inline-block;" data-target="#exampleModal" data-toggle="modal" onclick = "modalOpen(\'' + set.one + '\', \'' + set.two + '\')">新增套餐</button></td></tr>'
                    );
                } else {
                    $("#ps").append(
                        '<tr>' +
                        '<td class="breadcrumb-item" aria-current="page">' + set.one + '</td>' +
                        '<td class="breadcrumb-item" aria-current="page">' + set.two + '</td>' +
                        '<td class="breadcrumb-item" aria-current="page">' + set.three + '</td>' +
                        '<td><button type="button" class="c btn btn-outline-secondary" style="display:inline-block;" data-target="#exampleModal" data-toggle="modal" onclick = "modalOpen3(\'' + set.one + '\', \'' + set.two + '\', \'' + set.three + '\')">新增套餐</button></td></tr>'
                    );
                }
            }
        },
        error: function() {
            console.log("can not GET");
        }
    });
});

function updateDatabase() {
    $.ajax({
        // 更新資料庫
        type: "GET",
        url: "https://ai-rest.cse.ntou.edu.tw/rewabo/HotSales?action=update",
        //url: "http://localhost:8080/rewabo/HotSales?action=update",
        dataType: "text",
        success: function(data) {
            console.log("update success");
            window.location.reload();
        },
        error: function() {
            console.log("can not GET");
        }
    });
}
var addNum = 0;

function modalOpen(x, y) {
    $('#mybody').empty();
    document.getElementById("mybody").innerHTML += '<tr><td class="col-sm-4"><select id="newset1" name="name " class="form-control"><option value="na卡啦雞腿堡">卡啦雞腿堡</option><option value="na紅茶">紅茶</option><option value="na起司牛肉堡">起司牛肉堡</option><option value="na火腿蛋餅">火腿蛋餅</option><option value="na熱狗">熱狗</option><option value="na牛奶">牛奶</option><option value="na奶茶">奶茶</option><option value="na豆漿">豆漿</option><option value="na原味蛋餅">原味蛋餅</option><option value="na薯條">薯條</option><option value="na蘿蔔糕">蘿蔔糕</option><option value="na煎餃">煎餃</option><option value="na鐵板麵">鐵板麵</option><option value="na奶油義大利麵">奶油義大利麵</option><option value="na肉醬義大利麵">肉醬義大利麵</option><option value="na蔥油餅">蔥油餅</option><option value="na草莓厚片">草莓厚片</option><option value="na花生厚片">花生厚片</option><option value="na奶酥厚片">奶酥厚片</option><option value="na豬肉漢堡">豬肉漢堡</option><option value="na洋蔥圈">洋蔥圈</option><option value="na荷包蛋">荷包蛋</option><option value="na雞塊">雞塊</option><option value="na鮪魚蛋餅">鮪魚蛋餅</option><option value="na安生回我家">安生回我家</option></select></td><td class="col-sm-2"><a class="deleteRow"></a></td></tr>';
    document.getElementById("mybody").innerHTML += '<tr><td class="col-sm-4"><select id="newset2" name="name " class="form-control"><option value="na卡啦雞腿堡">卡啦雞腿堡</option><option value="na紅茶">紅茶</option><option value="na起司牛肉堡">起司牛肉堡</option><option value="na火腿蛋餅">火腿蛋餅</option><option value="na熱狗">熱狗</option><option value="na牛奶">牛奶</option><option value="na奶茶">奶茶</option><option value="na豆漿">豆漿</option><option value="na原味蛋餅">原味蛋餅</option><option value="na薯條">薯條</option><option value="na蘿蔔糕">蘿蔔糕</option><option value="na煎餃">煎餃</option><option value="na鐵板麵">鐵板麵</option><option value="na奶油義大利麵">奶油義大利麵</option><option value="na肉醬義大利麵">肉醬義大利麵</option><option value="na蔥油餅">蔥油餅</option><option value="na草莓厚片">草莓厚片</option><option value="na花生厚片">花生厚片</option><option value="na奶酥厚片">奶酥厚片</option><option value="na豬肉漢堡">豬肉漢堡</option><option value="na洋蔥圈">洋蔥圈</option><option value="na荷包蛋">荷包蛋</option><option value="na雞塊">雞塊</option><option value="na鮪魚蛋餅">鮪魚蛋餅</option><option value="na安生回我家">安生回我家</option></select></td><td class="col-sm-2"><a class="deleteRow"></a></td></tr>';
    addNum = 2;
    var aa = document.getElementById('newset1');
    var bb = document.getElementById('newset2');
    for (var i = 0; i < aa.options.length; i++) {
        if (aa.options[i].text === x) {
            aa.selectedIndex = i;
            break;
        }
    }
    for (var j = 0; j < bb.options.length; j++) {
        if (bb.options[j].text === y) {
            bb.selectedIndex = j;
            break;
        }
    }
    //document.getElementById("newset2").innerHTML = y;
    //document.getElementById("newset3").innerHTML = " ";
}

function modalOpen3(x, y, z) {
    $('#mybody').empty();
    addNum = 3;
    document.getElementById("mybody").innerHTML += '<tr><td class="col-sm-4"><select id="newset1" name="name " class="form-control"><option value="na卡啦雞腿堡">卡啦雞腿堡</option><option value="na紅茶">紅茶</option><option value="na起司牛肉堡">起司牛肉堡</option><option value="na火腿蛋餅">火腿蛋餅</option><option value="na熱狗">熱狗</option><option value="na牛奶">牛奶</option><option value="na奶茶">奶茶</option><option value="na豆漿">豆漿</option><option value="na原味蛋餅">原味蛋餅</option><option value="na薯條">薯條</option><option value="na蘿蔔糕">蘿蔔糕</option><option value="na煎餃">煎餃</option><option value="na鐵板麵">鐵板麵</option><option value="na奶油義大利麵">奶油義大利麵</option><option value="na肉醬義大利麵">肉醬義大利麵</option><option value="na蔥油餅">蔥油餅</option><option value="na草莓厚片">草莓厚片</option><option value="na花生厚片">花生厚片</option><option value="na奶酥厚片">奶酥厚片</option><option value="na豬肉漢堡">豬肉漢堡</option><option value="na洋蔥圈">洋蔥圈</option><option value="na荷包蛋">荷包蛋</option><option value="na雞塊">雞塊</option><option value="na鮪魚蛋餅">鮪魚蛋餅</option><option value="na安生回我家">安生回我家</option></select></td><td class="col-sm-2"><a class="deleteRow"></a></td></tr>';
    document.getElementById("mybody").innerHTML += '<tr><td class="col-sm-4"><select id="newset2" name="name " class="form-control"><option value="na卡啦雞腿堡">卡啦雞腿堡</option><option value="na紅茶">紅茶</option><option value="na起司牛肉堡">起司牛肉堡</option><option value="na火腿蛋餅">火腿蛋餅</option><option value="na熱狗">熱狗</option><option value="na牛奶">牛奶</option><option value="na奶茶">奶茶</option><option value="na豆漿">豆漿</option><option value="na原味蛋餅">原味蛋餅</option><option value="na薯條">薯條</option><option value="na蘿蔔糕">蘿蔔糕</option><option value="na煎餃">煎餃</option><option value="na鐵板麵">鐵板麵</option><option value="na奶油義大利麵">奶油義大利麵</option><option value="na肉醬義大利麵">肉醬義大利麵</option><option value="na蔥油餅">蔥油餅</option><option value="na草莓厚片">草莓厚片</option><option value="na花生厚片">花生厚片</option><option value="na奶酥厚片">奶酥厚片</option><option value="na豬肉漢堡">豬肉漢堡</option><option value="na洋蔥圈">洋蔥圈</option><option value="na荷包蛋">荷包蛋</option><option value="na雞塊">雞塊</option><option value="na鮪魚蛋餅">鮪魚蛋餅</option><option value="na安生回我家">安生回我家</option></select></td><td class="col-sm-2"><a class="deleteRow"></a></td></tr>';
    document.getElementById("mybody").innerHTML += '<tr><td class="col-sm-4"><select id="newset3" name="name " class="form-control"><option value="na卡啦雞腿堡">卡啦雞腿堡</option><option value="na紅茶">紅茶</option><option value="na起司牛肉堡">起司牛肉堡</option><option value="na火腿蛋餅">火腿蛋餅</option><option value="na熱狗">熱狗</option><option value="na牛奶">牛奶</option><option value="na奶茶">奶茶</option><option value="na豆漿">豆漿</option><option value="na原味蛋餅">原味蛋餅</option><option value="na薯條">薯條</option><option value="na蘿蔔糕">蘿蔔糕</option><option value="na煎餃">煎餃</option><option value="na鐵板麵">鐵板麵</option><option value="na奶油義大利麵">奶油義大利麵</option><option value="na肉醬義大利麵">肉醬義大利麵</option><option value="na蔥油餅">蔥油餅</option><option value="na草莓厚片">草莓厚片</option><option value="na花生厚片">花生厚片</option><option value="na奶酥厚片">奶酥厚片</option><option value="na豬肉漢堡">豬肉漢堡</option><option value="na洋蔥圈">洋蔥圈</option><option value="na荷包蛋">荷包蛋</option><option value="na雞塊">雞塊</option><option value="na鮪魚蛋餅">鮪魚蛋餅</option><option value="na安生回我家">安生回我家</option></select></td><td class="col-sm-2"><a class="deleteRow"></a></td></tr>';
    var aa = document.getElementById('newset1');
    var bb = document.getElementById('newset2');
    var cc = document.getElementById('newset3');
    for (var i = 0; i < aa.options.length; i++) {
        if (aa.options[i].text === x) {
            aa.selectedIndex = i;
            break;
        }
    }
    for (var j = 0; j < bb.options.length; j++) {
        if (bb.options[j].text === y) {
            bb.selectedIndex = j;
            break;
        }
    }
    for (var p = 0; p < cc.options.length; p++) {
        if (cc.options[p].text === z) {
            cc.selectedIndex = p;
            break;
        }
    }
    // document.getElementById("newset1").innerHTML = x;
    // document.getElementById("newset2").innerHTML = y;
    // document.getElementById("newset3").innerHTML = z;
}


function swap(num, x, y) {
    var tl = label[num][x];
    var td = data[num][x];
    label[num][x] = label[num][y];
    data[num][x] = data[num][y];
    label[num][y] = tl;
    data[num][y] = td;
}

function sort() {
    for (var k = 0; k < 4; k++) {
        var num = data[k];
        var ri = label[k].length - 1; // 最右邊的元素
        while (ri >= 1) {
            var li = 0;
            while (li < ri) {
                if (num[li] < num[ri])
                    swap(k, li, ri);
                li++;
            }
            ri--;
        }
    }
}

function hotSaleBread() {
    for (var i = 0; i < 4; i++) {
        for (var j = 1; j <= 3; j++) {
            var str = "#" + prefix[i] + j;
            $(str).html('<span class="badge badge-info">' + j + '</span> ' + label[i][j - 1]);
            console.log(str);
        }
    }
}

function creatChart() {
    Chart.defaults.global.defaultFontSize = 16;
    Chart.defaults.global.defaultFontFamily = "微軟正黑體";
    for (var i = 0; i < 4; i++) {
        var targetDiv = $("#" + prefix[i] + "CanvasDiv");
        targetDiv.html('<canvas id="' + prefix[i] + 'CanvasLine" style="height:540px"></canvas>');
        var ctx = $("#" + prefix[i] + "CanvasLine");
        var myChart = new Chart(ctx, {
            type: 'horizontalBar',
            data: {
                labels: label[i],
                datasets: [{
                    label: '出現次數',
                    data: data[i],
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255,99,132,1)',
                    borderWidth: 0.3,
                    barThickness: 3
                }]
            },
            option: {
                scales: {
                    xAxes: [{
                        ticks: {
                            min: 0,
                            max: 100,
                            stepSize: 1
                        }
                    }]
                }
            }
        });
    }
}
var itemNum = 0;
$(document).ready(function() {
    var counter = 0;
    var option;
    $.ajax({
        type: "GET",
        url: "https://ai-rest.cse.ntou.edu.tw/rewabo/MenuItemAPI",
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
            url: "https://ai-rest.cse.ntou.edu.tw/rewabo/MenuItemAPI",
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
        for (var i = 0; i < itemNum; i++) {
            var q = i + 1;
            item[i] = $("#selectname5" + q + " :selected").text();
            console.log(item[i]);
        }
        item[itemNum] = $("#newset1 :selected").text();
        itemNum++;
        item[itemNum] = $("#newset2 :selected").text();
        itemNum++;
        item[itemNum] = $("#newset3 :selected").text();
        itemNum++;
        if (addNum == 3) {
            console.log("三個");
            var newSet = $("#newSet").val();
            var newSetPrice = $("#newSetPrice").val();
            var newSetFile = $('#newSetFile').prop('files')[0];
            var SetForm = new FormData(); //建構new FormData()
            if (newSetFile != null && newSet != null && newSetPrice != null) {
                SetForm.append("Name", newSet);
                SetForm.append("Price", newSetPrice);
                SetForm.append("File", newSetFile);

                SetForm.append("action", "addSet");
                for (var j = 0; j < itemNum; j++) {
                    var v = "Item[" + j + "]";
                    SetForm.append(v, item[j]);
                }
                itemNum--;
                SetForm.append("itemNum", itemNum.toString());
                for (var pair of SetForm.entries()) {
                    console.log(pair[0] + ', ' + pair[1]);
                }
                $.ajax({
                    url: "https://ai-rest.cse.ntou.edu.tw/rewabo/MenuItemAPI",
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
        } else {
            var newSet = $("#newSet").val();
            var newSetPrice = $("#newSetPrice").val();
            var newSetFile = $('#newSetFile').prop('files')[0];
            var SetForm = new FormData(); //建構new FormData()
            itemNum--;
            console.log("兩個");
            if (newSetFile != null && newSet != null && newSetPrice != null) {
                SetForm.append("Name", newSet);
                SetForm.append("Price", newSetPrice);
                SetForm.append("File", newSetFile);

                SetForm.append("action", "addSet");
                for (var j = 0; j < itemNum; j++) {
                    var v = "Item[" + j + "]";
                    SetForm.append(v, item[j]);
                }
                itemNum--;
                SetForm.append("itemNum", itemNum.toString());
                for (var pair of SetForm.entries()) {
                    console.log(pair[0] + ', ' + pair[1]);
                }

                $.ajax({
                    url: "https://ai-rest.cse.ntou.edu.tw/rewabo/MenuItemAPI",
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
        }
    });
});