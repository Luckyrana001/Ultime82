package utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by Sanka on 9/25/16.
 */
public class SettingServices {

    protected static final String UTF8 = "utf-8";
    private static final char[] SEKRIT ={'0','0','1','1','1','1','0','1','0','0','1','1','1'};
    protected static String valueString;
    protected static String name;
    protected static String number;

    private static SettingServices instance;

    private Context context;

    public static void init(Context context) {
        instance = new SettingServices(context);
    }

    public SettingServices(Context context) {
        this.context = context;
    }

    public static SettingServices getInstance() {
        return instance;
    }

    public void putUserToken(Context context,String userToken){
        if (!Utils.isNullOrEmptyString(userToken)) {
            valueString = encrypt(userToken);
        }
    }

    public String getUserToken(Context context) {
        if (!Utils.isNullOrEmptyString(valueString)) {
            return decrypt(valueString);
        }
        return "";
    }
    public String getUserName(Context context){
        if (!Utils.isNullOrEmptyString(name)) {
            return decrypt(name);
        }
        return "";
        //return getString(context, USER_NAME);
    }

    public void putUserName(Context context,String userName){
        if (!Utils.isNullOrEmptyString(userName)) {
            name = encrypt(userName);
        }
        // putString(context, USER_NAME,userName);
    }

    public String getMobileNumber(Context context){

        if (!Utils.isNullOrEmptyString(number)) {
            return decrypt(number);
        }
        return "";
        //return getString(context, USER_MOBILE_NUMBER);
    }
    public void putMobileNumber(Context context,String mobileNumber){

        if (!Utils.isNullOrEmptyString(mobileNumber)) {
            number = encrypt(mobileNumber);
        }
       // putString(context, USER_MOBILE_NUMBER,userName);
    }

    public String getString(Context context, String key) {
        return context.getSharedPreferences("appBase",
                Context.MODE_PRIVATE).getString(key, null);
    }

    public void clearToken(Context context){
        valueString = "";
    }

    protected String encrypt( String value ) {

        try {
            final byte[] bytes = value!=null ? value.getBytes(UTF8) : new byte[0];
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(SEKRIT));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(Settings.Secure.getString(context.getContentResolver(),Settings.System.ANDROID_ID).getBytes(UTF8), 20));
            return new String(Base64.encode(pbeCipher.doFinal(bytes), Base64.NO_WRAP),UTF8);

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }

    }

    protected String decrypt(String value){
        try {
            final byte[] bytes = value!=null ? Base64.decode(value,Base64.DEFAULT) : new byte[0];
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(SEKRIT));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(Settings.Secure.getString(context.getContentResolver(),Settings.System.ANDROID_ID).getBytes(UTF8), 20));
            return new String(pbeCipher.doFinal(bytes),UTF8);

        } catch( Exception e) {
            throw new RuntimeException(e);
        }
    }
}
