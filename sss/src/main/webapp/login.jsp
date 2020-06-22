<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆</title>
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script type="application/javascript" src="/js/login.js"></script>
</head>
<body>
<form id="myForm" action="/login" method="POST" enctype="application/x-www-form-urlencoded">
    账号：<input name="username" type="text">
    密码：<input name="password" type="password">
    <input id="btn_login" type="submit" value="登陆">
</form>
</body>
</html>
