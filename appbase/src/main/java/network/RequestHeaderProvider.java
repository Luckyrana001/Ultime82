package network;

import java.util.HashMap;
import java.util.Map;

import shared.BaseFlyContext;
import utils.SettingServices;

/**
 * Created by nalin on 3/9/15.
 */
public class RequestHeaderProvider {

    private Map<String, String> headers;

    public static final String CONT_LANG = "Content-Language";
    public static final String CONT_TYP = "Content-Type";
    public static final String AGENT = "User-agent";
    public static boolean IS_HTTPS = true;


    public RequestHeaderProvider(){

        headers = new HashMap<>();
    }

    public Map<String, String> getRequestHeaders() {

        if (!IS_HTTPS) {
            headers.put(CONT_LANG, "en");
            headers.put(AGENT, System.getProperty("http.agent"));
            headers.put(CONT_TYP, "application/json");
        }
        headers.put("deviceType", "Android");
        headers.put("sessionToken", SettingServices.getInstance().getUserToken(BaseFlyContext.getInstant().getApplicationContext()));
        return headers;
    }

    public void addCustomHeader(String key, String values){
        headers.put(key, values);
    }
}
