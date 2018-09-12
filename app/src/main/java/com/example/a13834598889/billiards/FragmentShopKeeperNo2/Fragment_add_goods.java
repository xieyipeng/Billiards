package com.example.a13834598889.billiards.FragmentShopKeeperNo2;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.a13834598889.billiards.JavaBean.Goods;
import com.example.a13834598889.billiards.R;

import com.example.a13834598889.billiards.JavaBean.User;
import com.example.a13834598889.billiards.Tool.PictureUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.BmobRealTimeData.TAG;


/**
 * Created by Yangyulin on 2018/8/4.
 */
public class Fragment_add_goods extends Fragment implements View.OnClickListener {

    private Button tianjia_button;
    private EditText mingzi;
    private EditText jiage;
    private EditText kucunliang;
    private User administrator;

    private ImageView fanhui;
    private ImageView choose_image;
    private View view;
    private CircleImageView tupian;
    private String imagePath = null;
    private String chulihou_imagePath;
    private final int TAKE_PHOTO = 1;
    private final int CHOOSE_PHOTO = 2;
    private Uri imageUri;

    private String image = "";

    public static Fragment_add_goods newInstance() {
        return new Fragment_add_goods();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_goods, container, false);
        //administrator = BmobUser.getCurrentUser(User.class);
        preView();
        return view;
    }

    private void preView() {
        mingzi = (EditText) view.findViewById(R.id.mingzi_editText);
        jiage = (EditText) view.findViewById(R.id.jiage_editText);
        kucunliang = (EditText) view.findViewById(R.id.kucunliang_editText);
        fanhui = (ImageView) view.findViewById(R.id.add_goods_fanhui);
        tupian = (CircleImageView) view.findViewById(R.id.tupian_imageView);
        choose_image = (ImageView) view.findViewById(R.id.choose_image_from_camera);
        tianjia_button = (Button) view.findViewById(R.id.tianjia_button);

        fanhui.setOnClickListener(this);
        choose_image.setOnClickListener(this);
        tianjia_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_goods_fanhui:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.fragment_container2, Fragment_store_dianzhu.newInstance(image))
                        .commit();
                break;
            case R.id.choose_image_from_camera:
                PopupMenu popup = new PopupMenu(getContext(), choose_image);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.camera) {
                            openCamera();
                        } else {
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            } else {
                                openAlbum();
                            }
                        }
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.tianjia_button:
                if (mingzi.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "名字不能为空", Toast.LENGTH_SHORT).show();
                } else if (jiage.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "价格不能为空", Toast.LENGTH_SHORT).show();
                } else if (kucunliang.getText().toString().equals("0")) {
                    Toast.makeText(getActivity(), "库存量至少为1", Toast.LENGTH_SHORT).show();
                } else if (kucunliang.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "库存量不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("温馨提醒");
                    alertDialog.setMessage("是否确认添加？");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            //Toast.makeText(getActivity(),"imagePath="+imagePath,Toast.LENGTH_SHORT).show();
                            if (imagePath != null) {
                                final Goods drink = new Goods();
                                if (drink.getPicture() != null) {
                                    BmobFile yuan_FILE = drink.getPicture();
                                    if (yuan_FILE != null) {
                                        yuan_FILE.setUrl(yuan_FILE.getUrl());
                                        yuan_FILE.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e!=null){

                                                }else {
                                                    Log.e(TAG, "done: "+e.getMessage() );
                                                }
                                            }
                                        });
                                    }
                                }

                                long t = System.currentTimeMillis();//获得当前时间的毫秒数
                                Random rd = new Random(t);//作为种子数传入到Random的构造器中
                                String targetPath = getActivity().getExternalCacheDir() + "compressPic" + rd.nextInt() + ".jpg";
                                chulihou_imagePath = PictureUtil.compressImage(imagePath, targetPath, 30);
                                Log.i("bmob", "chulihou_imagePath=" + chulihou_imagePath);
                                File file = new File(chulihou_imagePath);
                                final BmobFile bmobFile = new BmobFile(file);
                                bmobFile.uploadblock(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        Log.i("bmob", "evdvfdv=" + e);
                                        if (e == null) {
                                            Log.i("bmob", "上传");
                                            drink.setGood_name(mingzi.getText().toString());
                                            drink.setGood_price(Double.valueOf(jiage.getText().toString()));
                                            drink.setShop_num(Integer.valueOf(kucunliang.getText().toString()));
                                            drink.setPicture(bmobFile);
                                            drink.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if (e == null) {
                                                        Log.i("bmob", "添加商品成功");
                                                        Toast.makeText(getActivity(), "添加商品成功", Toast.LENGTH_SHORT).show();
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    Thread.sleep(2000);
                                                                    //getActivity().finish();
                                                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                                    fragmentManager.beginTransaction()
                                                                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                                                            .add(R.id.fragment_container2, Fragment_store_dianzhu.newInstance(image))
                                                                            .commit();
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }).start();

                                                    } else {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getContext(), "文件无", Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                            } else {
                                Goods goods = new Goods();
                                goods.setGood_name(mingzi.getText().toString());
                                goods.setGood_price(Double.valueOf(jiage.getText().toString()));
                                goods.setShop_num(Integer.valueOf(kucunliang.getText().toString()));
                                goods.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Log.i("bmob", "添加商品成功");
                                            Toast.makeText(getActivity(), "添加商品成功", Toast.LENGTH_SHORT).show();
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(2000);
                                                        fanhui.setEnabled(true);
                                                        //getActivity().finish();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();

                                        } else {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    alertDialog.show();
                }
                break;
        }

    }

    public void openCamera() {
        File outputImage = new File(getContext().getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getContext(), "com.example.a13834598889.billiards.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri));
                        tupian.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "Sorry,you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    public void handleImageOnKitKat(Intent data) {
        imagePath = null;
        Uri uri;
        uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        Log.i("testtest", "imagePath======" + imagePath);
        displayImage(imagePath);
    }

    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Log.d("test", "getImagePath: " + path);
            }
            cursor.close();
        }
        return path;
    }

    public void displayImage(String imagePath) {
        Log.i("testtest", "imagePath==55====" + imagePath);
        if (imagePath != null) {
            //Toast.makeText(getActivity(),imagePath,Toast.LENGTH_SHORT).show();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            tupian.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getActivity(), "Filed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}


