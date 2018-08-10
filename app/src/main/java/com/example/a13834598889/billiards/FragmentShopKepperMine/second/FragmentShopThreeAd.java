package com.example.a13834598889.billiards.FragmentShopKepperMine.second;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentShopThreeAd extends Fragment {

    private CircleImageView backImageVie;
    private CardView oneCardView;
    private CardView twoCardView;
    private CardView threeCardView;

    public static Dialog dialog1;
    public static Dialog dialog2;
    public static Dialog dialog3;
    private View inflate;

    private Fragment fragmentTest;
    private FragmentManager fragmentManager;

    public static FragmentShopThreeAd newInstance() {
        FragmentShopThreeAd fragmentShopThreeAd = new FragmentShopThreeAd();
        return fragmentShopThreeAd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_three_ad, container, false);
        initViews(view);
        initClicks();
        return view;
    }

    private void initClicks() {
        backImageVie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTest = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .remove(fragmentTest)
                        .show(fragmentManager.findFragmentByTag("shop_fragment_mine"))
                        .commit();
            }
        });
        oneCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow1();
            }
        });
    }

    private void dialogShow1() {
        dialog1 = new Dialog(getContext(), R.style.ActionSheetDialogStyle);
        Window dialogWindow = dialog1.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 2;
        dialogWindow.setAttributes(lp);
        dialog1.show();
    }

    private void initViews(View view) {
        backImageVie = view.findViewById(R.id.shop_three_ad_back);
        oneCardView = view.findViewById(R.id.shop_three_ad_one);
        twoCardView = view.findViewById(R.id.shop_three_ad_two);
        threeCardView = view.findViewById(R.id.shop_three_ad_three);
    }

}
