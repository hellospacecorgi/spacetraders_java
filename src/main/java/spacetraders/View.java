package spacetraders;

public enum View {
    LOGIN("login.fxml"),
    MAIN("main.fxml"),
    LOANS("loans.fxml"),
    INFO("info.fxml");

    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}