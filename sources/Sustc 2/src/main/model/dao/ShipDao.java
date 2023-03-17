package main.model.dao;

import main.entity.Ship;

public interface ShipDao extends Dao {
    boolean updateSailing(String loader_cnf, String ship_name, boolean sailing);

    int getCount(String loader_cnf);

    Ship[] getShip(String loader_cnf, String ship_name);
}
