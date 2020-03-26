let rewabo_base_url;
let menuItemAPI_url;
let menuItemAPI_by_oneSet_id_url;
let addOrderServlet_url;
let fb_id;
let amt;
let fn = [];
let fp = [];

$(document).ready(function () {
  $.ajax({
    async: false,
    url: "static/json/settings.json",
    type: "GET",
    dataType: "json",
    success: function (data) {
      console.log(data);
      rewabo_base_url = data.rewabo.urls.baseUrl;
      menuItemAPI_url = rewabo_base_url
          + data.rewabo.urls.services.menuItemAPI_url;
      menuItemAPI_by_oneSet_id_url = rewabo_base_url
          + data.rewabo.urls.services.menuItemAPI_by_oneSet_id_url;
      addOrderServlet_url = rewabo_base_url
          + data.rewabo.urls.services.addOrderServlet_url;
      fb_id = data.rewabo.platform.id;
    },
    error: function () {

    }
  })
});

$(document).ready(function () {
  var param = $.UrlParam("content");
  console.log(param);
  $.ajax({
    type: "GET",
    url: menuItemAPI_url,
    dataType: "json",
    success: function (data) {
      console.log(data);
      let foodClassnameArray = [];
      amt = data.length;
      for (let i = 0; i < data.length; i++) {
        // food
        let food = data[i];
        // food class name
        let className = food.ClassName;
        // food name
        let name = food.Name;
        fn.push(name);
        // food price
        let price = Number(food.Price);
        fp.push(price);
        // food img url
        let imgUrl = food.url;

        foodClassnameArray.push(className);
      }
      // filter same class name
      let foodClassnames = foodClassnameArray.filter(
          function (element, index, arr) {
            return arr.indexOf(element) === index;
          });
      // console.log(foodClassnames);

      for (let i = 0; i < foodClassnames.length; i++) {

        let foodClassname = foodClassnames[i];

        // nav bar class name
        $('#navBar').append(
            //'<li class="nav-item">' +
            '<a class="nav-link" href="#' + foodClassname
            + '" style="display: inline-block">' + foodClassname + '</a>'
            //'</li>'
        );

        // menu total class name
        $("#center").append(
            '<div id="' + foodClassname + '">' + // 分類bar
            '<div id="herf' + foodClassname
            + '" class="alert text-white" style="background: #49525A;">'
            + foodClassname + '</div>' +
            '</div>'
        );

        // set food of each class
        for (let j = 0; j < data.length; j++) {

          // food
          let food = data[j];
          // food class name
          let className = food.ClassName;

          if (className === foodClassname) {

            // food name
            let name = food.Name;
            // food price
            let price = food.Price;
            // food img url
            let imgUrl = food.url;
            // set each food id
            let goal = "#" + className;

            let modalID = name + "Modal";

            let answerID = modalID + "Answer";
            let priceID = name + "Price";
            let soldOut = food.SoldOut;
            $(goal).append(
                '<div id="' + name + '">' +
                '<div class="card mb-3">' +
                '<div class="row no-gutters" data-toggle="modal" data-target="#'
                + modalID + '">' +
                '<div style="width:50%">' +
                '<img src="' + imgUrl + '" class="card-img" alt="' + name
                + '">' + // 圖片
                '</div>' +
                '<div style="width:50%"><br>' +
                '<div class="card-body">' + // 名稱
                '<h5 class="card-title">' + name + '</h5>' +
                '<p class="card-text"><small id="' + priceID
                + '" class="text-muted">' + "NT$" + price + '</small></p>'
                +
                '</div>' +
                '<div class="input-group" style="padding-left:2px; padding-right:2px; bottom: 0;">'
                +
                '<div class="input-group-prepend">' +
                '<div class="input-group-text">個數</div>' +
                '</div>' +
                '<input type="text" class="form-control" id="' + answerID
                + '" value="0" disabled>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>'
            );
            if (soldOut) {
              $('#' + name).addClass('soldOutMask');
              var soldOutImgDiv = document.createElement("div");
              var soldOutImg = new Image();
              soldOutImg.src = 'assets/image/soldOut.png';
              soldOutImgDiv.appendChild(soldOutImg);
              // console.log($('#' + name))
              // soldOutImg.onload = function() {
              // var top = $('#' + name).position().top;
              // var top = $('#' + name).outerHeight();
              var top = document.getElementById(name).offsetTop;
              // console.log(top)
              var width = $('#' + name).width() / 4;
              // var width = $('#' + name).width() / 2 - soldOutImg.width / 4;
              // }
              var style = "width:50%;position:absolute;z-index:2;top:" + top
                  + "px;left:" + width + "px;transform:rotate(-30deg);";
              soldOutImgDiv.setAttribute('style', style);
              $('#' + name).after(soldOutImgDiv);
              $('#' + name).children().children().removeAttr("data-toggle");
            }
            modal("#myModal", food, 0);
          }
        }
      }
      $("#navBar a").on('click', function (event) {
        let target = $($(this).attr('href'));
        if (target.length) {
          let target_top = target.offset().top;
          let offsetHeight = document.getElementById(
              'navBarFixTop').offsetHeight;
          let goalTop = target_top - offsetHeight;
          $('html, body').animate({scrollTop: goalTop}, 800, function () {
          });
        }
      });
      if (param != null) {
        $.ajax({
          type: "POST",
          url: menuItemAPI_by_oneSet_id_url + param,
          dataType: "json",
          success: function (data) {
            console.log(data);
            // setFood
            let setFood = data[0];
            // setFood class name
            // var className = setFood.ClassName;
            // setFood name
            let name = setFood.Name;
            // setFood price
            let price = setFood.Price;
            // setFood img url
            // var imgUrl = setFood.url;
            let soldOut = setFood.SoldOut;

            if (!soldOut) {
              inject(name, price, 1);
            }
            // 滑動到餐點區塊
            let targetID = "#" + name;
            let target = $(targetID);
            if (target.length) {
              let target_top = target.offset().top;
              let offsetHeight = document.getElementById(
                  'navBarFixTop').offsetHeight;
              let goalTop = target_top - offsetHeight;
              $('html, body').animate({scrollTop: goalTop}, 800,
                  function () {
                    if (!soldOut) {
                      let hintModalId = "successHintModalId";
                      hintModal(hintModalId, "hint", name + "已成功加入訂單");
                      $('#successHintModalId').modal('toggle');
                    } else {
                      let hintModalId = "failedHintModalId";
                      hintModal(hintModalId, "hint", name + "賣完惹QQ");
                      $('#failedHintModalId').modal('toggle');
                    }
                  });
            }

          }
        });
      }
    },
    error: function () {
      console.log("can not GET")
    }
  });
});

