package dataServerApp;

import org.apache.log4j.Logger;

import java.io.Serializable;

public class UserInformation implements Serializable{
    // 很不幸， logger不是serializable.  
//    private Logger logger = Logger.getLogger(UserInformation.class);


    private String username = null;
    private String password = null;
    private boolean isManager = false;  // Default set to false.

    public UserInformation(String username, String password, boolean isManager) {
        if(username == null || password == null){
            System.out.println("ERROR");
//            logger.fatal("User Information initialisation failed. Username or password is not provided. ");
        }
        this.username = username;
        this.password = password;
        this.isManager = isManager;
    }
    @Override
    public String toString(){

        return "User name : "+username+" passowrd: "+password+" " + isManager;
    }
}
