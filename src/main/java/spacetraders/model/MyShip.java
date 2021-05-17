package spacetraders.model;

import java.util.ArrayList;
import java.util.List;

public class MyShip {
    private List<Cargo> cargoList;
    private String shipClass;
    private String id;
    private String location;
    private String manufacturer;
    private Integer maxCargo = 0;
    private Integer plating = 0;
    private Integer spaceAvailable = 0;
    private Integer speed = 0;
    private String shipType;
    private Integer weapons;
    private Integer xPos = 0;
    private Integer yPos = 0;

    public MyShip(List<Cargo> cargoList, String shipClass, String id, String location, String manufacturer, String shipType) {
        this.cargoList = cargoList;
        this.shipClass = shipClass;
        this.id = id;
        this.location = location;
        this.manufacturer = manufacturer;
        this.shipType = shipType;
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }

    public String getShipClass() {
        return shipClass;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getMaxCargo() {
        return maxCargo;
    }

    public Integer getPlating() {
        return plating;
    }

    public Integer getSpaceAvailable() {
        return spaceAvailable;
    }

    public Integer getSpeed() {
        return speed;
    }

    public String getShipType() {
        return shipType;
    }

    public Integer getWeapons() {
        return weapons;
    }

    public Integer getXPos() {
        return xPos;
    }

    public Integer getYPos() {
        return yPos;
    }

    public void setMaxCargo(Integer maxCargo) {
        this.maxCargo = maxCargo;
    }

    public void setPlating(Integer plating) {
        this.plating = plating;
    }

    public void setSpaceAvailable(Integer spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setWeapons(Integer weapons) {
        this.weapons = weapons;
    }

    public void setxPos(Integer xPos) {
        this.xPos = xPos;
    }

    public void setyPos(Integer yPos) {
        this.yPos = yPos;
    }








}
