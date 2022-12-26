package main.model.dao.impl;

import main.entity.CityTax;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.CityTaxDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class CityTaxDaoImpl implements CityTaxDao {
    private Connection connection = null;

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<CityTax> cityTaxes = new ArrayList<>();
        long start_time;
        long end_time;
        try {
            connection = PostgresqlUtil.getConnection(verbose);
            if (verbose)
                System.out.println("CityTaxDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from city_tax";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table city_tax successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                CityTax cityTax = new CityTax();
                cityTax.setCity(resultSet.getString("city"));
                cityTax.setItem_type(resultSet.getString("item_type"));
                cityTax.setImport_tax_rate(resultSet.getDouble("import_tax_rate"));
                cityTax.setExport_tax_rate(resultSet.getDouble("export_tax_rate"));
                cityTaxes.add(cityTax);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return cityTaxes.toArray(new CityTax[0]);
    }

    @Override
    public void importData(boolean verbose) {
        try {
            System.out.println("CityTaxDaoImpl importData() start");
            String sql = "truncate table city_tax cascade;";
            execute_update(CON, sql, null);
            sql = """
                    insert into city_tax(city, item_type, export_tax_rate)
                    select distinct "Export City", "Item Class", ("Export Tax" / "Item Price") :: numeric(16, 6)
                    from raw_data_records
                    where "Export City" is not null
                    on conflict (city, item_type) do update set export_tax_rate = excluded.export_tax_rate;
                    """;
            execute_update(CON, sql, null);
            sql = """
                    insert into city_tax(city, item_type, import_tax_rate)
                    select distinct "Import City", "Item Class", ("Import Tax" / "Item Price") :: numeric(16, 6)
                    from raw_data_records
                    where "Import City" is not null
                    on conflict (city, item_type) do update set import_tax_rate = excluded.import_tax_rate;
                    """;
            execute_update(CON, sql, null);
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
    public CityTax[] getTaxRate(String loader_cnf, String city, String type) {
        ArrayList<CityTax> cityTaxes = new ArrayList<>();
        String sql;
        try {
            sql = "select * from city_tax where city like ? and item_type like ?";
            ResultSet resultSet = execute_query(CON, sql, new String[]{city, type});
            if (verbose)
                System.out.println("get data from table city_tax successfully");
            while (resultSet.next()) {
                cityTaxes.add(new CityTax(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getDouble(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return cityTaxes.toArray(new CityTax[0]);
    }
}
