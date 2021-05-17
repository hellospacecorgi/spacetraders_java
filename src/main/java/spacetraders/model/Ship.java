package spacetraders.model;

import java.util.HashMap;
import java.util.Map;

public class Ship {
    private String shipClass;
    private String manufacturer;
    private int maxCargo;
    private int plating;
    Map<String, Integer> locations;
    private double speed;
    private String shipType;
    private int weapons;

    public Ship(String ship_class, String manufacturer, int maxCargo, int plating, Map<String,Integer> locations, double speed, String type, int weapons){
        this.shipClass = ship_class;
        this.manufacturer = manufacturer;
        this.maxCargo = maxCargo;
        this.plating = plating;
        this.locations = locations;
        this.speed = speed;
        this.shipType = type;
        this.weapons = weapons;
    }

    public String getShipClass() {
        return shipClass;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getMaxCargo() {
        return Integer.valueOf(maxCargo);
    }

    public Integer getPlating() {
        return Integer.valueOf(plating);
    }

    public Map<String,Integer> getLocations() {
        return locations;
    }

    public Double getSpeed() {
        return Double.valueOf(speed);
    }

    public String getShipType() {
        return shipType;
    }

    public Integer getWeapons() {
        return Integer.valueOf(weapons);
    }



}
