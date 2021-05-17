package spacetraders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import spacetraders.model.Good;
import spacetraders.model.ModelFacade;
import spacetraders.model.MyShip;
import spacetraders.model.Ship;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarketController {
    @FXML
    TextArea message;

    @FXML
    TextArea message1;

    @FXML
    Label mode_label;

    ModelFacade model;


    String symbol = "OE-PM-TR";
    @FXML
    ChoiceBox location_list;

    ObservableList<Good> goods;

    @FXML
    ChoiceBox ship_selected;

    @FXML
    ChoiceBox good_selected;

    @FXML
    ChoiceBox quant_selected;

    //param for order
    String ship_id = "";
    String good = "";
    Integer quantity = 0;

    @FXML
    TableView goods_table;

    public MarketController(ModelFacade model) {
        this.model = model;
    }

    public void initialize(){
        message.setText("Manual refresh <View Marketplace> required to view goods.");
        message1.setText("Select location before <View Marketplace>\n"
        .concat("<View Marketplace> to load goods in Good select list\n")
        .concat("<Load My Ships> to load Ship ID in list"));

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

        location_list.getItems().add("OE-PM-TR");
        location_list.getItems().add("OE-CR");
        location_list.getItems().add("OE-KO");
        location_list.getItems().add("OE-UC");
        location_list.getItems().add("OE-UC-AD");
        location_list.getItems().add("OE-UC-OB");
        location_list.getItems().add("OE-NY");
        location_list.getItems().add("OE-BO");
        location_list.getItems().add("OE-XY-91-2");
        location_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                symbol = String.valueOf(t1);
                message.setText(String.valueOf(quantity).concat(" marketplace selected"));
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

    public void onViewMarket(){
        goods = model.viewMarketplace(symbol);
        if(goods == null){
            message.setText("Error viewing market, check if in same location?\n"
            .concat("Marketplace listings are only visible to docked ships at that location."));
            return;
        }
        TableColumn<Good, String> symbol = new TableColumn<>("Symbol");
        symbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Good, Double> ppu = new TableColumn<>("Price per Unit");
        ppu.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

        TableColumn<Good, Double> purpu = new TableColumn<>("Purchase Price per Unit");
        purpu.setCellValueFactory(new PropertyValueFactory<>("purchasePricePerUnit"));

        TableColumn<Good, Double> spu = new TableColumn<>("Sell Price per Unit");
        spu.setCellValueFactory(new PropertyValueFactory<>("sellPricePerUnit"));

        TableColumn<Good, Integer>  vol = new TableColumn<>("Volume per Unit");
        vol.setCellValueFactory(new PropertyValueFactory<>("volumePerUnit"));

        TableColumn<Good, Double> spread = new TableColumn<>("Spread");
        spread.setCellValueFactory(new PropertyValueFactory<>("spread"));

        TableColumn<Good, Integer>  quant = new TableColumn<>("Quantity");
        quant.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        goods_table.setItems(goods);
        goods_table.getColumns().addAll(symbol, ppu, purpu, spu, vol, spread, quant);

        //populate choice box
        good_selected.getItems().clear();
        for(int i = 0 ; i < goods.size(); i++){
            good_selected.getItems().add(goods.get(i).getSymbol());
        }
    }

    public void onLoadMyShips(){
        model.getAccountShips();
        List<MyShip> myships = model.getMyShips();
        if(myships.size() > 0){
            for(int i = 0 ; i < myships.size(); i++){
                ship_selected.getItems().add(myships.get(i).getId());
            }
        }
        message.setText("Loaded my ships in drop down list");
    }

    public void onPlaceOrder(){
        if(ship_id.equals("") || good.equals("") || quantity == 0){
            message.setText("Error placing order - all fields (Ship, good, quantity) selected?");
            return;
        }

        String result = model.placePurchaseOrder(ship_id, good, quantity);
        if(result != null){
            message.setText(result);
            return;
        }
        message.setText("Error placing order - check enough credits?");

    }
}
