package clientApp;

/**
 * Application layer facade of client
 * provide single control point of client application logic
 */
public class ClientAppFacade {
    /** private singleton instance */
    private static ClientAppFacade instance = null;

    /**
     * Private constructor
     */
    private ClientAppFacade() {}

    /**
     * get the singleton instance
     * @return singleton instance of ClientAppFacade
     */
    public static ClientAppFacade getInstance() {
        if (instance == null)
            instance = new ClientAppFacade();

        return instance;
    }


}
