package com.example.a13834598889.billiards.FragmentShopKepperMine;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.Tool.GetBmobFile;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.volley.VolleyLog.TAG;

public class FragmentShopMessageSetting extends Fragment implements View.OnClickListener {

    private CircleImageView backImageView;
    private LinearLayout nickNameSetting;
    private LinearLayout headPictureSetting;
    private LinearLayout passWordSetting;
    private LinearLayout phoneNumberSetting;
    private LinearLayout emailSetting;
    private LinearLayout signSetting;

    private static CircleImageView shopProfilePhoto;

    public static Dialog dialog;
    private View inflate;

    private TextView takePhoto;
    private TextView getPhoto;
    private TextView cancerPhoto;

    private TextView nickNameTextView;
    private TextView phoneNumberTextView;
    private TextView emailTextView;
    private TextView signTextView;

    private FragmentManager fragmentManager;

    private Boolean isFirstLoading;

    //签名字数：17*2

    public static FragmentShopMessageSetting newInstance() {
        FragmentShopMessageSetting fragmentShopMessageSetting = new FragmentShopMessageSetting();
        return fragmentShopMessageSetting;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_message_setting, container, false);
        initViews(view);

        bmobCheck();//初始化个人界面
        initPhoto();

        initClicks(view);

        return view;
    }

    private void initPhoto() {
        GetBmobFile.initInterface("编辑资料界面",2);
    }


    public static void setShopChangeIcon(Bitmap bitmap){
        shopProfilePhoto.setImageBitmap(bitmap);
    }

    private void initClicks(View view) {

        takePhoto = view.findViewById(R.id.shop_take_photo);
        getPhoto = view.findViewById(R.id.shop_get_photo);
        cancerPhoto = view.findViewById(R.id.shop_photo_cancer);
        headPictureSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: click");
                dialogShow();
            }
        });

    }

    private void dialogShow() {
        dialog = new Dialog(getContext(), R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.mine_dialog_view, null);
        dialog.setContentView(inflate);

        Window dialogWindow = dialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 2;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void bmobCheck() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    nickNameTextView.setText(object.getNickName());
                    phoneNumberTextView.setText(object.getMobilePhoneNumber());
                    emailTextView.setText(object.getEmail());
                    if (object.getSign() != null) {
                        signTextView.setText(object.getSign());
                    } else {
                        signTextView.setText(R.string.noSign);
                    }
                } else {
                    Toast.makeText(getActivity(), "店铺面板更新失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .remove(this)
                .commit();
        switch (v.getId()) {
            case R.id.shop_message_setting_back_ImageView:
                if (fragmentManager.findFragmentByTag("shop_keeper_mine_message_setting") != null) {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentByTag("shop_keeper_mine_message_setting"))
                            .remove(fragmentManager.findFragmentByTag("shop_keeper_mine_message_setting"))
                            .show(fragmentManager.findFragmentByTag("shop_fragment_mine"))
                            .commit();
                }
                break;
            case R.id.shop_message_setting_store_name_layout:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopChangeNickName.newInstance(), "shop_message_setting_store_name_layout")
                        .commit();
                break;
            case R.id.shop_message_setting_change_email_layout:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container, FragmentShopChangeEmail.newInstance(), "shop_message_setting_change_email_layout")
                        .commit();
                break;
            case R.id.shop_message_setting_change_password_layout:
                break;
            case R.id.shop_message_setting_change_phone_number_layout:
                break;
            case R.id.shop_message_setting_change_sign_layout:
                break;
            default:
                break;
        }
    }

    private void initViews(View view) {
        backImageView = view.findViewById(R.id.shop_message_setting_back_ImageView);
        headPictureSetting = view.findViewById(R.id.shop_message_setting_profile_photo_layout);
        nickNameSetting = view.findViewById(R.id.shop_message_setting_store_name_layout);
        passWordSetting = view.findViewById(R.id.shop_message_setting_change_password_layout);
        phoneNumberSetting = view.findViewById(R.id.shop_message_setting_change_phone_number_layout);
        emailSetting = view.findViewById(R.id.shop_message_setting_change_email_layout);
        signSetting = view.findViewById(R.id.shop_message_setting_change_sign_layout);

        shopProfilePhoto = view.findViewById(R.id.shop_profile_photo);

        backImageView.setOnClickListener(this);
        nickNameSetting.setOnClickListener(this);
        passWordSetting.setOnClickListener(this);
        phoneNumberSetting.setOnClickListener(this);
        emailSetting.setOnClickListener(this);
        signSetting.setOnClickListener(this);

        nickNameTextView = view.findViewById(R.id.shop_message_setting_store_name_TextView);
        phoneNumberTextView = view.findViewById(R.id.shop_message_setting_change_phone_number_TextView);
        emailTextView = view.findViewById(R.id.shop_message_setting_change_email_TextView);
        signTextView = view.findViewById(R.id.shop_message_setting_change_sign_TextView);
    }
}
