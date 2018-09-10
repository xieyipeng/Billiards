package com.example.a13834598889.billiards.FragmentShopKepperMine.second;

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
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentShopKepperMine.third.FragmentShopAddMember;
import com.example.a13834598889.billiards.JavaBean.Member;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.Adapter.MemberAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class FragmentShopMemberMessage extends Fragment {

    private CircleImageView memberBack;
    private CircleImageView memberAdd;
    private Fragment fragmentTest;
    private FragmentManager fragmentManager;
    private MemberAdapter memberAdapter;
    private RecyclerView memberRecyclerView;
    private List<User> users = new ArrayList<>();

    public static FragmentShopMemberMessage newInstance() {
        FragmentShopMemberMessage fragmentShopMemberMessage = new FragmentShopMemberMessage();
        return fragmentShopMemberMessage;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_member_message, container, false);
        initViews(view);
        initClicks();
        loadingMessage();//更新列表
        return view;
    }

    private void initClicks() {
        memberBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .remove(fragmentTest)
                        .show(fragmentManager.findFragmentByTag("shop_fragment_mine"))
                        .commit();
            }
        });
        memberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .remove(fragmentTest)
                        .commit();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopAddMember.newInstance(), "shop_member_add_ImageView")
                        .commit();
            }
        });
    }

    private void loadingMessage() {
        final String[] storeUserName = new String[1];
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                //拿到店名
                if (e == null) {
                    storeUserName[0] = user.getUsername();
                    getMembers(storeUserName);
                } else {
                    Log.e(TAG, "done: 获取店铺名错误");
                }
            }
        });
    }

    private void getMembers(final String[] storeUserName) {
        BmobQuery<Member> memberBmobQuery = new BmobQuery<>();
        memberBmobQuery.findObjects(new FindListener<Member>() {
            @Override
            public void done(List<Member> list, BmobException e) {
                //拿到该店所有会员的姓名
                if (e == null) {
                    List<Member> list1 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getStoreName().equals(storeUserName[0])) {
                            list1.add(list.get(i));
                        }
                    }
                    Log.e(TAG, "done: list1.size() " + list1.size());

                    if (list1.size() > 1) {
                        users.clear();
                    }

                    for (int i = 0; i < list1.size(); i++) {
                        boolean isLast = false;
                        if (i == list1.size() - 1) {
                            isLast = true;
                        }
                        getUsers(list1.get(i).getUserName(), isLast);
                    }
                } else {
                    Log.e(TAG, "done: 加载会员界面时出现错误");
                }
            }
        });
    }

    private void getUsers(final String username, final boolean isLast) {
        final BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                //拿到user
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getUsername().equals(username)) {
                            users.add(list.get(i));
                        }
                    }
                    if (users.size() <= 0) {
                        Toast.makeText(getContext(), "店长，您还没有会员呢", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isLast) {
                            setRecycleView();
                        }
                    }
                } else {
                    Log.e(TAG, "done: 获取user时出错: " + e.getMessage());
                }
            }
        });
    }

    private void setRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        memberRecyclerView.setLayoutManager(layoutManager);
        memberAdapter = new MemberAdapter(users, getContext());
        memberRecyclerView.setAdapter(memberAdapter);
    }

    private void initViews(View view) {
        memberRecyclerView = view.findViewById(R.id.shop_member_RecycleView);
        memberBack = view.findViewById(R.id.shop_member_back_ImageView);
        memberAdd = view.findViewById(R.id.shop_member_add_ImageView);
    }
}
