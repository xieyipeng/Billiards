package com.example.a13834598889.billiards.Tool.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CpuUsageInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.JavaBean.Card;
import com.example.a13834598889.billiards.JavaBean.Customer;
import com.example.a13834598889.billiards.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.volley.VolleyLog.TAG;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareHolder> {

    private List<Card> cards;
    private Context mContext;
    private Animation animation;

    public ShareAdapter(List<Card> cards, Context context) {
        this.cards = cards;
        this.mContext = context;
    }


    @Override
    public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item, parent, false);
        return new ShareHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShareHolder holder, final int position) {
        final Card card = cards.get(position);
        holder.bindView(card);
        holder.dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card.getDianzan().contains(Customer.getCurrentUser().getObjectId())) {
                    card.getDianzan().remove(Customer.getCurrentUser().getObjectId());
                    card.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                notifyItemChanged(position);
                            }else {
                                Log.e(TAG, "done: "+e.getMessage() );
                            }
                        }
                    });
                } else {
                    card.getDianzan().add(Customer.getCurrentUser().getObjectId());
                    card.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                notifyItemChanged(position);
                            }else {
                                Log.e(TAG, "done: "+e.getMessage() );
                            }
                        }
                    });
                }
            }
        });
        holder.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card.getShoucang().contains(Customer.getCurrentUser().getObjectId())){
                    card.getShoucang().remove(Customer.getCurrentUser().getObjectId());
                    card.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(mContext, "已取消收藏", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.e(TAG, "done: "+e.getMessage() );
                            }
                        }
                    });
                }else {
                    card.getShoucang().add(Customer.getCurrentUser().getObjectId());
                    card.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(mContext, "已收藏", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.e(TAG, "done: "+e.getMessage() );
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    class ShareHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView textView_idName;
        private TextView textView_time;
        private TextView textView_context;
        private ImageView imageView;
        private TextView textView_dianzanshu;
        private ImageView dianzan;
        private ImageView shoucang;
        private ImageView jiahaoyou;

        private ShareHolder(View view) {
            super(view);
            circleImageView = view.findViewById(R.id.image_share_item_circleView);
            textView_idName = view.findViewById(R.id.text_share_item_idName);
            textView_time = view.findViewById(R.id.text_share_item_time);
            textView_context = view.findViewById(R.id.text_share_item_context);
            imageView = view.findViewById(R.id.image_share_item_image);
            textView_dianzanshu = view.findViewById(R.id.text_dianzanshu);

            dianzan = view.findViewById(R.id.image_button_dianzan);
            shoucang = view.findViewById(R.id.image_button_shoucang);
            jiahaoyou = view.findViewById(R.id.image_button_jiahaoyou);
        }

        private void bindView(Card card) {
            textView_time.setText(card.getCreatedAt());
            textView_context.setText(card.getText());
            textView_dianzanshu.setText(String.valueOf(card.getDianzan().size()));
            Customer customer = card.getCustomer();
            BmobQuery<Customer> customerBmobQuery = new BmobQuery<>();
            customerBmobQuery.getObject(customer.getObjectId(), new QueryListener<Customer>() {
                @Override
                public void done(Customer customer, BmobException e) {
                    if (e == null) {
                        textView_idName.setText(customer.getNickName());
                        getHeadPhoto(customer);
                    } else {
                        Log.e(TAG, "done: " + e.getMessage());
                    }
                }

                private void getHeadPhoto(Customer customer) {
                    if (customer.getPicture_head() != null) {
                        customer.getPicture_head().download(new DownloadFileListener() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    circleImageView.setImageBitmap(BitmapFactory.decodeFile(s));
                                } else {
                                    Log.e(TAG, "done: " + e.getMessage());
                                }
                            }

                            @Override
                            public void onProgress(Integer integer, long l) {
                                Log.d("bmob", "onProgress: 下载进度：" + integer + "," + l);
                            }
                        });
                    } else {
                        circleImageView.setImageResource(R.drawable.test4);
                    }
                }
            });

            if (card.getPicture() != null) {
                card.getPicture().download(new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Drawable drawable = Drawable.createFromPath(s);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                imageView.setBackground(drawable);
                            }
                        } else {
                            Log.e(TAG, "done: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {
                        Log.d("bmob", "onProgress: 下载进度：" + integer + "," + l);
                    }
                });
            }
        }
    }
}
