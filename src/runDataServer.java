import dataServerApp.DataServerApplication;
import dataServerApp.DataServerCmdValue;
import dataServerApp.DataServerFacade;

import java.net.SocketException;

public class runDataServer {
    public static void main(String[] args) {
        // log setting
        System.setProperty("my.log", "resources/log/dataServer.log");
        // security settings
        System.setProperty("java.security.policy", "file:./security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        DataServerCmdValue cmdValidator = new DataServerCmdValue(args);
        if (cmdValidator.isErrorFree()) {
            // rmi setting
            System.setProperty("java.rmi.server.hostname", cmdValidator.getServerIP());

            DataServerFacade facade = DataServerFacade.getInstance();
            facade.setupRemoteApplication(); // Start DB server. Meanwhile remote object is created.

            DataServerApplication application = facade.getDataServer();

            application.setAddress(cmdValidator.getServerPort());
            application.runDataServer();
        }

        boolean t1 = isFileNameValid("abc");
        System.out.println(t1);

        boolean t2 = isFileNameValid("````*");
        System.out.println(t2);
        boolean t3 = isFileNameValid("//\\");
        System.out.println(t3);
        boolean t4 = isFileNameValid("&8a/");
        System.out.println(t4);
    }

    private static boolean isFileNameValid(String fileName) {

        char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

        if (fileName == null || fileName.length() > 255) {
            return false;
        } else {

            for (int i = 0; i < ILLEGAL_CHARACTERS.length; i ++) {
                if (fileName.contains(Character.toString(ILLEGAL_CHARACTERS[i]))) {
                    return false;
                }
            }
        }
        return true;
    }
}
