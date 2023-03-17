package main;

import main.entity.Container;
import main.entity.Delivery;
import main.entity.Ship;
import main.entity.Staff;
import main.enumcase.SOURCE;
import main.interfaces.*;
import main.service.*;
import main.service.impl.*;
import main.util.PostgresqlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

import static main.interfaces.ItemState.*;
import static main.util.PostgresqlUtil.*;

public class DBManipulation implements IDatabaseManipulation {
    CityService cityService = new CityServiceImpl();
    CityTaxService cityTaxService = new CityTaxServiceImpl();
    CompanyService companyService = new CompanyServiceImpl();
    ContainerService containerService = new ContainerServiceImpl();
    DeliveryService deliveryService = new DeliveryServiceImpl();
    LogService logService = new LogServiceImpl();
    RawDataService rawDataService = new RawDataServiceImpl();
    SeaTransportationService seaTransportationService = new SeaTransportationServiceImpl();
    ShipService shipService = new ShipServiceImpl();
    StaffService staffService = new StaffServiceImpl();
    StaffTypeService staffTypeService = new StaffTypeServiceImpl();
    StateService stateService = new StateServiceImpl();

    public static DBManipulation DB_MANIPULATION;

    public DBManipulation(String database, String root, String pass) {
        PostgresqlUtil.database = database;
        PostgresqlUtil.root = root;
        PostgresqlUtil.pass = pass;

        Statement stmt = null;
        Connection connection = null;
        try {
            // connection
            connection = PostgresqlUtil.getConnection(database, root, pass);
            System.out.println("project_interface constructor (database,root,pass) start");
            InputStream inputStream1 = this.getClass().getResourceAsStream("sql/1_DDL.sql");
            InputStream inputStream2 = this.getClass().getResourceAsStream("sql/2_trigger.sql");
            InputStream inputStream3 = this.getClass().getResourceAsStream("sql/3_authority.sql");
//            URL u1 = this.getClass().getResource("/main/sql/1_DDL.sql");
//            URL u2 = this.getClass().getResource("/main/sql/2_trigger.sql");
//            URL u3 = this.getClass().getResource("/main/sql/3_authority.sql");
            String sql_DDL = null;
            String sql_trigger = null;
            String sql_authority = null;
            if (inputStream1 != null) sql_DDL = readFile(inputStream1);
            else throw new IOException("can't find 1_DDL.main.sql");
            if (inputStream2 != null) sql_trigger = readFile(inputStream2);
            else throw new IOException("can't find 2_trigger.main.sql");
            if (inputStream3 != null) sql_authority = readFile(inputStream3);
            else throw new IOException("can't find 3_authority.main.sql");
            stmt = connection.createStatement();
            stmt.execute(sql_DDL);
            stmt.execute(sql_trigger);
            stmt.execute(sql_authority);

            store_cnf(database,"courier","111111","loader_Courier.cnf");
            store_cnf(database,"logchecker","111111","loader_LogChecker.cnf");
            store_cnf(database,"seaportofficer","111111","loader_SeaportOfficer.cnf");
            store_cnf(database,"companymanager","111111","loader_CompanyManager.cnf");
            store_cnf(database,"sustcdepartmentmanager","111111","loader_SUSTCDepartmentManager.cnf");
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
        } finally {
            PostgresqlUtil.closeResource(connection, stmt);
        }
    }

    @Override
    public void $import(String recordsCSV, String staffsCSV) {
        try {
            CON = getConnectionRoot();
            rawDataService.importData(recordsCSV, staffsCSV);
            cityService.importData(verbose, SOURCE.database);
            cityTaxService.importData(verbose, SOURCE.database);
            companyService.importData(verbose, SOURCE.database);
            containerService.importData(verbose, SOURCE.database);
            staffTypeService.importData();
            staffService.importData(verbose, SOURCE.database);
            logService.importData();
            shipService.importData(verbose, SOURCE.database);
            stateService.importData();
            deliveryService.importData(verbose, SOURCE.database);
            seaTransportationService.importData();
            CON.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeResource(CON);
        }
    }

