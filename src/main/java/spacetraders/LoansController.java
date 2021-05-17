package spacetraders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import spacetraders.model.Loan;
import spacetraders.model.ModelFacade;

import java.io.IOException;

public class LoansController {
    @FXML
    Label message;

    @FXML
    Label mode_label;

    ModelFacade model;

    @FXML
    ChoiceBox loan_selected;

    String loan_type = "";

    @FXML
    TableView loans_table;
    @FXML
    TableColumn amount;
    @FXML
    TableColumn collateral;
    @FXML
    TableColumn rate;
    @FXML
    TableColumn termdays;
    @FXML
    TableColumn type;

    public LoansController(ModelFacade model){
        this.model = model;
    }

    public void initialize(){
        message.setText("Manual refresh required to view loans.");
        if(model.isOnline()){
            mode_label.setText("Running in online mode.");
        } else {
            mode_label.setText("Running in offline mode. Presenting dummy values.");
        }

        loan_selected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loan_type = String.valueOf(t1);
                System.out.println(String.valueOf(t1).concat(" selected"));
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

    public void onViewLoans(){
        //display loans on table
        ObservableList<Loan> loans = model.viewAvailableLoans();
        System.out.println("View loans model complete");
        amount = new TableColumn<Loan, Double>("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        System.out.println("Set amount column");
        collateral = new TableColumn<Loan, Boolean>("Collateral Required");
        collateral.setCellValueFactory(new PropertyValueFactory<>("collateral"));

        rate = new TableColumn<Loan, Double>("Rate");
        rate.setCellValueFactory(new PropertyValueFactory<>("rate"));

        termdays = new TableColumn<Loan, Integer>("Rate");
        termdays.setCellValueFactory(new PropertyValueFactory<>("termDays"));

        type = new TableColumn<Loan, String>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("loanType"));

        loans_table.getColumns().clear();
        loans_table.setItems(loans);
        loans_table.getColumns().addAll(amount, collateral, rate, termdays, type);

        //populate choice box
        for(int i = 0 ; i < loans.size(); i++){
            loan_selected.getItems().add(loans.get(i).getLoanType());
        }

    }

    public void onTakeOutLoan(){
        if(loan_type.equals("")){
            message.setText("No loans selected - cannot take out loans");
        } else {
            //send request to take out loan
            String result = model.takeOutLoan(loan_type);
            message.setText(result);
        }


    }


}
