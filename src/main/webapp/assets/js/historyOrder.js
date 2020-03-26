let rewabo_base_url;
let orderHistoryAPI_by_CustomerID_url;
let showOrderByDate_by_CustomerID_FromDate_ToDate_url;
let fb_id;

$(document).ready(function () {
  $.ajax({
    async: false,
    url: "static/json/settings.json",
    type: "GET",
    dataType: "json",
    success: function (data) {
      console.log(data)
      rewabo_base_url = data.rewabo.urls.baseUrl;
      orderHistoryAPI_by_CustomerID_url = rewabo_base_url
          + data.rewabo.urls.services.orderHistoryAPI_by_CustomerID_url;
      showOrderByDate_by_CustomerID_FromDate_ToDate_url = rewabo_base_url
          + data.rewabo.urls.services.showOrderByDate_by_CustomerID_FromDate_ToDate_url;
      fb_id = data.rewabo.platform.id;
    },
    error: function () {

    }
  })
})
$(document).ready(function () {
  (function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
      return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/messenger.Extensions.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'Messenger'));
  var psid;
  window.extAsyncInit = () => {
    MessengerExtensions.getContext(fb_id, //set appID
        function success(thread_context) {
          // success
          psid = thread_context.psid; //get psid
          console.log("success");
        },
        function error(err) {
          // error
          console.log("error");
        }
    );
    document.getElementById('backButton').addEventListener('click',
        () => {
          MessengerExtensions.requestCloseBrowser(function success() {

            console.log("Webview closing");
          }, function error(err) {
            console.log(err);
          });
        });
  };

  // test
  // psid = "Allen";
  loadAllHistoryOrder(psid);

  $("#submit").click(function () {
    let start = $('.from_date').data('datepicker').getFormattedDate(
        'yyyy-mm-dd');
    let end = $('.to_date').data('datepicker').getFormattedDate(
        'yyyy-mm-dd');

    // test

    console.log(start);
    console.log(end);

    if (start == "" || end == "") {
      alert("選擇時間須全填！！");
    } else {
      $("#orderContent").empty();
      $("#myModal").empty();
      injectHistoryOrderByDate(psid, start, end);
    }

  })
})

function loadAllHistoryOrder(psid) {
  $.ajax({
    type: "GET",
    url: orderHistoryAPI_by_CustomerID_url + psid,
    dataType: "json",
    success: function (data) {

      if (!data.length) {
        $('#orderContent').append(
            '<div class="card text-white bg-secondary mb-3">\n' +
            '  <div class="card-header">Oops!!</div>\n' +
            '  <div class="card-body">\n' +
            '    <h5 class="card-title">您目前沒有任何一筆訂單</h5>\n' +
            '    <p class="card-text">可以跟機器人進行線上點餐啊～</p>\n' +
            '  </div>\n' +
            '</div>');
      }
      for (let i = 0; i < data.length; i++) {
        let order = data[i];

        let content = order;
        // order status
        let status = content.Status;
        // date
        let date = content.date;
        // type
        let type = content.Type;
        // number
        let number = content.Number;
        // note
        let note = content.Note;
        // total price
        let totalPrice = content.TotalPrice;
        // order oid
        let oid = content._id.$oid;

        let yymmdd = date.split(" ")[0];
        let year = yymmdd.split("-")[0];
        let month = yymmdd.split("-")[1];
        let day = yymmdd.split("-")[2];

        // my meunu
        let foods = content.MyMenu;

        let cardText = year + "年" + month + "月" + day + "日" + "  "
            + date.split(" ")[1] + "  " + type;

        $('#orderContent').append(
            '<div class="card text-white bg-info mb-3">' +
            '<div data-toggle="modal" data-target="#modal' + oid + '">' +
            '<div class="card-body">' +
            cardText +
            '</div>' +
            '</div>' +
            '</div>');

        modal(oid, cardText, type, number, totalPrice, note);

        for (let j = 0; j < foods.length; j++) {
          let food = foods[j];
          let name = food.Name;
          let price = food.Price;
          let amount = food.Amount;

          let modalDetail = "#detail" + oid;
          $(modalDetail).append(
              '<div class="card card-body">' +
              '<p>' + name + '</p>' +
              '<p>' + price + '</p>' +
              '<p>' + amount + '</p>' +
              '</div>');

        }

      }
    },
    error: function () {

    }
  });
}

