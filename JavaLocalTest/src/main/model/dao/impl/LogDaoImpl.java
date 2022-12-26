package main.model.dao.impl;

import main.entity.Entity;
import main.entity.Log;
import main.enumcase.ExitStatus;
import main.model.dao.LogDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class LogDaoImpl implements LogDao {

    @Override
    public Entity[] loadData(boolean verbose) {
        return new Entity[0];
    }

    @Override
    public void importData(boolean verbose) {
        try {
            System.out.println("LogDaoImpl importData() start");
            execute_update(CON, "truncate table log cascade;", null);
            String sql = """
                    insert into log(staff_id, password)
                    select staff.id, raw_data_staffs."Password"
                    from staff join raw_data_staffs
                         on staff.name = raw_data_staffs."Name" and staff.type = raw_data_staffs."Type";
                    """;
            execute_update(CON, sql, null);
            System.out.println("Load Data into table log SUCCESSFULLY.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        }
    }

    @Override
    public void insert(boolean verbose, Entity[] entities) {

    }

    @Override
    public void dropAll(boolean verbose) {

    }

    @Override
    public Log[] getLog(String loader_cnf, int staff_id) {
        String sql;
        ArrayList<Log> logs = new ArrayList<>();
        try {
            sql = "select * from log where staff_id = ?";
            ResultSet resultSet = execute_query(CON, sql, new Integer[]{staff_id});
            if (verbose)
                System.out.println("get logs from table log by staff id successfully");
            while (resultSet.next()) {
                logs.add(new Log(resultSet.getInt(1),
                        resultSet.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs.toArray(new Log[0]);
    }
}
