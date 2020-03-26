let protocol;
let hostname;
let port;
let bossOrderEndpoint;

let rewabo_base_url;
let showOrder_by_status_paid_url;
let showMessageServlet_url;

$(document).ready(function () {
  $.ajax({
    async: false,
    url: "static/json/settings.json",
    type: "GET",
    dataType: "json",
    success: function (data) {
      console.log(data)
      protocol = data.rewabo.protocol;
      hostname = data.rewabo.hostname;
      port = data.rewabo.port;
      bossOrderEndpoint = data.rewabo.urls.endpoint.bossOrderEndpoint;

      rewabo_base_url = data.rewabo.urls.baseUrl;
      showOrder_by_status_paid_url = rewabo_base_url
          + data.rewabo.urls.services.showOrder_by_status_paid_url;
      showMessageServlet_url = rewabo_base_url
          + data.rewabo.urls.services.showMessageServlet_url;

    },
    error: function () {

    }
  })
})
$(document).ready(function () {
  $.ajax({
    type: "GET",
    url: showOrder_by_status_paid_url,
    dataType: "json",
    success: function (data) {
      var allOrder = [];

      for (var i = data.length - 1; i >= 0; i--) {
        allOrder.push(data[i]);
        // order
      }

      for (var j = 0; j < allOrder.length; j++) {
        var oid = allOrder[j]._id.$oid;
        var historyTableId = "historyTable_" + Math.floor(j / 10);
        if (j % 10 == 0) {
          var historyTable = $(
              '<table class="table" style="display: none" id=' + historyTableId
              + '>\n'
              + '<thead class=" text-warning">\n'
              + '<tr>\n'
              + '<th>\n'
              + '日期\n'
              + '</th>\n'
              + '<th>\n'
              + '訂單編號\n'
              + '</th>\n'
              + '<th>\n'
              + '用餐方式\n'
              + '</th>\n'
              + '<th>\n'
              + '總價\n'
              + '</th>\n'
              + '<th>\n'
              + '\n'
              + '</th>\n'
              + '</tr>\n'
              + '</thead>\n'
              + '<tbody>\n'
              + '\n'
              + '</tbody>\n'
              + '</table>')
          $('#historyTables').append(historyTable);
        }
        $('#' + historyTableId).append(
            '<tr><td>' + allOrder[j].date + '</td>' +
            '<td>' + allOrder[j]._id.$oid + '</td>' +
            '<td>' + allOrder[j].Type + '</td>' +
            '<td>' + allOrder[j].TotalPrice + '</td>' +
            '<td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#class'
            + oid + '">檢視</button></td>' +
            '</tr>'
        );

        $('#historyHIHI').append(
            '<div class="modal fade" id="class' + oid
            + '" tabindex="0" role="dialog" aria-labelledby="class' + oid
            + 'Label" aria-hidden="true">' +
            '<div class="modal-dialog " role="document ">' +
            '<div class="modal-content ">' +
            '<div class="modal-header ">' +
            '<h3 class="modal-title " id="class' + oid
            + 'Label ">訂單明細</h3>' +
            '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
            +
            '<span aria-hidden="true">&times;</span>' +
            '</button></div><div class="modal-body ">' +
            '<div class="content "><div class="row ">' +
            '<div class="col-md-6 col-sm-6 "><div class="card card-nav-tabs ">'
            +
            '<div class="card-header card-header-warning ">時間</div>' +
            '<div class="card-body ">' +
            '<p class="card-text ">' + allOrder[j].date + '</p>' +
            '</div></div></div><div class="col-md-6 col-sm-6 ">' +
            '<div class="card card-nav-tabs ">' +
            '<div class="card-header card-header-warning ">訂單編號</div>' +
            '<div class="card-body ">' +
            '<p class="card-text ">' + allOrder[j]._id.$oid + '</p>' +
            '</div></div></div><div class="col-md-6 col-sm-6 ">' +
            '<div class="card card-nav-tabs ">' +
            '<div class="card-header card-header-warning ">'
            + allOrder[j].Type + '</div>' +
            '<div class="card-body ">' +
            '<p class="card-text ">' + allOrder[j].Number + '</p>' +
            '</div></div></div><div class="col-md-6 col-sm-6 ">' +
            '<div class="card card-nav-tabs ">' +
            '<div class="card-header card-header-warning ">費用</div>' +
            '<div class="card-body ">' +
            '<p class="card-text ">' + allOrder[j].TotalPrice + '</p>' +
            '</div></div></div><div class="col-md-12 col-sm-12 ">' +
            '<div class="card card-nav-tabs ">' +
            '<div class="card-header card-header-warning ">餐點</div>' +
            '<div class="card-body ">' +
            '<div class="table-responsive table-hover " id = "card' + oid
            + '">' +
            '<table class="table " id = "table' + oid
            + '"><thead class=" text-warning ">' +
            '<tr><th>餐點名稱</th><th>數量</th><th>價錢</th></tr></thead>');
        console.log("hihi");
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
              '<h5>備註：</h5><br><p>' + allOrder[j].Note + '</p>' +
              '</div></div></div></div></div></div><div class="modal-footer "><button type="button " class="btn btn-primary" data-dismiss="modal">Close</button></div></div></div></div>'
          );
        } else {
          $('#card' + oid).append(
              '<h5>備註：</h5><p>無</p>' +
              '</div></div></div></div></div></div><div class="modal-footer "><button type="button " class="btn btn-primary" data-dismiss="modal">Close</button></div></div></div></div>'
          );
        }
      }
      // nav
      $('#historyTab_nav_ul').append('<li class="page-item">\n'
          + '<a class="page-link" href="#" onclick="previousTab()" aria-label="Previous">\n'
          + '<span aria-hidden="true">&laquo;</span>\n'
          + '</a>\n'
          + '</li>');
      var historyTab_nav_ul_li_num = Math.ceil(data.length / 10);
      $('#maxHistoryTab_num').val(historyTab_nav_ul_li_num - 1);
      if (historyTab_nav_ul_li_num > 10) {
        historyTab_nav_ul_li_num = 10;
      }
      for (var i = 0; i < historyTab_nav_ul_li_num; i++) {
        $('#historyTab_nav_ul').append(
            '<li class="page-item"><a class="page-link my_tab_num" style="border:0; text-decoration:none; outline:none" href="#" onclick="gotoTargetTab(this)">'
            + (i + 1) + '</a></li>');
      }
      $('#historyTab_nav_ul').append('<li class="page-item">\n'
          + '<a class="page-link" href="#" onclick="nextTab()" aria-label="Next">\n'
          + '<span aria-hidden="true">&raquo;</span>\n'
          + '</a>\n'
          + '</li>');
      $('#historyTable_0').show();
      $('#currentHistoryTab_num').val(0);
    }
  });
});

