package com.exz.antcargo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.LocationManagerProxy;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.CarInfoManageAdapter;
import com.exz.antcargo.activity.adapter.CarLenthAdapter;
import com.exz.antcargo.activity.adapter.MudidiGvAdapter;
import com.exz.antcargo.activity.adapter.PoppuWindowMoreAdapter;
import com.exz.antcargo.activity.adapter.ZaiChongAdapter;
import com.exz.antcargo.activity.bean.CarManageBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.PopuWindowCheXingBean;
import com.exz.antcargo.activity.bean.VehicleBean;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.LocationUtils;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.exz.antcargo.activity.view.DrawableCenterButton;
import com.exz.antcargo.activity.view.NoScrollGridView;
import com.exz.antcargo.activity.xlistView.XListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;

import static com.exz.antcargo.activity.utils.ConstantValue.Area;
import static com.exz.antcargo.activity.utils.ConstantValue.CityName;
import static com.exz.antcargo.activity.utils.ConstantValue.ProvinceName;

/**
 * Created by pc on 2016/8/23.
 * 车源列表
 */
@ContentView(R.layout.activity_car_info_list)
public class CarInfoListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;


    private PopupWindow popupWindow;

    @ViewInject(R.id.lv)
    private XListView lv;

    private NoScrollGridView gv;

    private MudidiGvAdapter mudidiGvAdapter;
    private List<View> listLin;

    private CarInfoManageAdapter<CarManageBean> adapter;//查看记录的adapter

    private int widthPixels;

    @ViewInject(R.id.v_01)
    private View v_01;

    @ViewInject(R.id.v_02)
    private View v_02;

    @ViewInject(R.id.v_03)
    private View v_03;

    @ViewInject(R.id.v_04)
    private View v_04;

    @ViewInject(R.id.tv_mudidi)
    private DrawableCenterButton tv_mudidi;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    @ViewInject(R.id.tv_location)
    private TextView tv_location;


    @ViewInject(R.id.tv_gongduo)
    private DrawableCenterButton tv_gongduo;

    @ViewInject(R.id.tv_placeOfReceipt)
    private TextView tv_placeOfReceipt;


    private boolean isClean = true;

    @ViewInject(R.id.tv_chafa_time)
    private DrawableCenterButton tv_chafa_time;

    @ViewInject(R.id.bt_search)
    private TextView bt_search;


    @ViewInject(R.id.ed_key_word)
    private EditText ed_key_word;

    private RadioGroup rg;

    private Calendar calendar = Calendar.getInstance();

    private String tyep = "";


    private PoppuWindowMoreAdapter<PopuWindowCheXingBean> popuWindowAdater;
    private ZaiChongAdapter<PopuWindowCheXingBean> zaichongAdapter;
    private CarLenthAdapter<PopuWindowCheXingBean> carLenthAdapter;
    private List<PopuWindowCheXingBean> list = new ArrayList<PopuWindowCheXingBean>();
    private List<PopuWindowCheXingBean> listZaiChongList = new ArrayList<PopuWindowCheXingBean>();
    private List<PopuWindowCheXingBean> CarLenthChongList = new ArrayList<PopuWindowCheXingBean>();

    private Context c = CarInfoListActivity.this;

    private int page = 1;
    private List<CarManageBean> listCar = new ArrayList<CarManageBean>();

    private String ZaiChongStr[] = {"0-5", "5-10", "10-20", "20-30", "30-40", "40-50", "50"};
    private String carLenthStr[] = {"0-5", "5-10", "10-15", "10-15"};
    @ViewInject(R.id.iv_null_data)
    private ImageView iv_null_data;

    private boolean refsh = true;

    private RadioButton rb_01;
    private RadioButton rb_02;
    private RadioButton rb_03;

    String provinceStr = "";
    String cityStre = "";
    String countyStr = "";

    private String useCarType = "";
    private String placeOfReceipt = "";
    private String destinationAreaId = "";
    private String keyword = "";
    private String entruckingDate = "";
    private String carManagementId = "";
    private String deadweightTonnageId = "";
    private String carLenghtManagementId = "";

    @ViewInject(R.id.rl_location)
    private RelativeLayout rl_location;
    private List<VehicleBean> listVehicle;

    private ArrayList<AddressPicker.City> cityStr = new ArrayList<AddressPicker.City>();
    private ArrayList<AddressPicker.County> counties = new ArrayList<AddressPicker.County>();

    private List<String> carType = new ArrayList<String>();
    private List<String> zai = new ArrayList<String>();
    private List<String> carLong = new ArrayList<String>();

    private ArrayList<AddressPicker.Province> data;

    private int yearS, monthS, dayS;

    @Override
    public void initView() {
        llBack.setOnClickListener(this);
        adapter = new CarInfoManageAdapter<CarManageBean>(CarInfoListActivity.this);
        lv.setAdapter(adapter);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        listLin = new ArrayList<View>();
        listLin.add(v_01);
        listLin.add(v_02);
        listLin.add(v_03);
        listLin.add(v_04);
        for (int i = 0; i < listLin.size(); i++) {
            changeWidthAndHeight(listLin.get(i));
        }

        tv_mudidi.setOnClickListener(this);
        tv_chafa_time.setOnClickListener(this);
        tv_gongduo.setOnClickListener(this);
        popuWindowAdater = new PoppuWindowMoreAdapter<PopuWindowCheXingBean>(CarInfoListActivity.this);
        popuWindowAdater.addendData(list, true);
        lv.setPullRefreshEnable(true);
        lv.setPullLoadEnable(true);
        lv.setXListViewListener(this);
        rl_location.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        tv_placeOfReceipt.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        for (String name : ZaiChongStr) {
            PopuWindowCheXingBean zaichong = new PopuWindowCheXingBean();
            zaichong.setName(name);
            listZaiChongList.add(zaichong);
        }
        zaichongAdapter = new ZaiChongAdapter<PopuWindowCheXingBean>(CarInfoListActivity.this);
        zaichongAdapter.addendData(listZaiChongList, true);

        for (String name : carLenthStr) {
            PopuWindowCheXingBean carLenthBean = new PopuWindowCheXingBean();
            carLenthBean.setName(name);
            CarLenthChongList.add(carLenthBean);
        }

        carLenthAdapter = new CarLenthAdapter<PopuWindowCheXingBean>(CarInfoListActivity.this);
        carLenthAdapter.addendData(CarLenthChongList, true);

        tv_location.setText(ConstantValue.Address);


        AddressPicker.City area = new AddressPicker.City();
        AddressPicker.County county = new AddressPicker.County();
        area.setAreaName("不限");
        area.setAreaId("");

        county.setAreaName("不限");
        county.setAreaId("");

        counties.add(county);
        area.setCounties(counties);

        cityStr.add(area);
    }

    @Override
    public void initData() {
        yearS = calendar.get(Calendar.YEAR);
        monthS = calendar.get(Calendar.MONTH) + 1;
        dayS = calendar.get(Calendar.DAY_OF_MONTH);
        infoList();
        RequestParams p = new RequestParams(Constant.VEHICLEPARAMETERS);
        XUtilsApi xUtilsApi = new XUtilsApi();

        xUtilsApi.sendUrl(CarInfoListActivity.this, "post", p, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    list = JSON.parseArray(optJSONObject.optString("car"), (PopuWindowCheXingBean.class));
                    popuWindowAdater.addendData(list, true);
                    popuWindowAdater.updateAdapter();
                } else {
                    Intent intent = new Intent(CarInfoListActivity.this, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
        lv.setEmptyView(iv_null_data);

        initPicker();
    }

    private void initPicker() {

        data = new ArrayList<AddressPicker.Province>();
        if (!TextUtils.isEmpty(SPutils.getString(c, "city"))) {

            AddressPicker.Province province_ = new AddressPicker.Province();
            province_.setAreaName("不限");
            province_.setCities(cityStr);
            data.add(province_);
            for (AddressPicker.Province p : JSON.parseArray(SPutils.getString(CarInfoListActivity.this, "city"), AddressPicker.Province.class)) {
                AddressPicker.Province province = new AddressPicker.Province();
                province.setAreaId(p.getAreaId());
                province.setAreaName(p.getAreaName());

                AddressPicker.City city1 = new AddressPicker.City();
                city1.setAreaId("");
                city1.setAreaName("不限");
                p.getCities().add(0, city1);
                province.setCities(p.getCities());

                for (AddressPicker.City city : p.getCities()) {
                    AddressPicker.County a = new AddressPicker.County();
                    a.setAreaId("");
                    a.setAreaName("不限");
                    city.getCounties().add(0, a);
                }

                data.add(province);
            }


        } else {
            RequestParams params = new RequestParams(Constant.WORFK_CITY);
            params.setMethod(HttpMethod.POST);
            x.http().post(params, new Callback.CommonCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    JSONObject j = result.optJSONObject("info");
                    String provinces = j.optString("provinces");
                    SPutils.save(c, "city", provinces);

                    AddressPicker.Province province_ = new AddressPicker.Province();
                    province_.setAreaName("不限");
                    province_.setCities(cityStr);
                    data.add(province_);
                    for (AddressPicker.Province p : JSON.parseArray(provinces, AddressPicker.Province.class)) {

                        AddressPicker.Province province = new AddressPicker.Province();
                        province.setAreaId(p.getAreaId());
                        province.setAreaName(p.getAreaName());

                        AddressPicker.City city1 = new AddressPicker.City();
                        city1.setAreaId("");
                        city1.setAreaName("不限");
                        p.getCities().add(0, city1);
                        province.setCities(p.getCities());

                        for (AddressPicker.City city : p.getCities()) {
                            AddressPicker.County a = new AddressPicker.County();
                            a.setAreaId("");
                            a.setAreaName("不限");
                            city.getCounties().add(0, a);
                        }


                        data.add(province);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }


    }


    private void infoList() {

        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.VEHICLE_LIST);
        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
        params.addBodyParameter("page", page + "");// 分页（从1开始，每页10条数据)
        params.addBodyParameter("longitude", ConstantValue.Longitude + "");// 当前位置 的经度，如获取不到，就把认证的地址传过来；如果也没有认证，则显示默认的城市
        params.addBodyParameter("latitude", ConstantValue.Latitude + "");//  当前位置 的纬度


        if (!TextUtils.isEmpty(placeOfReceipt)) {

            params.addBodyParameter("placeOfReceipt", placeOfReceipt);//  收货地
        }
        if (!TextUtils.isEmpty(destinationAreaId)) {

            params.addBodyParameter("destinationAreaId", destinationAreaId);//目的地 区域编号
        }
        if (!TextUtils.isEmpty(keyword)) {

            params.addBodyParameter("keyword", keyword);//输入框搜索内容
        }
        if (!TextUtils.isEmpty(entruckingDate)) {

            params.addBodyParameter("entruckingDate", entruckingDate);// 出发时间 2016-8-30
        }

        if (!TextUtils.isEmpty(useCarType)) {
            params.addBodyParameter("useCarTypeId", useCarType);//  收货地
        }
        if (!TextUtils.isEmpty(carManagementId)) {

            params.addBodyParameter("carManagementId", carManagementId.substring(0, carManagementId.length() - 1));//  车型的id 出现多个车型字符串拼接逗号隔开,(如：1,2,3)
        }
        if (!TextUtils.isEmpty(deadweightTonnageId)) {

            params.addBodyParameter("deadweightTonnageId", deadweightTonnageId.substring(0, deadweightTonnageId.length() - 1));//  载重的 id 出现多个载重字符串拼接逗号隔开
        }
        if (!TextUtils.isEmpty(carLenghtManagementId)) {

            params.addBodyParameter("carLenght", carLenghtManagementId.substring(0, carLenghtManagementId.length() - 1));// 车长的 id 出现多个车长字符串拼接逗号隔开
        }

        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    listCar = JSON.parseArray(json, CarManageBean.class);
                    adapter.addendData(listCar, refsh, "1");//1 查看车主 2 编辑
                    adapter.updateAdapter();

                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime(Utils.getCurrentHour());


    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_key_word.getWindowToken(), 0);
    }

    private void changeWidthAndHeight(View tv) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        int size = widthPixels / 4;
        layoutParams.width = size;
        tv.setLayoutParams(layoutParams);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                } else {

                    finish();
                }
                break;

            case R.id.bt_search:

                refsh = true;
                keyword = ed_key_word.getText().toString().trim();
                page = 1;
                infoList();
                break;

            case R.id.tv_placeOfReceipt:
                if (Utils.isNetworkConnected(CarInfoListActivity.this)) {

                    refsh = true;
                    Intent intent = new Intent(CarInfoListActivity.this, ChoiceLocationActivity.class);
                    intent.putExtra("name", "收货地");
                    startActivityForResult(intent, 120);
                } else {
                    Toast.makeText(this, "网络没有开启", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.tv_mudidi://目的地

                refsh = true;
                AddressPicker pickerMudidi = new AddressPicker(CarInfoListActivity.this, data);
                pickerMudidi.setSelectedItem(provinceStr.equals("") ? ProvinceName : provinceStr, cityStre.equals("") ? CityName : cityStre, countyStr.equals("") ? Area : countyStr);
                pickerMudidi.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {


                        destinationAreaId = (TextUtils.isEmpty(province.getAreaId()) ? "" :
                                TextUtils.isEmpty(city.getAreaId()) ? province.getAreaId() :
                                        TextUtils.isEmpty(county.getAreaId()) ? city.getAreaId() : county.getAreaId());
                        tv_mudidi.setText(TextUtils.isEmpty(province.getAreaId()) ? "目的地" :
                                TextUtils.isEmpty(city.getAreaId()) ? province.getAreaName() :
                                        TextUtils.isEmpty(county.getAreaId()) ? city.getAreaName() : county.getAreaName());
                        provinceStr = province.getAreaName();
                        cityStre = city.getAreaName();
                        countyStr = county.getAreaName();
                        page = 1;
                        infoList();
                        if (TextUtils.isEmpty(province.getAreaId()) && TextUtils.isEmpty(city.getAreaId()) && TextUtils.isEmpty(county.getAreaId())) {
                            setGaryOrBlue(tv_mudidi, true);

                        } else {
                            setGaryOrBlue(tv_mudidi, false);

                        }
                    }
                });
                pickerMudidi.setCancelText("不限");
                pickerMudidi.setOnCancelListener(new DatePicker.OnCancelListener() {
                    @Override
                    public void cancel() {
                        tv_mudidi.setText("目的地");
                        destinationAreaId="";
                                page = 1;
                        infoList();
                        setGaryOrBlue(tv_mudidi, true);
                    }
                });

                pickerMudidi.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        radioGroup.clearCheck();
                    }
                });
                pickerMudidi.show();


                break;

            case R.id.tv_chafa_time://出发时间


                refsh = true;
                int year = calendar.get(Calendar.YEAR);
                DatePicker picker = new DatePicker(CarInfoListActivity.this);
                picker.setRange(year, year + 36);
                picker.setCancelText("不限");
                picker.setSelectedItem(yearS, monthS, dayS);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        entruckingDate = year + "-" + month + "-" + day;
                        yearS = Integer.parseInt(year);
                        monthS = Integer.parseInt(month);
                        dayS = Integer.parseInt(day);
                        infoList();
                        tv_chafa_time.setText(entruckingDate);
                        if (TextUtils.isEmpty(year) && TextUtils.isEmpty(month) && TextUtils.isEmpty(day)) {
                            setGaryOrBlue(tv_chafa_time, true);
                        } else {
                            setGaryOrBlue(tv_chafa_time, false);
                        }
                    }
                });
                picker.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        radioGroup.clearCheck();
                    }
                });
                picker.setOnCancelListener(new DatePicker.OnCancelListener() {
                    @Override
                    public void cancel() {
                        entruckingDate = "";
                        tv_chafa_time.setText("出发时间");
                        page = 1;
                        infoList();
                        setGaryOrBlue(tv_chafa_time, true);
                    }
                });
                picker.show();
                break;
            case R.id.tv_gongduo://更多
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }
                refsh = true;
                View menuView4 = LayoutInflater.from(CarInfoListActivity.this).inflate(R.layout.pop_gongduo, null);

                GridView gv_carxing_01 = (GridView) menuView4.findViewById(R.id.gv_carxing_01);
                GridView gv_zaizhong_02 = (GridView) menuView4.findViewById(R.id.gv_zaizhong_02);
                GridView gv_chezhang_03 = (GridView) menuView4.findViewById(R.id.gv_chezhang_03);
                Button bt_clean = (Button) menuView4.findViewById(R.id.bt_clean);
                Button bt_sumbint = (Button) menuView4.findViewById(R.id.bt_sumbint);
                rg = (RadioGroup) menuView4.findViewById(R.id.rg);
                rb_01 = (RadioButton) menuView4.findViewById(R.id.rb_01);
                final RadioButton rb_02 = (RadioButton) menuView4.findViewById(R.id.rb_02);
                rb_03 = (RadioButton) menuView4.findViewById(R.id.rb_03);

                gv_carxing_01.setAdapter(popuWindowAdater);
                gv_zaizhong_02.setAdapter(zaichongAdapter);
                gv_chezhang_03.setAdapter(carLenthAdapter);

                gv_zaizhong_02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        zaichongAdapter.getAdapterData().get(i).setSelectState(true);
                    }
                });
                gv_chezhang_03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        carLenthAdapter.getAdapterData().get(i).setSelectState(true);
                    }
                });


                if (useCarType.equals("0")) {
                    rb_01.setChecked(true);
                } else if (useCarType.equals("1")) {
                    rb_02.setChecked(true);
                } else if (useCarType.equals("2")) {
                    rb_03.setChecked(true);
                }

                popupWindow = new PopupWindow(menuView4, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, true);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(CarInfoListActivity.this.findViewById(R.id.rl_lin), 0, 0);


                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                        radioGroup.clearCheck();
                    }
                });
                menuView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();


                    }
                });
                if (isClean) {
                    for (PopuWindowCheXingBean i : popuWindowAdater.getAdapterData()) {
                        i.setSelectState(false);
                    }
                    for (PopuWindowCheXingBean i : zaichongAdapter.getAdapterData()) {
                        i.setSelectState(false);
                    }

                    for (PopuWindowCheXingBean i : carLenthAdapter.getAdapterData()) {
                        i.setSelectState(false);
                    }
                    popuWindowAdater.updateAdapter();
                    zaichongAdapter.updateAdapter();
                    carLenthAdapter.updateAdapter();
                }

                bt_clean.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isClean = true;
                        rg.clearCheck();

                        placeOfReceipt = "";
                        destinationAreaId = "";
                        keyword = "";
                        entruckingDate = "";
                        carManagementId = "";
                        deadweightTonnageId = "";
                        carLenghtManagementId = "";
                        for (PopuWindowCheXingBean i : popuWindowAdater.getAdapterData()) {
                            i.setSelectState(false);
                        }
                        for (PopuWindowCheXingBean i : zaichongAdapter.getAdapterData()) {
                            i.setSelectState(false);
                        }

                        for (PopuWindowCheXingBean i : carLenthAdapter.getAdapterData()) {
                            i.setSelectState(false);
                        }
                        useCarType = "";
