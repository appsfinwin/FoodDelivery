package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("user_context")
    @Expose
    private UserContext userContext;
    @SerializedName("uid")
    @Expose
    private Integer uid;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}
