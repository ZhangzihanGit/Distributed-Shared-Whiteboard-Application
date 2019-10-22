package wbServerData;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Publish method for whiteboard server to publish updates to clients
 */
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
     * @param retained Whehter retained the message
     * @return True if publish successfully
     */
    public boolean publish(MqttClient publisher, String topic, String content, boolean retained) {
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(QOS);
            message.setPayload(content.getBytes());
            message.setRetained(retained);

            publisher.publish(topic, message);
            logger.info("Message " + content + " published successfully on topic: " + topic);
            return true;
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Message " + content + " published failed on topic: " + topic);
            return false;
        }
    }
}
