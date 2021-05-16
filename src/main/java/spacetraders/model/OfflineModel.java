package spacetraders.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfflineModel implements ModelFacade{
    String username = "offline-user";
    String token = "offline-token";

    Map<String, String> saved_accounts = new HashMap<>();

    public OfflineModel(){

    }

    public boolean isOnline(){
        return false;
    }

    public List<String> createAccount(String username){
        this.username = username;
        List<String> dummy = new ArrayList<>();
        dummy.add(username);
        dummy.add("token");
        return dummy;
    }

    public boolean authenticate(String username, String token){
        if(username.equals("username") && token.equals("token")){
            return true;
        }

        return false;
    }

    public void setInfo(String name, String token){
        this.username = name;
        this.token = token;
    }

    public String getInfo(){
        String info = username.concat(" ").concat("token: ").concat(token);
        return info;
    }

    public String getToken(){
        return this.token;
    }

    public String getServerStatus(){
        String status = "Not connected to server. (Offline)";
        return status;
    }

    public Map<String, String> getSavedAccounts(){
        return this.saved_accounts;
    }

    public boolean saveAccount(String username, String token){
        if(this.saved_accounts.containsKey(username)){
            return false;
        }
        this.saved_accounts.put(username, token);
        return true;
    }


    @Override
    public String viewAccount() {
        return "Some dummy string";
    }

    @Override
    public String getAccountCredit(String responseBody) {
        return this.username.concat(" has ").concat("12345 (dummy) credits.");
    }

    @Override
    public ObservableList<Loan> getAccountLoans(String responseBody) {
        ObservableList<Loan> dummy_loans = FXCollections.observableArrayList();
        dummy_loans.add(new Loan(1000,false,20,3,"DUMMY"));
        return dummy_loans;
    }

    @Override
    public ObservableList<Ship> getAccountShips(String responseBody) {
        ObservableList<Ship> dummy_ships = FXCollections.observableArrayList();
        Map<String, Integer> dummy_locations = new HashMap<>();
        dummy_locations.put("somewhere", 99999);
        dummy_ships.add(new Ship("DUMMY SHIP", "DUMMY MANUFACTURER", 1, 1, dummy_locations, 11, "DUM TYPE", 3));
        return dummy_ships;
    }

    public ObservableList<Loan> viewAvailableLoans(){
        ObservableList<Loan> dummy_loans = FXCollections.observableArrayList();
        dummy_loans.add(new Loan(9999,false,20,3,"DUMMY"));
        return dummy_loans;
    }

    public ObservableList<Ship> viewShipsToPurchase() {
        ObservableList<Ship> dummy_ships = FXCollections.observableArrayList();
        Map<String, Integer> dummy_locations = new HashMap<>();
        dummy_locations.put("somewhere", 99999);
        dummy_ships.add(new Ship("DUMMY SHIP", "DUMMY MANUFACTURER", 1, 1, dummy_locations, 11, "DUM TYPE", 3));
        return dummy_ships;
    }

    public String takeOutLoan(String type){
        return "Took out dummy loan 9999 credits.";
    }


}
