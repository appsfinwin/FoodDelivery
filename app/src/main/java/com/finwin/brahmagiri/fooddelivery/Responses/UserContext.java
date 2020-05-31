package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserContext {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("tz")
    @Expose
    private Boolean tz;
    @SerializedName("uid")
    @Expose
    private Integer uid;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getTz() {
        return tz;
    }

    public void setTz(Boolean tz) {
        this.tz = tz;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
