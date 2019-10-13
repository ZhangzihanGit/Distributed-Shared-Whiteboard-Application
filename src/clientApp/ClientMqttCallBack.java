package clientApp;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClientMqttCallBack implements MqttCallback {
    private final static Logger logger = Logger.getLogger(ClientMqttCallBack.class);

    @Override
    public void connectionLost(Throwable throwable) {
        logger.error("Connection to MQTT broker lost");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        // TODO: Deal with arrived msg
        System.out.println("Message received: "+ new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // currently not used
    }
}
