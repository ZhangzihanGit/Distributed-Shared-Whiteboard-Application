import dataServerApp.DataServerApplication;
import dataServerApp.DataServerCmdValue;
import dataServerApp.DataServerFacade;

import java.net.SocketException;

public class runDataServer {
    public static void main(String[] args) {
        // log setting
        System.setProperty("my.log", "resources/log/dataServer.log");
        // security settings
        System.setProperty("java.security.policy", "file:./security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        DataServerCmdValue cmdValidator = new DataServerCmdValue(args);
        if (cmdValidator.isErrorFree()) {
            // rmi setting
            System.setProperty("java.rmi.server.hostname", cmdValidator.getServerIP());

            DataServerFacade facade = DataServerFacade.getInstance();
            facade.setupRemoteApplication(); // Start DB server. Meanwhile remote object is created.

            DataServerApplication application = facade.getDataServer();

            application.setAddress(cmdValidator.getServerPort());
            application.runDataServer();
        }
    }
}
