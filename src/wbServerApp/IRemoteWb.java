package wbServerApp;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI Remote interface shared between whiteboard server  and client
 */
public interface IRemoteWb extends Remote {
    /**
     * Register new users
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from data server, String
     * @throws RemoteException
     */
    public String register(String username, String password) throws RemoteException;

    /**
     * Existing user login authentication
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from data server, String
     * @throws RemoteException
     */
    public String login(String username, String password) throws RemoteException;

    /**
     * Create new whiteboard and set the user to be the manager
     * @param wbName Name of whiteboard, String
     * @param username Username, String
     * @return JSON response, String
     * @throws RemoteException
     */
    public String createWb(String wbName, String username) throws RemoteException;

    /**
     * Request to join created whiteboard on server
     * @param wbName Name of whiteboard, String
     * @param username Username, String
     * @return JSON response, String
     * @throws RemoteException
     */
    public String joinWb(String wbName, String username) throws RemoteException;

    /**
     * Update pending join request from the specific user
     * @param username Username
     * @param isAllow True is the join request is approved
     */
    public void allowJoin(String username, boolean isAllow) throws RemoteException;

    /**
     * Get the name of all created whiteboards
     * @return JSON response, String
     */
    public String getCreatedWb() throws RemoteException;

    /**
     * Close specific whiteboard
     * @param wbName Whiteboard name, String
     * @param username Username
     * @throws RemoteException
     */
    public void closeWb(String wbName, String username) throws RemoteException;

    /**
     * Kick out specific visitor
     * @param wbName Whiteboard name, String
     * @param visitor Username of visitor, String
     * @throws RemoteException
     */
    public void kickUser(String wbName, String visitor) throws RemoteException;

    /**
     * Render all the whiteboards
     * @param wbName Whiteboard name, String
     * @param username Username, String
     * @param wb Whiteboard, String
     * @param receiver receiver, String
     * @throws RemoteException
     */
    public void updateWb(String wbName, String username, String wb, String receiver) throws RemoteException;

    /**
     * Send message
     * @param wbName Whiteboard name, String
     * @param username Username, String
     * @param msg Message, String
     * @throws RemoteException
     */
    public void sendMsg(String wbName, String username, String msg, String time) throws RemoteException;
}
