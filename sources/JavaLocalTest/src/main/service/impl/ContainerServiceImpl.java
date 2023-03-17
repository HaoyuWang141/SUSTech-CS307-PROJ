package main.service.impl;

import main.entity.Container;
import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.ContainerDao;
import main.model.dao.impl.ContainerDaoImpl;
import main.model.fao.ContainerFao;
import main.model.fao.impl.ContainerFaoImpl;
import main.service.ContainerService;

import java.util.ArrayList;

public class ContainerServiceImpl implements ContainerService {
    private static final ContainerDao containerDao = new ContainerDaoImpl();
    private static final ContainerFao containerFao = new ContainerFaoImpl();

    @Override
    public void importData(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    containerDao.importData(verbose);
                    break;
                case csv:
                    containerFao.importData(verbose);
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
    public boolean getUsing(String loader_cnf, String container_code) {
        Container[] containers = containerDao.getContainer(loader_cnf, container_code);
        if (containers != null && containers.length == 1)
            return containers[0].getUsing();
        return false;
    }

    @Override
    public Container getContainer(String loader_cnf, String container_code) {
        Container[] containers = containerDao.getContainer(loader_cnf, container_code);
        if (containers.length == 1) return containers[0];
        return null;
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] containers) {
        try {
            switch (source) {
                case database:
                    containerDao.insert(verbose, containers);
                    break;
                case csv:
                    containerFao.insert(verbose, containers);
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
        ArrayList<Container> containers = new ArrayList<>();
        for (String str : strings) {
            containers.add(new Container(str));
        }
        try {
            switch (source) {
                case database:
                    containerDao.insert(verbose, containers.toArray(new Container[0]));
                    break;
                case csv:
                    containerFao.insert(verbose, containers.toArray(new Container[0]));
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
        Container[] containers = null;
        try {
            switch (source) {
                case database:
                    containers = (Container[]) containerDao.loadData(verbose);
                    break;
                case csv:
                    containers = (Container[]) containerFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return containers;
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    containerDao.dropAll(verbose);
                    break;
                case csv:
                    containerFao.dropAll(verbose);
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
