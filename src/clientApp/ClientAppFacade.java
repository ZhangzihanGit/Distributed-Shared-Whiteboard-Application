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
     * Get the singleton instance
     * @return singleton instance of ClientAppFacade
     */
    public static ClientAppFacade getInstance() {
        if (instance == null)
            instance = new ClientAppFacade();

        return instance;
    }

    /**
     * Connect to whiteboard server
     * @param ip IP address
     * @return True if connect successfully
     */
    public boolean connectWbServer(String ip) {
        return clientApp.connectWbServer(ip);
    }

    /**
     * Register new user on server
     * @param username Username
     * @param password Password
     * @return Register information
     */
    public String register(String username, String password) {
        return clientApp.register(username, password);
    }

    /**
     * Existing user log in
     * @param username Username
     * @param password Password
     * @return Login information
     */
    public String login(String username, String password) {
        return clientApp.login(username, password);
    }

    /**
     * Create new whiteboard and set the user to be the manager
     * @param username Username
     * @param wbName Whiteboard name
     * @return Created whiteboard information
     */
    public String createWb(String username, String wbName) {
        return clientApp.createWb(username, wbName);
    }

    /**
     * Get all available whiteboards
     * @return List of available whiteboards
     */
    public String getAvailableWb() {
        return clientApp.getAvailableWb();
    }

    /**
     * Join specific whiteboard
     * @param wbID Whiteboard id
     * @param username Username
     * @return Join feedback
     */
    public String joinWb(String wbID, String username) {
        return clientApp.joinWb(wbID, username);
    }

    /**
     * Close specific whiteboard
     * @param wbID Whiteboard id
     * @param username Username
     * @return Closing feedback
     */
    public String closeWb(String wbID, String username) {
        return clientApp.closeWb(wbID, username);
    }

    /**
     * Save specific whiteboard online
     * @param wbID Whiteboard id
     * @param username Username
     * @return Saving feedback
     */
    public String saveWbOnline(String wbID, String username) {
        return clientApp.saveWbOnline(wbID, username);
    }

    /**
     * Save specific whiteboard locally
     * @param wbID Whiteboard id
     * @param username Username
     * @param format File format
     * @return Saving feedback
     */
    public String saveWbLocally(String wbID, String username, String format) {
        return clientApp.saveWbLocally(wbID, username, format);
    }

    /**
     * Get all online-stored whiteboard files for a specific user
     * @param username Username
     * @return All whiteboard files
     */
    public String getAllStoredFiles(String username) {
        return clientApp.getAllStoredFiles(username);
    }

    /**
     * Open specific online-stored whiteboard
     * @param wbID Whiteboard id
     * @param username Username
     * @return Open feedback
     */
    public String openWbOnline(String wbID, String username) {
        return clientApp.openWbOnline(wbID, username);
    }

    /**
     * Open specific locally-stored whiteboard
     * @param username Username
     * @param wbContent Whiteboard content
     * @return Open feedback
     */
    public String openWbLocally(String username, String wbContent) {
        return clientApp.openWbLocally(username, wbContent);
    }

    /**
     * Render all the whiteboards
     * @param wbID Whiteboard id
     * @param username Username
     * @return Whiteboard content
     */
    public String render(String wbID, String username) {
        return clientApp.render(wbID, username);
    }

    /**
     * Draw diagram
     * @param wbID Whiteboard id
     * @param username Username
     * @param content Drawing content
     * @return Drawing feedback
     */
    public String draw(String wbID, String username, String content) {
        return clientApp.draw(wbID, username, content);
    }

    /**
     * Erase diagram
     * @param wbID Whiteboard id
     * @param username Username
     * @param content Erasing content
     * @return Erasing feedback
     */
    public String erase(String wbID, String username, String content) {
        return clientApp.erase(wbID, username, content);
    }

    /**
     * Send message
     * @param wbID Whiteboard id
     * @param username Username
     * @param msg Message
     * @return Sending feeback
     */
    public String sendMsg(String wbID, String username, String msg) {
        return clientApp.sendMsg(wbID, username, msg);
    }

    /**
     * Exit client program
     */
    public void exit() {
        logger.info("User exit client program");
        System.exit(1);
    }
}
