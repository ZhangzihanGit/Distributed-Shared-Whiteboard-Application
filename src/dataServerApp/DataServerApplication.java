package dataServerApp;

import org.apache.log4j.Logger;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class DataServerApplication {
    private final static Logger logger = Logger.getLogger(DataServerApplication.class);

    /** Smallest available server port */
    private static final int SMALLEST_PORT = 1025;
    /** Largest available server port */
    private static final int LARGEST_PORT = 65535;

    private String serverIP = null;

    private IRemoteDb remoteDb = null;

    /**
     * constructor
     */
    public DataServerApplication() {
        try {
            remoteDb = new RemoteDb();
        } catch (Exception e) {
            logger.fatal("Initialization database remote object failed");
        }
    }

    /**
     * start run server
     */
    public void runDataServer() {
        if (serverIP == null) {
            logger.fatal("Server address hasn't been specified");
            return;
        }

        try {
            Registry registry = LocateRegistry.getRegistry(serverIP);
            registry.bind("Database", remoteDb);

            logger.info("Data server start running (by RMI) at IP: " + serverIP);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remote registry set up failed");
        }
    }

    /**
     * exit server program
     */
    public void exit() {
        try {
            UnicastRemoteObject.unexportObject(remoteDb, false);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remove remote object from rmi runtime failed");
        }
    }

    /**
     * Set up server address (ip, port)
     * @param ip
     * @return true if set successfully
     */
    public boolean setAddress(String ip) {
        this.serverIP = ip;
        return true;
    }
}
