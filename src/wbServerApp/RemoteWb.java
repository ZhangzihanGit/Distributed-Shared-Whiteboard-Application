package wbServerApp;

import wbServerData.WbServerDataStrategy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteWb extends UnicastRemoteObject implements IRemoteWb {
    protected RemoteWb() throws RemoteException {}

    @Override
    public String register(String username, String password) {
        return WbServerFacade.getInstance().register(username, password);
    }

    @Override
    public String login(String username, String password) {
        return WbServerFacade.getInstance().login(username, password);
    }

    @Override
    public String createWb(String wbName, String username) {
        return WbServerFacade.getInstance().createWb(wbName, username);
    }

    @Override
    public String joinWb(String wbName, String username) {
        return WbServerFacade.getInstance().joinWb(wbName, username);
    }

    @Override
    public void allowJoin(String username, boolean isAllow) {
        WbServerFacade.getInstance().allowJoin(username, isAllow);
    }

    @Override
    public String getCreatedWb() {
        return WbServerFacade.getInstance().getCreatedWb();
    }

    @Override
    public void closeWb(String wbName, String username) {
        WbServerFacade.getInstance().closeWb(wbName, username);
    }

    @Override
    public void kickUser(String manager, String visitor) {
        WbServerFacade.getInstance().kickUser(manager, visitor);
    }

    @Override
    public void updateWb(String wbName, String username, String wb) {
        WbServerFacade.getInstance().updateWb(wbName, username, wb);
    }

    @Override
    public void sendMsg(String wbName, String username, String msg) {
        WbServerFacade.getInstance().sendMsg(wbName, username, msg);
    }
}