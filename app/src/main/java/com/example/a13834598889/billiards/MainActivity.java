package com.example.a13834598889.billiards;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.example.a13834598889.billiards.FragmentCustomerMine.Fragment_mine;
import com.example.a13834598889.billiards.FragmentCustomerOrder.Fragment_order;
import com.example.a13834598889.billiards.FragmentCustomerShare.Fragment_share;
import com.example.a13834598889.billiards.FragmentCustomerTeach.Fragment_teach;
import com.example.a13834598889.billiards.FragmentShopKeeperNo1.FragmentShopKeeperNo1;
import com.example.a13834598889.billiards.FragmentShopKeeperNo2.FragmentShopKeeperNo2;
import com.example.a13834598889.billiards.FragmentShopKeeperNo3.FragmentShopKeeperNo3;
import com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopKeeperMine;
import com.example.a13834598889.billiards.FragmentShopKepperMine.second.FragmentShopMemberMessage;
import com.example.a13834598889.billiards.FragmentShopKepperMine.second.FragmentShopMessageSetting;
import com.example.a13834598889.billiards.JavaBean.User;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends AppCompatActivity {
    private Fragment save_fragment_mine;
    private Fragment save_fragment_order;
    private Fragment save_fragment_share;
    private Fragment save_fragment_teach;

    private Fragment shop_fragment_mine;
    private Fragment shop_fragment_no1;
    private Fragment shop_fragment_no2;
    private Fragment shop_fragment_no3;

    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private boolean isStore;
    private static final String TAG = "MainActivity";
    private BottomNavigationView customerNavigation;
    private BottomNavigationView shopNavigation;

    private Fragment fragmentTest;

    public static File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        path = getExternalCacheDir();
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initViews();
        bmobCheckStore();
    }

    @Override
    public void onBackPressed() {
        fragmentTest = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        find_jude();
        caseStore();
        caseCustomer();
        String[] mainString = new String[]{
                "shop_fragment_mine",
                "shop_fragment_snacks",
                "shop_fragment_bill",
                "shop_fragment_table",
                "fragment_mine",
                "fragment_share",
                "fragment_order",
                "fragment_teach"};
        for (String tag : mainString) {
            if (tag.equals(fragmentTest.getTag())) {
                finish();
            }
        }
    }

    private void caseCustomer() {
        //2 -> 我的信息
        String[] toMineMessage = new String[]{
                "card_fragment"};
        for (String tag : toMineMessage) {
            if (tag.equals(fragmentTest.getTag())) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .show(fragmentManager.findFragmentByTag("fragment_mine"))
                        .commit();
            }
        }
    }

    private void caseStore() {
        //shop 2 -> 我的信息
        String[] toMineMessage = new String[]{
                "shop_keeper_mine_help",
                "shop_keeper_mine_message_setting",
                "shop_keeper_mine_members_message",
                "shop_keeper_mine_three_ad",
                "shop_keeper_mine_store_location"};
        for (String tag : toMineMessage) {
            if (tag.equals(fragmentTest.getTag())) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .show(fragmentManager.findFragmentByTag("shop_fragment_mine"))
                        .commit();
            }
        }

        //shop 3 -> 基本信息设置
        String[] toBaseMessage = new String[]{
                "shop_message_setting_store_name_layout",
                "shop_message_setting_change_email_layout",
                "shop_message_setting_change_password_layout",
                "shop_message_setting_change_phone_number_layout"};
        for (String tag : toBaseMessage) {
            if (tag.equals(fragmentTest.getTag())) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                        .commit();
            }
        }

        //shop 3 -> 会员信息
        String[] toMemberMessage = new String[]{
                "shop_member_add_ImageView"};
        for (String tag : toMemberMessage) {
            if (tag.equals(fragmentTest.getTag())) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopMemberMessage.newInstance(), "shop_keeper_mine_members_message")
                        .commit();
            }
        }
    }

    private void loadBottomMenu() {
        if (isStore) {
            customerNavigation.setVisibility(View.GONE);
            shopNavigation.setVisibility(View.VISIBLE);
            shopNavigation.setOnNavigationItemSelectedListener(shopKeeperBottomView);
            fragmentManager = getSupportFragmentManager();
            fragment = FragmentShopKeeperNo1.newInstance();
            shop_fragment_no1 = fragment;
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, fragment, "shop_fragment_no1")
                    .commit();
            int[][] states = new int[][]{
                    new int[]{-android.R.attr.state_checked},
                    new int[]{android.R.attr.state_checked}
            };
            int[] colors = new int[]{ContextCompat.getColor(this, R.color.toolbar_and_menu_color),
                    ContextCompat.getColor(this, R.color.testColor)
            };
            ColorStateList colorStateList = new ColorStateList(states, colors);
            shopNavigation.setItemTextColor(colorStateList);
            shopNavigation.setItemIconTintList(colorStateList);
        } else {
            shopNavigation.setVisibility(View.GONE);
            customerNavigation.setVisibility(View.VISIBLE);
            customerNavigation.setOnNavigationItemSelectedListener(customBottomView);
            fragmentManager = getSupportFragmentManager();
            fragment = Fragment_order.newInstance();
            save_fragment_order = fragment;
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, fragment, "fragment_order")
                    .commit();
            int[][] states = new int[][]{
                    new int[]{-android.R.attr.state_checked},
                    new int[]{android.R.attr.state_checked}
            };
            int[] colors = new int[]{ContextCompat.getColor(this, R.color.toolbar_and_menu_color),
                    ContextCompat.getColor(this, R.color.testColor)
            };
            ColorStateList colorStateList = new ColorStateList(states, colors);
            customerNavigation.setItemTextColor(colorStateList);
            customerNavigation.setItemIconTintList(colorStateList);
        }
    }

    /**
     * 检查是否店家
     */
    private void bmobCheckStore() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    if (object.isStore()) {
                        isStore = true;
                    } else {
                        isStore = false;
                    }
                } else {
                    Log.e(TAG, "done: 检查店家或球友失败，错误：" + e.getMessage());
                }
                loadBottomMenu();
            }
        });
    }

    private void find_jude() {
        String[] customerJudeString = new String[]{
                "friends_fragment",
                "card_fragment",
                "account_fragment"};
        for (String tag : customerJudeString) {
            if (fragmentManager.findFragmentByTag(tag) != null) {
                fragmentManager.beginTransaction()
                        .hide(fragmentManager.findFragmentByTag(tag))
                        .remove(fragmentManager.findFragmentByTag(tag))
                        .commit();
            }
        }
        String[] shopJudeString = new String[]{
                "shop_keeper_mine_message_setting",
                "shop_keeper_mine_members_message",
                "shop_keeper_mine_three_ad",
                "shop_keeper_mine_store_location",
                "shop_keeper_mine_help",
                "shop_message_setting_store_name_layout",
                "shop_message_setting_change_email_layout",
                "shop_keeper_mine_members_message",
                "shop_member_add_ImageView",
                "shop_message_setting_change_password_layout",
                "shop_message_setting_change_phone_number_layout"};
        for (String tag : shopJudeString) {
            if (fragmentManager.findFragmentByTag(tag) != null) {
                fragmentManager.beginTransaction()
                        .hide(fragmentManager.findFragmentByTag(tag))
                        .remove(fragmentManager.findFragmentByTag(tag))
                        .commit();
            }
        }
    }

    private void initViews() {
        customerNavigation = findViewById(R.id.navigation);
        shopNavigation = findViewById(R.id.shop_navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener customBottomView
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            find_jude();
            switch (item.getItemId()) {
                case R.id.navigation_teach:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_teach == null) {
                        fragment = Fragment_teach.newInstance();
                        save_fragment_teach = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_teach")
                                .commit();
                    } else {
                        fragment = save_fragment_teach;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.navigation_order:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_order == null) {
                        fragment = Fragment_order.newInstance();
                        save_fragment_order = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_order")
                                .commit();
                    } else {
                        fragment = save_fragment_order;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.navigation_share:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_share == null) {
                        fragment = Fragment_share.newInstance();
                        save_fragment_share = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_share")
                                .commit();
                    } else {
                        fragment = save_fragment_share;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.navigation_mine:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_mine == null) {
                        fragment = Fragment_mine.newInstance();
                        save_fragment_mine = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_mine")
                                .commit();
                    } else {
                        fragment = save_fragment_mine;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .show(fragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener shopKeeperBottomView
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            find_jude();
            switch (item.getItemId()) {
                //我的
                case R.id.shop_keeper_navigation_mine:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_mine == null) {
                        fragment = FragmentShopKeeperMine.newInstance();
                        shop_fragment_mine = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_mine")
                                .commit();
                    } else {
                        fragment = shop_fragment_mine;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_order:
                    //球桌
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no1 == null) {
                        fragment = FragmentShopKeeperNo1.newInstance();
                        shop_fragment_no1 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_table")
                                .commit();
                    } else {
                        fragment = shop_fragment_no1;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_share:
                    //账单
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no3 == null) {
                        fragment = FragmentShopKeeperNo3.newInstance();
                        shop_fragment_no3 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_bill")
                                .commit();
                    } else {
                        fragment = shop_fragment_no3;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_teach:
                    //零食
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no2 == null) {
                        fragment = FragmentShopKeeperNo2.newInstance();
                        shop_fragment_no2 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_snacks")
                                .commit();
                    } else {
                        fragment = shop_fragment_no2;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
            }
            return false;
        }
    };

    public static String getPathByUri(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
