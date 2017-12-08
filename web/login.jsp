<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>NutzBook demo</title>
    <!-- 导入jquery -->
    <script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/2.0.3/jquery-2.0.3.min.js"></script>
    <!-- 把user id复制到一个js变量 -->
    <script type="text/javascript">
        function login() {
            var username = "123";
            var psw = "123";
            var arr = {};
            var list = [];
            "[{"key":"v", "key": "v"...},{}...]"
            alert(username);
//            $.post(
//                "doLogin",
//                {
//                    username: username,
//                    psw: psw
//                },
//                function (data) {
//                    alert(data);
//                }
//            );
        }
    </script>
</head>
<body>
<%

%>
<div id="login_div">
    <form action="#" id="loginForm" method="post">
        用户名 <input name="username" type="text" value="admin">
        密码 <input name="password" type="password" value="123456">
        <button onclick="login()">提交</button>
    </form>
</div>
</body>
</html>