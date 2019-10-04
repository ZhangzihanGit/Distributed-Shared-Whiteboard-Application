import dataServerApp.IRemoteDb;
import dataServerApp.UserInformation;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Work as a web server(mock the behaviours of the client side).
public class DbTest {
    public static void main(String[] args){
        IRemoteDb test = null;
        try{


            // Test the connection with DB server.
           Registry registry = LocateRegistry.getRegistry("localhost");
           test = (IRemoteDb) registry.lookup("DB");
           String username = "abc";
           String password = "abcde";
           JSONObject message = new JSONObject();
           message.put("abc","abcde");

           // Testt if Register works(use Authenticator class and RemoteDB class)
           JSONObject jsonObject = test.addUser(username, password, message);
           if(jsonObject instanceof JSONObject){
               System.out.println(jsonObject);
               System.out.println(jsonObject.toString());
               System.out.println(jsonObject.toJSONString());
           }

           // Test the use of UserInformation Class.
           UserInformation userInformation = null;
           userInformation = test.transferInformation(username, password);
           System.out.println(userInformation.toString());

           // Test if RemoteDB.checkUser (Authenticator.authenticate) works.
            String mockUsername = "hello";
            String mockPassword = "world";
            System.out.println(test.checkUser(mockUsername,mockPassword));

            String wrongPassword = "nihao";
            System.out.println(test.checkUser(username,wrongPassword));


       }catch (RemoteException e){
           e.printStackTrace();
       }catch (Exception e){
           e.printStackTrace();
        }
    }
}
