package main.entity;

public class Log extends Entity{
    Integer staff_id;
    String password;

    public Log(Integer staff_id, String password) {
        this.staff_id = staff_id;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
