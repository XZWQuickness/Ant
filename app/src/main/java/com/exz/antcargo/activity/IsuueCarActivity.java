package com.exz.antcargo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.CarDetailBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.fragemt.SelectJieDanDialog;
import com.exz.antcargo.activity.fragemt.TianXieAddressDialog;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

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

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;

import static com.exz.antcargo.R.id.rb_01;
import static com.exz.antcargo.R.id.rb_02;
import static com.exz.antcargo.activity.utils.ConstantValue.RESULT;

/**
 * Created by pc on 2016/8/22.
 * 发布车源
 */
@ContentView(R.layout.activiy_isuuecar)
public class IsuueCarActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_destination)
    private TextView tv_destination;

    @ViewInject(R.id.tv_placeOfReceipt)
    private TextView tv_placeOfReceipt;

    private LatLng latLng;

    @ViewInject(R.id.tv_entruckingDate)
    private TextView tv_entruckingDate;
    @ViewInject(R.id.rl_entruckingDate)
    private RelativeLayout rl_entruckingDate;

    @ViewInject(R.id.tv_entruckingTime)
    private TextView tv_entruckingTime;

    @ViewInject(R.id.tv_distanceAround)
    private TextView tv_distanceAround;

    @ViewInject(R.id.tv_owner_Vehicle_Cer)
    private TextView tv_owner_Vehicle_Cer;

    @ViewInject(R.id.ed_note)
    private EditText ed_note;


    private String initEndDateTime; // 初始化结束时间-4-1-14:20:30

    @ViewInject(R.id.rl_select_address)
    private RelativeLayout rl_select_address;

    @ViewInject(R.id.ll_yantuluming)
    private LinearLayout ll_yantuluming;

    @ViewInject(R.id.rl_jiduanquyu)
    private RelativeLayout rl_jiduanquyu;


    @ViewInject(R.id.rl_select_car)
    private RelativeLayout rl_select_car;

    @ViewInject(R.id.rl_shouhuodi)
    private RelativeLayout rl_shouhuodi;

    @ViewInject(R.id.rl_time_duan)
    private RelativeLayout rl_time_duan;

    @ViewInject(R.id.rl_yantuluming)
    private RelativeLayout rl_yantuluming;

    @ViewInject(R.id.rg)
    private RadioGroup rg;

    private String yantu = "";

    private Calendar calendar = Calendar.getInstance();
    @ViewInject(R.id.tv_alongRoad)
    private TextView tv_alongRoad;
    private double latitude;
    private double longitude;
    private String flag = "";
    private double startlat = 0.00, startlng = 0.00, endlat = 0.00,
            endlng = 0.00;
    private GeocodeSearch geocoderSearch;

    @ViewInject(R.id.tv_destinationAddress)
    private TextView tv_destinationAddress;

    private Context c = IsuueCarActivity.this;

    private String entruckingDate = "不限";
    private String entruckingTime = "不限";
    private String placeOfReceipt = "不限";

    private String placeOfReceiptLongitude = "";
    private String placeOfReceiptLatitude = "";
    private String distanceAround = "";
    private String destinationAddress = "";

    private String destinationProvinceId = "";
    private String destinationCityId = "";
    private String destinationAreaId = "";

    private String provinceName = "";
    private String CityName = "";
    private String AreaName = "";

    private String ownerVehicleCerId = "";
    private String destinationLongitude = "";
    private String destinationLatitude = "";
    private String alongRoad = "";
    private String note = "";


    private String useCarType = "";
    private String stateAddresss = "";
    //    @ViewInject(R.id.bt_sumbint)
