package com.example.a13834598889.billiards.FragmentShopKepperMine;

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

import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentShopChangeEmail extends Fragment implements View.OnClickListener {

    private CircleImageView backImageView;
    private TextView commitTextView;
    private EditText inputEditText;
    private FragmentManager fragmentManager;
    private String last;

    public static FragmentShopChangeEmail newInstance() {
        FragmentShopChangeEmail fragmentShopChangeEmail = new FragmentShopChangeEmail();
        return fragmentShopChangeEmail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_change_email, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews(view);
        bmobCheck();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_change_email_back_ImageView:
                if (fragmentManager.findFragmentByTag("shop_message_setting_change_email_layout") != null) {
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .remove(this)
                            .commit();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                            .commit();
                }
                break;
            case R.id.shop_change_email_commit_TextView:
                last = inputEditText.getText().toString();
                //对邮箱的正确性检测
                if (last != null) {
                    User user = new User();
                    user.setEmail(last);
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
                }
                if (fragmentManager.findFragmentByTag("shop_message_setting_change_email_layout") != null) {
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
                    inputEditText.setText(object.getEmail());
                } else {
                    Toast.makeText(getActivity(), "bmob获取店铺名失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews(View view) {
        backImageView = view.findViewById(R.id.shop_change_email_back_ImageView);
        commitTextView = view.findViewById(R.id.shop_change_email_commit_TextView);
        inputEditText = view.findViewById(R.id.shop_change_email_EditText);
        backImageView.setOnClickListener(this);
        commitTextView.setOnClickListener(this);
    }
}
