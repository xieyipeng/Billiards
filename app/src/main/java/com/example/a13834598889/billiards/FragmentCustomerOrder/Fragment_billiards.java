package com.example.a13834598889.billiards.FragmentCustomerOrder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.example.a13834598889.billiards.JavaBean.BilliardStore;
import com.example.a13834598889.billiards.JavaBean.Table;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/7/24.
 */

public class Fragment_billiards extends Fragment {

    private String shopID;//店家id
    private Integer shopInteger;//店家客流量
    private String shopNickName;//店家昵称
    private ScrollView mScrollView;
    private Banner mBanner;
    private Button bt_good;
    private Button bt_oder;
    private TextView custom_count;
    private TextView time;
    private TextView state;
    private TextView choose;
    private List<String> list_image = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private List<Table> mTables = new ArrayList<>();
    private LinearLayout mLinearLayout;

    public static Fragment_billiards newInstance(String id, Integer count, String name) {
        Fragment_billiards fragment_billiards = new Fragment_billiards();
        fragment_billiards.shopID = id;
        fragment_billiards.shopInteger = count;
        fragment_billiards.shopNickName = name;
        return fragment_billiards;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        View view = inflater.inflate(R.layout.fragment_billiards, container, false);
        initViews(view);
        advertising();
        setScrollView();

        final BmobQuery<Table> query = new BmobQuery<>();
        query.addWhereEqualTo("storeId", shopID);
        query.findObjects(new FindListener<Table>() {
            @Override
            public void done(List<Table> list, BmobException e) {
                if (e == null) {
                    mTables.addAll(list);

                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                            StaggeredGridLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(manager);
                    TableAdapter adapter = new TableAdapter(mTables);
                    mRecyclerView.setAdapter(adapter);
                }
            }
        });
        initClicks();
        return view;
    }

    private void setScrollView() {
        mScrollView.smoothScrollTo(0, 20);
        mScrollView.setFocusable(true);
    }

    private void initClicks() {
        bt_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .addToBackStack(null)  //返回
                        .add(R.id.fragment_container, Fragmentstore.newInstance(shopID))
                        .commit();
            }
        });
    }

    private void initViews(View view) {
        bt_good = view.findViewById(R.id.bt_good);
        bt_good = view.findViewById(R.id.bt_good);
        mScrollView = view.findViewById(R.id.billiards_scrollView);
        mBanner = view.findViewById(R.id.banner);
        mRecyclerView = view.findViewById(R.id.recycler_view_table);
        custom_count = view.findViewById(R.id.count);
        choose = view.findViewById(R.id.choose);
        bt_oder = view.findViewById(R.id.bt_order);
        time = view.findViewById(R.id.time);
        state = view.findViewById(R.id.state);
        mLinearLayout = view.findViewById(R.id.table_info);
    }


    private void advertising() {
        BmobQuery<BilliardStore> query = new BmobQuery<>();
        query.addWhereEqualTo("storeId", shopID);
        query.setLimit(200);
        query.findObjects(new FindListener<BilliardStore>() {
            @Override
            public void done(List<BilliardStore> list, BmobException e) {
                if (e == null) {
                    for (BilliardStore billiardsStore : list) {
                        BmobQuery<BilliardStore> query1 = new BmobQuery<>();
                        query1.getObject(billiardsStore.getObjectId(), new QueryListener<BilliardStore>() {
                            @Override
                            public void done(BilliardStore billiardsStore, BmobException e) {
                                if (e == null) {
                                    list_image.add(billiardsStore.getPicture_1().getFileUrl());
                                    list_image.add(billiardsStore.getPicture_2().getFileUrl());
                                    list_image.add(billiardsStore.getPicture_3().getFileUrl());

                                }

                                mBanner.setImages(list_image);
                                mBanner.setImageLoader(new GlideImageLoader());
                                mBanner.isAutoPlay(true);
                                mBanner.setDelayTime(2000);
                                mBanner.start();
                                custom_count.setText("客流量：" + shopInteger);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }


    public class TableAdapter extends RecyclerView.Adapter<TableHolder> {

        private List<Table> mTables;

        public TableAdapter(List<Table> tables) {
            this.mTables = tables;
        }

        @Override
        public TableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.table_item, parent, false);
            final TableHolder holder = new TableHolder(view1);
            holder.tableView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    final Table table = mTables.get(position);
                    mLinearLayout.setVisibility(View.VISIBLE);
                    choose.setText("您已选择" + table.getTable_number() + "号球桌");

                    BmobQuery<Table> query = new BmobQuery<>();
                    query.addWhereEqualTo("table_num", table.getTable_number());
                    query.setLimit(200);
                    query.findObjects(new FindListener<Table>() {
                        @Override
                        public void done(List<Table> list, BmobException e) {
                            if (e == null) {
                                for (Table table1 : list) {
                                    Table table2 = new Table();
                                    Date date = new Date();
                                    table2.setAppoint_time(date.toString());
                                    table2.update(table1.getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {

                                            } else {
                                                Log.e(TAG, "done: " + e.getMessage());
                                            }
                                        }
                                    });
                                    time.setText("预约时间：" + date);


                                    switch (table1.getState()) {
                                        case "1":
                                            state.setText("球桌状态： 空闲");
                                            break;

                                        case "2":
                                            state.setText("球桌状态： 已预约");
                                            break;
                                        case "3":
                                            state.setText("球桌状态： 已开始");
                                            break;
                                        default:
                                            break;
                                    }


                                    if (Integer.valueOf(table1.getState()) == 1) {
                                        bt_oder.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction()
                                                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                                        .addToBackStack(null)  //返回
                                                        .add(R.id.fragment_container, FragmentPay.newInstance(shopID, shopNickName, table.getTable_number()))
                                                        .commit();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });


                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(TableHolder holder, int position) {
            Table table = mTables.get(position);
            holder.bindView(table);

        }

        @Override
        public int getItemCount() {
            return mTables.size();
        }
    }

    public class TableHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView table_number;
        public TextView table_state;
        public RelativeLayout mRelativeLayout;
        public View tableView;

        public TableHolder(View itemView) {
            super(itemView);
            table_number = (TextView) itemView.findViewById(R.id.table_number);
            table_state = (TextView) itemView.findViewById(R.id.table_state);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.fight_state);
            tableView = itemView;
        }

        @Override
        public void onClick(View v) {

        }

        public void bindView(Table table) {
            table_number.setText(table.getTable_number().toString());
            switch (Integer.valueOf(table.getState())) {
                case 1:
                    table_state.setText("空闲");
                    break;
                case 2:
                    table_state.setText("已预约");
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    break;

                case 3:
                    table_state.setText("已开始");
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
