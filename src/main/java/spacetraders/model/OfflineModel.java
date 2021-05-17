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
    List<MyShip> last_loaded_myships = new ArrayList<>();
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
    public ObservableList<TakenLoan> getAccountLoans() {
        ObservableList<TakenLoan> dummy_loans = FXCollections.observableArrayList();
        dummy_loans.add(new TakenLoan("2099-99-99","dummy-id", 9999999, "FOREVER" ,"DUMMY"));
        return dummy_loans;
    }


    public ObservableList<MyShip> getAccountShips() {
        ObservableList<MyShip> dummy_ships = FXCollections.observableArrayList();
        dummy_ships.add(new MyShip(null, "DUMMYCLASS", "dum-my-id", "BLACKHOLE","DUMMY MANUFACTURER", "DUM TYPE"));

        if(last_loaded_myships.size() == 0){
            last_loaded_myships.add(dummy_ships.get(0));
        }

        return dummy_ships;
    }

    public List<MyShip> getMyShips(){
        return last_loaded_myships;
    }

    public ObservableList<Loan> viewAvailableLoans(){
        ObservableList<Loan> dummy_loans = FXCollections.observableArrayList();
        dummy_loans.add(new Loan(9999,false,20,3,"DUMMY"));
        return dummy_loans;
    }

    public ObservableList<Ship> viewShipsToPurchase() {
        ObservableList<Ship> dummy_ships = FXCollections.observableArrayList();
        Map<String, Integer> dummy_loc = new HashMap<>();
        dummy_loc.put("DUMMY_LOC", 808080);
        dummy_ships.add(new Ship("DUMMY SHIP", "DUMMY MANUFACTURER", 1, 1, dummy_loc,11, "DUM TYPE", 3));
        return dummy_ships;
    }

    public String takeOutLoan(String type){
        return "Took out dummy loan 9999 credits.";
    }

    public String purchaseShip(String type, String location){
        return "Purchased dummy ship :o)";
    }

    public String purchaseShipFuel(String shipId, int quantity){
        return "Purchased dummy ships fuel :o";
    }

    public ObservableList<Good> viewMarketplace(String symbol){
        ObservableList<Good> goods = FXCollections.observableArrayList();
        goods.add(new Good(Double.valueOf(6789), Double.valueOf(2), Integer.valueOf(222),
                Double.valueOf(23), Double.valueOf(2), "DUM", Integer.valueOf(3)));
        goods.add(new Good(Double.valueOf(2345), Double.valueOf(1), Integer.valueOf(222),
                Double.valueOf(11), Double.valueOf(2), "DUM-II", Integer.valueOf(3)));
        return goods;
    }

    public String placePurchaseOrder(String shipId, String goodSymbol, Integer quantity){
        return "Purchased dummy order";
    }

    public String placeSellOrder(String shipId, String goodSymbol, Integer quantity){
        return "Sold dummy order";
    }

    public ObservableList<NearbyLocation> viewNearbyLocations(String system){
        ObservableList<NearbyLocation> nearby = FXCollections.observableArrayList();
        nearby.add(new NearbyLocation(false, "DUMMY", "DUM", "BLACKHOLE",
                Double.valueOf(-42), Double.valueOf(-42)));
        return nearby;
    }

    public FlightPlan createFlightPlan(String shipId, String destination){
        return new FlightPlan("Someday", "Somewhere","Nowhere",
                Double.valueOf(-42), Double.valueOf(1), Double.valueOf(1),
                "DUMMY", "DUMMY","SOMETIME", Double.valueOf(-42));
    }

    public ObservableList<FlightPlan> viewFlightPlan(String planID){
        ObservableList<FlightPlan> plans = FXCollections.observableArrayList();
        plans.add(new FlightPlan("Someday", "Somewhere","Nowhere",
                Double.valueOf(-42), Double.valueOf(1), Double.valueOf(1),
                "DUMMY", "DUMMY","SOMETIME", Double.valueOf(-42)));
        return plans;
    }



}
