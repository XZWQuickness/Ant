package com.exz.antcargo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.GoodsBaoZhaungBean;
import com.exz.antcargo.activity.bean.GoodsDetailBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.fragemt.SelectCarXingIsCarLongDialog;
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
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;

import static com.exz.antcargo.activity.utils.ConstantValue.RESULT;
import static com.exz.antcargo.activity.utils.ConstantValue.deadweightTonnage;
import static com.exz.antcargo.activity.utils.ConstantValue.modelsId;

/**
 * Created by pc on 2016/8/22.
 * 发布货源源
 */
@ContentView(R.layout.activiy_isuuegoods)
public class IsuueGoodsActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;


    @ViewInject(R.id.tv_entruckingDate)
    private TextView tv_entruckingDate;


    @ViewInject(R.id.tv_use_vechicle)
    private TextView tv_use_vechicle;

    @ViewInject(R.id.tv_chufadi)
    private TextView tv_chufadi;

    @ViewInject(R.id.tv_destination)
    private TextView tv_destination;

    @ViewInject(R.id.tv_entruckingTime)
    private TextView tv_entruckingTime;

    @ViewInject(R.id.tv_goods_info)
    private TextView tv_goods_info;


    @ViewInject(R.id.rl_origin)
    private RelativeLayout rl_origin;

    @ViewInject(R.id.rl_destination)
    private RelativeLayout rl_destination;

    @ViewInject(R.id.rb_origin)
    private RadioButton rb_origin;

    @ViewInject(R.id.rb_destination)
    private RadioButton rb_destination;

    @ViewInject(R.id.iv_red_location)
    private ImageView iv_red_location;


    @ViewInject(R.id.iv_red_location_02)
    private ImageView iv_red_location_02;


    @ViewInject(R.id.rl_entruckingDate)
    private RelativeLayout rl_entruckingDate;


    @ViewInject(R.id.ll_entruckingTime)
    private LinearLayout ll_entruckingTime;

    private Context c = IsuueGoodsActivity.this;


    private String initEndDateTime; // 初始化结束时间-4-1-14:20:30


    @ViewInject(R.id.rl_jiduanquyu)
    private RelativeLayout rl_jiduanquyu;

    @ViewInject(R.id.rl_xiangxi_address)
    private RelativeLayout rl_xiangxi_address;

    @ViewInject(R.id.rl_select_car)
    private RelativeLayout rl_select_car;

    @ViewInject(R.id.rl_yantuluming)
    private RelativeLayout rl_yantuluming;


    @ViewInject(R.id.rl_goods_info)
    private RelativeLayout rl_goods_info;

    @ViewInject(R.id.ed_low_price)
    private EditText ed_low_price;


    @ViewInject(R.id.ll_price)
    private LinearLayout ll_price;

    @ViewInject(R.id.tv_price)
    private TextView tv_price;

    @ViewInject(R.id.ll_else)
    private LinearLayout ll_else;


    private Calendar calendar = Calendar.getInstance();


    private String entruckingDate = "不限";
    private String entruckingTime = "不限";
    private String origin = "";//出发地
    private String originLongitude = "";//出发经度
    private String originLatitude = "";//出发维度
    private String destination = "";//目的地
    private String destinationLongitude = "";//目的地经度
    private String destinationLatitude = "";//目的地维度
    private String carManagementId = "";//carManagementId 车型的编号
    private String referencePrice = "";//  参考运费
    private String hopePrice = "";//期望金额
    private String goodsName = "";//货物名称
    private String goodsWeight = "";// 货物重量
    private String goodsVolume = "";// 货物体积
    private String goodsPackagingId = "";// 货物包装 编号
    private String goodsCount = "";// 货物数量
    private String goodsImg = "";// 货物图片,多个数据流以逗号隔开 最多5张
    private GoodsBaoZhaungBean bean;

    private List<String> goodsImgsStr = new ArrayList<String>();

    private String deleteGoodsImg = "";
    int year;
    int month;
    int Day;
    int hour;
    int minute;

    private String mapState = "";


    private GoodsDetailBean goodsDetailBean;

    @Override
    public void initView() {
        tv_title.setText("发布货源");
        tv_right.setText("发布");
        llBack.setOnClickListener(this);
        System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        month++;
        initEndDateTime = year + "年" + month + "月" + Day + "日" + " " + hour + ":" + minute;

        rl_entruckingDate.setOnClickListener(this);
        ll_entruckingTime.setOnClickListener(this);
        rl_select_car.setOnClickListener(this);
        rl_goods_info.setOnClickListener(this);
//
        rl_origin.setOnClickListener(this);
        rl_destination.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_else.setOnClickListener(this);
        tv_use_vechicle.addTextChangedListener(this);
    }


