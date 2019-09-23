package wbServerApp;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI Remote interface shared between whiteboard server  and client
 */
public interface IRemoteWb extends Remote {
    /**
     * Register new users
     * @param username
     * @param password
     * @return registration feedback in JSON
     * @throws RemoteException
     */
    public String register(String username, String password) throws RemoteException;

    /**
     * existing user login
     * @param username
     * @param password
     * @return login feedback in JSON
     * @throws RemoteException
     */
    public String login(String username, String password) throws RemoteException;

    /**
     * create new whiteboard and set the user to be the manager
     * @param username
     * @param wbName whiteboard name
     * @return created whiteboard information in JSON
     * @throws RemoteException
     */
    public String createWb(String username, String wbName) throws RemoteException;

    /**
     * get all available whiteboards
     * @return list of available whiteboards in JSON
     * @throws RemoteException
     */
    public String getAvaliableWb() throws RemoteException;

    /**
     * join specific whiteboard
     * @param username
     * @param wbID
     * @return join feedback in JSON
     * @throws RemoteException
     */
    public String joinWb(String wbID, String username) throws RemoteException;

    /**
     * Close specific whiteboard
     * @param wbID whiteboard id
     * @param username
     * @return closing feedback
     * @throws RemoteException
     */
    public String closeWb(String wbID, String username) throws RemoteException;

    /**
     * Save specific whiteboard online
     * @param wbID whiteboard id
     * @param username
     * @return saving feedback
     * @throws RemoteException
     */
    public String saveWbOnline(String wbID, String username) throws RemoteException;

    /**
     * Save specific whiteboard locally
     * @param wbID whiteboard id
     * @param username
     * @param format file format
     * @return saving feedback
     * @throws RemoteException
     */
    public String saveWbLocally(String wbID, String username, String format) throws RemoteException;

    /**
     * get all online-stored whiteboard files for a specific user
     * @param username
     * @return all whiteboard files in JSON
     * @throws RemoteException
     */
    public String getAllStoredFiles(String username) throws RemoteException;

    /**
     * Open specific online-stored whiteboard
     * @param wbID whiteboard id
     * @param username
     * @return open feedback
     * @throws RemoteException
     */
    public String openWbOnline(String wbID, String username) throws RemoteException;

    /**
     * Open specific locally-stored whiteboard
     * @param wbID whiteboard id
     * @param wbContent whiteboard content
     * @return open feedback
     * @throws RemoteException
     */
    public String openWbLocally(String username, String wbContent) throws RemoteException;

    /**
     * Render all the whiteboards
     * @param wbID whiteboard id
     * @param username
     * @return whiteboard content in JSON
     * @throws RemoteException
     */
    public String render(String wbID, String username) throws RemoteException;

    /**
     * draw diagram
     * @param wbID whiteboard id
     * @param username
     * @param content drawing content
     * @return drawing feedback
     * @throws RemoteException
     */
    public String draw(String wbID, String username, String content) throws RemoteException;

    /**
     * erase diagram
     * @param wbID whiteboard id
     * @param username
     * @param content erasing content
     * @return erasing feedback
     * @throws RemoteException
     */
    public String erase(String wbID, String username, String content) throws RemoteException;

    /**
     * send message
     * @param wbID whiteboard id
     * @param username
     * @param msg message
     * @return sending feeback
     * @throws RemoteException
     */
    public String sendMsg(String wbID, String username, String msg) throws RemoteException;
}