//                        carType.clear();
//                        zai.clear();
//                        carLong.clear();
                        popuWindowAdater.updateAdapter();
                        zaichongAdapter.updateAdapter();
                        carLenthAdapter.updateAdapter();
                        setGaryOrBlue(tv_gongduo, true);

                    }
                });
                bt_sumbint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        carManagementId = "";
                        deadweightTonnageId = "";
                        carLenghtManagementId = "";
                        if(rb_01.isChecked()){
                            useCarType = "0";
                        }else if(rb_02.isChecked()){
                            useCarType = "1";
                        }else if(rb_03.isChecked()){
                            useCarType = "2";
                        }

                        for (PopuWindowCheXingBean i : popuWindowAdater.getAdapterData()) {
                            if (i.isSelectState()) {

                                carManagementId += i.getTypeId() + ","; //车型
                                isClean = false;
                            }
                        }
                        for (PopuWindowCheXingBean i : zaichongAdapter.getAdapterData()) { //载重
                            if (i.isSelectState()) {
                                deadweightTonnageId += i.getName() + ",";
                                isClean = false;
                            }
                        }
                        for (PopuWindowCheXingBean i : carLenthAdapter.getAdapterData()) {//车长
                            if (i.isSelectState()) {
                                carLenghtManagementId += i.getName() + ",";
                                isClean = false;
                            }
                        }
                        if (TextUtils.isEmpty(carManagementId + deadweightTonnageId + carLenghtManagementId + useCarType)) {
                            setGaryOrBlue(tv_gongduo, true);
                        } else {
                            setGaryOrBlue(tv_gongduo, false);
                        }
                        page = 1;
                        refsh = true;
                        infoList();
                        popupWindow.dismiss();
                    }
                });

                break;
            case R.id.rl_location:

                getLocation();

                break;


        }
    }

    private void getLocation() {
        LocationUtils locationUtils = new LocationUtils();
        locationUtils.setLocationListener(new LocationUtils.LocationCallBack() {
            @Override
            public void Location() {
                refsh = true;
                infoList();
            }

            @Override
            public void LocationFail() {
            }
        });
        locationUtils.checkLocation(getApplicationContext(),
                LocationManagerProxy.getInstance(getApplicationContext()));

    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        } else {
            finish();
        }
    }


    @Subscribe
    public void carManagementId(MainSendEvent e) {
        if (e != null) {

            switch (e.getStringMsgData()) {
                case "1":
                    popuWindowAdater.getAdapterData().get((int) e.getT()).setSelectState(e.getBoolean());
//                    if (popuWindowAdater.getAdapterData().get((int) e.getT()).isSelectState()) {
//                        carType.add(popuWindowAdater.getAdapterData().get((int) e.getT()).getTypeId());
//                    } else {
//                        for (String i : carType) {
//                            if (i.equals(popuWindowAdater.getAdapterData().get((int) e.getT()).getTypeId())) {
//                                carType.remove(i);
//                            }
//                        }
//
//                    }
                    break;

                case "2":
                    zaichongAdapter.getAdapterData().get((int) e.getT()).setSelectState(e.getBoolean());
//                    if (zaichongAdapter.getAdapterData().get((int) e.getT()).isSelectState()) {
//                        zai.add(zaichongAdapter.getAdapterData().get((int) e.getT()).getName());
//                    } else {
//                        for (String i : zai) {
//                            if (i.equals(zaichongAdapter.getAdapterData().get((int) e.getT()).getName())) {
//                                zai.remove(i);
//                            }
//                        }
//
//                    }
                    break;

                case "3":
                    carLenthAdapter.getAdapterData().get((int) e.getT()).setSelectState(e.getBoolean());

//                    if (carLenthAdapter.getAdapterData().get((int) e.getT()).isSelectState()) {
//                        carLong.add(carLenthAdapter.getAdapterData().get((int) e.getT()).getName());
//                    } else {
//                        for (String i : carLong) {
//                            if (i.equals(carLenthAdapter.getAdapterData().get((int) e.getT()).getName())) {
//                                carLong.remove(i);
//                            }
//                        }
//
//                    }
                    break;

            }

        }
    }

    /**
     * 设置灰色还是蓝色 箭头
     *
     * @param b true gary  ; false blue
     */
    private void setGaryOrBlue(TextView view, boolean b) {
        if (b) {
            view.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.selector_gary_sanjiao), null);
            view.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        } else {
            view.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.selector_blue_sanjiao), null);
            view.setTextColor(ContextCompat.getColor(this, R.color.blueness));
        }

    }

    @Override
    public void onRefresh() {

        page = 1;
        refsh = true;
        initData();

    }

    @Override
    public void onLoadMore() {

        page++;
        refsh = false;
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 120:
                if (null != data && !TextUtils.isEmpty(data.getStringExtra("address") + data.getStringExtra("latitude") + data.getStringExtra("longitude"))) {
                    placeOfReceipt = data.getStringExtra("address");
                    ConstantValue.Latitude = Double.parseDouble(data.getStringExtra("latitude"));
                    ConstantValue.Longitude = Double.parseDouble(data.getStringExtra("longitude"));
                    if (TextUtils.isEmpty(data.getStringExtra("address") + data.getStringExtra("latitude") + data.getStringExtra("longitude"))) {
                        tv_placeOfReceipt.setTextColor(ContextCompat.getColor(this, R.color.gary_black));
                    } else {
                        tv_placeOfReceipt.setTextColor(ContextCompat.getColor(this, R.color.blueness));
                    }
                    refsh = true;
                    page = 1;
                    infoList();
                } else {
                    placeOfReceipt = "";
                    page = 1;
                    getLocation();
                    tv_placeOfReceipt.setTextColor(ContextCompat.getColor(this, R.color.gary_black));
                    refsh = true;
                }


                break;
        }
    }
}
