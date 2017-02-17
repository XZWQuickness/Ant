package com.exz.antcargo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.CarDetailBean;
import com.exz.antcargo.activity.bean.KouFeiGoodsBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.NewestGoodsBean;
import com.exz.antcargo.activity.popupwindow.PopGoods;
import com.exz.antcargo.activity.utils.CheckState;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import io.rong.imkit.RongIM;

import static com.exz.antcargo.activity.utils.ConstantValue.CAR_NUMBER_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.CAR_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.GOODS_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResult;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResultMessage;

/**
 * Created by pc on 2016/8/24.
 * 车源信息详情页
 */
@ContentView(R.layout.activity_car_info_detail)
public class CarInfoDetailActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.ll_lin)
    private LinearLayout ll_lin;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_margin)
    private TextView tv_margin;

    @ViewInject(R.id.tv_distanceAround)
    private TextView tv_distanceAround;

    @ViewInject(R.id.tv_shelvesDate)
    private TextView tv_shelvesDate;

    @ViewInject(R.id.tv_entruckingDate)
    private TextView tv_entruckingDate;

    @ViewInject(R.id.tv_car_info_name)
    private TextView tv_car_info_name;

    @ViewInject(R.id.tv_manege_state)
    private TextView tv_manege_state;


    @ViewInject(R.id.iv_vehicleImg)
    private ImageView iv_vehicleImg;

    @ViewInject(R.id.iv_rong_clound)
    private ImageView iv_rong_clound;

    @ViewInject(R.id.iv_car_next)
    private ImageView iv_car_next;


    @ViewInject(R.id.tv_forget_password)
    private TextView tv_forget_password;

    @ViewInject(R.id.tv_placeOfReceipt)
    private TextView tv_placeOfReceipt;

    @ViewInject(R.id.tv_destination)
    private TextView tv_destination;

    @ViewInject(R.id.tv_useCarType)
    private TextView tv_useCarType;

    @ViewInject(R.id.tv_yantu)
    private TextView tv_yantu;

    @ViewInject(R.id.tv_alongRoad)
    private TextView tv_alongRoad;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;


    @ViewInject(R.id.tv_owner_Vehicle_Cer)
    private TextView tv_owner_Vehicle_Cer;

    @ViewInject(R.id.tv_note)
    private TextView tv_note;

    @ViewInject(R.id.tv_01)
    private TextView tv_01;

    @ViewInject(R.id.tv_02)
    private TextView tv_02;


    @ViewInject(R.id.rl_chezhu_info)
    private RelativeLayout rl_chezhu_info;


    @ViewInject(R.id.ll_edit)
    private LinearLayout ll_edit;


    private CarDetailBean carDetailBean;

    private Context c = CarInfoDetailActivity.this;

    private String goodsId;
    @ViewInject(R.id.ll_info_view)
    private LinearLayout ll_info_view;

    @ViewInject(R.id.photo)
    private ImageView photo;

    @ViewInject(R.id.iv_call_phone)
    private ImageView iv_call_phone;

    private KouFeiGoodsBean koufei;


    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    @ViewInject(R.id.tv_addDate)
    private TextView tv_addDate;


    @ViewInject(R.id.tv_goodsCount)
    private TextView tv_goodsCount;

    private List<NewestGoodsBean> newestGoodsBeen;
    private String originLongitude = "";
    private String originLatitude = "";
    private String goodsName = "";


    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        tv_title.setText("车源详情");
        llBack.setOnClickListener(this);
        rl_chezhu_info.setOnClickListener(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            if (getIntent().getStringExtra("type").equals("1")) {
                rl_chezhu_info.setVisibility(View.VISIBLE);
                ll_edit.setVisibility(View.GONE);
            }
            if (getIntent().getStringExtra("type").equals("2")) {//
                ll_edit.setVisibility(View.VISIBLE);
                rl_chezhu_info.setVisibility(View.GONE);
            }
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra("state"))) {
            if (getIntent().getStringExtra("state").equals("1")) {
                tv_01.setText("编辑车源");
                tv_02.setText("手动下架");
            }
            if (getIntent().getStringExtra("state").equals("2")) {
                tv_01.setText("重新上架");
                tv_02.setText("删除车源");
            }
            if (getIntent().getStringExtra("state").equals("3")) {
                tv_01.setText("申请上架");
                tv_02.setText("删除车源");
                tv_manege_state.setText("您已被管理员下架!");
            }


            tv_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getIntent().getStringExtra("state")) {// "state":"1在线 2用户下架 3管理员下架"
                        case "1"://编辑
                            Intent intent = new Intent(CarInfoDetailActivity.this, IsuueCarActivity.class);
                            intent.putExtra("editId", getIntent().getStringExtra("id"));//车源编号
                            intent.putExtra("edit", "edit");//编辑
                            startActivityForResult(intent, 1200);
                            break;
                        case "2"://上架
                            editInfo(getIntent().getStringExtra("id"), "1");// editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                        case "3"://联系客服
                            final AlertDialog dlgtwo = new AlertDialog.Builder(c)
                                    .create();
                            View viewtwo = LayoutInflater.from(CarInfoDetailActivity.this).inflate(
                                    R.layout.pop_check, null);
                            dlgtwo.setView(CarInfoDetailActivity.this.getLayoutInflater().inflate(
                                    R.layout.pop_check, null));
                            dlgtwo.show();
                            dlgtwo.getWindow().setContentView(viewtwo);
                            TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                            TextView title = (TextView) viewtwo.findViewById(R.id.title);
                            TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
                            title.setText("拨打" + "8888");
                            quxiao.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    dlgtwo.dismiss();

                                }
                            });
                            queding.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    dlgtwo.dismiss();

                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8888"));
                                    startActivity(intent);

                                }
                            });

                            break;
                    }
                }
            });
            tv_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getIntent().getStringExtra("state")) {// "state":"1在线 2用户下架 3管理员下架"
                        case "1"://下架
                            editInfo(getIntent().getStringExtra("id"), "2");//editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                        case "2"://删除
                            editInfo(getIntent().getStringExtra("id"), "0");//editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                        case "3"://删除
                            editInfo(getIntent().getStringExtra("id"), "0");//editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                    }

                }
            });
        }
        tv_right.setOnClickListener(this);
        iv_call_phone.setOnClickListener(this);
        iv_vehicleImg.setOnClickListener(this);
        iv_rong_clound.setOnClickListener(this);
        tv_car_info_name.addTextChangedListener(this);
        switch (GOODS_STATE) {
            case "1":
                tv_right.setText("投诉");
                tv_right.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void editInfo(String editId, String editState) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.EDITVEHICLEGOODS);
        params.addBodyParameter("editType", "2");//编辑类型【1货源 2车源】
        params.addBodyParameter("editId", editId);
        params.addBodyParameter("editState", editState);

        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    EventBus.getDefault().post(new MainSendEvent("update"));
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    c.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("id"))) {
            XUtilsApi xUtilsApi = new XUtilsApi();//车源详情
            RequestParams params = new RequestParams(Constant.CAR_DETAIL);
            params.addBodyParameter("supplyCarsId", getIntent().getStringExtra("id"));
            xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                @Override
                public void OnSuccess(NewEntity content, JSONObject result) {
                    if (content.getResult().equals(ConstantValue.RESULT)) {
                        String json = result.optString("info");
                        carDetailBean = JSON.parseObject(json, CarDetailBean.class);
                        ImageLoader.getInstance().displayImage(carDetailBean.getVehicleImg(), iv_vehicleImg);//车辆照片
                        tv_margin.setText("车主已缴纳" + carDetailBean.getMargin() + "失联保证金");//保证金
                        tv_distanceAround.setText(carDetailBean.getDistanceAround() + "km");//接单区域
                        tv_shelvesDate.setText(carDetailBean.getShelvesDate());//上架时间
                        if (!TextUtils.isEmpty(carDetailBean.getEntruckingDate())) {

                            tv_entruckingDate.setText(carDetailBean.getEntruckingDate() + "  " + carDetailBean.getEntruckingTime());//装车时间
                        } else {
                            tv_entruckingDate.setText("不限");//装车时间
                        }
                        tv_placeOfReceipt.setText(carDetailBean.getPlaceOfReceipt());//收货地
                        if (!TextUtils.isEmpty(carDetailBean.getDestination())) {

                            tv_destination.setText(carDetailBean.getDestination() + carDetailBean.getDestinationAddress());//目的地
                        } else {
                            tv_destination.setText("不限");//目的地
                        }
                        if (carDetailBean.getUseCarType().equals("1")) {//空车
                            tv_useCarType.setText("空车");
                            tv_yantu.setVisibility(View.GONE);
                        } else if (carDetailBean.getUseCarType().equals("2")) {//顺风车
                            tv_useCarType.setText("顺风车");
                            tv_yantu.setVisibility(View.VISIBLE);
                            tv_alongRoad.setText(carDetailBean.getAlongRoad());
                        }
                        tv_owner_Vehicle_Cer.setText(carDetailBean.getOwner_Vehicle_Cer());//车源信息
                        tv_note.setText(carDetailBean.getNote());//备注
                        getCarInfo();
                    } else {
                        Intent intent = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", content.getMessage());
                        startActivity(intent);
                    }
                }
            });
        }

    }

    private void getCarInfo() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("history")) && !TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            rl_chezhu_info.setVisibility(View.VISIBLE);
            ll_edit.setVisibility(View.GONE);
            ll_info_view.setVisibility(View.VISIBLE);
            XUtilsApi xUtilsApi = new XUtilsApi();
            RequestParams params = new RequestParams(Constant.CAROWNERS);
            params.addBodyParameter("typeId", "2");
            params.addBodyParameter("accountId", carDetailBean.getAccountId());
            params.addBodyParameter("objectId", getIntent().getStringExtra("objectId"));
            xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

                @Override
                public void OnSuccess(NewEntity content, JSONObject result) {
                    if (content.getResult().equals(ConstantValue.RESULT)) {
                        String json = result.optString("info");
                        String josonObject = result.optString("info");
                        koufei = JSON.parseObject(json, KouFeiGoodsBean.class);
                        ImageLoader.getInstance().displayImage(koufei.getHeadImg(), photo);
                        tv_name.setText(koufei.getUserName());//
                        tv_car_info_name.setText(koufei.getName());
                        tv_addDate.setText("于" + koufei.getAddDate() + "加入平台");
                        tv_goodsCount.setText("累计发布" + koufei.getVehicleCount() + "条车源");

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1200:
                initData();
                break;
        }
    }

    private void popCheck(String message) {

        final AlertDialog dlgtwo = new AlertDialog.Builder(c)
                .create();
        View viewtwo = LayoutInflater.from(c).inflate(
                R.layout.pop_check, null);
        dlgtwo.setView(CarInfoDetailActivity.this.getLayoutInflater().inflate(
                R.layout.pop_check, null));
        dlgtwo.show();
        dlgtwo.getWindow().setContentView(viewtwo);
        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
        TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
        title.setText(message);
        queding.setText("认证");
        quxiao.setText("暂不");
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dlgtwo.dismiss();

            }
        });
        queding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dlgtwo.dismiss();
                switch (CAR_STATE) {
                    case "-1":
                    case "2":
                        Intent t = new Intent(CarInfoDetailActivity.this, IdentityAuditActivity.class);//完善信、信息
                        t.putExtra("checkResult", checkResult);
                        startActivity(t);
                        break;
                    case "1":
                        switch (CAR_NUMBER_STATE) {
                            case "-1":
                            case "2":
                                Intent gongsi = new Intent(CarInfoDetailActivity.this, CarInfoActivity.class);
                                gongsi.putExtra("state", "0");
                                startActivityForResult(gongsi, 100);
                                break;
                        }
                        break;

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckState.checkCarInfo(CarInfoDetailActivity.this, "1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_chezhu_info://查看车主信息
                switch (GOODS_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(CarInfoDetailActivity.this, checkResultMessage);
                        return;
                    case "1"://车主审核通过

                        XUtilsApi xUtilsApi = new XUtilsApi();
                        RequestParams params = new RequestParams(Constant.GOODS_MYINFORMATIONLIST);
                        params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
                        params.addBodyParameter("typeId", "1");//1在线

                        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

                            @Override
                            public void OnSuccess(NewEntity content, JSONObject result) {
                                if (content.getResult().equals(ConstantValue.RESULT)) {
                                    String json = result.optString("info");

                                    if (JSON.parseArray(json, NewestGoodsBean.class).size() > 0) {
                                        newestGoodsBeen = JSON.parseArray(json, NewestGoodsBean.class);
                                        new PopGoods(CarInfoDetailActivity.this, JSON.parseArray(json, NewestGoodsBean.class)).showPopupWindow();
                                    } else {

                                        final AlertDialog dlgtwo = new AlertDialog.Builder(CarInfoDetailActivity.this)
                                                .create();
                                        View viewtwo = LayoutInflater.from(CarInfoDetailActivity.this).inflate(
                                                R.layout.pop_check, null);
                                        dlgtwo.setView(CarInfoDetailActivity.this.getLayoutInflater().inflate(
                                                R.layout.pop_check, null));
                                        dlgtwo.show();
                                        dlgtwo.getWindow().setContentView(viewtwo);
                                        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                                        TextView title = (TextView) viewtwo.findViewById(R.id.title);
                                        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
                                        title.setText("您没有发布货源不可以查看车主!");
                                        quxiao.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View arg0) {
                                                dlgtwo.dismiss();

                                            }
                                        });
                                        queding.setOnClickListener(new View.OnClickListener() {

                                                                       @Override
                                                                       public void onClick(View arg0) {
                                                                           dlgtwo.dismiss();
                                                                           Utils.startActivity(CarInfoDetailActivity.this, IsuueGoodsActivity.class); //发布车源
                                                                       }
                                                                   }

                                        );
                                    }
                                }
                            }
                        });
                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }


                break;

            case R.id.tv_right:

                Intent intent1 = new Intent(c, TouSuActivity.class);
                intent1.putExtra("id", getIntent().getStringExtra("id"));
                intent1.putExtra("objectId", "2");//1货源 2车源
                intent1.putExtra("name", "投诉车主");//1货源 2车源
                startActivity(intent1);
                break;

            case R.id.iv_rong_clound:
                if (RongIM.getInstance() != null) {
                    ConstantValue.RongName = koufei.getUserName();
                    RongIM.getInstance().startPrivateChat(this, TextUtils.isEmpty(koufei.getAccountId()) ? getIntent().getStringExtra("accountId") : koufei.getAccountId(), "title");
                }
                break;

            case R.id.iv_vehicleImg:
