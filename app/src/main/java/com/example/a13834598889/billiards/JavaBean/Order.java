package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 13834598889 on 2018/4/29.
 * 结算账单表
 */

public class Order extends BmobObject {
    private ShopKeeper storeId;//店面ID（店主）
    private Customer userID;//结账发起人ID（顾客）
    private String time;//结账时间
    private Integer table_number;//结账桌位号
    private Double money_1;//桌费
    private Double money_2;//饮品零食费
    private Double money_all;//支付总金额

    public ShopKeeper getStoreId() {
        return storeId;
    }

    public void setStoreId(ShopKeeper storeId) {
        this.storeId = storeId;
    }

    public Customer getUserID() {
        return userID;
    }

    public void setUserID(Customer userID) {
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getTable_number() {
        return table_number;
    }

    public void setTable_number(Integer table_number) {
        this.table_number = table_number;
    }

    public Double getMoney_1() {
        return money_1;
    }

    public void setMoney_1(Double money_1) {
        this.money_1 = money_1;
    }

    public Double getMoney_2() {
        return money_2;
    }

    public void setMoney_2(Double money_2) {
        this.money_2 = money_2;
    }

    public Double getMoney_all() {
        return money_all;
    }

    public void setMoney_all(Double money_all) {
        this.money_all = money_all;
    }
}
