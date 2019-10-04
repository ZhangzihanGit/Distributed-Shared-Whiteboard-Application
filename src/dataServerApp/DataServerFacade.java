package dataServerApp;

import org.apache.log4j.Logger;

import java.rmi.RemoteException;
// TODO: 问题找到了， static class在compile 的时候就已经fix了，runtime无法改变他本身的状态了。
public class DataServerFacade {
    private final static Logger logger = Logger.getLogger(DataServerFacade.class);

    /** private singleton instance */
    private static DataServerFacade instance = null;

    private DataServerApplication dataServer = null;

    /**
     * Private constructor
     */
    private DataServerFacade() {
//        dataServer = new DataServerApplication(this);
//        if (instance!=null){
//            throw new RuntimeException("Use getInstance method to get the class");
//        }
    }

    /**
     * get the singleton instance
     * @return singleton instance of DataServerFacade
     */
    public static DataServerFacade getInstance() {
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
        System.out.println("Data server from Facade: 1"+dataServer);
        return dataServer.getAuthenticator();
    }

    public DataServerApplication getDataServer() {
        System.out.println("FACADE GET DATA SERVER : : : : "+this.dataServer);
        return this.dataServer;
    }
    public void createServerApplication(){
        // Singleton server application.
        if (this.dataServer == null){
            System.out.println("Data server from Facade: 3   "+dataServer);
            this.dataServer = new DataServerApplication(getInstance());
            System.out.println("Data server from Facade: ~~~~~"+dataServer);
            dataServer.setRemoteDb(this);
            System.out.println("Data server from Facade: !!!"+dataServer);

        }
        else{
            logger.fatal("Error. Server application is already on. ");
        }
    }
}