//                Intent intent=new Intent(c, PreviewImageActivity.class);
//                intent.putExtra("url",carDetailBean.getVehicleImg());
//                c.startActivity(intent);

                Utils.showPriviewPhoto(CarInfoDetailActivity.this, carDetailBean.getVehicleImg());
                break;

            case R.id.iv_call_phone:
                final AlertDialog dlgtwo = new AlertDialog.Builder(CarInfoDetailActivity.this)
                        .create();
                View viewtwo = LayoutInflater.from(CarInfoDetailActivity.this).inflate(
                        R.layout.pop_check, null);
                dlgtwo.setView(CarInfoDetailActivity.this.getLayoutInflater().inflate(
                        R.layout.pop_check, null));
                dlgtwo.show();
                dlgtwo.getWindow().setContentView(viewtwo);
                TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
                TextView title = (TextView) viewtwo.findViewById(R.id.title);
                TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
                title.setText("拨打" + koufei.getContact());
                quxiao.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dlgtwo.dismiss();

                    }
                });
                queding.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dlgtwo.dismiss();

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + koufei.getContact()));
                        startActivity(intent);

                    }
                });
                break;


        }
    }

    @Subscribe
    public void update(MainSendEvent e) {
        if (e != null && !TextUtils.isEmpty((String) e.getT())) {
            goodsId = (String) e.getT();

            goodsName=e.getStringMsgData();
            originLatitude = newestGoodsBeen.get(e.getPosition()).getOriginLatitude();
            originLongitude = newestGoodsBeen.get(e.getPosition()).getOriginLongitude();
            deductionMoney();
        }
    }

    /**
     * 扣费
     */
    private void deductionMoney() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.OWNERINFO);
        params.addBodyParameter("fromId", SPutils.getString(CarInfoDetailActivity.this, "accountId")); //账户编号【车主】
        params.addBodyParameter("supplyGoodsId", goodsId);//货源Id
        params.addBodyParameter("originLongitude", originLongitude);// 出发地 经度
        params.addBodyParameter("originLatitude", originLatitude);// 出发地 纬度
        params.addBodyParameter("toId", carDetailBean.getAccountId());//账户编号【货主】
        params.addBodyParameter("supplyCarsId", getIntent().getStringExtra("id"));//货源Id
        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    ll_info_view.setVisibility(View.VISIBLE);
                    String josonObject = result.optString("info");
                    koufei = JSON.parseObject(json, KouFeiGoodsBean.class);
                    ImageLoader.getInstance().displayImage(koufei.getHeadImg(), photo);
                    tv_name.setText(koufei.getUserName());//
                    tv_addDate.setText("于" + koufei.getAddDate() + "加入平台");
                    tv_goodsCount.setText("累计发布" + koufei.getVehicleCount() + "条车源");
                    tv_car_info_name.setText(goodsName);
                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!tv_car_info_name.getText().toString().trim().equals("查看车主信息并联系")) {
            rl_chezhu_info.setClickable(false);
            iv_car_next.setVisibility(View.GONE);
        } else {
            rl_chezhu_info.setClickable(true);
            iv_car_next.setBackgroundResource(R.mipmap.next);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
