import dataServerApp.DataServerApplication;
import dataServerPre.DataServerGUIFacade;

import java.rmi.RemoteException;

public class runDataServer {
    public static void main(String[] args) throws RemoteException {
        System.setProperty("my.log", "resources/log/dataServer.log");

        DataServerGUIFacade.getInstance().runDataServerGUI();
        DataServerApplication dataServerApplication = new DataServerApplication();
        dataServerApplication.runDataServer();
    }
}
