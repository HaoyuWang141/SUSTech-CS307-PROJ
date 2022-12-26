package main.entity;

public class SeaTransportation extends Entity {

    private String item_name;
    private String container_code;
    private String ship_name;
    private String company;

    public SeaTransportation(String item_name, String container_code, String ship_name, String company) {
        this.item_name = item_name;
        this.container_code = container_code;
        this.ship_name = ship_name;
        this.company = company;
    }

    public String getShip_name() {
        return ship_name;
    }
}
