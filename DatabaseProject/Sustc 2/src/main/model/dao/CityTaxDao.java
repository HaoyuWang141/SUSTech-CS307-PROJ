package main.model.dao;

import main.entity.CityTax;

public interface CityTaxDao extends Dao {
    CityTax[] getTaxRate(String loaderCnf,String city,String type);
}
