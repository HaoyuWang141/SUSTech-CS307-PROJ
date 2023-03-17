<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String msg = (String) request.getAttribute("msg");
    if (msg == null) msg = "";
    String color = "brown";
%>
<html>
<head>
    </style>
    <%
        if (!msg.equals("")) {
    %>
    <script type="text/javascript">
        alert("<%=msg%>");
    </script>
    <% if (msg.equals("数据导入已完成") || msg.equals("数据库初始化已完成")) color = "green";%>
    <% } %>
    <title>index</title>
    <style>
        #frame_ {
            margin-left: 200px;
            margin-top: 100px;
            width: 800px;
            height: 400px;
            background-color: antiquewhite;
            border: black solid 2px;
        }

        #inner_ {
            margin-left: 150px;
            margin-top: 50px;
            width: 600px;
            height: 300px;
            background-color: antiquewhite;
            /*border: black solid 2px;*/
        }

        .csv {
            width: 400px;
        }

        #table_input {
            display: none;
        }

    </style>
</head>
<body>
<div id="frame_">
    <div id="inner_">
        <form method="get" action="${pageContext.request.contextPath}/indexServlet">
            <input type="text" name="operation" value="initial" style="display: none">
            <input type="submit" value="initial"> &nbsp 初始化数据库（建表，建trigger，生成用户并赋权限）<br>
            <input type="radio" name="choice" value="cnf" onclick="show(this)" checked>use cnf
            <input type="radio" name="choice" value="input" onclick="show(this)">input
            <table id="table_cnf">
                <tr>
                    <th>
                        <b>the username of your cnf:</b>
                    </th>
                    <td>
                        <input type="text" name="username" value="why">
                    </td>
                </tr>
            </table>
            <table id="table_input">
                <tr>
                    <th>
                        host:
                    </th>
                    <td>
                        <input type="text" name="host" value="localhost">
                    </td>
                    <th>
                        database:
                    </th>
                    <td>
                        <input type="text" name="database" value="project2">
                    </td>
                </tr>
                <tr>
                    <th>
                        port:
                    </th>
                    <td>
                        <input type="text" name="port" value="5432">
                    </td>
                    <th>
                        user:
                    </th>
                    <td>
                        <input type="text" name="user" value="postgres">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <th>
                        password:
                    </th>
                    <td>
                        <input type="password" name="password" value="111111">
                    </td>
                </tr>
            </table>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/indexServlet">
            <input type="text" name="operation" value="import" style="display: none">
            <input type="submit" value="import"> &nbsp; 导入数据
            <table>
                <tr>
                    <th>
                        records.csv的路径：
                    </th>
                    <td>
                        <input type="text" name="path_records" class="csv"
                               value="F:\0course\database\project2\JavaLocalTest\data\test_records.csv">
                    </td>
                </tr>
                <tr>
                    <th>
                        staffs.csv的路径：
                    </th>
                    <td>
                        <input type="text" name="path_staffs" class="csv"
                               value="F:\0course\database\project2\JavaLocalTest\data\test_staffs.csv">
                    </td>
                </tr>
            </table>

        </form>
        <form method="get" action="${pageContext.request.contextPath}/login.jsp">
            <button>login</button> &nbsp; 跳转至登录页面
        </form>
        <span style="color: <%=color%>; display:inline-block;width:400px;font-weight:bold;"><%=msg%></span>
    </div>
</div>
<script>
    function show(obj) {
        document.getElementById("table_cnf").style.display = "none";
        document.getElementById("table_input").style.display = "none";
        switch (obj.value) {
            case "cnf":
                document.getElementById("table_cnf").style.display = "block";
                break;
            case "input":
                document.getElementById("table_input").style.display = "block";
                break;
        }
    }
</script>
</body>
</html>
