package main.model.fao.impl;

import main.entity.Entity;
import main.entity.RawData;
import main.enumcase.ExitStatus;
import main.model.fao.RawDataFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class RawDataFaoImpl implements RawDataFao {
    @Override
    public Entity[] loadData(boolean verbose) {
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] raw_data;
        ArrayList<RawData> rawData_list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH))) {
            // Load data(from file data) into file container
            if (verbose)
                System.out.println("RawDataFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                raw_data = line.split(",");
                rawData_list.add(new RawData(raw_data));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Load data into java successfully from raw_data.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("Load data into java successfully from raw_data.csv. " + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return rawData_list.toArray(new RawData[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        String line;
        int cnt = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RAW_DATA_CSV_PATH))) {

            // Clear file
            bufferedWriter.write("Item Name,Item Type,Item Price,Retrieval City,Retrieval Start Time,Retrieval Courier,Retrieval Courier Gender,Retrieval Courier Phone Number,Retrieval Courier Age,Delivery Finished Time,Delivery City,Delivery Courier,Delivery Courier Gender,Delivery Courier Phone Number,Delivery Courier Age,Item Export City,Item Export Tax,Item Export Time,Item Import City,Item Import Tax,Item Import Time,Container Code,Container Type,Ship Name,Company Name,Log Time\n");

            // Load data(from file data) into file container
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null && cnt < MAX_IMPORT_AMOUNT) {
                bufferedWriter.write(line + "\n");
                cnt++;
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file raw_data.csv successfully in " + (end_time - start_time) / 1000f + " s" +
                    "    (MAX IMPORT AMOUNT: " + MAX_IMPORT_AMOUNT + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(boolean verbose, Entity[] entities) {

    }

    @Override
    public void dropAll(boolean verbose) {

    }
}
