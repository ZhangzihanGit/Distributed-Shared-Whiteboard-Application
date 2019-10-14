package wbServerApp;

import java.util.ArrayList;

/**
 * Whiteboard class that stores all information related to this whiteboard
 */
public class Whiteboard {
    private String name = null;
    private String manager = null;
    private ArrayList<String> users = new ArrayList<>();

    /**
     * Constructor
     * @param name Name of whiteboard
     * @param manager Manager of this whiteboard
     */
    public Whiteboard(String name, String manager) {
        this.name = name;
        this.manager = manager;
    }

    // getter and setter
    public String getName() {
        return this.name;
    }

    public String getManager() {
        return this.manager;
    }

    public String getAllUsers() {
        String result = manager;

        for (String user: users) {
            result += "," + user;
        }

        return result;
    }

    public void addUser(String user) {
        this.users.add(user);
    }

    public void removeUser(String user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }
    }
}
