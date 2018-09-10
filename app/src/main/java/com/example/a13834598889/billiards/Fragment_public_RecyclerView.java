package com.example.a13834598889.billiards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 13834598889 on 2018/5/7.
 */

public class Fragment_public_RecyclerView extends Fragment {
    private RecyclerView recyclerView_public;
    private static String resyslerName = "";

    public static Fragment_public_RecyclerView newInstance(String resyslerName){
        Fragment_public_RecyclerView.resyslerName = resyslerName;
        return new Fragment_public_RecyclerView();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_recycler_view,container,false);

        if(this.resyslerName.equals("account")){

        }
        return view;
    }
}
