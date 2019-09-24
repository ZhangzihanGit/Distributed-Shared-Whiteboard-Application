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

    public Authenticator(String username, String password){
//        this.username = username;
//        this.password = password;
    }
    public JSONObject registerUser(String username, String password){
        if (password.contains(username)){
            return jsonParse(FAIL_HEADER, USER_REGISTER_DUPLICATION);
        }
        else {
            passbook.put(username, password);
            return jsonParse(SUCCESS_HEADER, USER_REGISTER_SUCCESS);
        }
    }

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
    @SuppressWarnings("unchecked")
    private JSONObject jsonParse(String header, String message){
        JSONObject object = new JSONObject();
        object.put(header, message);
        return object;
    }


}
