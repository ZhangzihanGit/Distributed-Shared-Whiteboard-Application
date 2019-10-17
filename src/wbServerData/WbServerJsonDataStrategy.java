package wbServerData;

import org.json.simple.JSONObject;

/**
 * JSON version implementation of data strategy used by whiteboard server
 */
public class WbServerJsonDataStrategy implements WbServerDataStrategy {
    /** settings  */
    private static final String HEADER = "header";
    private static final String MSG = "message";
    private static final String CATEGORY = "category";
    private static final String USER = "user";

    private static final String FAIL_HEADER = "Fail";
    private static final String SUCCESS_HEADER = "Success";

    @Override
    public String packRespond(Boolean isSuccess, String msg, String category, String user) {
        JSONObject respond = new JSONObject();

        if (isSuccess != null && isSuccess)
            respond.put(HEADER, SUCCESS_HEADER);
        else
            respond.put(HEADER, FAIL_HEADER);

        if (msg != null)
            respond.put(MSG, msg);
        else
            respond.put(MSG, "");

        if (category != null)
            respond.put(CATEGORY, category);
        else
            respond.put(CATEGORY, "");

        if (user != null)
            respond.put(USER, user);
        else
            respond.put(USER, "");

        return respond.toJSONString();
    }
}
