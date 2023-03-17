<%@ page import="main.interfaces.LogInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String msg = (String) request.getAttribute("msg");
    if (msg == null) msg = "";
    String color = "brown";

%>
<html>
<head>
    <title>Welcome Courier</title>
    <style>
        #table1 {
            display: none;
        }

        #table2 {
            display: none;
        }

        #frame_ {
            margin-left: 20%;
            margin-top: 5%;
            width: 70%;
            height: 80%;
            background-color: #fadee2;
            border: black solid 2px;
        }

        #inner_ {
            margin-left: 15%;
            margin-top: 50px;
            width: 70%;
            height: 50%;
            background-color: #fadee2;
            /*border: black solid 2px;*/
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
    <div id="inner_">
        Welcome &nbsp ${logInfo.name()}
        <form method="get" action="${pageContext.request.contextPath}/login.jsp">
            <input type="submit" value="登出">
        </form>
        choose your operation: <br>
        <input type="radio" name="list" value="newItem" onclick="show(this)">Add a new item<br>
        <input type="radio" name="list" value="setItemState" onclick="show(this)">Change item state<br>
        <form id="table1" method="get" action="courierServlet">
            <table>
                <tr>
                    <th>name</th>
                    <th>class</th>
                    <th>price</th>
                </tr>
                <tr>
                    <td><input type="text" id="name1" name="1"></td>
                    <td><input type="text" id="class" name="2"></td>
                    <td><input type="text" id="price" name="3"></td>
                </tr>
                <tr>
                    <th>retrieval city</th>
                    <th>export city</th>
                    <th>import city</th>
                    <th>delivery city</th>
                </tr>
                <tr>
                    <td><input type="text" id="retrieval city" name="4"></td>
                    <td><input type="text" id="export city" name="5"></td>
                    <td><input type="text" id="import city" name="7"></td>
                    <td><input type="text" id="delivery city" name="8"></td>
                </tr>
            </table>
            <%
                LogInfo logInfo_test = (LogInfo) request.getAttribute("logInfo");
            %>
            <input type="submit" value="test submit for transport object" disabled>
        </form>
        <table id="table2">
            <tr>
                <th>name</th>
                <th>state</th>
            </tr>
            <tr>
                <td><input type="text" id="name2"></td>
                <td>
                    <select id="state">
                        <option value="">(未选择)</option>
                        <option>To-Export Transporting</option>
                        <option>Export Checking</option>
                        <option>From-Import Transporting</option>
                        <option>Delivering</option>
                        <option>Finish</option>
                    </select>
                </td>
            </tr>
        </table>
        <button type="submit" id="href_button">gogogo</button>
        <br><br>
        <span id="msg" style="color: <%=color%>; display:inline-block;width:200px;font-weight:bold;"><%=msg%></span>

        <script>
            document.getElementById("href_button").addEventListener('click', function () {
                let choose = document.getElementsByName("list")
                let param = null;
                for (let i = 0; i < choose.length; i++) {
                    if (choose[i].checked) {
                        switch (choose[i].value) {
                            case "newItem":
                                param =
                                    "&method=" + "newItem" +
                                    "&1=" + document.getElementById("name1").value +
                                    "&2=" + document.getElementById("class").value +
                                    "&3=" + document.getElementById("price").value +
                                    "&4=" + document.getElementById("retrieval city").value +
                                    "&5=" + document.getElementById("export city").value +
                                    "&6=" + document.getElementById("import city").value +
                                    "&7=" + document.getElementById("delivery city").value;
                                break;
                            case "setItemState":
                                param =
                                    "&method=" + "setItemState" +
                                    "&1=" + document.getElementById("name2").value +
                                    "&2=" + document.getElementById("state").value;
                                break;
                        }
                        <%LogInfo logInfo = (LogInfo) request.getAttribute("logInfo");%>
                        <%if(logInfo==null) logInfo = new LogInfo(null,null,null);%>
                        <%="window.location.href = \"../courierServlet?logName=" + logInfo.name() + "&logType=" + logInfo.type() + "&logPwd=" +logInfo.password() + "\"+param;"%>
                    }
                }
            }, false);

            function show(obj) {
                switch (obj.value) {
                    case "newItem":
                        document.getElementById("table1").style.display = "block";
                        document.getElementById("table2").style.display = "none";
                        break;
                    case "setItemState":
                        document.getElementById("table1").style.display = "none";
                        document.getElementById("table2").style.display = "block";
                        break;
                }
            }
        </script>
    </div>
</div>
</body>
</html>
