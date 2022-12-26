package main.model.fao.impl;

import main.entity.CityTax;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.fao.CityTaxFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import static main.util.PostgresqlUtil.CITY_TAX_CSV_PATH;
import static main.util.PostgresqlUtil.RAW_DATA_CSV_PATH;

public class CityTaxFaoImpl implements CityTaxFao {

    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<CityTax> cityTaxes = new ArrayList<>();
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] cityTax;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CITY_TAX_CSV_PATH))) {
            if (verbose)
                System.out.println("CityTaxFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                cityTax = line.split(",");
                cityTaxes.add(new CityTax(cityTax));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Load data into java successfully from city_tax.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("Load data into java successfully from city_tax.csv int " + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return cityTaxes.toArray(new CityTax[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        String line;
        Set<CityTax> cityTaxSet = new HashSet<>();
        List<CityTax> cityTaxes = new ArrayList<>(0);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CITY_TAX_CSV_PATH))) {

            // Clear file
            bufferedWriter.write("city,item_type,import_tax_rate,export_tax_rate\n");

            // Load data(from file data) into file city_tax
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");

                if (!info[1].equals("") && !info[15].equals("")) {
                    String[] strings = new String[4];
                    strings[0] = info[15];
                    strings[1] = info[1];
                    strings[2] = "";
                    strings[3] = String.format("%.2f", Double.parseDouble(info[16]) / Double.parseDouble(info[2]));
                    CityTax cityTax = new CityTax(strings);
                    if (!has(cityTaxSet, cityTax)) {
                        cityTaxSet.add(cityTax);
                        cityTaxes.add(cityTax);
                    }
                }
                if (!info[1].equals("") && !info[18].equals("")) {
                    String[] strings = new String[4];
                    strings[0] = info[18];
                    strings[1] = info[1];
                    strings[2] = String.format("%.2f", Double.parseDouble(info[19]) / Double.parseDouble(info[2]));
                    strings[3] = "";
                    CityTax cityTax = new CityTax(strings);
                    if (!has(cityTaxSet, cityTax)) {
                        cityTaxSet.add(cityTax);
                        cityTaxes.add(cityTax);
                    }
                }
            }

            /*
            List<String> list = new ArrayList<>(0);
            for(CityTax cityTax : cityTaxes) {
                list.add(cityTax.toString());
            }
            Collections.sort(list);
            for(String s : list) {
                System.out.println(s);
            }
            */

            for (CityTax cityTax : cityTaxes) {
                bufferedWriter.write(cityTax.toString() + "\n");
            }

            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file city_tax.csv successfully in " + (end_time - start_time) / 1000f + " s");

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


    public boolean has(Set<CityTax> set, CityTax cityTax) {
        Iterator<CityTax> iterator = set.iterator();
        while (iterator.hasNext()) {
            CityTax o = iterator.next();
            if (o.equals(cityTax))
                return true;
        }
        return false;
    }

}
