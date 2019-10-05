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

    /**
     * Register a user into the database.
     * @param username  Username entered
     * @param password  Password entered
     * @param message  假设全部信息都会以一个json格式传过来。
     * @return
     * @throws RemoteException
     */
    @Override
    public JSONObject addUser(String username, String password, JSONObject message) throws RemoteException {
        JSONObject returnMessage = (JSONObject) authenticator.
                registerUser(username, password);

        if (returnMessage.get("Header").equals("Success")){
            authenticator.iteratePassbook();
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
        JSONObject returnMessage = authenticator.authenticate(username, password);
        System.out.println(returnMessage);
        return returnMessage.toJSONString();
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
        /* NEED FIX */
        JSONObject message = new JSONObject();
        message.put("hello", 123);
        facade.getDataServer().saveCanvas(message, "world");
        return null;
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