//    private Button bt_sumbint;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    private CarDetailBean carDetailBean;

    int year;
    int month;
    int Day;
    int hour;
    int minute;
    private TimePicker timeDuan;
    private DatePicker picker;
    private AddressPicker pickerMudidi;
    private ArrayList<AddressPicker.Province> data;
    private ArrayList<AddressPicker.City> cityStr = new ArrayList<AddressPicker.City>();
    private ArrayList<AddressPicker.County> counties = new ArrayList<AddressPicker.County>();

    @Override
    public void initView() {

        if (!EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().register(this);

        }
        tv_title.setText("发布车源");
        tv_right.setText("发布");
        llBack.setOnClickListener(this);
        rl_entruckingDate.setOnClickListener(this);
        rl_time_duan.setOnClickListener(this);
        System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        month++;
        initEndDateTime = year + "年" + month + "月" + Day + "日" + " " + hour + ":" + minute;
        rl_select_address.setOnClickListener(this);
        rl_jiduanquyu.setOnClickListener(this);
        rl_select_car.setOnClickListener(this);
        rl_yantuluming.setOnClickListener(this);
        rl_shouhuodi.setOnClickListener(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case rb_01:
                        useCarType = "1";
                        ll_yantuluming.setVisibility(View.GONE);
                        break;

                    case rb_02:
                        useCarType = "2";
                        ll_yantuluming.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });
        ((RadioButton) findViewById(rb_02)).setChecked(true);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("editId"))) {
            carDetailInfo(getIntent().getStringExtra("editId"));
        }


        initPicker();
    }

    private void initPicker() {
        picker = new DatePicker(this);
        picker.setRange(year, year + 30);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        timeDuan = new TimePicker(this, TimePicker.HOUR_OF_DAY);
        timeDuan.setTopLineVisible(false);
        data = new ArrayList<AddressPicker.Province>();
        if (!TextUtils.isEmpty(SPutils.getString(c, "city"))) {

            AddressPicker.Province province_ = new AddressPicker.Province();
            province_.setAreaName("不限");
            province_.setCities(cityStr);
            data.add(province_);
            for (AddressPicker.Province p : JSON.parseArray(SPutils.getString(IsuueCarActivity.this, "city"), AddressPicker.Province.class)) {
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

    private void carDetailInfo(String editId) {
        XUtilsApi xUtilsApi = new XUtilsApi();//车源详情
        RequestParams params = new RequestParams(Constant.CAR_DETAIL);
        params.addBodyParameter("supplyCarsId", editId);
        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    carDetailBean = JSON.parseObject(json, CarDetailBean.class);
                    tv_entruckingDate.setText(carDetailBean.getEntruckingDate());//装车日期


                    tv_entruckingTime.setText(carDetailBean.getEntruckingTime());//装车时间
                    tv_placeOfReceipt.setText(carDetailBean.getPlaceOfReceipt());//收货地
                    tv_distanceAround.setText(carDetailBean.getDistanceAround() + "公里");//接单区域
                    tv_destination.setText(carDetailBean.getDestination());//目的地
                    tv_destinationAddress.setText(carDetailBean.getDestinationAddress());//目的地详细地址
                    tv_alongRoad.setText(carDetailBean.getAlongRoad());//沿途路名
                    if (carDetailBean.getUseCarType().equals("1")) {//空车
                        ((RadioButton) findViewById(R.id.rb_01)).setChecked(true);
                        ((RadioButton) findViewById(R.id.rb_02)).setChecked(false);
                    } else if (carDetailBean.getUseCarType().equals("2")) {//顺风车
                        ((RadioButton) findViewById(R.id.rb_01)).setChecked(false);
                        ((RadioButton) findViewById(R.id.rb_02)).setChecked(true);
                    }
                    tv_owner_Vehicle_Cer.setText(carDetailBean.getOwner_Vehicle_Cer());//车源信息
                    ed_note.setText(carDetailBean.getNote());//备注
                    ownerVehicleCerId = carDetailBean.getOwner_Vehicle_CerId();
                    placeOfReceiptLongitude = carDetailBean.getPlaceOfReceiptLongitude();
                    placeOfReceiptLatitude = carDetailBean.getPlaceOfReceiptLatitude();

                    entruckingDate = carDetailBean.getEntruckingDate();
                    placeOfReceipt = carDetailBean.getPlaceOfReceipt();
                    distanceAround = carDetailBean.getDistanceAround();
                    destinationAddress = carDetailBean.getDestinationAddress();
                    alongRoad = carDetailBean.getAlongRoad();
                    yantu = carDetailBean.getAlongRoad();
                    note = carDetailBean.getNote();
                    useCarType = carDetailBean.getUseCarType();


                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        TianXieAddressDialog tianxieaddress = new TianXieAddressDialog();
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_entruckingDate:

                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {

                        if (calendar.get(Calendar.MONTH) + 1 > Integer.parseInt(month)) {

                            Toast.makeText(IsuueCarActivity.this, "请选择正确月份!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (calendar.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(day)) {
                            Toast.makeText(IsuueCarActivity.this, "请选择正确天数", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        tv_entruckingDate.setText(year + "-" + month + "-" + day);
                        entruckingDate = year + "-" + month + "-" + day;

                    }
                });
                picker.show();
                break;

            case R.id.rl_time_duan:
                if (TextUtils.isEmpty(tv_entruckingDate.getText().toString().trim())) {
                    PopTitleMes.showPopSingle(IsuueCarActivity.this, "亲~请先选择装车日期!");
                    return;
                }
                //默认选中当前时间
                timeDuan.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        int hourS = Integer.parseInt(hour);
                        int minuteS = Integer.parseInt(minute);
                        hourS = hourS < 10 ? Integer.parseInt(hour.substring(1, hour.length())) : hourS;
                        minuteS = minuteS < 10 ? Integer.parseInt(minute.substring(1, minute.length())) : minuteS;
                        tv_entruckingTime.setText(hourS + ":00-" + minuteS + ":00");
                        entruckingTime = hourS + ":00-" + minuteS + ":00";

                    }
                });
                timeDuan.show();
                break;


            case R.id.rl_jiduanquyu: //接单区域
                stateAddresss = "1";
                SelectJieDanDialog dialog = new SelectJieDanDialog();
                Bundle bundle = new Bundle();
                bundle.putString("distanceAround", distanceAround);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditNameDialog");
                break;

            case R.id.rl_select_address: //目的地
                AddressPicker pickerMudidi = new AddressPicker(IsuueCarActivity.this, data);
                pickerMudidi.setSelectedItem(TextUtils.isEmpty(provinceName) ? ConstantValue.ProvinceName : provinceName,
                        TextUtils.isEmpty(CityName) ? ConstantValue.CityName : CityName,
                        TextUtils.isEmpty(AreaName) ? ConstantValue.Area : AreaName);
                pickerMudidi.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
                        tv_destination.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                        destinationProvinceId = province.getAreaId();
                        destinationCityId = city.getAreaId();
                        destinationAreaId = county.getAreaId();

                        provinceName = province.getAreaName();
                        CityName = city.getAreaName();
                        AreaName = county.getAreaName();


                    }
                });

                pickerMudidi.setCancelText("不限");
                pickerMudidi.setOnCancelListener(new DatePicker.OnCancelListener() {
                    @Override
                    public void cancel() {
                        tv_destination.setText("");
                        destinationProvinceId = "";
                        destinationCityId = "";
                        destinationAreaId = "";

                        provinceName = "";
                        CityName = "";
                        AreaName = "";
                    }
                });
                pickerMudidi.show();


                break;


            case R.id.rl_select_car: //选择车辆
                Intent selectCar = new Intent(IsuueCarActivity.this, SelectCarActivity.class);
                selectCar.putExtra("ownerVehicleCerId", ownerVehicleCerId);
                startActivityForResult(selectCar, 123);
                break;

            case R.id.rl_yantuluming: //沿途路名
                Intent yantu = new Intent(IsuueCarActivity.this, YanTuLuMing.class);
                yantu.putExtra("yantu", this.yantu);
                startActivityForResult(yantu, 0x1100);
                break;

            case R.id.rl_shouhuodi: //收货地
                if (Utils.isNetworkConnected(IsuueCarActivity.this)) {
                    Intent intent = new Intent(IsuueCarActivity.this, ChoiceLocationActivity.class);
                    intent.putExtra("name", "收货地");
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(this, "网络没有开启", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.tv_right:
                String url = "";
                XUtilsApi xUtilsApi = new XUtilsApi();
                String entruckingDateStr = tv_entruckingDate.getText().toString().trim();//装车日期
                String entruckingTimeStr = tv_entruckingTime.getText().toString().trim();//装车时间
                String placeOfReceiptStr = tv_placeOfReceipt.getText().toString().trim();//收货地址
                String distanceAroundStr = tv_distanceAround.getText().toString().trim();//接单区域
                String destinationStr = tv_destination.getText().toString().trim();//目的地
                destinationAddress = tv_destinationAddress.getText().toString().trim();//目的地
                String owner_Vehicle_CerStr = tv_owner_Vehicle_Cer.getText().toString().trim();//车辆选择
                String alongRoadStr = tv_alongRoad.getText().toString().trim();//沿途路名
                String noteStr = ed_note.getText().toString().trim();//备注
                if (TextUtils.isEmpty(getIntent().getStringExtra("editId")) & carDetailBean == null) {
                    url = Constant.SEND_VECHICLE;
                } else {
                    url = Constant.EDIT_SEND_CAR;

                }
                RequestParams p = new RequestParams(url);

                if (TextUtils.isEmpty(getIntent().getStringExtra("editId"))) {


                    if (TextUtils.isEmpty(placeOfReceiptStr)) {
                        Intent message = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", "请填写收货地址!");
                        startActivity(message);
                        return;
                    }

                    if (TextUtils.isEmpty(distanceAroundStr)) {
                        Intent message = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", "请填写接单区域!");
                        startActivity(message);
                        return;
                    }

                    if (TextUtils.isEmpty(owner_Vehicle_CerStr)) {
                        Intent message = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", "请选择车辆!");
                        startActivity(message);
                        return;
                    }
                    p.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
                    p.addBodyParameter("entruckingDate", entruckingDate);
                    p.addBodyParameter("entruckingTime", entruckingTime);
                    p.addBodyParameter("placeOfReceipt", placeOfReceipt);//收货地址

                    p.addBodyParameter("placeOfReceiptLatitude", placeOfReceiptLatitude);
                    p.addBodyParameter("placeOfReceiptLongitude", placeOfReceiptLongitude);
                    p.addBodyParameter("distanceAround", distanceAround);//接单区域


                    p.addBodyParameter("destinationProvinceId", destinationProvinceId);//省id
                    p.addBodyParameter("destinationCityId", destinationCityId);//市id
                    p.addBodyParameter("destinationAreaId", destinationAreaId);//区id

                    p.addBodyParameter("destinationAddress", destinationAddress);//目的地详细地址
                    p.addBodyParameter("destinationLongitude", destinationLongitude);
                    p.addBodyParameter("destinationLatitude", destinationLatitude);
                    p.addBodyParameter("ownerVehicleCerId", ownerVehicleCerId);//车辆id
                    p.addBodyParameter("useCarType", useCarType);//是否顺风车 1 否 2 是
                    p.addBodyParameter("alongRoad", alongRoad);//沿途路名
                    p.addBodyParameter("note", noteStr);//备注

                } else {
                    p.addBodyParameter("editId", getIntent().getStringExtra("editId"));
                    if (!TextUtils.isEmpty(entruckingDate)) {

                        p.addBodyParameter("entruckingDate", entruckingDate);
                    }
                    if (!TextUtils.isEmpty(entruckingTime)) {

                        p.addBodyParameter("entruckingTime", entruckingTime);
                    }
                    if (!TextUtils.isEmpty(placeOfReceipt)) {

                        p.addBodyParameter("placeOfReceipt", placeOfReceipt);//收货地址
                    }
                    if (!TextUtils.isEmpty(distanceAround)) {

                        p.addBodyParameter("distanceAround", distanceAround);//接单区域
                    }
                    if (!TextUtils.isEmpty(destinationAddress)) {

                        p.addBodyParameter("destinationAddress", destinationAddress);//目的地详细地址
                    }

                    p.addBodyParameter("ownerVehicleCerId", carDetailBean.getOwner_Vehicle_CerId());//车辆id
                    p.addBodyParameter("useCarType", useCarType);//是否顺风车 1 否 2 是
                    p.addBodyParameter("alongRoad", alongRoad);//沿途路名
                    p.addBodyParameter("note", noteStr);//备注

                }


                xUtilsApi.sendUrl(c, "post", p, true, new XUtilsApi.URLSuccessListenter() {

                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        Intent intent = new Intent(c, DiaLogActivity.class);
                        if (content.getResult().equals(ConstantValue.RESULT)) {

                            goodsCount();

                        } else {
                            intent.putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });

                break;

        }
    }

    private void goodsCount() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams p = new RequestParams(Constant.RECGOODSCOUNT);
        p.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
        p.addBodyParameter("longitude", placeOfReceiptLongitude);
        p.addBodyParameter("latitude", placeOfReceiptLatitude);
        p.addBodyParameter("distanceAround", distanceAround);
        p.addBodyParameter("alongRoad", alongRoad);
        xUtilsApi.sendUrl(c, "post", p, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                Intent intent = new Intent(c, DiaLogActivity.class);
                if (content.getResult().equals(RESULT)) {

                    if (!TextUtils.isEmpty(content.getMessage())) {
                        final AlertDialog dlgtwo = new AlertDialog.Builder(IsuueCarActivity.this)
                                .create();
                        View viewtwo = LayoutInflater.from(IsuueCarActivity.this).inflate(
                                R.layout.pop_check, null);
                        dlgtwo.setView(IsuueCarActivity.this.getLayoutInflater().inflate(
                                R.layout.pop_check, null));
                        dlgtwo.show();
                        dlgtwo.getWindow().setContentView(viewtwo);
                        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                        TextView title = (TextView) viewtwo.findViewById(R.id.title);
                        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
                        dlgtwo.setCanceledOnTouchOutside(false);
                        title.setText(content.getMessage());
                        quxiao.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                dlgtwo.dismiss();
                                finish();


                            }
                        });
                        queding.setOnClickListener(new View.OnClickListener() {

                                                       @Override
                                                       public void onClick(View arg0) {
                                                           dlgtwo.dismiss();
                                                           finish();
                                                           Intent matchingIntent = new Intent(IsuueCarActivity.this, MathingGoodsListActivity.class);
                                                           matchingIntent.putExtra("longitude", placeOfReceiptLongitude);
                                                           matchingIntent.putExtra("latitude", placeOfReceiptLatitude);
                                                           matchingIntent.putExtra("distanceAround", distanceAround);
                                                           matchingIntent.putExtra("alongRoad", alongRoad);
                                                           startActivity(matchingIntent);
                                                       }
                                                   }

                        );
                    } else {
                        PopTitleMes.showPopSingle(IsuueCarActivity.this, "操作成功");
                        finish();
                    }


                } else {
                    PopTitleMes.showPopSingle(IsuueCarActivity.this, "操作成功");
                    finish();
                }
            }
        });


    }


    /**
     * 获取接单区域
     */
    @Subscribe
    public void getdistanceAround(MainSendEvent event) {
        if (event != null) {
            switch (stateAddresss) {
                case "1":
                    if (!TextUtils.isEmpty(event.getStringMsgData())) {

                        tv_distanceAround.setText(event.getStringMsgData() + "公里");
                    } else {
                        tv_distanceAround.setText("0 公里");
                    }
                    distanceAround = event.getStringMsgData();
                    break;

                case "2":
                    tv_destinationAddress.setText(event.getStringMsgData());
                    destinationAddress = event.getStringMsgData();
                    break;

            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                if (null != data && !TextUtils.isEmpty(data.getStringExtra("address"))) {
                    tv_placeOfReceipt.setText(data.getStringExtra("address"));
                    placeOfReceipt = data.getStringExtra("address");
                    placeOfReceiptLatitude = data.getStringExtra("latitude");
                    placeOfReceiptLongitude = data.getStringExtra("longitude");
                }

            case 0x1100:
                if (null != data) {

                    yantu = data.getStringExtra("yantu");
                    tv_alongRoad.setText(yantu);
                    alongRoad = yantu;
                }
                break;


            case 123://选择车辆
                if (null != data && !TextUtils.isEmpty(data.getStringExtra("ownerVehicleCerId"))) {

                    ownerVehicleCerId = data.getStringExtra("ownerVehicleCerId");
                    tv_owner_Vehicle_Cer.setText(data.getStringExtra("ownerVehicleCerName"));
                }
                break;


        }


    }


}
