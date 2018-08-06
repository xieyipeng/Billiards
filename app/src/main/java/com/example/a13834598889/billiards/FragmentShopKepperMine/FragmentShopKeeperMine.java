package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentShopKeeperMine extends Fragment {
    public static FragmentShopKeeperMine newInstance(){
        FragmentShopKeeperMine fragmentShopKeeperMine=new FragmentShopKeeperMine();
        return fragmentShopKeeperMine;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_keeper_mine,container,false);


        return view;
    }
}
