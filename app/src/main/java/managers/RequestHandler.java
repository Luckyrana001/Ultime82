package managers;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.extect.appbase.BaseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import common.AppConstants;
import listeners.IResponseReceivedNotifyInterface;
import listeners.ResponseArgs;
import network.RequestHeaderProvider;
import shared.BaseFlyContext;
import utils.RequestType;
import utils.ResponseStatus;

/**
 * Created by Himanshu .
 */
public class RequestHandler {

    public static RequestHandler requestHandler;
    protected Gson gson;
    RequestQueue queue;

    public RequestHandler() {
        queue = Volley.newRequestQueue(BaseFlyContext.getInstant().getApplicationContext());
        gson = new GsonBuilder().create();
    }

    public static RequestHandler getRequestHandler() {

        if (requestHandler == null) {
            requestHandler = new RequestHandler();
        }
        return requestHandler;
    }

        /*method used to handle Json response*/

    public void login(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String userName, String pswd) {

         /*Post data*/
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("mobile", userName);
        jsonParams.put("chtype", pswd);

        String url = AppConstants.getLoginUrl()/*+"mobile"+userName+"chtype="+pswd*/;

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Type type = new TypeToken<BaseModel>() {
                        }.getType();
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(gson.fromJson(response.toString(), type),
                                ResponseStatus.success, RequestType.Login));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(null, ResponseStatus.badRequest,
                                RequestType.Login));
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new RequestHeaderProvider().getRequestHeaders();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }


    /*method used to handle string response*/
    public void dologin(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, final String userName, final String pswd) {

        String url = AppConstants.getLoginUrl()+"mobile="+userName+"&chtype="+pswd;



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iResponseReceivedNotifyInterface.stringResponseReceived(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iResponseReceivedNotifyInterface.stringResponseReceived("Bad Request");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                /*map.put("mobile",userName);
                map.put("chtype", pswd);*/
                return map;
            }
        };
       RequestQueue requestQueue = Volley.newRequestQueue(BaseFlyContext.getInstant().getApplicationContext());
        requestQueue.add(stringRequest);




    }


}
