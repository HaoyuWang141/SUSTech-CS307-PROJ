package main.model.dao;

public interface CompanyDao extends Dao {
    int getCompanyCount(String loader_cnf);
}
