package main.model;

import main.entity.Entity;

public interface AccessObject {
    Entity[] loadData(boolean verbose);

    void importData(boolean verbose);

    void insert(boolean verbose, Entity[] entities);

    void dropAll(boolean verbose);
}

