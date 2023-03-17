package main.entity;

import java.util.Objects;

public class CityTax extends Entity {
    private String city;
    private String item_type;
    private Double import_tax_rate;
    private Double export_tax_rate;

    public CityTax() {
    }

    public CityTax(String city, String item_type, double import_tax_rate, double export_tax_rate) {
        this.city = city;
        this.item_type = item_type;
        this.import_tax_rate = import_tax_rate;
        this.export_tax_rate = export_tax_rate;
    }

    public CityTax(String[] strings) {
        try {
            this.city = strings[0];
            this.item_type = strings[1];
            this.import_tax_rate = strings[2].equals("") ? null : Double.valueOf(strings[2]);
            if (strings.length >= 4)
                this.export_tax_rate = strings[3].equals("") ? null : Double.valueOf(strings[3]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public CityTax(String str) {
        new CityTax(str.split(","));
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public double getImport_tax_rate() {
        return import_tax_rate;
    }

    public void setImport_tax_rate(double import_tax_rate) {
        this.import_tax_rate = import_tax_rate;
    }

    public double getExport_tax_rate() {
        return export_tax_rate;
    }

    public void setExport_tax_rate(double export_tax_rate) {
        this.export_tax_rate = export_tax_rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityTax city_tax = (CityTax) o;
        if (city.equals(city_tax.city) && item_type.equals(city_tax.item_type)) {
            if (this.export_tax_rate == null && this.import_tax_rate != null &&
                    city_tax.export_tax_rate != null && city_tax.import_tax_rate == null) {
                this.export_tax_rate = city_tax.export_tax_rate;
                city_tax.import_tax_rate = this.import_tax_rate;
            } else if (this.export_tax_rate != null && this.import_tax_rate == null &&
                    city_tax.export_tax_rate == null && city_tax.import_tax_rate != null) {
                city_tax.export_tax_rate = this.export_tax_rate;
                this.import_tax_rate = city_tax.import_tax_rate;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, item_type, import_tax_rate, export_tax_rate);
    }

    @Override
    public String toString() {
        return city + "," + item_type + "," +
                (import_tax_rate == null ? "" : String.format("%.2f", import_tax_rate)) + "," +
                (export_tax_rate == null ? "" : String.format("%.2f", export_tax_rate));
    }
}