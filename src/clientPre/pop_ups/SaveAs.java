package clientPre.pop_ups;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaveAs {
    public List<String> display(){
        List<String> list = new ArrayList<>();
        Stage window = new Stage();
        window.setTitle("");

        Label l = new Label("Please input the information below:");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(350);
        window.setMinHeight(350);

        Label label = new Label("save to:      ");

        TextField fileLocation = new TextField();
        fileLocation.setPrefWidth(180);

        Button selectFolder = new Button("select");
        selectFolder.setOnAction(e->{
            DirectoryChooser directoryChooser=new DirectoryChooser();
            File file = directoryChooser.showDialog(window);
            fileLocation.setText(file.getPath());
        });

        FlowPane savePlacePane = new FlowPane();
        savePlacePane.setPadding(new Insets(11, 12, 13, 30));

        savePlacePane.setHgap(5);//设置控件之间的垂直间隔距离

        savePlacePane.setVgap(100);//设置控件之间的水平间隔距离
        savePlacePane.setAlignment(Pos.CENTER_LEFT);
        savePlacePane.getChildren().addAll(label, fileLocation, selectFolder);



        Label fileNameLabel = new Label("file name:    ");
        TextField fileName = new TextField();
        fileName.setPrefWidth(180);
        FlowPane fileNamePane = new FlowPane();
        fileNamePane.setPadding(new Insets(11, 12, 13, 30));

        fileNamePane.setHgap(5);//设置控件之间的垂直间隔距离

        fileNamePane.setVgap(100);//设置控件之间的水平间隔距离
        fileNamePane.setAlignment(Pos.CENTER_LEFT);
        fileNamePane.getChildren().addAll(fileNameLabel, fileName);


        Label fileTypeLabel = new Label("Please select a file type:    ");
        ComboBox<String> fileType = new ComboBox<>();
        List<String> typeItem = new ArrayList<String>();
        typeItem.add("gif");
        typeItem.add("png");
        fileType.getItems().addAll(typeItem);

        FlowPane fileTypePane = new FlowPane();
        fileTypePane.setPadding(new Insets(11, 12, 13, 30));

        fileTypePane.setHgap(5);//设置控件之间的垂直间隔距离

        fileTypePane.setVgap(100);//设置控件之间的水平间隔距离
        fileTypePane.setAlignment(Pos.CENTER_LEFT);
        fileTypePane.getChildren().addAll(fileTypeLabel, fileType);




        Pane pane1 = new Pane();
        Pane pane2 = new Pane();


        Button button = new Button("Confirm");

        button.setOnAction(e ->{
            if(!fileLocation.getText().trim().isEmpty()){
                list.add(fileLocation.getText().trim());
            }
            if(!fileName.getText().trim().isEmpty()){
                list.add(fileName.getText().trim());
            }
            list.add(fileType.getValue());
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll( pane1, l, fileNamePane, savePlacePane, fileTypePane, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
        return list;
    }
}
