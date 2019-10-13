package wbServerData;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class WbServerMqttPublish {
    /** logger */
    private final static Logger logger = Logger.getLogger(WbServerMqttPublish.class);
    /** Quality of service code, 0: at most once. 1: at least once. 2: exactly once */
    private static final int QOS = 2;

    /**
     * Publish a content to specific topic
     * @param publisher The publisher
     * @param topic Topic that this content is related
     * @param content Content
     * @return True if publish successfully
     */
    public boolean publish(MqttClient publisher, String topic, String content) {
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(QOS);
            message.setPayload(content.getBytes());
            message.setRetained(true);

            publisher.publish(topic, message);
            logger.info("Message published successfully");
            return true;
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Message published failed");
            return false;
        }
    }
}
