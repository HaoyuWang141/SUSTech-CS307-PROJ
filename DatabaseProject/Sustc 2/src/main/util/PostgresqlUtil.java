package main.util;

import main.enumcase.ExitStatus;
import main.interfaces.ContainerInfo;
import main.interfaces.ItemState;
import main.interfaces.LogInfo;

import javax.servlet.ServletRequest;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static main.interfaces.ItemState.*;

public class PostgresqlUtil {
    //    public static String data_records;
//    public static String data_staffs;
    public static PostgresqlUtil postgresqlUtil = new PostgresqlUtil();
    public static Connection CON = null;
    public static final double ERROR = 1E-4;
    public static String database = null;
    public static String root = null;
    public static String pass = null;
    private static final String driver = "org.postgresql.Driver";
    private static final String LOADER = "loader_why.cnf";
    private static URL propertyURL = PostgresqlUtil.class.getResource(LOADER);

    public static boolean verbose = false;
    public static final int BATCH_SIZE = 500;

    public static String DATA_CSV_PATH = "./data/data.csv";
    public static String RAW_DATA_CSV_PATH = "./data/csv/raw_data.csv";
    public static String ALTERED_DATA_CSV_PATH = "./data/csv/altered_raw_data.csv";
    public static String CITY_CSV_PATH = "./data/csv/city.csv";
    public static String CITY_TAX_CSV_PATH = "./data/csv/city_tax.csv";
    public static String COMPANY_CSV_PATH = "./data/csv/company.csv";
    public static String CONTAINER_CSV_PATH = "./data/csv/container.csv";
    public static String COURIER_CSV_PATH = "./data/csv/courier.csv";
    public static String DELIVERY_CSV_PATH = "./data/csv/delivery.csv";
    public static String SHIP_CSV_PATH = "./data/csv/ship.csv";

    private PostgresqlUtil() {
    }

    public static int MAX_IMPORT_AMOUNT = 600000;
    private static Properties props = new Properties();

    public static Connection getConnectionRoot() {
        Connection connection = null;
        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(ExitStatus.ParameterException.getCode());
        }
        String url = "jdbc:postgresql://" + database;
        Properties props = new Properties();
        props.setProperty("user", root);
        props.setProperty("password", pass);
        try {
            connection = DriverManager.getConnection(url, props);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.IOException.getCode());
        }
        return connection;
    }

    public static Connection getConnection(String host, String dbname, String user, String pwd) {
        Connection connection = null;
        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(ExitStatus.ParameterException.getCode());
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            connection = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database " + dbname + " as " + user);
            }
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.IOException.getCode());
        }
        return connection;
    }

    public static Connection getConnection(String database, String user, String pwd) {
        Connection connection = null;
        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(ExitStatus.ParameterException.getCode());
        }
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + database + "?stringtype=unspecified", props);
            if (verbose) {
                System.out.println("Successfully connected to the database " + database + " as " + user);
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.IOException.getCode());
        }
        return connection;
    }

    public static Connection getConnection(String loader_cnf) {
        Connection connection = null;
        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(ExitStatus.SQLException.getCode());
        }

//        propertyURL = PostgresqlUtil.class.getResource("/main/util/" + loader_cnf);
//        if (propertyURL == null) {
//            System.err.println("No configuration file (" + loader_cnf + ") found");
//            System.exit(1);
//        }

//        try (BufferedReader conf = new BufferedReader(new FileReader(propertyURL.getPath()))) {
//            props.load(conf);
//        } catch (IOException e) {
//            System.err.println("No configuration file (" + loader_cnf + ") found");
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
        String path = "./properties/" + loader_cnf;
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
//            if (inputStream == null) throw new IOException("No configuration file (" + loader_cnf + ") found");
            props.load(fileInputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        }

        String url = "jdbc:postgresql://" + props.getProperty("host") + "/" + props.getProperty("database") + props.getProperty("stringtype");
        Properties props_login = new Properties();
        props_login.setProperty("user", props.getProperty("user"));
        props_login.setProperty("password", props.getProperty("password"));
        try {
            connection = DriverManager.getConnection(url, props_login);
            if (verbose) {
                System.out.println("Successfully connected to the database " + props.getProperty("database") + " as " + props.getProperty("user"));
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(3);
        }
        return connection;
    }

    public static Connection getConnection(boolean verbose) {
        Connection connection = null;
        try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(ExitStatus.SQLException.getCode());
        }

