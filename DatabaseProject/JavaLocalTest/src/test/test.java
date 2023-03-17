package test;

import main.DBManipulation;
import main.interfaces.LogInfo;
import main.util.PostgresqlUtil;

import java.io.IOException;

import static main.DBManipulation.DB_MANIPULATION;
import static test.LocalJudge.*;

public class test {
    public static void main(String[] args) throws IOException {
        DB_MANIPULATION.$import(PostgresqlUtil.readFile(recordsCSV),PostgresqlUtil.readFile(staffsCSV));
        DBManipulation d = new DBManipulation(database, root, pass);
        recordsCSV = "data/test_records.csv";
        staffsCSV = "./data/test_staffs.csv";
        d.$import(readFile(recordsCSV), readFile(staffsCSV));
        d.unloadItem(new LogInfo("e", LogInfo.StaffType.CompanyManager, "111111"), "test1");
        d.unloadItem(new LogInfo("e", LogInfo.StaffType.CompanyManager, "111111"), "test2");
    }
}
