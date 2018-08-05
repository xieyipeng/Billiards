package com.example.a13834598889.billiards.JavaBean;

/**
 * Created by 13834598889 on 2018/4/29.
 * 店家账号信息表
 */

public class ShopKeeper extends User{

    private String longitude;//店铺所在位置的经度
    private String latitude;//店铺所在位置的纬度
    private String location;//地址

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
