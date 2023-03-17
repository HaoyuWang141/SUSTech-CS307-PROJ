package main.model.dao.impl;

import main.entity.Delivery;
import main.entity.Entity;
import main.enumcase.ExitStatus;
import main.model.dao.DeliveryDao;
import main.util.PostgresqlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static main.util.PostgresqlUtil.*;

public class DeliveryDaoImpl implements DeliveryDao {
    @Override
    public Entity[] loadData(boolean verbose) {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        long start_time;
        long end_time;
        Connection connection = null;
        try {
            connection = PostgresqlUtil.getConnectionRoot();
            if (verbose)
                System.out.println("DeliveryDaoImpl loadData() start");
            start_time = System.currentTimeMillis();
            String sql = "select * from delivery";
            ResultSet resultSet = execute_query(connection, sql, null);
            end_time = System.currentTimeMillis();
            System.out.println("Load Data into java from table delivery successfully in " + (end_time - start_time) / 1000f + " s");
            while (resultSet.next()) {
                Delivery delivery = new Delivery();

                delivery.setItem_name(resultSet.getString("item_name"));
                delivery.setItem_type(resultSet.getString("item_type"));
                delivery.setItem_price(resultSet.getDouble("item_price"));
                delivery.setRetrieval_city(resultSet.getString("retrieval_city"));
                delivery.setRetrieval_courier_id(resultSet.getInt("retrieval_courier_id"));
                delivery.setExport_city(resultSet.getString("export_city"));
                delivery.setExport_officer_id(resultSet.getInt("export_officer_id"));
                delivery.setImport_city(resultSet.getString("import_city"));
                delivery.setExport_officer_id(resultSet.getInt("import_officer_id"));
                delivery.setDelivery_city(resultSet.getString("delivery_city"));
                delivery.setDelivery_courier_id(resultSet.getInt("delivery_courier_id"));
                delivery.setContainer_code(resultSet.getString("container_code"));
                delivery.setShip_name(resultSet.getString("ship_name"));
                delivery.setCompany(resultSet.getString("company"));
                delivery.setCompany(resultSet.getString("state"));

                deliveries.add(delivery);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(ExitStatus.SQLException.getCode());
        } finally {
            PostgresqlUtil.closeResource(connection);
            System.out.println();
        }
        return deliveries.toArray(new Delivery[0]);
    }

    @Override
    public void importData(boolean verbose) {
        try {
            System.out.println("DeliveryDaoImpl importData() start");
            execute_update(CON, "truncate table delivery cascade;", null);
            execute_update(CON, "ALTER TABLE delivery DISABLE TRIGGER ALL;", null);
            // import from raw_data
            String sql;
            sql = """
                    insert into delivery(item_name, item_type, item_price, retrieval_city, retrieval_courier_id,
                                         export_city, import_city,
                                         delivery_city, container_code, ship_name, company, state)
                    select "Item Name", "Item Class", "Item Price", "Retrieval City", r.id,
                           "Export City", "Import City",
                           "Delivery City", "Container Code", "Ship Name", "Company Name", "Item State"
                    from raw_data_records join staff r
                         on raw_data_records."Retrieval Courier" = r.name and r.type = 'Courier';
                    """;
            execute_update(CON, sql, null);
            sql = """
                    update delivery
                    set export_officer_id = e.id
                    from raw_data_records join staff e
                         on raw_data_records."Export Officer" = e.name and e.type = 'Seaport Officer'
                    where raw_data_records."Export Officer" is not null and
                          delivery.item_name = raw_data_records."Item Name";
                    """;
            execute_update(CON, sql, null);
            sql = """
                    update delivery
                    set import_officer_id = i.id
                    from raw_data_records join staff i
                         on raw_data_records."Import Officer" = i.name and i.type = 'Seaport Officer'
                    where raw_data_records."Import Officer" is not null and
                          delivery.item_name = raw_data_records."Item Name";
                    """;
            execute_update(CON, sql, null);
            sql = """
                    update delivery
                    set delivery_courier_id = d.id
                    from raw_data_records join staff d
                         on raw_data_records."Delivery Courier" = d.name and d.type = 'Courier'
                    where raw_data_records."Delivery Courier" is not null and
                          delivery.item_name = raw_data_records."Item Name";
                    """;
            execute_update(CON, sql, null);
            execute_update(CON, "ALTER TABLE delivery ENABLE TRIGGER ALL;", null);
            System.out.println("Load Data into table delivery SUCCESSFULLY.");
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
        long start_time, end_time;
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = PostgresqlUtil.getConnection(verbose);
            start_time = System.currentTimeMillis();
            stmt = connection.prepareStatement("truncate delivery cascade ;");
            stmt.execute();
            connection.commit();
            end_time = System.currentTimeMillis();
            System.out.println("Have deleted in " + (end_time - start_time) / 1000L + " s, with database APIs.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PostgresqlUtil.closeResource(connection, stmt);
            System.out.println();
        }
    }

    @Override
    public Delivery[] getDelivery(String loader_cnf, String item_name) {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        try {
            String sql = "select * from delivery where item_name like ?;";
            ResultSet resultSet = execute_query(CON, sql, new String[]{item_name});
            while (resultSet.next()) {
                deliveries.add(new Delivery(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getString(8),
                        resultSet.getInt(9),
                        resultSet.getString(10),
                        resultSet.getInt(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries.toArray(new Delivery[0]);
    }

    @Override
    public Delivery[] getDelivery_at_Port(String loader_cnf, String city) {
        ArrayList<Delivery> deliveries = new ArrayList<>();
        try {
            String sql = "select * from delivery where (export_city like ? and state like 'Export Checking') or (import_city like ? and state like 'Import Checking')";
            ResultSet resultSet = execute_query(CON, sql, new String[]{city, city});
            while (resultSet.next()) {
                deliveries.add(new Delivery(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getString(8),
                        resultSet.getInt(9),
                        resultSet.getString(10),
                        resultSet.getInt(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getString(15)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries.toArray(new Delivery[0]);
    }

    @Override
    public boolean insert(String loader_cnf, Delivery delivery) {
        boolean flag = true;
        try {
            String sql = "insert into delivery values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            execute_update(CON, sql, new Object[]{
                    delivery.getItem_name(),
                    delivery.getItem_type(),
                    delivery.getItem_price(),
                    delivery.getRetrieval_city(),
                    delivery.getRetrieval_courier_id(),
                    delivery.getExport_city(),
                    delivery.getExport_officer_id(),
                    delivery.getImport_city(),
                    delivery.getImport_officer_id(),
                    delivery.getDelivery_city(),
                    delivery.getDelivery_courier_id(),
                    delivery.getContainer_code(),
                    delivery.getShip_name(),
                    delivery.getCompany(),
                    delivery.getState()
            });
        } catch (SQLException e) {
            flag = false;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_ToExportTransporting(String loader_cnf, String item_name) {
        boolean flag = true;
        try {
            String sql = "update delivery set state ='To-Export Transporting' where item_name like ?";
            execute_update(CON, sql, new String[]{item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_ExportChecking(String loader_cnf, String item_name) {
        boolean flag = true;
        try {
            String sql = "update delivery set state ='Export Checking' where item_name like ?";
            execute_update(CON, sql, new String[]{item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_ExportCheckFail(String loader_cnf, String item_name, int staffId) {
        boolean flag = true;
        try {
            String sql = "update delivery set (export_officer_id,state) =(?,'Export Check Fail') where item_name like ?";
            execute_update(CON, sql, new Object[]{staffId, item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_PackingToContainer(String loader_cnf, String item_name, int staffId) {
        boolean flag = true;
        try {
            String sql = "update delivery set (export_officer_id,state) =(?,'Packing to Container') where item_name like ?";
            execute_update(CON, sql, new Object[]{staffId, item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateContainer_atState_PackingToContainer(String loader_cnf, String item_name, String containerCode) {
        boolean flag = true;
        try {
            String sql = "update delivery set container_code =? where item_name like ?";
            execute_update(CON, sql, new String[]{containerCode, item_name});
        } catch (SQLException e) {
            flag = false;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_ImportChecking(String loader_cnf, String item_name) {
        boolean flag = true;
        try {
            String sql = "update delivery set state ='Import Checking' where item_name like ?";
            execute_update(CON, sql, new String[]{item_name});
        } catch (SQLException e) {
            flag = false;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_ImportCheckFail(String loader_cnf, String item_name, int staffId) {
        boolean flag = true;
        try {
            String sql = "update delivery set (import_officer_id,state) =(?,'Import Check Fail') where item_name like ?";
            execute_update(CON, sql, new Object[]{staffId, item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_FromImportTransporting(String loader_cnf, String item_name, int staffId) {
        boolean flag = true;
        try {
            String sql = "update delivery set (import_officer_id,state) =(?,'From-Import Transporting') where item_name like ?";
            execute_update(CON, sql, new Object[]{staffId, item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateDeliveryCourierId(String loader_cnf, String item_name, int staffId) {
        boolean flag = true;
        try {
            String sql = "update delivery set (delivery_courier_id) =(?) where item_name like ?";
            execute_update(CON, sql, new Object[]{staffId, item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_Delivering(String loader_cnf, String item_name) {
        boolean flag = true;
        try {
            String sql = "update delivery set (state) =('Delivering') where item_name like ?";
            execute_update(CON, sql, new Object[]{item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateState_Finish(String loader_cnf, String item_name) {
        boolean flag = true;
        try {
            String sql = "update delivery set (state) =('Finish') where item_name like ?";
            execute_update(CON, sql, new Object[]{item_name});
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
