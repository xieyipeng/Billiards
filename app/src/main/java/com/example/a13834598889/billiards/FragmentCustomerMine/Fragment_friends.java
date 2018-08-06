package com.example.a13834598889.billiards.FragmentCustomerMine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.JavaBean.Customer;
import com.example.a13834598889.billiards.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by 13834598889 on 2018/5/3.
 */

public class Fragment_friends extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView_friends;
    private ContactsAdapter adapter;
    private List<Customer> customers = new ArrayList<>();
    private CircleImageView circleImageView;
    private ImageView imageView_back;



    public static Fragment_friends newInstance(){
        return new Fragment_friends();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends,container,false);
        circleImageView = (CircleImageView)view.findViewById(R.id.circleImageView_mine88);
        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView);

        recyclerView_friends = (RecyclerView) view.findViewById(R.id.contacts_list_recycler_view);
        imageView_back=(ImageView)view.findViewById(R.id.fragment_friends_back);
        imageView_back.setOnClickListener(this);

        test();
        adapter = new ContactsAdapter(customers);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView_friends.setLayoutManager(layoutManager);
        recyclerView_friends.setAdapter(adapter);
        return view;
    }

    public class ContactsHolder extends RecyclerView.ViewHolder {
        private TextView text_view_ni_cheng;
        private TextView text_view_ge_xing_qian_ming;
        private TextView text_view_dian_zan_shu;
        private ImageView imageView_TouXiang;
        private Customer customer;
        public ContactsHolder (View view){
            super(view);
            text_view_dian_zan_shu=(TextView)view.findViewById(R.id.dian_zan_shu_text_view);
            text_view_ge_xing_qian_ming=(TextView)view.findViewById(R.id.ge_xing_qian_ming_text_view);
            text_view_ni_cheng=(TextView)view.findViewById(R.id.ni_cheng_text_view);
            imageView_TouXiang=(ImageView)view.findViewById(R.id.tou_xiang_image_view);
        }

        public void bindView(Customer customer){

        }

    }

    public class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder> {
        private List<Customer> customers;

        public ContactsAdapter(List<Customer> customers) {
            this.customers = customers;
        }

        @Override
        public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.friend_item, parent, false);
            return new ContactsHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactsHolder holder, int position) {
            Customer Customer = customers.get(position);
            holder.bindView(Customer);
        }

        @Override
        public int getItemCount() {
            return customers.size();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_mine_back:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(fragmentManager.findFragmentByTag("friends_fragment") !=null){
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("friends_fragment"))
                            .remove(fragmentManager.findFragmentByTag("friends_fragment"))
                            .show(fragmentManager.findFragmentByTag("fragment_mine"))
                            .commit();
                }
                break;
                default:
                    break;
        }
    }


    //以下为测试代码
    private void test(){
        for(int i=0;i<20;i++){
            customers.add(new Customer());
        }
    }



}