    /**
     * Company Manager
     */
    @Override
    public double getImportTaxRate(LogInfo logInfo, String city, String itemClass) {
        double result = -1.;
        if (logInfo.type() == LogInfo.StaffType.CompanyManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_CompanyManager.cnf");
            result = cityTaxService.getImportTaxRate("loader_CompanyManager.cnf", city, itemClass);
        }
        closeResource(CON);
        return result;
    }

    @Override
    public double getExportTaxRate(LogInfo logInfo, String city, String itemClass) {
        double result = -1.;
        if (logInfo.type() == LogInfo.StaffType.CompanyManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_CompanyManager.cnf");
            result = cityTaxService.getExportTaxRate("loader_CompanyManager.cnf", city, itemClass);
        }
        closeResource(CON);
        return result;
    }

    @Override
    public boolean loadItemToContainer(LogInfo logInfo, String itemName, String containerCode) {
        boolean flag = false;
        if (logInfo.type() == LogInfo.StaffType.CompanyManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_CompanyManager.cnf");
            Delivery delivery = deliveryService.getDelivery("loader_CompanyManager.cnf", itemName);
            String item_company = null;
            if (delivery != null)
                item_company = delivery.getCompany();
            String staff_company = staffService.getStaffCompany("loader_CompanyManager.cnf", logInfo.name());
            if (!containerService.getUsing("loader_CompanyManager.cnf", containerCode) && (item_company != null && item_company.equals(staff_company)))
                flag = deliveryService.updateContainer_atState_PackingToContainer("loader_CompanyManager.cnf", itemName, containerCode);
        }
        closeResource(CON);
        return flag;
    }

    @Override
    public boolean loadContainerToShip(LogInfo logInfo, String shipName, String containerCode) {
        boolean flag = false;
        if (logInfo.type() == LogInfo.StaffType.CompanyManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_CompanyManager.cnf");
            Ship ship = shipService.getShip("loader_CompanyManager.cnf", shipName);
            String ship_company = null;
            if (ship != null)
                ship_company = ship.getCompany();
            String staff_company = staffService.getStaffCompany("loader_CompanyManager.cnf", logInfo.name());
            if (!shipService.getSailing("loader_CompanyManager.cnf", shipName)
                && containerService.getUsing("loader_CompanyManager.cnf", containerCode)
                && seaTransportationService.getShipName_by_containerCode("loader_CompanyManager.cnf", containerCode) == null
                && (ship_company != null && ship_company.equals(staff_company))) {
                flag = seaTransportationService.updateShip_by_containerCode("loader_CompanyManager.cnf", containerCode, shipName);
            }
        }
        closeResource(CON);
        return flag;
    }

    @Override
    public boolean shipStartSailing(LogInfo logInfo, String shipName) {
        boolean flag = false;
        if (logInfo.type() == LogInfo.StaffType.CompanyManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_CompanyManager.cnf");
            Ship ship = shipService.getShip("loader_CompanyManager.cnf", shipName);
            String ship_company = null;
            if (ship != null)
                ship_company = ship.getCompany();
            String staff_company = staffService.getStaffCompany("loader_CompanyManager.cnf", logInfo.name());
            if (!shipService.getSailing("loader_CompanyManager.cnf", shipName)
                && seaTransportationService.getItemCount_by_Ship("loader_CompanyManager.cnf", shipName) > 0
                && (ship_company != null && ship_company.equals(staff_company)))
                flag = shipService.shipStartSailing("loader_CompanyManager.cnf", shipName);
        }
        closeResource(CON);
        return flag;
    }