//        if (propertyURL == null) {
//            System.err.println("No configuration file (loader_why.cnf) found");
//            System.exit(1);
//        }

//        try (BufferedReader conf = new BufferedReader(new FileReader(propertyURL.getPath()))) {
//            props.load(conf);
//        } catch (IOException e) {
//            System.err.println("No configuration file (loader_why.cnf) found");
//        }

        InputStream inputStream = postgresqlUtil.getClass().getResourceAsStream(LOADER);
        try {
            if (inputStream == null) throw new IOException("No configuration file (" + LOADER + ") found");
            props.load(inputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        String url = "jdbc:postgresql://" + props.getProperty("host") + "/" + props.getProperty("database") + props.getProperty("stringtype");
        Properties props_login = new Properties();
        props_login.setProperty("user", props.getProperty("user"));
        props_login.setProperty("password", props.getProperty("password"));
        try {
            connection = DriverManager.getConnection(url, props_login);
            if (verbose) {
                System.out.println("Successfully connected to the database " + props.getProperty("database") + " as " + props.getProperty("user"));
            }
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(3);
        }
        return connection;
    }

    public static void closeResource(AutoCloseable... autoCloseable) {
        for (AutoCloseable resource : autoCloseable) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (SQLException e) {
                    System.err.println("Close Database(Postgresql) Resource Failed: \n");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * close resource and close database
     */

    // Query
    public static ResultSet execute_query(Connection connection, String sql, Object[] params) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null)
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return resultSet;
    }

    // Insert, Update, Delete, DDL
    public static int execute_update(Connection connection, String sql, Object[] params) throws SQLException {
        int updateRows = 0;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null)
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            updateRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return updateRows;
    }

    // 该方法用来将占位符替换为指定类型的数据，如果str=""，则替换为指定类型(sqlType)的null
    // "replace_placeholder" is
    public static void replace_placeholder(PreparedStatement prestmt, int parameterIndex, String str, int sqlType) {
        try {
            if (str.equals("")) prestmt.setNull(parameterIndex, sqlType);
            else
                switch (sqlType) {
                    case Types.VARCHAR:
                    case Types.CHAR:
                        prestmt.setString(parameterIndex, str);
                        break;
                    case Types.INTEGER:
                        prestmt.setInt(parameterIndex, (int) (Double.parseDouble(str)));
                        break;
                    case Types.NUMERIC:
                        prestmt.setDouble(parameterIndex, Double.parseDouble(str));
                        break;
                    case Types.DATE:
                        prestmt.setDate(parameterIndex, Date.valueOf(str));
                        break;
                    case Types.TIMESTAMP:
                        prestmt.setTimestamp(parameterIndex, Timestamp.valueOf(str));
                        break;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replace_placeholder(PreparedStatement prestmt, int parameterIndex, Serializable field) {
        try {
            int sqlType = getSqlType(field);
            if (field == null) prestmt.setNull(parameterIndex, sqlType);
            else
                switch (sqlType) {
                    case Types.VARCHAR:
                    case Types.CHAR:
                        prestmt.setString(parameterIndex, (String) field);
                        break;
                    case Types.INTEGER:
                        prestmt.setInt(parameterIndex, (Integer) field);
                        break;
                    case Types.NUMERIC:
                        prestmt.setDouble(parameterIndex, (Double) field);
                        break;
                    case Types.DATE:
                        prestmt.setDate(parameterIndex, (Date) field);
                        break;
                    case Types.TIMESTAMP:
                        prestmt.setTimestamp(parameterIndex, (Timestamp) field);
                        break;
                }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> get_types(String table_name, boolean verbose) {
        ArrayList<Integer> type_arr = new ArrayList<>();
        Connection con = getConnection(verbose);
        String pre_sql = "SELECT column_name, data_type, ordinal_position, is_nullable\n" +
                         "FROM information_schema.\"columns\"\n" +
                         "WHERE \"table_name\"=?\n" +
                         "ORDER BY ordinal_position;";
        ResultSet resultSet;
        try {
            PreparedStatement prestmt = con.prepareStatement(pre_sql);
            prestmt.setString(1, table_name);
            resultSet = prestmt.executeQuery();
            while (resultSet.next()) {
                type_arr.add(resultSet.getInt("data_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type_arr;
    }

    public static int getSqlType(Serializable field) {
        int type = Types.NULL;
        if (field != null)
            switch (field.getClass().getName()) {
                case "java.lang.String":
                    type = Types.VARCHAR;
                    break;
                case "java.lang.Integer":
                    type = Types.INTEGER;
                    break;
                case "java.lang.Double":
                    type = Types.NUMERIC;
                    break;
                case "java.main.sql.Date":
                    type = Types.DATE;
                    break;
                case "java.main.sql.Timestamp":
                    type = Types.TIMESTAMP;
                    break;
            }
        return type;
    }

    public static String readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(l -> {
            sb.append(l);
            sb.append("\n");
        });
        return sb.toString();
    }

    public static String readFile(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder buffer = new StringBuilder();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public static ContainerInfo.Type getContainerType_from_String(String type) {
        ContainerInfo.Type result_type = null;
        try {
            if (type == null) throw new NullPointerException("input type is null");
            String[] tmp = type.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < tmp.length - 1; i++) {
                stringBuilder.append(tmp[i]);
            }
            result_type = ContainerInfo.Type.valueOf(stringBuilder.toString());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result_type;
    }

    public static LogInfo.StaffType getStaffType_from_String(String type) {
        if (type == null || type.equals("null")) return LogInfo.StaffType.Null;
        String tmp = type.replace(" ", "");
        LogInfo.StaffType staffType = LogInfo.StaffType.Null;
        try {
            if (tmp.equals("SUSTCDepartmentManager"))
                staffType = LogInfo.StaffType.SustcManager;
            else staffType = LogInfo.StaffType.valueOf(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffType;
    }

    public static ItemState getItemState_from_String(String state) {
        if (state != null) {
            try {
                switch (state) {
                    case "Import Checking":
                        return ImportChecking;
                    case "Import Check Fail":
                        return ImportCheckFailed;
                    case "Export Checking":
                        return ExportChecking;
                    case "Export Check Fail":
                        return ExportCheckFailed;
                    case "Unpacking from Container":
                        return UnpackingFromContainer;
                    case "Delivering":
                        return Delivering;
                    case "To-Export Transporting":
                        return ToExportTransporting;
                    case "Finish":
                        return Finish;
                    case "Shipping":
                        return Shipping;
                    case "From-Import Transporting":
                        return FromImportTransporting;
                    case "Waiting for Shipping":
                        return WaitingForShipping;
                    case "Packing to Container":
                        return PackingToContainer;
                    case "Picking-up":
                        return PickingUp;
                    default:
                        throw new Exception("input state is illegal");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static LogInfo getLogInfo_from_JSP(ServletRequest req) {
        LogInfo logInfo = null;
        String logName = req.getParameter("logName");
        LogInfo.StaffType logType = getStaffType_from_String(req.getParameter("logType"));
        String logPwd = req.getParameter("logPwd");
        if (logName != null && !logName.equals("null") && !logName.equals("")
            && logType != LogInfo.StaffType.Null
            && logPwd != null && !logPwd.equals("null") && !logPwd.equals("")) {
            logInfo = new LogInfo(logName, logType, logPwd);
        }
        return logInfo;
    }

    public static void store_cnf(String host_database, String user, String pass, String fileName) throws IOException {
        Properties properties = new Properties();
        File file = new File("./properties");
        if (!file.exists() && !file.isDirectory()) {
            if (file.mkdir()) System.out.println("new dir \"properties\" has been created");
        }
        try (FileOutputStream outputFile = new FileOutputStream("./properties/" + fileName)) {
            String[] tmp = host_database.split("/");
            String host = tmp[0];
            String database = tmp[1];
            properties.setProperty("host", host);
            properties.setProperty("database", database);
            properties.setProperty("stringtype", "?stringtype=unspecified");
            properties.setProperty("user", user);
            properties.setProperty("password", pass);
            properties.store(outputFile, "111");
        } catch (IOException | NullPointerException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
