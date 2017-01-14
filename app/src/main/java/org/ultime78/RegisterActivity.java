package org.ultime78;

/**
 * Created by abc1 on 1/13/2017.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends Activity {


    private Button btn_signup;
    private EditText edt_name, edt_password, edt_emailid, edt_state, edt_city, edt_mobile;
    private String custname, custpassword, custemail, custstatId, custmobile, custcity;
    public static final String KEY_ACT = "Register";
    public static final String KEY_NAME = "custname";
    public static final String KEY_PASSWORD = "custpassword";
    public static final String KEY_EMAIL = "custemail";
    public static final String KEY_MOBILE = "custmobile";
    public static final String KEY_STATID = "custstatId";
    public static final String KEY_CITY = "custcity";
    public static final String REGISTER_URL = "http://ultime8.com/Mobile-services/Home/savecust?custpassword=12345678&custmobile=9808282630&custname=amit&custstatId=10&custcity=12&email=prateek@intouchgroup.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edt_mobile = (EditText) findViewById(R.id.mobnum1);
        edt_emailid = (EditText) findViewById(R.id.email1);
        edt_password = (EditText) findViewById(R.id.password1);
        edt_name = (EditText) findViewById(R.id.name1);
        btn_signup = (Button) findViewById(R.id.register);
        edt_state = (EditText) findViewById(R.id.state1);
        edt_city = (EditText) findViewById(R.id.city1);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignup();
            }
        });
    }


    private void userSignup() {
        custcity = edt_city.getText().toString().trim();
        custstatId = edt_state.getText().toString().trim();
        custemail = edt_emailid.getText().toString().trim();
        custpassword = edt_password.getText().toString().trim();
        custname = edt_name.getText().toString().trim();
        custmobile = edt_mobile.getText().toString().trim();
        if (custemail.equals("") || custpassword.equals("") || custname.equals("") || custcity.equals("")
                || custstatId.equals("")) {
            Toast.makeText(RegisterActivity.this, "please fill all field", Toast.LENGTH_LONG).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject obj;
                            String status = "";
                            try {
                                obj = new JSONObject(response);
                                status = obj.getString("status");
                            } catch (JSONException e) {
                            }
//  catch(JSONException e){e.printStackTrace();
                            Log.e("status", status);
                            if (status.trim().equals("Done")) {
                                Toast.makeText(RegisterActivity.this, "Register Sucessful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_ACT, "Register");
                    map.put(KEY_NAME, custname);
                    map.put(KEY_EMAIL, custemail);
                    map.put(KEY_PASSWORD, custpassword);
                    map.put(KEY_MOBILE, custmobile);
                    map.put(KEY_CITY, custcity);
                    map.put(KEY_STATID, custstatId);
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