    @Override
    public boolean unloadItem(LogInfo logInfo, String itemName) {
        boolean flag = false;
        if (logInfo.type() == LogInfo.StaffType.CompanyManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_CompanyManager.cnf");
            Delivery delivery = deliveryService.getDelivery("loader_CompanyManager.cnf", itemName);
            if (delivery != null) {
                String item_company = delivery.getCompany();
                String staff_company = staffService.getStaffCompany("loader_CompanyManager.cnf", logInfo.name());
                if (item_company != null && item_company.equals(staff_company)) {
                    if (getItemState_from_String(delivery.getState()).equals(Shipping))
                        flag = seaTransportationService.delete_by_Item("loader_CompanyManager.cnf", itemName);
                }
            }
        }
        closeResource(CON);
        return flag;
    }

    @Override
    public boolean itemWaitForChecking(LogInfo logInfo, String itemName) {
        boolean flag = false;
        if (logInfo.type() == LogInfo.StaffType.CompanyManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_CompanyManager.cnf");
            Delivery delivery = deliveryService.getDelivery("loader_CompanyManager.cnf", itemName);
            String item_company = null;
            if (delivery != null)
                item_company = delivery.getCompany();
            String staff_company = staffService.getStaffCompany("loader_CompanyManager.cnf", logInfo.name());
            if (item_company != null && item_company.equals(staff_company)) {
                flag = deliveryService.setItemState_ImportChecking("loader_CompanyManager.cnf", itemName);
            }
        }
        closeResource(CON);
        return flag;
    }

    /**
     * Courier
     */
    @Override
    public boolean newItem(LogInfo logInfo, ItemInfo itemInfo) {
        boolean flag = false;
        if (logInfo.type() == LogInfo.StaffType.Courier && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_Courier.cnf");
            if (itemInfo.export().officer() == null
                && itemInfo.$import().officer() == null
                && itemInfo.delivery().courier() == null
                && itemInfo.state() == null
                && itemInfo.price() > 0
                && Math.abs((cityTaxService.getExportTaxRate("loader_Courier.cnf", itemInfo.export().city(), itemInfo.$class()) * itemInfo.price() - itemInfo.export().tax())) / itemInfo.export().tax() < ERROR
                && Math.abs((cityTaxService.getImportTaxRate("loader_Courier.cnf", itemInfo.$import().city(), itemInfo.$class()) * itemInfo.price() - itemInfo.$import().tax())) / itemInfo.$import().tax() < ERROR
                && itemInfo.retrieval().city().equals(staffService.getStaffCity("loader_Courier.cnf", logInfo.name()))
                && !itemInfo.retrieval().city().equals(itemInfo.$import().city())
                && !itemInfo.retrieval().city().equals(itemInfo.delivery().city())
                && !itemInfo.export().city().equals(itemInfo.$import().city())
                && !itemInfo.export().city().equals(itemInfo.delivery().city())
            ) // 部分信息要求是null
            {
                Delivery delivery = new Delivery(
                        itemInfo.name(),
                        itemInfo.$class(),
                        itemInfo.price(),
                        itemInfo.retrieval().city(),
                        staffService.getStaffId("loader_Courier.cnf", logInfo.name()),
                        itemInfo.export().city(),
                        null,
                        itemInfo.$import().city(),
                        null,
                        itemInfo.delivery().city(),
                        null,
                        null,
                        null,
                        staffService.getStaffCompany("loader_Courier.cnf", logInfo.name()),
                        "Picking-up"
                );
                flag = deliveryService.insertDelivery("loader_Courier.cnf", delivery);
            }
        }
        closeResource(CON);
        return flag;
    }

