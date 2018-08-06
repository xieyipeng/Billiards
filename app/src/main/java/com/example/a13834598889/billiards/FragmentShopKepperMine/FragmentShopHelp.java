package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a13834598889.billiards.R;

public class FragmentShopHelp extends Fragment implements View.OnClickListener{

    private ImageView helpBackImageView;

    public static FragmentShopHelp newInstance(){
        FragmentShopHelp fragmentShopHelp=new FragmentShopHelp();
        return fragmentShopHelp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_help, container, false);

        initViews(view);

        return view;
    }


    private void initViews(View view) {
        helpBackImageView=view.findViewById(R.id.help_back_ImageView);
        helpBackImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.help_back_ImageView:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(fragmentManager.findFragmentByTag("shop_keeper_mine_help") !=null){
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("shop_keeper_mine_help"))
                            .remove(fragmentManager.findFragmentByTag("shop_keeper_mine_help"))
                            .show(fragmentManager.findFragmentByTag("shop_fragment_mine"))
                            .commit();
                }
                break;
            default:
                break;
        }
    }
}


