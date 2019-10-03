package dataServerApp;

import org.apache.log4j.Logger;

import java.rmi.RemoteException;

public class DataServerFacade {
    private final static Logger logger = Logger.getLogger(DataServerFacade.class);

    /** private singleton instance */
    private static DataServerFacade instance = null;

    private DataServerApplication dataServer = null;

    /**
     * Private constructor
     */
    private DataServerFacade() {
//        dataServer = new DataServerApplication();
        if (instance!=null){
            throw new RuntimeException("Use getInstance method to get the class");
        }
    }

    /**
     * get the singleton instance
     * @return singleton instance of DataServerFacade
     */
    public static synchronized DataServerFacade getInstance() {
        if (instance == null) {
            instance = new DataServerFacade();
        }
        return instance;
    }

    /**
     * start run server
     */
    public void runDataServer() {
        dataServer.runDataServer();
    }

    /**
     * exit server program
     */
    public void exit() {
        dataServer.exit();
        logger.info("User exit data server program");
        System.exit(1);
    }

    /**
     * Set up server address (ip, port)
     * @param ip
     * @return true if set successfully
     */
    public boolean setAddress(String ip) {
        return dataServer.setAddress(ip);
    }


    public Authenticator getAuthenticator(){
        return dataServer.getAuthenticator();
    }

    public DataServerApplication getDataServer() {
        return dataServer;
    }
    public void startServerApplication(){
        // Singleton server application.
        if (dataServer == null){
            dataServer = new DataServerApplication();
        }
        else{
            logger.fatal("Error. Server application is already on. ");
        }
    }
}
