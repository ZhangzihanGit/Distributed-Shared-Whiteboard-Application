import dataServerApp.DataServerApplication;
import dataServerApp.DataServerFacade;

public class runDataServer {
    public static void main(String[] args) {
        System.setProperty("my.log", "resources/log/dataServer.log");

        DataServerFacade facade = DataServerFacade.getInstance();
        facade.setupRemoteApplication(); // Start DB server. Meanwhile remote object is created.

        DataServerApplication application = facade.getDataServer();

        try {
            application.setAddress(args[0]);
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Invalid command line argument, Usage: ");
            System.err.println("java -jar dataServer <portNum>");
            System.exit(1);
        }

        application.runDataServer();
    }
}
