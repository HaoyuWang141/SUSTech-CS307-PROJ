package main.model.dao.impl;

import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.StaffTypeDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.Statement;

import static main.util.PostgresqlUtil.CON;

public class StaffTypeDaoImpl implements StaffTypeDao {
    @Override
    public Entity[] loadData(boolean verbose) {
        return new Entity[0];
    }

    @Override
    public void importData(boolean verbose) {
        Statement stmt = null;
        try {
            System.out.println("StaffTypeDaoImpl importData() start");

            // Drop table
            stmt = CON.createStatement();
            stmt.execute("truncate table staff_type cascade;");

            // import from raw_data
            String sql = """
                    insert into staff_type(type)
                    select distinct "Type" from raw_data_staffs;
                    """;
            stmt.execute(sql);
            System.out.println("Load Data into table staff_type SUCCESSFULLY.");
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
}
