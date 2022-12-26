package main.model.dao;

import main.entity.SeaTransportation;

public interface SeaTransportationDao extends Dao {
    SeaTransportation[] getSeaTransportation_by_containerCode(String loader_cnf, String container_code);

    boolean updateShip_by_containerCode(String loader_cnf, String container_code, String ship_name);

    SeaTransportation[] getSeaTransportation_by_itemName(String loader_cnf, String item_name);

    boolean delete_by_ship(String loader_cnf, String ship_name);
    boolean delete_by_item(String loader_cnf, String itemName);

    int getItemCount_by_Ship(String loader_cnf, String ship_name);


}
