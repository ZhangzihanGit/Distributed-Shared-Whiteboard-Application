package wbServerData;

/**
 * Singleton factory to obtain different data strategy implementation
 */
public class WbServerDataStrategyFactory {
    /** singleton instance */
    private static WbServerDataStrategyFactory instance = null;
    /** JSON strategy */
    private WbServerJsonDataStrategy json = null;
    /** MQTT publish */
    private WbServerMqttPublish mqttPublish = null;

    /**
     * Constructor
     */
    private WbServerDataStrategyFactory() {
        json = new WbServerJsonDataStrategy();
        mqttPublish = new WbServerMqttPublish();
    }

    /**
     * Get the singleton instance of factory
     * @return Singleton instance of factory
     */
    public static WbServerDataStrategyFactory getInstance() {
        if (instance == null)
            instance = new WbServerDataStrategyFactory();

        return instance;
    }

    /**
     * Get JSON implementation data strategy
     * @return JSON strategy
     */
    public WbServerDataStrategy getJsonStrategy() {
        return json;
    }

    /**
     * Get Mqtt publish implementation
     * @return WbServerMqttPublish
     */
    public WbServerMqttPublish getMqttPublish() {
        return this.mqttPublish;
    }
}
