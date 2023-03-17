package main.service;

import main.entity.Delivery;
import main.enumcase.SOURCE;

public interface DeliveryService extends Service {
    void importData(boolean verbose, SOURCE source);

    String getState(String loader_cnf, String itemName);

    Delivery getDelivery(String loader_cnf, String itemName);

    String[] getAllItemNameAtPort(String loader_cnf, String city);

    boolean insertDelivery(String loader_cnf, Delivery delivery);

    boolean setItemState_ToExportTransporting(String loader_cnf, String itemName, int staffId);

    boolean setItemState_ExportChecking(String loader_cnf, String itemName, int staffId);

    boolean setItemState_ExportCheckFail(String loader_cnf, String itemName, int staffId, String city);

    boolean setItemState_PackingToContainer(String loader_cnf, String itemName, int staffId, String city);

    boolean updateContainer_atState_PackingToContainer(String loader_cnf, String itemName, String containerCode);

    boolean setItemState_ImportChecking(String loader_cnf, String itemName);

    boolean setItemState_ImportCheckFail(String loader_cnf, String itemName, int staffId, String city);

    boolean setItemState_FromImportTransporting(String loader_cnf, String itemName, int staffId, String city);

    boolean update_DeliveryCourierStaffId_atState_FromImportTransporting(String loader_cnf, String itemName, int staffId);

    boolean setItemState_Delivering(String loader_cnf, String itemName, int staffId);

    boolean setItemState_Finish(String loader_cnf, String itemName, int staffId);

}
