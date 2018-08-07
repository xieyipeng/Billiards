package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

import static android.support.constraint.Constraints.TAG;

public class FragmentShopChangeSomething extends Fragment{
    public static FragmentShopChangeSomething newInstance() {
        FragmentShopChangeSomething fragmentShopChangeSomething = new FragmentShopChangeSomething();
        return fragmentShopChangeSomething;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_change_somethings, container, false);
        return view;
    }
}
