package main.service;

import main.enumcase.INSERT_TYPE;
import main.enumcase.SOURCE;

public interface RawDataService extends Service{
    void importData(String recordsCSV, String staffsCSV);
}
