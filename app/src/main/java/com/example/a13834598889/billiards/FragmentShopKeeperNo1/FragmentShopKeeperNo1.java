package com.example.a13834598889.billiards.FragmentShopKeeperNo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.JavaBean.BilliardStore;
import com.example.a13834598889.billiards.JavaBean.ShopKeeper;
import com.example.a13834598889.billiards.JavaBean.Table;
import com.example.a13834598889.billiards.R;

import java.util.List;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.BmobRealTimeData.TAG;

public class FragmentShopKeeperNo1 extends Fragment {

    private LinearLayout noTable;
    private LinearLayout haveTable;
    private int bmobTableNum;
    private TextView commitTextView;
    private EditText tableNum;
    private EditText vipPay;
    private EditText noemalPay;

    public static FragmentShopKeeperNo1 newInstance() {
        FragmentShopKeeperNo1 fragmentShopKeeperNo1 = new FragmentShopKeeperNo1();
        return fragmentShopKeeperNo1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_keeper_no1, container, false);
        initViews(view);
        noTable.setVisibility(View.VISIBLE);
        haveTable.setVisibility(View.GONE);
        loadingView();
        initClicks();
        return view;
    }

    private void initClicks() {
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
                            table.setTable_number(String.valueOf(i));
                            table.setState("1");
                            table.setStart(false);
                            table.setReserve(false);
                            table.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Log.e(TAG, "done: " + s );
                                    }else {
                                        Log.e(TAG, "done: "+e.getMessage() );
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
