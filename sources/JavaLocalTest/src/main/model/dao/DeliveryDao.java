package main.model.dao;

import main.entity.Delivery;

public interface DeliveryDao extends Dao {
    Delivery[] getDelivery(String loader_cnf, String item_name);

    Delivery[] getDelivery_at_Port(String loader_cnf, String city);

    boolean insert(String loader_cnf, Delivery delivery);

    boolean updateState_ToExportTransporting(String loader_cnf, String itemName);

    boolean updateState_ExportChecking(String loader_cnf, String itemName);

    boolean updateState_ExportCheckFail(String loader_cnf, String itemName, int staffId);

    boolean updateState_PackingToContainer(String loader_cnf, String itemName, int staffId);

    boolean updateContainer_atState_PackingToContainer(String loader_cnf, String itemName, String containerCode);

    boolean updateState_ImportChecking(String loader_cnf, String itemName);

    boolean updateState_ImportCheckFail(String loader_cnf, String itemName, int staffId);

    boolean updateState_FromImportTransporting(String loader_cnf, String itemName, int staffId);

    boolean updateDeliveryCourierId(String loader_cnf, String itemName, int staffId);

    boolean updateState_Delivering(String loader_cnf, String itemName);

    boolean updateState_Finish(String loader_cnf, String itemName);
}
