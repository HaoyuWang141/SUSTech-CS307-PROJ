package main.service.impl;

import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.StaffTypeDao;
import main.model.dao.impl.StaffTypeDaoImpl;
import main.service.StaffTypeService;

import static main.util.PostgresqlUtil.verbose;

public class StaffTypeServiceImpl implements StaffTypeService {
    private static StaffTypeDao staffTypeDao = new StaffTypeDaoImpl();
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

    @Override
    public void importData() {
        staffTypeDao.importData(verbose);
    }
}
