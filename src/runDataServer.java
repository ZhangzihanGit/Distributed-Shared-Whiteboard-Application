import dataServerApp.DataServerApplication;
import dataServerApp.DataServerFacade;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class runDataServer {
    public static void main(String[] args) throws SocketException {
        // log setting
        System.setProperty("my.log", "resources/log/dataServer.log");
        // rmi setting
        System.setProperty("java.rmi.server.hostname", "10.12.43.225");
        // security settings
        System.setProperty("java.security.policy", "file:./security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        DataServerFacade facade = DataServerFacade.getInstance();
        facade.setupRemoteApplication(); // Start DB server. Meanwhile remote object is created.

        DataServerApplication application = facade.getDataServer();

        try {
            application.setAddress(args[0]);
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Invalid command line argument, Usage: ");
            System.err.println("java -jar dataServer <portNum>");
            application.exit();
        }

        application.runDataServer();
    }
}
