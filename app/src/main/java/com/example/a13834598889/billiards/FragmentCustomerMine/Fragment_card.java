package com.example.a13834598889.billiards.FragmentCustomerMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.Adapter.TabFragmentPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 13834598889 on 2018/5/3.
 */

public class Fragment_card extends Fragment {

    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private ImageView imageView_back;
    private SlidingTabLayout slidingTabLayout;

    public static Fragment_card newInstance() {
        Fragment_card fragment_card = new Fragment_card();
        return fragment_card;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initViews(view);
        initClicks();
        return view;
    }

    private void initClicks() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (fragmentManager.findFragmentByTag("card_fragment") != null) {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("card_fragment"))
                            .remove(fragmentManager.findFragmentByTag("card_fragment"))
                            .show(fragmentManager.findFragmentByTag("fragment_mine"))
                            .commit();
                }
            }
        });
    }

    private void initViews(View view) {
        CircleImageView circleImageView = view.findViewById(R.id.circleImageView_mine99);
        slidingTabLayout=view.findViewById(R.id.sliding_tabs);
        viewPager = view.findViewById(R.id.viewPager_container3);
        imageView_back = view.findViewById(R.id.fragment_card_back);
        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView);
        initViewPager();
    }

    public void initViewPager() {
        Fragment fragmentFirst = Fragment_card_mine.newInstance();
        Fragment fragmentSecond = Fragment_card_star.newInstance();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(fragmentFirst);
        fragmentList.add(fragmentSecond);
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        slidingTabLayout.setViewPager(viewPager);

        fragmentManager=getActivity().getSupportFragmentManager();
//        fragmentManager = getChildFragmentManager();
    }
}