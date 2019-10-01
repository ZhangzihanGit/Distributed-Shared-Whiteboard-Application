//package clientPre;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class Main extends Application {
//
//    private static Stage primaryStage;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        this.primaryStage = primaryStage;
//        this.primaryStage.setTitle("My WhiteBoard");
//        showWelcomeView();
//    }
//
//    private void showWelcomeView() throws IOException{
//        Parent root = FXMLLoader.load(getClass().getResource("clientViews/clientWelcome.fxml"));
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("../assets/css/clientGUI.css").toExternalForm());
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    static void showLoginView() throws IOException {
////        Parent root = FXMLLoader.load(Main.class.getResource("test.fxml"));
////        primaryStage.setScene(new Scene(root, 800,500));
////        primaryStage.show();
//    }
//
//}