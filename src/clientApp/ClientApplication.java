package clientApp;

import clientData.ClientDataStrategyFactory;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import wbServerApp.IRemoteWb;
import wbServerData.WbServerDataStrategy;
import wbServerData.WbServerDataStrategyFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApplication {
    private final static Logger logger = Logger.getLogger(ClientApplication.class);

    private IRemoteWb remoteWb = null;
    private String username = null;
    private String wbName = null;
    private String ip = null;
    private boolean isManager = false;
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
    public String connectWbServer(String ip, String port) {
        // parameter checking
        WbServerDataStrategyFactory factory = WbServerDataStrategyFactory.getInstance();
        WbServerDataStrategy parser = factory.getJsonStrategy();
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
            return parser.packRespond(true,"Successfully connect to the web server at ip: "+ip+" port: "+port,
                    "Webser Connection", this.username);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Obtain remote service from whiteboard server(" + ipAddr + ", " + portNum + ") failed");
            return parser.packRespond(false, "Fail to connect to the web server at "+ ip+ "port: "+port,
            "Webser Connection", this.username);
        }
    }

    /**
     * Connect to a remote broker
     * @param ip Ip address
     * @param port Port
     * @return True if connect successfully
     */
    public String connectBroker(String ip, String port) {
        String broker = "tcp://localhost:1883";
        MemoryPersistence persistence = new MemoryPersistence();
        WbServerDataStrategyFactory factory = WbServerDataStrategyFactory.getInstance();
        WbServerDataStrategy parser = factory.getJsonStrategy();


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
            return parser.packRespond(true, "Successfully connect to the broker at port: "+port,"" +
                    "Broker connection",this.username);
//            return true;
        } catch(Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Connect to remote broker failed");
            return parser.packRespond(false, "Fail to connect to the broker at port: "+port, "Broker connection" +
                    "",this.username);
        }
    }

    /**
     * Let this client subscribe to a set of specific topics
     * @param wbName Name of whiteboard
     * @param subtopics Subtopics that this client will subscribe
     * @param qos Quality of services tags for each topic
     * @return True if subscribe successfully
     */
    public boolean subscribeTopic(String wbName, String[] subtopics, int[] qos) {
        if (this.mqttSubscriber == null || subtopics == null || qos == null) {
            return false;
        }

        // assembly topics
        String [] topics = new String[subtopics.length];
        for (int i = 0; i < subtopics.length; i++) {
            topics[i] = new String(wbName + "/" + subtopics[i]);
        }

        try {
            this.mqttSubscriber.subscribe(topics, qos);
            return true;
        } catch(Exception e) {
            logger.error(e.toString());
            logger.error("Subscribe topic: failed");
            return false;
        }
    }

    /**
     * Let this client unsubscribe a set of specific topics
     * @param wbName Name of whiteboard
     * @param subtopics Subtopics that this client will subscribe
     * @return True if subscribe successfully
     */
    public boolean unsubscribeTopic(String wbName, String[] subtopics) {
        if (this.mqttSubscriber == null || subtopics == null) {
            return false;
        }

        // assembly topics
        String [] topics = new String[subtopics.length];
        for (int i = 0; i < subtopics.length; i++) {
            topics[i] = new String(wbName + "/" + subtopics[i]);
        }

        try {
            this.mqttSubscriber.unsubscribe(topics);
            logger.info("Unsubscribe topics successfully");
            return true;
        } catch(Exception e) {
            logger.error(e.toString());
            logger.error("Unsubscribe topics: failed");
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
     * Update pending join request from the specific user
     * @param username Username
     * @param isAllow True is the join request is approved
     */
    public void allowJoin(String username, boolean isAllow) {
        try {
            remoteWb.allowJoin(username, isAllow);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Join whiteboard service from whiteboard server fail to execute");
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
     */
    public void closeWb() {
        try {
            this.unsubscribeTopic(this.getWbName(), ClientAppFacade.UserTopics);
            remoteWb.closeWb(this.wbName, this.username);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Close whiteboard service from whiteboard server fail to execute");
        }
    }

    /**
     * Kick out specific visitor
     * @param visitor Username of visitor
     */
    public void kickUser(String visitor) {
        try {
            remoteWb.kickUser(this.wbName, visitor);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Close whiteboard service from whiteboard server fail to execute");
        }
    }

    /**
     * Render all the whiteboards
     * @param wb Whiteboard, String
     */
    public void updateWb(String wb) {
        try {
            remoteWb.updateWb(this.wbName, this.username, wb);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Close whiteboard service from whiteboard server fail to execute");
        }
    }

    /**
     * Send message
     * @param msg Message, String
     */
    public void sendMsg(String msg) {
        try {
            remoteWb.sendMsg(this.wbName, this.username, msg);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Close whiteboard service from whiteboard server fail to execute");
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

    // services provided from data layer
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
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setWbName(String wbName) {
        this.wbName = wbName;
    }

    public String getWbName() { return this.wbName; }

    public String getIp() { return this.ip; }

    public void setIp(String ip) { this.ip = ip; }

    public boolean isManager() {
        return this.isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }
}
