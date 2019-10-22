package dataServerApp;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class DataServerApplication {
    private final static Logger logger = Logger.getLogger(DataServerApplication.class);
    private int defaultPort = 1099;

    private IRemoteDb remoteDb = null;

    // Change the control of authentication from Remote Db to Data Server.
    private Authenticator authenticator = null;
    private DataWareHouse wareHouse = null;
    private Registry registry = null;
    private DataWareHouse dataWareHouse = null;
    /**
     * constructor
     */
    public DataServerApplication() {
        this.authenticator = Authenticator.getInstance();
        this.dataWareHouse = new DataWareHouse();
        authenticator.syncStorage(dataWareHouse.getLocalPassbook());
    }

    /**
     * start run server
     */
    public void runDataServer() {
        try {
            // For testing purpose, IP address is not used(since it is for now only local machine)
            // Later the ip will be used for several machines testing purpose.
            registry = LocateRegistry.createRegistry(defaultPort);
            registry.bind("DB", remoteDb);

            logger.info("Data server start running (by RMI) at localhost port: " + defaultPort);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remote registry set up failed");
            this.exit();
        }
    }

    /**
     * exit data server program
     */
    public void exit() {
        try {
            UnicastRemoteObject.unexportObject(remoteDb, false);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Data server remove remote object from rmi runtime failed");
        }

        System.exit(1);
    }

    /**
     * Set up server address (port)
     * @param port, String
     * @return true if set successfully
     */
    public void setAddress(String port) {
        try {
            this.defaultPort = Integer.parseInt(port);
        } catch (Exception e) {
            logger.fatal("Invalid port number: " + port);
            this.exit();
        }
    }



    void setRemoteDb(DataServerFacade facade){
        try{
            this.remoteDb = new RemoteDb(facade);

        }catch (Exception e){
            logger.fatal("Initialization database remote object failed");
            this.exit();
        }
    }

    public String addUser(String username, String password){
        JSONObject message = authenticator.registerUser(username, password);
        if(message.get("header").equals("Success")){
            String encryptedPassword = message.get("encoded_password").toString();
            logger.info("Now write to the server:");
            dataWareHouse.writeDb(username,encryptedPassword);
        }
        return message.toJSONString();
    }

    public String checkUser(String username, String password){
        return authenticator.authenticate(username, password).toJSONString();
    }


    void iteratePassBook(){
        authenticator.iteratePassbook();
    }
}
