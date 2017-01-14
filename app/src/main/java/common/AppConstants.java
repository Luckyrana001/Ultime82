package common;


import android.util.Log;

/**
 * Created by Sanka on 9/25/16.
 */

public class AppConstants {

    public static int COUNT = 0;

    public static int SELECTED_INDEX = 0;
    public static String SELECTED_PANNEL = "";

    public static final boolean IS_DEBUG = false;
    public static final String TOKEN_HEADER_AUTHORIZATION = "Token-Authorization";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json";

    public static final String MULTIPART_BOUNDARY_VALUE = "multipart/form-data;";

    public static String BASE_ROOT_URL = "http://ultime8.com/Mobile-services/Home/";
    public static final String LOGIN_URL = "http://ultime8.com/Mobile-services/Home/check_email_availablity?chtype=2&mobile=9310524721";


    public static final String DEVICE_TYPE = "Android";


    public static String getRootUrl(String filePath){
        Log.v("filePath",BASE_ROOT_URL  + filePath);
        return BASE_ROOT_URL  + filePath;
    }
    public static String getLoginUrl()
    {
        return BASE_ROOT_URL  + "check_email_availablity?";
    }

}

