package main.service.impl;

import main.entity.City;
import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.CityDao;
import main.model.dao.impl.CityDaoImpl;
import main.model.fao.CityFao;
import main.model.fao.impl.CityFaoImpl;
import main.service.CityService;

import java.util.ArrayList;

public class CityServiceImpl implements CityService {
    private static final CityDao cityDao = new CityDaoImpl();
    private static final CityFao cityFao = new CityFaoImpl();

    @Override
    public void importData(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    cityDao.importData(verbose);
                    break;
                case csv:
                    cityFao.importData(verbose);
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
    public int getCityCount(String loader_cnf) {
        return cityDao.getCityCount(loader_cnf);
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] cities) {
        try {
            switch (source) {
                case database:
                    cityDao.insert(verbose, cities);
                    break;
                case csv:
                    cityFao.insert(verbose, cities);
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
        ArrayList<City> cities = new ArrayList<>();
        for (String str : strings) {
            cities.add(new City(str));
        }
        try {
            switch (source) {
                case database:
                    cityDao.insert(verbose, cities.toArray(new City[0]));
                    break;
                case csv:
                    cityFao.insert(verbose, cities.toArray(new City[0]));
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
        City[] cities = null;
        try {
            switch (source) {
                case database:
                    cities = (City[]) cityDao.loadData(verbose);
                    break;
                case csv:
                    cities = (City[]) cityFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public void dropAll(boolean verbose,SOURCE source) {
        try {
            switch (source) {
                case database:
                    cityDao.dropAll(verbose);
                    break;
                case csv:
                    cityFao.dropAll(verbose);
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
