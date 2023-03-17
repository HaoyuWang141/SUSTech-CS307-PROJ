package main.service.impl;

import main.entity.Company;
import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.CompanyDao;
import main.model.dao.impl.CompanyDaoImpl;
import main.model.fao.CompanyFao;
import main.model.fao.impl.CompanyFaoImpl;
import main.service.CompanyService;

import java.util.ArrayList;

public class CompanyServiceImpl implements CompanyService {
    public static final CompanyDao companyDao = new CompanyDaoImpl();
    public static final CompanyFao companyFao = new CompanyFaoImpl();

    @Override
    public void importData(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    companyDao.importData(verbose);
                    break;
                case csv:
                    companyFao.importData(verbose);
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
    public int getCompanyCount(String loader_cnf) {
        return companyDao.getCompanyCount(loader_cnf);
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] companies) {
        try {
            switch (source) {
                case database:
                    companyDao.insert(verbose, companies);
                    break;
                case csv:
                    companyFao.insert(verbose, companies);
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
        ArrayList<Company> companies = new ArrayList<>();
        for (String str : strings) {
            companies.add(new Company(str));
        }
        try {
            switch (source) {
                case database:
                    companyDao.insert(verbose, companies.toArray(new Company[0]));
                    break;
                case csv:
                    companyFao.insert(verbose, companies.toArray(new Company[0]));
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
        Company[] companies = null;
        try {
            switch (source) {
                case database:
                    companies = (Company[]) companyDao.loadData(verbose);
                    break;
                case csv:
                    companies = (Company[]) companyFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    companyDao.dropAll(verbose);
                    break;
                case csv:
                    companyFao.dropAll(verbose);
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
