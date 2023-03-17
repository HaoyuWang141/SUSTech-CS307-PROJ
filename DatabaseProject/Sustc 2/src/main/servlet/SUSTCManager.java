package main.servlet;

import main.interfaces.LogInfo;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static main.DBManipulation.DB_MANIPULATION;
import static main.util.PostgresqlUtil.getLogInfo_from_JSP;

@WebServlet("/SUSTCManagerServlet")
public class SUSTCManager extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        LogInfo logInfo = getLogInfo_from_JSP(req);
        if (logInfo == null) {
            req.setAttribute("msg", "login error");
            req.getRequestDispatcher("/welcome/welcome_SeaportOfficer.jsp").forward(req, res);
            return;
        }
        switch (req.getParameter("method")) {
            case "": {
                String[] items = DB_MANIPULATION.getAllItemsAtPort(logInfo);
                if (items != null) {
                    req.setAttribute("msg", "success");
                } else {
                    req.setAttribute("msg", "not found any item at your port <br>\n 睡觉吧您嘞！");
                }
                break;
            }
        }
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
