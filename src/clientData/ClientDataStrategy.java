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
}