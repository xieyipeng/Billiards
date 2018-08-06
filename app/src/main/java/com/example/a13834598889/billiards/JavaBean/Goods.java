package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 13834598889 on 2018/4/29.
 * 单个商品信息表
 */

public class Goods extends BmobObject {
    private String storeID;//店主
    private String shop_name;//商品名字
    private Double shop_price;//商品价格
    private Integer shop_num;//商品库存量
    private BmobFile picture;//商品图片
    private Integer pictureId;//商品图片Id

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public Double getShop_price() {
        return shop_price;
    }

    public void setShop_price(Double shop_price) {
        this.shop_price = shop_price;
    }

    public Integer getShop_num() {
        return shop_num;
    }

    public void setShop_num(Integer shop_num) {
        this.shop_num = shop_num;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }
}
