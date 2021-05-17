package spacetraders.model;

import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public interface ModelFacade {
    //Login
    public List<String> createAccount(String username);
    public boolean authenticate(String username, String token);
    public void setInfo(String username, String token);
    public String getInfo();

    //Allow to save credentials on login page (does not check whether valid or not)
    public Map<String, String> getSavedAccounts();
    public boolean saveAccount(String username, String token);

    public String getServerStatus();
    public boolean isOnline();
    public String getToken();

    //View account credit, loans and ships
    public String viewAccount();
    public String getAccountCredit(String responseBody);
    public double getRemainingCredits(String responseBody);
    public ObservableList<TakenLoan> getAccountLoans();
    public ObservableList<MyShip> getAccountShips();

    //View loans
    public ObservableList<Loan> viewAvailableLoans();
    public String takeOutLoan(String type);
    public String payOffLoan(String loanID);

    //View ships, purchase ship fuel
    public ObservableList<Ship> viewShipsToPurchase();
    public String purchaseShip(String type, String location);
    public List<MyShip> getMyShips();
    public String purchaseShipFuel(String shipId, int quantity);


    //View marketplace and sell goods
    public ObservableList<Good> viewMarketplace(String symbol);
    public String placePurchaseOrder(String shipId, String goodSymbol, Integer quantity);
    public String placeSellOrder(String shipId, String goodSymbol, Integer quantity);

    //Flight - view nearby locations, create and view flight plans
    public ObservableList<NearbyLocation> viewNearbyLocations(String system);
    public FlightPlan createFlightPlan(String shipId, String destination);
    public ObservableList<FlightPlan> viewFlightPlan(String planID);



}
