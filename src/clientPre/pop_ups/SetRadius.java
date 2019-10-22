package clientPre.pop_ups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetRadius {
    public Double display(){

        Stage window = new Stage();
        window.setTitle("");

        Label l = new Label("Please input the radius:");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(200);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        TextField radius = new TextField();
        radius.setPrefWidth(150);
        hbox.getChildren().add(radius);

        Button button = new Button("Confirm");
        final Double[] r = new Double[1];
        button.setOnAction(e ->{
            r[0] = Double.valueOf(radius.getText());
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll( l, hbox, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
        return r[0];
    }
}