    @Override
    public boolean setItemState(LogInfo logInfo, String itemName, ItemState itemState) {
        boolean flag = false;
        if (logInfo.type() == LogInfo.StaffType.Courier && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_Courier.cnf");
            int staffId = staffService.getStaffId("loader_Courier.cnf", logInfo.name());
            if (itemState != null) {
                if (itemState.equals(ToExportTransporting)) {
                    flag = deliveryService.setItemState_ToExportTransporting("loader_Courier.cnf", itemName, staffId);
                } else if (itemState.equals(ExportChecking)) {
                    flag = deliveryService.setItemState_ExportChecking("loader_Courier.cnf", itemName, staffId);
                } else if (itemState.equals(FromImportTransporting)) {
                    flag = deliveryService.update_DeliveryCourierStaffId_atState_FromImportTransporting("loader_Courier.cnf", itemName, staffId);
                } else if (itemState.equals(Delivering)) {
                    flag = deliveryService.setItemState_Delivering("loader_Courier.cnf", itemName, staffId);
                } else if (itemState.equals(Finish)) {
                    flag = deliveryService.setItemState_Finish("loader_Courier.cnf", itemName, staffId);
                }
            }
        }
        closeResource(CON);
        return flag;
    }

    /**
     * Seaport Officer
     */
    @Override
    public String[] getAllItemsAtPort(LogInfo logInfo) {
        String[] result = null;
        if (logInfo.type() == LogInfo.StaffType.SeaportOfficer && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SeaportOfficer.cnf");
            String city = staffService.getStaffCity("loader_SeaportOfficer.cnf", logInfo.name());
            result = deliveryService.getAllItemNameAtPort("loader_SeaportOfficer.cnf", city);
        }
        closeResource(CON);
        return result;
    }

    @Override
    public boolean setItemCheckState(LogInfo logInfo, String itemName, boolean success) {
        boolean result = false;
        if (logInfo.type() == LogInfo.StaffType.SeaportOfficer && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SeaportOfficer.cnf");
            ItemState itemState = getItemState_from_String(deliveryService.getState("loader_SeaportOfficer.cnf", itemName));
            int staffId = staffService.getStaffId("loader_SeaportOfficer.cnf", logInfo.name());
            String staffCity = staffService.getStaffCity("loader_SeaportOfficer.cnf", logInfo.name());
            if (itemState != null) {
                if (itemState.equals(ExportChecking)) {
                    if (success)
                        result = deliveryService.setItemState_PackingToContainer("loader_SeaportOfficer.cnf", itemName, staffId, staffCity);
                    else
                        result = deliveryService.setItemState_ExportCheckFail("loader_SeaportOfficer.cnf", itemName, staffId, staffCity);
                } else if (itemState.equals(ImportChecking)) {
                    if (success)
                        result = deliveryService.setItemState_FromImportTransporting("loader_SeaportOfficer.cnf", itemName, staffId, staffCity);
                    else
                        result = deliveryService.setItemState_ImportCheckFail("loader_SeaportOfficer.cnf", itemName, staffId, staffCity);
                }
            }
        }
        closeResource(CON);
        return result;
    }


