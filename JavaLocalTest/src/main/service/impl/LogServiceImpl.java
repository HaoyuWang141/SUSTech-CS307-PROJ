package main.service.impl;

import main.interfaces.LogInfo;
import main.entity.Entity;
import main.entity.Log;
import main.enumcase.SOURCE;
import main.model.dao.LogDao;
import main.model.dao.impl.LogDaoImpl;
import main.service.LogService;
import main.service.StaffService;

import static main.util.PostgresqlUtil.*;

public class LogServiceImpl implements LogService {
    private static final LogDao logDao = new LogDaoImpl();
    private static final StaffService staffService = new StaffServiceImpl();

    @Override
    public boolean check_logInfo(LogInfo logInfo) {
        CON = getConnection("loader_LogChecker.cnf");
        int staffId = staffService.getStaffId("loader_LogChecker.cnf", logInfo.name());
        Log[] logs = logDao.getLog("loader_LogChecker.cnf", staffId);
        closeResource(CON);
        if (logs != null && logs.length == 1 && logs[0].getPassword().equals(logInfo.password()))
            return true;
        return false;
    }

    @Override
    public void importData() {
        logDao.importData(verbose);
    }

    @Override
    public String getPassword(String loader_cnf, int id) {
        Log[] logs = logDao.getLog(loader_cnf, id);
        if (logs != null && logs.length == 1) return logs[0].getPassword();
        return null;
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
