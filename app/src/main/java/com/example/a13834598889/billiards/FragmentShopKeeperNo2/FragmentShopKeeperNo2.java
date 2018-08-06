package com.example.a13834598889.billiards.FragmentShopKeeperNo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

public class FragmentShopKeeperNo2 extends Fragment {
    public static FragmentShopKeeperNo2 newInstance(){
        FragmentShopKeeperNo2 fragmentShopKeeperNo2=new FragmentShopKeeperNo2();
        return fragmentShopKeeperNo2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_keeper_no2,container,false);


        return view;
    }
}
