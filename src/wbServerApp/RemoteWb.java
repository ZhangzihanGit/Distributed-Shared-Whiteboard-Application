package wbServerApp;

import java.rmi.RemoteException;

public class RemoteWb implements IRemoteWb {
    @Override
    public String register(String username, String password) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String createWb(String username, String wbName) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String getAvaliableWb() throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String joinWb(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String closeWb(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String saveWbOnline(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String saveWbLocally(String wbID, String username, String format) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String getAllStoredFiles(String username) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String openWbOnline(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String openWbLocally(String username, String wbContent) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String render(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String draw(String wbID, String username, String content) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String erase(String wbID, String username, String content) throws RemoteException {
        /* NEED FIX */
        return null;
    }

    @Override
    public String sendMsg(String wbID, String username, String msg) throws RemoteException {
        /* NEED FIX */
        return null;
    }
}
