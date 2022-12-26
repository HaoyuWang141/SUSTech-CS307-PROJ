package main.service;

import main.enumcase.SOURCE;

public interface CompanyService extends Service{
    void importData(boolean verbose, SOURCE source);

    int getCompanyCount(String loader_cnf);
}
