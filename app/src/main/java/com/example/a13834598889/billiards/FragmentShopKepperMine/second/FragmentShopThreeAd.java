package com.example.a13834598889.billiards.FragmentShopKepperMine.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

public class FragmentShopThreeAd extends Fragment {
    public static FragmentShopThreeAd newInstance(){
        FragmentShopThreeAd fragmentShopThreeAd=new FragmentShopThreeAd();
        return fragmentShopThreeAd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_three_ad, container, false);
        return view;
    }
}
