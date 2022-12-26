package main.model.fao.impl;

import main.entity.Entity;
import main.entity.Ship;
import main.enumcase.ExitStatus;
import main.model.fao.ShipFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static main.util.PostgresqlUtil.RAW_DATA_CSV_PATH;
import static main.util.PostgresqlUtil.SHIP_CSV_PATH;

public class ShipFaoImpl implements ShipFao {

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Ship> ships = new ArrayList<>();
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] ship;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SHIP_CSV_PATH))) {
            if (verbose)
                System.out.println("ShipFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                ship = line.split(",");
                ships.add(new Ship(ship));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Load data into java successfully from ship.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("Load data into java successfully from ship.csv. " + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return ships.toArray(new Ship[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        String line;
        String name, company;
        Set<String> ships = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(SHIP_CSV_PATH))) {

            // Clear file
            bufferedWriter.write("name,company\n");

            // Load data(from file data) into file container
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                String[] info = line.split(",");
                name = info[23];
                company = info[24];
                if (name.equals("")) continue;
                if (!ships.contains(name + "," + company)) {
                    ships.add(name + "," + company);
                    sb.append(name).append(",").append(company).append("\n");
                    bufferedWriter.write(sb.toString());
                }
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file ship.csv successfully in " + (end_time - start_time) / 1000f + " s");

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
