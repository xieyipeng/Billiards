package com.example.a13834598889.billiards.FragmentCustomerShare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.JavaBean.Card;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.Adapter.ShareAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.volley.VolleyLog.TAG;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class Fragment_share extends Fragment {

    private RecyclerView recyclerView_shares;

    private List<Card> cards = new ArrayList<>();

    public static Fragment_share newInstance() {
        Fragment_share fragment_share = new Fragment_share();
        return fragment_share;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        initViews(view);
        initData();
        return view;
    }

    private void initData() {
        BmobQuery<Card> cardBmobQuery=new BmobQuery<>();
        cardBmobQuery.setLimit(20);
        cardBmobQuery.findObjects(new FindListener<Card>() {
            @Override
            public void done(List<Card> list, BmobException e) {
                if (e==null){
                    cards.addAll(list);
                    setAdapter();
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }

    private void setAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layoutManager.setReverseLayout(true);//列表翻转
        recyclerView_shares.setLayoutManager(layoutManager);
        ShareAdapter shareAdapter = new ShareAdapter(cards, getContext());
        recyclerView_shares.setAdapter(shareAdapter);
    }

    private void initViews(View view) {
        recyclerView_shares = view.findViewById(R.id.recycler_View_shares);
        CircleImageView circleImageView3 = view.findViewById(R.id.circleImageView_mine3);
        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView3);
    }
}
