package com.example.a13834598889.billiards.FragmentCustomerMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class Fragment_mine extends Fragment {

    private TextView textView_button_tiezi;
    private TextView textView_button_qiuyou;
    private TextView textView_button_yuding;
    private TextView textView_button_qiubi;
    private TextView textView_button_bangzhu;

    private ImageView imageView_edit;
    private FragmentManager fragmentManager;
    private Fragment fragmentTest;

    public static Fragment_mine newInstance() {
        Fragment_mine fragment_mine = new Fragment_mine();
        return fragment_mine;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        fragmentManager=getActivity().getSupportFragmentManager();
        initViews(view);
        initClicks();
        return view;
    }

    private void initClicks() {
        textView_button_tiezi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
//                if (fragmentManager.findFragmentByTag("card_fragment") != null) {
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                            .show(fragmentManager.findFragmentByTag("card_fragment"))
//                            .commit();
//                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .add(R.id.fragment_container, Fragment_card.newInstance(), "card_fragment")
                            .commit();
//                }
            }
        });
    }

    private void hideFragment() {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTest = fragmentManager.findFragmentById(R.id.fragment_container);
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(fragmentTest)
                .commit();
    }

    private void initViews(View view) {
        CircleImageView circleImageView = view.findViewById(R.id.circleImageView_mine00);
        textView_button_tiezi = view.findViewById(R.id.text_button_wodetiezi);
        textView_button_qiuyou = view.findViewById(R.id.text_button_wodeqiuyou);
        textView_button_yuding = view.findViewById(R.id.text_button_wodeyuding);
        textView_button_qiubi = view.findViewById(R.id.text_button_wodeqiubi);
        textView_button_bangzhu = view.findViewById(R.id.text_button_bangzhu);
        imageView_edit = view.findViewById(R.id.image_button_edit);
        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView);
    }
}