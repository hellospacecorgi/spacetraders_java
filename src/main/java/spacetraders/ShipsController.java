package spacetraders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import spacetraders.model.ModelFacade;
import spacetraders.model.MyShip;
import spacetraders.model.Ship;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShipsController {
    @FXML
    TextArea message;

    @FXML
    TextArea message1;

    @FXML
    Label mode_label;

    ModelFacade model;

    String ship_type = "";
    String ship_location = "";

    ObservableList<Ship> ships;

    @FXML
    ChoiceBox ship_selected;

    @FXML
    ChoiceBox location_selected;

    @FXML
    ChoiceBox ships_id;

    String id_selected;

    @FXML
    TableView ships_table;

    public ShipsController(ModelFacade model) {
        this.model = model;
    }

    public void initialize(){
        message.setText("Manual refresh required to view ships.");
        message1.setText("<View Ships> to load types in list\n"
        .concat("Select type to load purchase locations in list\n")
        .concat("<Load My Ships> to load ShipIDs in list\n")
        .concat("Select ShipID before purchasing fuel\n"));

        if(model.isOnline()){
            mode_label.setText("Running in online mode.");
        } else {
            mode_label.setText("Running in offline mode. Presenting dummy values.");
        }

        ship_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                ship_type = String.valueOf(t1);

                location_selected.getItems().clear();

                for(int i = 0; i < ships.size(); i++){
                    //find the type selected
                    if(ships.get(i).getShipType().equals(ship_type)) {
                        //go through the price location list for that type
                        Set<String> locations = ships.get(i).getLocations().keySet();
                        for(String l : locations){
                            location_selected.getItems().add(l);
                        }

                    }
                }

                message.setText(String.valueOf(t1).concat(" selected \n"));
                System.out.println(String.valueOf(t1).concat(" selected"));
            }
        });

        location_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                ship_location = String.valueOf(t1);
                String msg = "Ship type: ".concat(ship_type);
                msg.concat("\n Purchase location: ").concat(ship_location);

                Integer price = 0;
                //get price at location chosen
                for(int i = 0; i < ships.size(); i++){
                    //find the type selected
                    if(ships.get(i).getShipType().equals(ship_type)) {
                        //go through the price location list for that type
                        Map<String, Integer> locations = ships.get(i).getLocations();
                        price = locations.get(ship_location);
                    }
                }

                message.setText("Ship type: ".concat(ship_type).concat("Location: ").concat(ship_location).concat(" Price: ").concat(String.valueOf(price)));

                System.out.println(String.valueOf(t1).concat(" selected, price ").concat(String.valueOf(price)));
            }
        });

        ships_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                id_selected = String.valueOf(t1);
                message.setText("Selected ship with id ".concat(id_selected).concat("for fuel purchase."));
            }
        });
    }

    public void onToMain() throws IOException {
        ViewSwitcher.switchTo(View.MAIN);
    }

    public void onLogout() throws IOException {
        ViewSwitcher.switchTo(View.LOGIN);
    }

    public void onViewInfo() throws IOException {
        ViewSwitcher.switchTo(View.INFO);
    }

    public void onViewShips(){
        ships = model.viewShipsToPurchase();
        TableColumn<Ship, String> ship_class = new TableColumn<>("Class");
        ship_class.setCellValueFactory(new PropertyValueFactory<>("shipClass"));

        TableColumn<Ship, String> manu = new TableColumn<>("Manufacturer");
        manu.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        TableColumn<Ship, Integer> max = new TableColumn<>("Max Cargo");
        max.setCellValueFactory(new PropertyValueFactory<>("maxCargo"));

        TableColumn<Ship, Integer> plating = new TableColumn<>("Plating");
        plating.setCellValueFactory(new PropertyValueFactory<>("plating"));

        TableColumn<Ship, Double> speed = new TableColumn<>("Speed");
        speed.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<Ship, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("shipType"));

        TableColumn<Ship, Integer> weapons = new TableColumn<>("Weapons");
        weapons.setCellValueFactory(new PropertyValueFactory<>("weapons"));

        ships_table.setItems(ships);
        ships_table.getColumns().addAll(ship_class, manu, max, plating, speed, type, weapons);

        //populate choice box
        for(int i = 0 ; i < ships.size(); i++){
            ship_selected.getItems().add(ships.get(i).getShipType());
        }
    }

    public void onPurchaseShip() throws InterruptedException {
        if(ship_type.equals("") || ship_location.equals("")) {
            message.setText("No ships or location selected - cannot create order :/");
        } else {
            //send request to purchase ship
            String result = model.purchaseShip(ship_type, ship_location);
            message.setText(result);
        }
        //wait 2 secs (Josh says dont do this ???)
        Thread.sleep(2000);
        model.getAccountShips();
        List<MyShip> ships = model.getMyShips();
        ships_id.getItems().clear();
        for(int i = 0 ; i < ships.size(); i ++){
            ships_id.getItems().add(ships.get(i));
        }
    }

    public void onLoadMyShips(){
        model.getAccountShips();
        List<MyShip> myships = model.getMyShips();
        if(myships.size() > 0){
            for(int i = 0 ; i < myships.size(); i++){
                ships_id.getItems().add(myships.get(i).getId());
            }
        }
        message.setText("Loaded my ships in drop down list");
    }

    public void onPurchaseShipFuel(){
        if(id_selected.equals("")){
            message.setText("No ship id selected - cannot purchase fuel");
        }
        //default 20 units
        String result = model.purchaseShipFuel(id_selected, 20);
        if(result != null){
            message.setText(result);
            return;
        }
        message.setText("Error processing order, id selected?");


    }
}
