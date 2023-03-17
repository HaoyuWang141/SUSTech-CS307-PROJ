package main.service.impl;

import main.entity.Delivery;
import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.DeliveryDao;
import main.model.dao.impl.DeliveryDaoImpl;
import main.model.fao.DeliveryFao;
import main.model.fao.impl.DeliveryFaoImpl;
import main.service.DeliveryService;

import java.util.ArrayList;

public class DeliveryServiceImpl implements DeliveryService {
    private static final DeliveryDao deliveryDao = new DeliveryDaoImpl();
    private static final DeliveryFao deliveryFao = new DeliveryFaoImpl();

    @Override
    public void importData(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    deliveryDao.importData(verbose);
                    break;
                case csv:
                    deliveryFao.importData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] deliveries) {
        try {
            switch (source) {
                case database:
                    deliveryDao.insert(verbose, deliveries);
                    break;
                case csv:
                    deliveryFao.insert(verbose, deliveries);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void insert(boolean verbose, SOURCE source, String[] strings) {

    }

    @Override
    public Entity[] loadData(boolean verbose, SOURCE source) {
        Delivery[] deliveries = null;
        try {
            switch (source) {
                case database:
                    deliveries = (Delivery[]) deliveryDao.loadData(verbose);
                    break;
                case csv:
                    deliveries = (Delivery[]) deliveryFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return deliveries;
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    deliveryDao.dropAll(verbose);
                    break;
                case csv:
                    deliveryFao.dropAll(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getState(String loader_cnf, String itemName) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1)
            return deliveries[0].getState();
        return null;
    }

    @Override
    public Delivery getDelivery(String loader_cnf, String itemName) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1)
            return deliveries[0];
        return null;
    }

    @Override
    public String[] getAllItemNameAtPort(String loader_cnf, String city) {
        ArrayList<String> itemNames = new ArrayList<>();
        Delivery[] deliveries = deliveryDao.getDelivery_at_Port(loader_cnf, city);
        for (Delivery delivery : deliveries) {
            itemNames.add(delivery.getItem_name());
        }
        return (itemNames.size() == 0) ? null : itemNames.toArray(new String[0]);
    }

    @Override
    public boolean insertDelivery(String loader_cnf, Delivery delivery) {
        return deliveryDao.insert(loader_cnf, delivery);
    }

    @Override
    public boolean setItemState_ToExportTransporting(String loader_cnf, String itemName, int staffId) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries != null && deliveries.length == 1 && deliveries[0].getRetrieval_courier_id() == staffId)
            return deliveryDao.updateState_ToExportTransporting(loader_cnf, itemName);
        return false;
    }

    @Override
    public boolean setItemState_ExportChecking(String loader_cnf, String itemName, int staffId) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getRetrieval_courier_id() == staffId)
            return deliveryDao.updateState_ExportChecking(loader_cnf, itemName);
        return false;
    }

    @Override
    public boolean setItemState_ExportCheckFail(String loader_cnf, String itemName, int staffId, String city) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getExport_city().equals(city))
            return deliveryDao.updateState_ExportCheckFail(loader_cnf, itemName, staffId);
        return false;
    }

    @Override
    public boolean setItemState_PackingToContainer(String loader_cnf, String itemName, int staffId, String city) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getExport_city().equals(city))
            return deliveryDao.updateState_PackingToContainer(loader_cnf, itemName, staffId);
        return false;
    }

    @Override
    public boolean updateContainer_atState_PackingToContainer(String loader_cnf, String itemName, String containerCode) {
        return deliveryDao.updateContainer_atState_PackingToContainer(loader_cnf, itemName, containerCode);
    }

    @Override
    public boolean setItemState_ImportChecking(String loader_cnf, String itemName) {
        return deliveryDao.updateState_ImportChecking(loader_cnf, itemName);
    }

    @Override
    public boolean setItemState_ImportCheckFail(String loader_cnf, String itemName, int staffId, String city) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getImport_city().equals(city))
            return deliveryDao.updateState_ImportCheckFail(loader_cnf, itemName, staffId);
        return false;
    }

    @Override
    public boolean setItemState_FromImportTransporting(String loader_cnf, String itemName, int staffId, String city) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getImport_city().equals(city))
            return deliveryDao.updateState_FromImportTransporting(loader_cnf, itemName, staffId);
        return false;
    }

    @Override
    public boolean update_DeliveryCourierStaffId_atState_FromImportTransporting(String loader_cnf, String itemName, int staffId) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getDelivery_courier_id() == null)
            return deliveryDao.updateDeliveryCourierId(loader_cnf, itemName, staffId);
        return false;
    }

    @Override
    public boolean setItemState_Delivering(String loader_cnf, String itemName, int staffId) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getDelivery_courier_id() == staffId)
            return deliveryDao.updateState_Delivering(loader_cnf, itemName);
        return false;
    }

    @Override
    public boolean setItemState_Finish(String loader_cnf, String itemName, int staffId) {
        Delivery[] deliveries = deliveryDao.getDelivery(loader_cnf, itemName);
        if (deliveries.length == 1 && deliveries[0].getDelivery_courier_id() == staffId)
            return deliveryDao.updateState_Finish(loader_cnf, itemName);
        return false;
    }

}
