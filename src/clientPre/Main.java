package clientPre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Button button;

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Hello World");
        this.primaryStage.setResizable(false);
        whiteBoardView();
    }

    private void whiteBoardView() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("clientViews/WhiteBoard.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    static void showLoginView() throws IOException {
//        Parent root = FXMLLoader.load(Main.class.getResource("test.fxml"));
//        primaryStage.setScene(new Scene(root, 800,500));
//        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}