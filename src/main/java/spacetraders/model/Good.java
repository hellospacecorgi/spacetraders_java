package spacetraders.model;

public class Good {
    private Double pricePerUnit;
    private Double purchasePricePerUnit;
    private Integer quantity;
    private Double sellPricePerUnit;
    private Double spread;
    private String symbol;
    private Integer volumePerUnit;

    public Good(Double pricePerUnit, Double purchasePricePerUnit, Integer quantity, Double sellPricePerUnit, Double spread, String symbol, Integer volumePerUnit) {
        this.pricePerUnit = pricePerUnit;
        this.purchasePricePerUnit = purchasePricePerUnit;
        this.quantity = quantity;
        this.sellPricePerUnit = sellPricePerUnit;
        this.spread = spread;
        this.symbol = symbol;
        this.volumePerUnit = volumePerUnit;
    }



    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public Double getPurchasePricePerUnit() {
        return purchasePricePerUnit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSellPricePerUnit() {
        return sellPricePerUnit;
    }

    public Double getSpread() {
        return spread;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getVolumePerUnit() {
        return volumePerUnit;
    }



}
