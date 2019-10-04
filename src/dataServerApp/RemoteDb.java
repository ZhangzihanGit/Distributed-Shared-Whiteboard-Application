package dataServerApp;

import org.json.simple.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDb extends UnicastRemoteObject implements IRemoteDb {
    private Authenticator authenticator = null;
    private DataServerFacade facade = null;


    public RemoteDb(DataServerFacade facade, DataServerApplication application) throws RemoteException{
        super();

        this.facade= facade;
        this.authenticator = facade.getAuthenticator();
    }


    // TODO: 需要确认： 1. remote得到的用户信息包括了什么？是一个JSON么？如果是的话会有什么field 2. 用户画的内容是什么形式？
    // First two
    @Override
    public JSONObject addUser(String username, String password, JSONObject message) throws RemoteException {
        JSONObject returnMessage = (JSONObject) authenticator.
                registerUser(username, password);

        if (returnMessage.get("Header").equals("Success")){
            authenticator.iteratePassbook();
        }

        return returnMessage;
    }
    // Authenticate the user by using the information stored in Authenticator.
    @Override
    public String checkUser(String username, String password) throws RemoteException {
        JSONObject returnMessage = authenticator.authenticate(username, password);
        System.out.println(returnMessage);
        return returnMessage.toJSONString();
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
    // This is for testing purpose. Not known the communication protocol between the web server.
    @Override
    public UserInformation transferInformation(String username, String password) throws RemoteException{
        return new UserInformation(username,password, false);
    }
}
