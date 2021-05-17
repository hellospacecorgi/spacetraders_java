package spacetraders.model;

public class TakenLoan {
    private String dueDate;
    private String id;
    private double repay;
    private String status;
    private String loanType;

    public TakenLoan(String due_date, String id, double repay, String status, String loanType) {
        this.dueDate = due_date;
        this.id = id;
        this.repay = repay;
        this.status = status;
        this.loanType = loanType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getId() {
        return id;
    }

    public Double getRepay() {
        return Double.valueOf(repay);
    }

    public String getStatus() {
        return status;
    }

    public String getLoanType() {
        return loanType;
    }








}
