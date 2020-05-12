package com.finwin.brahmagiri.fooddelivery.Responses.Fetch_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemCat {
    @SerializedName("Cat Id")
    @Expose
    private Integer catId;
    @SerializedName("Cat Name")
    @Expose
    private String catName;
    @SerializedName("Decrip")
    @Expose
    private String decrip;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("ID")
    @Expose
    private Integer iD;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getDecrip() {
        return decrip;
    }

    public void setDecrip(String decrip) {
        this.decrip = decrip;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }
}
