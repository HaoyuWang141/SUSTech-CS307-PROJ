package main.service.impl;

import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.StateDao;
import main.model.dao.impl.StateDaoImpl;
import main.service.StateService;

import static main.util.PostgresqlUtil.verbose;

public class StateServiceImpl implements StateService {
    private static StateDao stateDao = new StateDaoImpl();

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
        stateDao.importData(verbose);
    }
}
