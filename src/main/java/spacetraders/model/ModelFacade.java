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
    public ObservableList<Loan> getAccountLoans(String responseBody);
    public ObservableList<Ship> getAccountShips(String responseBody);

    //View available loans
    public ObservableList<Loan> viewAvailableLoans();
    public String takeOutLoan(String type);

    public ObservableList<Ship> viewShipsToPurchase();



}
