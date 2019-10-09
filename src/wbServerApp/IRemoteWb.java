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
     * @param username Username, String
     * @return JSON response, String
     * @throws RemoteException
     */
    public String createWb(String username) throws RemoteException;

    /**
     * join created whiteboard on server
     * @param username Username, String
     * @return JSON response, String
     * @throws RemoteException
     */
    public String joinWb(String username) throws RemoteException;

    /**
     * Close specific whiteboard
     * @param wbID Whiteboard id
     * @param username Username
     * @return Closing feedback
     * @throws RemoteException
     */
    public String closeWb(String wbID, String username) throws RemoteException;

    /**
     * Save specific whiteboard online
     * @param wbID whiteboard id
     * @param username Username
     * @return Saving feedback
     * @throws RemoteException
     */
    public String saveWbOnline(String wbID, String username) throws RemoteException;

    /**
     * Save specific whiteboard locally
     * @param wbID Whiteboard id
     * @param username Username
     * @param format File format
     * @return Saving feedback
     * @throws RemoteException
     */
    public String saveWbLocally(String wbID, String username, String format) throws RemoteException;

    /**
     * Get all online-stored whiteboard files for a specific user
     * @param username Username
     * @return All whiteboard files
     * @throws RemoteException
     */
    public String getAllStoredFiles(String username) throws RemoteException;

    /**
     * Open specific online-stored whiteboard
     * @param wbID Whiteboard id
     * @param username Username
     * @return Open feedback
     * @throws RemoteException
     */
    public String openWbOnline(String wbID, String username) throws RemoteException;

    /**
     * Open specific locally-stored whiteboard
     * @param username Username
     * @param wbContent Whiteboard content
     * @return Open feedback
     * @throws RemoteException
     */
    public String openWbLocally(String username, String wbContent) throws RemoteException;

    /**
     * Render all the whiteboards
     * @param wbID Whiteboard id
     * @param username Username
     * @return Whiteboard content
     * @throws RemoteException
     */
    public String render(String wbID, String username) throws RemoteException;

    /**
     * Draw diagram
     * @param wbID Whiteboard id
     * @param username Username
     * @param content Drawing content
     * @return Drawing feedback
     * @throws RemoteException
     */
    public String draw(String wbID, String username, String content) throws RemoteException;

    /**
     * Erase diagram
     * @param wbID Whiteboard id
     * @param username Username
     * @param content Erasing content
     * @return Erasing feedback
     * @throws RemoteException
     */
    public String erase(String wbID, String username, String content) throws RemoteException;

    /**
     * Send message
     * @param wbID Whiteboard id
     * @param username Username
     * @param msg Message
     * @return Sending feeback
     * @throws RemoteException
     */
    public String sendMsg(String wbID, String username, String msg) throws RemoteException;
}
