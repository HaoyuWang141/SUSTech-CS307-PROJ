package main.service.impl;

import main.entity.Entity;
import main.entity.Ship;
import main.enumcase.SOURCE;
import main.model.dao.ShipDao;
import main.model.dao.impl.ShipDaoImpl;
import main.model.fao.ShipFao;
import main.model.fao.impl.ShipFaoImpl;
import main.service.ShipService;

import java.util.ArrayList;

public class ShipServiceImpl implements ShipService {
    private static final ShipDao shipDao = new ShipDaoImpl();
    private static final ShipFao shipFao = new ShipFaoImpl();


    @Override
    public void importData(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    shipDao.importData(verbose);
                    break;
                case csv:
                    shipFao.importData(verbose);
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
    public boolean shipStartSailing(String loader_cnf, String ship_name) {
        return shipDao.updateSailing(loader_cnf, ship_name, true);
    }

    @Override
    public int getShipCount(String loader_cnf) {
        return shipDao.getCount(loader_cnf);
    }

    @Override
    public Ship getShip(String loader_cnf, String ship_name) {
        Ship[] ships = shipDao.getShip(loader_cnf, ship_name);
        if (ships.length == 1)
            return ships[0];
        return null;
    }

    @Override
    public boolean getSailing(String loader_cnf, String ship_name) {
        Ship[] ships = shipDao.getShip(loader_cnf, ship_name);
        if (ships.length == 1)
            return ships[0].getSailing();
        return false;
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] ships) {
        try {
            switch (source) {
                case database:
                    shipDao.insert(verbose, ships);
                    break;
                case csv:
                    shipFao.insert(verbose, ships);
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
        ArrayList<Ship> ships = new ArrayList<>();
        for (String str : strings) {
            ships.add(new Ship(str));
        }
        try {
            switch (source) {
                case database:
                    shipDao.insert(verbose, ships.toArray(new Ship[0]));
                    break;
                case csv:
                    shipFao.insert(verbose, ships.toArray(new Ship[0]));
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
    public Entity[] loadData(boolean verbose, SOURCE source) {
        Ship[] ships = null;
        try {
            switch (source) {
                case database:
                    ships = (Ship[]) shipDao.loadData(verbose);
                    break;
                case csv:
                    ships = (Ship[]) shipFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ships;
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    shipDao.dropAll(verbose);
                    break;
                case csv:
                    shipFao.dropAll(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