function hintModal(hintModalId, title, msg) {
  $('#hintModal').append(
      '<div class="modal fade" id=' + hintModalId
      + ' tabindex="-1" role="dialog"\n'
      + '     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">\n'
      + '  <div class="modal-dialog modal-dialog-centered" role="document">\n'
      + '    <div class="modal-content">\n'
      + '      <div class="modal-header">\n'
      + '        <h5 class="modal-title" id="exampleModalLongTitle">' + title
      + '</h5>\n'
      + '        <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n'
      + '          <span aria-hidden="true">&times;</span>\n'
      + '        </button>\n'
      + '      </div>\n'
      + '      <div class="modal-body">\n'
      + '       ' + msg + '\n'
      + '      </div>\n'
      + '      <div class="modal-footer">\n'
      + '        <button type="button" class="btn btn-primary" data-dismiss="modal">確定</button>\n'
      + '      </div>\n'
      + '    </div>\n'
      + '  </div>\n'
      + '</div>');
}

function modal(place, food, number) {

  let className = food.ClassName;
  // food name
  let foodName = food.Name;
  let id = foodName + "Modal";

  $(place).append(
      '<div class="modal fade" id="' + id
      + '" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">'
      +
      '<div class="modal-dialog modal-dialog-centered" role="document">' +
      '<div class="modal-content">' +
      '<div class="modal-header">' +
      '<h5 class="modal-title" id="exampleModalLabel">' + foodName
      + '</h5>' +
      '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
      +
      '<span aria-hidden="true">&times;</span>' +
      '</button>' +
      '</div>' +
      '<div class="modal-body" id="' + id + 'modalBody">' +
      '<form>' +
      '<div class="form-group">' +
      '<label for="recipient-name" class="col-form-label">個數</label>' +
      '<div class="input-group mb-3">' +
      '<div class="input-group-append">' +
      '<button class="btn btn-outline-secondary" onClick=buttonMinus("'
      + id + '") type="button">-</button>' +
      '</div>' +
      '<input type="text" readonly disabled class="form-control" id="'
      + id + 'Number" value="' + number
      + '" aria-label="Recipients_username" aria-describedby="basic-addon2">'
      +
      '<div class="input-group-append">' +
      '<button class="btn btn-outline-secondary" onClick=buttonAdd("' + id
      + '") type="button">+</button>' +
      '</div>' +
      '</div>' +
      '</div>' +
      '</form>' +
      '</div>' +
      '<div class="modal-footer">' +
      '<button type="button" class="btn btn-secondary" onClick=buttonClean("'
      + id + '")>清除</button>' +
      '<button type="button" class="btn btn-primary" onClick=buttonConfirm("'
      + id + '","' + foodName + '") data-dismiss="modal">確認</button>' +
      '</div>' +
      '</div>' +
      '</div>' +
      '</div>');
  if (className === "套餐") {
    let target = "#" + id + "modalBody";
    let items = food.Item;
    $(target).prepend('<ol id="' + id + 'List" class="breadcrumb"></ol>');
    items.forEach(function (item) {
      $('#' + id + 'List').append(
          '<li class="breadcrumb-item">' + item + '</li>');
    })
  }
}

