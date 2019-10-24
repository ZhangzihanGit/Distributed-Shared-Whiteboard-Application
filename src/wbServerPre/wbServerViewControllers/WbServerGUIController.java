package wbServerPre.wbServerViewControllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import wbServerApp.WbServerFacade;
import wbServerData.WbServerDataStrategy;
import wbServerData.WbServerDataStrategyFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WbServerGUIController extends Application {

    private static WbServerGUIController wbServerGUIController = null;
    private final String WARNINGCSS = "-fx-border-color: red;";
    private final String REMOVECSS = "-fx-border-color: none;";
    private final String LABELCSS = "-fx-font-family: NunitoSans;-fx-text-fill: red;visibility: true;";
    private final String LABELREMOVECSS = "visibility: false;";
    private static Stage primaryStage;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private TextField portField;
    @FXML
    TextField brokerField;
    @FXML
    private TextField IPField;
    @FXML
    private TextField dbPortField;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextArea loggerArea;
    @FXML
    private Label wbName;
    @FXML
    private Label managerName;

    /**
     * get the singleton instance
     *
     * @return singleton instance of ClientGUIControl
     */
    public static WbServerGUIController getInstance() {
        if (wbServerGUIController == null)
            wbServerGUIController = new WbServerGUIController();

        return wbServerGUIController;
    }

    /**
     * run whiteboard server GUI
     */
    public void runWbServerGUI() {
        launch();
    }

    /**
     * initially show the welcome panel
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setOnCloseRequest(event -> {
            WbServerFacade.getInstance().exit();
        });
        showWelcomeView();
    }

    @FXML
    public void showErrorView(String type, String msg, String wbName) {

        String title, header, text;
        Boolean isEmpty = msg == null || msg.isEmpty();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (type) {
            case "dbConfig":
                title = "Config Unsuccessful";
                header = "Sorry, database Server configuration is unsuccessful";
                text = isEmpty ? "Fail to connect to the DB server, please try again!" : msg;
                break;
            case "config":
                title = "Config Unsuccessful";
                header = "Sorry, whiteboard server configuration is unsuccessful";
                text = isEmpty ? "Fail to run the server, please try again!" : msg;
                break;
            case "mqttConfig":
                title = "Config Broker Unsuccessful";
                header = "Sorry, broker configuration is unsuccessful";
                text = isEmpty ? "Fail to run the broker, please try again!" : msg;
                break;
            case "closed":
                title = "Closing Whiteboard";
                header = "Sorry, " + wbName + " will close";
                text = isEmpty ? "The whiteboard is closing, please try again!" : msg;
                break;
            default:
                title = "Error";
                header = "Sorry, something wrong happened.";
                text = "Sorry, we have detected an error, please try again!";
                break;
        }

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    private void showWelcomeView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(WbServerFxmlView.WELCOME.getFxmlFile()));
        this.primaryStage.setTitle(WbServerFxmlView.WELCOME.getTitle());
        baseView();
    }

    @FXML
    private void showConfigView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(WbServerFxmlView.CONFIG.getFxmlFile()));
        this.primaryStage.setTitle(WbServerFxmlView.CONFIG.getTitle());
        baseView();
    }

    @FXML
    private void showConfigDbView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(WbServerFxmlView.CONFIG_DB.getFxmlFile()));
        this.primaryStage.setTitle(WbServerFxmlView.CONFIG_DB.getTitle());
        baseView();
    }

    @FXML
    private void showMqttConfigView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(WbServerFxmlView.MQTT.getFxmlFile()));
        this.primaryStage.setTitle(WbServerFxmlView.MQTT.getTitle());
        baseView();
    }

    @FXML
    public void showCurrentWbView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(WbServerFxmlView.WB_LIST.getFxmlFile()));
        this.primaryStage.setTitle(WbServerFxmlView.WB_LIST.getTitle());
        baseView();
    }

    @FXML
    public void showMonitorView() throws IOException {
        CurrentWbMonitorController.flag = true;
        this.root = FXMLLoader.load(getClass().getResource(WbServerFxmlView.MONITOR.getFxmlFile()));
        this.primaryStage.setTitle(WbServerFxmlView.MONITOR.getTitle());
        baseView();
    }


    @FXML
    private void controlDbconfig() throws IOException {
        String ip = this.IPField.getText();
        String dbPort = this.dbPortField.getText();

        if (!this.checkIsEmpty(IPField, dbPortField)) {
            WbServerFacade wbServer = WbServerFacade.getInstance();
            WbServerDataStrategy jsonStrategy = WbServerDataStrategyFactory.getInstance().getJsonStrategy();

            String respond = wbServer.connectDbServer(ip, dbPort);
            Boolean isSuccess = jsonStrategy.getHeader(respond);

            if (isSuccess) {
                this.showConfigView();
            } else {
                this.showErrorView("config", jsonStrategy.getMsg(respond), "");
            }
        }
    }

    @FXML
    private void controlConfig() throws IOException {
        String port = this.portField.getText();

        if (!this.checkIsEmpty(portField)) {
            WbServerFacade wbServer = WbServerFacade.getInstance();
            WbServerDataStrategy jsonStrategy = WbServerDataStrategyFactory.getInstance().getJsonStrategy();

            String respond = wbServer.runWbServer(port);
            Boolean isSuccess = jsonStrategy.getHeader(respond);
            if (isSuccess) {
                this.showMqttConfigView();
            } else {
                this.showErrorView("config", jsonStrategy.getMsg(respond), "");
            }
        }
    }

    @FXML
    private void controlMqttConfig() throws IOException {
        String broker = this.brokerField.getText();

        if (!this.checkIsEmpty(brokerField)) {
            WbServerFacade wbServer = WbServerFacade.getInstance();
            WbServerDataStrategy jsonStrategy = WbServerDataStrategyFactory.getInstance().getJsonStrategy();

            String respond = wbServer.startBroker(broker);
            Boolean isSuccess = jsonStrategy.getHeader(respond);

            if (isSuccess) {
                this.showCurrentWbView();
            } else {
                this.showErrorView("mqttConfig", jsonStrategy.getMsg(respond), "");
            }
        }
    }

    private boolean checkIsEmpty(TextField field) {

        String input = field.getText();
        field.setStyle(REMOVECSS);

        if (!input.isEmpty()) return false;
        if (input.isEmpty()) field.setStyle(WARNINGCSS);

        return true;
    }

    private boolean checkIsEmpty(TextField field1, TextField field2) {

        String input1 = field1.getText();
        String input2 = field2.getText();

        field1.setStyle(REMOVECSS);
        field2.setStyle(REMOVECSS);

        if (!input1.isEmpty() && !input2.isEmpty()) return false;
        if (input1.isEmpty()) field1.setStyle(WARNINGCSS);
        if (input2.isEmpty()) field2.setStyle(WARNINGCSS);

        return true;
    }

    private void baseView() {
        this.scene = new Scene(this.root);
        this.scene.getStylesheets().add(getClass().getResource("../../assets/css/clientGUI.css").toExternalForm());
        this.primaryStage.getIcons().add(new Image(WbServerGUIController.class.getResourceAsStream("../../assets/imgs/whiteboard.png")));
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }
}
