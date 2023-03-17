package main.service;

import main.entity.Staff;
import main.enumcase.SOURCE;

public interface StaffService extends Service {
    void importData(boolean verbose, SOURCE source);

    int getStaffId(String loader_cnf, String staff_name);

    int getCourierCount(String loader_cnf);

    Staff getStaff(String loader_cnf, String staff_name);

    String getStaffName(String loader_cnf, int staff_id);

    String getStaffCity(String loader_cnf, String staff_name);

    String getStaffCompany(String loader_cnf, String staff_name);
}
