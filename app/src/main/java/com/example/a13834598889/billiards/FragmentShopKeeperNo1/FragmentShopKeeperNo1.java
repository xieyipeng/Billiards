package com.example.a13834598889.billiards.FragmentShopKeeperNo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

public class FragmentShopKeeperNo1 extends Fragment {
    public static FragmentShopKeeperNo1 newInstance(){
        FragmentShopKeeperNo1 fragmentShopKeeperNo1=new FragmentShopKeeperNo1();
        return fragmentShopKeeperNo1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_keeper_no1,container,false);


        return view;
    }
}
