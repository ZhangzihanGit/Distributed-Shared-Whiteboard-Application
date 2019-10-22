package clientPre.clientViewControllers.whiteBoardController;

import clientApp.ClientAppFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class StringAndButtonList extends ListCell<String> {
    static ObservableList<String> list;
    HBox hbox = new HBox();
    Label label = new Label("(empty)");
    Pane pane = new Pane();
    Button button = new Button("x");
    String lastItem;

    public StringAndButtonList(String clientType) {
        super();
        if(!clientType.equals("manager")){
            button.setVisible(false);
        }
        hbox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientAppFacade.getInstance().kickUser(lastItem);

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
            }
            setGraphic(hbox);
        }
    }
}
