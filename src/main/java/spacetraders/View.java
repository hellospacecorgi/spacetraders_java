package spacetraders;

public enum View {
    LOGIN("login.fxml"),
    MAIN("main.fxml"),
    LOANS("loans.fxml"),
    INFO("info.fxml"),
    SHIPS("ships.fxml"),
    MARKET("marketplace.fxml"),
    TRADE("trade.fxml"),
    FLIGHT("flight.fxml");

    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}