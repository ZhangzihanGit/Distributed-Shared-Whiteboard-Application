package wbServerApp;

import org.apache.log4j.Logger;

public class WbServerFacade {
    private final static Logger logger = Logger.getLogger(WbServerFacade.class);

    /** private singleton instance */
    private static WbServerFacade instance = null;

    private WbServerApplication wbServer = null;

    /**
     * Private constructor
     */
    private WbServerFacade() {
        wbServer = new WbServerApplication();
    }

    /**
     * get the singleton instance
     * @return singleton instance of WbServerFacade
     */
    public static WbServerFacade getInstance() {
        if (instance == null)
            instance = new WbServerFacade();

        return instance;
    }

    /**
     * start run server at localhost
     * @param port Port number, String
     */
    public void runWbServer(String port) {
        wbServer.runWbServer(port);
    }

    /**
     * connect to database server
     * @param ip IP address, String
     * @param port port, String
     */
    public void connectDbServer(String ip, String port) {
        wbServer.connectDbServer(ip, port);
    }

    /**
     * Create new subprocess to start mosquitto broker
     * @param port Port
     */
    public void startBroker(String port) {
        wbServer.startBroker(port);
    }

    /**
     * Register new users
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from data server, String
     */
    public String register(String username, String password) {
        return wbServer.register(username, password);
    }

    /**
     * Existing user login authentication
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from data server, String
     */
    public String login(String username, String password) {
        return wbServer.login(username, password);
    }

    /**
     * Create new whiteboard and set the user to be the manager
     * @param wbName Name of whiteboard, String
     * @param username Username, String
     * @return JSON respond, String
     */
    public String createWb(String wbName, String username) {
        return wbServer.createWb(wbName, username);
    }

    /**
     * join created whiteboard on server
     * @param wbName Name of whiteboard, String
     * @param username Username, String
     * @return JSON respond, String
     */
    public String joinWb(String wbName, String username) {
        return wbServer.joinWb(wbName, username);
    }

    /**
     * Get the name of all created whiteboards
     * @return JSON response, String
     */
    public String getCreatedWb() {
        return wbServer.getCreatedWb();
    }

    /**
     * exit server program
     */
    public void exit() {
        wbServer.exit();
    }
}
