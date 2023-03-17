package main.model.fao.impl;

import main.entity.Delivery;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.fao.DeliveryFao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static main.util.PostgresqlUtil.*;

public class DeliveryFaoImpl implements DeliveryFao {
    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        long start_time, end_time;
        int cnt = 0;
        String line;
        String[] delivery;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DELIVERY_CSV_PATH))) {
            if (verbose)
                System.out.println("DeliveryFaoImpl loadData() begin");
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                cnt++;
                delivery = line.split(",");
                deliveries.add(new Delivery(delivery));
            }
            end_time = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Load data into java Successfully from delivery.csv. " + cnt + " records are loaded");
                System.out.println("Loading Time : " + (end_time - start_time) / 1000f + " s");
                System.out.println("Loading speed : " + (cnt * 1000L) / (end_time - start_time) + " records/s");
            } else {
                System.out.println("Load data into java from delivery.csv successfully in " + (end_time - start_time) / 1000f + " s");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            System.out.println();
        }
        return deliveries.toArray(new Delivery[0]);
    }

    @Override
    public void importData(boolean verbose) {
        long start_time, end_time;
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ALTERED_DATA_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DELIVERY_CSV_PATH))) {

            // Clear file
            bufferedWriter.write("item_name,item_type,item_price,retrieval_start_time,retrieval_city,retrieval_courier_id," +
                    "export_time,export_city,import_time,import_city,delivery_finish_time,delivery_city,delivery_courier_id," +
                    "container_code,ship_name,company,log_time\n");

            // Load data(from file altered_data) into file delivery
            start_time = System.currentTimeMillis();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");

                String str = info[2] + "," + info[3] + "," + info[4] + "," + info[6] + "," + info[5] + "," + info[0] + "," +
                        info[19] + "," + info[17] + "," + info[22] + "," + info[20] + "," + info[11] + "," + info[12] + "," + info[1] + "," +
                        info[23] + "," + info[25] + "," + info[26] + "," + info[27];
                bufferedWriter.write(str + "\n");
            }
            end_time = System.currentTimeMillis();
            System.out.println("Import Data into file delivery.csv successfully in " + (end_time - start_time) / 1000f + " s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(boolean verbose, Entity[] entities) {

    }

    @Override
    public void dropAll(boolean verbose) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DELIVERY_CSV_PATH))) {
            bufferedWriter.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void _1_insertPartically(boolean verbose) {
    }

    @Override
    public void _2_deleteApple(boolean verbose) {
        long start_time, end_time;
        List<String> list = new ArrayList<>(0);
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DELIVERY_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DELIVERY_CSV_PATH))) {

            start_time = System.currentTimeMillis();

            // Read
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");
                if (info[1].equals("apple")) continue;
                list.add(line);
            }

            // Write
            bufferedWriter.write("item_name,item_type,item_price,retrieval_start_time,retrieval_city,retrieval_courier_id," +
                    "export_time,export_city,import_time,import_city,delivery_finish_time,delivery_city,delivery_courier_id," +
                    "container_code,ship_name,company,log_time\n");
            for (String str : list) {
                bufferedWriter.write(str + "\n");
            }

            end_time = System.currentTimeMillis();
            System.out.println("FAO: Deleted selected data SUCCESSFULLY in " + (end_time - start_time) / 1000f + " s");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void _3_update(boolean verbose) {
        long start_time, end_time;
        List<String> list = new ArrayList<>(0);
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DELIVERY_CSV_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DELIVERY_CSV_PATH))) {

            start_time = System.currentTimeMillis();

            // Read
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");
                if (!info[6].equals("") && !info[8].equals("") && info[10].equals("")) {
                    info[10] = "2022-01-01";
                    info[12] = "1";
                }
                if (!info[6].equals("") && info[8].equals("") && info[10].equals("")) {
                    info[10] = "2022-01-01";
                    info[12] = "1";
                    info[8] = "2022-01-01";
                }
                if (info[6].equals("") && !info[8].equals("") && info[10].equals("")) {
                    info[10] = "2022-01-01";
                    info[12] = "1";
                    info[8] = "2022-01-01";
                    info[6] = "2022-01-01";
                    info[13] = "container0000";
                    info[14] = "ship0000";
                }
                String str = info[0] + "," + info[1] + "," + info[2] + "," + info[3] + "," + info[4] + "," + info[5] + "," +
                        info[6] + "," + info[7] + "," + info[8] + "," + info[9] + "," + info[10] + "," + info[11] + "," +
                        info[12] + "," + info[13] + "," + info[14] + "," + info[15] + "," + info[16];
                list.add(str);
            }

            // Write
            bufferedWriter.write("item_name,item_type,item_price,retrieval_start_time,retrieval_city,retrieval_courier_id," +
                    "export_time,export_city,import_time,import_city,delivery_finish_time,delivery_city,delivery_courier_id," +
                    "container_code,ship_name,company,log_time\n");
            for (String str : list) {
                bufferedWriter.write(str + "\n");
            }

            end_time = System.currentTimeMillis();
            System.out.println("FAO: Updated empty data SUCCESSFULLY in " + (end_time - start_time) / 1000f + " s");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void _4_selectUnfinished(boolean verbose) {
        long start_time, end_time;
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DELIVERY_CSV_PATH))) {
            start_time = System.currentTimeMillis();

            // Read
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");
                if (info[6].equals("") || !info[8].equals("") || info[10].equals("")) {
                    System.out.println(line);
                }
            }

            end_time = System.currentTimeMillis();
            System.out.println("FAO: Selected unfinished records SUCCESSFULLY in " + (end_time - start_time) / 1000f + " s");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void _5_selectOnContainer(boolean verbose, String container_code) {
        long start_time, end_time;
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DELIVERY_CSV_PATH))) {
            start_time = System.currentTimeMillis();

            // Read
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",");
                if (info[13].equals(container_code)) {
                    System.out.println(line);
                }
            }

            end_time = System.currentTimeMillis();
            System.out.println("FAO: Selected data SUCCESSFULLY in " + (end_time - start_time) / 1000f + " s");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
