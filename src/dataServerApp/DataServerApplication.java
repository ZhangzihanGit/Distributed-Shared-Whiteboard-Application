package dataServerApp;
/*
* TODO: registry 的创建似乎有点问题。 我们不需要LocateRegistry.createRegistry吗？为什么只需要getRegistry?
* */
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
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
    private int serverPort = 0;

    private IRemoteDb remoteDb = null;

    // Change the control of authentication from Remote Db to Data Server.
    private Authenticator authenticator = null;
    private DataWareHouse wareHouse = null;
    /**
     * constructor
     */
    public DataServerApplication()   {
        try{
            remoteDb = new RemoteDb();
            // Singleton Instance for Authentication module.
            this.authenticator = Authenticator.getInstance();

        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    /**
     * start run server
     */
    public void runDataServer() {
        if (serverIP == null || serverPort == 0) {
            logger.fatal("Server address hasn't been specified");
            return;
        }

        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
            registry.bind("Database", remoteDb);

            logger.info("Data server start running (by RMI) at IP: " + serverIP + ", Port: " + serverPort);
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
//    public JSONObject userRegister(String username, String password){
//        return authenticator.registerUser(username, password);
//    }
//    public JSONObject userAuthenticate(String username, String password){
//        return authenticator.authenticate(username, password);
//    }
    public Authenticator getAuthenticator(){
        return this.authenticator;
    }
}
