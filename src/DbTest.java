import dataServerApp.DataServerApplication;
import dataServerApp.IRemoteDb;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
//           testAuthentication(test);
//           testSaveCanvas(test);
//           testRetrieveCanvas(test);
//           testCypher();
//           testCreateDirectory();
           testSavetoDB();

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
        String jsonString = test.addUser(username, password);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;

        //Read JSON requst
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (Exception e) {
            // TODO: Exception catching
        }

        if(jsonObject != null && jsonObject instanceof JSONObject){
            System.out.println(jsonObject);
            System.out.println(jsonObject.toString());
            System.out.println(jsonObject.toJSONString());
        }

        // Test the use of UserInformation Class.

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
//        test.saveWb(username,password);
    }
//    private static void testRetrieveCanvas(IRemoteDb test) throws RemoteException{
//        String manager = "world";
//        String password = "world";
//        test.loadAllWb(manager);
//    }
//    private static void testCypher(){
//        Cipher cypher = new Cipher("hello");
//    }
    private static void testCreateDirectory(){
    }
    private static void testSavetoDB(){
        DataServerApplication server = new DataServerApplication();
//        server.addUser("hello9", "hello");
//        server.checkUser("hello4","NInygbToaAiN/gKaJtqzbg==");
        server.checkUser("hello9", "hello");
        server.addUser("hello","hello");
        server.checkUser("hello","hell1");
//        dataWareHouse.createDirectory();


    }
}