function injectHistoryOrderByDate(psid, fromDate, toDate) {
  $.ajax({
    type: "GET",
    url: showOrderByDate_by_CustomerID_FromDate_ToDate_url + psid
        + "&FromDate=" + fromDate + "&ToDate=" + toDate,
    dataType: "json",
    success: function (data) {
      if (!data.length) {
        $('#orderContent').append(
            '<div class="card text-white bg-secondary mb-3">\n' +
            '  <div class="card-header">Oops!!</div>\n' +
            '  <div class="card-body">\n' +
            '    <h5 class="card-title">您目前沒有任何一筆訂單</h5>\n' +
            '    <p class="card-text">可以跟機器人進行線上點餐啊～</p>\n' +
            '  </div>\n' +
            '</div>');
      }
      for (let i = 0; i < data.length; i++) {
        let order = data[i];

        let content = order._id;
        // order status
        let status = content.Status;
        // date
        let date = content.date;
        // type
        let type = content.Type;
        // number
        let number = content.Number;
        // note
        let note = content.Note;
        // total price
        let totalPrice = content.TotalPrice;
        // order oid
        let oid = content._id.$oid;

        let yymmdd = date.split(" ")[0];
        let year = yymmdd.split("-")[0];
        let month = yymmdd.split("-")[1];
        let day = yymmdd.split("-")[2];

        // my meunu
        let foods = content.MyMenu;

        let cardText = year + "年" + month + "月" + day + "日" + "  "
            + date.split(" ")[1] + "  " + type;

        $('#orderContent').append(
            '<div class="card text-white bg-info mb-3">' +
            '<div data-toggle="modal" data-target="#modal' + oid + '">' +
            '<div class="card-body">' +
            cardText +
            '</div>' +
            '</div>' +
            '</div>');

        modal(oid, cardText, type, number, totalPrice, note);

        for (let j = 0; j < foods.length; j++) {
          let food = foods[j];
          let name = food.Name;
          let price = food.Price;
          let amount = food.Amount;

          let modalDetail = "#detail" + oid;
          $(modalDetail).append(
              '<div class="card card-body">' +
              '<p>' + name + '</p>' +
              '<p>' + price + '</p>' +
              '<p>' + amount + '</p>' +
              '</div>');

        }

      }
    },
    error: function () {

    }
  });
}

function meal(name, price, amount) {
  let meal = {
    "name": name,
    "price": price,
    "amount": amount,
  };
  return meal;
}

function modal(oid, cardText, type, number, totalPrice, note) {

  $("#myModal").append(
      '<div class="modal fade" id="modal' + oid
      + '" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">'
      +
      '<div class="modal-dialog" role="document">' +
      '<div class="modal-content">' +
      '<div class="modal-header">' +
      '<h5 class="modal-title" id="exampleModalLabel">' + cardText
      + '</h5>' +
      '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
      +
      '<span aria-hidden="true">&times;</span>' +
      '</button>' +
      '</div>' +
      '<div class="modal-body" id="numberSection' + oid + '">' +
      // number section
      '<div class="card card-body" id="detail' + oid + '">' +
      // order detail info
      '</div>' +
      '<div class="card card-body">' +
      '<div class="input-group">' +
      '<div class="input-group-prepend">' +
      '<span class="input-group-text">備註</span>' +
      '</div>' +
      '<textarea class="form-control" aria-label="With textarea" id="Note'
      + oid + '" disabled></textarea>' +
      '</div>' +
      '</div>' +
      '<div class="card card-body">' +
      '<p>總價 $' + totalPrice + '</p>' +
      '</div>' +
      '</div>' +
      '<div class="modal-footer">' +
      '<button type="button" class="btn btn-primary" data-dismiss="modal">確認</button>'
      +
      '</div>' +
      '</div>' +
      '</div>' +
      '</div>');
  let numberSection = "#numberSection" + oid;
  if (type === "內用") {
    $(numberSection).append(
        '<div class="card card-body">' +
        '<div class="input-group mb-3">' +
        '<div class="input-group-prepend">' +
        '<span class="input-group-text">內用桌號</span>' +
        '</div>' +
        '<input type="text" class="form-control" aria-label="內用桌號" id="tableNum'
        + oid + '" disabled>' +
        '<div class="input-group-prepend">' +
        '<span class="input-group-text">號桌</span>' +
        '</div>' +
        '</div>' +
        '</div>');
  } else {
    $(numberSection).append(
        '<div class="card card-body">' +
        '<div class="input-group mb-3">' +
        '<div class="input-group-prepend">' +
        '<span class="input-group-text">外帶號碼</span>' +
        '</div>' +
        '<input type="text" class="form-control" aria-label="內用桌號" id="tableNum'
        + oid + '" disabled>' +
        '<div class="input-group-prepend">' +
        '<span class="input-group-text">號</span>' +
        '</div>' +
        '</div>' +
        '</div>');
  }
  let targetTableNum = "input#tableNum" + oid;
  let targetNote = "textarea#Note" + oid;
  $(targetTableNum).val(number);
  $(targetNote).val(note);
}
