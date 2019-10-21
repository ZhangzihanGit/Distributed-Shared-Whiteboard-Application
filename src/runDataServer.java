import dataServerApp.DataServerApplication;
import dataServerApp.DataServerFacade;

public class runDataServer {
    public static void main(String[] args) {
        System.setProperty("my.log", "resources/log/dataServer.log");

//        DataServerGUIFacade.getInstance().runDataServerGUI();
        DataServerFacade facade = DataServerFacade.getInstance();
        facade.setupRemoteApplication(); // Start DB server. Meanwhile remote object is created.

        DataServerApplication application = facade.getDataServer();


        application.setAddress("localhost");
        application.runDataServer();



    }
}
