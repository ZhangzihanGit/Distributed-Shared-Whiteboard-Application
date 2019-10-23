package dataServerApp;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Authenticator {
    private final static Logger logger = Logger.getLogger(Authenticator.class);

    private HashMap<String, String[]> passbook = null;
//    private String username = null;
//    private String password = null;

    private static String USER_NOT_FOUND = "User not found in passbook";
    private static String AUTHENTICATION_FAILED = "Authentication failed";
    private static String USER_REGISTER_DUPLICATION = "User duplication";
    private static String USER_REGISTER_SUCCESS = "User register success";
    private static String USER_AUTHENTICATION_SUCCESS = "User authentication success";
    private static String USER_NULL= "User name not provided";
    private static String PASSWORD_NULLL= "Password not provided";

    public static String FAIL_HEADER = "Fail";
    public static String SUCCESS_HEADER = "Success";

    // private Singleton instance.
    private static Authenticator authenticator = null;
    private Cipher cipher;
    private ArrayList loggedInUser = null;
    // Authenticator should be a singleton, since passbook should be kept unique.
    private Authenticator(){
        this.passbook = new HashMap<String, String[]>();
        this.cipher = Cipher.getInstance();
        this.loggedInUser = new ArrayList<String>();
        logger.info("Cipher created: "+this.cipher);
    }
    public static Authenticator getInstance(){
        if (authenticator == null){
            authenticator = new Authenticator();
        }
        return authenticator;
    }
    public void syncStorage(HashMap<String, String[]> localStorage){
        HashMap <String, String[]>temp = new HashMap(localStorage);
        temp.keySet().removeAll(this.passbook.keySet());
        this.passbook.putAll(temp);
//        this.passbook = localStorage;
    }
    private String encryptPassword(String password){
        return cipher.encrypt(password);
    }

    /**
     * Register new user. Check if the username or password null first.
     * @param username  New username that the user wants to add.
     * @param password  New password that the user wants to add.
     * @return
     */
    public JSONObject registerUser(String username, String password){
        if (username == null){
            logger.info("The user has not provided the username. ");
            return jsonParse(FAIL_HEADER, USER_NULL,"","");
        }
        if (password == null) {
            logger.info("The user has not provided the password.");
            return jsonParse(FAIL_HEADER, PASSWORD_NULLL,"","");
        }
        if (passbook.containsKey(username)){
            logger.info("The username entered has already been used by others. Fail to register the username!!");
            return jsonParse(FAIL_HEADER, USER_REGISTER_DUPLICATION,"","");
        }
        else {
            // Put encrypted password.
            String[] saltAndPasssalt = cipher.getSaltAndPassSalt(password);
            String salt = saltAndPasssalt[1];
            String passhash = saltAndPasssalt[0];
            passbook.put(username,saltAndPasssalt);
            loggedInUser.add(username);
            logger.info("Successfully registered. Password has been encoded. ");
            return jsonParse(SUCCESS_HEADER, USER_REGISTER_SUCCESS,passhash,salt);
        }
    }

    /**
     * Authenticate the user login. Check if the user exist first, then check if the
     * username and password are previously stored in the passbook.
     * @param username  New username that the user wants to add.
     * @param password  New password that the user wants to add.
     * @return
     */
    public JSONObject authenticate(String username, String password){
        // If passbook not contain the user name

        if(passbook.containsKey(username)){
            String passsalt = (passbook.get(username))[0];
            String salt = (passbook.get(username))[1];
            logger.info("PASSSALT is: "+passsalt+"SALT IS: "+salt);
            if(!cipher.isExpectedPassword(password,salt,passsalt)){
                logger.info("The password or the username entered are INCORRECT. Please check the username or password. ");
                return  jsonParse(FAIL_HEADER, AUTHENTICATION_FAILED,"","");
            }
            // Successfully authenticated
            else {
                logger.info("Successfully : !!!!!");
                if(!loggedInUser.contains(username)){
                    loggedInUser.add(username);
                    return jsonParse(SUCCESS_HEADER,USER_AUTHENTICATION_SUCCESS,"","");
                }
                else {
                    logger.info("You shoudl not enter here. Duplicate user. ");
                    return jsonParse(FAIL_HEADER,AUTHENTICATION_FAILED,"","");
                }
            }
        }
        else {
            logger.info("There is no such user exist in our database. ");
            return  jsonParse(FAIL_HEADER, USER_NOT_FOUND,"","");
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject jsonParse(String header, String message,String encodedPassword, String salt){
        JSONObject object = new JSONObject();
        object.put("header", header);
        object.put("message", message);
        object.put("encodedhash",encodedPassword);
        object.put("salt",salt);
        return object;
    }

    // This method is for testing purpose.
    public void iteratePassbook(){
        Iterator it = passbook.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            logger.info(pair.getKey().toString()+pair.getValue().toString());
        }
    }
}
