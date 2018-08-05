package com.example.a13834598889.billiards.xieyipeng;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.a13834598889.billiards.Fragment_Order.Fragment_order;
import com.example.a13834598889.billiards.JavaBean.Customer;
import com.example.a13834598889.billiards.JavaBean.ShopKeeper;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.R;
import com.example.a13834598889.billiards.xieyipeng.bean.DianZan;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.JsonAdapter;
//import com.example.a13834598889.lovepets.JavaBean.Decode_json;
//import com.example.a13834598889.lovepets.JavaBean.DianZan;
//import com.example.a13834598889.lovepets.JavaBean.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.support.constraint.Constraints.TAG;

public class RegisterActivity extends AppCompatActivity {
    private String choose_zhuce;
    private EditText register_account;
    private EditText register_phone;
    private EditText register_emile;
    private EditText register_passWord;
    private EditText register_location;
    private Button register_button;


    private String Latitude;
    private String Longitude;
    private String location;
    private TextView hintTextView;
    private LinearLayout hintLinearLayout;
    //    private LinearLayout theBigestLayout;
    private LocationClient locationClient;

    public static Intent newInstance(Context context, String choose) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("choose_zhuce", choose);
        //A：店家
        //B：球友
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        super.onCreate(savedInstanceState);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener2());
        locationClient.start();
        /**
         * 点赞
         */
        final DianZan dianZan = new DianZan();
//        dianZan.setmDianZanShu(new Integer(0));
        dianZan.setmDianZanShu(0);
        dianZan.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

        setContentView(R.layout.activity_register);
        choose_zhuce = getIntent().getStringExtra("choose_zhuce");

        initViews();

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("提示信息 ：")
                        .setMessage("注册后将使用注册账号登陆，且注册账号将不可修改。\n 是否确认注册 ？")
                        .setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (register_account.getText().toString().equals("") || register_emile.
                                getText().toString().equals("") || register_passWord.getText().toString
                                ().equals("") || register_phone.getText().toString().equals("")) {
                            Toast.makeText(RegisterActivity.this, "所有注册信息均不能为空", Toast.LENGTH_SHORT).show();
                            dialog.create().dismiss();
                        } else if (register_account.getText().toString().length() < 6 || register_passWord.getText().length() < 6) {
                            Toast.makeText(RegisterActivity.this, "账号或密码长度不能小于六位", Toast.LENGTH_SHORT).show();
                            dialog.create().dismiss();
                        } else {
                            /**
                             * 实现网上注册:
                             */
                            if (choose_zhuce.equals("A")) {
                                ShopKeeper shopKeeper = new ShopKeeper();
                                shopKeeper.setStore(true);
                                shopKeeper.setLatitude(Latitude);
                                shopKeeper.setLongitude(Longitude);
                                if (shopKeeper.getLatitude() != null) {
                                    Log.e(TAG, "onClick: getLatitude success!!");
                                }
                                Log.e(TAG, "onClick: location是否提前知道："+location );
                                shopKeeper.setLocation(location);
                                zhuce(shopKeeper);

                            } else {
                                Customer customer = new Customer();
                                customer.setStore(false);
                                zhuce(customer);
                            }
                        }
                    }
                });
                dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    private void zhuce(User user) {
        user.setUsername(register_account.getText().toString());
        user.setEmail(register_emile.getText().toString());
        user.setPassword(register_passWord.getText().toString());
        user.setMobilePhoneNumber(register_phone.getText().toString());
        user.setNickName("Century");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                if (e == null) {
                    /**
                     * 即时通讯
                     */
                    Toast.makeText(RegisterActivity.this, "注册成功" + user.getObjectId(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setTitle("温馨提醒")
                            .setMessage("您已成功注册账号，请使用账号和密码进行登陆。")
                            .setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    dialog.show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败" + e.getMessage() + "Error code:" + e.getErrorCode(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initViews() {
        hintLinearLayout = findViewById(R.id.register_hint_LinearLayout);
        hintTextView = findViewById(R.id.register_hint_TextView);
//        theBigestLayout=findViewById(R.id.linearLayout);
        register_account = (EditText) findViewById(R.id.register_name_editText);
        register_phone = (EditText) findViewById(R.id.register_phone_editText);
        register_emile = (EditText) findViewById(R.id.register_emile_editText);
        register_passWord = (EditText) findViewById(R.id.register_mima_editText);
        register_location = findViewById(R.id.register_location_editText);
        register_button = (Button) findViewById(R.id.register_account_button);
        if (choose_zhuce.equals("B")) {
            hintTextView.setVisibility(View.GONE);
            hintLinearLayout.setVisibility(View.GONE);
        } else {
            //设置背景大小
//            ViewGroup.LayoutParams layoutParams;
//            layoutParams=theBigestLayout.getLayoutParams();
//            layoutParams.height=500;
//            theBigestLayout.setLayoutParams(layoutParams);
        }
    }

    public class MyLocationListener2 implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Longitude = String.valueOf(bdLocation.getLongitude());
            Latitude = String.valueOf(bdLocation.getLatitude());
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    Log.e(TAG, "onReceiveLocation: " + Longitude + " " + Latitude);
                    String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="
                            + Latitude + "," + Longitude + "&type=100";
//                    Log.e(TAG, "onReceiveLocation: " + urlString);
                    BufferedReader reader = null;
                    StringBuilder response = new StringBuilder();
                    HttpURLConnection conn = null;
                    try {
                        URL url = new URL(urlString);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(8000);
                        conn.setReadTimeout(8000);
                        InputStream inputStream = conn.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        inputStream.close();
//                        Log.e(TAG, "getAdd: " + response);
                    } catch (Exception e) {
                        Log.e(TAG, "onReceiveLocation: " + "error in wapaction,and e is " + e.getMessage());
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                Log.e(TAG, "run: IOException", e);
                            }
                        }
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }

                    JsonParser parser = new JsonParser();
                    JsonObject object = (JsonObject) parser.parse(String.valueOf(response));
                    JsonArray add = object.get("addrList").getAsJsonArray();
                    JsonObject addList = add.get(0).getAsJsonObject();
                    location = addList.get("admName").getAsString() + "," + addList.get("name").getAsString();
//                    Log.e(TAG, "run: location " + location + " " + location.length());
                    for (int i = 0; i < location.length(); i++) {
                        if (location.charAt(i) == ',') {
                            location = location.substring(i + 1, location.length());
                            break;
                        }
                    }
//                    Log.e(TAG, "run: change: " + location);
                    register_location.post(new Runnable() {
                        @Override
                        public void run() {
                            register_location.setText(location);
                        }
                    });
                }
            }).start();
        }
    }
}
