package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

public class FragmentShopMemberMessage extends Fragment {
    public static FragmentShopMemberMessage newInstance(){
        FragmentShopMemberMessage fragmentShopMemberMessage=new FragmentShopMemberMessage();
        return fragmentShopMemberMessage;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_member_message, container, false);
        return view;
    }
}
