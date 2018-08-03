package com.example.a13834598889.billiards.Fragment_Teach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class Fragment_teach extends Fragment {
    private CircleImageView circleImageView2;

    public static Fragment_teach newInstance(){
        Fragment_teach fragment_teach = new Fragment_teach();
        return fragment_teach;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teach,container,false);
        circleImageView2 = (CircleImageView)view.findViewById(R.id.circleImageView_mine2);
        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView2);
        return view;
    }
}
