package main.model.fao.impl;

import main.entity.City;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.fao.CityFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static main.util.PostgresqlUtil.CITY_CSV_PATH;
import static main.util.PostgresqlUtil.RAW_DATA_CSV_PATH;

public class CityFaoImpl implements CityFao {

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<City> cities = new ArrayList<>();
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] city;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CITY_CSV_PATH))) {
            if (verbose)
                System.out.println("CityFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                city = line.split(",");
                cities.add(new City(city));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Load data into java successfully from city.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("Load data into java successfully from city.csv in " + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return cities.toArray(new City[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        String line;
        Set<String> cities = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CITY_CSV_PATH))) {

            // Clear file
            bufferedWriter.write("name,phone_code\n");

            // Load data(from file data) into file container
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");

                String str1 = info[3] + "," + info[7].substring(0, 4);
                if (!cities.contains(str1)) {
                    cities.add(str1);
                    bufferedWriter.write(str1 + "\n");
                }

                String str2 = info[15] + ",";
                if (!cities.contains(str2)) {
                    cities.add(str2);
                    bufferedWriter.write(str2 + "\n");
                }

                String str3 = info[18] + ",";
                if (!cities.contains(str3)) {
                    cities.add(str3);
                    bufferedWriter.write(str3 + "\n");
                }

                if (!info[13].equals("")) {
                    String str4 = info[10] + "," + info[13].substring(0, 4);
                    if (!cities.contains(str4)) {
                        cities.add(str4);
                        bufferedWriter.write(str4 + "\n");
                    }
                }
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file city.csv successfully in " + (end_time - start_time) / 1000f + " s");

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
