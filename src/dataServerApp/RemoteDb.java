package dataServerApp;

import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDb extends UnicastRemoteObject implements IRemoteDb {
    private Authenticator authenticator = null;
    private DataServerApplication application = null;
    private DataServerFacade facade = null;


    public RemoteDb(DataServerFacade facade, DataServerApplication application) throws RemoteException{
        super();
        this.facade= facade;
        System.out.println("This is from Remote object:"+facade);

        System.out.println("This is from Remote object:"+facade.getDataServer());
        System.out.println("This is from Remote object:"+application);
        System.out.println("终止了！！！");


        // Make Singleton call.
//        // Retrieve Authenticator module from the server.
        application = facade.getDataServer();
        System.out.println("This is the null pointer exception: "+ application);
//
//
//        authenticator = facade.getAuthenticator();
    }

    @Override
    public synchronized JSONObject addUser(String username, String password) throws RemoteException {
        /* NEED FIX */
        JSONObject returnMessage = (JSONObject) authenticator.registerUser(username, password).get("Header");
        if (authenticator.registerUser(username, password).get("Header").equals("Success")){
            return returnMessage;
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
