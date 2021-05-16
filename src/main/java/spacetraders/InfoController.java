package spacetraders;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import spacetraders.model.ModelFacade;

import java.io.IOException;

public class InfoController {
    @FXML
    Label message;

    @FXML
    Label mode_label;

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

    ModelFacade model;

    public InfoController(ModelFacade model){
        this.model = model;
    }

    public void initialize(){
        message.setText("Diplaying loans and ships.");
        if(model.isOnline()){
            mode_label.setText("Running in online mode.");
        } else {
            mode_label.setText("Running in offline mode. Presenting dummy values.");
        }
    }

    public void onViewCredits() {
        String responseBody = model.viewAccount();
        message.setText(model.getAccountCredit(responseBody));

        //Upper table for loans


        //Lower table for ships
    }


    public void onToMain() throws IOException {
        ViewSwitcher.switchTo(View.MAIN);
    }

    public void onLogout() throws IOException {
        ViewSwitcher.switchTo(View.LOGIN);
    }


}
