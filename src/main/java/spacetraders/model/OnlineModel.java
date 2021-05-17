package spacetraders.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineModel implements ModelFacade{
    private HttpClient client;
    JSONHandler handler;
    String username = "online-user";
    String token = "online-token";
    Map<String, String> saved_accounts = new HashMap<>();
    List<MyShip> last_loaded_myships = new ArrayList<>();

    String system = "OE";

    public OnlineModel(){
        handler = new JSONHandler();
        client = HttpClient.newHttpClient();
    }

    public boolean isOnline(){
        return true;
    }

    public List<String> createAccount(String username) {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/users/"+username+"/claim"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body().concat(" ").concat(String.valueOf(response.statusCode())));
            List<String> result = handler.parseGenerateAccessToken(response.body());
            if(result == null){
                return null;
            }
            return result;
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean authenticate(String username, String token){
        if(token.equals("")){
            return false;
        }
        //see if valid username-token pair by viewing user account
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/my/account"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                return true;
            }
            System.out.println(response.body());
            System.out.println(response.statusCode());
            return false;

            /*
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(handler::parseViewAccount)
                    .join();

             */

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
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

    public String viewAccount(){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/my/account"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getAccountCredit(String responseBody){
       return handler.parseViewAccountCredit(responseBody);
    }
    public double getRemainingCredits(String responseBody){
        return handler.parseAccountCredits(responseBody);
    }
    public ObservableList<TakenLoan> getAccountLoans(){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/my/loans"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObservableList<TakenLoan> loans = handler.parseViewAccountLoans(response.body());
            return loans;

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;

    }

    public ObservableList<MyShip> getAccountShips(){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/my/ships"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObservableList<MyShip> ships = handler.parseViewAccountShips(response.body());

            last_loaded_myships.clear();
            for(int i = 0 ; i < ships.size() ; i++){
                last_loaded_myships.add(ships.get(i));
            }

            return ships;

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<MyShip> getMyShips(){
            return last_loaded_myships;
    }

    public String getToken(){
        return this.token;
    }

    public String getServerStatus(){
        String status = "No response";
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/game/status"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            status = response.body();
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        status = handler.parseStatus(status);
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

    public ObservableList<Loan> viewAvailableLoans(){

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/types/loans"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("View available loans: ".concat(String.valueOf(response.statusCode())));
            System.out.println(response.body());
            ObservableList<Loan> avail_loans = handler.parseAvailableLoans(response.body());
            return avail_loans;

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Ship> viewShipsToPurchase() {
        try{
            /*
            //New endpoint after 15th may - but no locations :/
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/types/ships"))
                    .build();
             */

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/game/ships"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObservableList<Ship> ships = handler.parseShipsToPurchase(response.body());
            return ships;


        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public String takeOutLoan(String type){
        try{

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .setHeader("Authorization", "Bearer ".concat(token))
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/my/loans".concat("?type=").concat(type)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                return handler.parseTakeOutLoan(response.body());
            }
            return "STATUS CODE ".concat(String.valueOf(response.statusCode()));
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return "failed";
    }

    public String purchaseShip(String type, String location){
        try{

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .setHeader("Authorization", "Bearer ".concat(token))
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/my/ships".concat("?type=").concat(type).concat("&location=").concat(location)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                return handler.parsePurchaseShip(response.body());
            }
            System.out.println(response.body());
            return "STATUS CODE ".concat(String.valueOf(response.statusCode())).concat(response.body());
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return "failed";
    }

    public String purchaseShipFuel(String shipId, int quantity){
        try{

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .setHeader("Authorization", "Bearer ".concat(token))
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/my/purchase-orders".concat("?shipId=").concat(shipId).concat("&good=FUEL").concat("&quantity=").concat(String.valueOf(quantity))))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                return handler.parsePurchaseShipFuel(response.body());
            }
            System.out.println(response.body());
            return "STATUS CODE ".concat(String.valueOf(response.statusCode())).concat(response.body());
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return "failed";
    }

    public ObservableList<Good> viewMarketplace(String symbol){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                .uri(URI.create("https://api.spacetraders.io/locations/".concat(symbol).concat("/marketplace")))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(String.valueOf(response.statusCode()) + response.body());

            if(response.statusCode() < 400){
                ObservableList<Good> goods = handler.parseMarketplace(response.body());

                return goods;
            }

            return null;

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public String placePurchaseOrder(String shipId, String goodSymbol, Integer quantity){
        try{

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .setHeader("Authorization", "Bearer ".concat(token))
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/my/purchase-orders".concat("?shipId=").concat(shipId).concat("&good=").concat(goodSymbol).concat("&quantity=").concat(String.valueOf(quantity))))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                return handler.parsePurchaseOrder(response.body());
            }
            System.out.println(response.body());
            return "STATUS CODE ".concat(String.valueOf(response.statusCode())).concat(response.body());
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public String placeSellOrder(String shipId, String goodSymbol, Integer quantity){
        try{

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .setHeader("Authorization", "Bearer ".concat(token))
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/my/sell-orders".concat("?shipId=").concat(shipId).concat("&good=").concat(goodSymbol).concat("&quantity=").concat(String.valueOf(quantity))))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                return handler.parseSellOrder(response.body());
            }
            System.out.println(response.body());
            return "STATUS CODE ".concat(String.valueOf(response.statusCode())).concat(handler.parseErrorMessage(response.body()));
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<NearbyLocation> viewNearbyLocations(String system){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/systems/".concat(system).concat("/locations")))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(String.valueOf(response.statusCode()) + response.body());
            ObservableList<NearbyLocation> nearby = handler.parseNearbyLocations(response.body());

            return nearby;
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public FlightPlan createFlightPlan(String shipId, String destination){
        try{

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .setHeader("Authorization", "Bearer ".concat(token))
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/my/flight-plans".concat("?shipId=").concat(shipId).concat("&destination=").concat(destination)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                return handler.parseCreateFlightPlan(response.body());
            }
            System.out.println(response.body());
            return null;
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<FlightPlan> viewFlightPlan(String planID){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/my/flight-plans/".concat(planID)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(String.valueOf(response.statusCode()) + response.body());
            ObservableList<FlightPlan> plans = handler.parseViewFlightPlan(response.body());

            return plans;
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public String payOffLoan(String loanID){
        try{

            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .setHeader("Authorization", "Bearer ".concat(token))
                    .header("accept", "application/json")
                    .uri(URI.create("https://api.spacetraders.io/my/loans/".concat(loanID)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                return handler.parsePayOffLoan(response.body());
            }
            return handler.parseErrorMessage(response.body());
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

}
