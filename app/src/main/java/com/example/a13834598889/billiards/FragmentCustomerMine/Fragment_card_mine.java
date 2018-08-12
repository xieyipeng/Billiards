package com.example.a13834598889.billiards.FragmentCustomerMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13834598889.billiards.R;

/**
 * Created by 13834598889 on 2018/5/4.
 */

public class Fragment_card_mine extends Fragment {

    public static Fragment_card_mine newInstance(){
        Fragment_card_mine fragment_card_mine=new Fragment_card_mine();
        return fragment_card_mine;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_mine,container,false);
        return view;
    }
}
