package clientPre.clientViewControllers;

import clientApp.ClientAppFacade;
import clientData.ClientDataStrategy;
import clientData.ClientDataStrategyFactory;
import clientPre.clientViewControllers.whiteBoardController.whiteBoardController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class ClientGUIController extends Application {
    private final static Logger logger = Logger.getLogger(ClientGUIController.class);

    /**
     * private singleton instance
     */
    private static ClientGUIController clientGUIController = null;
    private final String WARNINGCSS = "-fx-border-color: red;";
    private final String REMOVECSS = "-fx-border-color: none;";
    private final String LABELCSS = "-fx-font-family: NunitoSans;-fx-text-fill: red;visibility: true;";
    private final String LABELREMOVECSS = "visibility: false;";
    private URL entryURL = null;
    private static Stage primaryStage;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private TextField loginUsernameField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private BorderPane checkBoxField;
    @FXML
    private CheckBox visitorCheckBox;
    @FXML
    private CheckBox managerCheckBox;
    @FXML
    private TextField signupUsernameField;
    @FXML
    private PasswordField signupPasswordField1;
    @FXML
    private PasswordField signupPasswordField2;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField IPField;
    @FXML
    private TextField portField;
    @FXML
    private TextField brokerField;
    @FXML
    private TextField wbNameField;
    @FXML
    private ListView<String> listView;


    /**
     * private constructor
     */
    /** must have a default constructor for JavaFX to use */
//    private ClientGUIController() {}

    /**
     * get the singleton instance
     *
     * @return singleton instance of ClientGUIControl
     */
    public static ClientGUIController getInstance() {
        if (clientGUIController == null)
            clientGUIController = new ClientGUIController();

        return clientGUIController;
    }

    /**
     * run client GUI
     */
    public void runClientGUI() {
        launch();
    }

    /**
     * initially show the welcome panel
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setOnCloseRequest(event -> {
            ClientAppFacade app = ClientAppFacade.getInstance();
            app.closeWb();
            app.exit();

        });
        showWelcomeView();
    }

    @FXML
    private void showWelcomeView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.WELCOME.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.WELCOME.getTitle());
        baseView();
    }

    @FXML
    private void showLoginView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.LOGIN.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.LOGIN.getTitle());
        baseView();
    }

    @FXML
    private void showSignupView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.SIGNUP.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.SIGNUP.getTitle());
        baseView();
    }

    @FXML
    public void showChooseIdentityView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.IDENTITY.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.IDENTITY.getTitle());
        baseView();
    }

    @FXML
    private void showConfigView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.CONFIG.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.CONFIG.getTitle());
        baseView();
    }

    @FXML
    private void showMqttConfigView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.MQTT.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.MQTT.getTitle());
        baseView();
    }

    @FXML
    private void showCurrentWbView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.WB_LIST.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.WB_LIST.getTitle());
        baseView();
    }

    @FXML
    private void showCreateWbView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.CREATE_WB.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.CREATE_WB.getTitle());
        baseView();
    }

    @FXML
    public void showErrorView(String type, String msg, String wbName) {

        String title, header, text;
        Boolean isEmpty = msg == null || msg.isEmpty();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (type) {
            case "login":
                title = "Login Unsuccessful";
                header = "Sorry, login is unsuccessful";
                // TODO: better to return which is correct: username or password in msg
                text = isEmpty ? "Incorrect username or password, please try again!" : msg;
                break;
            case "signup":
                title = "Sign Up Unsuccessful";
                header = "Sorry, Sign up is unsuccessful";
                text = isEmpty ? "Fail to create a new account, please try again!" : msg;
                break;
            case "config":
                title = "Config Unsuccessful";
                header = "Sorry, configuration is unsuccessful";
                text = isEmpty ? "Fail to connect to the server, please try again!" : msg;
                break;
            case "mqttConfig":
                title = "Config Broker Unsuccessful";
                header = "Sorry, broker configuration is unsuccessful";
                text = isEmpty ? "Fail to connect to the broker, please try again!" : msg;
                break;
            case "visitorJoin":
                title = "Join Whiteboard Unsuccessful";
                header = "Sorry, joining " + wbName + " is unsuccessful";
                text = isEmpty ? "Fail to join the whiteboard, please try again!" : msg;
                break;
            case "managerCreate":
                title = "Create Whiteboard Unsuccessful";
                header = "Sorry, creating " + wbName + " is unsuccessful";
                text = isEmpty ? "Fail to create the whiteboard, please try again!" : msg;
                break;
            case "visitorSubscribe":
                title = "Subscribe Whiteboard Unsuccessful";
                header = "Sorry, subscribing " + wbName + " is unsuccessful";
                text = isEmpty ? "Fail to subscribe the whiteboard, please try again!" : msg;
                break;
            case "joinDenied":
                title = "Join Whiteboard Unsuccessful";
                header = "Sorry, request to join " + wbName + " is unsuccessful";
                text = isEmpty ? "Fail to join the whiteboard, please try again!" : msg;
                break;
            case "closed":
                title = "Closing Whiteboard";
                header = "Sorry, " + wbName + " will close";
                text = isEmpty ? "The whiteboard is closing, please try again!" : msg;
                break;
            case "fileName":
                title = "Save Whiteboard Unsuccessful";
                header = "Sorry, fail to save whiteboard";
                text = isEmpty ? "Fail to save whiteboard, please try again!" : msg;
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
    public void showJoinDeniedView(String msg) throws IOException, URISyntaxException {
        String selectedWb = ClientAppFacade.getInstance().getWbName();

        this.showErrorView("joinDenied", msg, selectedWb);
        this.showChooseIdentityView();
    }

    @FXML
    public void showJoinRequestView(String username) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType agreeBtn = new ButtonType("agree");
        ButtonType refuseBtn = new ButtonType("refuse");
        String title, header, text;

        title = "New Join Request";
        header = username + " request to join the whiteboard";
        text = "Would you agree it?";
        /* add customized buttons */
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(agreeBtn);
        alert.getButtonTypes().add(refuseBtn);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        Optional<ButtonType> btnClicked = alert.showAndWait();

        if (btnClicked.get() == agreeBtn) {
            logger.info("Manager agreed the join request from " + username);
            ClientAppFacade.getInstance().allowJoin(username, true);
            whiteBoardController.getInstance().updateNewUserWB(username);
        } else if (btnClicked.get() == refuseBtn) {
            logger.info("Manager refused the join request from " + username);
            ClientAppFacade.getInstance().allowJoin(username, false);
        }
    }

    @FXML
    public void showCloseView(String msg) throws IOException, URISyntaxException {
        this.showErrorView("closed", msg, ClientAppFacade.getInstance().getWbName());
        ClientAppFacade.getInstance().setWbName("");

        whiteBoardController.getInstance().newCanvas();
        whiteBoardController.getInstance().clearTextArea();
        this.showChooseIdentityView();
    }

    @FXML
    public void showWhiteBoardView() throws IOException, URISyntaxException {
        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ FxmlView.CANVAS.getFxmlFile());
        this.root = FXMLLoader.load(this.entryURL);
        this.primaryStage.setTitle(FxmlView.CANVAS.getTitle());
        baseView();
    }

    /**
     * control login
     */
    @FXML
    private void controlLogin() throws IOException, URISyntaxException {
        String loginUsername = this.loginUsernameField.getText();
        String loginPassword = this.loginPasswordField.getText();

        /** If not empty, pass it to the server to authenticate  */
        if (!this.checkIsEmpty(loginUsernameField, loginPasswordField)) {
            ClientAppFacade clientApp = ClientAppFacade.getInstance();
            String respond = clientApp.login(loginUsername, loginPassword);
            ClientDataStrategy jsonStrategy = ClientDataStrategyFactory.getInstance().getJsonStrategy();
            Boolean isMatch = jsonStrategy.getHeader(respond);

            if (isMatch) {
                // switch to next page
                logger.info("User " + loginUsername + " log in successfully");
                clientApp.setUsername(loginUsername);
                this.showChooseIdentityView();
            } else {
                // prompt window
                logger.info("User " + loginUsername + " log in failed");
                this.showErrorView("login", jsonStrategy.getMsg(respond), "");
            }
        }
    }

    @FXML
    private void controlSignup() throws IOException, URISyntaxException {

        String signupUsername = this.signupUsernameField.getText();
        String signupPassword1 = this.signupPasswordField1.getText();
        String signupPassword2 = this.signupPasswordField2.getText();

        if (!this.checkIsEmpty(signupUsernameField, signupPasswordField1, signupPasswordField2)) {
            // if passwords match, , pass it to the server to authenticate
            if (signupPassword1.equals(signupPassword2)) {
                passwordLabel.setStyle(LABELREMOVECSS);
                ClientAppFacade clientApp = ClientAppFacade.getInstance();
                ClientDataStrategy jsonStrategy = ClientDataStrategyFactory.getInstance().getJsonStrategy();

                String respond = clientApp.register(signupUsername, signupPassword1);
                boolean addSuccess = jsonStrategy.getHeader(respond);

                if (addSuccess) {
                    // sign up successfully
                    logger.info("New user " + signupUsername + " sign up successfully");
                    clientApp.setUsername(signupUsername);
                    this.showChooseIdentityView();
                } else {
                    logger.info("New user " + signupUsername + " sign up failed");
                    showErrorView("signup", jsonStrategy.getMsg(respond), "");
                    // usernameLabel.setStyle(LABELCSS);
                }
            } else {
                logger.info("Entered two passwords do not match ");
                passwordLabel.setStyle(LABELCSS);
            }
        }
    }

    @FXML
    private void controlConfig() throws IOException, URISyntaxException {

        String ip = this.IPField.getText();
        String port = this.portField.getText();

        if (!this.checkIsEmpty(IPField, portField)) {
            // if connect to server successfully, go to login page, else report error message
            ClientAppFacade clientApp = ClientAppFacade.getInstance();
            ClientDataStrategy jsonStrategy = ClientDataStrategyFactory.getInstance().getJsonStrategy();

            String respond = clientApp.connectWbServer(ip, port);
            clientApp.setIP(ip);

            Boolean isSuccess = jsonStrategy.getHeader(respond);
            if (isSuccess) {
                // display mqtt ip/port configuration
                this.showMqttConfigView();
            } else {
                // display error message if can't connect to server
                this.showErrorView("config", jsonStrategy.getMsg(respond), "");
            }
        }
    }

    @FXML
    private void controlMqttConfig() throws IOException, URISyntaxException {
        ClientAppFacade clientApp = ClientAppFacade.getInstance();

        String broker = this.brokerField.getText();
        String ip = clientApp.getIp().isEmpty() ? "localhost" : clientApp.getIp();  // make sure ip is not empty

        if (!this.checkIsEmpty(brokerField)) {
            ClientDataStrategy jsonStrategy = ClientDataStrategyFactory.getInstance().getJsonStrategy();

            String respond = clientApp.connectBroker(ip, broker);
            Boolean isSuccess = jsonStrategy.getHeader(respond);

            if (isSuccess) {
                // move to LoginView
                this.showLoginView();
            } else {
                // display error message if can't connect to server
                this.showErrorView("mqttConfig", jsonStrategy.getMsg(respond), "");
            }
        }
    }

    @FXML
    private void controlCheckBox() throws IOException, URISyntaxException {

        if (!this.checkIsEmpty()) {
            if (visitorCheckBox.isSelected()) {
                //  visitor can join one of existing whiteboards
                this.showCurrentWbView();
            } else if (managerCheckBox.isSelected()) {
                //  manager can create the whiteboard by enter the name
                this.showCreateWbView();
            }
        }
    }

    @FXML
    private void controlCreateWb() throws IOException, URISyntaxException {
        String wbName = this.wbNameField.getText();

        if (!this.checkIsEmpty(wbNameField)) {
            ClientAppFacade clientApp = ClientAppFacade.getInstance();
            ClientDataStrategy jsonStrategy = ClientDataStrategyFactory.getInstance().getJsonStrategy();

            clientApp.subscribeTopic(wbName, ClientAppFacade.UserTopics, ClientAppFacade.UserQos);
            String createRespond = clientApp.createWb(wbName);

            // if create whiteboard successfully
            if (jsonStrategy.getHeader(createRespond)) {
                clientApp.setWbName(wbName);
                clientApp.setManager(true);

                this.showWhiteBoardView();  // move to whiteboard page
            } else {
                // Pop out window to indicate there is one whiteboard being created (or already has manager)
                clientApp.unsubscribeTopic(wbName, ClientAppFacade.UserTopics);
                this.showErrorView("managerCreate", jsonStrategy.getMsg(createRespond), wbName);
            }
        }
    }

    @FXML
    private void controlGoBack() throws IOException, URISyntaxException {
        this.showLoginView();
    }

    @FXML
    private void handleVisitor() {
        if (this.visitorCheckBox.isSelected()) {
            checkBoxField.setStyle(REMOVECSS);
            this.managerCheckBox.setSelected(false);
        }
    }

    @FXML
    private void handleManager() {
        if (this.managerCheckBox.isSelected()) {
            checkBoxField.setStyle(REMOVECSS);
            this.visitorCheckBox.setSelected(false);
        }
    }

    private boolean checkIsEmpty() {

        if (visitorCheckBox.isSelected() || managerCheckBox.isSelected()) return false;
        if (!visitorCheckBox.isSelected() && !managerCheckBox.isSelected()) checkBoxField.setStyle(WARNINGCSS);

        return true;
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

    private boolean checkIsEmpty(TextField field1, TextField field2, TextField field3) {

        String input1 = field1.getText();
        String input2 = field2.getText();
        String input3 = field3.getText();

        field1.setStyle(REMOVECSS);
        field2.setStyle(REMOVECSS);
        field3.setStyle(REMOVECSS);

        if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) return false;
        if (input1.isEmpty()) field1.setStyle(WARNINGCSS);
        if (input2.isEmpty()) field2.setStyle(WARNINGCSS);
        if (input3.isEmpty()) field3.setStyle(WARNINGCSS);

        return true;
    }

    private void baseView() throws URISyntaxException, MalformedURLException {
        this.scene = new Scene(this.root);

        URI uri = ClientGUIController.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        this.entryURL = new URL("jar:" + uri.toString()+ "!/assets/css/clientGUI.css");
        this.scene.getStylesheets().add(this.entryURL.toExternalForm());

        String iconPath = new URL("jar:" + uri.toString()+ "!/assets/imgs/whiteboard.png").toString();
        this.primaryStage.getIcons().add(new Image(iconPath));
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }
}