function buttonAdd(id) {
  let goal = "#" + id + "Number";
  let origin = parseInt($(goal).val());
  origin++;
  $(goal).val(origin);
}

function buttonMinus(id) {
  let goal = "#" + id + "Number";
  let origin = parseInt($(goal).val());
  if (origin - 1 >= 0) {
    origin--;
  }
  $(goal).val(origin);
}

function buttonClean(id) {
  let goal = "#" + id + "Number";
  $(goal).val(0);
}

function buttonConfirm(id, foodName) {
  let goal = "#" + id + "Number";
  let current = parseInt($(goal).val()); // meal amount
  let ans = "#" + id + "Answer";
  $(ans).val(current);
  let priceID = "#" + foodName + "Price";
  let price = parseInt($(priceID).text().split("$")[1]);

  let orderContentID = foodName + "OrderContent";

  let orderContentPriceID = foodName + "OrderContentPrice";
  let orderContentNumberID = foodName + "OrderContentNumber";
  let modalID = foodName + "Modal";

  let orderContentNameID = foodName + "OrderContentName";

  if (!document.getElementById(orderContentID)) { // 訂單無該餐點
    if (current) {

      $("#orderContent").append(
          '<ol id="' + orderContentID
          + '" class="breadcrumb" data-toggle="modal" data-target="#'
          + modalID + '">' +
          '<li class="breadcrumb-item active" id="' + orderContentNameID
          + '" aria-current="page">' + foodName + '</a></li>' +
          '<li class="breadcrumb-item active" id="' + orderContentPriceID
          + '" aria-current="page">NT$' + price + '</li>' +
          '<li class="breadcrumb-item active" id="' + orderContentNumberID
          + '" aria-current="page">' + current + '個</li>' +
          '</ol>'
      );
    }

  } else { // 訂單有該餐點
    if (current) { // 數量 不為 0
      console.log(current);
      let obj = document.getElementById(orderContentNumberID);
      obj.innerHTML = current + "個";
      // console.log(obj.innerHTML);
    } else {  // 數量 為 0
      let obj = document.getElementById(orderContentID);
      let parentObj = obj.parentNode;
      parentObj.removeChild(obj);
    }
  }
}

function inject(foodName, price, amount) {

  let orderContentID = foodName + "OrderContent";
  let orderContentPriceID = foodName + "OrderContentPrice";
  let orderContentNumberID = foodName + "OrderContentNumber";
  let modalID = foodName + "Modal";

  let answerID = "#" + modalID + "Answer";
  $(answerID).val(amount);

  let modalNumberID = "#" + modalID + "Number";
  $(modalNumberID).val(amount);
  let orderContentNameID = foodName + "OrderContentName";

  $("#orderContent").append(
      '<ol id="' + orderContentID
      + '" class="breadcrumb" data-toggle="modal" data-target="#'
      + modalID + '">' +
      '<li class="breadcrumb-item active" id="' + orderContentNameID
      + '" aria-current="page">' + foodName + '</a></li>' +
      '<li class="breadcrumb-item active" id="' + orderContentPriceID
      + '" aria-current="page">NT$' + price + '</li>' +
      '<li class="breadcrumb-item active" id="' + orderContentNumberID
      + '" aria-current="page">' + amount + '個</li>' +
      '</ol>'
  );
}

