let protocol;
let hostname;
let port;
let bossOrderEndpoint;
let iotEndpoint;
let endpoint;

let rewabo_base_url;
let f1_fan_status_url;
let f2_fan_status_url;
let showPeopleServlet_url;
let showMessageServlet_url;
let configIoT_url;

let fanStatusMatrix = [false, false, false, false, false, false, false, false];

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
      iotEndpoint = data.rewabo.urls.endpoint.iotEndpoint;
      endpoint = data.rewabo.urls.endpoint.endpoint;

      rewabo_base_url = data.rewabo.urls.baseUrl;
      f1_fan_status_url = rewabo_base_url
          + data.rewabo.urls.services.f1_fan_status_url;
      f2_fan_status_url = rewabo_base_url
          + data.rewabo.urls.services.f2_fan_status_url;
      showPeopleServlet_url = rewabo_base_url
          + data.rewabo.urls.services.showPeopleServlet_url;
      showMessageServlet_url = rewabo_base_url
          + data.rewabo.urls.services.showMessageServlet_url;
      configIoT_url = rewabo_base_url + data.rewabo.urls.services.configIoT_url;
    },
    error: function () {

    }
  })
});
$(document).ready(function () {
  class iotWebSocketClient {

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
          console.log("iotonopen::" + JSON.stringify(event, null, 4));
        };

        // 接收Websocket Server傳來的資料---->
        this.webSocket.onmessage = function (event) {
          var msg = event.data;
          var json = JSON.parse(msg);
          var topic = json['topic'];
          let num = json['number'];
          var d = json['data'];

          if (topic === "th") {
            var temp = d['temperature'];
            var hum = d['humidity'];

            $('#temp').html(temp + '°C');
            $('#hum').html(hum + '%');
          } else {  // 風扇
            /*if (topic === "2ffs")
              num +=4;*/
            let fanStatus = "#fanStatus" + num;
            let fanBtn = "#fanOnBtn" + num;
            let fanImg = "#fanImg" + num;
            let fanOnImg = "assets/image/fanOpen.gif";
            let fanOffImg = "assets/image/fanStop.png";

            if (d === "on") {
              $(fanStatus).html(num + "號風扇 : 開");
              $(fanBtn).html("關");
              $(fanImg).attr("src", fanOnImg);
            } else if (d === "off") {
              $(fanStatus).html(num + "號風扇 : 關");
              $(fanBtn).html("開");
              $(fanImg).attr("src", fanOffImg);
            }
            /*let fanStatus;
            let fanBtn;
            let fanImg;
            let fanOnImg = "assets/image/fanOpen.gif";
            let fanOffImg = "assets/image/fanStop.png";

            if (num === "1") {
              fanStatus = $("#fanStatus1");
              fanBtn = $("#fanOnBtn1");
              fanImg = $("#fanImg1");
            } else if (num === "2") {
              fanStatus = $("#fanStatus2");
              fanBtn = $("#fanOnBtn2");
              fanImg = $("#fanImg2");
            } else if (num === "3") {
              fanStatus = $("#fanStatus3");
              fanBtn = $("#fanOnBtn3");
              fanImg = $("#fanImg3");
            } else {
              fanStatus = $("#fanStatus4");
              fanBtn = $("#fanOnBtn4");
              fanImg = $("#fanImg4");
            }

            if (d === "on") {
              fanStatus.html(num + "號風扇 : 開");
              fanBtn.html("關");
              fanImg.attr("src", fanOnImg);
            } else {
              fanStatus.html(num + "號風扇 : 關");
              fanBtn.html("開");
              fanImg.attr("src", fanOffImg);
            }*/
          }
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

  let iotClient = new iotWebSocketClient(protocol, hostname, port,
      iotEndpoint);
  iotClient.connect();

  let temp = 25;
  let airTempWords = $("#airTempWords");
  $("#airOnBtn").click(function () {
    iotClient.send(
        '{"topic":"lab401/raspberrypibplus/ir/actuator/switch/airconditioner","data":{"command":"on"},"protocolType":"MQTT","dataId":"57txwirnef"}');
    console.log("air on");
    airTempWords.html("大廳冷氣目前設定為" + temp + "度");

  });

  $("#airOffBtn").click(function () {
    iotClient.send(
        '{"topic":"lab401/raspberrypibplus/ir/actuator/switch/airconditioner","data":{"command":"off"},"protocolType":"MQTT","dataId":"57txwirnef"}');
    console.log("air off");
    airTempWords.html("大廳冷氣目前設定為關");
  });

  $("#tempUpBtn").click(function () {
    if (temp < 30) {
      iotClient.send(
          '{"topic":"lab401/raspberrypibplus/ir/actuator/switch/airconditioner","data":{"command":"up"},"protocolType":"MQTT","dataId":"57txwirnef"}');
      temp++;
      airTempWords.html("大廳冷氣目前設定為" + temp + "度");
      console.log("air up");
    }
  });

  $("#tempDownBtn").click(function () {
    if (temp > 16) {
      iotClient.send(
          '{"topic":"lab401/raspberrypibplus/ir/actuator/switch/airconditioner","data":{"command":"down"},"protocolType":"MQTT","dataId":"57txwirnef"}');
      temp--;
      airTempWords.html("大廳冷氣目前設定為" + temp + "度");
      console.log("air down");
    }
  });

  for (let i = 1; i <= 4; i++) {
    let onBtn = "#fanOnBtn" + i;
    let cmd;
    //console.log(onBtn);
    $(onBtn).click(function () {
      if (fanStatusMatrix[i-1]) {  // 風扇目前開著
          cmd = "off";
          fanStatusMatrix[i-1] = false;
      } else {
          cmd = "on";
          fanStatusMatrix[i-1] = true;
      }
      iotClient.send(
          '{"topic":"lab401/raspberrypibplus/relay/actuator/switch/fan/1f","data":{"command":"' + cmd + '","number":"'
          + i + '"},"protocolType":"MQTT","dataId":"57txwirnef"}');
      console.log('{"topic":"lab401/raspberrypibplus/relay/actuator/switch/fan/1f","data":{"command":"' + cmd + '","number":"'
          + i + '"},"protocolType":"MQTT","dataId":"57txwirnef"}');
    });
  }

});

$(document).ready(function () {
  $.ajax({
    type: "GET",
    url: f1_fan_status_url,
    dataType: "json",
    success: function (data) {
      let showDialogue = "#fanStatus";
      let fanBtn = "#fanOnBtn";

      for (let i=1;i<=4;i++) {
        let status = data['fan' + i];
        let str1 = showDialogue + i;
        let str2 = fanBtn + i;
        if (status === "on") {
          $(str1).html(i + "號風扇 : 開");
          $(str2).html("關");
          fanStatusMatrix[i-1] = true;
        } else {
          $(str1).html(i + "號風扇 : 關");
          $(str2).html("開");
          fanStatusMatrix[i-1] = false;
        }
      }
    },
    error: function () {
      console.log("GG");
    }
  });
});

/*$(document).ready(function () {
  $.ajax({
    type: "GET",
    url: f2_fan_status_url,
    dataType: "json",
    success: function (data) {
      let showDialogue = "#fanStatus";
      let fanBtn = "#fanOnBtn";

      for (let i=5;i<=8;i++) {
        let status = data['fan' + (i-4)];
        let str1 = showDialogue + i;
        let str2 = fanBtn + i;
        if (status === "on") {
          $(str1).html(i + "號風扇 : 開");
          $(str2).html("關");
          fanStatusMatrix[i-1] = true;
        } else {
          $(str1).html(i + "號風扇 : 關");
          $(str2).html("開");
        }
      }
    },
    error: function () {
      console.log("GG");
    }
  });
});*/

$(document).ready(function () {
  $.ajax({
    type: "POST",
    url: showPeopleServlet_url,
    dataType: "json",
    success: function (data) {
      console.log(data);
      var people = data[0].People;
      console.log(people);
      $('#peo').emtpy();
      $('#peo').append(
          people + '<small>人</small>'
      );
    }
  });
});
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

  // $("#submit").click(function () {
  //
  //     var remarks = generateOrder();
  //     console.log(remarks);
  //     client.send(remarks);
  // });
  //設置要訂閱的端點路徑
  // let client = new WebSocketClient("ws", "127.0.0.1", 8080, "/rewabo/endpoint");
  //設置要訂閱的端點路徑(有URL query string)
  // let client = new WebSocketClient("ws", "127.0.0.1", 8080, "/ConversationAPI/endpoint?push=TIME");
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
const generateId = function _generateId() {
  return (1 + Math.random() * 4294967295).toString(16);
};

$(document).ready(function () {
  //  新增推播餐點
  $('#uploadFoot').click(function (e) {
    var device = $("#addFootDeviceSelect :selected").text();
    var t1 = $("#addFootTempSelect :selected").val();
    var v1 = $("#addFootDeviceTemp").val();
    var t2 = $("#addFootPeopleSelect :selected").val();
    var v2 = $("#addFootDevicePeople").val();
    var op = $("#addFootOpenSelect").val();
    var name2 = device + op;
    var Id = generateId();
    var data123 = {
      userID: {},
      message: {},
      result: {},
      config: {
        id: Id,
        type: "NICP-ConditionalTrigger",
        name: name2,
        rules: [{
          t: t1,
          v: v1,
          vt: "num",
          propertyType: "flow",
          property: "temperature",
          topic: "temperature"
        },
          {
            t: t2,
            v: v2,
            vt: "num",
            propertyType: "flow",
            property: "peopleQuantity",
            topic: "peopleQuantity"
          }
        ],
        outputTopic: "AndNodeTopic1",
        gateType: "or",
        emitOnlyIfTrue: false
      }
    };
    console.log(JSON.stringify(data123));
    $.ajax({
      url: configIoT_url,
      data: JSON.stringify(data123), //data只能指定單一物件
      contentType: "application/json",
      dataType: "json",
      type: 'Post',
      success: function (data) {
        alert("新增成功！");
        window.location.reload();
      },
      error: function (data) {
        alert("失敗");
      }

    });
  });
});
