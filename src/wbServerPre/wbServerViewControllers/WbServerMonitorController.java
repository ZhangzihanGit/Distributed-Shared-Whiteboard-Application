//package wbServerPre.wbServerViewControllers;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//
//import java.io.IOException;
//
//public class WbServerMonitorController {
//
//
//    private static WbServerMonitorController wbServerMonitorController = null;
//
//    /**
//     * get the singleton instance
//     *
//     * @return singleton instance of WbServerMonitorController
//     */
//    public static WbServerMonitorController getInstance() {
//        if (wbServerMonitorController == null)
//            wbServerMonitorController = new WbServerMonitorController();
//
//        return wbServerMonitorController;
//    }
//
//    @FXML
//    public void showMonitorView(String wbName) throws IOException {
//        this.root = FXMLLoader.load(getClass().getResource(WbServerFxmlView.MONITOR.getFxmlFile()));
//        this.primaryStage.setTitle(WbServerFxmlView.MONITOR.getTitle());
//
//        this.scrollPane.setFitToWidth(true);
//        this.wbName.setText(wbName);
//        baseView();
//        System.out.println(wbName);
//    }
//
//    public void initialize() {
//        this.init();
//    }
//
//    private void init() {
//
//    }
//}
