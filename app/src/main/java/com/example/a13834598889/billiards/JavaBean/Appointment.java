package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 13834598889 on 2018/4/29.
 * 预约表
 */

public class Appointment extends BmobObject{
    private ShopKeeper storeId;//店面(店主)
    private Customer userId;//预约发起人
    private String time_order;//预约发起时间
    private String time_start;//预约到店时间
    private Double appointment_money;//预约应付金额
    private Integer table_number;//预约球桌号
    private Integer isDeal;//是否已经被处理

    public ShopKeeper getStoreId() {
        return storeId;
    }

    public void setStoreId(ShopKeeper storeId) {
        this.storeId = storeId;
    }

    public Customer getUserId() {
        return userId;
    }

    public void setUserId(Customer userId) {
        this.userId = userId;
    }

    public String getTime_order() {
        return time_order;
    }

    public void setTime_order(String time_order) {
        this.time_order = time_order;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public Double getAppointment_money() {
        return appointment_money;
    }

    public void setAppointment_money(Double appointment_money) {
        this.appointment_money = appointment_money;
    }

    public Integer getTable_number() {
        return table_number;
    }

    public void setTable_number(Integer table_number) {
        this.table_number = table_number;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }
}
