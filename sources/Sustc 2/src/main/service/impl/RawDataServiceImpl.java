package main.service.impl;

import main.entity.Entity;
import main.entity.RawData;
import main.enumcase.SOURCE;
import main.model.dao.RawDataDao;
import main.model.dao.impl.RawDataDaoImpl;
import main.model.fao.RawDataFao;
import main.model.fao.impl.RawDataFaoImpl;
import main.service.RawDataService;


public class RawDataServiceImpl implements RawDataService {
    private static final RawDataDao rawDataDao = new RawDataDaoImpl();
    private static final RawDataFao rawDataFao = new RawDataFaoImpl();

    @Override
    public void importData(String recordsCSV, String staffsCSV) {
        rawDataDao.loadAndImport_from_csv(recordsCSV,staffsCSV);
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] entities) {
        try {
            throw new Exception(" insert data into Origin data.csv");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void insert(boolean verbose, SOURCE source, String[] strings) {
        try {
            throw new Exception(" insert data into Origin data.csv");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public Entity[] loadData(boolean verbose, SOURCE source) {
        RawData[] rawDataArray = null;
        try {
            switch (source) {
                case database:
                    rawDataArray = (RawData[]) rawDataDao.loadData(verbose);
                    break;
                case csv:
                    rawDataArray = (RawData[]) rawDataFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rawDataArray;
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    rawDataDao.dropAll(verbose);
                    break;
                case csv:
                    rawDataFao.dropAll(verbose);
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
