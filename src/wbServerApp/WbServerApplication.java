package wbServerApp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

public class WbServerApplication {
    private final static Logger logger = Logger.getLogger(WbServerApplication.class);
    /** Smallest available server port */
    private static final int SMALLEST_PORT = 1025;
    /** Largest available server port */
    private static final int LARGEST_PORT = 65535;

    private String serverIP = null;
    private int serverPort = 0;

    private IRemoteWb remoteWb = null;

    /**
     * constructor
     */
    public WbServerApplication() {
        remoteWb = new RemoteWb();
    }

    /**
     * start run server
     */
    public void runWbServer() {
        if (serverIP == null || serverPort == 0) {
            logger.fatal("Server address hasn't been specified");
            return;
        }

        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
            registry.bind("Whiteboard", remoteWb);

            logger.info("Whiteboard server start running (by RMI) at IP: " + serverIP + ", Port: " + serverPort);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Whiteboard remote registry set up failed");
        }
    }

    /**
     * exit server program
     */
    public void exit() {
        try {
            UnicastRemoteObject.unexportObject(remoteWb, false);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Whiteboard server remove remote object from rmi runtime failed");
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
