package spacetraders;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import spacetraders.model.ModelFacade;

import java.io.IOException;

public class MainController {

    @FXML
    Label message;

    @FXML
    Label mode_label;

    ModelFacade model;

    public MainController(ModelFacade model){
        this.model = model;
    }

    @FXML
    public void initialize(){
        message.setText("Hello! Logged in ".concat(model.getInfo()));
        if(model.isOnline()){
            mode_label.setText("Running in online mode.");
        } else {
            mode_label.setText("Running in offline mode. Presenting dummy values.");
        }
    }

    public void onLogout() throws IOException {
        ViewSwitcher.switchTo(View.LOGIN);
    }

    public void onCheckServer() {
        message.setText(model.getServerStatus());
    }

    public void onViewInfo() throws IOException {
        ViewSwitcher.switchTo(View.INFO);
    }

    public void onLoans() throws IOException {
        ViewSwitcher.switchTo(View.LOANS);
    }
}
