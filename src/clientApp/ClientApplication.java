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
     * @return True if connect successfully
     */
    public boolean connectWbServer(String ip) {
        try {
            //Connect to the rmiregistry that is running on localhost
            Registry registry = LocateRegistry.getRegistry(ip);

            //Retrieve the stub/proxy for the remote math object from the registry
            remoteWb = (IRemoteWb) registry.lookup("Whiteboard");
            return true;
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Obtain remote service from whiteboard server(" + ip + ") failed");
            return false;
        }
    }

    /**
     * Register new user on server
     * @param username
     * @param password
     * @return Register information
     */
    public String register(String username, String password) {
        try {
            return remoteWb.register(username, password);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Register new users service from whiteboard server fail to execute");
            return "[ERROR]: Register new users service from whiteboard server fail to execute";
        }
    }

    /**
     * Existing user log in
     * @param username
     * @param password
     * @return Login information
     */
    public String login(String username, String password) {
        try {
            return remoteWb.login(username, password);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Existing user login service from whiteboard server fail to execute");
            return "[ERROR]: Existing user login service from whiteboard server fail to execute";
        }
    }
}