function generateMeals() {
  let meals = [];
  $("ol[id$='OrderContent']").each(function () {
    let name = $(this).find("li[id$='OrderContentName']")[0].innerHTML;
    let price = $(this).find(
        "li[id$='OrderContentPrice']")[0].innerHTML.split("$")[1];
    let amount = $(this).find(
        "li[id$='OrderContentNumber']")[0].innerHTML.split("個")[0];
    let targetUrl = "img[alt=" + name + "]";
    let url = $(targetUrl).attr("src");
    meals.push(meal(name, price, amount, url));
  });
  return meals;
}

function meal(name, price, amount, url) {
  return {
    "Name": name,
    "Price": price,
    "Amount": amount,
    "Url": url
  };
}

function getNowFormatDate() {
  let date = new Date();
  let seperator1 = "-";
  let seperator2 = ":";
  let month = date.getMonth() + 1;
  let strDate = date.getDate();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  return date.getFullYear() + seperator1 + month + seperator1 + strDate
      + " " + date.getHours() + seperator2 + date.getMinutes()
      + seperator2 + date.getSeconds();
}

function calculateTotalPrice(meals) {
  let totalPrice = 0;
  meals.forEach(function (meal) {
    totalPrice += parseInt(meal.Price) * parseInt(meal.Amount);
  });
  return totalPrice;
}

// fb messenger Extensions sdk
$(document).ready(function () {
  (function (d, s, id) {
    let js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
      return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/messenger.Extensions.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'Messenger'));

  window.extAsyncInit = () => {
    let psid;
    MessengerExtensions.getContext(fb_id,
        function success(thread_context) {
          // success
          psid = thread_context.psid; //get psid
          console.log("MessengerExtensions success");
        },
        function error(err) {
          // error
          // console.log(err);
          console.log("MessengerExtensions error");
        }
    );

    document.getElementById('submit').addEventListener('click', () => {
      MessengerExtensions.requestCloseBrowser(function success() {
        let date = getNowFormatDate();
        // console.log(date);
        sendOrder(psid, date);

        console.log("Webview closing");
      }, function error(err) {
        // console.log(err);
        console.log("Webview can not closing");
      });
    });
  }

  // // 未放進messenger >>測試<<
  // $('#submit').click(function () {
  //     let date = getNowFormatDate();
  //     console.log(date);
  //     var psid = Math.floor(100000 + Math.random() * 900000);
  //
  //     sendOrder(psid,date);
  //
  //     console.log("submit success");
  // })
});

function sendOrder(psid, date) {
  let order = generateOrder(psid, date);
  console.log(order);
  $.ajax({
    type: "POST",
    url: addOrderServlet_url,
    dataType: "json",
    data: order,
    success: function (data) {
      console.log("POST success");
      console.log(data);
    },
    error: function (data) {
      console.log(data);
      console.log("can not POST");
    }
  });
}

function generateOrder(psid, date) {
  let customerId = psid;
  let type;
  let number = parseInt(document.getElementById('tableNum').value);
  let note = $('#Note').val().replace(/\r\n|\n/g, "");
  let meals = generateMeals();
  let totalPrice = calculateTotalPrice(meals);
  let people = parseInt(document.getElementById('eaterNum').value);
  let orderId;
  if ($('#collapseTableNum').hasClass('show') && $(
      '#internalUse').hasClass('active')) {
    type = "內用";
    orderId = type + " " + number.toString();
  } else if (!$('#collapseTableNum').hasClass('show') && $(
      '#externalUse').hasClass('active')) {
    type = "外帶";
    orderId = type;
  }

  let order = {
    "CustomerID": customerId,
    "OrderID": orderId,
    "Status": "待製作",
    "Type": type,
    "Number": number,
    "date": date,
    "Note": note,
    "TotalPrice": totalPrice,
    "MyMenu": meals,
    "People": people
  };
  console.log(order);
  let jsonOrder = JSON.stringify(order);
  return jsonOrder;
}

