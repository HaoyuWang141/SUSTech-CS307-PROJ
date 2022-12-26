-- REVOKE ALL ON TABLE city,city_tax,company,container,delivery,log,sea_transportation,ship,staff,staff_type,state FROM LogChecker,Courier,CompanyManager,SeaportOfficer,SUSTCDepartmentManager;
drop user if exists LogChecker,Courier,CompanyManager,SeaportOfficer,SUSTCDepartmentManager;

-- role
create user LogChecker with password '111111';
create user Courier with password '111111';
create user CompanyManager with password '111111';
create user SeaportOfficer with password '111111';
create user SUSTCDepartmentManager with password '111111';
-- GRANT ALL ON TABLE city,city_tax,company,container,delivery,log,sea_transportation,ship,staff,staff_type,state TO LogChecker,Courier,CompanyManager,SeaportOfficer,SUSTCDepartmentManager;
-- LogChecker
GRANT SELECT, INSERT, UPDATE ON TABLE staff,log TO LogChecker;
-- SUSTCDepartmentManager
GRANT SELECT ON TABLE city,city_tax,company,container,delivery,log,sea_transportation,ship,staff,staff_type,state
    TO SUSTCDepartmentManager;
-- Courier:
GRANT SELECT, INSERT, UPDATE ON TABLE delivery TO Courier;
GRANT SELECT ON TABLE city,city_tax,staff,state TO Courier;
-- CompanyManager:
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sea_transportation TO CompanyManager;
GRANT SELECT, UPDATE ON TABLE delivery,container,ship,staff TO CompanyManager;
GRANT SELECT ON TABLE city,city_tax,company,staff_type,state TO CompanyManager;
-- SeaportOfficer:
GRANT SELECT, UPDATE ON TABLE delivery TO SeaportOfficer;
GRANT SELECT ON TABLE staff TO SeaportOfficer;