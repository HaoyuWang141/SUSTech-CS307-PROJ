package main.service;

import main.entity.Entity;
import main.enumcase.SOURCE;

public interface Service {
    void insert(boolean verbose, SOURCE source, Entity[] entities);

    void insert(boolean verbose, SOURCE source, String[] strings);

    Entity[] loadData(boolean verbose, SOURCE source);

    void dropAll(boolean verbose, SOURCE source);
}
