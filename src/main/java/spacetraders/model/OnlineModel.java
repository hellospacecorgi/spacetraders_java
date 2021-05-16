package spacetraders.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineModel implements ModelFacade{
    private HttpClient client;
    JSONHandler handler;
    String username = "online-user";
    String token = "online-token";
    Map<String, String> saved_accounts = new HashMap<>();

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

    public ObservableList<Loan> getAccountLoans(String responseBody){
        ObservableList<Loan> loans = handler.parseViewAccountLoans(responseBody);
        return loans;

    }

    public ObservableList<Ship> getAccountShips(String responseBody){
        return handler.parseViewAccountShips(responseBody);
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
            ObservableList<Loan> avail_loans = handler.parseAvailableLoans(response.body());
            return avail_loans;

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Ship> viewShipsToPurchase() {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json", "Authorization", "Bearer ".concat(token))
                    .uri(URI.create("https://api.spacetraders.io/types/ships"))
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
            if(response.statusCode() == 200){
                return handler.parseTakeOutLoan(response.body());
            }
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return "failed";
    }

}
