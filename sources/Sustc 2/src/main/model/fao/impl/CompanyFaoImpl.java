package main.model.fao.impl;

import main.entity.Company;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.fao.CompanyFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static main.util.PostgresqlUtil.COMPANY_CSV_PATH;
import static main.util.PostgresqlUtil.RAW_DATA_CSV_PATH;

public class
CompanyFaoImpl implements CompanyFao {

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Company> companies = new ArrayList<>();
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] company;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(COMPANY_CSV_PATH))) {
            if (verbose)
                System.out.println("CompanyFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                company = line.split(",");
                companies.add(new Company(company));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("load data into java Successfully from company.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("load data into java successfully from company.csv in" + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return companies.toArray(new Company[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        String line;
        String name;
        Set<String> containers = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(COMPANY_CSV_PATH))) {

            // Clear file
            bufferedWriter.write("name\n");

            // Load data(from file data) into file company
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");
                name = info[24];
                if (!containers.contains(name)) {
                    containers.add(name);
                    bufferedWriter.write(name + "\n");
                }
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file company.csv SUCCESSFULLY in " + (end_time - start_time) / 1000f + " s");

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
