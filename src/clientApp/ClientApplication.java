package clientApp;

import org.apache.log4j.Logger;
import wbServerApp.IRemoteWb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApplication {
    private final static Logger logger = Logger.getLogger(ClientApplication.class);

    private String serverIP = null;
    private int serverPort = 0;

    private IRemoteWb remoteWb = null;

    /**
     * constructor
     */
    public ClientApplication() {}

    /**
     * Connect to whiteboard server
     * @param ip
     * @param port
     * @return True if connect successfully
     */
    public boolean connectWbServer(String ip, int port) {
        try {
            //Connect to the rmiregistry that is running on localhost
            Registry registry = LocateRegistry.getRegistry(ip, port);

            //Retrieve the stub/proxy for the remote math object from the registry
            remoteWb = (IRemoteWb) registry.lookup("Whiteboard");
            return true;
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Obtain remote service from whiteboard server(" + ip + ", " + port + ") failed");
            return false;
        }
    }
}
