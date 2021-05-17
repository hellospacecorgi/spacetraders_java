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

    public ObservableList<TakenLoan> parseViewAccountLoans(String responseBody){
        ObservableList<TakenLoan> loans_list = FXCollections.observableArrayList();
        try{
            JSONArray loans = new JSONObject(responseBody).getJSONArray("loans");
            for(int i = 0 ; i < loans.length(); i++){
                JSONObject l = loans.getJSONObject(i);
                String duedate = l.getString("due");
                String id = l.getString("id");
                double repay = l.getDouble("repaymentAmount");
                String status = l.getString("status");
                String type = l.getString("type");
                loans_list.add(new TakenLoan(duedate, id, repay, status, type));
            }

            return loans_list;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<MyShip> parseViewAccountShips(String responseBody){
        ObservableList<MyShip> ships_list = FXCollections.observableArrayList();
        try{
            JSONArray ships = new JSONObject(responseBody).getJSONArray("ships");
            for(int i = 0 ; i < ships.length(); i++){
                List<Cargo> cargos = new ArrayList<>();
                JSONObject s = ships.getJSONObject(i);
                JSONArray cargos_array = s.getJSONArray("cargo");
                for(int j = 0 ; j < cargos_array.length() ; j++){
                    //each cargo
                    JSONObject c = cargos_array.getJSONObject(j);
                    cargos.add(new Cargo(c.getString("good"), c.getInt("quantity"), c.getInt("totalVolume")));
                }

                String shipclass = s.getString("class");
                String id = s.getString("id");
                String location = s.getString("location");
                String manufacturer = s.getString("manufacturer");
                Integer maxCargo = s.getInt("maxCargo");
                Integer plating = s.getInt("plating");
                Integer spaceAvail = s.getInt("spaceAvailable");
                Integer speed = s.getInt("speed");
                String shiptype = s.getString("type");
                Integer weapons = s.getInt("weapons");
                Integer xPos = s.getInt("x");
                Integer yPos = s.getInt("y");

                MyShip ship = new MyShip(cargos, shipclass, id, location, manufacturer, shiptype);
                ship.setyPos(yPos);
                ship.setxPos(xPos);
                ship.setPlating(plating);
                ship.setMaxCargo(maxCargo);
                ship.setSpeed(speed);
                ship.setSpaceAvailable(spaceAvail);
                ship.setWeapons(weapons);

                ships_list.add(ship);
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
                double speed = l.getInt("speed");
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
            String duedate = loan.getString("due");
            double repay = loan.getDouble("repaymentAmount");
            String response = "Took out ".concat(String.valueOf(credits)).concat("due ").concat(duedate).concat("repayment ").concat(String.valueOf(repay));
            return response;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "parseFailed";
    }
    public String parsePurchaseShip(String responseBody){
        try{
            System.out.println(responseBody);
            double credits = new JSONObject(responseBody).getDouble("credits");
            JSONObject ship = new JSONObject(responseBody).getJSONObject("loan");
            String ship_class = ship.getString("class");
            String ship_id = ship.getString("id");
            String response = "Purchased ".concat(ship_class).concat(" with id ").concat(ship_id).concat("\n").concat("Remaining ".concat(String.valueOf(credits)).concat(" credits"));
            return response;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "parseFailed";
    }

    public String parsePurchaseShipFuel(String responseBody){
        try{
            System.out.println(responseBody);
            JSONObject order = new JSONObject(responseBody).getJSONObject("order");
            String ship_id = new JSONObject(responseBody).getJSONObject("ship").getString("id");
            double credits = new JSONObject(responseBody).getDouble("credits");
            String response = "Purchased 20 units of ship fuel for".concat(ship_id).concat("\n").concat("Remaining ".concat(String.valueOf(credits)).concat(" credits"));
            return response;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "parseFailed";
    }

    public ObservableList<Good> parseMarketplace(String responseBody){
        ObservableList<Good> goods = FXCollections.observableArrayList();
        try{
            JSONArray marketplace = new JSONObject(responseBody).getJSONArray("marketplace");
            for(int i = 0 ; i < marketplace.length(); i++){
                JSONObject l = marketplace.getJSONObject(i);
                Double price = l.getDouble("pricePerUnit");
                Double purchase = l.getDouble("purchasePricePerUnit");
                Integer quant = l.getInt("quantityAvailable");
                Double sell = l.getDouble("sellPricePerUnit");
                Double spread = l.getDouble("spread");
                String sym = l.getString("symbol");
                Integer volume = l.getInt("volumePerUnit");

                goods.add(new Good(price, purchase, quant, sell, spread, sym, volume));
            }

            return goods;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public String parsePurchaseOrder(String responseBody){
        try{
            System.out.println(responseBody);
            JSONObject order = new JSONObject(responseBody).getJSONObject("order");
            int quantity = order.getInt("quantity");
            String good = order.getString("good");
            String ship_id = new JSONObject(responseBody).getJSONObject("ship").getString("id");
            double credits = new JSONObject(responseBody).getDouble("credits");
            String response = "Purchased ".concat(String.valueOf(quantity)).concat(" ").concat(good)
                    .concat(" for ship ").concat(ship_id).concat("\n")
                    .concat("Remaining ".concat(String.valueOf(credits)).concat(" credits"));
            return response;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "parseFailed";
    }

    public String parseErrorMessage(String responseBody){
        try{
            JSONObject error = new JSONObject(responseBody).getJSONObject("error");
            return error.getString("message");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public String parseSellOrder(String responseBody){
        try{
            System.out.println(responseBody);
            JSONObject order = new JSONObject(responseBody).getJSONObject("order");
            int quantity = order.getInt("quantity");
            String good = order.getString("good");
            double ppu = order.getDouble("pricePerUnit");
            double total = order.getDouble("total");
            double credits = new JSONObject(responseBody).getDouble("credits");
            String response = "Sold ".concat(String.valueOf(quantity)).concat("units ").concat(good)
                    .concat(" at price per unit ").concat(String.valueOf(ppu)).concat("\n")
                    .concat(" Total price sold: ").concat(String.valueOf(total))
                    .concat("Remaining ".concat(String.valueOf(credits)).concat(" credits"));
            return response;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "Sold";
    }

    public ObservableList<NearbyLocation> parseNearbyLocations(String responseBody){
        try{
            ObservableList<NearbyLocation> nearby = FXCollections.observableArrayList();
            System.out.println(responseBody);
            JSONArray list = new JSONObject(responseBody).getJSONArray("locations");
            for(int i = 0 ; i < list.length(); i++){
                JSONObject place = list.getJSONObject(i);
                Boolean allow = place.getBoolean("allowsConstruction");
                String name = place.getString("name");
                String symbol = place.getString("symbol");
                String loctype = place.getString("type");
                Double X = place.getDouble("x");
                Double Y = place.getDouble("y");

                nearby.add(new NearbyLocation(allow, name, symbol, loctype, X, Y));

            }
            return nearby;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public FlightPlan parseCreateFlightPlan(String responseBody){
        try{
            System.out.println(responseBody);
            JSONObject flight = new JSONObject(responseBody).getJSONObject("flightPlan");
            String arrival = flight.getString("arrivesAt");
            String depart = flight.getString("departure");
            String dest = flight.getString("destination");
            Double dist = Double.valueOf(flight.getDouble("distance"));
            Double cons = Double.valueOf(flight.getDouble("fuelConsumed"));
            Double remain = Double.valueOf(flight.getDouble("fuelRemaining"));
            String planID = flight.getString("id");
            String shipID = flight.getString("shipId");
            String termin = flight.getString("terminatedAt");
            Double time = Double.valueOf(flight.getDouble("timeRemainingInSeconds"));

            //ObservableList<FlightPlan> plans = FXCollections.observableArrayList();
            FlightPlan plan = new FlightPlan(arrival,depart,dest,dist,cons,remain,planID, shipID,termin, time);
            return plan;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<FlightPlan> parseViewFlightPlan(String responseBody){
        try{
            System.out.println(responseBody);
            JSONObject flight = new JSONObject(responseBody).getJSONObject("flightPlan");
            String arrival = flight.getString("arrivesAt");
            String depart = flight.getString("departure");
            String dest = flight.getString("destination");
            Double dist = Double.valueOf(flight.getDouble("distance"));
            Double cons = Double.valueOf(flight.getDouble("fuelConsumed"));
            Double remain = Double.valueOf(flight.getDouble("fuelRemaining"));
            String planID = flight.getString("id");
            String shipID = flight.getString("shipId");
            String termin = flight.getString("terminatedAt");
            Double time = Double.valueOf(flight.getDouble("timeRemainingInSeconds"));

            ObservableList<FlightPlan> plans = FXCollections.observableArrayList();
            FlightPlan plan = new FlightPlan(arrival,depart,dest,dist,cons,remain,planID, shipID,termin, time);
            plans.add(plan);
            return plans;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }


}
