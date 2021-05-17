package spacetraders.model;

public class NearbyLocation {
    private Boolean allow;
    private String name;
    private String symbol;
    private String locationType;
    private Double XPos;
    private Double YPos;

    public NearbyLocation(Boolean allow, String name, String symbol, String locationType, Double XPos, Double YPos) {
        this.allow = allow; //allowsConstruction
        this.name = name;
        this.symbol = symbol;
        this.locationType = locationType;
        this.XPos = XPos;
        this.YPos = YPos;
    }

    public Boolean getAllow(){
        return allow;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLocationType() {
        return locationType;
    }

    public Double getXPos() {
        return XPos;
    }

    public Double getYPos() {
        return YPos;
    }




}
