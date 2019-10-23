package clientPre.pop_ups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SetHeightAndWidth {

    public List<Double> display(){
        List<Double> list = new ArrayList<>();
        Stage window = new Stage();
        window.setTitle("");

        Label l = new Label("Please input the width and height below:");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(350);
        window.setMinHeight(300);
        TextField textField1 = new TextField();
        textField1.setPrefWidth(180);
        TextField textField2 = new TextField();
        textField2.setPrefWidth(180);
        Button button = new Button("Confirm");

        button.setOnAction(e ->{
            if(!textField1.getText().trim().isEmpty()){
                list.add(Double.parseDouble(textField1.getText().trim()));
            }
            if(!textField2.getText().trim().isEmpty()){
                list.add(Double.parseDouble(textField2.getText().trim()));
            }
            window.close();
        });

        Label label = new Label("width:    ");
        FlowPane fpane = new FlowPane();
        fpane.setPadding(new Insets(11, 12, 13, 14));

        fpane.setHgap(5);//设置控件之间的垂直间隔距离

        fpane.setVgap(100);//设置控件之间的水平间隔距离
        fpane.setAlignment(Pos.CENTER);
        fpane.getChildren().addAll(label, textField1);

        Label label2 = new Label("height:    ");
        FlowPane fpane2 = new FlowPane();
        fpane2.setPadding(new Insets(11, 12, 13, 14));

        fpane2.setHgap(5);//设置控件之间的垂直间隔距离

        fpane2.setVgap(100);//设置控件之间的水平间隔距离
        fpane2.setAlignment(Pos.CENTER);
        fpane2.getChildren().addAll(label2, textField2);

        Pane pane1 = new Pane();
        Pane pane2 = new Pane();

        VBox layout = new VBox(10);
        layout.getChildren().addAll( pane1, l, fpane2, fpane, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
        return list;
    }
}
