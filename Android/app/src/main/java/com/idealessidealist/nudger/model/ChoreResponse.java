package com.idealessidealist.nudger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class ChoreResponse {

    @SerializedName("chores")
    @Expose
    public List<String> chores;

    public List<String> getChores() {
        return chores;
    }

    public void setChores(List<String> chores) {
        this.chores = chores;
    }
}