function previousTab() {
  var currentHistoryTab_num = parseInt($('#currentHistoryTab_num').val());
  if (currentHistoryTab_num > 0) {
    var target = currentHistoryTab_num - 1;
    showCurrentTab(currentHistoryTab_num, target);
  }
}

function gotoTargetTab(object) {
  var currentHistoryTab_num = parseInt($('#currentHistoryTab_num').val());
  var target = parseInt($(object)[0].innerHTML) - 1;
  showCurrentTab(currentHistoryTab_num, target);

}

function jumpToTargetTab() {
  var currentHistoryTab_num = parseInt($('#currentHistoryTab_num').val());
  var target = parseInt($('#my_jump2TabId').val()) - 1;
  var maxHistoryTab_num = parseInt($('#maxHistoryTab_num').val());
  if(target <= maxHistoryTab_num && target >= 0) {
    showCurrentTab(currentHistoryTab_num, target);
    window.location.href = "#";
  }else {
    alert("沒有這頁喔~");
  }
}

function turnTab_nums(currentHistoryTab_num, target) {
  console.log(target);
    var maxHistoryTab_num = parseInt($('#maxHistoryTab_num').val());
    // if (target - currentHistoryTab_num > 0 && currentHistoryTab_num + 1
    //     <= maxHistoryTab_num) {
    $('#historyTab_nav_ul').empty();
    $('#historyTab_nav_ul').append('<li class="page-item">\n'
        + '<a class="page-link" href="#" onclick="previousTab()" aria-label="Previous">\n'
        + '<span aria-hidden="true">&laquo;</span>\n'
        + '</a>\n'
        + '</li>');
    var start = (target - 10 / 2 > 0) ? (target - 10 / 2) : 0;
    var end = (target + 10 / 2 > maxHistoryTab_num) ? target : start + 10;
    for (var i = start; i <= end; i++) {
      $('#historyTab_nav_ul').append(
          '<li class="page-item"><a class="page-link my_tab_num" style="border:0; text-decoration:none; outline:none" href="#" onclick="gotoTargetTab(this)">'
          + (i + 1) + '</a></li>');
    }
    $('#historyTab_nav_ul').append('<li class="page-item">\n'
        + '<a class="page-link" href="#" onclick="nextTab()" aria-label="Next">\n'
        + '<span aria-hidden="true">&raquo;</span>\n'
        + '</a>\n'
        + '</li>');
    // $('#historyTab_nav_ul .my_tab_num').each(function () {
    //   var num = parseInt($(this)[0].innerText);
    //   $(this)[0].innerText = num + 1;
    // });
    // }
    // else if (target - currentHistoryTab_num < 0) {
    //   $('#historyTab_nav_ul').empty();
    //   $('#historyTab_nav_ul').append('<li class="page-item">\n'
    //       + '<a class="page-link" href="#" onclick="previousTab()" aria-label="Previous">\n'
    //       + '<span aria-hidden="true">&laquo;</span>\n'
    //       + '</a>\n'
    //       + '</li>');
    //   var start = (target-10/2 > 0)?(target-10/2):0;
    //   var end = (target+10/2 > maxHistoryTab_num)?target:target+10/2;
    //   for (var i = start ; i < end + 1; i++) {
    //     $('#historyTab_nav_ul').append(
    //         '<li class="page-item"><a class="page-link my_tab_num" style="border:0; text-decoration:none; outline:none" href="#" onclick="gotoTargetTab(this)">'
    //         + (i + 1) + '</a></li>');
    //   }
    //   $('#historyTab_nav_ul').append('<li class="page-item">\n'
    //       + '<a class="page-link" href="#" onclick="nextTab()" aria-label="Next">\n'
    //       + '<span aria-hidden="true">&raquo;</span>\n'
    //       + '</a>\n'
    //       + '</li>');
    //   // $('#historyTab_nav_ul .my_tab_num').each(function () {
    //   //   var num = parseInt($(this)[0].innerText);
    //   //   if (num - 1 > 0) {
    //   //     $(this)[0].innerText = num - 1;
    //   //   }
    //   // });
    // }
    $('#historyTab_nav_ul .my_tab_num').each(function () {
      var num = parseInt($(this)[0].innerText);
      if (num == target + 1) {
        $(this).attr("style",
            "border:2px solid DodgerBlue; text-decoration:none; outline:none");
        $(this).css("color", "blue");
      }
      // else {
      //   $(this).attr("style", "border:0; text-decoration:none; outline:none");
      // }
    });
}

