package com.example.a13834598889.billiards.FragmentShopKeeperNo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.JavaBean.BilliardStore;
import com.example.a13834598889.billiards.JavaBean.ShopKeeper;
import com.example.a13834598889.billiards.JavaBean.Table;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.bmob.v3.BmobRealTimeData.TAG;

public class Fragment_qiuzhuo_dianzhu extends Fragment {

    private LinearLayout noTable;
    private LinearLayout haveTable;
    private int bmobTableNum;
    private TextView commitTextView;
    private EditText tableNum;
    private EditText vipPay;
    private EditText noemalPay;


    private CircleImageView circleImageView;
    private RecyclerView recyclerView_table;
    public LinearLayout linearLayout;
    public TextView qiuzhuohao;
    public TextView startTime;
    public TextView endTime;
    public TextView zhuofei;
    private TableAdapter adapter;
    private View view;
    private User administor;
    private String picture_path = "";
    private List<Table> tableList = new ArrayList<>();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    public static Fragment_qiuzhuo_dianzhu newInstance() {
        Fragment_qiuzhuo_dianzhu fragmentQiuzhuodianzhu = new Fragment_qiuzhuo_dianzhu();
        return fragmentQiuzhuodianzhu;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qiuzhuo_dianzhu, container, false);
        initViews(view);
        noTable.setVisibility(View.VISIBLE);
        haveTable.setVisibility(View.GONE);

