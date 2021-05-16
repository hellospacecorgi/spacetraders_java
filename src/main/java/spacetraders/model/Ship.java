package spacetraders.model;

import java.util.HashMap;
import java.util.Map;

public class Ship {
    private String ship_class;
    private String manufacturer;
    private int maxCargo;
    private int plating;
    private Map<String, Integer> purchaseLocations = new HashMap<>();
    private int speed;
    private String type;
    private int weapons;

    public Ship(String ship_class, String manufacturer, int maxCargo, int plating, Map<String, Integer> locations, int speed, String type, int weapons){
        this.ship_class = ship_class;
        this.manufacturer = manufacturer;
        this.maxCargo = maxCargo;
        this.plating = plating;
        this.purchaseLocations = locations;
        this.speed = speed;
        this.type = type;
        this.weapons = weapons;
    }

    public String getShipClass() {
        return ship_class;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public int getPlating() {
        return plating;
    }

    public Map<String, Integer> getPurchaseLocations() {
        return purchaseLocations;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getWeapons() {
        return weapons;
    }



}
