package dataServerApp;

import java.util.HashMap;
import org.json.simple.*;
public class Authenticator {
    private HashMap<String, String> passbook = null;
//    private String username = null;
//    private String password = null;

    private static String USER_NOT_FOUND = "User not found in passbook";
    private static String AUTHENTICATION_FAILED = "Authentication failed";
    private static String USER_REGISTER_DUPLICATION = "User duplication";
    private static String USER_REGISTER_SUCCESS = "User register success";
    private static String USER_AUTHENTICATION_SUCCESS = "User authentication success";
    private static String FAIL_HEADER = "Fail";
    private static String SUCCESS_HEADER = "Success";

    private static String USER_NULL= "User name not provided";
    private static String PASSWORD_NULLL= "Password not provided";


    public Authenticator(String username, String password){
        passbook = new HashMap<String, String>();
//        this.username = username;
//        this.password = password;
    }

    /**
     * Register new user. Check if the username or password null first.
     * @param username  New username that the user wants to add.
     * @param password  New password that the user wants to add.
     * @return
     */
    public JSONObject registerUser(String username, String password){
        if username == null{
            return jsonParse(FAIL_HEADER, USER_NULL);
        }
        if password == null {
            return jsonParse(FAIL_HEADER, PASSWORD_NULLL);
        }
        if (password.contains(username)){
            return jsonParse(FAIL_HEADER, USER_REGISTER_DUPLICATION);
        }
        else {
            passbook.put(username, password);
            return jsonParse(SUCCESS_HEADER, USER_REGISTER_SUCCESS);
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
        if (!password.contains(username)){
            return  jsonParse(FAIL_HEADER, USER_NOT_FOUND);
        }
        // If the password under the user name is not the same as that in passbook
        else if(!passbook.get(username).equals(password)){
            return  jsonParse(FAIL_HEADER, AUTHENTICATION_FAILED);
        }
        // Successfully authenticated
        else {
            return jsonParse(SUCCESS_HEADER,USER_AUTHENTICATION_SUCCESS);
        }
    }

    private JSONObject jsonParse(String header, String message){
        JSONObject object = new JSONObject();
        object.put(header, message);
        return object;
    }


}
