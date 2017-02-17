package com.exz.antcargo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.AMap.OnMyLocationChangeListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.app.SealAppContext;
import com.exz.antcargo.activity.utils.AMapUtil;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.rong.message.LocationMessage;

/**
 * @author swz AMapV2地图中简单介绍显示定位小蓝点
 *         地图搜索地址选择
 */
public class ChoiceLocationActivity extends Activity implements LocationSource,
        AMapLocationListener, OnMarkerClickListener, OnMarkerDragListener,
        OnMapLoadedListener, OnClickListener, OnCameraChangeListener,
        OnMyLocationChangeListener, OnPoiSearchListener, TextWatcher {
    private ImageView back;
    private Button btn, lo, btn_cancel;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private Marker marker;// 定位雷达小图标
    private LatLng locationLatLng;
    private int tsearchType = 0;// 当前选择搜索类型
    private PoiResult poiResult; // poi返回的结果
    private Marker locationMarker; // 选择的点
    private PoiSearch poiSearch;
    private PoiOverlay poiOverlay;// poi图层
    private List<PoiItem> poiItems;// poi数据
    private Marker detailMarker;// 显示Marker的详情
    private String city;
    private String detail;
    private TextView tv_name;
    private LatLng latLng;
    private AutoCompleteTextView searchText;// 输入搜索关键字
    private Button sousuo_button;
    private ImageView location;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String keyWord = "";
    private TextView tv_location;
    private GeocodeSearch geocoderSearch;
    private String address = "";
    private ProgressDialog progDialog = null;// 搜索时进度条
    private PoiSearch.Query query;// Poi查询条件类
    private LocationMessage mMsg;


    Executor executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choicelocationsource_activity);
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
		 * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
        // Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        // MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        mapView = (MapView) findViewById(R.id.map);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(getIntent().getStringExtra("name"));
        mapView.onCreate(savedInstanceState);// 必须要写
        // mapView.onCreate(savedInstanceState);// 此方法必须重写
        back = (ImageView) findViewById(R.id.back);
        // tv_title_middle = (TextView) findViewById(R.id.tv_title_middle) ;
        btn = (Button) findViewById(R.id.btn);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        searchText = (AutoCompleteTextView) findViewById(R.id.sousuo_text);
        searchText.addTextChangedListener(this);// 添加文本输入框监听事件
        sousuo_button = (Button) findViewById(R.id.sousuo_button);
        sousuo_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                keyWord = AMapUtil.checkEditText(searchText);
                if ("".equals(keyWord)) {
                    ToastUtil.show(ChoiceLocationActivity.this, "请输入搜索关键字");
                    return;
                } else {
                    doSearchQuery();
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(searchText.getWindowToken(), 0); //强制隐藏键盘

                }
            }
        });
        location = (ImageView) findViewById(R.id.location);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ChoiceLocationActivity.this.finish();
            }
        });
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(!getIntent().getStringExtra("name").equals("选择位置")) {

                    // 获取新的地理位置
                    Intent i = new Intent();
                    i.putExtra("address", address);
                    i.putExtra("latitude", latitude + "");
                    i.putExtra("longitude", longitude + "");
                    setResult(120, i);
                    ChoiceLocationActivity.this.finish();
                }else {
                    mMsg = LocationMessage.obtain(latitude, longitude, address, getMapUrl(latitude, longitude));
                    SealAppContext.getInstance().getLastLocationCallback().onSuccess(mMsg);
                    SealAppContext.getInstance().setLastLocationCallback(null);
                    finish();
                }
            }
        });
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("address", "");
                i.putExtra("latitude", "");
                i.putExtra("longitude", "");
                setResult(120, i);
                ChoiceLocationActivity.this.finish();
            }
        });
        init();
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
    private Uri getMapUrl(double x, double y) {
        String url = "http://restapi.amap.com/v3/staticmap?location=" + y + "," + x +
                "&zoom=16&scale=2&size=408*240&markers=mid,,A:" + y + ","
                + x + "&key=" + "ee95e52bf08006f63fd29bcfbcf21df0";
        return Uri.parse(url);
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

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

                String formatAddress = result.getRegeocodeAddress().getFormatAddress();
                address = formatAddress;
                tv_location.setText(formatAddress);
            }
        });


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
     * 此方法已经废弃
     */
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null && aLocation != null) {
            mListener.onLocationChanged(aLocation);// 显示系统小蓝点
//            marker.setDraggable(true);
//            marker.setVisible(false);
            String address = aLocation.getAddress();
            lo.setText(address);

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
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMapLoaded() {
        new Handler() {

        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (longitude != 0.0 && latitude != 0.0) {
                    LatLng marker1 = new LatLng(latitude, longitude);
                    //设置中心点和缩放比例
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
                    return;
                }

            }
        }, 500);


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

        LatLonPoint lp = new LatLonPoint(latitude, longitude);
        RegeocodeQuery query = new RegeocodeQuery(lp, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
//
        // getdata();
    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showProgressDialog();// 显示进度框
        query = new PoiSearch.Query(keyWord, "", ConstantValue.CityName.equals("")?"":ConstantValue.CityName);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(1);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        Inputtips inputTips = new Inputtips(ChoiceLocationActivity.this,
                new InputtipsListener() {

                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        if (rCode == 0) {// 正确返回
                            List<String> listString = new ArrayList<String>();
                            for (int i = 0; i < tipList.size(); i++) {
                                listString.add(tipList.get(i).getName());
                            }
                            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                                    getApplicationContext(),
                                    R.layout.route_inputs, listString);
                            searchText.setAdapter(aAdapter);
                            aAdapter.notifyDataSetChanged();
                        }
                    }
                });
        try {
            inputTips.requestInputtips(newText, "");// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == 0) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果

                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtil.show(ChoiceLocationActivity.this,
                                "对不起，没有搜索到相关数据！");
                    }
                }
            } else {
                ToastUtil.show(ChoiceLocationActivity.this, "对不起，没有搜索到相关数据！");
            }
        } else if (rCode == 27) {
            ToastUtil.show(ChoiceLocationActivity.this, "搜索失败,请检查网络连接！");
        } else if (rCode == 32) {
            ToastUtil.show(ChoiceLocationActivity.this, "key验证无效！");
        } else {
            ToastUtil.show(ChoiceLocationActivity.this, "未知错误，请稍后重试!错误码为"
                    + rCode);
        }
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        PopTitleMes.showPopSingle(ChoiceLocationActivity.this, "请添加所在市县区!");

    }
}