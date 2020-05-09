<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="application/javascript" src="/js/list.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            姓名：<input id="t_name" type="text" name="name">
            手机：<input id="t_phone" type="tel" name="phone">
            地址：<input id="t_address" type="text" name="address">
            <button id="btn_saveInfo" class="btn btn-primary btn-small" type="button">新增</button>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>
                        编号
                    </th>
                    <th>
                        姓名
                    </th>
                    <th>
                        手机号
                    </th>
                    <th>
                        地址
                    </th>
                    <th>
                        操作
                    </th>
                </tr>
                </thead>
                <tbody id="t_body">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
