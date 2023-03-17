package main.model.dao.impl;

import main.entity.Entity;
import main.entity.RawData;
import main.enumcase.ExitStatus;
import main.model.dao.RawDataDao;
import main.util.PostgresqlUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class RawDataDaoImpl implements RawDataDao {
    private static final int OUTPUT_COUNT = 50000;

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<RawData> rawData_list = new ArrayList<>();
        long start_time;
        long end_time;
        Connection connection = null;
        try {
            connection = PostgresqlUtil.getConnection(verbose);
            if (verbose)
                System.out.println("RawDataDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from raw_data";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table raw_data successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                RawData rawData = new RawData();
                System.err.println("lksajdflksadfjlkasdjfal");
                rawData_list.add(rawData);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return rawData_list.toArray(new RawData[0]);
    }

    @Override
    public void insert(boolean verbose, Entity[] entities) {

    }

    @Override
    public void dropAll(boolean verbose) {

    }

    @Override
    public void importData(boolean verbose) {
        System.err.println("Using importData() in RawDataDaoImpl!");
    }

    @Override
    public void loadAndImport_from_csv(String recordsCSV, String staffsCSV) {
        Statement stmt = null;
        int cnt1 = 0;
        int cnt2 = 0;
        String[] dataFile1, dataFile2;
        String line1, line2;
        String[] parts1, parts2;
        PreparedStatement prestmt1 = null;
        PreparedStatement prestmt2 = null;

        try {
            System.out.println("RawDataDaoImpl loadAndImport_from_csv() start");

            stmt = CON.createStatement();
            stmt.execute("truncate table raw_data_records cascade");
            stmt.execute("truncate table raw_data_staffs cascade");
            prestmt1 = CON.prepareStatement("insert into raw_data_records"
                                            + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            prestmt2 = CON.prepareStatement("insert into raw_data_staffs"
                                            + " values(?,?,?,?,?,?,?,?)");
            dataFile1 = recordsCSV.split("\n");
            for (int cur = 1; /* jump header */ cur < dataFile1.length; ++cur) {
                line1 = dataFile1[cur];
                parts1 = line1.split(",");
                if (parts1.length > 1) {
                    for (int i = 0; i <= 1; i++)
                        PostgresqlUtil.replace_placeholder(prestmt1, i + 1, parts1[i], Types.VARCHAR);
                    PostgresqlUtil.replace_placeholder(prestmt1, 3, parts1[2], Types.INTEGER);
                    for (int i = 3; i <= 8; i++)
                        PostgresqlUtil.replace_placeholder(prestmt1, i + 1, parts1[i], Types.VARCHAR);
                    for (int i = 9; i <= 10; i++)
                        PostgresqlUtil.replace_placeholder(prestmt1, i + 1, parts1[i], Types.NUMERIC);
                    for (int i = 11; i <= 17; i++)
                        PostgresqlUtil.replace_placeholder(prestmt1, i + 1, parts1[i], Types.VARCHAR);
                    prestmt1.addBatch();
                    cnt1++;
                    if (cnt1 % BATCH_SIZE == 0) {
                        prestmt1.executeBatch();
                        prestmt1.clearBatch();
                    }
                }
            }
            if (cnt1 % BATCH_SIZE != 0) {
                prestmt1.executeBatch();
            }
            if (verbose)
                System.out.println("Commit!");
            if (verbose) {
                System.out.println("DROP all tuples and LOAD data into table raw_data_records SUCCESSFULLY. " + cnt1 + " records are loaded");
            } else {
                System.out.println("RawDataDaoImpl loadAndImport_from_csv(), imported to raw_data_records");
            }

            // 导入 raw_data_staffs
            dataFile2 = staffsCSV.split("\n");
            for (int cur = 1; /* jump header */ cur < dataFile2.length; ++cur) {
                line2 = dataFile2[cur];
                parts2 = line2.split(",");
                if (parts2.length > 1) {
                    for (int i = 0; i <= 4; i++)
                        PostgresqlUtil.replace_placeholder(prestmt2, i + 1, parts2[i], Types.VARCHAR);
                    PostgresqlUtil.replace_placeholder(prestmt2, 6, parts2[5], Types.INTEGER);
                    for (int i = 6; i <= 7; i++)
                        PostgresqlUtil.replace_placeholder(prestmt2, i + 1, parts2[i], Types.VARCHAR);
                    prestmt2.addBatch();
                    cnt2++;
                    if (cnt2 % BATCH_SIZE == 0) {
                        prestmt2.executeBatch();
                        prestmt2.clearBatch();
                    }
                }
            }
            if (cnt2 % BATCH_SIZE != 0) {
                prestmt2.executeBatch();
            }
            if (verbose)
                System.out.println("Commit!");
            if (verbose) {
                System.out.println("DROP all tuples and LOAD data into table raw_data_staffs SUCCESSFULLY. " + cnt2 + " records are loaded");
            } else {
                System.out.println("RawDataDaoImpl loadAndImport_from_csv(), imported to raw_data_staffs");
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.exit(3);
        }
    }
}
