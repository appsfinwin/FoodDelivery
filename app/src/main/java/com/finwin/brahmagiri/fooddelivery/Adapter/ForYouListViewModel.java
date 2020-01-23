package com.finwin.brahmagiri.fooddelivery.Adapter;

public class ForYouListViewModel {
    Integer image, imgLike;
    String itemID, itemoffer, itemPrice, itemName, itemDescp, itemAmount;

    public ForYouListViewModel(String _itemID, Integer _imgLike, Integer _image, String _itemoffer, String _itemPrice,
                               String _itemName) {
        //, String _itemDescp
        this.itemID = _itemID;
        this.imgLike = _imgLike;
        this.image = _image;
        this.itemoffer = _itemoffer;
        this.itemPrice = _itemPrice;
        this.itemName = _itemName;
//        this.itemDescp = _itemDescp;
    }

    public String getItemID() {
        return itemID;
    }

    public void setImageLike(Integer imgLike) {
        this.imgLike = imgLike;
    }

    public Integer getImageLike() {
        return imgLike;
    }


    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getImage() {
        return image;
    }


    public String getItemoffer() {
        return itemoffer;
    }

    public void setItemoffer(String itemoffer) {
        this.itemoffer = itemoffer;
    }


    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


//    public String getItemDescrptn() {
//        return itemDescp;
//    }
//
//    public void setItemDescrptn(String itemDescp) {
//        this.itemDescp = itemDescp;
//    }




//
//    Integer image1, image2;
//    String textoffer, textprice, textshoes;
//
//    public ForYouListViewModel(Integer image1, Integer image2, String textoffer, String textprice, String textshoes) {
//        this.image1 = image1;
//        this.image2 = image2;
//        this.textoffer = textoffer;
//        this.textprice = textprice;
//        this.textshoes = textshoes;
//    }
//    public Integer getImage1() {
//        return image1;
//    }
//
//    public void setImage1(Integer image1) {
//        this.image1 = image1;
//    }
//
//    public Integer getImage2() {
//        return image2;
//    }
//
//    public void setImage2(Integer image2) {
//        this.image2 = image2;
//    }
//
//    public String getTextoffer() {
//        return textoffer;
//    }
//
//    public void setTextoffer(String textoffer) {
//        this.textoffer = textoffer;
//    }
//
//    public String getTextprice() {
//        return textprice;
//    }
//
//    public void setTextprice(String textprice) {
//        this.textprice = textprice;
//    }
//
//    public String getTextshoes() {
//        return textshoes;
//    }
//
//    public void setTextshoes(String textshoes) {
//        this.textshoes = textshoes;
//    }

}