package main.entity;

import java.util.Objects;

public class Company extends Entity {
    private String name;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(String[] strings) {
        try {
                name = strings[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
        Company company = (Company) o;
        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "company{" +
                "name='" + name + '\'' +
                '}';
    }
}
