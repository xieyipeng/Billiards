package com.example.a13834598889.billiards.FragmentCustomerMine.second;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentCustomerMine.thired.FragmentIM;
import com.example.a13834598889.billiards.FragmentCustomerMine.thired.Fragment_Friend_Add;
import com.example.a13834598889.billiards.JavaBean.Customer;
import com.example.a13834598889.billiards.JavaBean.Friend;
import com.example.a13834598889.billiards.JavaBean.ShopKeeper;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.MainActivity;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.Adapter.ContactsAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.BmobRealTimeData.TAG;


/**
 * Created by 13834598889 on 2018/5/3.
 */

public class Fragment_friends extends Fragment {

    private RecyclerView recyclerView_friends;
    private List<User> users = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private TextView addImageView;
    private ImageView imageView_back;
    private boolean isLast = false;
    private ContactsAdapter adapter;

    private FragmentManager fragmentManager;

    public static Fragment_friends newInstance() {
        return new Fragment_friends();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        initViews(view);
        initClisks();
        initData();
        return view;
    }

    private void initClisks() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + fragmentManager.findFragmentById(R.id.fragment_container).getTag());
                if (fragmentManager.findFragmentByTag("text_button_wodeqiuyou") != null) {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("text_button_wodeqiuyou"))
                            .remove(fragmentManager.findFragmentByTag("text_button_wodeqiuyou"))
                            .show(fragmentManager.findFragmentByTag("fragment_mine"))
                            .commit();
                }
            }
        });
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag("text_button_wodeqiuyou") != null) {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("text_button_wodeqiuyou"))
                            .remove(fragmentManager.findFragmentByTag("text_button_wodeqiuyou"))
                            .add(R.id.fragment_container, Fragment_Friend_Add.newInstance(), "circleImageView_mine88")
                            .commit();
                }
            }
        });
    }

    private void initViews(View view) {
        addImageView = view.findViewById(R.id.circleImageView_mine88);
        recyclerView_friends = view.findViewById(R.id.contacts_list_recycler_view);
        imageView_back = view.findViewById(R.id.fragment_friends_back);
    }

    private void reConnect() {
        Toast.makeText(getContext(), "正在连接im...", Toast.LENGTH_SHORT).show();
        BmobIM.connect(User.getCurrentUser().getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: im连接成功");
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                Log.e(TAG, "onChange: " + BmobIM.getInstance().getCurrentStatus().getMsg());
            }
        });
    }

    private void initData() {
        if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            reConnect();
        }
        BmobQuery<Friend> friendBmobQuery = new BmobQuery<>();
        friendBmobQuery.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getUser().getObjectId().equals(User.getCurrentUser().getObjectId())) {
                            stringList.add(list.get(i).getFriendUser().getObjectId());
                        }
                    }
                    if (stringList.size() != 0) {
                        for (int i = 0; i < stringList.size(); i++) {
                            if (stringList.size() - 1 == i) {
                                isLast = true;
                            }
                            User user = new User();
                            user.setObjectId(stringList.get(i));
                            users.add(user);
                            if (isLast) {
                                setRecycleViews();
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

    private void setRecycleViews() {
        adapter = new ContactsAdapter(users, getContext(), fragmentManager,"liebiao");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_friends.setLayoutManager(layoutManager);
        recyclerView_friends.setAdapter(adapter);
    }
}
