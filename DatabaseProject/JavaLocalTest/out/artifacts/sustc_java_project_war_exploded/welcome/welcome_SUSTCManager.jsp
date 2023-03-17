<%@ page import="main.interfaces.LogInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LogInfo logInfo = (LogInfo) request.getAttribute("logInfo");
    if (logInfo == null) logInfo = new LogInfo(null, null, null);
    String msg = (String) request.getAttribute("msg");
    if (msg == null) msg = "";
    String color = "brown";
%>
<html>
<head>
    <title>Welcome SUSTC Manager</title>
    <style>
        #frame_ {
            margin-left: 200px;
            margin-top: 50px;
            width: 800px;
            height: 500px;
            background-color: #e8d2dc;
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
        }

        #inner_result {
            width: 300px;
            height: 300px;
            background-color: #e9f1ee;
        }

        .td {
            width: 140px;
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
                    <input type="radio" name="list" value="getCompanyCount" onclick="show(this)">getCompanyCount<br>
                    <input type="radio" name="list" value="getCityCount" onclick="show(this)">getCityCount<br>
                    <input type="radio" name="list" value="getCourierCount" onclick="show(this)">getCourierCount<br>
                    <input type="radio" name="list" value="getShipCount" onclick="show(this)">getShipCount<br>
                    <input type="radio" name="list" value="getItemInfo" onclick="show(this)">getItemInfo<br>
                    <input type="radio" name="list" value="getShipInfo" onclick="show(this)">getShipInfo<br>
                    <input type="radio" name="list" value="getContainerInfo" onclick="show(this)">getContainerInfo<br>
                    <input type="radio" name="list" value="getStaffInfo" onclick="show(this)">getStaffInfo<br>
                    <span id="msg" style="color: <%=color%>; display:inline-block;font-weight:bold;"><%=msg%></span>
                    <br>
                    <form id="getInfo" action="${pageContext.request.contextPath}/SUSTCManagerServlet" method="get">
<%--                        <table>--%>
<%--                            <tr>--%>
<%--                                <th>--%>
<%--                                    item name--%>
<%--                                </th>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <th>--%>
<%--                                    ship name--%>
<%--                                </th>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <th>--%>
<%--                                    container code--%>
<%--                                </th>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <th>--%>
<%--                                    staff name--%>
<%--                                </th>--%>
<%--                            </tr>--%>
                        </table>
                    </form>
                </div>
            </td>
        </tr>
    </table>
</div>
<script>
    function show(obj) {
        document.getElementById("form_getTaxRate").style.display = "none";
        document.getElementById("form_others").style.display = "none";
        document.getElementById("a1").style.display = "none";
        document.getElementById("a2").style.display = "none";
        document.getElementById("b1").style.display = "none";
        document.getElementById("b2").style.display = "none";
        document.getElementById("c1").style.display = "none";
        document.getElementById("c2").style.display = "none";
        switch (obj.value) {
            case "getExportTaxRate":
                document.getElementById("form_getTaxRate").style.display = "block";
                break;
            case "getImportTaxRate":
                document.getElementById("form_getTaxRate").style.display = "block";
                break;
            default:
                document.getElementById("form_others").style.display = "block";
                switch (obj.value) {
                    case "loadItemToContainer":
                        document.getElementById("a1").style.display = "block";
                        document.getElementById("a2").style.display = "block";
                        document.getElementById("b1").style.display = "block";
                        document.getElementById("b2").style.display = "block";
                        document.getElementById("method").value = "loadItemToContainer";
                        break;
                    case "loadContainerToShip":
                        document.getElementById("b1").style.display = "block";
                        document.getElementById("b2").style.display = "block";
                        document.getElementById("c1").style.display = "block";
                        document.getElementById("c2").style.display = "block";
                        document.getElementById("method").value = "loadContainerToShip";
                        break;
                    case "shipStartSailing":
                        document.getElementById("c1").style.display = "block";
                        document.getElementById("c2").style.display = "block";
                        document.getElementById("method").value = "shipStartSailing";
                        break;
                    case "unloadItem":
                        document.getElementById("a1").style.display = "block";
                        document.getElementById("a2").style.display = "block";
                        document.getElementById("method").value = "unloadItem";
                        break;
                    case "itemWaitForChecking":
                        document.getElementById("a1").style.display = "block";
                        document.getElementById("a2").style.display = "block";
                        document.getElementById("method").value = "itemWaitForChecking";
                        break;
                }
                break;
        }
    }
</script>
</body>
</html>
