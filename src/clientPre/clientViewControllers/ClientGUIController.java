package clientPre.clientViewControllers;

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
    private static Stage primaryStage;
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private TextField loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label identityHeader;
    @FXML private BorderPane checkBoxField;
    @FXML private CheckBox visitorCheckBox;
    @FXML private CheckBox managerCheckBox;

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
    public void runClientGUI() { launch(); }

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
    private void showChooseIdentityView() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(FxmlView.IDENTITY.getFxmlFile()));
        this.primaryStage.setTitle(FxmlView.IDENTITY.getTitle());

//        String userName = ClientAppFacade.getUserName();
        String userName = "Zihan";  // pass the username to me
        String header = userName + ", Which role do you wanna play today?";

//        this.identityHeader.setText(header);   // null pointer error, dont know why
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
    private void showConfigView() {

    }

    /**
     * control login
     */
    @FXML
    private void controlLogin() throws IOException {
        System.out.println("login clicked");

        String loginUsername = this.loginUsernameField.getText();
        String loginPassword = this.loginPasswordField.getText();

        /** If not empty, pass it to the server to authenticate  */
        if (!this.checkIsEmpty(loginUsername, loginPassword)) {
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

    private boolean checkIsEmpty(String loginUsername, String loginPassword) {

        loginUsernameField.setStyle(REMOVECSS);  // remove text field css
        loginPasswordField.setStyle(REMOVECSS);  // remove text field css

        if (!loginUsername.isEmpty() && !loginPassword.isEmpty()) return false;
        if (loginUsername.isEmpty()) loginUsernameField.setStyle(WARNINGCSS);
        if (loginPassword.isEmpty()) loginPasswordField.setStyle(WARNINGCSS);

        System.out.println("username: " + loginUsername);
        System.out.println("password: " + loginPassword);

        return true;
    }

    private boolean checkIsEmpty(){

        if (visitorCheckBox.isSelected() || managerCheckBox.isSelected()) return false;
        if (!visitorCheckBox.isSelected() && !managerCheckBox.isSelected()) checkBoxField.setStyle(WARNINGCSS);

        return true;
    }

    @FXML
    private void controlCheckBox() {
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



    private void baseView() {
        this.scene = new Scene(this.root);
        this.scene.getStylesheets().add(getClass().getResource("../../assets/css/clientGUI.css").toExternalForm());
        this.primaryStage.getIcons().add(new Image(ClientGUIController.class.getResourceAsStream("../../assets/imgs/whiteboard.png")));
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }
}
