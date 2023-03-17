<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="login.css">
    <title>login</title>
    <style>
        #frame_ {
            width: 600px;
            height: 400px;
            margin-left: 300px;
            margin-top: 100px;
            background-color: azure;
            border: black solid 2px;
        }

        #inner_ {
            margin-left: 150px;
            margin-top: 150px;
            width: 50%;
            height: 50%;
            background-color: azure;
            /*border: black solid 2px;*/
        }
    </style>

</head>
<body>
<form method="GET" action="loginServlet" class="login">
    <p>Login</p>
    <input type="text" placeholder="name" name="name">
    <input type="password" placeholder="password" name="pwd">
    <select name="type">
        <option>大哥大</option>
        <option>Courier</option>
        <option>Company Manager</option>
        <option>SUSTC Department Manager</option>
        <option>Seaport Officer</option>
    </select>
    <input type="submit" class="btn" value="登  录">
</form>
<span id="error" style="color: darkred">${error}</span>
</body>
</html>