function getRandomArray(n) {    //隨機產生不重覆的n個數字
  let rdmArray = [];     //儲存產生的陣列

  for (let i = 0; i < n; i++) {
    let rdm = 0;        //暫存的亂數
    let exist;
    do {
      exist = false;          //此亂數是否已存在
      rdm = Math.floor(Math.random() * amt);    //取得亂數

      //檢查亂數是否存在於陣列中，若存在則繼續回圈
      if (rdmArray.indexOf(rdm) !== -1) {
        exist = true;
      }

    } while (exist);    //產生沒出現過的亂數時離開迴圈
    rdmArray.push(rdm);
  }

  return rdmArray;
}
// this randomOrder button i put in the servlet.So,this func is actually referred.
function randomOrder() {
  for (let i = 0; i < 100; i++) {  // 產生一百筆隨機訂單
    let meals = [];
    let meal;
    let num = Math.floor(Math.random() * 4) + 2;  // 決定訂單裡要幾項餐點(2-5項)
    let index;
    let name, price, totalPrice = 0;

    index = getRandomArray(num);  // 決定是哪幾樣餐點
    index.forEach(function (val, index) {
      name = fn[val];
      price = fp[val];
      totalPrice += price;
      meal = {
        "Name": name,
        "Price": price,
        "Amount": 1
      };
      meals.push(meal);
    });

    let date = getNowFormatDate();
    let order = {
      "CustomerID": "2822127224464866",
      "OrderID": "內用 3",
      "Status": "已結帳",
      "Type": "內用",
      "Number": "3",
      "date": date,
      "Note": "",
      "TotalPrice": totalPrice,
      "MyMenu": meals,
      "People": 1
    };

    let jsonOrder = JSON.stringify(order);
    $.ajax({
      type: "POST",
      url: addOrderServlet_url,
      dataType: "json",
      data: jsonOrder,
      success: function (data) {
        console.log("POST success");
        console.log(data);
      },
      error: function (data) {
        console.log(data);
        console.log("can not POST");
      }
    });
  }
}

function enableExternalUseSubmit() {
  let orderFlag = false;

  // 有無點餐
  let length = document.getElementById(
      "orderContent").getElementsByTagName("ol").length;
  // console.log(length);
  if (length) { // 有點餐
    orderFlag = true;
  } else { // 無點餐
    orderFlag = false;
  }

  if (orderFlag) { // 打開按鈕
    // console.log("open button");
    $("#submit").attr("disabled", false);
  } else { // 關閉按鈕
    // console.log("close button");
    $("#submit").attr("disabled", true);
  }
}

function enableInternalUseSubmit() {
  let tableFlag = false;
  let peopleFlag = false;
  let orderFlag = false;

  // 有無用餐方式 桌號 人數
  let tableNum = document.getElementById('tableNum').value;
  // 桌號
  if (!tableNum) { // 沒填桌號
    tableFlag = false;
  } else {
    let r = /^[0-9]*[1-9][0-9]*$/;
    if (r.test(tableNum)) {
      tableFlag = true;
    } else { // 重新填桌號
      tableFlag = false;
    }
  }

  // 用餐人數
  let peopleNum = document.getElementById('eaterNum').value;
  if (!peopleNum) { // 沒填用餐人數
    peopleFlag = false;
  } else {
    let r = /^[0-9]*[1-9][0-9]*$/;
    if (r.test(peopleNum)) {
      peopleFlag = true;
    } else { // 重新填用餐人數
      peopleFlag = false;
    }
  }

  // 有無點餐
  let length = document.getElementById(
      "orderContent").getElementsByTagName("ol").length;
  // console.log(length);
  if (length) { // 有點餐
    orderFlag = true;
  } else { // 無點餐
    orderFlag = false;
  }

  if (tableFlag && peopleFlag && orderFlag) { // 打開按鈕
    // console.log("open button");
    $("#submit").attr("disabled", false);
  } else { // 關閉按鈕
    // console.log("close button");
    $("#submit").attr("disabled", true);
  }
}

