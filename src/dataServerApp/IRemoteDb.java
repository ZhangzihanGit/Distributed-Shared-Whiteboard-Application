package dataServerApp;

import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI Remote interface shared between whiteboard server and data server
 */
public interface IRemoteDb extends Remote {
    /**
     * add new users
     * @param username
     * @param password
     * @return adding feedback
     * @throws RemoteException
     */
    public String addUser(String username, String password) throws RemoteException;

    /**
     * check whether the user exist and use the correct password
     * @param username
     * @param password
     * @return check results
     * @throws RemoteException
     */
    public String checkUser(String username, String password) throws RemoteException;

    public void userExit(String usernmae) throws RemoteException;
//    public String saveWb(String username, String wbContent) throws RemoteException;

    /**
     * load all whiteboard files saved by specific user
     * @param username
     * @return all whiteboard files
     * @throws RemoteException
     */
//    public String loadAllWb(String username) throws RemoteException;

}
