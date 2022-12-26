package main.model.dao;

import main.entity.Entity;

public interface RawDataDao extends Dao {
    void loadAndImport_from_csv(String recordsCSV,String staffsCSV);
}