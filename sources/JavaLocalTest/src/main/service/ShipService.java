package main.service;

import main.entity.Ship;
import main.enumcase.SOURCE;

public interface ShipService extends Service {
    void importData(boolean verbose, SOURCE source);

    boolean shipStartSailing(String loader_cnf, String ship_name);

    int getShipCount(String loader_cnf);

    Ship getShip(String loader_cnf, String ship_name);

    boolean getSailing(String loader_cnf, String ship_name);
}
