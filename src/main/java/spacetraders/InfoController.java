package spacetraders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import spacetraders.model.Loan;
import spacetraders.model.ModelFacade;
import spacetraders.model.MyShip;
import spacetraders.model.TakenLoan;

import java.io.IOException;

public class InfoController {
    @FXML
    TextArea message;

    @FXML
    Label message1;
    @FXML
    Label message2;

    @FXML
    Label mode_label;
    @FXML
    TableView loans_table;

    @FXML
    TableView ships_table;

    @FXML
    ChoiceBox loan_selected;

    String loan_id = "";


    ModelFacade model;

    public InfoController(ModelFacade model){
        this.model = model;
    }

    public void initialize(){
        message.setText("Displaying loans and ships.");
        if(model.isOnline()){
            mode_label.setText("Running in online mode.");
        } else {
            mode_label.setText("Running in offline mode. Presenting dummy values.");
        }

        loan_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loan_id = String.valueOf(t1);
                if(loan_id != null){
                    message.setText("Selected loan with id ".concat(loan_id).concat("for pay off."));
                }

            }
        });
    }

    public void onLoadInfo() {
        String responseBody = model.viewAccount();
        message.setText(model.getAccountCredit(responseBody));
        message1.setText("Remaining credits: ");
        message2.setText(String.valueOf(model.getRemainingCredits(responseBody)));

        //Upper table for loans
        ObservableList<TakenLoan> loans = model.getAccountLoans();

        //populate loan_selected list on the right
        loan_selected.getItems().clear();
        for(int i = 0 ; i < loans.size() ; i++){
            loan_selected.getItems().add(loans.get(i).getId());
        }

        TableColumn<TakenLoan, String> due  = new TableColumn<TakenLoan, String>("Due Date");
        due.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<TakenLoan, String> id  = new TableColumn<TakenLoan, String>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TakenLoan, Double> repayment  = new TableColumn<>("Repayment");
        repayment.setCellValueFactory(new PropertyValueFactory<>("repay"));

        TableColumn<TakenLoan, String> status = new TableColumn<TakenLoan, String>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<TakenLoan, String> type = new TableColumn<TakenLoan, String>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("loanType"));

        loans_table.setItems(loans);
        loans_table.getColumns().addAll(due, id, repayment, status, type);

        //Lower table for ships
        ObservableList<MyShip> ships = model.getAccountShips();

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

        ships_table.setItems(ships);
        ships_table.getColumns().addAll(shipClass, location, manufacturer, shipType, maxCargo, space, speed, xPos, yPos);

    }

    public void onPayOffLoan(){
        if(loan_id.equals("")){
            message.setText("Cannot pay off loan - No loan ID selected");
            return;
        }

        String response = model.payOffLoan(loan_id);
        message.setText(response);

    }


    public void onToMain() throws IOException {
        ViewSwitcher.switchTo(View.MAIN);
    }

    public void onLogout() throws IOException {
        ViewSwitcher.switchTo(View.LOGIN);
    }


}
