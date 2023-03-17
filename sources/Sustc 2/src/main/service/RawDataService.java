package main.service;

public interface RawDataService extends Service{
    void importData(String recordsCSV, String staffsCSV);
}
