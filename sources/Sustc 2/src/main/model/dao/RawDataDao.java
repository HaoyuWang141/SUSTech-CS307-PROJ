package main.model.dao;

public interface RawDataDao extends Dao {
    void loadAndImport_from_csv(String recordsCSV,String staffsCSV);
}