function showCurrentTab(currentHistoryTab_num, target) {
  $('#currentHistoryTab_num').val(target);
  turnTab_nums(currentHistoryTab_num, target);
  $('#historyTable_' + currentHistoryTab_num).hide();
  $('#historyTable_' + target).show();
}

function nextTab() {
  var currentHistoryTab_num = parseInt($('#currentHistoryTab_num').val());
  var maxHistoryTab_num = parseInt($('#maxHistoryTab_num').val());
  if (currentHistoryTab_num + 1 <= maxHistoryTab_num) {
    var target = currentHistoryTab_num + 1;
    showCurrentTab(currentHistoryTab_num, target);
  }
}

$(document).ready(function () {
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
          console.log("onopen::" + JSON.stringify(event, null, 4));
        };

        // 接收Websocket Server傳來的資料----> 訂單json <-----------
        this.webSocket.onmessage = function (event) {
          var app = event.data;
          var msg = JSON.parse(app)[0];
          // get order json object!!
          alert("您有新訂單");

          // 機器人也會跳通知
          var showDialogue = $("#showDialogue");
          showDialogue.append(
              '<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">您有新訂單 '
              + msg.Type + msg.Number + '</div>');
          playAudio("您有新訂單");
          //scrollbar置底
          showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
          $("#chatBox").collapse("show");

          var oid = msg._id.$oid;
          console.log(msg._id.$oid);
          $('#orderClass').append(
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
                + '" type="submit" class="btn btn-success pull-right" onclick ="waitingClick(\''
                + oid + '\')">開始製作</button>'
            );
          } else {
            $('#card' + oid).append(
                '<div class="card-footer"><div class="stats">' +
                '<h5>備註：</h5><p>無</p>' +
                '</div></div><button id= "button' + oid
                + '" type="submit" class="btn btn-success pull-right" onclick ="waitingClick(\''
                + oid + '\')">開始製作</button>'
            );
          }

          // order

        }
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

  // var client = new WebSocketClient("ws", "127.0.0.1", 8080, "/rewabo/endpoint");
  var client = new WebSocketClient("wss", "ai-rest.cse.ntou.edu.tw", 443,
      "/rewabo/endpoint");
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
