package main.model.fao.impl;

import main.entity.Container;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.fao.ContainerFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static main.util.PostgresqlUtil.CONTAINER_CSV_PATH;
import static main.util.PostgresqlUtil.RAW_DATA_CSV_PATH;

public class
ContainerFaoImpl implements ContainerFao {

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Container> containers = new ArrayList<>();
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] container;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CONTAINER_CSV_PATH))) {
            if (verbose)
                System.out.println("ContainerFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                container = line.split(",");
                containers.add(new Container(container));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Load data into java Successfully from container.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("Load data into java Successfully from container.csv in " + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return containers.toArray(new Container[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        String line;
        String code, type;
        Set<String> containers = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CONTAINER_CSV_PATH))) {

            // Clear file
            bufferedWriter.write("code,type\n");

            // Load data(from file data) into file container
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                String[] info = line.split(",");
                code = info[21];
                type = info[22];
                if (code.equals("")) continue;
                if (!containers.contains(code + "," + type)) {
                    containers.add(code + "," + type);
                    sb.append(code).append(",").append(type).append("\n");
                    bufferedWriter.write(sb.toString());
                }
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file container.csv successfully in " + (end_time - start_time) / 1000f + " s");
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
