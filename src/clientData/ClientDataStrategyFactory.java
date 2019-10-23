package clientData;

/**
 * Factory from obtain specific data handling strategy
 */
public class ClientDataStrategyFactory {
    /** singleton instance */
    private static ClientDataStrategyFactory instance = null;
    /** JSON strategy */
    private ClientJsonDataStrategy jsonStrategy;

    /**
     * Constructor
     */
    private ClientDataStrategyFactory() {
        jsonStrategy = new ClientJsonDataStrategy();
    }

    /**
     * Get the instance of singleton factory
     * @return Singleton instance of factory
     */
    public static ClientDataStrategyFactory getInstance() {
        if (instance == null)
            instance = new ClientDataStrategyFactory();

        return instance;
    }

    /**
     * Get data strategy to handel JSON format message
     * @return JSON format message handling strategy
     */
    public ClientDataStrategy getJsonStrategy() {
        return jsonStrategy;
    }
}
