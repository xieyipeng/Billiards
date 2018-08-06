package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentShopKeeperMine extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
//    private CircleImageView profilePhoto; //android:id="@+id/shop_keeper_mine_profile_photo"
    private LinearLayout mineMessageSetting;
    private LinearLayout membersSetting;
    private LinearLayout threeAd;
    private LinearLayout mineLocation;
    private LinearLayout helpLayout;

    public static FragmentShopKeeperMine newInstance() {
        FragmentShopKeeperMine fragmentShopKeeperMine = new FragmentShopKeeperMine();
        return fragmentShopKeeperMine;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_keeper_mine, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out).hide(this).commit();
        switch (v.getId()) {
            case R.id.shop_keeper_mine_message_setting:
                //个人信息设置
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                        .commit();
                break;
            case R.id.shop_keeper_mine_members_message:
                //会员信息
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopMemberMessage.newInstance(), "shop_keeper_mine_members_message")
                        .commit();
                break;
            case R.id.shop_keeper_mine_three_ad:
                //三个广告
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopThreeAd.newInstance(), "shop_keeper_mine_three_ad")
                        .commit();
                break;
            case R.id.shop_keeper_mine_store_location:
                //地理位置
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopLocation.newInstance(), "shop_keeper_mine_store_location")
                        .commit();
                break;
            case R.id.shop_keeper_mine_help:
                //帮助
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopHelp.newInstance(), "shop_keeper_mine_help")
                        .commit();
                break;
            default:
                break;
        }
    }

    private void initViews(View view) {
//        profilePhoto = view.findViewById(R.id.shop_keeper_mine_profile_photo);
        mineMessageSetting = view.findViewById(R.id.shop_keeper_mine_message_setting);
        membersSetting = view.findViewById(R.id.shop_keeper_mine_members_message);
        threeAd = view.findViewById(R.id.shop_keeper_mine_three_ad);
        mineLocation = view.findViewById(R.id.shop_keeper_mine_store_location);
        helpLayout = view.findViewById(R.id.shop_keeper_mine_help);
//        profilePhoto.setOnClickListener(this);
        mineMessageSetting.setOnClickListener(this);
        membersSetting.setOnClickListener(this);
        threeAd.setOnClickListener(this);
        mineLocation.setOnClickListener(this);
        helpLayout.setOnClickListener(this);
    }
}
