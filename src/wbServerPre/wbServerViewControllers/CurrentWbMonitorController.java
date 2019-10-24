package wbServerPre.wbServerViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class CurrentWbMonitorController {
    public static boolean flag = false;
    public static CurrentWbMonitorController instance = null;
    @FXML private ScrollPane scrollPane;
    private static TextArea loggerArea;

    public static CurrentWbMonitorController getInstance() {
        if (instance == null)
            instance = new CurrentWbMonitorController();

        return instance;
    }

    public void initialize() {

        loggerArea = new TextArea();
        loggerArea.setPrefSize(575, 256);
        loggerArea.setEditable(false);
        this.scrollPane.setContent(loggerArea);
    }

    // TODO: in WbServerApplication runlater call this function
    public void updateLogger(String logger) {
        if(flag == true){
            loggerArea.appendText(logger + "\n");
        }
    }
}
