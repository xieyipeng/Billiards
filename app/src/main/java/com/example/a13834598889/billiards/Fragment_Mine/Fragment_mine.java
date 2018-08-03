package com.example.a13834598889.billiards.Fragment_Mine;

import android.content.Intent;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.Fragment_Order.Fragment_order;
import com.example.a13834598889.billiards.OtherActivity.Billards;
import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class Fragment_mine extends Fragment implements View.OnClickListener{

    private CircleImageView circleImageView;

    private FragmentManager fragmentManager;


    private TextView textView_button_tiezi;
    private TextView textView_button_qiuyou;
    private TextView textView_button_yuding;
    private TextView textView_button_qiubi;
    private TextView textView_button_bangzhu;

    private ImageView imageView_edit;


    public static Fragment_mine newInstance(){
        Fragment_mine fragment_mine = new Fragment_mine();
        return fragment_mine;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        circleImageView = (CircleImageView)view.findViewById(R.id.circleImageView_mine00);

        textView_button_tiezi = (TextView)view.findViewById(R.id.text_button_wodetiezi) ;
        textView_button_qiuyou = (TextView)view.findViewById(R.id.text_button_wodeqiuyou) ;
        textView_button_yuding = (TextView)view.findViewById(R.id.text_button_wodeyuding) ;
        textView_button_qiubi = (TextView)view.findViewById(R.id.text_button_wodeqiubi) ;
        textView_button_bangzhu = (TextView)view.findViewById(R.id.text_button_bangzhu) ;
        imageView_edit = (ImageView)view.findViewById(R.id.image_button_edit);
        textView_button_tiezi.setOnClickListener(this);
        textView_button_qiuyou.setOnClickListener(this);
        textView_button_yuding.setOnClickListener(this);
        textView_button_qiubi.setOnClickListener(this);
        textView_button_bangzhu.setOnClickListener(this);
        imageView_edit.setOnClickListener(this);


        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView);
        return view;
    }

    @Override
    public void onClick(View view) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out).hide(this).commit();


        switch (view.getId()){
            case R.id.text_button_wodetiezi:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                        .add(R.id.fragment_container,Fragment_card.newInstance(),"card_fragment")
                        .commit();
                break;
                default:
//                    Log.e("TEST", "onClick: test" );
                    break;

        }
    }
}
