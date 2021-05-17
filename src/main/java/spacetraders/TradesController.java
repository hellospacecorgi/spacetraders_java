package spacetraders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import spacetraders.model.Cargo;
import spacetraders.model.ModelFacade;
import spacetraders.model.MyShip;
import spacetraders.model.TakenLoan;

import java.io.IOException;

public class TradesController {
    @FXML
    TextArea message;
    @FXML
    TextArea message1;

    @FXML
    Label mode_label;

    @FXML
    TableView ships_table;

    //for placing sell order
    @FXML
    ChoiceBox ship_selected;

    @FXML
    ChoiceBox good_selected;

    @FXML
    ChoiceBox quant_selected;

    String ship_id="";
    String good="";
    Integer quantity=0;

    //for loading cargo
    @FXML
    ChoiceBox ship_selected1;
    String shipId_carg = "";

    ModelFacade model;

    public TradesController(ModelFacade model){
        this.model = model;
    }

    public void initialize(){
        message.setText("Manual refresh needed.");
        message1.setText("<Load My Ships> to load ships for ShipID menu.\n"
        .concat("Select Ship ID before <Load Cargos>"));
        if(model.isOnline()){
            mode_label.setText("Running in online mode.");
        } else {
            mode_label.setText("Running in offline mode. Presenting dummy values.");
        }

        ship_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                ship_id = String.valueOf(t1);
                message.setText(ship_id.concat(" selected \n"));
                System.out.println(String.valueOf(t1).concat(" selected"));
            }
        });

        ship_selected1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                shipId_carg = String.valueOf(t1);
                message.setText(shipId_carg.concat(" selected for loading cargos\n"));
                System.out.println(String.valueOf(t1).concat(" selected for loading cargos"));
            }
        });

        good_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                good = String.valueOf((t1));
                message.setText(good.concat(" selected\n"));
            }
        });

        //populate quantity list
        quant_selected.getItems().clear();
        for(int i = 10; i < 100; i += 10){
            quant_selected.getItems().add(Integer.valueOf(i));
        }

        quant_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                quantity = Integer.valueOf((Integer) t1);
                message.setText(String.valueOf(quantity).concat(" selected"));
            }
        });

    }

    public void onLoadMyShipsTable() {
        String responseBody = model.viewAccount();
        message.setText(model.getAccountCredit(responseBody));

        ObservableList<MyShip> ships = model.getAccountShips();

        //populate drop down lists
        ship_selected1.getItems().clear();
        ship_selected.getItems().clear();
        for(int i = 0 ; i < ships.size(); i++){
            ship_selected1.getItems().add(ships.get(i).getId());
            ship_selected.getItems().add(ships.get(i).getId());
        }

        //Populate table
        TableColumn<MyShip, String> ship_id = new TableColumn<>("ID");
        ship_id.setCellValueFactory(new PropertyValueFactory<>("Id"));


        TableColumn<MyShip, String> shipClass = new TableColumn<>("Class");
        shipClass.setCellValueFactory(new PropertyValueFactory<>("shipClass"));

        TableColumn<MyShip, String> location = new TableColumn<>("Location");
        location.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<MyShip, String> manufacturer = new TableColumn<>("Manufacturer");
        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        TableColumn<MyShip, String> shipType = new TableColumn<>("Type");
        shipType.setCellValueFactory(new PropertyValueFactory<>("shipType"));

        TableColumn<MyShip, Integer> maxCargo = new TableColumn<>("Max Cargo");
        maxCargo.setCellValueFactory(new PropertyValueFactory<>("maxCargo"));

        TableColumn<MyShip, Integer> space = new TableColumn<>("Space Available");
        space.setCellValueFactory(new PropertyValueFactory<>("spaceAvailable"));

        TableColumn<MyShip, Integer> speed = new TableColumn<>("Speed");
        speed.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<MyShip, Integer> yPos = new TableColumn<>("Y");
        yPos.setCellValueFactory(new PropertyValueFactory<>("yPos"));

        TableColumn<MyShip, Integer> xPos = new TableColumn<>("X");
        xPos.setCellValueFactory(new PropertyValueFactory<>("xPos"));

        ships_table.getItems().clear();
        ships_table.getColumns().clear();
        ships_table.setItems(ships);
        ships_table.getColumns().addAll(ship_id, shipClass, location, manufacturer, shipType, maxCargo, space, speed, xPos, yPos);

    }

    public void onLoadCargoTable() {
        if(shipId_carg.equals("")){
            message.setText("Error - No ship selected for loading cargo");
            return;
        }
        String responseBody = model.viewAccount();
        message.setText(model.getAccountCredit(responseBody));

        ObservableList<MyShip> ships = model.getAccountShips();
        ObservableList<Cargo> cargos = FXCollections.observableArrayList();
        //find selected ship
        MyShip selected = null;
        for(int i = 0 ; i < ships.size(); i++){
            if(ships.get(i).getId().equals(shipId_carg)){
                selected = ships.get(i);
                break;
            }
        }

        if(selected == null){
            message.setText("Error - couldn't find ship");
            return;
        }

        good_selected.getItems().clear();
        for(int i = 0; i < selected.getCargoList().size(); i++){
            cargos.add(selected.getCargoList().get(i));
            good_selected.getItems().add(selected.getCargoList().get(i).getGood());
        }

        TableColumn<Cargo, String> cargo_good = new TableColumn<>("Good");
        cargo_good.setCellValueFactory(new PropertyValueFactory<>("good"));

        TableColumn<Cargo, Integer> cargo_quantity = new TableColumn<>("Quantity");
        cargo_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Cargo, Integer> cargo_vol = new TableColumn<>("Total Volume");
        cargo_vol.setCellValueFactory(new PropertyValueFactory<>("totalVolume"));

        ships_table.getItems().clear();
        ships_table.getColumns().clear();
        ships_table.setItems(cargos);
        ships_table.getColumns().addAll(cargo_good, cargo_quantity, cargo_vol);

    }

    public void onPlaceSellOrder(){
        if(ship_id.equals("") || good.equals("") || quantity==0){
            message.setText("Error in sell order - check if selected all fields (ShipID, Good, Quantity)?");
            return;
        }
        message.setText(ship_id.concat(" ").concat(good).concat(" ").concat(String.valueOf(quantity)));

        String result = model.placeSellOrder(ship_id, good, quantity);
        message.setText(result);
    }

    public void onViewInfo() throws IOException {
        ViewSwitcher.switchTo(View.INFO);
    }


    public void onToMain() throws IOException {
        ViewSwitcher.switchTo(View.MAIN);
    }

    public void onLogout() throws IOException {
        ViewSwitcher.switchTo(View.LOGIN);
    }




}
