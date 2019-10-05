package wbServerApp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import dataServerApp.IRemoteDb;
import org.apache.log4j.Logger;

public class WbServerApplication {
    private final static Logger logger = Logger.getLogger(WbServerApplication.class);
    /** Smallest available server port */
    private static final int SMALLEST_PORT = 1025;
    /** Largest available server port */
    private static final int LARGEST_PORT = 65535;

    private int serverPort = 1111;

    private IRemoteWb remoteWb = null;
    private IRemoteDb remoteDb = null;

    /**
     * constructor
     */
    public WbServerApplication() {
        try {
            remoteWb = new RemoteWb();
        } catch (Exception e) {
            logger.fatal("Initialization whiteboard remote object failed");
        }
    }

    /**
     * start run server
     */
    public void runWbServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(serverPort);
            registry.rebind("Whiteboard", remoteWb);

            logger.info("Whiteboard server start running (by RMI) at port: " + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e.toString());
            logger.fatal("Whiteboard remote registry set up failed");
        }
    }

    /**
     * Connect to data server
     * @param ip
     * @param port
     */
    public boolean connectDbServer(String ip, int port) {
        try {
            //Connect to the rmiregistry that is running on localhost
            Registry registry = LocateRegistry.getRegistry(ip, port);

            //Retrieve the stub/proxy for the remote math object from the registry
            remoteDb = (IRemoteDb) registry.lookup("Database");
            return true;
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Obtain remote service from database server(" + ip + ") failed");
            return false;
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
     * Set up server address (port)
     * @param port
     * @return true if set successfully
     */
    public boolean setAddress(int port) {
        this.serverPort = port;
        return true;
    }
}
