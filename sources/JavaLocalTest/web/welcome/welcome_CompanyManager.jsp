<%@ page import="main.interfaces.LogInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LogInfo logInfo = (LogInfo) request.getAttribute("logInfo");
    if (logInfo == null) logInfo = new LogInfo(null, null, null);
    String taxRate = (String) request.getAttribute("taxRate");
    if (taxRate == null || taxRate.equals("null")) taxRate = "";
    String msg = (String) request.getAttribute("msg");
    if (msg == null) msg = "";
    String color = "brown";
%>
<html>
<head>
    <title>Welcome Company Manager</title>
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

        #form_getTaxRate {
            display: none;
        }

        #form_others {
            display: none;
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
                    <input type="radio" name="list" value="getImportTaxRate" onclick="show(this)">getImportTaxRate<br>
                    <input type="radio" name="list" value="getExportTaxRate" onclick="show(this)">getExportTaxRate<br>
                    <input type="radio" name="list" value="loadItemToContainer" onclick="show(this)">loadItemToContainer<br>
                    <input type="radio" name="list" value="loadContainerToShip" onclick="show(this)">loadContainerToShip<br>
                    <input type="radio" name="list" value="shipStartSailing" onclick="show(this)">shipStartSailing<br>
                    <input type="radio" name="list" value="unloadItem" onclick="show(this)">unloadItem<br>
                    <input type="radio" name="list" value="itemWaitForChecking" onclick="show(this)">itemWaitForChecking<br>
                    <span id="msg" style="color: <%=color%>; display:inline-block;font-weight:bold;"><%=msg%></span>
                    <br>
                    <form id="form_getTaxRate" action="${pageContext.request.contextPath}/companyManagerServlet"
                          method="get">
                        <table>
                            <tr>
                                <th>city</th>
                            </tr>
                            <tr>
                                <td><input class="td" type="text" name="city"></td>
                            </tr>
                            <tr>
                                <th>item class</th>
                            </tr>
                            <tr>
                                <td><input class="td" type="text" name="item_class"></td>
                            </tr>
                        </table>
                        <input type="text" name="logName" style="display: none" value="<%=logInfo.name()%>">
                        <input type="text" name="logType" style="display: none" value="<%=logInfo.type()%>">
                        <input type="text" name="logPwd" style="display: none" value="<%=logInfo.password()%>">
                        <input  id="method_getTaxRate" type="text" name="method" style="display: none">
                        <input type="submit" value="助教贼帅">
                    </form>
                    <form id="form_others" action="${pageContext.request.contextPath}/companyManagerServlet"
                          method="get">
                        <table>
                            <tr>
                                <th id="a1">item name</th>
                            </tr>
                            <tr>
                                <td><input class="td" id="a2" type="text" name="item_name" value=""></td>
                            </tr>
                            <tr>
                                <th id="b1">container code</th>
                            </tr>
                            <tr>
                                <td><input class="td" id="b2" type="text" name="container_code" value=""></td>
                            </tr>
                            <tr>
                                <th id="c1">ship name</th>
                            </tr>
                            <tr>
                                <td><input class="td" id="c2" type="text" name="ship_name" value=""></td>
                            </tr>
                        </table>
                        <input type="text" name="logName" style="display: none" value="<%=logInfo.name()%>">
                        <input type="text" name="logType" style="display: none" value="<%=logInfo.type()%>">
                        <input type="text" name="logPwd" style="display: none" value="<%=logInfo.password()%>">
                        <input id="method_others" type="text" name="method" style="display: none">
                        <input type="submit" value="喔喔喔">
                    </form>
                </div>
            </td>
            <td>
                <div id="inner_result">
                    <table id="table_result">
                        <tr>
                            <th>Query Result:</th>
                        </tr>
                        <tr>
                            <td>
                                <%=taxRate%>
                            </td>
                        </tr>
                    </table>
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
                document.getElementById("method_getTaxRate").value = "getExportTaxRate";
                break;
            case "getImportTaxRate":
                document.getElementById("form_getTaxRate").style.display = "block";
                document.getElementById("method_getTaxRate").value = "getImportTaxRate";
                break;
            default:
                document.getElementById("form_others").style.display = "block";
                switch (obj.value) {
                    case "loadItemToContainer":
                        document.getElementById("a1").style.display = "block";
                        document.getElementById("a2").style.display = "block";
                        document.getElementById("b1").style.display = "block";
                        document.getElementById("b2").style.display = "block";
                        document.getElementById("method_others").value = "loadItemToContainer";
                        break;
                    case "loadContainerToShip":
                        document.getElementById("b1").style.display = "block";
                        document.getElementById("b2").style.display = "block";
                        document.getElementById("c1").style.display = "block";
                        document.getElementById("c2").style.display = "block";
                        document.getElementById("method_others").value = "loadContainerToShip";
                        break;
                    case "shipStartSailing":
                        document.getElementById("c1").style.display = "block";
                        document.getElementById("c2").style.display = "block";
                        document.getElementById("method_others").value = "shipStartSailing";
                        break;
                    case "unloadItem":
                        document.getElementById("a1").style.display = "block";
                        document.getElementById("a2").style.display = "block";
                        document.getElementById("method_others").value = "unloadItem";
                        break;
                    case "itemWaitForChecking":
                        document.getElementById("a1").style.display = "block";
                        document.getElementById("a2").style.display = "block";
                        document.getElementById("method_others").value = "itemWaitForChecking";
                        break;
                }
                break;
        }
    }
</script>
</body>
</html>
