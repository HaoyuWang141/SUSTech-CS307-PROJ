package main.servlet;

import main.DBManipulation;
import main.util.PostgresqlUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

import static main.DBManipulation.DB_MANIPULATION;

@WebServlet("/indexServlet")
public class indexServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            String operation = req.getParameter("operation");
            if (operation != null) {
                switch (operation) {
                    case "initial": {
                        String choice = req.getParameter("choice");
                        switch (choice) {
                            case "cnf": {
                                String user_name = req.getParameter("username");
                                String loader_cnf = "loader_" + user_name + ".cnf";
                                URL propertyURL = PostgresqlUtil.class.getResource("/main/util/" + loader_cnf);
                                if (propertyURL == null)
                                    throw new NullPointerException("No configuration file (" + loader_cnf + ") found, 数据库初始化失败");
                                BufferedReader conf = new BufferedReader(new FileReader(propertyURL.getPath()));
                                Properties properties = new Properties();
                                properties.load(conf);
                                String database = properties.getProperty("host") + "/" + properties.getProperty("database");
                                String root = properties.getProperty("user");
                                String pass = properties.getProperty("password");

                                DB_MANIPULATION = new DBManipulation(database, root, pass);
                                req.setAttribute("msg", "数据库初始化已完成");
                                break;
                            }
                            case "input": {
                                String host = req.getParameter("host");
                                String port = req.getParameter("port");
                                String database_name = req.getParameter("database");
                                String database = host + ":" + port + "/" + database_name;
                                String root = req.getParameter("user");
                                String pass = req.getParameter("password");
                                if (host != null && port != null && database_name != null && root != null && pass != null
                                    && !host.equals("") && !port.equals("") && !database_name.equals("") && !root.equals("") && !pass.equals(""))
                                    DB_MANIPULATION = new DBManipulation(database, root, pass);
                                else throw new NullPointerException("input is null");
                                req.setAttribute("msg", "数据库初始化已完成");
                                break;
                            }
                        }
                        break;
                    }
                    case "import": {
                        String path_records = req.getParameter("path_records");
                        String path_staffs = req.getParameter("path_staffs");
                            if (DB_MANIPULATION != null)
                                DB_MANIPULATION.$import(PostgresqlUtil.readFile(path_records), PostgresqlUtil.readFile(path_staffs));
                            else throw new NullPointerException("数据库未初始化，请先执行initial操作");
                        req.setAttribute("msg", "数据导入已完成");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            String str = "输入的路径不正确";
            req.setAttribute("msg", str);
        } catch (NullPointerException e) {
            e.printStackTrace();
            String str = e.getMessage();
            req.setAttribute("msg", str);
        } finally {
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        }
//        super.service(req, res);
    }
}

