package clientData;

/**
 * Strategy interface of data handling
 */
public interface ClientDataStrategy {
    /**
     * Resolve the header of respond from server of some specific format (JSON, XML...)
     * @param respond Respond from server, String
     * @return True if the header stores success, Boolean
     */
    public Boolean getHeader(String respond);

    /**
     * Resolve the message appended in the respond from server of some specific format (JSON, XML...)
     * @param respond Respond from server, String
     * @return Message appended in the respond, String
     */
    public String getMsg(String respond);

    /**
     * Resolve the category of the respond from server of some specific format (JSON, XML...)
     * @param respond Respond from server, String
     * @return Category of the respond, String
     */
    public String getCategory(String respond);

    /**
     * Resolve the specified user of the respond from server of some specific format (JSON, XML...)
     * @param respond Respond from server, String
     * @return Specified user of the respond, String
     */
    public String getUser(String respond);
}