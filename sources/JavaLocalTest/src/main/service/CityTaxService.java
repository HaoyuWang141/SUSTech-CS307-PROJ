package main.service;

import main.enumcase.SOURCE;

public interface CityTaxService extends Service{
    void importData(boolean verbose, SOURCE source);

    Double getImportTaxRate(String load_cnf,String city,String type);
    Double getExportTaxRate(String load_cnf,String city,String type);

}
