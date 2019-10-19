package clientPre.pop_ups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SBwantToJoin {
    public String userName;
    public SBwantToJoin(String userName){
        this.userName = userName;
    }

    public Boolean display(){
        final Boolean[] choose = {true};

        Stage window = new Stage();
        window.setTitle("");

        Label l = new Label(userName + "want to join your white board. Would you like he or she to enter?");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(350);
        window.setMinHeight(250);

        Button yesButton = new Button("yes");
        Button noButton = new Button("no");

        yesButton.setOnAction(e->{
            choose[0] = true;
            window.close();
        });

        noButton.setOnAction(e->{
            choose[0] = false;
            window.close();
        });
        
        FlowPane choosePane = new FlowPane();
        choosePane.setPadding(new Insets(11, 12, 13, 30));

        choosePane.setHgap(100);//设置控件之间的垂直间隔距离
        choosePane.setVgap(50);//设置控件之间的水平间隔距离
        choosePane.setAlignment(Pos.CENTER);
        choosePane.getChildren().addAll( yesButton, noButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(l,  choosePane);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
        return choose[0];
    }

}
