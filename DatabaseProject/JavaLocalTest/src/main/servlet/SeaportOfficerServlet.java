package main.servlet;

import main.interfaces.ItemInfo;
import main.interfaces.LogInfo;
import main.service.impl.CityTaxServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static main.DBManipulation.DB_MANIPULATION;
import static main.util.PostgresqlUtil.*;

@WebServlet("/seaportOfficerServlet")
public class SeaportOfficerServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        LogInfo logInfo = getLogInfo_from_JSP(req);
        if (logInfo == null) {
            req.setAttribute("msg", "login error");
            req.getRequestDispatcher("/welcome/welcome_SeaportOfficer.jsp").forward(req, res);
            return;
        }
        switch (req.getParameter("method")) {
            case "getAllItemsAtPort": {
                String[] items = DB_MANIPULATION.getAllItemsAtPort(logInfo);
                if (items != null) {
                    req.setAttribute("msg", "success");
                    req.setAttribute("items", items);
                } else {
                    req.setAttribute("msg", "not found any item at your port <br>\n 睡觉吧您嘞！");
                }
                break;
            }
            case "setItemCheckState": {
                String name = req.getParameter("1");
                String success_str = req.getParameter("2");
                boolean success;
                if (success_str.equals("true")) success = true;
                else if (success_str.equals("false")) success = false;
                else {
                    req.setAttribute("logInfo", logInfo);
                    req.setAttribute("msg", "Error: 未将success传入正确的bool类型！");
                    req.getRequestDispatcher("/welcome/welcome_SeaportOfficer.jsp").forward(req, res);
                    return;
                }
                if (DB_MANIPULATION.setItemCheckState(logInfo, name, success)) {
                    req.setAttribute("msg", "success");
                } else {
                    req.setAttribute("msg", "fail");
                }
                break;
            }
        }
        req.setAttribute("logInfo", logInfo);
        req.getRequestDispatcher("/welcome/welcome_SeaportOfficer.jsp").forward(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
