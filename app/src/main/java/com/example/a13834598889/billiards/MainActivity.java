package com.example.a13834598889.billiards;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.a13834598889.billiards.FragmentCustomerMine.Fragment_mine;
import com.example.a13834598889.billiards.FragmentCustomerOrder.Fragment_order;
import com.example.a13834598889.billiards.FragmentCustomerShare.Fragment_share;
import com.example.a13834598889.billiards.FragmentCustomerTeach.Fragment_teach;
import com.example.a13834598889.billiards.FragmentShopKeeperNo1.FragmentShopKeeperNo1;
import com.example.a13834598889.billiards.FragmentShopKeeperNo2.FragmentShopKeeperNo2;
import com.example.a13834598889.billiards.FragmentShopKeeperNo3.FragmentShopKeeperNo3;
import com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopKeeperMine;
import com.example.a13834598889.billiards.JavaBean.User;

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
    private boolean isStore = false;
    private final String TAG = "MainActivity";


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
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
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
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no1 == null) {
                        fragment = FragmentShopKeeperNo1.newInstance();
                        shop_fragment_no1 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_no1")
                                .commit();
                    } else {
                        fragment = shop_fragment_no1;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_share:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no3 == null) {
                        fragment = FragmentShopKeeperNo3.newInstance();
                        shop_fragment_no3 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_no3")
                                .commit();
                    } else {
                        fragment = shop_fragment_no3;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_teach:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no2 == null) {
                        fragment = FragmentShopKeeperNo2.newInstance();
                        shop_fragment_no2 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_no2")
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        bmobCheckStore();

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    if (object.isStore()) {
                        isStore = true;
                        Log.e(TAG, "done: " + "店家好 " + object.isStore());
                        Toast.makeText(MainActivity.this, "店家好", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "done: " + "球友好 " + object.isStore());
                        Toast.makeText(MainActivity.this, "球友好", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "done: 检查店家或球友失败，错误：" + e.getMessage());
                }
                Log.e(TAG, "done: "+"has checked" );
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        Log.e(TAG, "onCreate: isStore:"+isStore );
        if (isStore){
            Log.e(TAG, "onCreate: "+"dianjia" );
            navigation.setOnNavigationItemSelectedListener(shopKeeperBottomView);
            fragmentManager = getSupportFragmentManager();
            fragment = FragmentShopKeeperNo1.newInstance();
            shop_fragment_no1 = fragment;
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, fragment, "fragment_order")
                    .commit();
        }else {
            Log.e(TAG, "onCreate: "+"qiuyou" );
            navigation.setOnNavigationItemSelectedListener(customBottomView);
            fragmentManager = getSupportFragmentManager();
            fragment = Fragment_order.newInstance();
            save_fragment_order = fragment;
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, fragment, "fragment_order")
                    .commit();
        }
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{ContextCompat.getColor(this, R.color.toolbar_and_menu_color),
                ContextCompat.getColor(this, R.color.testColor)
        };
        ColorStateList colorStateList = new ColorStateList(states, colors);
        navigation.setItemTextColor(colorStateList);
        navigation.setItemIconTintList(colorStateList);
    }

    /**
     * 检查是否店家
     */
    private void bmobCheckStore() {

    }


    private void find_jude() {
        if (fragmentManager.findFragmentByTag("friends_fragment") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("friends_fragment"))
                    .remove(fragmentManager.findFragmentByTag("friends_fragment"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("card_fragment") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("card_fragment"))
                    .remove(fragmentManager.findFragmentByTag("card_fragment"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("account_fragment") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("account_fragment"))
                    .remove(fragmentManager.findFragmentByTag("account_fragment"))
                    .commit();
        }
    }
}
