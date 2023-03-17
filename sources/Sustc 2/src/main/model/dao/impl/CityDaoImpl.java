package main.model.dao.impl;

import main.entity.City;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.CityDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class CityDaoImpl implements CityDao {
    private Connection connection = null;

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<City> cities = new ArrayList<>();
        long start_time;
        long end_time;
        try {
            connection = PostgresqlUtil.getConnection(verbose);
            if (verbose)
                System.out.println("CityDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from city";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table city successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                City city = new City();
                city.setName(resultSet.getString("name"));
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return cities.toArray(new City[0]);
    }

    @Override
    public void importData(boolean verbose) {
        try {
            System.out.println("CityDaoImpl importData() start");
            String sql = "truncate table city cascade;";
            execute_update(CON,sql,null);
            sql = """
                    insert into city(name)
                    select * from (
                        select "Retrieval City" from raw_data_records UNION
                        select "Delivery City" from raw_data_records UNION
                        select "Export City" from raw_data_records UNION
                        select "Import City" from raw_data_records
                        ) as t;
                    """;
            execute_update(CON,sql,null);
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
    public int getCityCount(String loader_cnf) {
        String sql;
        int count = -1;
        try {
            sql = "select count(name) from city";
            ResultSet resultSet = execute_query(CON, sql, null);
            if (verbose)
                System.out.println("get city count from table city successfully");
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
