package com.idealessidealist.nudger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class Success {

    @SerializedName("success")
    @Expose
    private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
