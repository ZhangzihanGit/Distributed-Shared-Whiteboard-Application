package clientPre.clientViewControllers;

import clientApp.ClientAppFacade;
import clientData.ClientDataStrategy;
import clientData.ClientDataStrategyFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class WbListViewController {

    private final String WARNINGCSS = "-fx-border-color: red;";
    private final String REMOVECSS = "-fx-border-color: none;";
    @FXML
    private ListView<String> listView;

    public void initialize() {
        this.renderAvailableWb();
    }

    @FXML
    private void controlJoinWb() {
        ClientDataStrategy jsonStrategy = ClientDataStrategyFactory.getInstance().getJsonStrategy();
        ClientAppFacade clientApp = ClientAppFacade.getInstance();
        ClientGUIController clientGUI = ClientGUIController.getInstance();

        String selectedWbName = this.listView.getSelectionModel().getSelectedItem();
        boolean isEmpty = selectedWbName == null || selectedWbName.isEmpty();

        listView.setStyle(REMOVECSS);

        if (!isEmpty) {
            String joinRespond = clientApp.joinWb(selectedWbName);

            // receive msg from server to know if join successfully
            if (jsonStrategy.getHeader(joinRespond)) {
                clientApp.subscribeTopic(selectedWbName, ClientAppFacade.nonUserTopics, ClientAppFacade.nonUserQos);
            } else {
                // Pop out window to indicate there is no whiteboard being created yet (therefore can not join)
                clientGUI.showErrorView("visitorJoin", jsonStrategy.getMsg(joinRespond), selectedWbName);
            }
        } else {
            listView.setStyle(WARNINGCSS);
        }
    }

    @FXML
    private void controlGoBack() throws IOException, URISyntaxException {
        ClientGUIController.getInstance().showChooseIdentityView();
    }

    private void renderAvailableWb() {
        ClientDataStrategy jsonStrategy = ClientDataStrategyFactory.getInstance().getJsonStrategy();

        String listRespond = ClientAppFacade.getInstance().getCreatedWb();
        String[] list = jsonStrategy.getMsg(listRespond).split(",");

        ObservableList<String> listContainer = FXCollections.observableArrayList(Arrays.asList(list));
//        ObservableList<String> listContainer = FXCollections.observableArrayList("apple", "orange", "lemon");
        this.listView.setItems(listContainer);


    }
}
