package wbServerApp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataServerApp.IRemoteDb;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import wbServerData.WbServerDataStrategyFactory;

public class WbServerApplication {
    private final static Logger logger = Logger.getLogger(WbServerApplication.class);

    private IRemoteWb remoteWb = null;
    private IRemoteDb remoteDb = null;

    private Process mosquittoProc = null;
    MqttClient mqttPublisher = null;

    private ArrayList<Whiteboard> whiteboards = new ArrayList<>();

    /**
     * constructor
     */
    public WbServerApplication() {
        try {
            remoteWb = new RemoteWb();
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Initialization whiteboard remote object failed");

            this.exit();
        }
    }

    /**
     * start run server at localhost
     * @param port port, String
     */
    public void runWbServer(String port) {
        // parameter checking
        int portNum = 1111;
        try {
            portNum = Integer.parseInt(port);
        } catch (Exception e) {
            logger.warn(e.toString());
            logger.warn("port specified not valid, use default port number 1111");
        }

        try {
            Registry registry = LocateRegistry.createRegistry(portNum);
            registry.rebind("Whiteboard", remoteWb);

            logger.info("Whiteboard server start running (by RMI) at port: " + portNum);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Whiteboard remote registry set up failed");
            this.exit();
        }
    }

    /**
     * Connect to data server
     * @param ip IP address, String
     * @param port port, String
     */
    public void connectDbServer(String ip, String port) {
        // parameter checking
        int portNum = 1111;
        try {
            portNum = Integer.parseInt(port);
        } catch (Exception e) {
            logger.warn(e.toString());
            logger.warn("port specified not valid, use default port number 1111");
        }

        try {
            //Connect to the rmiregistry that is running on localhost
            Registry registry = LocateRegistry.getRegistry(ip, portNum);

            //Retrieve the stub/proxy for the remote math object from the registry
            remoteDb = (IRemoteDb) registry.lookup("DB");

            logger.info("connect to data server at ip: " + ip + ", port: " + portNum);
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Obtain remote service from database server(" + ip + ") failed");
            this.exit();
        }
    }

    /**
     * Create new subprocess to start mosquitto broker
     * @param port Port
     */
    public void startBroker(String port) {
        String[] cmd = new String[] {"/bin/bash", "-c", "/usr/local/sbin/mosquitto"};
        String broker = "tcp://localhost:1883";

        if (port != null && !port.equals("")) {
            cmd = new String[] {"/bin/bash", "-c", "/usr/local/sbin/mosquitto", "-p", port};
            broker = "tcp://localhost:" + port;
        }

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            this.mosquittoProc = new ProcessBuilder(cmd).start();

            this.mqttPublisher  = new MqttClient(broker, "wbServer", persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            connOpts.setConnectionTimeout(10);

            logger.info("Connecting to broker: " + broker);
            mqttPublisher.connect(connOpts);
            logger.info("Connected to broker successfully");
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Create process to start broker and connect to it failed");

            this.exit();
        }
    }

    /**
     * Register new users
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from data server, String
     */
    public String register(String username, String password) {
        try {
            return remoteDb.addUser(username, password);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("New user register service from data server fail to execute");
            return "";
        }
    }

    /**
     * Existing user login authentication
     * @param username Username, String
     * @param password Password, String
     * @return JSON respond from data server, String
     */
    public String login(String username, String password) {
        try {
            return remoteDb.checkUser(username, password);
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Existing user login authentication service from data server fail to execute");
            return "";
        }
    }

    /**
     * Create new whiteboard and set the user to be the manager
     * @param wbName Name of whiteboard, String
     * @param username Username, String
     * @return JSON respond, String
     */
    public synchronized String createWb(String wbName, String username) {
        // check whether the same whiteboard is already created
        for (Whiteboard wb: whiteboards) {
            if (wb.getName().equals(wbName)) {
                return WbServerDataStrategyFactory.getInstance().getJsonStrategy().packRespond(false,
                        "There is one manager already sign up on this server, please join in");
            }
        }

        whiteboards.add(new Whiteboard(wbName, username));

        return WbServerDataStrategyFactory.getInstance().getJsonStrategy().packRespond(true, "");
    }

    /**
     * join created whiteboard on server
     * @param wbName Name of whiteboard, String
     * @param username Username, String
     * @return JSON respond, String
     */
    public synchronized String joinWb(String wbName, String username) {
        for (Whiteboard wb: whiteboards) {
            if (wb.getName().equals(wbName)) {
                wb.addUser(username);
                return WbServerDataStrategyFactory.getInstance().getJsonStrategy().packRespond(true, "");
            }
        }

        return WbServerDataStrategyFactory.getInstance().getJsonStrategy().packRespond(false,
                "No manager has signed up on this server, please sign up as manager first");
    }

    /**
     * Get the name of all created whiteboards
     * @return JSON response, String
     */
    public String getCreatedWb() {
        String msg = "";

        for (Whiteboard wb: whiteboards) {
            msg += wb.getName() + ",";
        }

        return WbServerDataStrategyFactory.getInstance().getJsonStrategy().packRespond(false,
                msg);
    }

    /**
     * exit server program
     */
    public void exit() {
        try {
            UnicastRemoteObject.unexportObject(remoteWb, false);

            if (this.mosquittoProc != null) {
                mosquittoProc.destroy();
            }
            if (this.mqttPublisher != null) {
                mqttPublisher.disconnect();
            }
        } catch (Exception e) {
            logger.fatal(e.toString());
            logger.fatal("Whiteboard server remove remote object from rmi runtime failed");
        }

        System.exit(1);
    }
}
