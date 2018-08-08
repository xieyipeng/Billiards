package com.example.a13834598889.billiards.xieyipeng;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.MainActivity;
import com.example.a13834598889.billiards.R;
//import com.example.a13834598889.lovepets.JavaBean.User;
//import com.netease.nimlib.sdk.NIMClient;
//import com.netease.nimlib.sdk.RequestCallback;
//import com.netease.nimlib.sdk.auth.AuthService;
//import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private EditText editText_account;
    private EditText editText_passWord;
    private Button button_login;
    private CheckBox checkBox_remberPassWord;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Button register_login_button;
    private CircleImageView circleImageView_login;
    boolean isRember;
    boolean isRember1;
    private ProgressBar progressBar;

    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("fef642bee9678388a478d8b5b25bafa0")
                .setConnectTimeout(30)
                .setUploadBlockSize(1024 * 1024)
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

        initView();
        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            initPermission();
        }
    }

    //权限判断和申请
    private void initPermission() {

        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            Toast.makeText(this, "所有权限均通过", Toast.LENGTH_SHORT).show();
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (!hasPermissionDismiss) {
                Toast.makeText(this, "权限申请完成", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "存在权限申请失败", Toast.LENGTH_SHORT).show();
            }
        }

    }


//        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
//
//        }else{
//            if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
//
//            }else{
//                if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},4);
//
//                }else{
//                    if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//                    }else{
//                        Toast.makeText(this, "请给定位权限", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case 1:
//                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
//                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
//                        Log.e("Test", "onCreate: case WRITE_EXTERNAL_STORAGE" );
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this,"拒绝了拨打电话权限权限",Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 2:
//                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},4);
//                        Log.e("Test", "onCreate: case ACCESS_COARSE_LOCATION 1" );
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this,"拒绝了存储权限",Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 3:
//                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},4);
//                        Log.e("Test", "onCreate: case ACCESS_COARSE_LOCATION 2" );
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this,"拒绝了定位权限1",Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 4:
//                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
////                    ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},4);
////                    Log.e("Test", "onCreate: case ACCESS_COARSE_LOCATION 3" );
//                    if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},4);
//                        Log.e("Test", "onCreate: case ACCESS_COARSE_LOCATION 3" );
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this,"拒绝了定位权限2",Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }


    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        button_login.setEnabled(false);
        register_login_button.setEnabled(false);
        final String account0 = editText_account.getText().toString();
        final String passWord0 = editText_passWord.getText().toString();
        User user = new User();
        user.setUsername(account0);
        user.setPassword(passWord0);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    editor = pref.edit();
                    if (checkBox_remberPassWord.isChecked()) {
                        editor.putBoolean("rember_passWord", true);
                        editor.putString("account", account0);
                        editor.putString("password", passWord0);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "" + e.getErrorCode() + e.getMessage(), Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.INVISIBLE);
                    button_login.setEnabled(true);
                    register_login_button.setEnabled(true);
                }
            }
        });
    }

    private void initView() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        isRember = pref.getBoolean("rember_passWord", false);
        isRember1 = pref.getBoolean("image_path", false);

        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        editText_account = (EditText) findViewById(R.id.login_account_Edit_text);
        editText_passWord = (EditText) findViewById(R.id.login_passWord_Edit_text);
        button_login = (Button) findViewById(R.id.login_button);
        register_login_button = (Button) findViewById(R.id.register_button);
        checkBox_remberPassWord = (CheckBox) findViewById(R.id.rember_passWord_Check_Box);
        circleImageView_login = (CircleImageView) findViewById(R.id.circleImageView_login);
        if (isRember1) {
            String path = pref.getString("image", "");
            Glide.with(getApplication()).load(path).into(circleImageView_login);
        }


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        if (isRember) {
            String account = pref.getString("account", "");
            String passWord = pref.getString("password", "");
            editText_account.setText(account);
            editText_passWord.setText(passWord);
            checkBox_remberPassWord.setChecked(true);
        }
        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setTitle("请选择用户初测类型：")
                        .setMessage("A：店家\nB：球友")
                        .setCancelable(true);
                dialog.setPositiveButton("B", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = RegisterActivity.newInstance(LoginActivity.this, "B");
                        startActivity(intent1);
                    }
                });
                dialog.setNegativeButton("A", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = RegisterActivity.newInstance(LoginActivity.this, "A");
                        startActivity(intent1);
                    }
                });
                dialog.show();
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
            }
        });
    }


    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
        initView();
    }

    @Override
    protected void onResume() {
        initView();
        super.onResume();
        initView();
        progressBar.setVisibility(View.INVISIBLE);
        button_login.setEnabled(true);
        register_login_button.setEnabled(true);
    }
}