// 拿url 參數
(function ($) {
  $.UrlParam = function (name) {
    //宣告正規表達式
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    /*
     * window.location.search 獲取URL ?之後的參數(包含問號)
     * substr(1) 獲取第一個字以後的字串(就是去除掉?號)
     * match(reg) 用正規表達式檢查是否符合要查詢的參數
    */
    let r = window.location.search.substr(1).match(reg);
    //如果取出的參數存在則取出參數的值否則回穿null
    if (r != null) {
      return unescape(r[2]);
    }
    return null;
  }
})(jQuery);


function handleCheckAdminModal() {
  if(!isAdminSectionOpen()){
    openCheckAdminModal();
  }else {
    closeAdminSection();
  }
}

function openCheckAdminModal() {
  let checkAdminHintModalId = 'checkAdminHintModalId';
  $('#hintModal').append(
      '<div class="modal fade" id=' + checkAdminHintModalId
      + ' tabindex="-1" role="dialog"\n'
      + '     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">\n'
      + '  <div class="modal-dialog modal-dialog-centered" role="document">\n'
      + '    <div class="modal-content">\n'
      + '      <div class="modal-header">\n'
      + '        <h5 class="modal-title" id="exampleModalLongTitle">' + "身份認證"
      + '</h5>\n'
      + '        <button type="button" class="close" onclick="closeCheckAdminModal()" aria-label="Close">\n'
      + '          <span aria-hidden="true">&times;</span>\n'
      + '        </button>\n'
      + '      </div>\n'
      + '      <div class="modal-body">\n'
      + '         <label id="CheckAdminId_Label"></label>\n'
      + '         <div>\n'
      + '           <label>帳號</label>\n'
      + '           <input id="CheckAdminId_Account" type="text"/>\n'
      + '         </div>\n'
      + '         <div>\n'
      + '           <label>密碼</label>\n'
      + '           <input id="CheckAdminId_Password" type="password"/>\n'
      + '         </div>\n'
      + '      </div>\n'
      + '      <div class="modal-footer">\n'
      + '        <button type="button" class="btn btn-primary" onclick="checkAdmin()">確定</button>\n'
      + '      </div>\n'
      + '    </div>\n'
      + '  </div>\n'
      + '</div>');
  $('#checkAdminHintModalId').modal('toggle');
}

function isAdminSectionOpen() {
  if($('#checkAdminModalButton').hasClass('openCheckAdminModalButton')){
    return true;
  }else {
    return false;
  }
}

function checkAdmin() {
  let account = $('#CheckAdminId_Account').val();
  let password = $('#CheckAdminId_Password').val();
  let json = {
    "username":account,
    "password":password
  };
  $.ajax({
    type: "POST",
    url: "CheckIdentityServlet",
    dataType: "json",
    data: JSON.stringify(json),
    success: function (data) {
      let success = data.success;
      if(success){
        let htmlContent = data.htmlContent.html;
        $('#adminSection').append(htmlContent);
        showPassIdentity();
        window.setTimeout(function () {
          closeCheckAdminModal();
        },1000);
        openAdminSection();
      } else {
        showErrorIdentity();
      }
    },
    error: function () {
    }
  });
}

function showPassIdentity() {
  $('#CheckAdminId_Label')[0].innerHTML = "pass";
}

function showErrorIdentity() {
  $('#CheckAdminId_Label')[0].innerHTML = "error";
}

function openAdminSection() {
  $('#checkAdminModalButton')[0].innerHTML = "關閉admin權限";
  $('#checkAdminModalButton').removeClass('closeCheckAdminModalButton');
  $('#checkAdminModalButton').addClass('openCheckAdminModalButton');
  $('#adminSection').show();
}

function closeAdminSection() {
  $('#checkAdminModalButton')[0].innerHTML = "開啟admin權限";
  $('#checkAdminModalButton').removeClass('openCheckAdminModalButton');
  $('#checkAdminModalButton').addClass('closeCheckAdminModalButton');
  $('#adminSection').empty();
  $('#adminSection').hide();
}

function closeCheckAdminModal() {
  $('#checkAdminHintModalId').modal('hide');
  $('body').removeClass('modal-open');
  $('.modal-backdrop').remove();
  $('#checkAdminHintModalId').remove();
}