        administor = BmobUser.getCurrentUser(User.class);
        loadingView();
        initClicks();
        return view;
    }


    class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

        public List<Table> tableList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            View tableView;
            //            ImageView imageView_table;
            TextView textView_table_number;
            TextView TextView_table_state;

            public ViewHolder(View view) {
                super(view);
                tableView = view;
//                imageView_table = view.findViewById(R.id.table_fightting);
                textView_table_number = view.findViewById(R.id.table_number);
                TextView_table_state = view.findViewById(R.id.fight_state);
            }
        }

        public TableAdapter(List<Table> tableList) {
            this.tableList = tableList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false);
            final RelativeLayout relativeLayout;
            relativeLayout = view.findViewById(R.id.fight_state);
            final TextView TextView_table_state;
            TextView_table_state = view.findViewById(R.id.table_state);
            final ViewHolder holder = new ViewHolder(view);
            holder.tableView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Table table = tableList.get(position);
                    table.setStart(true);
                    table.setState("2");
                    table.setStart_time(df.format(new Date()));
                    table.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.i("bmob", "成功");
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                    TextView_table_state.setText("已 开 始");
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    qiuzhuohao.setText(table.getTable_number() + "号球桌使用信息：");
                    startTime.setText("开桌时间：" + table.getStart_time());
                    endTime.setText("结束时间：");
                }
            });
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Table table = tableList.get(position);
//                    table.setEmpty(true);
                    table.setState("1");
                    table.setEnd_time(df.format(new Date()));
                    table.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.i("bmob", "成功");
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                    TextView_table_state.setText("空    闲");
                    relativeLayout.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    endTime.setText("结束时间：" + df.format(new Date()));
                    //zhuofei.setText("打球桌费：" + );
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Table table = tableList.get(position);
            holder.textView_table_number.setText(String.valueOf(table.getTable_number()));
            holder.TextView_table_state.setText("空    闲");
        }

        @Override
        public int getItemCount() {
            return tableList.size();
        }
    }


    // 以下为测试方法
    public void test() {
        for (int i = 0; i < 16; i++) {
            Table table = new Table();
            table.setTable_number(i);
            table.setState("1");
//            table.setEmpty(true);
            table.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.i("bmob", "添加球桌成功");
                    } else {
                        e.printStackTrace();
                    }
                }
            });
            tableList.add(table);
        }
    }


    private void initClicks() {
        if (noTable.getVisibility()==View.VISIBLE){
            commitTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tableNum.getText().toString().equals("")
                            || vipPay.getText().toString().equals("")
                            || noemalPay.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!isInteger(tableNum.getText().toString())) {
                        Toast.makeText(getContext(), "球桌数必须为整数", Toast.LENGTH_SHORT).show();
                    } else if (!isNumber(vipPay.getText().toString())) {
                        Toast.makeText(getContext(), "会员价必须为数字", Toast.LENGTH_SHORT).show();
                    } else if (!isNumber(noemalPay.getText().toString())) {
                        Toast.makeText(getContext(), "普通价必须为数字", Toast.LENGTH_SHORT).show();
                    } else {
                        updataTableNumber();
                        updataPay();
                    }
                }
            });
        }

    }

    private void updataPay() {
        BmobQuery<BilliardStore> billiardStoreBmobQuery = new BmobQuery<>();
        billiardStoreBmobQuery.findObjects(new FindListener<BilliardStore>() {
            @Override
            public void done(List<BilliardStore> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (ShopKeeper.getCurrentUser().getObjectId().equals(list.get(i).getStoreID())) {
                            list.get(i).setPrice_vip(Double.valueOf(vipPay.getText().toString()));
                            list.get(i).setPrice_pt(Double.valueOf(noemalPay.getText().toString()));
                            list.get(i).update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getContext(), "价格上传成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e(TAG, "done: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

    private void updataTableNumber() {
        ShopKeeper shopKeeper = new ShopKeeper();
        shopKeeper.setObjectId(ShopKeeper.getCurrentUser().getObjectId());
        shopKeeper.setStore(true);
        shopKeeper.setTableNum(Integer.valueOf(tableNum.getText().toString()));
        shopKeeper.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getContext(), "桌球数上传成功", Toast.LENGTH_SHORT).show();
                    loadingView();
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

    private void initViews(View view) {
        noTable = view.findViewById(R.id.no_table);
        haveTable = view.findViewById(R.id.have_table);


        noTable.setVisibility(View.GONE);
        haveTable.setVisibility(View.GONE);


        commitTextView = view.findViewById(R.id.shop_setting_table_commit);
        tableNum = view.findViewById(R.id.shop_setting_table_number);
        vipPay = view.findViewById(R.id.shop_setting_table_vip_pay);
        noemalPay = view.findViewById(R.id.shop_setting_table_normal_pay);


        circleImageView = view.findViewById(R.id.circleImageView_dianzhu);
        recyclerView_table = view.findViewById(R.id.recycler_View_table_dianzhu);
        linearLayout = view.findViewById(R.id.qiuzhuoxinxi);
        qiuzhuohao = view.findViewById(R.id.qiuzhuohao);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        zhuofei = view.findViewById(R.id.zhuofei);

        if (picture_path.equals("")) {
            Glide.with(getActivity().getApplication()).load(R.drawable.test_touxiang3).into(circleImageView);
        } else {
            Glide.with(getActivity().getApplication()).load(picture_path).into(circleImageView);
        }

        test();
        adapter = new TableAdapter(tableList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_table.setLayoutManager(layoutManager);
        recyclerView_table.setAdapter(adapter);

    }

    public static boolean isNumber(String str) {
        //是否数字
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    public static boolean isInteger(String str) {
        //是否整数
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private void loadingView() {
        BmobQuery<ShopKeeper> tableBmobQuery = new BmobQuery<>();
        tableBmobQuery.findObjects(new FindListener<ShopKeeper>() {
            @Override
            public void done(List<ShopKeeper> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getObjectId().equals(ShopKeeper.getCurrentUser().getObjectId())) {
                            bmobTableNum = list.get(i).getTableNum();
                        }
                    }
                    if (bmobTableNum == 0) {
                        noTable.setVisibility(View.VISIBLE);
                        haveTable.setVisibility(View.GONE);
                    } else {
                        noTable.setVisibility(View.GONE);
                        haveTable.setVisibility(View.VISIBLE);

                        for (int i = 0; i < bmobTableNum; i++) {
                            Log.e(TAG, "onClick: " + i);
                            Table table = new Table();
                            table.setStoreID(ShopKeeper.getCurrentUser().getObjectId());
                            table.setTable_number(i);
                            table.setState("1");
                            table.setStart(false);
                            table.setReserve(false);
                            table.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Log.e(TAG, "done: " + s);
                                    } else {
                                        Log.e(TAG, "done: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Log.e(TAG, "done: zhelima " + e);
                }
            }
        });
    }
}
