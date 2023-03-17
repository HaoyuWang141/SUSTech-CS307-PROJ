package main.entity;

import java.util.Objects;

public class Ship extends Entity {
    private String name;
    private String company;
    private Boolean sailing;

    public Ship() {
    }

    public Ship(String name, String company, Boolean sailing) {
        this.name = name;
        this.company = company;
        this.sailing = sailing;
    }

    public Ship(String[] strings) {
        try {
            this.name = strings[0];
            this.company = strings[1];
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Ship(String str) {
        new Ship(str.split(","));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship1 = (Ship) o;
        return name.equals(ship1.name) && company.equals(ship1.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company);
    }

    @Override
    public String toString() {
        return "ship{" +
                "ship='" + name + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    public boolean getSailing() {
        return sailing;
    }
}
