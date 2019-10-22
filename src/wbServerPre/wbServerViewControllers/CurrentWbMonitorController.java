package wbServerPre.wbServerViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class CurrentWbMonitorController {

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
        this.scrollPane.setContent(loggerArea);
    }

    // TODO: in WbServerApplication runlater call this function
    public void updateLogger(String logger) {
        loggerArea.appendText(logger + "\n");
    }

    @FXML
    private void controlGoBack() throws IOException {
        WbServerGUIController.getInstance().showCurrentWbView();
    }
}
