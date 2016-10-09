package com.idealessidealist.nudger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kenny Tsui on 10/8/2016.
 */

public class Group {

    @SerializedName("name")
    @Expose
    private String name;
//
//    @SerializedName("number")
//    @Expose
//    private Integer remaining;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public Integer getRemaining() {
//        return remaining;
//    }
//
//    public void setRemaining(Integer remaining) {
//        this.remaining = remaining;
//    }
}
