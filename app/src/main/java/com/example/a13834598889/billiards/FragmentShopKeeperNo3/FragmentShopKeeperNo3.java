package com.example.a13834598889.billiards.FragmentShopKeeperNo3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

public class FragmentShopKeeperNo3 extends Fragment{
    public static FragmentShopKeeperNo3 newInstance(){
        FragmentShopKeeperNo3 fragmentShopKeeperNo3=new FragmentShopKeeperNo3();
        return fragmentShopKeeperNo3;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_keeper_no3,container,false);


        return view;
    }
}
