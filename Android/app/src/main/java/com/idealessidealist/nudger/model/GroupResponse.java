package com.idealessidealist.nudger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class GroupResponse {

    @SerializedName("success")
    @Expose
    private Integer success;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("members")
    @Expose
    private List<String> members;

    @SerializedName("chores")
    @Expose
    private List<String> chores;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getChores() {
        return chores;
    }

    public void setChores(List<String> chores) {
        this.chores = chores;
    }
}
