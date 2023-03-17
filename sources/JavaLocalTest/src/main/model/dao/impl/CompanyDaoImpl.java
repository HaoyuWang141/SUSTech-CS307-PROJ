package main.model.dao.impl;

import main.entity.Company;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.CompanyDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class CompanyDaoImpl implements CompanyDao {
    private Connection connection = null;

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Company> companies = new ArrayList<>();
        long start_time;
        long end_time;
        try {
            connection = PostgresqlUtil.getConnection(verbose);
            if (verbose)
                System.out.println("CompanyDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from company";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table company successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                Company company = new Company();
                company.setName(resultSet.getString("name"));
                companies.add(company);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return companies.toArray(new Company[0]);
    }

    @Override
    public void importData(boolean verbose) {
        try {
            System.out.println("CompanyDaoImpl importData() start");
            String sql = "truncate table company cascade;";
            execute_update(CON, sql, null);
            sql = """
                    insert into company(name)
                    select distinct "Company Name" from raw_data_records;
                    """;
            execute_update(CON, sql, null);
            System.out.println("Load Data into table company SUCCESSFULLY.");
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
    public int getCompanyCount(String loader_cnf) {
        String sql;
        int count = -1;
        try {
            sql = "select count(name) from company";
            ResultSet resultSet = execute_query(CON, sql, null);
            if (verbose)
                System.out.println("get company count from table company successfully");
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
