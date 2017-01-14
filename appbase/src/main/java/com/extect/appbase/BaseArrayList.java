package com.extect.appbase;


import java.util.ArrayList;

/**
 * Created by Sanka on 9/4/16.
 */
public class BaseArrayList<T> extends ArrayList<T> {

    public String tagValue;

    public void setTag(String tag){
        this.tagValue=tag;
    }

    public String getTag(){
        return tagValue;
    }
}
