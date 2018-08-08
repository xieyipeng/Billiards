package com.example.a13834598889.billiards;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.a13834598889.billiards.FragmentCustomerMine.Fragment_mine;
import com.example.a13834598889.billiards.FragmentCustomerOrder.Fragment_order;
import com.example.a13834598889.billiards.FragmentCustomerShare.Fragment_share;
import com.example.a13834598889.billiards.FragmentCustomerTeach.Fragment_teach;
import com.example.a13834598889.billiards.FragmentShopKeeperNo1.FragmentShopKeeperNo1;
import com.example.a13834598889.billiards.FragmentShopKeeperNo2.FragmentShopKeeperNo2;
import com.example.a13834598889.billiards.FragmentShopKeeperNo3.FragmentShopKeeperNo3;
import com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopKeeperMine;
import com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopMessageSetting;
import com.example.a13834598889.billiards.JavaBean.ShopKeeper;
import com.example.a13834598889.billiards.JavaBean.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopKeeperMine.setShopShowIcon;
import static com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopMessageSetting.dialog;
import static com.example.a13834598889.billiards.FragmentShopKepperMine.FragmentShopMessageSetting.setShopChangeIcon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Fragment save_fragment_mine;
    private Fragment save_fragment_order;
    private Fragment save_fragment_share;
    private Fragment save_fragment_teach;

    private Fragment shop_fragment_mine;
    private Fragment shop_fragment_no1;
    private Fragment shop_fragment_no2;
    private Fragment shop_fragment_no3;

    private TextView takePhoto;
    private TextView getPhoto;
    private TextView cancerPhoto;

    private static final int RESULT_CAMERA = 200;
    private static final int RESULT_IMAGE = 100;
    private Uri imageUri;
    private static final int CROP_PICTURE = 2;//裁剪后图片返回码
    //裁剪图片存放地址的Uri
    private Uri cropImageUri;

    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private boolean isStore = false;
    private final String TAG = "MainActivity";
    BottomNavigationView customerNavigation;
    BottomNavigationView shopNavigation;

    private Fragment fragmentTest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initViews();
        bmobCheckStore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_take_photo:
                //先验证手机是否有sdcard
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    //创建File对象，用于存储拍照后的照片
                    File outputImage = new File(getExternalCacheDir(), "out_image.jpg");//SD卡的应用关联缓存目录
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            imageUri = FileProvider.getUriForFile(MainActivity.this,
                                    "com.hanrui.android.fileprovider", outputImage);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                        } else {
                            imageUri = Uri.fromFile(outputImage);
                        }
                        //启动相机程序
                        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, RESULT_CAMERA);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "没有找到储存目录", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "没有储存卡", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
                break;
            case R.id.shop_get_photo:

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }

                dialog.dismiss();
                break;
            case R.id.shop_photo_cancer:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CAMERA:
                if (resultCode == RESULT_OK) {
                    //进行裁剪
                    startPhotoZoom(imageUri);
                }
                break;
            case RESULT_IMAGE:

                if (resultCode == RESULT_OK && data != null) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handlerImageOnKitKat(data);
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        handlerImageBeforeKitKat(data);
                    }
                }
                break;

            case CROP_PICTURE: // 取得裁剪后的图片
                if(resultCode==RESULT_OK) {
                    try {
                        Bitmap headShot = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
                        setShopChangeIcon(headShot);
                        setShopShowIcon(headShot);
                        commitToBmob();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void commitToBmob() {
//        BmobFile bmobFile=new BmobFile(uriToFile(cropImageUri,this));
        BmobFile bmobFile= null;
        try {
            bmobFile = new BmobFile(new File(new URI(cropImageUri.toString())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (bmobFile!=null){
            final User user=new User();
            user.setPicture_head(bmobFile);
            user.setStore(true);
            user.getPicture_head().uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        Log.e(TAG, "done: 上传服务器成功" );
                        user.update(User.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(MainActivity.this, "bmob上传成功", Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.e(TAG, "done: "+e.getMessage() );
                                    Toast.makeText(MainActivity.this, "bmob上传失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Log.e(TAG, "done: 上传服务器失败 "+e.getMessage() );
                    }
                }
            });
        }else {
            Log.e(TAG, "commitToBmob: file is null" );
        }
    }

//    public static File uriToFile(Uri uri,Context context) {
//        String path = null;
//        if ("file".equals(uri.getScheme())) {
//            path = uri.getEncodedPath();
//            if (path != null) {
//                path = Uri.decode(path);
//                ContentResolver cr = context.getContentResolver();
//                StringBuffer buff = new StringBuffer();
//                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
//                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
//                int index = 0;
//                int dataIdx = 0;
//                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
//                    index = cur.getInt(index);
//                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                    path = cur.getString(dataIdx);
//                }
//                cur.close();
//                if (index == 0) {
//                } else {
//                    Uri u = Uri.parse("content://media/external/images/media/" + index);
//                    System.out.println("temp uri is :" + u);
//                }
//            }
//            if (path != null) {
//                return new File(path);
//            }
//        } else if ("content".equals(uri.getScheme())) {
//            // 4.2.2以后
//            String[] proj = { MediaStore.Images.Media.DATA };
//            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
//            if (cursor.moveToFirst()) {
//                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                path = cursor.getString(columnIndex);
//            }
//            cursor.close();
//
//            return new File(path);
//        } else {
//            //Log.i(TAG, "Uri Scheme:" + uri.getScheme());
//        }
//        return null;
//    }

    public void startPhotoZoom(Uri uri) {
        File CropPhoto=new File(getExternalCacheDir(),"crop_image.jpg");
        try{
            if(CropPhoto.exists()){
                CropPhoto.delete();
            }
            CropPhoto.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        cropImageUri=Uri.fromFile(CropPhoto);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }



    private void handlerImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(DocumentsContract.isDocumentUri(this,uri)){
                //如果是document类型的Uri,则通过document id处理
                String docId=DocumentsContract.getDocumentId(uri);
                if("com.android.providers.media.documents".equals(uri.getAuthority())){
                    String id=docId.split(":")[1];//解析出数字格式的id
                    String selection=MediaStore.Images.Media._ID+"="+id;
                    imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                    Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                    imagePath=getImagePath(contentUri,null);
                }
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                //如果是content类型的URI，则使用普通方式处理
                imagePath=getImagePath(uri,null);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                //如果是file类型的Uri,直接获取图片路径即可
                imagePath=uri.getPath();
            }
        }
        startPhotoZoom(uri);
    }

    private void handlerImageBeforeKitKat(Intent data){
        Uri cropUri=data.getData();
        startPhotoZoom(cropUri);
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你没有开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_IMAGE);//打开相册
    }


    @Override
    public void onBackPressed() {

        Log.e(TAG, "onBackPressed: 点击返回键");

        find_jude();
        //获取当前id上的fragment
        fragmentTest = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragmentTest == fragmentManager.findFragmentByTag("shop_keeper_mine_help")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_keeper_mine_message_setting")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_keeper_mine_members_message")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_keeper_mine_three_ad")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_keeper_mine_store_location")) {
            Log.e(TAG, "onBackPressed: 2 -> 1");
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .show(fragmentManager.findFragmentByTag("shop_fragment_mine"))
                    .commit();
        }
        if (fragmentTest == fragmentManager.findFragmentByTag("shop_message_setting_store_name_layout")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_message_setting_change_email_layout")) {
            Log.e(TAG, "onBackPressed: 3 -> 2");
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, FragmentShopMessageSetting.newInstance(), "shop_keeper_mine_message_setting")
                    .commit();
        }
        if (fragmentTest == fragmentManager.findFragmentByTag("shop_fragment_mine")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_fragment_snacks")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_fragment_bill")
                || fragmentTest == fragmentManager.findFragmentByTag("shop_fragment_table")) {
            Log.e(TAG, "onBackPressed: finish");
            finish();
        }
    }

    private void loadBottomMenu() {
        if (isStore) {
            customerNavigation.setVisibility(View.GONE);
            shopNavigation.setVisibility(View.VISIBLE);
            shopNavigation.setOnNavigationItemSelectedListener(shopKeeperBottomView);
            fragmentManager = getSupportFragmentManager();
            fragment = FragmentShopKeeperNo1.newInstance();
            shop_fragment_no1 = fragment;
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, fragment, "shop_fragment_no1")
                    .commit();
            int[][] states = new int[][]{
                    new int[]{-android.R.attr.state_checked},
                    new int[]{android.R.attr.state_checked}
            };
            int[] colors = new int[]{ContextCompat.getColor(this, R.color.toolbar_and_menu_color),
                    ContextCompat.getColor(this, R.color.testColor)
            };
            ColorStateList colorStateList = new ColorStateList(states, colors);
            shopNavigation.setItemTextColor(colorStateList);
            shopNavigation.setItemIconTintList(colorStateList);
        } else {
            shopNavigation.setVisibility(View.GONE);
            customerNavigation.setVisibility(View.VISIBLE);
            customerNavigation.setOnNavigationItemSelectedListener(customBottomView);
            fragmentManager = getSupportFragmentManager();
            fragment = Fragment_order.newInstance();
            save_fragment_order = fragment;
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.fragment_container, fragment, "fragment_order")
                    .commit();
            int[][] states = new int[][]{
                    new int[]{-android.R.attr.state_checked},
                    new int[]{android.R.attr.state_checked}
            };
            int[] colors = new int[]{ContextCompat.getColor(this, R.color.toolbar_and_menu_color),
                    ContextCompat.getColor(this, R.color.testColor)
            };
            ColorStateList colorStateList = new ColorStateList(states, colors);
            customerNavigation.setItemTextColor(colorStateList);
            customerNavigation.setItemIconTintList(colorStateList);
        }
    }

    /**
     * 检查是否店家
     */
    private void bmobCheckStore() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(User.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    if (object.isStore()) {
                        isStore = true;
                        Log.e(TAG, "done: " + "店家好 " + object.isStore());
                        Toast.makeText(MainActivity.this, "店家好", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "done: " + "球友好 " + object.isStore());
                        Toast.makeText(MainActivity.this, "球友好", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "done: 检查店家或球友失败，错误：" + e.getMessage());
                }
                loadBottomMenu();
            }
        });
    }


    private void find_jude() {
        Log.e(TAG, "find_jude: jude");
        if (fragmentManager.findFragmentByTag("friends_fragment") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("friends_fragment"))
                    .remove(fragmentManager.findFragmentByTag("friends_fragment"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("card_fragment") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("card_fragment"))
                    .remove(fragmentManager.findFragmentByTag("card_fragment"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("account_fragment") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("account_fragment"))
                    .remove(fragmentManager.findFragmentByTag("account_fragment"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("shop_keeper_mine_message_setting") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("shop_keeper_mine_message_setting"))
                    .remove(fragmentManager.findFragmentByTag("shop_keeper_mine_message_setting"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("shop_keeper_mine_members_message") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("shop_keeper_mine_members_message"))
                    .remove(fragmentManager.findFragmentByTag("shop_keeper_mine_members_message"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("shop_keeper_mine_three_ad") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("shop_keeper_mine_three_ad"))
                    .remove(fragmentManager.findFragmentByTag("shop_keeper_mine_three_ad"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("shop_keeper_mine_store_location") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("shop_keeper_mine_store_location"))
                    .remove(fragmentManager.findFragmentByTag("shop_keeper_mine_store_location"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("shop_keeper_mine_help") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("shop_keeper_mine_help"))
                    .remove(fragmentManager.findFragmentByTag("shop_keeper_mine_help"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("shop_message_setting_store_name_layout") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("shop_message_setting_store_name_layout"))
                    .remove(fragmentManager.findFragmentByTag("shop_message_setting_store_name_layout"))
                    .commit();
        }
        if (fragmentManager.findFragmentByTag("shop_message_setting_change_email_layout") != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("shop_message_setting_change_email_layout"))
                    .remove(fragmentManager.findFragmentByTag("shop_message_setting_change_email_layout"))
                    .commit();
        }
//        isMainFragment=true;
    }

    private void initViews() {
        takePhoto = findViewById(R.id.shop_take_photo);
        getPhoto = findViewById(R.id.shop_get_photo);
        cancerPhoto = findViewById(R.id.shop_photo_cancer);

        customerNavigation = findViewById(R.id.navigation);
        shopNavigation = findViewById(R.id.shop_navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener customBottomView
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            find_jude();
            switch (item.getItemId()) {
                case R.id.navigation_teach:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_teach == null) {
                        fragment = Fragment_teach.newInstance();
                        save_fragment_teach = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_teach")
                                .commit();
                    } else {
                        fragment = save_fragment_teach;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.navigation_order:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_order == null) {
                        fragment = Fragment_order.newInstance();
                        save_fragment_order = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_order")
                                .commit();
                    } else {
                        fragment = save_fragment_order;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.navigation_share:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_share == null) {
                        fragment = Fragment_share.newInstance();
                        save_fragment_share = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_share")
                                .commit();
                    } else {
                        fragment = save_fragment_share;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.navigation_mine:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (save_fragment_mine == null) {
                        fragment = Fragment_mine.newInstance();
                        save_fragment_mine = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "fragment_mine")
                                .commit();
                    } else {
                        fragment = save_fragment_mine;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
            }
            return false;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener shopKeeperBottomView
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            find_jude();
            switch (item.getItemId()) {
                //我的
                case R.id.shop_keeper_navigation_mine:
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_mine == null) {
                        fragment = FragmentShopKeeperMine.newInstance();
                        shop_fragment_mine = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_mine")
                                .commit();
                    } else {
                        fragment = shop_fragment_mine;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_order:
                    //球桌
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no1 == null) {
                        fragment = FragmentShopKeeperNo1.newInstance();
                        shop_fragment_no1 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_table")
                                .commit();
                    } else {
                        fragment = shop_fragment_no1;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_share:
                    //账单
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no3 == null) {
                        fragment = FragmentShopKeeperNo3.newInstance();
                        shop_fragment_no3 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_bill")
                                .commit();
                    } else {
                        fragment = shop_fragment_no3;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
                case R.id.shop_keeper_navigation_teach:
                    //零食
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).hide(fragment).commit();
                    if (shop_fragment_no2 == null) {
                        fragment = FragmentShopKeeperNo2.newInstance();
                        shop_fragment_no2 = fragment;
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.fragment_container, fragment, "shop_fragment_snacks")
                                .commit();
                    } else {
                        fragment = shop_fragment_no2;
                    }
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out).show(fragment).commit();
                    return true;
            }
            return false;
        }
    };

}
