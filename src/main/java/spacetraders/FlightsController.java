package spacetraders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import spacetraders.model.*;

import java.io.IOException;
import java.util.List;

public class FlightsController {
    @FXML
    TextArea message;

    @FXML
    TextArea message1; // instructions

    @FXML
    Label mode_label;

    ModelFacade model;


    String system = "";
    @FXML
    ChoiceBox location_list;

    @FXML
    ChoiceBox ship_selected;

    @FXML
    ChoiceBox dest_selected;

    //param for flight plan
    String ship_id = "";
    String dest = "";

    @FXML
    ChoiceBox plan_selected;
    String plan_id = "";

    //for loading cargo
    @FXML
    ChoiceBox ship_selected1;
    String shipId_carg = "";

    @FXML
    TableView flights_table;

    public FlightsController(ModelFacade model) {
        this.model = model;
    }

    public void initialize(){
        message.setText("Manual refresh required to view ships.");
        message1.setText("Select System before <View Nearby Planets>\n"
                .concat("Viewing planets required for loading destinations\n")
                .concat("<Load My Ships> to load ShipID list\n")
                .concat("Select Ship ID before <Load Cargos>\n"));

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

        dest_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                dest = String.valueOf((t1));
                message.setText(dest.concat(" selected\n"));
            }
        });

        plan_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                plan_id = String.valueOf((t1));
                message.setText(plan_id.concat(" selected\n"));
            }
        });

        location_list.getItems().add("OE");
        location_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                system = String.valueOf(t1);
                message.setText(system.concat(" system selected"));
            }
        });
    }

    public void onViewNearbyPlanets(){
        if(system.equals("")){
            message.setText("Empty system - cannot load nearby planets");
            return;
        }
        ObservableList<NearbyLocation> nearby = model.viewNearbyLocations(system);

        //Populate dest
        dest_selected.getItems().clear();
        for(int i = 0 ; i < nearby.size(); i++){
            dest_selected.getItems().add(nearby.get(i).getSymbol());
        }

        //Populate table
        TableColumn<NearbyLocation, Boolean> allow = new TableColumn<>("Allow Construction");
        allow.setCellValueFactory(new PropertyValueFactory<>("allow"));

        TableColumn<NearbyLocation, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<NearbyLocation, String> sym = new TableColumn<>("Symbol");
        sym.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<NearbyLocation, String> locType = new TableColumn<>("Type");
        locType.setCellValueFactory(new PropertyValueFactory<>("locationType"));

        TableColumn<NearbyLocation, Double> x = new TableColumn<>("X Position");
        x.setCellValueFactory(new PropertyValueFactory<>("XPos"));

        TableColumn<NearbyLocation, Double> y = new TableColumn<>("Y Position");
        y.setCellValueFactory(new PropertyValueFactory<>("YPos"));

        flights_table.getItems().clear();
        flights_table.getColumns().clear();
        flights_table.setItems(nearby);
        flights_table.getColumns().addAll(allow, name, sym, locType, x, y);

    }

    public void onCreateFlightPlan(){
        if(ship_id.equals("") || dest.equals("")){
            message.setText("Error creating flight plan - check if all fields (ShipID, destination) selected?");
            return;
        }

        FlightPlan created = model.createFlightPlan(ship_id, dest);
        if(created != null){
            String id = created.getPlanId();
            //save to plan_selected list
            plan_selected.getItems().add(id);

            //Generate meaningful display message
            String depart = created.getDepartureLocation();
            String dest = created.getDestinationLocation();
            Double time = created.getTimeRemainingSec();
            String result = "Flight plan created - \n".concat("Plan ID ").concat(id)
                    .concat("\nDeparture: ").concat(depart)
                    .concat("\nDestination: ").concat(dest)
                    .concat("\nTime remaining: ").concat(String.valueOf(time))
                    .concat(" seconds.");
            message.setText(result);

            return;
        }
        message.setText("Error creating flight plan");

    }

    public void onViewFlightPlan(){
        if(plan_id.equals("")){
            message.setText("Can't get flight plan - No plan ID selected");
            return;
        }
        ObservableList<FlightPlan> plans = model.viewFlightPlan(plan_id);

        TableColumn<FlightPlan, String> planID = new TableColumn<>("ID");
        planID.setCellValueFactory(new PropertyValueFactory<>("planId"));

        TableColumn<FlightPlan, String> shipID = new TableColumn<>("Ship ID");
        shipID.setCellValueFactory(new PropertyValueFactory<>("shipId"));

        TableColumn<FlightPlan, String> arr = new TableColumn<>("Arrival Time");
        arr.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<FlightPlan, String> dep = new TableColumn<>("Departure");
        dep.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));

        TableColumn<FlightPlan, String> dest = new TableColumn<>("Destination");
        dest.setCellValueFactory(new PropertyValueFactory<>("destinationLocation"));

        TableColumn<FlightPlan, Double> dist  = new TableColumn<>("Distance");
        dist.setCellValueFactory(new PropertyValueFactory<>("distance"));

        TableColumn<FlightPlan, Double> consumed  = new TableColumn<>("Fuel Consumed");
        consumed.setCellValueFactory(new PropertyValueFactory<>("fuelConsumed"));

        TableColumn<FlightPlan, Double> remain = new TableColumn<>("Fuel Remaining");
        remain.setCellValueFactory(new PropertyValueFactory<>("fuelRemaining"));

        TableColumn<FlightPlan, String> termin = new TableColumn<>("Terminated At");
        termin.setCellValueFactory(new PropertyValueFactory<>("terminated"));

        TableColumn<FlightPlan, Double> remainTime = new TableColumn<>("Time remaining (s)");
        remainTime.setCellValueFactory(new PropertyValueFactory<>("timeRemainingSec"));

        flights_table.getItems().clear();
        flights_table.getColumns().clear();
        flights_table.setItems(plans);
        flights_table.getColumns().addAll(planID, shipID, arr, dep, dest, dist, consumed,remain,termin,remainTime);

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

    public void onLoadMyShips(){
        ObservableList<MyShip> ships = model.getAccountShips();
        //populate drop down lists
        ship_selected1.getItems().clear();
        ship_selected.getItems().clear();
        for(int i = 0 ; i < ships.size(); i++){
            ship_selected1.getItems().add(ships.get(i).getId());
            ship_selected.getItems().add(ships.get(i).getId());
        }
        message.setText("Loaded my ships in drop down list");

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

        flights_table.getItems().clear();
        flights_table.getColumns().clear();
        flights_table.setItems(ships);
        flights_table.getColumns().addAll(ship_id, shipClass, location, manufacturer, shipType, maxCargo, space, speed, xPos, yPos);

    }

    //same as Trades
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
                for(int j = 0; j < selected.getCargoList().size(); j++){
                    cargos.add(selected.getCargoList().get(j));
                }
                break;
            }
        }

        if(selected == null){
            message.setText("Error - couldn't find ship");
            return;
        }

        TableColumn<Cargo, String> cargo_good = new TableColumn<>("Good");
        cargo_good.setCellValueFactory(new PropertyValueFactory<>("good"));

        TableColumn<Cargo, Integer> cargo_quantity = new TableColumn<>("Quantity");
        cargo_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Cargo, Integer> cargo_vol = new TableColumn<>("Total Volume");
        cargo_vol.setCellValueFactory(new PropertyValueFactory<>("totalVolume"));

        flights_table.getItems().clear();
        flights_table.getColumns().clear();
        flights_table.setItems(cargos);
        flights_table.getColumns().addAll(cargo_good, cargo_quantity, cargo_vol);

    }
}
