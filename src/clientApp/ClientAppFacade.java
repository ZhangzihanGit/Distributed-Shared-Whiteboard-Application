package clientApp;

import org.apache.log4j.Logger;

/**
 * Application layer facade of client
 * provide single control point of client application logic
 */
public class ClientAppFacade {
    private final static Logger logger = Logger.getLogger(ClientAppFacade.class);

    /** private singleton instance */
    private static ClientAppFacade instance = null;

    private ClientApplication clientApp = null;

    /**
     * Private constructor
     */
    private ClientAppFacade() {
        clientApp = new ClientApplication();
    }

    /**
     * get the singleton instance
     * @return singleton instance of ClientAppFacade
     */
    public static ClientAppFacade getInstance() {
        if (instance == null)
            instance = new ClientAppFacade();

        return instance;
    }

    /**
     * exit client program
     */
    public void exit() {
        logger.info("User exit client program");
        System.exit(1);
    }
}
