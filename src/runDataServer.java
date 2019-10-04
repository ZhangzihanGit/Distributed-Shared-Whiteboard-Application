import dataServerApp.DataServerApplication;
import dataServerApp.DataServerFacade;
import dataServerPre.DataServerGUIFacade;

import javax.xml.crypto.Data;
import java.rmi.RemoteException;

public class runDataServer {
    public static void main(String[] args) {
        System.setProperty("my.log", "resources/log/dataServer.log");

//        DataServerGUIFacade.getInstance().runDataServerGUI();
        DataServerFacade facade = DataServerFacade.getInstance();
        System.out.println("Start of Facade"+facade);

        facade.createServerApplication(); // Start DB server. Meanwhile remote object is created.

        DataServerApplication application = facade.getDataServer();

        System.out.println("Data server saw from the main: "+application + facade);

//        application.setAddress("localhost");
//
//        System.out.println("Authenticator: "+application.getAuthenticator());
//        application.runDataServer();



    }
}
