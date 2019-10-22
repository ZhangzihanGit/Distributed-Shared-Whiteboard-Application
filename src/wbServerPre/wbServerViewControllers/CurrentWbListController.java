package wbServerPre.wbServerViewControllers;

import clientData.ClientDataStrategyFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import wbServerApp.WbServerFacade;

import java.io.IOException;
import java.util.Arrays;

public class CurrentWbListController {

    private static CurrentWbListController instance = null;
    private final String WARNINGCSS = "-fx-border-color: red;";
    private final String REMOVECSS = "-fx-border-color: none;";
//    @FXML
//    private ListView<String> listView;
    @FXML private Pane listViewContainer;
    public static ListView<String> listView;

    public static CurrentWbListController getInstance() {
        if (instance == null)
            instance = new CurrentWbListController();

        return instance;
    }

    public void initialize() {

        listView = new ListView<>();
        listView.setPrefSize(318, 169);
        this.listViewContainer.getChildren().add(listView);

        this.renderInitWb();
    }

    @FXML
    private void controlSelectWb() throws IOException {
        String selectedWbName = listView.getSelectionModel().getSelectedItem();
        boolean isEmpty = selectedWbName == null || selectedWbName.isEmpty();

        listView.setStyle(REMOVECSS);

        if (!isEmpty) {
//            this.showMonitorView(selectedWbName);
//            WbServerMonitorController.getInstance().showMonitorView(selectedWbName);
            WbServerGUIController.getInstance().showMonitorView();
        } else {
            listView.setStyle(WARNINGCSS);
        }

    }

    private void renderInitWb() {
        String listRespond = WbServerFacade.getInstance().getCreatedWb();
        String availableWbs = ClientDataStrategyFactory.getInstance().getJsonStrategy().getMsg(listRespond);

        String[] list = availableWbs.split(",");

        System.out.println(Arrays.toString(list));
        for(int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }

        if (list.length > 0) {
            ObservableList<String> listContainer = FXCollections.observableArrayList(Arrays.asList(list));
//        ObservableList<String> listContainer = FXCollections.observableArrayList("apple", "orange", "lemon");
            listView.setItems(listContainer);
        }
    }

    public void updateWbList(String wbName) {
        listView.getItems().add(wbName);
    }
}
