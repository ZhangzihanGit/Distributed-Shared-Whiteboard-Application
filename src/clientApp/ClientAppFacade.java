package clientApp;

import org.apache.log4j.Logger;

/**
 * Application layer facade of client
 * provide single control point of client application logic
 */
public class ClientAppFacade {
    private final static Logger logger = Logger.getLogger(ClientAppFacade.class);

    /** private singleton instance */
    private static ClientAppFacade instance = null;

    private ClientApplication clientApp = null;

    /**
     * Private constructor
     */
    private ClientAppFacade() {
        clientApp = new ClientApplication();
    }

    /**
     * get the singleton instance
     * @return singleton instance of ClientAppFacade
     */
    public static ClientAppFacade getInstance() {
        if (instance == null)
            instance = new ClientAppFacade();

        return instance;
    }

    /**
     * connect to whiteboard server
     * @param ip
     * @return True if connect successfully
     */
    public boolean connectWbServer(String ip) {
        return clientApp.connectWbServer(ip);
    }

    /**
     * Register new user on server
     * @param username
     * @param password
     * @return Register information
     */
    public String register(String username, String password) {
        return clientApp.register(username, password);
    }

    /**
     * Existing user log in
     * @param username
     * @param password
     * @return Login information
     */
    public String login(String username, String password) {
        return clientApp.login(username, password);
    }

    /**
     * exit client program
     */
    public void exit() {
        logger.info("User exit client program");
        System.exit(1);
    }
}
