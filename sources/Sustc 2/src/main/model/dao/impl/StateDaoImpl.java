package main.model.dao.impl;

import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.StateDao;

import java.sql.Statement;

import static main.util.PostgresqlUtil.CON;

public class StateDaoImpl implements StateDao {
    @Override
    public Entity[] loadData(boolean verbose) {
        return new Entity[0];
    }

    @Override
    public void importData(boolean verbose) {
        Statement stmt = null;
        try {
            System.out.println("StateDaoImpl importData() start");
            stmt = CON.createStatement();
            stmt.execute("truncate table state cascade;");
            String sql = """
                    insert into state(name)
                    select distinct "Item State" from raw_data_records;
                    """;
            stmt.execute(sql);
            System.out.println("Load Data into table state SUCCESSFULLY.");
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
