package main.entity;

import java.util.Objects;

public class Container extends Entity {
    private String code;
    private String type;
    private Boolean using;

    public Container() {
    }

    public Container(String code, String type, Boolean using) {
        this.code = code;
        this.type = type;
        this.using = using;
    }

    public Container(String[] strings) {
        try {
            this.code = strings[0];
            this.type = strings[1];
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Container(String str) {
        new Container(str.split(","));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Container container = (Container) o;
        return code.equals(container.code) && type.equals(container.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, type);
    }

    @Override
    public String toString() {
        return "container{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public boolean getUsing() {
        return using;
    }
}
