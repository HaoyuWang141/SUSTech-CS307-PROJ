package main.model.dao;

import main.entity.Container;

public interface ContainerDao extends Dao {
    Container[] getContainer(String loader_cnf, String container_code);
}
