package com.example.a13834598889.billiards.FragmentShopKepperMine.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentShopKepperMine.second.FragmentShopMemberMessage;
import com.example.a13834598889.billiards.JavaBean.Member;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.bmob.v3.BmobRealTimeData.TAG;

public class FragmentShopAddMember extends Fragment {

    private CircleImageView backImageView;
    private TextView commitTextView;
    private EditText inputEditText;
    private FragmentManager fragmentManager;
    private Fragment fragmentTest;

    public static FragmentShopAddMember newInstance() {
        FragmentShopAddMember fragmentShopAddMember = new FragmentShopAddMember();
        return fragmentShopAddMember;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_add_member, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews(view);
        initClicks();
        return view;
    }

    private void initClicks() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .remove(fragmentTest)
                        .add(R.id.fragment_container, FragmentShopMemberMessage.newInstance(), "shop_keeper_mine_members_message")
                        .commit();
            }
        });
        commitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inputNickName = inputEditText.getText().toString();
                getShopNickName(inputNickName);
            }
        });
    }

    private void getShopNickName(final String inputNickName) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Member member = new Member();
                    member.setStoreName(user.getNickName());
                    setMemberUserNickName(member, inputNickName);
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

    private void setMemberUserNickName(final Member member, final String inputNickName) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: list.size " + list.size());
                    boolean have = false;
                    for (int i = 0; i < list.size(); i++) {
                        Log.e(TAG, "done: " + list.get(i).getNickName());
                        if (list.get(i).getNickName().equals(inputNickName)) {
                            have = true;
                            member.setNickName(list.get(i).getNickName());
                            Log.e(TAG, "done: nickName:" + member.getStoreName());
                            member.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getContext(), "添加会员成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (e.getMessage().equals("unique index cannot has duplicate value: newName")) {
                                            Toast.makeText(getContext(), "您已经添加了该会员", Toast.LENGTH_SHORT).show();
                                        }
                                        Toast.makeText(getContext(), "添加会员失败", Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "done: 保存会员失败 " + e.getMessage());
                                    }
                                }
                            });
                            break;
                        }
                    }
                    if (!have) {
                        Toast.makeText(getContext(), "没有该名字", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "done: 添加会员界面，查找姓名时,服务器没有该名字");
                    }
                    fragmentTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .remove(fragmentTest)
                            .add(R.id.fragment_container, FragmentShopMemberMessage.newInstance(), "shop_keeper_mine_members_message")
                            .commit();
                } else {
                    Log.e(TAG, "done: 添加会员界面，查找姓名时失败 " + e.getMessage());
                }
            }
        });
    }

    private void initViews(View view) {
        backImageView = view.findViewById(R.id.shop_add_member_back_ImageView);
        commitTextView = view.findViewById(R.id.shop_add_member_commit_TextView);
        inputEditText = view.findViewById(R.id.shop_add_member_EditText);
    }
}
