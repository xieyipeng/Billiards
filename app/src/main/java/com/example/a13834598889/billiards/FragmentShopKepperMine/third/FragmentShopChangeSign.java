package com.example.a13834598889.billiards.FragmentShopKepperMine.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.FragmentShopKepperMine.second.FragmentShopMessageSetting;
import com.example.a13834598889.billiards.JavaBean.ShopKeeper;
import com.example.a13834598889.billiards.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class FragmentShopChangeSign extends Fragment {

    private Fragment fragmentTest;
    private FragmentManager fragmentManager;

    private CircleImageView backImageView;
    private TextView commitTextView;
    private EditText inputEditText;

    public static FragmentShopChangeSign newInstance(){
        FragmentShopChangeSign fragmentShopChangeSign=new FragmentShopChangeSign();
        return fragmentShopChangeSign;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_change_sign, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews(view);
        initSign();
        initClicks();
        return view;
    }

    private void initSign() {
        BmobQuery<ShopKeeper> shopKeeperBmobQuery=new BmobQuery<>();
        shopKeeperBmobQuery.getObject(ShopKeeper.getCurrentUser().getObjectId(), new QueryListener<ShopKeeper>() {
            @Override
            public void done(ShopKeeper shopKeeper, BmobException e) {
                if (e==null){
                    inputEditText.setText(shopKeeper.getSign());
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }

    private void initClicks() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .remove(fragmentTest)
                        .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                        .commit();
            }
        });
        commitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newSigin=inputEditText.getText().toString();
                if (newSigin.length()>50||newSigin.length()<=0){
                    Toast.makeText(getContext(), "个性签名最多25个祖字符", Toast.LENGTH_SHORT).show();
                }else {
                    ShopKeeper shopKeeper=new ShopKeeper();
                    shopKeeper.setStore(true);
                    shopKeeper.setObjectId(ShopKeeper.getCurrentUser().getObjectId());
                    shopKeeper.setSign(newSigin);
                    shopKeeper.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                                fragmentTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                                fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                        .remove(fragmentTest)
                                        .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                                        .commit();
                            }else {
                                Log.e(TAG, "done: "+e.getMessage() );
                            }
                        }
                    });
                }
            }
        });
    }

    private void initViews(View view) {
        backImageView=view.findViewById(R.id.shop_change_sign_back);
        commitTextView=view.findViewById(R.id.shop_change_sign_commit);
        inputEditText=view.findViewById(R.id.shop_change_sign_input);
    }
}
