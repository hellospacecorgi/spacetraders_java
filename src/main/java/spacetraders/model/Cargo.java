package spacetraders.model;

public class Cargo {
    private String good;
    private Integer quantity;
    private Integer totalVolume;

    public Cargo(String good, Integer quantity, Integer totalVolume) {
        this.good = good;
        this.quantity = quantity;
        this.totalVolume = totalVolume;
    }

    public String getGood() {
        return good;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getTotalVolume() {
        return totalVolume;
    }




}
