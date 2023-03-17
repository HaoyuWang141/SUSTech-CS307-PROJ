package main.model.fao.impl;

import main.entity.Entity;
import main.entity.Staff;
import main.enumcase.ExitStatus;
import main.model.fao.CourierFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static main.util.PostgresqlUtil.*;

public class CourierFaoImpl implements CourierFao {
    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Staff> staffs = new ArrayList<>();
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] courier;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(COURIER_CSV_PATH))) {
            if (verbose)
                System.out.println("CourierFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                courier = line.split(",");
                staffs.add(new Staff(courier));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Load data into java successfully from courier.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("Load data into java successfully from courier.csv. " + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return staffs.toArray(new Staff[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        int id_cnt = 1;
        String line;
        Set<String> couriers = new HashSet<>();
        String[] courierArray = new String[50005];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(COURIER_CSV_PATH))) {
            bufferedWriter.write("id,company,name,gender,birth_year,phone_number,city\n");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");

                int birth_year_1 = (int) (Double.parseDouble(info[4].substring(0, 4)) - Double.parseDouble(info[8]));
                String str1 = info[24] + "," + info[5] + "," + info[6] + "," +
                        birth_year_1 + "-1-1," + info[7].substring(5, 15) + "," + info[15];
                if (!couriers.contains(str1)) {
                    couriers.add(str1);
                    bufferedWriter.write(id_cnt + "," + str1 + "\n");
                    courierArray[id_cnt] = str1;
                    id_cnt++;
                }

                if (!info[9].equals("")) {
                    int birth_year_2 = (int) (Double.parseDouble(info[9].substring(0, 4)) - Double.parseDouble(info[14]));
                    String str2 = info[24] + "," + info[11] + "," + info[12] + "," +
                            birth_year_2 + "-1-1," + info[13].substring(5, 15) + "," + info[18];
                    if (!couriers.contains(str2)) {
                        couriers.add(str2);
                        bufferedWriter.write(id_cnt + "," + str2 + "\n");
                        courierArray[id_cnt] = str2;
                        id_cnt++;
                    }
                }
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file courier.csv successfully in " + (end_time - start_time) / 1000f + " s");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(RAW_DATA_CSV_PATH));
             BufferedWriter alter = new BufferedWriter(new FileWriter(ALTERED_DATA_CSV_PATH))) {

            // Clear file
            alter.write("retrieval_courier_id,delivery_courier_id,Item Name,Item Type,Item Price,Retrieval City,Retrieval Start Time,Retrieval Courier,Retrieval Courier Gender,Retrieval Courier Phone Number,Retrieval Courier Age,Delivery Finished Time,Delivery City,Delivery Courier,Delivery Courier Gender,Delivery Courier Phone Number,Delivery Courier Age,Item Export City,Item Export Tax,Item Export Time,Item Import City,Item Import Tax,Item Import Time,Container Code,Container Type,Ship Name,Company Name,Log Time"
                    + "\n");

            // alter data
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");
                String retrieval_courier_id = "";
                String delivery_courier_id = "";

                int birth_year_1 = (int) (Double.parseDouble(info[4].substring(0, 4)) - Double.parseDouble(info[8]));
                String str1 = info[24] + "," + info[5] + "," + info[6] + "," +
                        birth_year_1 + "," + info[7].substring(5, 15) + "," + info[15];

                String str2 = "";
                if (!info[9].equals("")) {
                    int birth_year_2 = (int) (Double.parseDouble(info[9].substring(0, 4)) - Double.parseDouble(info[14]));
                    str2 = info[24] + "," + info[11] + "," + info[12] + "," +
                            birth_year_2 + "," + info[13].substring(5, 15) + "," + info[18];
                }

                for (int i = 0; i < courierArray.length; ++i) {
                    if (courierArray[i] != null && courierArray[i].equals(str1)) {
                        retrieval_courier_id = String.valueOf(i);
                    }
                    if (courierArray[i] != null && courierArray[i].equals(str2)) {
                        delivery_courier_id = String.valueOf(i);
                    }
                }
                alter.write(retrieval_courier_id + "," + delivery_courier_id + "," + line + "\n");
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file altered_raw_data.csv successfully in " + (end_time - start_time) / 1000f + " s");
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
