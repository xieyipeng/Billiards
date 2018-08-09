package com.example.a13834598889.billiards.FragmentCustomerMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.Adapter.CardPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by 13834598889 on 2018/5/3.
 */

public class Fragment_card extends Fragment implements View.OnClickListener{



    private FragAdapter adapter;
    private FragmentManager fragmentManager;

    private CircleImageView circleImageView;
    private ViewPager viewPager ;
    private ImageView imageView_back;
    private SlidingTabLayout slidingTabLayout;



    public static Fragment_card newInstance(){
        return new Fragment_card();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card,container,false);
        slidingTabLayout=(SlidingTabLayout)view.findViewById(R.id.sliding_tabs);
        circleImageView = (CircleImageView)view.findViewById(R.id.circleImageView_mine99);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager_container3);

        imageView_back=(ImageView)view.findViewById(R.id.fragment_card_back);
        imageView_back.setOnClickListener(this);


        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView);

        initViewPager();

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_card_back:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(fragmentManager.findFragmentByTag("card_fragment") !=null){
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("card_fragment"))
                            .remove(fragmentManager.findFragmentByTag("card_fragment"))
                            .show(fragmentManager.findFragmentByTag("fragment_mine"))
                            .commit();
                }
                break;
            default:
                break;
        }
    }


    public class FragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public FragAdapter(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            // TODO Auto-generated constructor stub
            mFragments=fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mFragments.size();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewPager();
    }

    public void initViewPager(){
        CardPagerAdapter adapter1 = new CardPagerAdapter(getActivity().getSupportFragmentManager());
        fragmentManager = getActivity().getSupportFragmentManager();
        viewPager.setAdapter(adapter1);
        slidingTabLayout.setViewPager(viewPager);
    }
}
