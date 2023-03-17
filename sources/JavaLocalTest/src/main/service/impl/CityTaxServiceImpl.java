package main.service.impl;

import main.entity.CityTax;
import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.CityTaxDao;
import main.model.dao.impl.CityTaxDaoImpl;
import main.model.fao.CityTaxFao;
import main.model.fao.impl.CityTaxFaoImpl;
import main.service.CityTaxService;

import java.util.ArrayList;

public class CityTaxServiceImpl implements CityTaxService {
    private static final CityTaxDao cityTaxDao = new CityTaxDaoImpl();
    private static final CityTaxFao cityTaxFao = new CityTaxFaoImpl();

    @Override
    public void importData(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    cityTaxDao.importData(verbose);
                    break;
                case csv:
                    cityTaxFao.importData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Double getImportTaxRate(String load_cnf, String city, String type) {
        double taxRate = -1.;
        CityTax[] cityTaxes = cityTaxDao.getTaxRate(load_cnf, city, type);
        if (cityTaxes != null && cityTaxes.length == 1) {
            if (cityTaxes[0].getImport_tax_rate() != 0)
                taxRate = cityTaxes[0].getImport_tax_rate();
        }
        return taxRate;

    }

    @Override
    public Double getExportTaxRate(String load_cnf, String city, String type) {
        double taxRate = -1.;
        CityTax[] cityTaxes = cityTaxDao.getTaxRate(load_cnf, city, type);
        if (cityTaxes != null && cityTaxes.length == 1) {
            if (cityTaxes[0].getExport_tax_rate() != 0)
                taxRate = cityTaxes[0].getExport_tax_rate();
        }
        return taxRate;
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] cityTaxList) {
        try {
            switch (source) {
                case database:
                    cityTaxDao.insert(verbose, cityTaxList);
                    break;
                case csv:
                    cityTaxFao.insert(verbose, cityTaxList);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void insert(boolean verbose, SOURCE source, String[] strings) {
        ArrayList<CityTax> cityTax = new ArrayList<>();
        for (String str : strings) {
            cityTax.add(new CityTax(str));
        }
        try {
            switch (source) {
                case database:
                    cityTaxDao.insert(verbose, cityTax.toArray(new CityTax[0]));
                    break;
                case csv:
                    cityTaxFao.insert(verbose, cityTax.toArray(new CityTax[0]));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Entity[] loadData(boolean verbose, SOURCE source) {
        CityTax[] cityTaxes = null;
        try {
            switch (source) {
                case database:
                    cityTaxes = (CityTax[]) cityTaxDao.loadData(verbose);
                    break;
                case csv:
                    cityTaxes = (CityTax[]) cityTaxFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return cityTaxes;
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    cityTaxDao.dropAll(verbose);
                    break;
                case csv:
                    cityTaxFao.dropAll(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
