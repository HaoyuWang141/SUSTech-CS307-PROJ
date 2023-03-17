package main.servlet;

import main.interfaces.LogInfo;
import main.service.LogService;
import main.service.impl.LogServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static main.util.PostgresqlUtil.*;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String pwd = req.getParameter("pwd");
        System.out.println(name + " " + type + " " + pwd);
        if (name == null || type.equals("大哥大") || pwd == null) {
            req.setAttribute("error", "log information is wrong");
            req.getRequestDispatcher("login.jsp").forward(req, res);
            return;
        }
        LogInfo.StaffType staffType = getStaffType_from_String(type);
        LogInfo logInfo = new LogInfo(name, staffType, pwd);
        LogService logService = new LogServiceImpl();
        if (logService.check_logInfo(logInfo)) {
            // goto welcome.jsp
            req.setAttribute("logInfo", logInfo);
            String url = null;
            switch (Objects.requireNonNull(staffType)) {
                case SustcManager:
                    url = "/welcome/welcome_SUSTCManager.jsp";
                    break;
                case CompanyManager:
                    url = "/welcome/welcome_CompanyManager.jsp";
                    break;
                case Courier:
                    url = "/welcome/welcome_Courier.jsp";
                    break;
                case SeaportOfficer:
                    url = "/welcome/welcome_SeaportOfficer.jsp";
                    break;
            }
            req.getRequestDispatcher(url).forward(req, res);
        } else {
            // go back login.jsp and throw error
            req.setAttribute("error", "login fail");
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
//        super.service(req, res);
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
