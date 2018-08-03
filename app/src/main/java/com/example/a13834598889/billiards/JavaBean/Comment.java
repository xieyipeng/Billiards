package com.example.a13834598889.billiards.JavaBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class Comment extends BmobObject {
    private Cards card;  //帖子
    private Customer customer;  //评论人
    private String time;  //评论时间
    private String context;  //评论内容
}
