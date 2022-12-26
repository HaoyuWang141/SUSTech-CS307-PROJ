package main.service;

import main.enumcase.SOURCE;

public interface CityService extends Service{
    void importData(boolean verbose, SOURCE source);

    int getCityCount(String loader_cnf);
}
