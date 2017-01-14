package utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import listeners.SmsReceiverNotify;

/**
 * Created by nalin on 9/6/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static SmsReceiverNotify smsRecivedNotify;
    public static String smsCode;
    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            smsCode = "";
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                   // Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains("scaled")) {
                        return;
                    }

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);

                   // Log.e(TAG, "OTP received: " + verificationCode);
                    smsCode = verificationCode;
                    //SmsReceiver.smsRecivedNotify.smsReceived(verificationCode);
                    //Intent hhtpIntent = new Intent(context, HttpService.class);
                    //hhtpIntent.putExtra("otp", verificationCode);
                   // context.startService(hhtpIntent);
                }
            }
        } catch (Exception e) {
           // Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {

        if (!message.contains(":")){
            return message;
        }
        String[] code = message.split(":");

        if (code.length < 2){
            return "";
        }
        //SmsReceiver.smsRecivedNotify.smsReceived(code);
        return code[1];
    }

    public static void setSmsReceiver(SmsReceiverNotify smsRecivedNotify) {
        SmsReceiver.smsRecivedNotify = smsRecivedNotify;
    }
}
