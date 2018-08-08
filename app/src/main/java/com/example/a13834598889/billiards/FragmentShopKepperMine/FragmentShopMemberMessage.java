package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentCustomerOrder.Fragment_billiards;
import com.example.a13834598889.billiards.JavaBean.Member;
import com.example.a13834598889.billiards.JavaBean.Table;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopMessageSetting.staticNickNameTextView;
import static rx.schedulers.Schedulers.test;

public class FragmentShopMemberMessage extends Fragment {

    private MemberAdapter memberAdapter;
    private RecyclerView memberRecyclerView;
    private final List<User> users = new ArrayList<>();

    public static FragmentShopMemberMessage newInstance() {
        FragmentShopMemberMessage fragmentShopMemberMessage = new FragmentShopMemberMessage();
        return fragmentShopMemberMessage;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_member_message, container, false);
        initViews(view);

        //初始化会员数据
        test1();
        return view;
    }

    private void test1() {
        final List<BmobQuery<User>> bmobQueryList = new ArrayList<>();
        final String[] nickName = new String[1];

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    nickName[0] = user.getNickName();
                    getMembers(nickName);
                } else {
                    Log.e(TAG, "done: 获取店铺名错误");
                }
            }
        });
    }

    private void getMembers(final String[] nickName) {
        BmobQuery<Member> memberBmobQuery = new BmobQuery<>();
        memberBmobQuery.findObjects(new FindListener<Member>() {
            @Override
            public void done(List<Member> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getStoreName().equals(nickName[0])) {
                            getUsers(list.get(i).getUserName());
                        }
                    }
                } else {
                    Log.e(TAG, "done: 加载会员界面时出现错误");
                }
            }
        });
    }

    private void getUsers(final String nickname) {
        final BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getNickName().equals(nickname)) {
                            users.add(list.get(i));
                        }
                    }
                    if (users.size()>0){

                        setRecycleView();

                    }else {
                        Toast.makeText(getContext(), "店长，您还没有会员呢", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e(TAG, "done: 获取user时出错: " + e.getMessage());
                }
            }
        });
    }

    private void setRecycleView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        memberRecyclerView.setLayoutManager(layoutManager);
        memberAdapter = new MemberAdapter(users);
        memberRecyclerView.setAdapter(memberAdapter);
    }

    public class MemberHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView memberItemNickNameTextView;
        private TextView memberItemSignTextView;
        private CircleImageView memberPhotoCircleView;

        public MemberHolder(View view) {
            super(view);
            memberItemNickNameTextView=view.findViewById(R.id.shop_member_nick_name);
            memberItemSignTextView=view.findViewById(R.id.shop_member_sign);
            memberPhotoCircleView=view.findViewById(R.id.shop_member_photo);
        }

        @Override
        public void onClick(View view) {
//            switch (view.getId()){
//                //点击事件
//            }
        }

        public void bindView(User user) {
            memberItemNickNameTextView.setText(user.getNickName());
            if (user.getSign()!=null){
                memberItemSignTextView.setText(user.getSign());
            }else {
                memberItemSignTextView.setText(R.string.SnoSign);
            }
            if (user.getPicture_head()!=null){

                //获取BmobFile转化为Bitmap并设置给memberPhotoCircleView
            }else {

            }
        }
    }

    public class MemberAdapter extends RecyclerView.Adapter<MemberHolder> {
        private List<User> member;

        public MemberAdapter(List<User> users) {
            this.member = users;
        }

        @Override
        public MemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.member_item, parent, false);
            return new MemberHolder(view);
        }

        @Override
        public void onBindViewHolder(MemberHolder holder, int position) {
            User user = member.get(position);
            holder.bindView(user);
        }

        @Override
        public int getItemCount() {
            return member.size();
        }
    }

    private void initViews(View view) {
        memberRecyclerView = view.findViewById(R.id.shop_member_RecycleView);
    }
}
