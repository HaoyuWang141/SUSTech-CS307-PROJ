package main.model.dao.impl;

import main.entity.Staff;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.StaffDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class StaffDaoImpl implements StaffDao {
    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Staff> staffs = new ArrayList<>();
        long start_time;
        long end_time;
        Connection connection = null;
        try {
            connection = PostgresqlUtil.getConnectionRoot();
            if (verbose)
                System.out.println("CourierDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from courier";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table courier successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setId(resultSet.getInt("id"));
                staff.setCompany(resultSet.getString("company"));
                staff.setName(resultSet.getString("name"));
                staff.setGender(resultSet.getString("gender"));
                staff.setBirth_year(resultSet.getDate("birth_year"));
                staff.setPhone_number(resultSet.getString("phone_number"));
                staff.setCity(resultSet.getString("city"));
                staffs.add(staff);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return staffs.toArray(new Staff[0]);
    }

    @Override
    public void importData(boolean verbose) {
        Statement stmt = null;
        try {
            System.out.println("StaffDaoImpl importData() start");
            stmt = CON.createStatement();
            stmt.execute("TRUNCATE staff RESTART IDENTITY CASCADE;");

            // import from raw_data
            String sql = """
                    insert into staff(company, city, name, type, gender, birthday, phone_number)
                    select "Company", "City", "Name", "Type", "Gender",
                           ((substr(current_date::varchar, 1, 4)::integer - "Age")::varchar ||
                           substr(current_date::varchar, 6, 2) || substr(current_date::varchar, 9, 2)) :: date,
                           "Phone"
                    from raw_data_staffs;
                    """;
            stmt.execute(sql);
            System.out.println("Load Data into table staff SUCCESSFULLY.");
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
    public Staff[] getStaff(String loader_cnf, String staff_name) {
        String sql;
        ArrayList<Staff> staffs = new ArrayList<>();
        try {
            sql = "select * from staff where name like ?";
            ResultSet resultSet = execute_query(CON, sql, new String[]{staff_name});
            if (verbose)
                System.out.println("get staffs from table staff successfully");
            while (resultSet.next()) {
                staffs.add(new Staff(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getDate(7),
                        resultSet.getString(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffs.toArray(new Staff[0]);
    }

    @Override
    public Staff[] getStaff(String loader_cnf, int staff_id) {
        String sql;
        ArrayList<Staff> staffs = new ArrayList<>();
        try {
            sql = "select * from staff where id = ?";
            ResultSet resultSet = execute_query(CON, sql, new Integer[]{staff_id});
            if (verbose)
                System.out.println("get staffs from table staff successfully");
            while (resultSet.next()) {
                staffs.add(new Staff(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getDate(7),
                        resultSet.getString(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffs.toArray(new Staff[0]);
    }

    @Override
    public int getCourierCount(String loader_cnf) {
        String sql;
        int count = -1;
        try {
            sql = "select count(name) from staff where type like 'Courier'";
            ResultSet resultSet = execute_query(CON, sql, null);
            while (resultSet.next())
                count = resultSet.getInt(1);
            if (verbose)
                System.out.println("get courier count from table staff successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
