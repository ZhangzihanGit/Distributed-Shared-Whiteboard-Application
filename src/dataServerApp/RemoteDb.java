package dataServerApp;

import org.json.simple.JSONObject;

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
    // TODO: 输出值根据Web Server决定。
    @Override
    public String saveWb(String managerName, String wbContent) throws RemoteException {
        /* NEED FIX */
        JSONObject message = new JSONObject();
        message.put("hello", 123);
        facade.getDataServer().saveCanvas(message, "world");
        return null;
    }
    // TODO: 输出值根据Web Server决定。 需要看能否存入Canva的东西。
    @Override
    public String loadAllWb(String managerName) throws RemoteException {
        /* NEED FIX */
        facade.getDataServer().retrieveCanvas(managerName);
        return null;
    }
    // This is for testing purpose. Not known the communication protocol between the web server.
    @Override
    public UserInformation transferInformation(String username, String password) throws RemoteException{
        return new UserInformation(username,password, false);
    }
}
