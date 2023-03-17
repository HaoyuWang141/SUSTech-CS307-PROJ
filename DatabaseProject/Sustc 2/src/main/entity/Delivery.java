package main.entity;

import java.util.Objects;

public class Delivery extends Entity {

    private String item_name;
    private String item_type;
    private Double item_price;
    private String retrieval_city;
    private Integer retrieval_courier_id;
    private String export_city;
    private Integer export_officer_id;
    private String import_city;
    private Integer import_officer_id;
    private String delivery_city;
    private Integer delivery_courier_id;
    private String container_code;
    private String ship_name;
    private String company;
    private String state;


    public Delivery() {
    }

    public Delivery(String item_name, String item_type, Double item_price, String retrieval_city, Integer retrieval_courier_id, String export_city, Integer export_officer_id, String import_city, Integer import_officer_id, String delivery_city, Integer delivery_courier_id, String container_code, String ship_name, String company, String state) {
        this.item_name = item_name;
        this.item_type = item_type;
        this.item_price = item_price;
        this.retrieval_city = retrieval_city;
        this.retrieval_courier_id = retrieval_courier_id;
        this.export_city = export_city;
        this.export_officer_id = export_officer_id;
        this.import_city = import_city;
        this.import_officer_id = import_officer_id;
        this.delivery_city = delivery_city;
        this.delivery_courier_id = delivery_courier_id;
        this.container_code = container_code;
        this.ship_name = ship_name;
        this.company = company;
        this.state = state;
    }

    public Delivery(String[] strings){
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public void setRetrieval_city(String retrieval_city) {
        this.retrieval_city = retrieval_city;
    }

    public void setRetrieval_courier_id(Integer retrieval_courier_id) {
        this.retrieval_courier_id = retrieval_courier_id;
    }

    public void setExport_city(String export_city) {
        this.export_city = export_city;
    }

    public void setExport_officer_id(Integer export_officer_id) {
        this.export_officer_id = export_officer_id;
    }

    public void setImport_city(String import_city) {
        this.import_city = import_city;
    }

    public void setImport_officer_id(Integer import_officer_id) {
        this.import_officer_id = import_officer_id;
    }

    public void setDelivery_city(String delivery_city) {
        this.delivery_city = delivery_city;
    }

    public void setDelivery_courier_id(Integer delivery_courier_id) {
        this.delivery_courier_id = delivery_courier_id;
    }

    public void setContainer_code(String container_code) {
        this.container_code = container_code;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public Double getItem_price() {
        return item_price;
    }

    public String getRetrieval_city() {
        return retrieval_city;
    }

    public Integer getRetrieval_courier_id() {
        return retrieval_courier_id;
    }

    public String getExport_city() {
        return export_city;
    }

    public Integer getExport_officer_id() {
        return export_officer_id;
    }

    public String getImport_city() {
        return import_city;
    }

    public Integer getImport_officer_id() {
        return import_officer_id;
    }

    public String getDelivery_city() {
        return delivery_city;
    }

    public Integer getDelivery_courier_id() {
        return delivery_courier_id;
    }

    public String getContainer_code() {
        return container_code;
    }

    public String getShip_name() {
        return ship_name;
    }

    public String getCompany() {
        return company;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return item_name.equals(delivery.item_name) && item_type.equals(delivery.item_type) && item_price.equals(delivery.item_price) && retrieval_city.equals(delivery.retrieval_city) && retrieval_courier_id.equals(delivery.retrieval_courier_id) && export_city.equals(delivery.export_city) && Objects.equals(export_officer_id, delivery.export_officer_id) && import_city.equals(delivery.import_city) && Objects.equals(import_officer_id, delivery.import_officer_id) && delivery_city.equals(delivery.delivery_city) && Objects.equals(delivery_courier_id, delivery.delivery_courier_id) && Objects.equals(container_code, delivery.container_code) && Objects.equals(ship_name, delivery.ship_name) && company.equals(delivery.company) && state.equals(delivery.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_name, item_type, item_price, retrieval_city, retrieval_courier_id, export_city, export_officer_id, import_city, import_officer_id, delivery_city, delivery_courier_id, container_code, ship_name, company, state);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "item_name='" + item_name + '\'' +
                ", item_type='" + item_type + '\'' +
                ", item_price=" + item_price +
                ", retrieval_city='" + retrieval_city + '\'' +
                ", retrieval_courier_id=" + retrieval_courier_id +
                ", export_city='" + export_city + '\'' +
                ", export_officer_id=" + export_officer_id +
                ", import_city='" + import_city + '\'' +
                ", import_officer_id=" + import_officer_id +
                ", delivery_city='" + delivery_city + '\'' +
                ", delivery_courier_id=" + delivery_courier_id +
                ", container_code='" + container_code + '\'' +
                ", ship_name='" + ship_name + '\'' +
                ", company='" + company + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}