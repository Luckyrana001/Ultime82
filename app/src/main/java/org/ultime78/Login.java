package org.ultime78;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.extect.appbase.BaseActivity;
import com.extect.appbase.BaseModel;

import common.Constants;
import listeners.IResponseReceivedNotifyInterface;
import listeners.ResponseArgs;
import managers.AppDataManager;
import shared.BaseFlyContext;
import utils.RequestType;
import utils.ResponseStatus;
import utils.SettingServices;
import utils.Utils;

/**
 * Created by abc1 on 1/13/2017.
 */


public class Login extends BaseActivity implements IResponseReceivedNotifyInterface {


    private EditText userNameEt, passwordEt;
    private TextView loginBtn;
    private TextView singupBtn;

    public static ProgressDialog progressDialog;
    public String mobile, password;

    RequestQueue requestQueue;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_login);

        generateHashKey();




        hideKeyboard(activity);

        SettingServices.init(getApplicationContext());
        BaseFlyContext.getInstant().setActivity(this);
        activity = Login.this;


        initLayout();


        getmActionBar();
        mActionBar.hide();
        hideKeyboard(activity);

    }

    private void generateHashKey() {
         /*  // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "org.ultime78",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
*/
    }

    private void initLayout() {

        userNameEt = (EditText) findViewById(R.id.userNameEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        loginBtn = (TextView) findViewById(R.id.loginBtn);
        singupBtn = (TextView) findViewById(R.id.signUpTv);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        singupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(activity);

                Intent i_signup = new Intent(Login.this, RegisterActivity.class);
                startActivity(i_signup);
                /* Start right to left animation*/
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        });
    }

    public void hideKeyboard(Activity activity) {

        hideKeyboard();
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void userLogin() {

        hideKeyboard(activity);

        mobile = userNameEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();


        if (mobile.equals("") || password.equals("")) {

            Toast.makeText(Login.this, "Please enter valid credential", Toast.LENGTH_LONG).show();
        } else {

            if (Utils.isNetworkConnected(this, true, R.style.AppCompatAlertDialogStyle)) {
                progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                AppDataManager.getDataManager().login(this, mobile, password);

            } else {
                Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_LONG).show();
            }


        }
    }


    @Override
    public void responseReceived(ResponseArgs responseArgs) {
        progressDialog.dismiss();
        if (responseArgs.args != null) {
            BaseModel clinicModel = (BaseModel) responseArgs.args;

            if (responseArgs.responseStatus == ResponseStatus.success) {

                if (clinicModel.respCode.equals(Constants.ERROR_CODE_SUCCESS_1000)) {

                    if (responseArgs.requestType == RequestType.Login) {

                        Toast.makeText(Login.this, "Login Sucessful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                    }
                }

            }
        }

    }

    @Override
    public void stringResponseReceived(String response) {
        progressDialog.dismiss();
        if (response != null) {
            if (response.trim().equals("\"s^existmobile\"")) {
                showToast("Login Successful.");
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
            } else if (response.trim().equals("\"s^notexist\"")) {
                showToast("User do not exist, Please register.");
            }
            else
            {
                showToast("Error:  "+response);
            }


        } else {
            showToast("Error:  "+response);

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void replaceView() {

    }

}
