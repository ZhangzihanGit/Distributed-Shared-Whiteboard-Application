package dataServerApp;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class is ideally used for information trasferring between data server
 * and web server. Whether or not use it will be under discussion. (Either use plain string that has json format or
 * use serialisable obejct to transfer information.
 */
public class UserInformation implements Serializable{
//    private Logger logger = Logger.getLogger(UserInformation.class);


    private String username = null;
    private String password = null;
    private boolean isManager = false;  // Default set to false.

    private HashMap <String, ArrayList<Integer>> drawing;

    public UserInformation(String username, String password, boolean isManager) {
        if(username == null || password == null){
            System.out.println("ERROR");
//            logger.fatal("User Information initialisation failed. Username or password is not provided. ");
        }
        this.username = username;
        this.password = password;
        this.isManager = isManager;
    }
    public void updateDrawing(HashMap<String, ArrayList<Integer>> drawing){
        this.drawing = drawing;
    }


    @Override
    public String toString(){

        return "User name : "+username+" passowrd: "+password+" " + isManager;
    }
}
