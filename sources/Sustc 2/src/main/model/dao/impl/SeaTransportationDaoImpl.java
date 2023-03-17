package main.model.dao.impl;

import main.entity.Entity;
import main.entity.SeaTransportation;
import main.enumcase.ExitStatus;
import main.model.dao.SeaTransportationDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class SeaTransportationDaoImpl implements SeaTransportationDao {
    @Override
    public Entity[] loadData(boolean verbose) {
        return new Entity[0];
    }

    @Override
    public void importData(boolean verbose) {
        try {
            System.out.println("SeaTransportationDaoImpl importData() start");
            execute_update(CON, "truncate table sea_transportation cascade;", null);
            String sql = """
                    insert into sea_transportation(item_name, container_code, ship_name, company)
                    select item_name, container_code, ship_name, company
                    from delivery
                    where container_code is not null and
                          state in ('Packing to Container', 'Waiting for Shipping', 'Shipping');
                    """;
            execute_update(CON, sql, null);
            System.out.println("Load Data into table sea_transportation SUCCESSFULLY.");
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
    public SeaTransportation[] getSeaTransportation_by_containerCode(String loader_cnf, String container_code) {
        String sql;
        ArrayList<SeaTransportation> seaTransportations = new ArrayList<>();
        try {
            sql = "select * from sea_transportation where container_code like ?";
            ResultSet resultSet = execute_query(CON, sql, new String[]{container_code});
            if (verbose)
                System.out.println("get sea transportation records from table sea_transportation successfully");
            while (resultSet.next()) {
                seaTransportations.add(new SeaTransportation(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seaTransportations.toArray(new SeaTransportation[0]);
    }

    @Override
    public boolean updateShip_by_containerCode(String loader_cnf, String container_code, String ship_name) {
        String sql;
        boolean flag = true;
        try {
            sql = "update sea_transportation set ship_name = ? where container_code like ?";
            execute_update(CON, sql, new String[]{ship_name, container_code});
            if (verbose)
                System.out.println("update ship_name from table sea_transportation successfully");
        } catch (SQLException e) {
            flag = false;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public SeaTransportation[] getSeaTransportation_by_itemName(String loader_cnf, String item_name) {
        String sql;
        ArrayList<SeaTransportation> seaTransportations = new ArrayList<>();
        try {
            sql = "select * from sea_transportation where item_name like ?";
            ResultSet resultSet = execute_query(CON, sql, new String[]{item_name});
            if (verbose)
                System.out.println("get sea transportation records from table sea_transportation successfully");
            while (resultSet.next()) {
                seaTransportations.add(new SeaTransportation(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seaTransportations.toArray(new SeaTransportation[0]);
    }

    @Override
    public boolean delete_by_ship(String loader_cnf, String ship_name) {
        String sql;
        boolean flag = true;
        try {
            sql = "delete from sea_transportation where ship_name like ?";
            execute_update(CON, sql, new String[]{ship_name});
            if (verbose)
                System.out.println("delete some records by ship name from table sea_transportation successfully");
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean delete_by_item(String loader_cnf, String item_name) {
        String sql;
        boolean flag = true;
        try {
            sql = "delete from sea_transportation where item_name like ?";
            execute_update(CON, sql, new String[]{item_name});
            if (verbose)
                System.out.println("delete a record by item name from table sea_transportation successfully");
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public int getItemCount_by_Ship(String loader_cnf, String ship_name) {
        String sql;
        int result = -1;
        try {
            sql = "select count(item_name) from sea_transportation where ship_name like ?";
            ResultSet resultSet = execute_query(CON, sql, new String[]{ship_name});
            if (verbose)
                System.out.println("get count of items in ship " + ship_name + " from table sea_transportation successfully");
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
