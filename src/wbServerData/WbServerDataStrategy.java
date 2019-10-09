package wbServerData;

/**
 * Whiteboard server data packing strategy interface
 */
public interface WbServerDataStrategy {
    /**
     * Pack the respond to client
     * @param isSuccess True if the operation succeed, Boolean
     * @param msg Appended message, String
     * @return Respond, String
     */
    public String packRespond(Boolean isSuccess, String msg);
}
