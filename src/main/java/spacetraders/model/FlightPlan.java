package spacetraders.model;

public class FlightPlan {
    private String arrivalTime;
    private String departureLocation;
    private String destinationLocation;
    private Double distance;
    private Double fuelConsumed;
    private Double fuelRemaining;
    private String planId;
    private String shipId;
    private String terminated;
    private Double timeRemainingSec;

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getFuelConsumed() {
        return fuelConsumed;
    }

    public Double getFuelRemaining() {
        return fuelRemaining;
    }

    public String getPlanId() {
        return planId;
    }

    public String getShipId() {
        return shipId;
    }

    public String getTerminated() {
        return terminated;
    }

    public Double getTimeRemainingSec() {
        return timeRemainingSec;
    }



    public FlightPlan(String arrivalTime, String departureLocation, String destinationLocation, Double distance, Double fuelConsumed, Double fuelRemaining, String planId, String shipId, String terminated, Double timeRemainingSec) {
        this.arrivalTime = arrivalTime;
        this.departureLocation = departureLocation;
        this.destinationLocation = destinationLocation;
        this.distance = distance;
        this.fuelConsumed = fuelConsumed;
        this.fuelRemaining = fuelRemaining;
        this.planId = planId;
        this.shipId = shipId;
        this.terminated = terminated;
        this.timeRemainingSec = timeRemainingSec;
    }



}
