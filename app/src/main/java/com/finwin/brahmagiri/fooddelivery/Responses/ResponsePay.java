package com.finwin.brahmagiri.fooddelivery.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePay {
    @SerializedName("String_strPGMsg")
    @Expose
    private String stringStrPGMsg;

    public String getStringStrPGMsg() {
        return stringStrPGMsg;
    }

    public void setStringStrPGMsg(String stringStrPGMsg) {
        this.stringStrPGMsg = stringStrPGMsg;
    }
}
