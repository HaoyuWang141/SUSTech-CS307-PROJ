package main.entity;

import java.util.Objects;

public class City extends Entity {
    private String name;

    public City() {
    }

    public City(String name, String phone_code) {
        this.name = name;
    }

    public City(String[] strings) {
        try {
            name = strings[0];
            if (strings.length >= 2) {
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public City(String str) {
        new City(str.split(","));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "city{" +
                "name='" + name + '\'' +
                '}';
    }
}
