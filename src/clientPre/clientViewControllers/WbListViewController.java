package clientPre.clientViewControllers;

import clientApp.ClientAppFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
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
    private void controlJoinWb() throws IOException {

        ClientAppFacade clientApp = ClientAppFacade.getInstance();
        ClientGUIController clientGUI = ClientGUIController.getInstance();
        String selectedWbName = this.listView.getSelectionModel().getSelectedItem();
        boolean isEmpty = selectedWbName == null || selectedWbName.isEmpty();

        listView.setStyle(REMOVECSS);
        System.out.println("selected: " + selectedWbName);

        if (!isEmpty) {
            String joinRespond = clientApp.joinWb(selectedWbName);
            clientApp.subscribeTopic(selectedWbName, ClientAppFacade.nonUserTopics, ClientAppFacade.nonUserQos);

            /*
            // receive msg from server to know if join successfully
            if (clientApp.getHeader(joinRespond)) {
                System.out.println("join success");
                boolean isSubscribeSuccess = clientApp.subscribeTopic(selectedWbName, ClientAppFacade.nonUserTopics, ClientAppFacade.nonUserQos);
                boolean isSubscribeSuccess = true;
                if (isSubscribeSuccess) {
                    // move to the canvas
                    clientGUI.showWhiteBoardView();
                } else {
                    // TODO: 传回具体error msg给我
//                    ClientGUIController.getInstance().showErrorView("visitorJoin", clientApp.getMsg(joinRespond), selectedWbName);
                    clientGUI.showErrorView("visitorSubscribe", clientApp.getMsg(joinRespond), selectedWbName);
                }
            } else {
                // Pop out window to indicate there is no whiteboard being created yet (therefore can not join)
                clientGUI.showErrorView("visitorJoin", clientApp.getMsg(joinRespond), selectedWbName);
                System.out.println(clientApp.getMsg(joinRespond));
            }*/
        } else {
            listView.setStyle(WARNINGCSS);
        }
    }

    private void renderAvailableWb() {
        String listRespond = ClientAppFacade.getInstance().getCreatedWb();
        String[] list = ClientAppFacade.getInstance().getMsg(listRespond).split(",");
        ObservableList<String> listContainer = FXCollections.observableArrayList(Arrays.asList(list));
//        ObservableList<String> listContainer = FXCollections.observableArrayList("apple", "orange", "lemon");
        this.listView.setItems(listContainer);
    }
}
