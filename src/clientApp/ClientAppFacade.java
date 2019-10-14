package clientApp;

import clientPre.clientViewControllers.ClientGUIController;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Application layer facade of client
 * provide single control point of client application logic
 */
public class ClientAppFacade {
    private final static Logger logger = Logger.getLogger(ClientAppFacade.class);

    public final static String[] UserTopics = {"whiteboard", "message", "users", "general"};
    public final static int[] UserQos = {2, 2, 2, 2};
    public final static String[] nonUserTopics = {"join"};
    public final static int[] nonUserQos = {2};

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
    public boolean connectWbServer(String ip, String port) throws IOException {
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
     * @param wbName Name of whiteboard
     * @param subtopics Subtopics that this client will subscribe
     * @param qos Quality of services tags for each topic
     * @return True if subscribe successfully
     */
    public boolean subscribeTopic(String wbName, String[] subtopics, int[] qos) {
        return clientApp.subscribeTopic(wbName, subtopics, qos);
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
     * Update pending join request from the specific user
     * @param username Username
     * @param isAllow True is the join request is approved
     */
    public void allowJoin(String username, boolean isAllow) {
        clientApp.allowJoin(username, isAllow);
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
     * @return Closing feedback
     */
    public void closeWb() {
        clientApp.closeWb();
    }

    /**
     * Kick out specific visitor
     * @param visitor Username of visitor
     */
    public void kickUser(String visitor) {
        clientApp.kickUser(visitor);
    }

    /**
     * Render all the whiteboards
     * @param wb Whiteboard, String
     */
    public void updateWb(String wb) {
        clientApp.updateWb(wb);
    }

    /**
     * Send message
     * @param msg Message, String
     */
    public void sendMsg(String msg) {
        clientApp.sendMsg(msg);
    }

    /**
     * Exit client program
     */
    public void exit() {
        clientApp.exit();
    }

    // services provided from data layer
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

    // getter and setter
    public void setUsername(String username) {
        clientApp.setUsername(username);
    }

    public String getUsername() {
        return clientApp.getUsername();
    }

    public void setWbName(String wbName) {
        clientApp.setWbName(wbName);
    }

    public String getWbName() {
        return clientApp.getWbName();
    }
}
