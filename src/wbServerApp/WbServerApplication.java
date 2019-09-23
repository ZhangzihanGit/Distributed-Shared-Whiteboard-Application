package wbServerApp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

public class WbServerApplication {
    private final static Logger logger = Logger.getLogger(WbServerApplication.class);
    /** Smallest available server port */
    private static final int SMALLEST_PORT = 1025;
    /** Largest available server port */
    private static final int LARGEST_PORT = 65535;

    private String serverIP = null;
    private int serverPort = 0;

    public WbServerApplication() {}

    /**
     * start run server,
     */
    public void runWbServer() {
        if (serverIP == null || serverPort == 0) {
            logger.fatal("Server address hasn't been specified");
            return;
        }

        try {
            IRemoteWb remoteWb = new RemoteWb();

            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
            registry.bind("Whiteboard", remoteWb);

            logger.info("Server start running (by RMI) at IP: " + serverIP + ", Port: " + serverPort);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Remote registry set up failed");
        }
    }

    /**
     * Set up server address (ip, port)
     * @param ip
     * @param port
     * @return true if set successfully
     */
    public boolean setAddress(String ip, int port) {
        this.serverIP = ip;

        if (isValidPort(port)) {
            this.serverPort = port;
            return true;
        }
        else
            return false;
    }

    /**
     * Check whether given port number is valid
     * @param port
     * @return True if the port number is valid
     */
    private boolean isValidPort(int port) {
        if (port <= LARGEST_PORT && port >= SMALLEST_PORT)
            return true;
        else {
            logger.error("Port number should be some number between "
                    + SMALLEST_PORT + " and " +  LARGEST_PORT + ", instead of " + serverPort);
            return false;
        }
    }
}
