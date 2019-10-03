package dataServerApp;

import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDb implements IRemoteDb {
    private Authenticator authenticator = null;
    private static DataServerFacade facade = null;


    public RemoteDb() throws RemoteException{
        super();
        // Make Singleton call.
        facade = DataServerFacade.getInstance();
        // Retrieve Authenticator module from the server.
//        authenticator = facade.getAuthenticator();

    }

    @Override
    public JSONObject addUser(String username, String password) throws RemoteException {
        /* NEED FIX */
        JSONObject returnMessage = (JSONObject) authenticator.registerUser(username, password).get("Header");
        if (authenticator.registerUser(username, password).get("Header").equals("Success")){

        }

        return null;
    }
    // Authenticate the user by using the information stored in Authenticator.
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
