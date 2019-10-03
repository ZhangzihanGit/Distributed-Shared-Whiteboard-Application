package dataServerApp;
/*
* TODO: registry 的创建似乎有点问题。 我们不需要LocateRegistry.createRegistry吗？为什么只需要getRegistry?
* */
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class DataServerApplication {
    private final static Logger logger = Logger.getLogger(DataServerApplication.class);

    /** Smallest available server port */
    private static final int SMALLEST_PORT = 1025;
    /** Largest available server port */
    private static final int LARGEST_PORT = 65535;

    private String serverIP = null;

    private IRemoteDb remoteDb = null;

    // Change the control of authentication from Remote Db to Data Server.
    private Authenticator authenticator = null;
    private DataWareHouse wareHouse = null;
    private Registry registry = null;
    /**
     * constructor
     */
    public DataServerApplication()   {
        try{
            this.remoteDb = new RemoteDb();
            // Singleton Instance for Authentication module.
            this.authenticator = Authenticator.getInstance();
//            DataServerFacade.getInstance();

        }catch (RemoteException e){
            e.printStackTrace();
            logger.fatal("Initialization database remote object failed");
        }
    }

    /**
     * start run server
     */
    public void runDataServer() {
        if (serverIP == null) {
            logger.fatal("Server address hasn't been specified");
            return;
        }

        try {
            // TODO: 很好奇这里咋回事， 如果是LocateRegistry.getRegistry(), 手动rmiregistry就会出问题。
            registry = LocateRegistry.createRegistry(1099);
            System.out.println(serverIP);
            registry.bind("DB", remoteDb);

            logger.info("Data server start running (by RMI) at IP: " + serverIP);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remote registry set up failed");
        }
    }

    /**
     * exit server program
     */
    public void exit() {
        try {
            UnicastRemoteObject.unexportObject(remoteDb, false);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remove remote object from rmi runtime failed");
        }
    }

    /**
     * Set up server address (ip, port)
     * @param ip
     * @return true if set successfully
     */
    public boolean setAddress(String ip) {
        this.serverIP = ip;
        return true;
    }
//    public JSONObject userRegister(String username, String password){
//        return authenticator.registerUser(username, password);
//    }
//    public JSONObject userAuthenticate(String username, String password){
//        return authenticator.authenticate(username, password);
//    }
    public Authenticator getAuthenticator(){
        return this.authenticator;
    }
}
