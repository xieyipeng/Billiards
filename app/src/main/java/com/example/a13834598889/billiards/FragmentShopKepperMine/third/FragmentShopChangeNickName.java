package com.example.a13834598889.billiards.FragmentShopKepperMine.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentShopKepperMine.second.FragmentShopMessageSetting;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentShopChangeNickName extends Fragment implements View.OnClickListener {

    private CircleImageView backImageView;
    private TextView commitTextView;
    private EditText inputEditText;
    private FragmentManager fragmentManager;
    private String last;

    public static FragmentShopChangeNickName newInstance() {
        FragmentShopChangeNickName fragmentShopChangeSomething = new FragmentShopChangeNickName();
        return fragmentShopChangeSomething;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_change_nick_name, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews(view);
        bmobCheck();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_change_nick_name_back_ImageView:
                if (fragmentManager.findFragmentByTag("shop_message_setting_store_name_layout") != null) {
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .remove(this)
                            .commit();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                            .commit();
                }
                break;
            case R.id.shop_change_nick_name_commit_TextView:
                last = inputEditText.getText().toString();
                if (last != null) {
                    User user=new User();
                    user.setNickName(last);
                    user.setStore(true);
                    user.update(User.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "已保存", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "bmob修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "店铺名不能为空", Toast.LENGTH_SHORT).show();
                }
                if (fragmentManager.findFragmentByTag("shop_message_setting_store_name_layout") != null) {
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .remove(this)
                            .commit();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                            .commit();
                }
                break;
            default:
                break;
        }
    }

    private void bmobCheck() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    inputEditText.setText(object.getNickName());
                } else {
                    Toast.makeText(getActivity(), "bmob获取店铺名失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews(View view) {
        backImageView = view.findViewById(R.id.shop_change_nick_name_back_ImageView);
        commitTextView = view.findViewById(R.id.shop_change_nick_name_commit_TextView);
        inputEditText = view.findViewById(R.id.shop_change_nick_name_EditText);
        backImageView.setOnClickListener(this);
        commitTextView.setOnClickListener(this);
    }
}
