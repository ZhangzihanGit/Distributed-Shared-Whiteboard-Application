package clientApp;

/**
 * Application layer facade of client
 * provide single control point of client application logic
 */
public class ClientAppFacade {
    /** private singleton instance */
    private ClientAppFacade instance = null;

    /**
     * Private constructor
     */
    private ClientAppFacade() {}

    /**
     * get the singleton instance
     * @return singleton instance of ClientAppFacade
     */
    public ClientAppFacade getInstance() {
        if (instance == null)
            instance = new ClientAppFacade();

        return instance;
    }


}
