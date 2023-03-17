<%@ page import="main.interfaces.LogInfo" %>
<%@ page import="main.entity.Log" %><%--
  Created by IntelliJ IDEA.
  User: Haoyu
  Date: 2022/12/22
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String msg = (String) request.getAttribute("msg");
    if (msg == null) msg = "";
    String[] items = (String[]) request.getAttribute("items");
    String items_output = "";
    String items_num = "";
    if (items != null && items.length > 0) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(items[0]);
        for (int i = 1; i < 10 && i < items.length; i++) {
            stringBuilder.append(", ").append(items[i]);
        }
        if (items.length > 10)
            stringBuilder.append("...");
        items_output = stringBuilder.toString();
        items_num = items.length + " items at your port in total, They are:";
    }
    String color = "brown";
%>
<html>
<head>
    <title>Welcome Seaport Officer</title>
    <style>
        #table2 {
            display: none;
        }

        #frame_ {
            margin-left: 200px;
            margin-top: 100px;
            width: 800px;
            height: 400px;
            background-color: #ddf1ed;
            border: black solid 2px;
        }

        #table_ {
            margin-left: 100px;
            margin-top: 50px;
            width: 300px;
            height: 300px;
        }

        #inner_main {
            width: 350px;
            height: 300px;
            background-color: #ddf1ed;
        }

        #inner_result {
            width: 300px;
            height: 300px;
            background-color: #e9f1ee;
        }
    </style>
    <%
        if (!msg.equals("")) {
    %>
    <script type="text/javascript">
        alert("<%=msg%>");
    </script>
    <% if (msg.equals("success")) color = "green";%>
    <% } %>
</head>
<body>
<div id="frame_">
    <table id="table_">
        <tr>
            <td>
                <div id="inner_main">
                    <b>Welcome &nbsp ${logInfo.name()}</b>
                    <form action="${pageContext.request.contextPath}/login.jsp" method="get">
                        <input type="submit" value="登出">
                    </form>
                    choose your operation:<br>
                    <input type="radio" name="list" value="getAllItemsAtPort" onclick="show(this)">Get all items at port<br>
                    <input type="radio" name="list" value="setItemCheckState" onclick="show(this)">Set items Check State<br>

                    <table id="table2">
                        <tr>
                            <th>item name</th>
                        </tr>
                        <tr>
                            <td><input type="text" id="name2"></td>
                        </tr>
                        <tr>
                            <th>checking state</th>
                        </tr>
                        <tr>
                            <td>
                                <input type="radio" name="success" checked>彳亍
                                <input type="radio" name="success">寄
                            </td>
                        </tr>
                    </table>
                    <br>
                    <button type="submit" id="href_button">安排</button>
                    <br><br>
                    <span id="msg" style="color: <%=color%>; display:inline-block;font-weight:bold;"><%=msg%></span>
                </div>
            </td>
            <td>
                <div id="inner_result">
                    <table id="table1">
                        <tr>
                            <th>Query Result:</th>
                        </tr>
                        <tr>
                            <td><%=items_num%>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <%=items_output%>
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>
    <script>
        document.getElementById("href_button").addEventListener('click', function () {
            let choose = document.getElementsByName("list")
            let param = null;
            for (let i = 0; i < choose.length; i++) {
                if (choose[i].checked) {
                    switch (choose[i].value) {
                        case "getAllItemsAtPort":
                            param = "&method=" + "getAllItemsAtPort";
                            break;
                        case "setItemCheckState":
                            param =
                                "&method=" + "setItemCheckState" +
                                "&1=" + document.getElementById("name2").value +
                                "&2=" + (document.getElementsByName("success"))[0].checked.toString();
                            break;
                    }
                    <%LogInfo logInfo = (LogInfo) request.getAttribute("logInfo");%>
                    <%if(logInfo==null) logInfo = new LogInfo(null,null,null);%>
                    <%="window.location.href = \"../seaportOfficerServlet?logName=" + logInfo.name() + "&logType=" + logInfo.type() + "&logPwd=" +logInfo.password() + "\"+param;"%>
                }
            }
        }, false);

        function show(obj) {
            switch (obj.value) {
                case "getAllItemsAtPort":
                    document.getElementById("table2").style.display = "none";
                    break;
                case "setItemCheckState":
                    document.getElementById("table2").style.display = "block";
                    break;
            }
        }
    </script>
</div>
</body>
</html>
