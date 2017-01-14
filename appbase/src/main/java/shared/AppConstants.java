/*
package shared;

import android.content.Context;

import com.extect.appbase.BaseApplication;
import com.extect.appbase.R;





public class AppConstants {

    public static final boolean IS_DEBUG = false;
    public static final String TOKEN_HEADER_AUTHORIZATION = "Token-Authorization";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json";

    public static final String MULTIPART_BOUNDARY_VALUE = "multipart/form-data;";

   // public static String BASE_ROOT_URL = "http://45.32.107.244:8080/";
    //public static String BASE_ROOT_URL = "Http://sgldkfxa01:8080/";



*/
/** SIT *//*




    //public static String BASE_ROOT_URL = "Http://sglukfxa01:8080/";



*/
/** UAT *//*




    //public static String BASE_ROOT_URL = "http://10.100.15.38:8080/";
    //public static String BASE_ROOT_URL = "http://10.30.121:8080/";
    //public static String BASE_ROOT_URL = "http://192.168.0.105:8080/";

   // Context context = BaseApplication.context;


    public static String BASE_ROOT_URL = Constant.B;

    public static String ROOT_URL = "ecapservices/";
    //constant value
    public static final String DEVICE_TYPE = "Android";
//ecapservices/login/
    //logout/
    //sessionPulse/

    public static String getRootUrl(String filePath){
        return BASE_ROOT_URL + ROOT_URL + filePath;
    }
    public static String getConfigUrl()
    {
        return BASE_ROOT_URL + ROOT_URL + "getAppConfig/";
    }
    public static String getAPILogin(){
        return BASE_ROOT_URL + ROOT_URL + "dwaveLogin/";
    }
    public static String getAPILogout(){
        return BASE_ROOT_URL + ROOT_URL + "logout/";
    }
    public static String getAPISessionPulse(){
        return BASE_ROOT_URL + ROOT_URL + "sessionPulse/";
    }
    public static String getAPITwoFA(){
        return BASE_ROOT_URL + ROOT_URL + "twoStepVerReg/";
    }
    public static String getAPIGenerateOtp(){
        return BASE_ROOT_URL + ROOT_URL + "generateOtp/";
    }

    public static String getAPITermsAgreed(){
        return BASE_ROOT_URL + ROOT_URL + "termsAgreed/";
    }
    public static String getAPIValidateOtp(){
        return BASE_ROOT_URL + ROOT_URL + "validateOtp/";
    }
    public static String getAPIViewPendProp(){
        return BASE_ROOT_URL + ROOT_URL + "viewPendProp/";
    }
    public static String getAPIProfile(){
        return BASE_ROOT_URL + ROOT_URL + "getProfile/";
    }
    public static String getAPIUploadDocument(){
        return BASE_ROOT_URL + ROOT_URL + "submitPendProp/";
    }
}


*/
