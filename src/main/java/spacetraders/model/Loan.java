package spacetraders.model;

public class Loan {
    private double amount;
    private boolean collateral;
    private double rate;
    private int termDays;
    private String type;

    public Loan(double amt, boolean col, double rate, int days, String type){
        this.amount = amt;
        this.collateral = col;
        this.rate = rate;
        this.termDays = days;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isCollateral() {
        return collateral;
    }

    public double getRate() {
        return rate;
    }

    public int getTermDays() {
        return termDays;
    }

    public String getType() {
        return type;
    }

}
