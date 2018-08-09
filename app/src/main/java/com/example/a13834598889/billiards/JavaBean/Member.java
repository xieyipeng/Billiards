package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;

public class Member extends BmobObject{
    private String userName;
    private String storeName;

    public String getNickName() {
        return userName;
    }

    public void setNickName(String userName) {
        this.userName = userName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
