package main.model.dao.impl;

import main.entity.Entity;
import main.entity.Ship;
import main.enumcase.ExitStatus;
import main.model.dao.ShipDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class ShipDaoImpl implements ShipDao {
    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Ship> ships = new ArrayList<>();
        long start_time;
        long end_time;
        Connection connection = null;
        try {
            connection = PostgresqlUtil.getConnectionRoot();
            System.out.println("ShipDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from ship";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table ship successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                Ship ship = new Ship();
                ship.setName(resultSet.getString("name"));
                ship.setCompany(resultSet.getString("company"));
                ships.add(ship);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return ships.toArray(new Ship[0]);
    }

    @Override
    public void importData(boolean verbose) {
        try {
            execute_update(CON, "truncate table ship cascade;", null);
            String sql;
            sql = """
                    insert into ship(name, company, sailing)
                    select distinct "Ship Name", "Company Name", true
                    from raw_data_records
                    where "Ship Name" is not null and
                          "Item State" = 'Shipping';
                    """;
            execute_update(CON, sql, null);
            sql = """
                    insert into ship(name, company, sailing)
                    select "Ship Name", "Company Name", false
                    from raw_data_records
                    where "Ship Name" is not null
                    on conflict (name, company) do nothing;
                    """;
            execute_update(CON, sql, null);
            System.out.println("Load Data into table ship SUCCESSFULLY.");
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
    public boolean updateSailing(String loader_cnf, String ship_name, boolean sailing) {
        String sql;
        boolean flag = true;
        try {
            sql = "update ship set sailing = ? where name like ?";
            execute_update(CON, sql, new Object[]{sailing, ship_name});
            if (verbose)
                System.out.println("update sailing from table ship successfully");
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public int getCount(String loader_cnf) {
        String sql;
        int count = -1;
        try {
            sql = "select count(name) from ship";
            ResultSet resultSet = execute_query(CON, sql, null);
            while (resultSet.next())
                count = resultSet.getInt(1);
            if (verbose)
                System.out.println("get ship count from table ship successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Ship[] getShip(String loader_cnf, String ship_name) {
        String sql;
        ArrayList<Ship> ships = new ArrayList<>();
        try {
            sql = "select * from ship where name like ?";
            ResultSet resultSet = execute_query(CON, sql, new String[]{ship_name});
            if (verbose)
                System.out.println("get ships from table ship successfully");
            while (resultSet.next()) {
                ships.add(new Ship(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ships.toArray(new Ship[0]);
    }
}
