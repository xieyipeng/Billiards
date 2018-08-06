package com.example.a13834598889.billiards.FragmentCustomerShare;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.JavaBean.Cards;
import com.example.a13834598889.billiards.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class Fragment_share extends Fragment {
    private CircleImageView circleImageView3;
    private String download_path;
    private ShareAdapter adapter;
    private RecyclerView recyclerView_shares;

    private List<Cards> cards=new ArrayList<>();

    public static Fragment_share newInstance(){
        Fragment_share fragment_share = new Fragment_share();
        return fragment_share;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share,container,false);
        circleImageView3 = (CircleImageView)view.findViewById(R.id.circleImageView_mine3);
        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView3);

        recyclerView_shares=(RecyclerView)view.findViewById(R.id.recycler_View_shares);
        test();
        adapter=new ShareAdapter(cards);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView_shares.setLayoutManager(layoutManager);
        recyclerView_shares.setAdapter(adapter);


        return view;
    }


    public class ShareHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView_idName;
        private TextView textView_ID;
        private TextView textView_signture;
        private TextView textView_time;
        private TextView textView_title;
        private TextView textView_context;
        private CircleImageView circleImageView;
        private ImageView imageView;

        private View imageView_dianzan;
        private View imageView_shoucang;
        private View imageView_jiahaoyou;

        public ShareHolder(View view){
            super(view);
            textView_idName=(TextView)view.findViewById(R.id.text_share_item_idName);
            textView_ID=(TextView)view.findViewById(R.id.text_share_item_ID);
            textView_signture=(TextView)view.findViewById(R.id.text_share_item_signture);
            textView_time=(TextView)view.findViewById(R.id.text_share_item_time);
            textView_title=(TextView)view.findViewById(R.id.text_share_item_title);
            textView_context=(TextView)view.findViewById(R.id.text_share_item_context);
            circleImageView=(CircleImageView)view.findViewById(R.id.image_share_item_circleView);
            imageView=(ImageView)view.findViewById(R.id.image_share_item_image);
            imageView_dianzan=(ImageView)view.findViewById(R.id.image_button_dianzan);
            imageView_shoucang=(ImageView)view.findViewById(R.id.image_button_shoucang);
            imageView_jiahaoyou=(ImageView)view.findViewById(R.id.image_button_jiahaoyou);
            imageView_dianzan.setOnClickListener(this);
            imageView_shoucang.setOnClickListener(this);
            imageView_jiahaoyou.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.image_button_dianzan:
                    Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();
                    actionDaianzanCartoon(imageView_dianzan);
                    break;
                case R.id.image_button_shoucang:
                    Toast.makeText(getActivity(),"2",Toast.LENGTH_SHORT).show();
                    actionDaianzanCartoon(imageView_shoucang);
                    break;
                case R.id.image_button_jiahaoyou:
                    Toast.makeText(getActivity(),"3",Toast.LENGTH_SHORT).show();
                    actionDaianzanCartoon(imageView_jiahaoyou);
                    break;


            }
        }

        public void bindView(Cards card){
//            textView_idName.setText(card.getCustomer().getId_Name());
//            textView_ID.setText(card.getCustomer().getObjectId());
//            textView_signture.setText(card.getCustomer().getSignature());
//            textView_time.setText(card.getTime());
//            textView_title.setText(card.getTitle());
//            textView_context.setText(card.getContext());




            // -----------------------------分别下载头像和配图并更新UI-------------------------------//
//            downloadFile(card.getCustomer().getFile_head_sculpture());
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Glide.with(getActivity().getApplication()).load(download_path).into(circleImageView);
//                }
//            });
//            downloadFile(card.getImage());
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Glide.with(getActivity().getApplication()).load(download_path).into(circleImageView);
//                }
//            });
            // -----------------------------分别下载头像和配图并更新UI-------------------------------//

        }

        private void downloadFile(BmobFile file){
            File saveFile=new File(Environment.getExternalStorageDirectory(),file.getFilename());
            file.download(saveFile, new DownloadFileListener() {
                @Override
                public void done(final String s, BmobException e) {
                    if(e==null){
                        download_path=s;
                    }

                }

                @Override
                public void onProgress(Integer integer, long l) {

                }
            });
        }

        private void actionDaianzanCartoon(View imageView){
            ObjectAnimator button_dianzan0=ObjectAnimator.ofFloat(imageView,"scaleX",1f,1.7f,1f);
            ObjectAnimator button_dianzan1=ObjectAnimator.ofFloat(imageView,"scaleY",1f,1.7f,1f);
            ObjectAnimator button_dianzan2=ObjectAnimator.ofFloat(imageView,"rotation",0f,360f);
            AnimatorSet animatorSet=new AnimatorSet();
            animatorSet.setDuration(1000)
                    .play(button_dianzan0)
                    .with(button_dianzan1)
                    .with(button_dianzan2);
            animatorSet.start();
        }
    }

    public class ShareAdapter extends RecyclerView.Adapter<ShareHolder>{
        private List<Cards> cards;

        public ShareAdapter (List<Cards> cards){
            this.cards = cards;
        }


        @Override
        public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.share_item, parent, false);
            return new ShareHolder(view);
        }

        @Override
        public void onBindViewHolder(ShareHolder holder, int position) {
            Cards card = cards.get(position);
            holder.bindView(card);
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

    }


    private void test(){
        for(int i=0;i<10;i++){
            cards.add(new Cards());
        }
    }

}