//

    @Subscribe
    public void vehicleModel(MainSendEvent event) {
        if (event != null && event.getT() != null) {
            if (!TextUtils.isEmpty(ConstantValue.modelsName)) {
                tv_use_vechicle.setText(ConstantValue.modelsName);
                if (!TextUtils.isEmpty(originLatitude) && !TextUtils.isEmpty(originLatitude) && !TextUtils.isEmpty(destinationLatitude)
                        && !TextUtils.isEmpty(destinationLongitude) && !TextUtils.isEmpty(ConstantValue.modelsId)) {
                    calculateFreight();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ConstantValue.modelsName = "";
        modelsId = "";
        ConstantValue.vehicleLengthName = "";
        ConstantValue.vehicleWidth = "";
        deadweightTonnage = "";
    }

    @Override
    public void initData() {
        rb_origin.addTextChangedListener(this);
        rb_destination.addTextChangedListener(this);
        rb_origin.setOnClickListener(this);
        rb_destination.setOnClickListener(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("edit"))) {
            XUtilsApi xUtilsApi = new XUtilsApi();
            RequestParams params = new RequestParams(Constant.GOODS_DETAIL);
            params.addBodyParameter("supplyGoodsId", getIntent().getStringExtra("editId"));
            xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                @Override
                public void OnSuccess(NewEntity content, JSONObject result) {
                    if (content.getResult().equals(RESULT)) {
                        String json = result.optString("info");
                        JSONObject josonObject = result.optJSONObject("info");
                        goodsDetailBean = JSON.parseObject(json, GoodsDetailBean.class);

                        tv_entruckingDate.setText(goodsDetailBean.getEntruckingDate());//装车时间
                        tv_entruckingTime.setText(goodsDetailBean.getEntruckingTime());

                        entruckingDate = goodsDetailBean.getEntruckingDate();
                        entruckingTime = goodsDetailBean.getEntruckingTime();
                        origin = goodsDetailBean.getOrigin();
                        originLongitude = goodsDetailBean.getOriginLongitude();
                        originLatitude = goodsDetailBean.getOriginLatitude();
                        destination = goodsDetailBean.getDestination();
                        destinationLatitude = goodsDetailBean.getDestinationLatitude();
                        destinationLongitude = goodsDetailBean.getDestinationLongitude();

                        rb_origin.setText(goodsDetailBean.getOrigin());//出发地
                        originLatitude = goodsDetailBean.getOriginLatitude();
                        originLongitude = goodsDetailBean.getOriginLongitude();
                        rb_destination.setText(goodsDetailBean.getDestination());//目的地

                        ConstantValue.modelsId = goodsDetailBean.getCarManagementId();//车型的id
                        tv_use_vechicle.setText(goodsDetailBean.getCarManagement());//用车要求
                        ed_low_price.setText(goodsDetailBean.getHopePrice());
                        tv_price.setText(goodsDetailBean.getHopePrice());
                        tv_goods_info.setText(goodsDetailBean.getGoodsName());


                        org.json.JSONArray array = josonObject.optJSONArray("goodsImg");
                        String photoStr = "";
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                                photoStr += array.optString(i) + ",";
                            }

                            goodsDetailBean.setGoodsImg(photoStr);
                        }


                        calculateFreight();//计算运费

                    } else {
                        Intent intent = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", content.getMessage());
                        startActivity(intent);
                    }
                }
            });

        } else {
            XUtilsApi xUtilsApi = new XUtilsApi();
            RequestParams params = new RequestParams(Constant.GOODSLASTRECORD);
            params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
            xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                @Override
                public void OnSuccess(NewEntity content, JSONObject result) {
                    if (content.getResult().equals(RESULT)) {
                        String json = result.optString("info");
                        JSONObject josonObject = result.optJSONObject("info");
                        goodsDetailBean = JSON.parseObject(json, GoodsDetailBean.class);
                        origin = goodsDetailBean.getOrigin();
                        goodsName=goodsDetailBean.getGoodsName();
                        tv_goods_info.setText(goodsDetailBean.getGoodsName());
                        rb_origin.setText(goodsDetailBean.getOrigin());
                        originLongitude = goodsDetailBean.getOriginLongitude();
                        originLatitude = goodsDetailBean.getOriginLatitude();

                    } else {
                        Intent intent = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", content.getMessage());
                        startActivity(intent);
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(IsuueGoodsActivity.this, ChoiceLocationActivity.class);
        TianXieAddressDialog tianxieaddress = new TianXieAddressDialog();
        switch (view.getId()) {
            case R.id.ll_back:
                ConstantValue.modelsName = "";
                modelsId = "";
                ConstantValue.vehicleLengthName = "";
                ConstantValue.vehicleWidth = "";
                deadweightTonnage = "";
                finish();
                break;

            case R.id.rl_entruckingDate: //装车日期
                DatePicker picker = new DatePicker(this);
                picker.setRange(year, year + 30);
                picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        if (calendar.get(Calendar.MONTH) + 1 > Integer.parseInt(month)) {

                            Toast.makeText(IsuueGoodsActivity.this, "请选择正确月份!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (calendar.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(day)) {
                            Toast.makeText(IsuueGoodsActivity.this, "请选择正确天数", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tv_entruckingDate.setText(year + "-" + month + "-" + day);
                        entruckingDate = year + "-" + month + "-" + day;


                    }
                });
                picker.show();
                break;

            case R.id.ll_entruckingTime: //装车时间
                if (TextUtils.isEmpty(tv_entruckingDate.getText().toString().trim())) {
                    Intent timeIntent = new Intent(c, DiaLogActivity.class);
                    timeIntent.putExtra("message", "亲~请先选择装车日期!");
                    startActivity(timeIntent);
                    return;
                }
                //默认选中当前时间
                TimePicker timeDuan = new TimePicker(this, TimePicker.HOUR_OF_DAY);
                timeDuan.setTopLineVisible(false);
                timeDuan.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        tv_entruckingTime.setText(hour + ":00-" + minute + ":00");
                        entruckingTime = hour + ":00-" + minute + ":00";


                    }
                });
                timeDuan.show();
                break;

            case R.id.rb_origin://出发地
                mapState = "1";
                if (Utils.isNetworkConnected(IsuueGoodsActivity.this)) {
                    intent.putExtra("name", "出发地");
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(this, "网络没有开启", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.rb_destination://目的地
                if (Utils.isNetworkConnected(IsuueGoodsActivity.this)) {
                    mapState = "2";
                    intent.putExtra("name", "目的地");
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(this, "网络没有开启", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.rl_select_car: //用车要求
//                Intent useVechicle = new Intent(c, UseVehicleClaimActivity.class);
//                startActivityForResult(useVechicle, 110);
                SelectCarXingIsCarLongDialog dialog = new SelectCarXingIsCarLongDialog();
                Bundle args = new Bundle();
                args.putString("name", "请选择车型");
                args.putString("state", "1");
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "EditNameDialog");
                break;


            case R.id.rl_goods_info: //货物信息

//                Intent goodsInfo = new Intent(IsuueGoodsActivity.this, GoodsInfoActivity.class);
//                if (bean != null) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("bean", bean);
//                    goodsInfo.putExtras(bundle);
//                }
//                startActivityForResult(goodsInfo, 222);
                break;

            case R.id.tv_right:  //提交发布
                String url = "";

                if (TextUtils.isEmpty(getIntent().getStringExtra("edit"))) {
                    url = Constant.SEND_GOODS;
                } else {
                    url = Constant.EDIT_SEND_VECHICLE;

                }

                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams p = new RequestParams(url);
                entruckingDate = tv_entruckingDate.getText().toString().trim();
                entruckingTime = tv_entruckingTime.getText().toString().trim();
                String use_vechicle = tv_use_vechicle.getText().toString().trim();
                String chufadi = tv_chufadi.getText().toString().trim();
                String mudidi = rb_destination.getText().toString().trim();
                hopePrice = ed_low_price.getText().toString().trim();
                goodsName = tv_goods_info.getText().toString().trim();

                if (TextUtils.isEmpty(getIntent().getStringExtra("editId"))) {
                    if (TextUtils.isEmpty(entruckingDate)) {
                        Intent message = new Intent(c, DiaLogActivity.class);
                        message.putExtra("message", "请选择装车日期");
                        startActivity(message);
                        return;
                    }

                    if (TextUtils.isEmpty(chufadi)) {
                        Intent message = new Intent(c, DiaLogActivity.class);
                        message.putExtra("message", "请选择出发地");
                        startActivity(message);
                        return;
                    }

                    if (TextUtils.isEmpty(mudidi)) {
                        Intent message = new Intent(c, DiaLogActivity.class);
                        message.putExtra("message", "请选择目的地");
                        startActivity(message);
                        return;
                    }
                    if (TextUtils.isEmpty(mudidi)) {
                        Intent message = new Intent(c, DiaLogActivity.class);
                        message.putExtra("message", "请选择目的地");
                        startActivity(message);
                        return;
                    }
                    if (TextUtils.isEmpty(use_vechicle)) {
                        Intent message = new Intent(c, DiaLogActivity.class);
                        message.putExtra("message", "请选择用车车型");
                        startActivity(message);
                        return;
                    }
//                    if (TextUtils.isEmpty(hopePrice)) {
//                        Intent message = new Intent(c, DiaLogActivity.class);
//                        message.putExtra("message", "请输入期望价格");
//                        startActivity(message);
//                        return;
//                    }
                    if (TextUtils.isEmpty(goodsName)) {
                        Intent message = new Intent(c, DiaLogActivity.class);
                        message.putExtra("message", "请输入货物名称");
                        startActivity(message);
                        return;
                    }

                    p.addBodyParameter("accountId", SPutils.getString(c, "accountId"));//账户ID
                    p.addBodyParameter("entruckingDate", entruckingDate);//装车日期
                    p.addBodyParameter("entruckingTime", entruckingTime);//装车时间
                    p.addBodyParameter("origin", origin);//出发地
                    p.addBodyParameter("originLongitude", originLongitude);// 出发地 经度
                    p.addBodyParameter("originLatitude", originLatitude);// 出发地 纬度
                    p.addBodyParameter("destination", destination);//   目的地
                    p.addBodyParameter("destinationLongitude", destinationLongitude);// 目的地 经度
                    p.addBodyParameter("destinationLatitude", destinationLatitude);// 目的地 纬度
                    p.addBodyParameter("carManagementId", modelsId);//  车型 编号
                    p.addBodyParameter("referencePrice", referencePrice);//  参考运费
                    p.addBodyParameter("hopePrice", hopePrice);//  期望金额
                    p.addBodyParameter("goodsName", goodsName);//货物名称
                    p.addBodyParameter("useCarTypeId", goodsDetailBean!=null?goodsDetailBean.getUseCarTypeId():"0");// 是否 顺风车
                    if (goodsDetailBean != null) {

                        if (!TextUtils.isEmpty(goodsDetailBean.getCarLenght())) {

                            p.addBodyParameter("carLenght", goodsDetailBean.getCarLenght());// 车长
                        }
                        if (!TextUtils.isEmpty(goodsDetailBean.getCarWidth())) {
                            p.addBodyParameter("carWidth", goodsDetailBean.getCarWidth());//  载重
                        }
                        if (!TextUtils.isEmpty(goodsDetailBean.getDeadweightTonnage())) {
                            p.addBodyParameter("deadweightTonnage", goodsDetailBean.getDeadweightTonnage());//  载重
                        }
                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsWeight())) {
                            p.addBodyParameter("goodsWeight", goodsDetailBean.getGoodsWeight());//货物重量
                        }

                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsVolume())) {
                            p.addBodyParameter("goodsVolume", goodsDetailBean.getGoodsVolume());//货物体积
                        }

                        if (!TextUtils.isEmpty(goodsDetailBean.getSetGoodsPackagingId())) {
                            p.addBodyParameter("goodsPackagingId", goodsDetailBean.getSetGoodsPackagingId());//货物包装 编号
                        }

                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsCount())) {
                            p.addBodyParameter("goodsCount", goodsDetailBean.getGoodsCount());// 货物数量
                        }


                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsImg())) {
                            p.addBodyParameter("goodsImg", goodsDetailBean.getGoodsImg());//  货物图片,多个数据流以逗号隔开 最多5张
                        }

                        if (!TextUtils.isEmpty(goodsDetailBean.getNote())) {
                            p.addBodyParameter("note", goodsDetailBean.getNote());//  备注
                        }
                    }


                } else {
                    p.addBodyParameter("editId", getIntent().getStringExtra("editId"));//账户ID
                    p.addBodyParameter("entruckingDate", entruckingDate);//装车日期
                    p.addBodyParameter("entruckingTime", entruckingTime);//装车时间
                    p.addBodyParameter("origin", origin);//出发地
                    p.addBodyParameter("originLongitude", originLongitude);// 出发地 经度
                    p.addBodyParameter("originLatitude", originLatitude);// 出发地 纬度
                    p.addBodyParameter("destination", destination);//   目的地
                    p.addBodyParameter("destinationLongitude", destinationLongitude);// 目的地 经度
                    p.addBodyParameter("destinationLatitude", destinationLatitude);// 目的地 纬度
                    p.addBodyParameter("carManagementId", modelsId);//  车型 编号
                    p.addBodyParameter("referencePrice", referencePrice);//  参考运费
                    p.addBodyParameter("hopePrice", hopePrice);//  期望金额
                    p.addBodyParameter("goodsName", goodsName);//货物名称
                    p.addBodyParameter("useCarTypeId", goodsDetailBean!=null?goodsDetailBean.getUseCarTypeId():"0");// 是否 顺风车
                    if (goodsDetailBean != null) {
                        if (!TextUtils.isEmpty(goodsDetailBean.getCarLenght()) && !goodsDetailBean.getCarLenght().equals("不限")) {

                            p.addBodyParameter("carLenght", goodsDetailBean.getCarLenght());// 车长
                        }
                        if (!TextUtils.isEmpty(goodsDetailBean.getCarWidth()) && !goodsDetailBean.getCarWidth().equals("不限")) {
                            p.addBodyParameter("carWidth", goodsDetailBean.getCarWidth());//  载重
                        }
                        if (!TextUtils.isEmpty(goodsDetailBean.getDeadweightTonnage()) && !goodsDetailBean.getDeadweightTonnage().equals("不限")) {
                            p.addBodyParameter("deadweightTonnage", goodsDetailBean.getDeadweightTonnage());//  载重
                        }
                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsWeight()) && !goodsDetailBean.getGoodsWeight().equals("不限")) {
                            p.addBodyParameter("goodsWeight", goodsDetailBean.getGoodsWeight());//货物重量
                        }

                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsVolume()) && !goodsDetailBean.getGoodsVolume().equals("不限")) {
                            p.addBodyParameter("goodsVolume", goodsDetailBean.getGoodsVolume());//货物体积
                        }

                        if (!TextUtils.isEmpty(goodsDetailBean.getSetGoodsPackagingId())) {
                            p.addBodyParameter("goodsPackagingId", goodsDetailBean.getSetGoodsPackagingId());//货物包装 编号
                        }

                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsCount()) && !goodsDetailBean.getGoodsCount().equals("不限")) {
                            p.addBodyParameter("goodsCount", goodsDetailBean.getGoodsCount());// 货物数量
                        }


                        if (!TextUtils.isEmpty(goodsDetailBean.getGoodsImg()) && !goodsDetailBean.getGoodsImg().contains("http")) {
                            p.addBodyParameter("goodsImg", goodsDetailBean.getGoodsImg());//  货物图片,多个数据流以逗号隔开 最多5张
                        }
                        if (!TextUtils.isEmpty(goodsDetailBean.getDeleteGoodsImg())) {
                            p.addBodyParameter("deleteGoodsImg", goodsDetailBean.getDeleteGoodsImg());//  货物图片,多个数据流以逗号隔开 最多5张
                        }

                        p.addBodyParameter("note", goodsDetailBean.getNote());//  备注


                    }


                }
                xUtilsApi.sendUrl(c, "post", p, true, new XUtilsApi.URLSuccessListenter() {

                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        Intent intent = new Intent(c, DiaLogActivity.class);
                        if (content.getResult().equals(RESULT)) {

                            carCount();
                        } else {
                            intent.putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });

                break;

            case R.id.ll_else://其他选择
                Intent elseOptionIntent = new Intent(IsuueGoodsActivity.this, ElseOptionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", goodsDetailBean == null ? null : goodsDetailBean);
                elseOptionIntent.putExtras(bundle);
                elseOptionIntent.putExtra("state", !TextUtils.isEmpty(getIntent().getStringExtra("edit")) ? getIntent().getStringExtra("edit") : "1");
                startActivityForResult(elseOptionIntent, 113);
                break;


        }
    }

    /**
     * 发布货源，匹配的车源列表的数量
     */
    private void carCount() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams p = new RequestParams(Constant.RECVEHICLECOUNT);
        p.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
        p.addBodyParameter("longitude", originLongitude);
        p.addBodyParameter("latitude", originLatitude);
        p.addBodyParameter("origin", origin);
        xUtilsApi.sendUrl(c, "post", p, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                Intent intent = new Intent(c, DiaLogActivity.class);
                if (content.getResult().equals(RESULT)) {

                    if (!TextUtils.isEmpty(content.getMessage())) {
                        final AlertDialog dlgtwo = new AlertDialog.Builder(IsuueGoodsActivity.this)
                                .create();
                        dlgtwo.setCanceledOnTouchOutside(false);
                        View viewtwo = LayoutInflater.from(IsuueGoodsActivity.this).inflate(
                                R.layout.pop_check, null);
                        dlgtwo.setView(IsuueGoodsActivity.this.getLayoutInflater().inflate(
                                R.layout.pop_check, null));
                        dlgtwo.show();
                        dlgtwo.getWindow().setContentView(viewtwo);
                        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                        TextView title = (TextView) viewtwo.findViewById(R.id.title);
                        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
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
                                                           Intent matchingIntent = new Intent(IsuueGoodsActivity.this, MathingCarListActivity.class);
                                                           matchingIntent.putExtra("longitude", originLongitude);
                                                           matchingIntent.putExtra("latitude", originLatitude);
                                                           matchingIntent.putExtra("origin", origin);
                                                           startActivity(matchingIntent);
                                                       }
                                                   }

                        );
                    } else {
                        PopTitleMes.showPopSingle(IsuueGoodsActivity.this, "操作成功");
                        finish();
                    }


                } else {
                    PopTitleMes.showPopSingle(IsuueGoodsActivity.this, "操作成功");
                    finish();
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 100://出发地
                if (mapState.equals("1")) {
                    if (null != data && !TextUtils.isEmpty(data.getStringExtra("address"))) {
                        rb_origin.setText(data.getStringExtra("address"));
                        origin = data.getStringExtra("address");
                        originLatitude = data.getStringExtra("latitude");
                        originLongitude = data.getStringExtra("longitude");
                    }

                } else if (mapState.equals("2")) {//目的地
                    if (null != data && !TextUtils.isEmpty(data.getStringExtra("address"))) {
                        rb_destination.setText(data.getStringExtra("address"));
                        destination = data.getStringExtra("address");
                        destinationLatitude = data.getStringExtra("latitude");
                        destinationLongitude = data.getStringExtra("longitude");
                    }
                }
                break;
            case 222://货物包装
                if (null != data && null != data.getSerializableExtra("bean")) {
                    bean = (GoodsBaoZhaungBean) data.getSerializableExtra("bean");

                    if (!TextUtils.isEmpty(bean.getGoodsName())) {
                        goodsName = bean.getGoodsName();
                    }
                    if (!TextUtils.isEmpty(bean.getGoodsWeight())) {
                        goodsWeight = bean.getGoodsWeight();
                    }
                    if (!TextUtils.isEmpty(bean.getGoodsVolume())) {
                        goodsVolume = bean.getGoodsVolume();
                    }
                    if (!TextUtils.isEmpty(bean.getGoodsPackagingId())) {
                        goodsPackagingId = bean.getGoodsPackagingId();
                    }
                    if (!TextUtils.isEmpty(bean.getGoodsCount())) {
                        goodsCount = bean.getGoodsCount();
                    }

                    tv_goods_info.setText(goodsName + " " + goodsWeight + "吨 " + goodsVolume + "方 " + goodsCount);

                }
                break;

            case 113:
                if (null != data && null != data.getSerializableExtra("goodsDetailBean")) {
                    goodsDetailBean = (GoodsDetailBean) data.getSerializableExtra("goodsDetailBean");
                }
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(originLatitude) && !TextUtils.isEmpty(originLatitude) && !TextUtils.isEmpty(destinationLatitude)
                && !TextUtils.isEmpty(destinationLongitude) && !TextUtils.isEmpty(ConstantValue.modelsId)) {
            calculateFreight();
        }

    }

    /**
     * 计算运费
     */

    private void calculateFreight() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.CALCULATE_FREIGHT);
        params.addBodyParameter("originLongitude", originLongitude);//出发地经度
        params.addBodyParameter("originLatitude", originLatitude);//出发地维度
        params.addBodyParameter("destinationLongitude", destinationLongitude);//目的地维度
        params.addBodyParameter("destinationLatitude", destinationLatitude);//目的地维度
        params.addBodyParameter("carManagementId", ConstantValue.modelsId);//  车型id
        if (goodsDetailBean != null && !TextUtils.isEmpty(goodsDetailBean.getCarLenght())) {

            params.addBodyParameter("carLenght", ConstantValue.vehicleLengthName);//  车长
        }

        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    referencePrice = optJSONObject.optString("price");
                    tv_price.setText(referencePrice + "元");
                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        if (!TextUtils.isEmpty(rb_origin.getText().toString().trim())) {
            iv_red_location.setVisibility(View.VISIBLE);

        } else {

            iv_red_location.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(rb_destination.getText().toString().trim())) {
            iv_red_location_02.setVisibility(View.VISIBLE);
        } else {

            iv_red_location_02.setVisibility(View.GONE);
        }

//        if (TextUtils.isEmpty(tv_use_vechicle.getText().toString().trim())) {
//            ll_price.setVisibility(View.GONE);
//        } else {
//            ll_price.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
