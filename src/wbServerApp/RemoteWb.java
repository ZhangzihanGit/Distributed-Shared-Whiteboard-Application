package wbServerApp;

import wbServerData.WbServerDataStrategy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteWb extends UnicastRemoteObject implements IRemoteWb {
    protected RemoteWb() throws RemoteException {}

    @Override
    public String register(String username, String password) throws RemoteException {
        return WbServerFacade.getInstance().register(username, password);
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        return WbServerFacade.getInstance().login(username, password);
    }

    @Override
    public String createWb(String wbName, String username) throws RemoteException {
        return WbServerFacade.getInstance().createWb(wbName, username);
    }

    @Override
    public String joinWb(String wbName, String username) throws RemoteException {
        return WbServerFacade.getInstance().joinWb(wbName, username);
    }

    @Override
    public void allowJoin(String username, boolean isAllow) throws RemoteException {
        WbServerFacade.getInstance().allowJoin(username, isAllow);
    }

    @Override
    public String getCreatedWb() {
        return WbServerFacade.getInstance().getCreatedWb();
    }

    @Override
    public void closeWb(String username) throws RemoteException {
        WbServerFacade.getInstance().closeWb(username);
    }

    @Override
    public void kickUser(String manager, String visitor) throws RemoteException {
        WbServerFacade.getInstance().kickUser(manager, visitor);
    }

    @Override
    public void updateWb(String wbName, String username, String wb) throws RemoteException {
        WbServerFacade.getInstance().updateWb(wbName, username, wb);
    }

    @Override
    public void sendMsg(String wbName, String username, String msg) throws RemoteException {
        WbServerFacade.getInstance().sendMsg(wbName, username, msg);
    }
}
