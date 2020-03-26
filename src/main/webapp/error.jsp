<%--
  Created by IntelliJ IDEA.
  User: berserker
  Date: 2019-07-07
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>沒有提供這個 服務喔</title>
</head>
<style>
    #center {
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -105px;
        margin-top: -170px;
    }
</style>
<body>
<div id="center">
    <h1>沒有提供這個 服務喔!!</h1>
    <div id='div1'>
        <h1 id="timeout"></h1>
    </div>
</div>

<script>
  //設定倒數秒數
  var t = 5;

  //顯示倒數秒收
  function showTime() {
    t -= 1;
    document.getElementById('timeout').innerHTML = "將在 "+t+" 秒轉向!!";

    if (t == 0) {
      window.location.href = "/rewabo"
    }

    //每秒執行一次,showTime()
    setTimeout("showTime()", 1000);
  }

  //執行showTime()
  showTime();

</script>
</body>
</html>
