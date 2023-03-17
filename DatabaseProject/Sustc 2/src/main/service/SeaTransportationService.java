package main.service;

public interface SeaTransportationService extends Service {
    String getShipName_by_containerCode(String loader_cnf, String containerCode);

    boolean updateShip_by_containerCode(String loader_cnf, String containerCode, String shipName);

    String getShipName_by_itemName(String loader_cnf, String itemName);

    boolean delete_by_Ship(String loader_cnf, String shipName);
    boolean delete_by_Item(String loader_cnf, String itemName);
    void importData();

    int getItemCount_by_Ship(String loader_cnf, String shipName);


}
