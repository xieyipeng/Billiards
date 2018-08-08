package com.example.a13834598889.billiards.FragmentCustomerOrder;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.a13834598889.billiards.JavaBean.Table;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.GlideImageLoader;
import com.example.a13834598889.billiards.Tool.ResourceIdToUri;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13834598889 on 2018/4/30.
 */

public class Fragment_billiards extends Fragment implements View.OnClickListener {
    private FragmentManager fragmentManager;

    private Button button_goods_send;
    private Banner banner;
    private List<Uri> uris = new ArrayList<>();
    private TableAdapter adapter;
    private RecyclerView recyclerView_table;
    private ScrollView scrollView_billards;

    private List<Table> tables = new ArrayList<>();   ///------------------->test

    public static Fragment_billiards newInstance() {
        Fragment_billiards fragment_billiards = new Fragment_billiards();
        return fragment_billiards;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.billiards_room, container, false);
        button_goods_send = (Button) view.findViewById(R.id.button_goods_send);
        scrollView_billards = (ScrollView) view.findViewById(R.id.billiards_ScrollView);
        button_goods_send.setOnClickListener(this);
        try {
            addURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        banner = (Banner) view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(uris);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles();
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        recyclerView_table = (RecyclerView) view.findViewById(R.id.recycler_View_table);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_table.setLayoutManager(layoutManager);
        test();
        adapter = new TableAdapter(tables);
        recyclerView_table.setAdapter(adapter);
        scrollView_billards.smoothScrollTo(0, 20);
        scrollView_billards.setFocusable(true);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_goods_send:
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container2, Fragment_store.newInstance())
                        .commit();
                break;
        }
    }


    private void addURL() throws MalformedURLException {
        Uri uri = ResourceIdToUri.resourceIdToUri(getActivity(), R.drawable.test3);
        uris.add(uri);
        uri = ResourceIdToUri.resourceIdToUri(getActivity(), R.drawable.test8);
        uris.add(uri);
        uri = ResourceIdToUri.resourceIdToUri(getActivity(), R.drawable.test_touxiang);
        uris.add(uri);
        uri = ResourceIdToUri.resourceIdToUri(getActivity(), R.drawable.test7);
        uris.add(uri);

    }
    //如果你需要考虑更好的体验，可以这么操作


    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    public class TableHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView_table_number;
        public TextView textView_state;
        public RelativeLayout relativeLayout;

        public TableHolder(View view) {
            super(view);
            textView_table_number = (TextView) view.findViewById(R.id.textView_table_number);
            textView_state = (TextView) view.findViewById(R.id.textTextView_table_state);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout_fight_state);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.relativelayout_fight_state:
                    break;
            }
        }

        public void bindView(Table table) {
            textView_table_number.setText(table.getTable_number().toString());
            if (table.getStart()) {
                textView_state.setText("已 开 始");
                relativeLayout.setVisibility(View.VISIBLE);
            } else if (table.getReserve()) {
                textView_state.setText("已 预 约");
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }


    }

    public class TableAdapter extends RecyclerView.Adapter<TableHolder> {
        private List<Table> tables;

        public TableAdapter(List<Table> tables) {
            this.tables = tables;
        }

        @Override
        public TableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.table_item, parent, false);
            return new TableHolder(view);
        }

        @Override
        public void onBindViewHolder(TableHolder holder, int position) {
            Table table = tables.get(position);
            holder.bindView(table);
        }

        @Override
        public int getItemCount() {
            return tables.size();
        }
    }

    // 以下为测试方法
    public void test() {
        for (int i = 0; i < 8; i++) {
            Table table = new Table();
            table.setTable_number(i);
            if (i / 2 == 0) {
                table.setStart(true);
            }
            tables.add(table);
        }
    }
}
