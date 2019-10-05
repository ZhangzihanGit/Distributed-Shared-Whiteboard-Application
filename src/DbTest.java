import dataServerApp.IRemoteDb;
import dataServerApp.UserInformation;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Work as a web server(mock the behaviours of the client side).
 * This is for local testing purpose only.
 */
public class DbTest {
    private static Registry registry = null;

    public static void main(String[] args){
        IRemoteDb test = null;
        try{
            // Test the connection with DB server.
           registry = LocateRegistry.getRegistry("localhost");
           test = (IRemoteDb) registry.lookup("DB");
           testAuthentication(test);
           testSaveCanvas(test);
           testRetrieveCanvas(test);

       }catch (RemoteException e){
           e.printStackTrace();
       }catch (Exception e){
           e.printStackTrace();
        }
    }

    private static void testAuthentication(IRemoteDb test) throws RemoteException{
        String username = "abc";
        String password = "abcde";
        JSONObject message = new JSONObject();
        message.put("abc","abcde");

        // Test if Register works(use Authenticator class and RemoteDB class)
        JSONObject jsonObject = test.addUser(username, password);
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
        System.out.println(test.checkUser(mockUsername,mockPassword) + "1111");
        assert (test.checkUser(mockUsername,mockPassword).equals("{\"header\":\"Fail\",\"message\":\"User not " +
                "found in passbook\"}"));

        String wrongPassword = "nihao";
        System.out.println(test.checkUser(username,wrongPassword) + "222");
        assert (test.checkUser(username,wrongPassword).equals("{\"header\":\"Fail\",\"message\":\"Authentication failed\"}"));

        System.out.println(test.checkUser(username,password)+"333");
        assert (test.checkUser(username, password).equals("{\"header\":\"Success\",\"message\":\"User authentication success\"}"));

    }
    private static void testSaveCanvas(IRemoteDb test) throws RemoteException{
        String username = "world";
        String password = "world";
        test.saveWb(username,password);
    }
    private static void testRetrieveCanvas(IRemoteDb test) throws RemoteException{
        String manager = "world";
        String password = "world";
        test.loadAllWb(manager);
    }
}