    /**
     * Sustc Manager
     */
    @Override
    public int getCompanyCount(LogInfo logInfo) {
        int result = -1;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            result = companyService.getCompanyCount("loader_SUSTCDepartmentManager.cnf");
        }
        closeResource(CON);
        return result;
    }

    @Override
    public int getCityCount(LogInfo logInfo) {
        int result = -1;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            result = cityService.getCityCount("loader_SUSTCDepartmentManager.cnf");
        }
        closeResource(CON);
        return result;
    }

    @Override
    public int getCourierCount(LogInfo logInfo) {
        int result = -1;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            result = staffService.getCourierCount("loader_SUSTCDepartmentManager.cnf");
        }
        closeResource(CON);
        return result;
    }

    @Override
    public int getShipCount(LogInfo logInfo) {
        int result = -1;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            result = shipService.getShipCount("loader_SUSTCDepartmentManager.cnf");
        }
        closeResource(CON);
        return result;
    }

    @Override
    public ItemInfo getItemInfo(LogInfo logInfo, String item_name) {
        ItemInfo itemInfo = null;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            Delivery deliveryRecord = deliveryService.getDelivery("loader_SUSTCDepartmentManager.cnf", item_name);
            if (deliveryRecord != null) {
                ItemInfo.RetrievalDeliveryInfo retrieval = new ItemInfo.RetrievalDeliveryInfo(deliveryRecord.getRetrieval_city(),
                        staffService.getStaffName("loader_SUSTCDepartmentManager.cnf", deliveryRecord.getRetrieval_courier_id()));
                ItemInfo.RetrievalDeliveryInfo delivery = new ItemInfo.RetrievalDeliveryInfo(deliveryRecord.getDelivery_city(),
                        staffService.getStaffName("loader_SUSTCDepartmentManager.cnf", deliveryRecord.getDelivery_courier_id()));
                ItemInfo.ImportExportInfo export = new ItemInfo.ImportExportInfo(deliveryRecord.getExport_city(),
                        staffService.getStaffName("loader_SUSTCDepartmentManager.cnf", deliveryRecord.getExport_officer_id()),
                        cityTaxService.getExportTaxRate("loader_SUSTCDepartmentManager.cnf",
                                deliveryRecord.getExport_city(), deliveryRecord.getItem_type()) * deliveryRecord.getItem_price());
                ItemInfo.ImportExportInfo $import = new ItemInfo.ImportExportInfo(deliveryRecord.getImport_city(),
                        staffService.getStaffName("loader_SUSTCDepartmentManager.cnf", deliveryRecord.getImport_officer_id()),
                        cityTaxService.getImportTaxRate("loader_SUSTCDepartmentManager.cnf",
                                deliveryRecord.getImport_city(), deliveryRecord.getItem_type()) * deliveryRecord.getItem_price());
                itemInfo = new ItemInfo(
                        deliveryRecord.getItem_name(),
                        deliveryRecord.getItem_type(),
                        deliveryRecord.getItem_price(),
                        getItemState_from_String(deliveryRecord.getState()),
                        retrieval,
                        delivery,
                        $import,
                        export
                );
            }
        }
        closeResource(CON);
        return itemInfo;
    }

    @Override
    public ShipInfo getShipInfo(LogInfo logInfo, String ship_name) {
        ShipInfo shipInfo = null;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            Ship ship = shipService.getShip("loader_SUSTCDepartmentManager.cnf", ship_name);
            if (ship != null)
                shipInfo = new ShipInfo(ship.getName(), ship.getCompany(), ship.getSailing());
        }
        closeResource(CON);
        return shipInfo;
    }

    @Override
    public ContainerInfo getContainerInfo(LogInfo logInfo, String container_code) {
        ContainerInfo containerInfo = null;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            Container container = containerService.getContainer("loader_SUSTCDepartmentManager.cnf", container_code);
            if (container != null)
                containerInfo = new ContainerInfo(getContainerType_from_String(container.getType()), container.getCode(), container.getUsing());
        }
        closeResource(CON);
        return containerInfo;
    }

    @Override
    public StaffInfo getStaffInfo(LogInfo logInfo, String staff_name) {
        StaffInfo staffInfo = null;
        if (logInfo.type() == LogInfo.StaffType.SustcManager && logService.check_logInfo(logInfo)) {
            CON = getConnection("loader_SUSTCDepartmentManager.cnf");
            Staff staff = staffService.getStaff("loader_SUSTCDepartmentManager.cnf", staff_name);
            Calendar ca = Calendar.getInstance();
            ca.setTime(staff.getBirth_year());
            int year = ca.get(Calendar.YEAR);
            String password = logService.getPassword("loader_SUSTCDepartmentManager.cnf", staff.getId());
            LogInfo l = new LogInfo(staff.getName(), LogInfo.StaffType.valueOf(staff.getType().replaceAll(" ", "").equals("SUSTCDepartmentManager") ? "SustcManager" : staff.getType().replaceAll(" ", "")), password);
            staffInfo = new StaffInfo(l, staff.getCompany(), staff.getCity(), staff.getGender().equals("female"), 2022 - year, staff.getPhone_number());
        }
        closeResource(CON);
        return staffInfo;
    }
}
