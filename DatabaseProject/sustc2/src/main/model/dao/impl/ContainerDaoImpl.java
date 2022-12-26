package main.model.dao.impl;

import main.entity.Container;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.ContainerDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class ContainerDaoImpl implements ContainerDao {
    private Connection connection = null;

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Container> containers = new ArrayList<>();
        long start_time;
        long end_time;
        try {
            connection = PostgresqlUtil.getConnectionRoot();
            if (verbose)
                System.out.println("ContainerDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from container";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table container successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                Container container = new Container();
                container.setCode(resultSet.getString("code"));
                container.setType(resultSet.getString("type"));
                containers.add(container);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return containers.toArray(new Container[0]);
    }

    @Override
    public void importData(boolean verbose) {
        try {
            System.out.println("ContainerDaoImpl importData() start");
            String sql = "truncate table container cascade;";
            execute_update(CON, sql, null);
            sql = """
                    insert into container(code, type, "using")
                    select distinct "Container Code", "Container Type", true
                    from raw_data_records
                    where "Container Code" is not null and
                          "Item State" in ('Packing to Container', 'Waiting for Shipping', 'Shipping');
                    """;
            execute_update(CON, sql, null);
            sql = """
                    insert into container(code, type, "using")
                    select "Container Code", "Container Type", false
                    from raw_data_records
                    where "Container Code" is not null
                    on conflict (code) do nothing;
                    """;
            execute_update(CON, sql, null);
            System.out.println("Load Data into table container SUCCESSFULLY.");
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
    public Container[] getContainer(String loader_cnf, String container_code) {
        String sql;
        ArrayList<Container> containers = new ArrayList<>();
        try {
            sql = "select * from container where code like ?";
            ResultSet resultSet = execute_query(CON, sql, new String[]{container_code});
            if (verbose)
                System.out.println("get city count from table city successfully");
            while (resultSet.next()) {
                containers.add(new Container(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return containers.toArray(new Container[0]);
    }
}
