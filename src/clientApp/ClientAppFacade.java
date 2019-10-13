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
     * @param ip IP address, String
     * @param port port, String
     * @return True if connect successfully
     */
    public boolean connectWbServer(String ip, String port) {
        return clientApp.connectWbServer(ip, port);
    }

    /**
     * Connect to a remote broker
     * @param ip Ip address
     * @param port Port
     * @return True if connect successfully
     */
    public boolean connectBroker(String ip, String port) {
        return clientApp.connectBroker(ip, port);
    }

    /**
     * Let this client subscribe to a specific topic
     * @param topic Topic that this client will subscribe
     * @return True if subscribe successfully
     */
    public boolean subscribeTopic(String topic) {
        return clientApp.subscribeTopic(topic);
    }

    /**
     * Register new user on server
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from server, String
     */
    public String register(String username, String password) {
        return clientApp.register(username, password);
    }

    /**
     * Existing user log in authenticate
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from server, String
     */
    public String login(String username, String password) {
        return clientApp.login(username, password);
    }

    /**
     * Create new whiteboard on server and set the user to be the manager
     * @param wbName Name of whiteboard, String
     * @return JSON response from server, String
     */
    public String createWb(String wbName) {
        return clientApp.createWb(wbName);
    }

    /**
     * Join whiteboard on server
     * @param wbName Name of whiteboard, String
     * @return JSON response from server, String
     */
    public String joinWb(String wbName) {
        return clientApp.joinWb(wbName);
    }

    /**
     * Get the name of all created whiteboards
     * @return JSON response from server, String
     */
    public String getCreatedWb() {
        return clientApp.getCreatedWb();
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
     * Resolve the header of JSON respond from server
     * @param respond JSON respond from server, String
     * @return True if the header stores success, Boolean
     */
    public Boolean getHeader(String respond) {
        return clientApp.getHeader(respond);
    }

    /**
     * Resolve the message appended in the JSON respond from server
     * @param respond JSON respond from server, String
     * @return Message appended in the respond, String
     */
    public String getMsg(String respond) {
        return clientApp.getMsg(respond);
    }

    /**
     * Set the username of current client
     * @param username Username, String
     */
    public void setUsername(String username) {
        clientApp.setUsername(username);
    }

    /**
     * Get the username of current client
     * @return Username, String
     */
    public String getUsername() {
        return clientApp.getUsername();
    }

    /**
     * Exit client program
     */
    public void exit() {
        clientApp.exit();
    }
}
