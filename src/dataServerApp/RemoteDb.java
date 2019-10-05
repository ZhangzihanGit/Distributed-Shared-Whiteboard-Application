package dataServerApp;

import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteDb extends UnicastRemoteObject implements IRemoteDb {
    private DataServerFacade facade = null;

    public RemoteDb(DataServerFacade facade) throws RemoteException{
        super();
//        this.facade= facade;
        this.facade = DataServerFacade.getInstance();
    }

    /**
     * Register a user into the database.
     * @param username  Username entered
     * @param password  Password entered
     * @return
     * @throws RemoteException
     */
    @Override
    public JSONObject addUser(String username, String password) throws RemoteException {
        JSONObject returnMessage = facade.addUser(username, password);
        if (returnMessage.get("header").equals("Success")){
            facade.iteratePassBook();
        }

        return returnMessage;
    }

    /**
     * Check if a user is authorised to perform actions. This method envokes Authentication module.
     * The return message will be a string but with json format. The message has: "header": [Success/ Fail],
     * "message": [message body]
     * @param username  username that the user entered.
     * @param password  password that the user entered.
     * @return  A string with JSON format.
     * @throws RemoteException
     */
    // Authenticate the user by using the information stored in Authenticator.
    @Override
    public String checkUser(String username, String password) throws RemoteException {
        return facade.checkUser(username, password);
    }

    /**
     *  Save the cavas to local file system. Under the directory(with the name of the manager)
     * @param managerName   The manager name that wants to associate the canvas with.
     * @param wbContent   content of whiteboard(Should be a canvas object? or a png? or a JSON?)
     * @return  Probably return a save success JSON string? Not too sure yet.
     * @throws RemoteException
     */
    // TODO: 输出值根据Web Server决定。
    @Override
    public String saveWb(String managerName, String wbContent) throws RemoteException {
        return facade.saveWb(managerName, wbContent);
    }

    /**
     * Load canvas with specified manager name. This manager could possibly hold several canvas(maybe?)
     * @param managerName
     * @return
     * @throws RemoteException
     */
    // TODO: 输出值根据Web Server决定。 需要看能否存入Canva的东西。
    @Override
    public String loadAllWb(String managerName) throws RemoteException {
        return facade.loadAllWb(managerName);
    }
    // This is for testing purpose. Not known the communication protocol between the web server.
    @Override
    public UserInformation transferInformation(String username, String password) throws RemoteException{
        return new UserInformation(username,password, false);
    }
}
