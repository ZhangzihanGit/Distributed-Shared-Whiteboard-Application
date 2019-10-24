package clientPre.clientViewControllers.whiteBoardController;

import clientApp.ClientAppFacade;
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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


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
    URI uri = null;
    private Image MANAGER_IMAGE = null;
    private Image VISITOR_IMAGE = null;

    public StringAndButtonList(String clientType) throws URISyntaxException, MalformedURLException {
        super();
        this.clientType = clientType;
        this.imageView = new ImageView();

        this.uri = StringAndButtonList.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        String managerIconPath = new URL("jar:" + uri.toString()+ "!/assets/imgs/manager2.png").toString();
        String visitorIconPath = new URL("jar:" + uri.toString()+ "!/assets/imgs/visitor2.png").toString();
        this.MANAGER_IMAGE = new Image(managerIconPath);
        this.VISITOR_IMAGE = new Image(visitorIconPath);

        if(!clientType.equals("manager")){
            button.setVisible(false);
        }
        pane2.setPrefWidth(5);
        hbox.getChildren().addAll(imageView, pane2, label, pane1, button);
        HBox.setHgrow(pane1, Priority.ALWAYS);
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
