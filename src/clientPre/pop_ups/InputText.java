package clientPre.pop_ups;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class InputText{

    public String display(){

        Stage window = new Stage();
        window.setTitle("");

        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(350);
        window.setMinHeight(250);
        TextField textField = new TextField();
        textField.setPrefWidth(180);
        textField.setCenterShape(true);
        Button button = new Button("Confirm");
        final String[] input = {""};
        button.setOnAction(e ->{
            input[0] = textField.getText().trim();
            window.close();
        });

        Label label = new Label("Please input your text here");
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(textField);

        Pane pane1 = new Pane();
        Pane pane2 = new Pane();

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, pane1, hbox, pane2, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
        return input[0];
    }
}
