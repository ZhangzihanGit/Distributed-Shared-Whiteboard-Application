package wbServerApp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataServerApp.IRemoteDb;
import org.apache.log4j.Logger;

public class WbServerApplication {
    private final static Logger logger = Logger.getLogger(WbServerApplication.class);

    private IRemoteWb remoteWb = null;
    private IRemoteDb remoteDb = null;

    private String manager = null;
    private ArrayList<String> users = new ArrayList<>();

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
     * start run server at localhost
     * @param port port, String
     */
    public void runWbServer(String port) {
        // parameter checking
        int portNum = 1111;
        try {
            portNum = Integer.parseInt(port);
        } catch (Exception e) {
            logger.warn(e.toString());
            logger.warn("port specified not valid, use default port number 1111");
        }

        try {
            Registry registry = LocateRegistry.createRegistry(portNum);
            registry.rebind("Whiteboard", remoteWb);

            logger.info("Whiteboard server start running (by RMI) at port: " + portNum);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Whiteboard remote registry set up failed");
        }
    }

    /**
     * Connect to data server
     * @param ip IP address, String
     * @param port port, String
     * @return True if connect successfully, Boolean
     */
    public boolean connectDbServer(String ip, String port) {
        // parameter checking
        int portNum = 1111;
        try {
            portNum = Integer.parseInt(port);
        } catch (Exception e) {
            logger.warn(e.toString());
            logger.warn("port specified not valid, use default port number 1111");
        }

        try {
            //Connect to the rmiregistry that is running on localhost
            Registry registry = LocateRegistry.getRegistry(ip, portNum);

            //Retrieve the stub/proxy for the remote math object from the registry
            remoteDb = (IRemoteDb) registry.lookup("Database");

            logger.info("connect to data server at ip: " + ip + ", port: " + portNum);
            return true;
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Obtain remote service from database server(" + ip + ") failed");
            return false;
        }
    }

    /**
     * Register new users
     * @param username Username, String
     * @param password Password, String
     * @return True if register successfully
     */
    public Boolean register(String username, String password) {
        //TODO: Add remote database authendication

        // return remoteDb.addUser(username, password);
        return true;
    }

    /**
     * Existing user login authentication
     * @param username Username, String
     * @param password Password, String
     * @return True if authenticate success, Boolean
     */
    public Boolean login(String username, String password) {
        //TODO: Add remote database authendication

        // return remoteDb.checkUser(username, password);
        return true;
    }

    /**
     * Create new whiteboard and set the user to be the manager
     * @param username Username, String
     * @return True if create successfully, Boolean
     */
    public synchronized Boolean createWb(String username) {
        if (this.manager == null) {
            this.manager = username;
            return true;
        }

        return false;
    }

    /**
     * join created whiteboard on server
     * @param username Username, String
     * @return True if join existing whiteboard successfully, Boolean
     */
    public synchronized Boolean joinWb(String username) {
        if (this.manager != null) {
            this.users.add(username);
            return true;
        }

        return false;
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
}
