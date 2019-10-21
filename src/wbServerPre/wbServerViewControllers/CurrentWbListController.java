package wbServerPre.wbServerViewControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import wbServerApp.WbServerFacade;

import java.io.IOException;
import java.util.Arrays;

public class CurrentWbListController {

    private final String WARNINGCSS = "-fx-border-color: red;";
    private final String REMOVECSS = "-fx-border-color: none;";
    @FXML
    private ListView<String> listView;

    public void initialize() {
        this.renderAvailableWb();
    }

    @FXML
    private void controlSelectWb() throws IOException {
        String selectedWbName = this.listView.getSelectionModel().getSelectedItem();
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

    private void renderAvailableWb() {
        String listRespond = WbServerFacade.getInstance().getCreatedWb();
        String[] list = listRespond.split(",");
        ObservableList<String> listContainer = FXCollections.observableArrayList(Arrays.asList(list));
//        ObservableList<String> listContainer = FXCollections.observableArrayList("apple", "orange", "lemon");
        this.listView.setItems(listContainer);
    }
}
