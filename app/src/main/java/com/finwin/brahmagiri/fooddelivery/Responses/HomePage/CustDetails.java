package com.finwin.brahmagiri.fooddelivery.Responses.HomePage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustDetails {
    @SerializedName("CustId")
    @Expose
    private Integer custId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Telephone")
    @Expose
    private String telephone;
    @SerializedName("EmailId")
    @Expose
    private Object emailId;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Object getEmailId() {
        return emailId;
    }

    public void setEmailId(Object emailId) {
        this.emailId = emailId;
    }

}
