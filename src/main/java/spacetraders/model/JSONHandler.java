package spacetraders.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONHandler {
    public JSONHandler(){

    }
    public List<String> parseGenerateAccessToken(String responseBody){
        List<String> result = new ArrayList<>();
        try{
            String token = new JSONObject(responseBody).getString("token");
            String username = new JSONObject(responseBody).getJSONObject("user").getString("username");
            result.add(username);
            result.add(token);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    public String parseStatus(String responseBody){
        String status = "Parse";
        try{
            status = new JSONObject(responseBody).getString("status");

        } catch (JSONException e){
            e.printStackTrace();
        }
        return status;
    }

    public String parseViewAccountCredit(String responseBody){
        //Parse our JSON data (org.json in build.gradle)
        try{
            JSONObject account = new JSONObject(responseBody).getJSONObject("user");
            int credits = account.getInt("credits");
            String username = account.getString("username");
            System.out.println(username + " has " + credits + " credits.");
            return username.concat(" has ".concat(String.valueOf(credits)).concat(" credits."));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Loan> parseViewAccountLoans(String responseBody){
        ObservableList<Loan> loans_list = FXCollections.observableArrayList();
        try{
            JSONObject account = new JSONObject(responseBody).getJSONObject("user");
            JSONArray loans = account.getJSONArray("loans");
            for(int i = 0 ; i < loans.length(); i++){
                JSONObject l = loans.getJSONObject(i);
                double amt = l.getDouble("amount");
                boolean col = l.getBoolean("collateralRequired");
                double rate = l.getDouble("rate");
                int days = l.getInt("termInDays");
                String type = l.getString("type");
                loans_list.add(new Loan(amt, col, rate, days, type));
            }

            return loans_list;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Ship> parseViewAccountShips(String responseBody){
        ObservableList<Ship> ships_list = FXCollections.observableArrayList();
        try{
            JSONObject account = new JSONObject(responseBody).getJSONObject("user");
            JSONArray loans = account.getJSONArray("ships");
            for(int i = 0 ; i < loans.length(); i++){
                JSONObject l = loans.getJSONObject(i);
                String cls = l.getString("class");
                String manu = l.getString("manufacturer");
                int max = l.getInt("maxCargo");
                int plating = l.getInt("plating");
                int speed = l.getInt("speed");
                String type = l.getString("type");
                int weapons = l.getInt("weapons");
                JSONArray locations = l.getJSONArray("purchaseLocations");
                Map<String, Integer> loc = new HashMap<>();
                for(int j = 0 ; j < locations.length(); j ++){
                    JSONObject each_location = locations.getJSONObject(j);
                    loc.put(each_location.getString("location"), each_location.getInt("price"));
                }
                ships_list.add(new Ship(cls, manu, max, plating, loc, speed, type, weapons));
            }

            return ships_list;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Loan> parseAvailableLoans(String responseBody){
        ObservableList<Loan> loans_list = FXCollections.observableArrayList();
        try{
            JSONArray loans = new JSONObject(responseBody).getJSONArray("loans");
            for(int i = 0 ; i < loans.length(); i++){
                JSONObject l = loans.getJSONObject(i);
                double amt = l.getDouble("amount");
                boolean col = l.getBoolean("collateralRequired");
                double rate = l.getDouble("rate");
                int days = l.getInt("termInDays");
                String type = l.getString("type");
                loans_list.add(new Loan(amt, col, rate, days, type));
            }

            return loans_list;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Ship> parseShipsToPurchase(String responseBody){
        ObservableList<Ship> ships_list = FXCollections.observableArrayList();
        try{
            JSONArray loans = new JSONObject(responseBody).getJSONArray("ships");
            for(int i = 0 ; i < loans.length(); i++){
                JSONObject l = loans.getJSONObject(i);
                String cls = l.getString("class");
                String manu = l.getString("manufacturer");
                int max = l.getInt("maxCargo");
                int plating = l.getInt("plating");
                int speed = l.getInt("speed");
                String type = l.getString("type");
                int weapons = l.getInt("weapons");
                JSONArray locations = l.getJSONArray("purchaseLocations");
                Map<String, Integer> loc = new HashMap<>();
                for(int j = 0 ; j < locations.length(); j ++){
                    JSONObject each_location = locations.getJSONObject(j);
                    loc.put(each_location.getString("location"), each_location.getInt("price"));
                }
                ships_list.add(new Ship(cls, manu, max, plating, loc, speed, type, weapons));
            }

            return ships_list;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public String parseTakeOutLoan(String responseBody){
        try{
            double credits = new JSONObject(responseBody).getDouble("credits");
            JSONObject loan = new JSONObject(responseBody).getJSONObject("loan");



        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
