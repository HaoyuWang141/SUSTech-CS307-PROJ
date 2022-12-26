package main.model.dao;

import main.entity.Log;

public interface LogDao extends Dao {
    Log[] getLog(String loader_cnf, int staff_id);
}
