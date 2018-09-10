package com.example.a13834598889.billiards.FragmentCustomerMine.thired;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentCustomerMine.second.Fragment_friends;
import com.example.a13834598889.billiards.JavaBean.Friend;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.BmobRealTimeData.TAG;

public class Fragment_Friend_Add extends Fragment {

    private ImageView backImageView;
    private TextView commitTextView;
    private EditText inputEditText;
    private FragmentManager fragmentManager;


    public static Fragment_Friend_Add newInstance(){
        return new Fragment_Friend_Add();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_add, container, false);
        fragmentManager=getActivity().getSupportFragmentManager();
        initViews(view);
        initClisks();
        return view;
    }

    private void initClisks() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.findFragmentByTag("circleImageView_mine88") != null) {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("circleImageView_mine88"))
                            .remove(fragmentManager.findFragmentByTag("circleImageView_mine88"))
                            .add(R.id.fragment_container, Fragment_friends.newInstance(),"text_button_wodeqiuyou")
                            .commit();
                }
            }
        });
        commitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user=new User();
                user.setUsername(inputEditText.getText().toString());
                BmobQuery<User> userBmobQuery=new BmobQuery<>();
                userBmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e==null){
                            getUser(list, user);
                        }else {
                            Log.e(TAG, "done: "+e.getMessage() );
                        }
                    }
                });
            }

            private void getUser(List<User> list, User user) {
                boolean have=false;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUsername().equals(user.getUsername())){
                        if (list.get(i).getObjectId().equals(User.getCurrentUser(User.class).getObjectId())){
                            Toast.makeText(getContext(), "不能添加自己", Toast.LENGTH_SHORT).show();
                        }else{
                            have=true;
                            final User testUser=list.get(i);
                            BmobQuery<Friend> query=new BmobQuery<>();
                            query.findObjects(new FindListener<Friend>() {
                                @Override
                                public void done(List<Friend> list, BmobException e) {
                                    boolean isFriend=false;
                                    for (int j = 0; j < list.size(); j++) {
                                        if (list.get(j).getUser().getObjectId().equals(User.getCurrentUser().getObjectId())&&
                                                list.get(j).getFriendUser().getObjectId().equals(testUser.getObjectId())){
                                            isFriend=true;
                                        }
                                    }
                                    if (isFriend){
                                        Toast.makeText(getContext(), "你们已经是好友了", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Friend friend=new Friend();
                                        friend.setUser(User.getCurrentUser(User.class));
                                        friend.setFriendUser(testUser);
                                        friend.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e==null){
                                                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                                    if (fragmentManager.findFragmentByTag("circleImageView_mine88") != null) {
                                                        fragmentManager.beginTransaction()
                                                                .hide(fragmentManager.findFragmentByTag("circleImageView_mine88"))
                                                                .remove(fragmentManager.findFragmentByTag("circleImageView_mine88"))
                                                                .add(R.id.fragment_container, Fragment_friends.newInstance(),"text_button_wodeqiuyou")
                                                                .commit();
                                                    }
                                                }else {
                                                    Log.e(TAG, "done: "+e.getMessage() );
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
                if (!have){
                    Toast.makeText(getContext(), "不存在此账号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews(View view) {
        backImageView=view.findViewById(R.id.customer_add_friend_back);
        commitTextView=view.findViewById(R.id.customer_add_friend_commit);
        inputEditText=view.findViewById(R.id.customer_add_friend_input);
    }
}
