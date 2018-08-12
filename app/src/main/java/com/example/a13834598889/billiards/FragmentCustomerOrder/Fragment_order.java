package com.example.a13834598889.billiards.FragmentCustomerOrder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.a13834598889.billiards.OtherActivity.Billards;
import com.example.a13834598889.billiards.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by 13834598889 on 2018/4/29.
 */

public class Fragment_order extends Fragment implements View.OnClickListener {
    private CircleImageView circleImageView1;
    private LocationClient locationClient;
    private MapView mapView = null;
    private BaiduMap baiduMap;
    private Button button_yuyuedaqiu;
    private Button button_xitongtuijian;

    private boolean isFirstLocate = true;

    public static Fragment_order newInstance() {
        Fragment_order fragment_order = new Fragment_order();
        return fragment_order;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        SDKInitializer.initialize(getActivity().getApplicationContext());

        mapView = (MapView) view.findViewById(R.id.mapView);
        button_yuyuedaqiu = (Button) view.findViewById(R.id.button_fragment_order_yuyuedaqiu);
        button_xitongtuijian = (Button) view.findViewById(R.id.button_fragment_order_xitongtuiijian);
        button_yuyuedaqiu.setOnClickListener(this);
        button_xitongtuijian.setOnClickListener(this);
        circleImageView1 = (CircleImageView) view.findViewById(R.id.circleImageView_mine1);
        Glide.with(getActivity())
                .load(R.drawable.test_touxiang)
                .into(circleImageView1);
        map();//定位
        return view;
    }

    /**
     * 定位
     */
    private void map() {
        baiduMap = mapView.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);

//        // 构造定位数据
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(100).latitude(location.getLatitude())
//                .longitude(location.getLongitude()).build();
////         设置定位数据
//        baiduMap.setMyLocationData(locData);
//        设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）\


//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.tubikao_zhanghu);
//        MyLocationConfiguration config = new MyLocationConfiguration(
//                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
//        baiduMap.setMyLocationConfiguration(config);


//        baiduMap.setMyLocationEnabled(true);

        locationClient = new LocationClient(getActivity());
        locationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_fragment_order_yuyuedaqiu:
                Intent intent = Billards.newInstance(getActivity(), 1);
                startActivity(intent);
                break;
            case R.id.button_fragment_order_xitongtuiijian:
                Toast.makeText(getActivity(), "还没实现呢", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    private void navigateTo(BDLocation location){
        /**
         * @param location 地图
         */
        Log.d(TAG, "navigateTo: isFirstLocate "+isFirstLocate);
        if (isFirstLocate){
            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());

            Log.d(TAG, "navigateTo: getLatitude "+latLng.toString());

            //定位到自己的位置
            MapStatus mapStatus=new MapStatus.Builder()
                    .target(latLng)
                    .zoom(16f)
                    .build();
            MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newMapStatus(mapStatus);
            baiduMap.setMapStatus(mapStatusUpdate);
//            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(latLng);
//            baiduMap.animateMapStatus(update);
//            update=MapStatusUpdateFactory.zoomTo(16f);
//            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        //将自己显示在地图上
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }


    public  class MyLocationListener implements BDLocationListener {
        /**
         * 地图
         * @param bdLocation
         */
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            /**
             * @param bdLocation 地图
             */
            Log.d(TAG, "onReceiveLocation: isFirstLocate " + isFirstLocate);
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
//                Log.e(TAG, "onReceiveLocation: "+bdLocation.getLatitude()+","+bdLocation.getLongitude() );
            }
        }
    }
}
/**
 * 开发版本
 * SHA1: BA:82:0B:5D:80:45:01:CA:3F:6F:2D:98:D2:C8:2F:A4:66:01:45:DC
 * SHA256: B2:4C:61:38:E7:AD:93:2E:09:98:A9:64:55:54:37:95:32:8D:2F:B0:11:D3:3A:66:E1:B7:FC:87:5F:73:EF:01
 * <p>
 * 发布版本
 * SHA1: 64:63:FD:78:A0:24:D8:03:23:10:DB:1B:4D:8E:33:78:72:77:21:C5
 * SHA256: 0F:ED:87:B6:AB:E1:C3:31:B0:DF:31:BF:92:03:62:8B:D9:83:B6:BF:00:0A:E8:DE:37:79:C2:42:B5:79:04:F9
 * <p>
 * 发布版本
 * SHA1: 64:63:FD:78:A0:24:D8:03:23:10:DB:1B:4D:8E:33:78:72:77:21:C5
 * SHA256: 0F:ED:87:B6:AB:E1:C3:31:B0:DF:31:BF:92:03:62:8B:D9:83:B6:BF:00:0A:E8:DE:37:79:C2:42:B5:79:04:F9
 */

/**
 * 发布版本
 * SHA1: 64:63:FD:78:A0:24:D8:03:23:10:DB:1B:4D:8E:33:78:72:77:21:C5
 * SHA256: 0F:ED:87:B6:AB:E1:C3:31:B0:DF:31:BF:92:03:62:8B:D9:83:B6:BF:00:0A:E8:DE:37:79:C2:42:B5:79:04:F9
 */
