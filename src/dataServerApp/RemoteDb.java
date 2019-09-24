package dataServerApp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDb extends UnicastRemoteObject implements IRemoteDb {
    protected RemoteDb() throws RemoteException {}

    @Override
    public String addUser(String username, String password) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String checkUser(String username, String password) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String saveWb(String username, String wbContent) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String loadAllWb(String username) throws RemoteException {
        /* NEED FIX */
        return null;
    }
}
