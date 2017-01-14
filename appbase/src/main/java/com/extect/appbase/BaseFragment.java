package com.extect.appbase;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import listeners.IOnFocusListenable;
import listeners.OnListFragmentInteractionListener;
import utils.UiConstants;

import static android.R.attr.button;
import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class BaseFragment extends Fragment implements View.OnClickListener, IOnFocusListenable {

    //public abstract BaseFragment newInstance(Object object);
    public String TAG = "BaseFragment";
    Menu mMenu;
    public boolean isAllowBackStackNotify = false;
    public OnListFragmentInteractionListener onListFragmentInteractionListener;
    public static ProgressDialog progressDialog;
    public static Typeface typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        typeface = getNewJuneRegular();
        setHasOptionsMenu(true);
        setupActionbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        hideKeyboard();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(true);
        }
        this.mMenu = menu;
        setupActionbar();
    }

    public void requestPermissionForCamera(){
        if (getBaseActivity().shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
           // Toast.makeText(getBaseActivity(), "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            getBaseActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, UiConstants.CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForExternalStorage(){
        if (getBaseActivity().shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
           // Toast.makeText(getBaseActivity(), "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            getBaseActivity().requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, UiConstants.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }
    public void showDialog(String title,String msg){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getBaseActivity());
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });

        /*alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @SuppressWarnings("deprecation")
    protected void setupActionbar() {

    }

    public void showToast(String message){
        ((BaseActivity)getActivity()).showToast(message);
    }

    public void hideKeyboard() {
        getBaseActivity().hideKeyboard();
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

        @Override
    public void onDetach() {
        super.onDetach();
    }

    public String getTAG() {
        return TAG;
    }

    public BaseActivity getBaseActivity(){
        return ((BaseActivity)getActivity());
    }

    public Typeface getNewJuneRegular() {
        return getBaseActivity().getNewJuneRegular();
    }

    public Typeface getFontAwesome() {
        return getBaseActivity().getFontAwesome();
    }

    public abstract void setTAG(String TAG);
}
