package com.example.a13834598889.billiards.FragmentCustomerMine.second;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentCustomerMine.thired.FragmentIM;
import com.example.a13834598889.billiards.FragmentCustomerMine.thired.Fragment_Friend_Add;
import com.example.a13834598889.billiards.JavaBean.Customer;
import com.example.a13834598889.billiards.JavaBean.Friend;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.MainActivity;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.DemoMessageHandler;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.BmobRealTimeData.TAG;
import static com.example.a13834598889.billiards.MainActivity.getMyProcessName;


/**
 * Created by 13834598889 on 2018/5/3.
 */

public class Fragment_friends extends Fragment {

    private RecyclerView recyclerView_friends;
    private List<User> users = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private ImageView addImageView;
    private ImageView imageView_back;
    private boolean isLast = false;

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

    private void initData() {
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
                    Log.e(TAG, "done: " + stringList.size());
                    if (stringList.size() != 0) {
                        for (int i = 0; i < stringList.size(); i++) {
                            if (i == (stringList.size() - 1)) {
                                isLast = true;
                            }
                            final User user = new User();
                            user.setObjectId(stringList.get(i));
                            BmobQuery<User> userBmobQuery = new BmobQuery<>();
                            userBmobQuery.findObjects(new FindListener<User>() {
                                @Override
                                public void done(List<User> list, BmobException e) {
                                    if (e == null) {
                                        for (int j = 0; j < list.size(); j++) {
                                            if (list.get(j).getObjectId().equals(user.getObjectId())) {
                                                users.add(list.get(j));
                                            }
                                        }
                                        if (isLast) {
                                            Log.e(TAG, "done: 次数 ");
                                            setRecycleViews();
                                        }
                                        Log.e(TAG, "done: 好友数量： " + users.size());
                                    } else {
                                        Log.e(TAG, "done: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

    private void setRecycleViews() {
        ContactsAdapter adapter = new ContactsAdapter(users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_friends.setLayoutManager(layoutManager);
        recyclerView_friends.setAdapter(adapter);
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


    public class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder> {
        private List<User> users;

        public ContactsAdapter(List<User> users) {
            this.users = users;
        }

        @Override
        public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.friend_item, parent, false);
            return new ContactsHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactsHolder holder, int position) {
            final User user = users.get(position);
            holder.bindView(user);
            holder.imLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()){
                        Toast.makeText(getContext(), "im未连接，正在重新连接im...", Toast.LENGTH_SHORT).show();
                        BmobIM.connect(user.getObjectId(), new ConnectListener() {
                            @Override
                            public void done(String uid, BmobException e) {
                                if (e == null) {
                                    //连接成功
                                    Toast.makeText(getContext(), "im连接成功", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "done: im连接成功");
                                } else {
                                    //连接失败
                                    Log.e(TAG, "done: " + e.getMessage());
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
//                        BmobIMUserInfo info = new BmobIMUserInfo(User.getCurrentUser().getObjectId(), "123", "touxiang");

                        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getNickName(), "touxiang");
                        BmobIMConversation conversation = BmobIM.getInstance().startPrivateConversation(info, null);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("c", conversation);
                        MainActivity.customerNavigation.setVisibility(View.GONE);
                        MainActivity.shopNavigation.setVisibility(View.GONE);
                        fragmentManager.beginTransaction()
                                .hide(fragmentManager.findFragmentByTag("text_button_wodeqiuyou"))
                                .remove(fragmentManager.findFragmentByTag("text_button_wodeqiuyou"))
                                .add(R.id.fragment_container, FragmentIM.newInstance(user.getNickName(),
                                        user.getObjectId(), bundle), "im_Layout")
                                .commit();
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return users.size();
        }
    }

    public class ContactsHolder extends RecyclerView.ViewHolder {

        private TextView text_view_ni_cheng;
        private TextView text_view_ge_xing_qian_ming;
        private ImageView imageView_TouXiang;
        private LinearLayout imLayout;

        public ContactsHolder(View view) {
            super(view);
            text_view_ge_xing_qian_ming = view.findViewById(R.id.ge_xing_qian_ming_text_view);
            text_view_ni_cheng = view.findViewById(R.id.ni_cheng_text_view);
            imageView_TouXiang = view.findViewById(R.id.tou_xiang_image_view);
            imLayout = view.findViewById(R.id.im_Layout);
        }

        public void bindView(User user) {
            if (user.getSign() != null) {
                text_view_ge_xing_qian_ming.setText(user.getSign());
            } else {
                text_view_ge_xing_qian_ming.setText(R.string.SnoSign);
            }
            text_view_ni_cheng.setText(user.getNickName());
            if (user.getPicture_head() != null) {
                user.getPicture_head().download(new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            imageView_TouXiang.setImageBitmap(BitmapFactory.decodeFile(s));
                        } else {
                            Log.e(TAG, "done: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {

                    }
                });
            }
        }
    }

    private void initViews(View view) {
        addImageView = view.findViewById(R.id.circleImageView_mine88);
        recyclerView_friends = view.findViewById(R.id.contacts_list_recycler_view);
        imageView_back = view.findViewById(R.id.fragment_friends_back);
    }
}
