package clientPre.clientViewControllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUIController extends Application {

    /**
     * private singleton instance
     */
    private static ClientGUIController clientGUIController = null;
    private static Stage primaryStage;


//    @FXML private Button btnStart;
    @FXML private Parent root;
    @FXML private Scene scene;

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

    public void runClientGUI() { launch(); }

    /**
     * initially show the welcome panel
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        showWelcomeView();
    }

    public void showWelcomeView() throws IOException {

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

    private void baseView() {
        this.scene = new Scene(this.root);
        this.scene.getStylesheets().add(getClass().getResource("../../assets/css/clientGUI.css").toExternalForm());
        this.primaryStage.getIcons().add(new Image(ClientGUIController.class.getResourceAsStream("../../assets/imgs/whiteboard.png")));
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }
}
