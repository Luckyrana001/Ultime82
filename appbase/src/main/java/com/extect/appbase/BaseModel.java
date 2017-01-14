package com.extect.appbase;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanka on 9/4/16.
 */
public class BaseModel {

    private String tag;

    @SerializedName("respCode")
    public String respCode;

    @SerializedName("respDesc")
    public String respDesc;

    @SerializedName("errorDetails")
    public String errorDetails[];

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }
}
