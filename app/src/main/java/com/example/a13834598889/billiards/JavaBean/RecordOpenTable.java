package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class RecordOpenTable extends BmobObject {
    private ShopKeeper shopKeeper;  //拥有订单店主
    private String time_start;  //开台时间
    private String time_end;  //结束时间
    private String payment_method;  //付款方式
    private Double money_drink;  //饮料费用
    private Double money_table;  //台费
    private Double money_demage;  //损坏设备赔偿
    private Double money_all;  //总付款金额

    public ShopKeeper getShopKeeper() {
        return shopKeeper;
    }

    public void setShopKeeper(ShopKeeper shopKeeper) {
        this.shopKeeper = shopKeeper;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Double getMoney_drink() {
        return money_drink;
    }

    public void setMoney_drink(Double money_drink) {
        this.money_drink = money_drink;
    }

    public Double getMoney_table() {
        return money_table;
    }

    public void setMoney_table(Double money_table) {
        this.money_table = money_table;
    }

    public Double getMoney_demage() {
        return money_demage;
    }

    public void setMoney_demage(Double money_demage) {
        this.money_demage = money_demage;
    }

    public Double getMoney_all() {
        return money_all;
    }

    public void setMoney_all(Double money_all) {
        this.money_all = money_all;
    }
}
