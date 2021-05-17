package spacetraders.model;

public class Loan {
    private double amount;
    private boolean collateral;
    private double rate;
    private int termDays;
    private String loanType;

    public Loan(double amt, boolean col, double rate, int days, String type){
        this.amount = amt;
        this.collateral = col;
        this.rate = rate;
        this.termDays = days;
        this.loanType = type;
    }

    public Double getAmount() {
        return Double.valueOf(amount);
    }

    public Boolean isCollateral() {
        return Boolean.valueOf(collateral);
    }

    public Double getRate() {
        return Double.valueOf(rate);
    }

    public Integer getTermDays() {
        return Integer.valueOf(termDays);
    }

    public String getLoanType() {
        return loanType;
    }

}
