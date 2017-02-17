package com.exz.antcargo.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.exz.antcargo.R;

import java.util.ArrayList;

import io.rong.message.LocationMessage;

/**
 * */
public class MapActivity extends Activity implements LocationSource, AMap.OnMyLocationChangeListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener, AMap.OnMarkerClickListener, AMap.OnCameraChangeListener, AMapLocationListener {
    private LinearLayout back;
    private AMap aMap;
    private MapView mapView;
    private LocationSource.OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private Marker marker;// 定位雷达小图标
    private LatLng locationLatLng;
    private Marker detailMarker;// 显示Marker的详情
    private LatLng latLng;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String address = "";

    private TextView tv_title;
    private Marker geoMarker;

    private LinearLayout ll_back;
    private LocationMessage mMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mapview);
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
		 * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
        // Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        // MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        back = (LinearLayout) findViewById(R.id.ll_back);


        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getStringExtra("name"));
        init();

        if (getIntent().hasExtra("Latitude") && getIntent().hasExtra("Longitude")) {

            showMarker(Double.parseDouble(getIntent().getStringExtra("Latitude")), Double.parseDouble(getIntent().getStringExtra("Longitude")),getIntent().getStringExtra("address"));
        } else if (getIntent().hasExtra("location")) {
            mMsg = getIntent().getParcelableExtra("location");
            showMarker(mMsg.getLat(), mMsg.getLng(),mMsg.getPoi());

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showMarker(double latitude, double longitude,String address) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.location_marker)));
        markerOption.setFlat(true);

        marker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .position(latLng).title(address)
                .icon(BitmapDescriptorFactory
                        .fromResource((int) BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location));
        myLocationStyle.myLocationIcon(null);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationRotateAngle(180);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnCameraChangeListener(this);// 设置地图滑动事件监听
        // aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        // aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        // addMarkersToMap();// 往地图上添加marker

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    /**
     * 定位成功后回调函数
     */
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null && aLocation != null) {
//            mListener.onLocationChanged(aLocation);// 显示系统小蓝点
//            marker.setDraggable(true);
//            marker.setVisible(false);
            String address = aLocation.getAddress();

            aMap.moveCamera(CameraUpdateFactory.changeLatLng(locationLatLng));

        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
            /*
             * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 2 * 1000, 10, this);
            // mAMapLocationManager.requestLocationUpdates(
            // LocationProviderProxy.AMapNetwork, 2000, 10, this);
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destory();
        }
        mAMapLocationManager = null;
    }


    @Override
    public void onMapLoaded() {
        LatLng latLng = null;
        if (getIntent().hasExtra("Latitude") && getIntent().hasExtra("Longitude")) {

            latLng = new LatLng(Double.parseDouble(getIntent().getStringExtra("Latitude")), Double.parseDouble(getIntent().getStringExtra("Longitude")));
        } else if (getIntent().hasExtra("location")) {
            latLng = new LatLng(mMsg.getLat(), mMsg.getLng());
        }
// 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(latLng).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));

        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public void onMarkerDrag(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMarkerDragEnd(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMarkerDragStart(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onMarkerClick(Marker m) {
        return false;
    }

    @Override
    public void onCameraChange(CameraPosition arg0) {
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        // 获取屏幕中心点坐标
        latLng = cameraPosition.target;
        latitude = latLng.latitude;
        longitude = latLng.longitude;

//
        // getdata();
    }

    @Override
    public void onMyLocationChange(Location location) {

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
