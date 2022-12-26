package main.service;

import main.interfaces.LogInfo;

public interface LogService extends Service{
    boolean check_logInfo(LogInfo logInfo);

    void importData();


    String getPassword(String loader_cnf, int id);
}
