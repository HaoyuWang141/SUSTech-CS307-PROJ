package main.service.impl;

import main.entity.Staff;
import main.entity.Entity;
import main.enumcase.SOURCE;
import main.model.dao.StaffDao;
import main.model.dao.impl.StaffDaoImpl;
import main.model.fao.CourierFao;
import main.model.fao.impl.CourierFaoImpl;
import main.service.StaffService;

import java.util.ArrayList;

public class StaffServiceImpl implements StaffService {
    public static final StaffDao staffDao = new StaffDaoImpl();
    public static final CourierFao courierFao = new CourierFaoImpl();


    @Override
    public void importData(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    staffDao.importData(verbose);
                    break;
                case csv:
                    courierFao.importData(verbose);
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
    public int getStaffId(String loader_cnf, String staff_name) {
        Staff[] staffs = staffDao.getStaff(loader_cnf, staff_name);
        if (staffs.length == 1)
            return staffs[0].getId();
        return -1;
    }

    @Override
    public int getCourierCount(String loader_cnf) {
        return staffDao.getCourierCount(loader_cnf);
    }

    @Override
    public Staff getStaff(String loader_cnf, String staff_name) {
        Staff[] staffs = staffDao.getStaff(loader_cnf, staff_name);
        if (staffs.length == 1)
            return staffs[0];
        return null;
    }

    @Override
    public String getStaffName(String loader_cnf, int staff_id) {
        Staff[] staffs = staffDao.getStaff(loader_cnf, staff_id);
        if (staffs.length == 1)
            return staffs[0].getName();
        return null;
    }

    @Override
    public String getStaffCity(String loader_cnf, String staff_name) {
        Staff[] staffs = staffDao.getStaff(loader_cnf, staff_name);
        if (staffs != null && staffs.length == 1)
            return staffs[0].getCity();
        return null;
    }

    @Override
    public String getStaffCompany(String loader_cnf, String staff_name) {
        Staff[] staffs = staffDao.getStaff(loader_cnf, staff_name);
        if (staffs.length == 1)
            return staffs[0].getCompany();
        return null;
    }

    @Override
    public void insert(boolean verbose, SOURCE source, Entity[] couriers) {
        try {
            switch (source) {
                case database:
                    staffDao.insert(verbose, couriers);
                    break;
                case csv:
                    courierFao.insert(verbose, couriers);
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
        ArrayList<Staff> staffs = new ArrayList<>();
        for (String str : strings) {
            staffs.add(new Staff(str));
        }
        try {
            switch (source) {
                case database:
                    staffDao.insert(verbose, staffs.toArray(new Staff[0]));
                    break;
                case csv:
                    courierFao.insert(verbose, staffs.toArray(new Staff[0]));
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
        Staff[] staffs = null;
        try {
            switch (source) {
                case database:
                    staffs = (Staff[]) staffDao.loadData(verbose);
                    break;
                case csv:
                    staffs = (Staff[]) courierFao.loadData(verbose);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return staffs;
    }

    @Override
    public void dropAll(boolean verbose, SOURCE source) {
        try {
            switch (source) {
                case database:
                    staffDao.dropAll(verbose);
                    break;
                case csv:
                    courierFao.dropAll(verbose);
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
