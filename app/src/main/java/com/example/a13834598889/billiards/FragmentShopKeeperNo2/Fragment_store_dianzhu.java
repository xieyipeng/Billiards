package com.example.a13834598889.billiards.FragmentShopKeeperNo2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.JavaBean.Goods;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Yangyulin on 2018/8/6.
 */
public class Fragment_store_dianzhu extends Fragment implements View.OnClickListener {

    private String image_path;
    private View view;
    private CircleImageView circleImageView;
    //private ImageView imageView_back;
    private RecyclerView recyclerView_drink;
    private FloatingActionButton fab;

    private List<Goods> drinkList = new ArrayList<>();
    private GoodsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private User administor;
    private RelativeLayout layout_no_goods;

    public static Fragment_store_dianzhu newInstance(String path) {
        Fragment_store_dianzhu fragment_store_dianzhu = new Fragment_store_dianzhu();
        fragment_store_dianzhu.image_path = path;
        return fragment_store_dianzhu;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store_dianzhu, container, false);
        administor = BmobUser.getCurrentUser(User.class);
        preView();
        return view;
    }

    public void preView() {
        circleImageView = view.findViewById(R.id.circleImageView_dianzhu);
        //imageView_back= view.findViewById(R.id.fragment_store_dianzhu_back);
        recyclerView_drink = view.findViewById(R.id.recycler_View_goods_dianzhu);
        fab = view.findViewById(R.id.fab_jiahao);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        layout_no_goods = view.findViewById(R.id.layout_no_goods);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "刷新了", Toast.LENGTH_SHORT);
                refresh();
            }
        });

        //imageView_back.setOnClickListener(this);
        fab.setOnClickListener(this);

        if (image_path.equals("")) {
            Glide.with(getActivity().getApplication()).load(R.drawable.test_touxiang3).into(circleImageView);
        } else {
            Glide.with(getActivity().getApplication()).load(image_path).into(circleImageView);
        }

        addData();
        adapter = new GoodsAdapter(drinkList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView_drink.setLayoutManager(layoutManager);
        recyclerView_drink.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.fragment_store_dianzhu_back:
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
//                        .add(R.id.fragment_container, FragmentShopKeeperNo1.newInstance())
//                        .commit();
//                break;
            case R.id.fab_jiahao:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container2, Fragment_add_goods.newInstance())
                        .commit();
                break;
        }
    }

    public class DrinkViewHolder extends RecyclerView.ViewHolder {

        private CardView drinkView;
        private ImageView drinkImage;
        private TextView drinkName;
        private TextView drinkPrice;

        public DrinkViewHolder(View view) {
            super(view);
            drinkView = (CardView) view;
            drinkImage = view.findViewById(R.id.good_image);
            drinkName = view.findViewById(R.id.good_name);
            drinkPrice = view.findViewById(R.id.good_price);
        }

        private void bindView(Goods good) {
            drinkName.setText("名 称：" + good.getGood_name());
            drinkPrice.setText("售 价：" + good.getGood_price().toString());

            if (good.getPicture() != null) {
                drinkImage.setVisibility(View.VISIBLE);
                downloadFile_picture(good.getPicture(), drinkImage);
            } else {
                Glide.with(getActivity().getApplication()).load(image_path).into(drinkImage);
            }
        }

        private void downloadFile_picture(BmobFile file, final ImageView view) {
            file.download(new DownloadFileListener() {
                @Override
                public void done(final String s, BmobException e) {
                    if (e == null) {
                        if (s != null) {
                            try {
                                Glide.with(getActivity().getApplication()).load(s).into(view);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        } else {
                        }
                    }

                }

                @Override
                public void onProgress(Integer integer, long l) {

                }
            });
        }

    }

    public class GoodsAdapter extends RecyclerView.Adapter<DrinkViewHolder> {

        public List<Goods> drinkList;


        public GoodsAdapter(List<Goods> drinkList) {
            this.drinkList = drinkList;
        }

        @Override
        public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.drink_item_dianzhu, parent, false);
            final DrinkViewHolder holder = new DrinkViewHolder(view);
            holder.drinkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Goods good = drinkList.get(position);
                    showNormalDialog(good);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(DrinkViewHolder holder, int position) {
            Goods drink = drinkList.get(position);
            holder.bindView(drink);
        }

        @Override
        public int getItemCount() {
            return drinkList.size();
        }

    }

    public void refresh() {
        addData();
        swipeRefreshLayout.setRefreshing(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                addData();
                                swipeRefreshLayout.setRefreshing(false);
                            } catch (Exception c) {
                                c.printStackTrace();
                            }

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void addData() {
        BmobQuery<Goods> query = new BmobQuery<>();
        // query.addWhereEqualTo("user",administor);
        query.setLimit(20);
        query.order("-createdAt");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(final List<Goods> list, BmobException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (list.size() == 0) {
                                layout_no_goods.setVisibility(View.VISIBLE);
                            } else {
                                layout_no_goods.setVisibility(View.INVISIBLE);
                                adapter.drinkList = list;
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception c) {
                            c.printStackTrace();
                        }

                    }
                });
            }
        });
        Log.i("查询", "更新我的");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        layout_no_goods.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void showNormalDialog(final Goods drink) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());
        //normalDialog.setIcon(R.drawable.delete);
        normalDialog.setMessage("确认删除该饮料吗？");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialogInterface, int i) {
                drink.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("bmob", "成功");
                            Toast.makeText(getActivity(), "删除商品成功", Toast.LENGTH_SHORT).show();
                            refresh();
                            //Toast.makeText(getContext(),"lal",Toast.LENGTH_SHORT).show();

                        } else {
                            Log.i("bmob", "失败:" + e.getMessage() + "," + e.getErrorCode());
                            //Toast.makeText(getContext(),"lalNO", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        normalDialog.show();
    }
}



