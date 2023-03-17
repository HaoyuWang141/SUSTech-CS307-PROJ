package main.servlet;

import main.interfaces.ItemInfo;
import main.interfaces.LogInfo;
import main.service.CityTaxService;
import main.service.impl.CityTaxServiceImpl;

import javax.persistence.SqlResultSetMapping;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.server.ExportException;

import static main.DBManipulation.DB_MANIPULATION;
import static main.util.PostgresqlUtil.*;

@WebServlet("/companyManagerServlet")
public class CompanyManagerServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        LogInfo logInfo = getLogInfo_from_JSP(req);
        if (logInfo == null) {
            req.setAttribute("msg", "login error");
            req.getRequestDispatcher("/welcome/welcome_CompanyManager.jsp").forward(req, res);
            return;
        }
        String city = req.getParameter("city");
        String item_class = req.getParameter("item_class");
        String item_name = req.getParameter("item_name");
        String container_code = req.getParameter("container_code");
        String ship_Name = req.getParameter("ship_Name");
        String method = null;
        try {
            method = req.getParameter("method");
            if (method == null) {
                throw new NullPointerException("parameter method is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        switch (method) {
            case "getExportTaxRate": {
                double exportTaxRate = DB_MANIPULATION.getExportTaxRate(logInfo, city, item_class);
                if (exportTaxRate != -1.) {
                    req.setAttribute("taxRate", "the tax rate of " + item_class + " in city " + city + " is <bar> " + exportTaxRate);
                    req.setAttribute("msg", "success");
                } else {
                    req.setAttribute("msg", "can not get the export tax of " + item_class + " in city" + city);
                }
                break;
            }
            case "getImportTaxRate": {
                double importTaxRate = DB_MANIPULATION.getImportTaxRate(logInfo, city, item_class);
                if (importTaxRate != -1.) {
                    req.setAttribute("taxRate", "the tax rate of " + item_class + " in city " + city + " is <bar> " + importTaxRate);
                    req.setAttribute("msg", "success");
                } else {
                    req.setAttribute("msg", "can not get the export tax of " + item_class + " in city" + city);
                }
                break;
            }
            case "loadItemToContainer": {
                if (DB_MANIPULATION.loadItemToContainer(logInfo, item_name, container_code))
                    req.setAttribute("msg", "success");
                else
                    req.setAttribute("msg", "fail");
                break;
            }
            case "loadContainerToShip": {
                if (DB_MANIPULATION.loadContainerToShip(logInfo, ship_Name, container_code))
                    req.setAttribute("msg", "success");
                else
                    req.setAttribute("msg", "fail");
                break;
            }
            case "shipStartSailing": {
                if (DB_MANIPULATION.shipStartSailing(logInfo, ship_Name))
                    req.setAttribute("msg", "success");
                else
                    req.setAttribute("msg", "fail");
                break;
            }
            case "unloadItem": {
                if (DB_MANIPULATION.unloadItem(logInfo, item_name))
                    req.setAttribute("msg", "success");
                else
                    req.setAttribute("msg", "fail");
                break;
            }
            case "itemWaitForChecking": {
                if (DB_MANIPULATION.itemWaitForChecking(logInfo, item_name))
                    req.setAttribute("msg", "success");
                else
                    req.setAttribute("msg", "fail");
                break;
            }
        }
        req.setAttribute("logInfo", logInfo);
        req.getRequestDispatcher("/welcome/welcome_CompanyManager.jsp").forward(req, res);
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
