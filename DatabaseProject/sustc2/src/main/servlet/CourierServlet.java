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

@WebServlet("/courierServlet")
public class CourierServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        LogInfo logInfo = getLogInfo_from_JSP(req);
        if (logInfo == null) {
            req.setAttribute("msg", "login error");
            req.getRequestDispatcher("/welcome/welcome_Courier.jsp").forward(req, res);
            return;
        }
        switch (req.getParameter("method")) {
            case "newItem": {
                String name = req.getParameter("1");
                String $class = req.getParameter("2");
                String price_str = req.getParameter("3");
                double price = -1;
                if (price_str != null)
                    price = Double.parseDouble(price_str);
                String r_city = req.getParameter("4");
                String e_city = req.getParameter("5");
                String i_city = req.getParameter("6");
                String d_city = req.getParameter("7");
                CON = getConnectionRoot();
                double importTax = new CityTaxServiceImpl().getExportTaxRate(null, i_city, $class);
                double exportTax = new CityTaxServiceImpl().getExportTaxRate(null, e_city, $class);
                closeResource(CON);
                ItemInfo.RetrievalDeliveryInfo r = new ItemInfo.RetrievalDeliveryInfo(r_city, null);
                ItemInfo.RetrievalDeliveryInfo d = new ItemInfo.RetrievalDeliveryInfo(d_city, null);
                ItemInfo.ImportExportInfo i = new ItemInfo.ImportExportInfo(i_city, null, importTax);
                ItemInfo.ImportExportInfo e = new ItemInfo.ImportExportInfo(e_city, null, exportTax);
                ItemInfo itemInfo = new ItemInfo(name, $class, price, null, r, d, i, e);
                if (DB_MANIPULATION.newItem(logInfo, itemInfo)) {
                    req.setAttribute("msg", "success");
                } else {
                    req.setAttribute("msg", "fail");
                }
                break;
            }
            case "setItemState": {
                String name = req.getParameter("1");
                String state = req.getParameter("2");
                if (DB_MANIPULATION.setItemState(logInfo, name, getItemState_from_String(state))) {
                    req.setAttribute("msg", "success");
                } else {
                    req.setAttribute("msg", "fail");
                }
                break;
            }
        }
        req.setAttribute("logInfo", logInfo);
        req.getRequestDispatcher("/welcome/welcome_Courier.jsp").forward(req, res);
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
