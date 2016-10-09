package com.idealessidealist.nudger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class GroupList {

    @SerializedName("success")
    @Expose
    private Integer success;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("joined")
    @Expose
    private List<String> joined;

    @SerializedName("pending")
    @Expose
    private List<String> pending;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getJoined() {
        return joined;
    }

    public void setJoined(List<String> joined) {
        this.joined = joined;
    }

    public List<String> getPending() {
        return pending;
    }

    public void setPending(List<String> pending) {
        this.pending = pending;
    }
}
