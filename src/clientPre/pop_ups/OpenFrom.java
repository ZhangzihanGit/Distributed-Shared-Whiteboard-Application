package clientPre.pop_ups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class OpenFrom {
    public String display(){
        Stage window = new Stage();
        window.setTitle("");

        Label l = new Label("Please select the file you want to open:");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(350);
        window.setMinHeight(250);

        Label label = new Label("Select file path:      ");

        TextField fileLocation = new TextField();
        fileLocation.setEditable(false);
        fileLocation.setPrefWidth(180);

        Button selectFolder = new Button("select");
        selectFolder.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("GIF","*.gif", "*.png", "PNG")
            );
            File file = fileChooser.showOpenDialog(window);
            fileLocation.setText(file.getPath());
        });

        FlowPane savePlacePane = new FlowPane();
        savePlacePane.setPadding(new Insets(11, 12, 13, 30));

        savePlacePane.setHgap(5);//设置控件之间的垂直间隔距离

        savePlacePane.setVgap(100);//设置控件之间的水平间隔距离
        savePlacePane.setAlignment(Pos.CENTER_LEFT);
        savePlacePane.getChildren().addAll(label, fileLocation, selectFolder);

        Pane pane = new Pane();
        Button button = new Button("Confirm");

        button.setOnAction(e ->{
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll( pane, l,  savePlacePane,  button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
        return fileLocation.getText();
    }
}
