package clientApp;

import clientData.ClientDataStrategyFactory;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import wbServerApp.IRemoteWb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApplication {
    private final static Logger logger = Logger.getLogger(ClientApplication.class);

    private IRemoteWb remoteWb = null;
    private String username = null;

    private MqttClient mqttSubscriber = null;

    /**
     * constructor
     */
    public ClientApplication() {}

    /**
     * Connect to whiteboard server
     * @oaram ip IP address, String
     * @param port port, String
     * @return True if connect successfully
     */
    public boolean connectWbServer(String ip, String port) {
        // parameter checking
        int portNum = 1111;
        try {
            portNum = Integer.parseInt(port);
        } catch (Exception e) {
            logger.warn(e.toString());
            logger.warn("port number specified not valid, use default port number 1111");
        }

        String ipAddr = "localhost";
        if (!ip.equals("")) {
            ipAddr = ip;
        }

        try {
            //Connect to the rmiregistry that is running on localhost
            Registry registry = LocateRegistry.getRegistry(ipAddr, portNum);

            //Retrieve the stub/proxy for the remote math object from the registry
            remoteWb = (IRemoteWb) registry.lookup("Whiteboard");

            logger.info("connect to server at ip: " + ipAddr + ", port: " + portNum);
            return true;
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Obtain remote service from whiteboard server(" + ipAddr + ", " + portNum + ") failed");
            return false;
        }
    }

    /**
     * Connect to a remote broker
     * @param ip Ip address
     * @param port Port
     * @return True if connect successfully
     */
    public boolean connectBroker(String ip, String port) {
        String broker = "tcp://localhost:1883";
        MemoryPersistence persistence = new MemoryPersistence();

        if (ip != null && !ip.equals("")) {
            broker = "tcp://" + ip + ":1883";
            if (port != null && !port.equals("")) {
                broker = "tcp://" + ip + ":" + port;
            }
        }

        try {
            this.mqttSubscriber = new MqttClient(broker, MqttClient.generateClientId(), persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            connOpts.setConnectionTimeout(10);

            logger.info("Connecting to broker: " + broker);
            this.mqttSubscriber.connect(connOpts);
            logger.info("Connected to broker successfully");

            this.mqttSubscriber.setCallback(new ClientMqttCallBack());
            return true;
        } catch(Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Connect to remote broker failed");
            return false;
        }
    }

    /**
     * Let this client subscribe to a specific topic
     * @param topic Topic that this client will subscribe
     * @return True if subscribe successfully
     */
    public boolean subscribeTopic(String topic) {
        if (this.mqttSubscriber == null) {
            return false;
        }

        try {
            this.mqttSubscriber.subscribe(topic);
            logger.info("Subscribe to topic: " + topic + " successfully");
            return true;
        } catch(Exception e) {
            logger.error(e.toString());
            logger.error("Subscribe topic: " + topic + " failed");
            return false;
        }
    }

    /**
     * Register new user on server
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from server, String
     */
    public String register(String username, String password) {
        try {
            return remoteWb.register(username, password);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Register new users service from whiteboard server fail to execute");
            return "";
        }
    }

    /**
     * Existing user log in authentication
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from server, String
     */
    public String login(String username, String password) {
        try {
            return remoteWb.login(username, password);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Existing user login service from whiteboard server fail to execute");
            return "";
        }
    }

    /**
     * Create new whiteboard and set the user to be the manager
     * @param wbName Name of whiteboard, String
     * @return JSON response from server, String
     */
    public String createWb(String wbName) {
        try {
            return remoteWb.createWb(wbName, this.getUsername());
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Create whiteboard service from whiteboard server fail to execute");
            return "";
        }
    }

    /**
     * Join whiteboard on server
     * @param wbName Name of whiteboard, String
     * @return JSON response from server, String
     */
    public String joinWb(String wbName) {
        try {
            return remoteWb.joinWb(wbName, this.getUsername());
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Join whiteboard service from whiteboard server fail to execute");
            return "";
        }
    }

    /**
     * Get the name of all created whiteboards
     * @return JSON response from server, String
     */
    public String getCreatedWb() {
        try {
            return remoteWb.getCreatedWb();
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Get all created whiteboard names service from whiteboard server fail to execute");
            return "";
        }
    }

    /**
     * Close specific whiteboard
     * @param wbID Whiteboard id
     * @param username Username
     * @return Closing feedback
     */
    public String closeWb(String wbID, String username) {
        try {
            return remoteWb.closeWb(wbID, username);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Close whiteboard service from whiteboard server fail to execute");
            return "[ERROR]: Close whiteboard service from whiteboard server fail to execute";
        }
    }

    /**
     * Save specific whiteboard online
     * @param wbID Whiteboard id
     * @param username Username
     * @return Saving feedback
     */
    public String saveWbOnline(String wbID, String username) {
        try {
            return remoteWb.saveWbOnline(wbID, username);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Save whiteboard online service from whiteboard server fail to execute");
            return "[ERROR]: Save whiteboard online service from whiteboard server fail to execute";
        }
    }

    /**
     * Get all online-stored whiteboard files for a specific user
     * @param username Username
     * @return All whiteboard files
     */
    public String getAllStoredFiles(String username) {
        try {
            return remoteWb.getAllStoredFiles(username);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Get all online stored whiteboard files service from whiteboard server fail to execute");
            return "[ERROR]: Get all online stored whiteboard files service from whiteboard server fail to execute";
        }
    }

    /**
     * Open specific online-stored whiteboard
     * @param wbID Whiteboard id
     * @param username Username
     * @return Open feedback
     */
    public String openWbOnline(String wbID, String username) {
        try {
            return remoteWb.openWbOnline(wbID, username);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Open online stored whiteboard service from whiteboard server fail to execute");
            return "[ERROR]: Open online stored whiteboard service from whiteboard server fail to execute";
        }
    }

    /**
     * Open specific locally-stored whiteboard
     * @param username Username
     * @param wbContent Whiteboard content
     * @return Open feedback
     */
    public String openWbLocally(String username, String wbContent) {
        try {
            return remoteWb.openWbLocally(username, wbContent);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Open locally stored whiteboard service from whiteboard server fail to execute");
            return "[ERROR]: Open locally stored whiteboard service from whiteboard server fail to execute";
        }
    }

    /**
     * Render all the whiteboards
     * @param wbID Whiteboard id
     * @param username Username
     * @return Whiteboard content
     */
    public String render(String wbID, String username) {
        try {
            return remoteWb.render(wbID, username);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Render distributed whiteboard service from whiteboard server fail to execute");
            return "[ERROR]: Render distributed whiteboard service from whiteboard server fail to execute";
        }
    }

    /**
     * Draw diagram
     * @param wbID Whiteboard id
     * @param username Username
     * @param content Drawing content
     * @return Drawing feedback
     */
    public String draw(String wbID, String username, String content) {
        try {
            return remoteWb.draw(wbID, username, content);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Draw distributed whiteboard service from whiteboard server fail to execute");
            return "[ERROR]: Draw distributed whiteboard service from whiteboard server fail to execute";
        }
    }

    /**
     * Send message
     * @param wbID Whiteboard id
     * @param username Username
     * @param msg Message
     * @return Sending feeback
     */
    public String sendMsg(String wbID, String username, String msg) {
        try {
            return remoteWb.sendMsg(wbID, username, msg);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Send message to whiteboard service from whiteboard server fail to execute");
            return "[ERROR]: Send message to whiteboard service from whiteboard server fail to execute";
        }
    }

    /**
     * Resolve the header of JSON respond from server
     * @param respond JSON respond from server, String
     * @return True if the header stores success, Boolean
     */
    public Boolean getHeader(String respond) {
        return ClientDataStrategyFactory.getInstance().getJsonStrategy().getHeader(respond);
    }

    /**
     * Resolve the message appended in the JSON respond from server
     * @param respond JSON respond from server, String
     * @return Message appended in the respond, String
     */
    public String getMsg(String respond) {
        return ClientDataStrategyFactory.getInstance().getJsonStrategy().getMsg(respond);
    }

    // getter and setter
    /**
     * Set the username of current client
     * @param username Username, String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the username of current client
     * @return Username, String
     */
    public String getUsername() {
        if (this.username != null) {
            return this.username;
        }
        else {
            return "User";
        }
    }

    /**
     * Exit client program
     */
    public void exit() {
        try {
            if (this.mqttSubscriber != null) {
                this.mqttSubscriber.disconnect();
            }
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Disconnect with remote broker failed");
        }

        System.exit(1);
    }
}
