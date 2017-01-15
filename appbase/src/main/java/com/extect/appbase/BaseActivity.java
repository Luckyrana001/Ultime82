package com.extect.appbase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.splunk.mint.Mint;

import listeners.OnFragmentInteractionListener;
import widgets.CustomTypefaceSpan;

public abstract class BaseActivity extends AppCompatActivity implements OnFragmentInteractionListener, View.OnClickListener {


    /* Network change broadcast receiver object */
    public static boolean isConnected = false;
    protected NetworkChangeReceiver receiver;

    public static boolean isActive = false;
    public static CoordinatorLayout coordinatiorLayout;
    public View mControlsView;
    public ActionBar mActionBar;
    private Typeface newJuneRegular;
//    private Typeface openSansRegular;
//    private Typeface openSansSemibold;
    private Typeface fontAwesome;

    public static void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(coordinatiorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        fontAwesome = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");

        newJuneRegular = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");

        Mint.initAndStartSession(this.getApplication(), "d7cde585");
        //registerNetworkReciever();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        isActive = true;
        if(receiver!=null)
        {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;

    }

    public void replaceView(int layout, BaseFragment fragment, boolean isAddToBackStack, boolean... isAnim) {

        try {
            if (fragment != null) {

                String fragmentName = fragment.getClass().getSimpleName();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left, R.anim.slide_in_left,
                        R.anim.slide_out_right);
                fragmentTransaction.replace(layout, fragment, fragmentName);
                //if (isAddToBackStack) {
                String s = fragment.getClass().getSimpleName();
                fragmentTransaction.addToBackStack(fragmentName);
                // }
                // else {
                //     fragmentTransaction.addToBackStack(null);
                // }
                fragmentTransaction.commitAllowingStateLoss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void popBackStackFromName(String fragment) {
        getSupportFragmentManager().popBackStack(fragment, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setFitSystemLayoutMode(boolean mode) {

        if (coordinatiorLayout != null){
            coordinatiorLayout.setFitsSystemWindows(mode);
        }
    }

    public ActionBar getmActionBar() {

        if (mActionBar == null) {
            mActionBar = getSupportActionBar();

            SpannableString s = new SpannableString("My Title");
            s.setSpan( new CustomTypefaceSpan("", getNewJuneRegular()), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Update the action bar title with the TypefaceSpan instance
            mActionBar.setTitle(s);
        }
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.show();
        exitFullScreenMode();
        return mActionBar;
    }

    public void showActionBar() {
        getmActionBar();
    }

    public void setFullScreenMode() {

        mControlsView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void exitFullScreenMode() {

       /* mControlsView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/
    }


    protected void hideKeyboard() {
        if (getCurrentFocus() != null
                && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(),
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    public abstract void replaceView();

    public Typeface getNewJuneRegular() {
        return newJuneRegular;
    }

    public Typeface getFontAwesome() {
        return fontAwesome;
    }
    /**
     * @desc method to registering network change receiver
     * @return null
     */
   protected void registerNetworkReciever() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
       // registerReceiver(receiver, filter);
    }
    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            isNetworkAvailable(context);
        }

        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {
                                Log.v("Connection available", "Now you are connected to Internet!");
                                isConnected = true;
                               // showSnackBar(getResources().getString(R.string.internet_connection_found));
// do your processing here â€”
// if you need to post any data to the server or
// get status
// update from the server
                            }
                            return true;
                        }
                    }
                }
            }

            Log.v("Connection Lost", "You are not connected to Internet!");
            isConnected = false;
            //showToast("Connection Lost");
            //showSnackBar(getResources().getString(R.string.internet_connection_Lost));
            return false;
        }
    }
}
