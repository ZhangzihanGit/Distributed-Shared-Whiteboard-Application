package dataServerApp;

import org.apache.log4j.Logger;

public class DataServerFacade {
    private final static Logger logger = Logger.getLogger(DataServerFacade.class);

    /** private singleton instance */
    private static DataServerFacade instance = null;

    private DataServerApplication dataServer = null;

    /**
     * Private constructor
     */
    private DataServerFacade() {
        dataServer = new DataServerApplication();
    }

    /**
     * get the singleton instance
     * @return singleton instance of DataServerFacade
     */
    public static DataServerFacade getInstance() {
        if (instance == null)
            instance = new DataServerFacade();

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
     * @param port
     * @return true if set successfully
     */
    public boolean setAddress(String ip, int port) {
        return dataServer.setAddress(ip, port);
    }
}
