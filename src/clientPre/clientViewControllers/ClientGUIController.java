package clientPre.clientViewControllers;

import clientApp.ClientAppFacade;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUIController extends Application {

    /**
     * private singleton instance
     */
    private static ClientGUIController clientGUIController = null;
    private final String WARNINGCSS = "-fx-border-color: red;";
    private final String REMOVECSS = "-fx-border-color: none;";
    private final String LABELCSS = "-fx-font-family: NunitoSans;-fx-text-fill: red;visibility: true;";
    private final String LABELREMOVECSS = "visibility: false;";
    private static Stage primaryStage;
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private TextField loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label identityHeader;
    @FXML private BorderPane checkBoxField;
    @FXML private CheckBox visitorCheckBox;
    @FXML private CheckBox managerCheckBox;
    @FXML private TextField signupUsernameField;
    @FXML private PasswordField signupPasswordField1;
    @FXML private PasswordField signupPasswordField2;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField IPField;
    @FXML private TextField portField;

    private ClientAppFacade facade = null;

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
        facade = ClientAppFacade.getInstance();
    }

    /**
     * initially show the welcome panel
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        showWelcomeView();
    }

    private void showWelcomeView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(FxmlView.WELCOME.getFxmlFile()));
        this.primaryStage.setTitle(FxmlView.WELCOME.getTitle());
        baseView();
    }

    @FXML
    private void showLoginView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(FxmlView.LOGIN.getFxmlFile()));
        this.primaryStage.setTitle(FxmlView.LOGIN.getTitle());
        baseView();
    }

    @FXML
    private void showSignupView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(FxmlView.SIGNUP.getFxmlFile()));
        this.primaryStage.setTitle(FxmlView.SIGNUP.getTitle());
        baseView();
    }

    @FXML
    private void showChooseIdentityView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(FxmlView.IDENTITY.getFxmlFile()));
        this.primaryStage.setTitle(FxmlView.IDENTITY.getTitle());

//        String userName = ClientAppFacade.getUserName();
        String userName = "Zihan";  // pass the username to me
        String header = userName + ", Which role do you wanna play today?";

//        identityHeader.setText(header);   // null pointer error, dont know why
        baseView();
    }

    @FXML
    private void showConfigView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(FxmlView.CONFIG.getFxmlFile()));
        this.primaryStage.setTitle(FxmlView.CONFIG.getTitle());
        baseView();
    }

    private void showLoginErrorView() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Unsuccessful");
        alert.setHeaderText("Sorry, login is unsuccessful");
        alert.setContentText("Incorrect username or password, please try again!");
        alert.showAndWait();
    }

    @FXML
    private void showWhiteBoardView() throws IOException{
        this.root = FXMLLoader.load(getClass().getResource(FxmlView.CANVAS.getFxmlFile()));
        this.primaryStage.setTitle(FxmlView.CANVAS.getTitle());
        baseView();
    }

    /**
     * control login
     */
    @FXML
    private void controlLogin() throws IOException {
//        String loginUsername = this.loginUsernameField.getText();
//        String loginPassword = this.loginPasswordField.getText();

        /** If not empty, pass it to the server to authenticate  */
        if (!this.checkIsEmpty(loginUsernameField, loginPasswordField)) {
            // Boolean isMatch = ClientAppFacade.authenticate(loginUsername, loginPassword);
            Boolean isMatch = true;

            if (isMatch) {
                // switch to next page
                this.showChooseIdentityView();
            } else {
                // prompt window
                this.showLoginErrorView();
            }
        }
    }

    @FXML
    private void controlSignup() throws IOException {

        String signupPassword1 = this.signupPasswordField1.getText();
        String signupPassword2 = this.signupPasswordField2.getText();

        /** If not empty */
        if (!this.checkIsEmpty(signupUsernameField, signupPasswordField1, signupPasswordField2)) {
            // if passwords match, , pass it to the server to authenticate
            if (signupPassword1.equals(signupPassword2) ) {
                passwordLabel.setStyle(LABELREMOVECSS);
                //TODO: boolean isUsed = ClientAppFacade.isUsed(signupUsername);
                boolean isUsed = false;

                if (!isUsed) {
                    // sign up successfully
                    System.out.println("sign up successfully");
                    //TODO: record the user info into server
                    this.showChooseIdentityView();
                } else {
                    usernameLabel.setStyle(LABELCSS);
                }
            } else {
                passwordLabel.setStyle(LABELCSS);
            }
        }
    }

    @FXML
    private void controlConfig() throws IOException {

        String ip = this.IPField.getText();
        String port = this.portField.getText();

        if(!this.checkIsEmpty(IPField, portField)) {
            //TODO: handle if IP and port is valid exception


//            this.showCanvasView();
            System.out.println("move to canvas");
            this.showWhiteBoardView();
        }

    }

    @FXML
    private void controlCheckBox() throws IOException {
        /** If not empty, pass it to next page  */
        if (!this.checkIsEmpty()) {
            this.showConfigView();
            // pass role to the server
            //ClientAppFacade.setRole(...);
        }
    }

    @FXML
    private void handleVisitor() {
        if (this.visitorCheckBox.isSelected()){
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

        signupUsernameField.setStyle(REMOVECSS);
        signupPasswordField1.setStyle(REMOVECSS);
        signupPasswordField2.setStyle(REMOVECSS);

        if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) return false;
        if (input1.isEmpty()) signupUsernameField.setStyle(WARNINGCSS);
        if (input2.isEmpty()) signupPasswordField1.setStyle(WARNINGCSS);
        if (input3.isEmpty()) signupPasswordField2.setStyle(WARNINGCSS);

        return true;
    }

    private boolean checkIsEmpty(){

        if (visitorCheckBox.isSelected() || managerCheckBox.isSelected()) return false;
        if (!visitorCheckBox.isSelected() && !managerCheckBox.isSelected()) checkBoxField.setStyle(WARNINGCSS);

        return true;
    }

    private void baseView() {
        this.scene = new Scene(this.root);
        this.scene.getStylesheets().add(getClass().getResource("../../assets/css/clientGUI.css").toExternalForm());
        this.primaryStage.getIcons().add(new Image(ClientGUIController.class.getResourceAsStream("../../assets/imgs/whiteboard.png")));
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }
}
