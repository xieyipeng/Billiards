package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 13834598889 on 2018/4/29.
 * 用户发表的帖子信息表
 */

public class Cards extends BmobObject {
    private Customer userId;//发表人(顾客)
    private String title;//标题
    private String time;//发表时间
    private BmobFile picture;//配图
    private Integer num_dianzan;//点赞数
    private String context;//帖子内容

    public Customer getUserId() {
        return userId;
    }

    public void setUserId(Customer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public Integer getNum_dianzan() {
        return num_dianzan;
    }

    public void setNum_dianzan(Integer num_dianzan) {
        this.num_dianzan = num_dianzan;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
