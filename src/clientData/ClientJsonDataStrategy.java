package clientData;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Data strategy designed to handling JSON format message
 */
public class ClientJsonDataStrategy implements ClientDataStrategy {
    /** logger */
    private final static Logger logger = Logger.getLogger(ClientDataStrategy.class);

    @Override
    public Boolean getHeader(String respond) {
        if (respond == null || respond.isEmpty()) {
            logger.error("Receive empty or null response from server");
            return false;
        }

        Boolean result = false;
        JSONParser jsonParser = new JSONParser();

        //Read JSON requst
        try {
            JSONObject respondJSON = (JSONObject) jsonParser.parse(respond);
            String header = (String) respondJSON.get("header");

            if (header.equals("Success")) {
                result = true;
            }
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Parse json failed from respond: " + respond);
            return result;
        }

        logger.info("Resolve server response header successfully");
        return result;
    }

    @Override
    public String getMsg(String respond) {
        if (respond == null || respond.isEmpty()) {
            logger.error("Receive empty or null response from server");
            return "";
        }

        String result = "[ERROR]: Can't parse respond from server: " + respond;
        JSONParser jsonParser = new JSONParser();

        //Read JSON requst
        try {
            JSONObject respondJSON = (JSONObject) jsonParser.parse(respond);
            result = (String) respondJSON.get("message");
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("Parse json failed from respond: " + respond);
            return result;
        }

        logger.info("Resolve server response message successfully");
        return result;
    }
}
