package com.example.a13834598889.billiards.FragmentCustomerMine.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.Adapter.ChatAdapter;
import com.example.a13834598889.billiards.Tool.Conversation;
import com.example.a13834598889.billiards.Tool.PrivateConversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;

import static org.greenrobot.eventbus.EventBus.TAG;

public class Fragment_huihua extends Fragment {

    private FragmentManager fragmentManager;
    private ImageView back;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private LinearLayout linearLayout;
    private List<BmobIMMessage> messageList;

    public static Fragment_huihua newInstance() {
        return new Fragment_huihua();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huihua, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        adapter=new ChatAdapter();
        initViews(view);
        initClicks();
        return view;
    }

//    /**
//     * 首次加载，可设置msg为null，下拉刷新的时候，默认取消息表的第一个msg作为刷新的起始时间点，默认按照消息时间的降序排列
//     *
//     * @param msg
//     */
//    public void queryMessages(BmobIMMessage msg) {
//        //TODO 消息：5.2、查询指定会话的消息记录
//        mConversationManager.queryMessages(msg, 10, new MessagesQueryListener() {
//            @Override
//            public void done(List<BmobIMMessage> list, BmobException e) {
//                sw_refresh.setRefreshing(false);
//                if (e == null) {
//                    if (null != list && list.size() > 0) {
//                        adapter.addMessages(list);
//                        layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
//                    }
//                } else {
//                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
//                }
//            }
//        });
//    }

    private void initClicks() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
//        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                refreshLayout.setRefreshing(true);
//                //自动刷新
//                queryMessages(null);
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag("huihua") != null) {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("huihua"))
                            .remove(fragmentManager.findFragmentByTag("huihua"))
                            .show(fragmentManager.findFragmentByTag("fragment_mine"))
                            .commit();
                }
            }
        });
    }

    /**
     * 获取会话列表的数据：增加新朋友会话
     *
     * @return
     */
    private List<Conversation> getConversations() {
        //添加会话
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        //TODO 会话：4.2、查询全部会话
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        Log.e(TAG, "getConversations: "+ list.size());
        if (list != null && list.size() > 0) {
            for (BmobIMConversation item : list) {
                switch (item.getConversationType()) {
                    case 1://私聊
                        Log.e(TAG, "getConversations: id: "+item.getConversationId() );
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        //添加新朋友会话-获取好友请求表中最新一条记录
//        List<NewFriend> friends = NewFriendManager.getInstance(getActivity()).getAllNewFriend();
//        if(friends!=null && friends.size()>0){
//            conversationList.add(new NewFriendConversation(friends.get(0)));
//        }
        //重新排序
        Collections.sort(conversationList);
        return conversationList;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.setRefreshing(true);
        query();
    }


    private void query() {
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    private void initViews(View view) {
        linearLayout=view.findViewById(R.id.huihua_big);
        back = view.findViewById(R.id.message_back);
        refreshLayout = view.findViewById(R.id.message_refresh);
        recyclerView = view.findViewById(R.id.message_view);
    }
}
