package wbServerApp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteWb extends UnicastRemoteObject implements IRemoteWb {
    protected RemoteWb() throws RemoteException {}

    @Override
    public Boolean register(String username, String password) throws RemoteException {
        return WbServerFacade.getInstance().register(username, password);
    }

    @Override
    public Boolean login(String username, String password) throws RemoteException {
        return WbServerFacade.getInstance().login(username, password);
    }

    @Override
    public Boolean createWb(String username) throws RemoteException {
        return WbServerFacade.getInstance().createWb(username);
    }

    @Override
    public Boolean joinWb(String username) throws RemoteException {
        return WbServerFacade.getInstance().joinWb(username);
    }

    @Override
    public String closeWb(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return "CLOSE WB FUNCTION NOT FINISH ";
    }

    @Override
    public String saveWbOnline(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return "ONLINE SAVE FUNCTION NOT FINISH " + username + " " + wbID;
    }

    @Override
    public String saveWbLocally(String wbID, String username, String format) throws RemoteException {
        /* NEED FIX */
        return "LOCAL SAVE FUNCTION NOT FINISH " + username + " " + wbID;
    }

    @Override
    public String getAllStoredFiles(String username) throws RemoteException {
        /* NEED FIX */
        return "GET ALL STORED FILES FUNCTION NOT FINISH " + username;
    }

    @Override
    public String openWbOnline(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return "OPEN ONLINE SAVED WB FUNCTION NOT FINISH " + username + " " + wbID;
    }

    @Override
    public String openWbLocally(String username, String wbContent) throws RemoteException {
        /* NEED FIX */
        return "OPEN LOCAL SAVED WB FUNCTION NOT FINISH " + username;
    }

    @Override
    public String render(String wbID, String username) throws RemoteException {
        /* NEED FIX */
        return "RENDER FUNCTION NOT FINISH " + username + " " + wbID;
    }

    @Override
    public String draw(String wbID, String username, String content) throws RemoteException {
        /* NEED FIX */
        return "DRAW FUNCTION NOT FINISH " + username + " " + wbID;
    }

    @Override
    public String erase(String wbID, String username, String content) throws RemoteException {
        /* NEED FIX */
        return "ERASE FUNCTION NOT FINISH " + username + " " + wbID;
    }

    @Override
    public String sendMsg(String wbID, String username, String msg) throws RemoteException {
        /* NEED FIX */
        return "SEND MESSAGE FUNCTION NOT FINISH " + username + " " + wbID + " " + msg;
    }
}
