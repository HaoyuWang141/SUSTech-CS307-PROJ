package main.model.dao;

import main.entity.Staff;

public interface StaffDao extends Dao {
    Staff[] getStaff(String loader_cnf, String staff_name);

    Staff[] getStaff(String loader_cnf, int staff_id);

    int getCourierCount(String loader_cnf);
}
