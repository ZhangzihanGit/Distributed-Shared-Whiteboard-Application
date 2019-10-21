package clientPre.clientViewControllers.whiteBoardController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class StringAndButtonList extends ListCell<String> {
    static ObservableList<String> list;
    HBox hbox = new HBox();
    Label label = new Label("(empty)");
    Pane pane1 = new Pane();
    Pane pane2 = new Pane();
    Button button = new Button("x");
    String lastItem;

    private String clientType;
    private ImageView imageView = null;
    private final Image MANAGER_IMAGE = new Image(getClass().getResource("../../../assets/imgs/manager2.png").toExternalForm());
    private final Image VISITOR_IMAGE = new Image(getClass().getResource("../../../assets/imgs/visitor2.png").toExternalForm());

    public StringAndButtonList(String clientType) {
        super();
        this.clientType = clientType;
        this.imageView = new ImageView();
        if(!clientType.equals("manager")){
            button.setVisible(false);
        }
        pane2.setPrefWidth(5);
        hbox.getChildren().addAll(imageView, pane2, label, pane1, button);
        HBox.setHgrow(pane1, Priority.ALWAYS);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(lastItem);

            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);  // No text in label of super class
        if (empty) {
            lastItem = null;
            setGraphic(null);
        } else {

            lastItem = item;
            label.setText(item!=null ? item : "<null>");
            if(label.getText().equals(list.get(0))){
                button.setVisible(false);
                this.imageView.setImage(MANAGER_IMAGE);
            }
            else{
                this.imageView.setImage(VISITOR_IMAGE);
            }
            this.imageView.setFitHeight(27);
            this.imageView.setFitWidth(27);
            setGraphic(hbox);
        }
    }
}
