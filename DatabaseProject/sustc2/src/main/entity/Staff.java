package main.entity;

import java.sql.Date;
import java.util.Objects;

public class Staff extends Entity {
    private Integer id;
    private String company;
    private String city;
    private String name;
    private String type;
    private String gender;
    private Date birth_year;
    private String phone_number;


    public Staff() {
    }

    public Staff(Integer id, String company, String city, String name, String type, String gender, Date birth_year, String phone_number) {
        this.id = id;
        this.company = company;
        this.city = city;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.birth_year = birth_year;
        this.phone_number = phone_number;
    }

    public Staff(String[] strings) {
        try {
            this.id = strings[0].equals("") ? null : Integer.valueOf(strings[0]);
            this.company = strings[1];
            this.name = strings[2];
            this.gender = strings[3];
            this.birth_year = strings[4].equals("") ? null : Date.valueOf(strings[4]);
            this.phone_number = strings[5];
            this.city = strings[6];
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Staff(String str) {
        new Container(str.split(","));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Date birth_year) {
        this.birth_year = birth_year;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return name.equals(staff.name) &&
                gender.equals(staff.gender) &&
                birth_year.equals(staff.birth_year) &&
                phone_number.equals(staff.phone_number);
        // 是否要加上 type == o.type ?
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,gender,birth_year,phone_number);
    }

    @Override
    public String toString() {
        return "courier{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birth_year=" + birth_year +
                ", phone_number='" + phone_number + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }
}