package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class RecordBallMoneyGrant extends BmobObject {
    private ShopKeeper shopKeeper;  //球币发放记录拥有店主
    private Customer customer;  //球币发放对象
    private String time_grant;  //发放时间
    private Integer amout_grant;  //发放数量
    private Double money_ball;  //发放球币所收款
    private String name_grant;  //发放球币处理员名字

    public ShopKeeper getShopKeeper() {
        return shopKeeper;
    }

    public void setShopKeeper(ShopKeeper shopKeeper) {
        this.shopKeeper = shopKeeper;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTime_grant() {
        return time_grant;
    }

    public void setTime_grant(String time_grant) {
        this.time_grant = time_grant;
    }

    public Integer getAmout_grant() {
        return amout_grant;
    }

    public void setAmout_grant(Integer amout_grant) {
        this.amout_grant = amout_grant;
    }

    public Double getMoney_ball() {
        return money_ball;
    }

    public void setMoney_ball(Double money_ball) {
        this.money_ball = money_ball;
    }

    public String getName_grant() {
        return name_grant;
    }

    public void setName_grant(String name_grant) {
        this.name_grant = name_grant;
    }
}
