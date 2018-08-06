package com.example.a13834598889.billiards.FragmentCustomerOrder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13834598889.billiards.JavaBean.Goods;
import com.example.a13834598889.billiards.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13834598889 on 2018/4/30.
 */

public class Fragment_store extends Fragment {

    private RecyclerView recyclerView_drink;
    private DrinkAdapter adapter;

    private List<Goods> goods=new ArrayList<>();

    public static Fragment_store newInstance(){
        return new Fragment_store();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_drink,container,false);
        recyclerView_drink=(RecyclerView)view.findViewById(R.id.recycler_View_goods);

//        test();

        adapter=new DrinkAdapter(goods);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_drink.setLayoutManager(layoutManager);
        recyclerView_drink.setAdapter(adapter);
        return view;
    }

    public class DrinkHolder extends RecyclerView.ViewHolder{
        private ImageView good_image;
        private TextView good_name;
        private TextView good_price;

        public DrinkHolder(View view){
            super(view);
            good_image=(ImageView)view.findViewById(R.id.good_image);
            good_name=(TextView)view.findViewById(R.id.good_name);
            good_price=(TextView)view.findViewById(R.id.good_prive);
        }

        public void bindView(Goods good){
            //图片
            good_name.setText(good.getShop_name());
            good_price.setText(""+good.getShop_price());
        }
    }

    public class DrinkAdapter extends RecyclerView.Adapter<DrinkHolder>{
        private List<Goods> goods;


        public DrinkAdapter(List<Goods> goods){
            this.goods=goods;
        }

        @Override
        public DrinkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.drink_item, parent, false);
            return new DrinkHolder(view);
        }

        @Override
        public void onBindViewHolder(DrinkHolder holder, int position) {
            Goods good = goods.get(position);
            holder.bindView(good);
        }

        @Override
        public int getItemCount() {
            return goods.size();
        }

    }


    //以下为测试方法

//    public void test(){
//        goods.add(new Goods("农夫山泉",2.0,1));
//        goods.add(new Goods("脉动",3.0,1));
//        goods.add(new Goods("娃哈哈",4.0,1));
//        goods.add(new Goods("红牛",5.0,1));
//        goods.add(new Goods("冰红茶",4.0,1));
//        goods.add(new Goods("健力宝",4.0,1));
//        goods.add(new Goods("雪碧",8.0,1));
//    }
}
