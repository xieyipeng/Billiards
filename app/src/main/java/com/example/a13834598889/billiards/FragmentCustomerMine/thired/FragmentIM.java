package com.example.a13834598889.billiards.FragmentCustomerMine.thired;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

public class FragmentIM extends Fragment {

    private FragmentManager fragmentManager;

    public static FragmentIM newInstance(){
        return new FragmentIM();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews(view);
        initClisks();
        return view;
    }

    private void initClisks() {

    }

    private void initViews(View view) {


    }
}
