package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

public class FragmentShopMessageSetting extends Fragment implements View.OnClickListener {

    private CircleImageView backImageView;
    private LinearLayout nickNameSetting;
    private LinearLayout headPictureSetting;
    private LinearLayout passWordSetting;
    private LinearLayout phoneNumberSetting;
    private LinearLayout emailSetting;
    private LinearLayout signSetting;

    private FragmentManager fragmentManager;


    public static FragmentShopMessageSetting newInstance() {
        FragmentShopMessageSetting fragmentShopMessageSetting = new FragmentShopMessageSetting();
        return fragmentShopMessageSetting;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_message_setting, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out).hide(this).commit();
        switch (v.getId()) {
            case R.id.shop_message_setting_store_name_layout:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopChangeSomething.newInstance(),"shop_message_setting_store_name_layout")
                        .commit();
                break;
            case R.id.shop_message_setting_change_email_layout:
                break;
            case R.id.shop_message_setting_change_password_layout:
                break;
            case R.id.shop_message_setting_change_phone_number_layout:
                break;
            case R.id.shop_message_setting_change_sign_layout:
                break;
            default:
                break;
        }
    }

    private void initViews(View view) {
        backImageView = view.findViewById(R.id.shop_message_setting_back_ImageView);
        nickNameSetting = view.findViewById(R.id.shop_message_setting_store_name_layout);
        headPictureSetting = view.findViewById(R.id.shop_message_setting_profile_photo_layout);
        passWordSetting = view.findViewById(R.id.shop_message_setting_change_password_layout);
        phoneNumberSetting = view.findViewById(R.id.shop_message_setting_change_phone_number_layout);
        emailSetting = view.findViewById(R.id.shop_message_setting_change_email_layout);
        signSetting = view.findViewById(R.id.shop_message_setting_change_sign_layout);
        backImageView.setOnClickListener(this);
        nickNameSetting.setOnClickListener(this);
        headPictureSetting.setOnClickListener(this);
        passWordSetting.setOnClickListener(this);
        phoneNumberSetting.setOnClickListener(this);
        emailSetting.setOnClickListener(this);
        signSetting.setOnClickListener(this);
    }
}
