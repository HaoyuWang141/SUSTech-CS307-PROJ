package main.service.impl;

import main.entity.Entity;
import main.entity.SeaTransportation;
import main.enumcase.SOURCE;
import main.model.dao.SeaTransportationDao;
import main.model.dao.impl.SeaTransportationDaoImpl;
import main.service.SeaTransportationService;

import static main.util.PostgresqlUtil.verbose;

public class SeaTransportationServiceImpl implements SeaTransportationService {
    SeaTransportationDao seaTransportationDao = new SeaTransportationDaoImpl();

    @Override
    public String getShipName_by_containerCode(String loader_cnf, String containerCode) {
        SeaTransportation[] seaTransportations = seaTransportationDao.getSeaTransportation_by_containerCode(loader_cnf, containerCode);
        if (seaTransportations.length == 1)
            return seaTransportations[0].getShip_name();
        return null;
    }

    @Override
    public boolean updateShip_by_containerCode(String loader_cnf, String containerCode, String shipName) {
        return seaTransportationDao.updateShip_by_containerCode(loader_cnf, containerCode, shipName);
    }

    @Override
    public String getShipName_by_itemName(String loader_cnf, String itemName) {
        SeaTransportation[] seaTransportations = seaTransportationDao.getSeaTransportation_by_itemName(loader_cnf, itemName);
        if (seaTransportations.length == 1)
            return seaTransportations[0].getShip_name();
        return null;
    }

    @Override
    public boolean delete_by_Ship(String loader_cnf, String shipName) {
        return seaTransportationDao.delete_by_ship(loader_cnf, shipName);
    }

    @Override
    public boolean delete_by_Item(String loader_cnf, String itemName) {
        return seaTransportationDao.delete_by_item(loader_cnf, itemName);
    }

    @Override
    public void importData() {
        seaTransportationDao.importData(verbose);
    }

    @Override
    public int getItemCount_by_Ship(String loader_cnf, String shipName) {
        return seaTransportationDao.getItemCount_by_Ship(loader_cnf,shipName);
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] entities) {

    }

    @Override
    public void insert(boolean verbose, SOURCE source, String[] strings) {

    }

    @Override
    public Entity[] loadData(boolean verbose, SOURCE source) {
        return new Entity[0];
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {

    }
}
