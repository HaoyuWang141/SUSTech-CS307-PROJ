package main.service;

import main.entity.Container;
import main.enumcase.SOURCE;

public interface ContainerService extends Service {
    void importData(boolean verbose, SOURCE source);

    boolean getUsing(String loader_cnf, String container_code);

    Container getContainer(String loader_cnf, String container_code);
}
