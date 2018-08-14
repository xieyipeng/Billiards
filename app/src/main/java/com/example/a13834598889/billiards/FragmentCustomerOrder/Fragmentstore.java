package com.example.a13834598889.billiards.FragmentCustomerOrder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.JavaBean.Goods;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.OrderInfoUtil2_0;
import com.example.a13834598889.billiards.Tool.PayResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2018/7/26.
 */

public class Fragmentstore extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private List<Goods> mGoods = new ArrayList<>();
    private String id;
    private StringBuilder info = new StringBuilder();
    private double money = 0.0;
    private Button pay;
    private String RSA_PRIVATE ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCM+4vsh5mskSWiwDwBrMc8dGsTmtQInVimEkPnNsYQQ1ENvRjTttkSU3cXDrmO+coZCDWYRQhS72G/xHhgdcHf5rH+oTT6j3Xenww5oD41azPmRGnCNXLvReb1gHfYD64josY08nwBakSt/cHlSMCfkNDez0O0in1sEucYILNYkFCbpHY5rhgvN0jEAqYg2z43piEvARhiSjRaKHb8ZwrNFye91HsHxV5dHUmqwkfUaPl5yfAaYm7dlr/JlBsYEsOOwV11PvKYEQuTI6bHt//2w/D9JBnAXXxfeXndhBnlHmo+lrRsIK7ZPTz0rBnOb3KUpqMxn+LLo3Us+gTD/QLFAgMBAAECggEAKD8jScnIKAhjmxuPxdaiJfMCIl2fzDnG9dnfAqGTV08wU2C5Nq9LNr0XEUEF3fgXJqA+VJLYdnyaBhm7V6YmS5nbFFrG+gR8XKpA3i6Ns8g/z6uWGXgSsJXfAhTDoa2QQ+IS/Uh/+BNzOcxoTuE/BA5eYkz/AgpLFdAroqqrKEvR8FXbhFjES4yklTtC2TweDF2xCwEoeMFA8lbBjLECc40lesRoncC49/mxVbAZOwEhcB7hkvnLZ4one03O/gE0wdwyEbJHm23YyK2FkksDPtcCMBx1+HC4uA7zn3go3ZIjrfpeqSGG3d19UXDkDSIVTSqlGdrhonbOAmEJMAaOgQKBgQD6DpCFb3MxukpLchH6p4PJuXWr5evO5ZIAvDSvliKGkUGR37dlCGUmIGAgvCL2ldOatjQcwUcyZTqkwNuPgTtMzl2WSs3aiR0lzf2UQTD4cUDWRtyBj25J69ZqoR/87IyWqqqd4G7tdWr9a7xIxOn+9Rtr1qjj5auop/+frneCJQKBgQCQVVXJUx0MWuMpw9d20+XaUSzJEnv1ra6WHVjw04VRh5OeNzFw/S6RJDkgPPB5TsCMaGxNE4WhykKWBs0e7t0CQ9vufjEz5OXbCQb0By7f3pYtrOXTl20YMlFOcl/d5RWpRIECA5Ieqc5mkv25xnw1onkHC+47UZ8t/xY7hvWMIQKBgQDq0ViH5aPwU7dG6ATYNAy/F0jYNt5c+RpFVHfJV5xub+N6P/KxjtOlnQuIUgQnOYVvqKCBTEM2oPcUFgNY3Iu6UaRy6SYsjUvw32K8oQeClp/DWOHjTLTN+AjvMwWd9ukC55u3DDY/CV+CQXSbhUcT5Epu1zLcaCXuCG01H5ocfQKBgCuzWsUZQCtUfYFQxbU51Vdzyo6a5SNu0fSrsBlCwhP8a8q0xWiDkAzsHcvQB7ODD7Ozjk8MASMKfXy1VHfwNMSRzU55sOYYgSv/oLZUUnIAEBKGThPxvltcKNgKs1IZIaTdk/4LHLviCBdwnBgaq9MFfYWPrDMTtJGVsaKWa1RhAoGBAMqCNJcNDYs3vj8BDMdpWl3pVq5hl2BieYzvS0ZSKU99E278ejYWs2cn1kPaXO3qfs/V/uufYeeRK5mMd6xepBdI0zpe5vy99yB+pdA2tS7QAN5Wbk1NvFmHDnrMDRRikDI3+trTGjWPyIKk+L1lBJ2sPqnCbHnQuerWiEOA/jts";
    public static final String APPID = "2016091800539811";
    private static final int SDK_PAY_FLAG = 1001;

    public static Fragmentstore newInstance(String id){
        Fragmentstore fragmentstore = new Fragmentstore();
        fragmentstore.id = id;
        return fragmentstore;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_atore, container, false);
        BmobQuery<Goods> query = new BmobQuery<>();
        query.addWhereEqualTo("storeId", id);
        query.setLimit(200);
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null){
                    for (Goods goods : list){
                        mGoods.add(goods);
                    }
                    mRecyclerView = view.findViewById(R.id.recycler_view_goods);
                    DrinkAdapter adapter = new DrinkAdapter(mGoods);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(adapter);
                }
            }
        });
        return view;
    }

    public class DrinkHolder extends RecyclerView.ViewHolder{
        private ImageView good_image;
        private TextView good_name;
        private TextView good_price;
        private View goodview;
        public DrinkHolder(View itemView) {
            super(itemView);
            goodview = itemView;
            good_image = (ImageView)itemView.findViewById(R.id.good_image);
            good_name = (TextView)itemView.findViewById(R.id.good_name);
            good_price = (TextView)itemView.findViewById(R.id.good_price);
        }

        public void bindView(Goods goods){
            good_price.setText(goods.getGood_price().toString());
            good_name.setText(goods.getGood_name());
            downloadFile_picture(goods.getPicture(), good_image);

        }
    }
    private void downloadFile_picture(BmobFile file, final ImageView view) {
        file.download(new DownloadFileListener() {
            @Override
            public void done(final String s, BmobException e) {
                if(e==null){
                    if(s!=null){
                        try{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(getActivity().getApplication()).load(s).into(view);
                                }
                            });
                        }catch (Exception d){
                            d.printStackTrace();
                        }
                    }else {
                    }
                }
            }
            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    Log.i("Pay", "Pay:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public class DrinkAdapter extends RecyclerView.Adapter<DrinkHolder>{
        private List<Goods> mGoodsList;

        public DrinkAdapter(List<Goods> goods){
            this.mGoodsList = goods;
        }

        @Override
        public DrinkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           final View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.drink_item,
                   parent, false);
           final DrinkHolder holder = new DrinkHolder(view1);

               holder.goodview.setOnClickListener(new View.OnClickListener() {


                   @Override
                   public void onClick(View v) {
                       int position = holder.getAdapterPosition();
                       Goods goods = mGoodsList.get(position);
                       TextView good_info = (TextView) view.findViewById(R.id.goods_info);
                       info.append(goods.getGood_name() + "×1");
                       good_info.setText(info);
                       TextView goodMoney = (TextView)view.findViewById(R.id.money);
                       money = money + goods.getGood_price();
                       goodMoney.setText(String.valueOf(money));

                       pay = (Button)view.findViewById(R.id.pay);
                       pay.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {

                               //秘钥验证的类型 true:RSA2 false:RSA
                               OrderInfoUtil2_0.money = money;
                               boolean rsa = false;
                               //构造支付订单参数列表
                               Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa);
                               //构造支付订单参数信息
                               String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                               //对支付参数信息进行签名
                               String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa);
                               //订单信息
                               final String orderInfo = orderParam + "&" + sign;
                               //异步处理
                               Runnable payRunnable = new Runnable() {

                                   @Override
                                   public void run() {
                                       //新建任务
                                       PayTask alipay = new PayTask(getActivity());
                                       //获取支付结果
                                       Map<String, String> result = alipay.payV2(orderInfo, true);
                                       Message msg = new Message();
                                       msg.what = SDK_PAY_FLAG;
                                       msg.obj = result;
                                       mHandler.sendMessage(msg);
                                   }
                               };
                               // 必须异步调用
                               Thread payThread = new Thread(payRunnable);
                               payThread.start();
                           }
                       });
                   }
               });
           return holder;
        }
        @Override
        public void onBindViewHolder(DrinkHolder holder, int position) {

            Goods goods = mGoodsList.get(position);
            holder.bindView(goods);
        }

        @Override
        public int getItemCount() {
            return mGoodsList.size();
        }
    }
}
