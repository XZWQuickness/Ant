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
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.PhotoImgAdapter;
import com.exz.antcargo.activity.adapter.PopCarAdapter;
import com.exz.antcargo.activity.bean.CarManageBean;
import com.exz.antcargo.activity.bean.GoodsDetailBean;
import com.exz.antcargo.activity.bean.KouFeiGoodsBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.popupwindow.PopCar;
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

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

import static com.exz.antcargo.activity.utils.ConstantValue.CAR_NUMBER_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.CAR_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResult;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResultMessage;

/**
 * Created by pc on 2016/8/24.
 * 货源详情
 */
@ContentView(R.layout.activity_goods_info_detail)
public class GoosdInfoDetailActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.ll_info_view)
    private LinearLayout ll_info_view;


    @ViewInject(R.id.ll_lin)
    private LinearLayout ll_lin;


    @ViewInject(R.id.tv_title)
    private TextView tv_title;


    @ViewInject(R.id.tv_manege_state)
    private TextView tv_manege_state;


    @ViewInject(R.id.tv_goods_name)
    private TextView tv_goods_name;


    @ViewInject(R.id.tv_shelvesDate)
    private TextView tv_shelvesDate;

    @ViewInject(R.id.tv_entruckingDate)
    private TextView tv_entruckingDate;

    @ViewInject(R.id.tv_origin)
    private TextView tv_origin;

    @ViewInject(R.id.tv_destination)
    private TextView tv_destination;

    @ViewInject(R.id.tv_goodsType)
    private TextView tv_goodsType;

    @ViewInject(R.id.tv_carManagement)
    private TextView tv_carManagement;

    @ViewInject(R.id.tv_hopePrice)
    private TextView tv_hopePrice;


    @ViewInject(R.id.tv_referencePrice)
    private TextView tv_referencePrice;


    @ViewInject(R.id.tv_note)
    private TextView tv_note;

    @ViewInject(R.id.iv_goods_info_next)
    private ImageView iv_goods_info_next;


    @ViewInject(R.id.tv_forget_password)
    private TextView tv_forget_password;


    @ViewInject(R.id.rl_chezhu_info)
    private RelativeLayout rl_chezhu_info;


    @ViewInject(R.id.ll_edit)
    private LinearLayout ll_edit;

    @ViewInject(R.id.tv_01)
    private TextView tv_01;

    @ViewInject(R.id.tv_02)
    private TextView tv_02;


    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    private String supplyCarId;

    @ViewInject(R.id.photo)
    private ImageView photo;

    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    @ViewInject(R.id.tv_addDate)
    private TextView tv_addDate;


    @ViewInject(R.id.tv_goodsCount)
    private TextView tv_goodsCount;

    private KouFeiGoodsBean koufei;

    @ViewInject(R.id.iv_rong_clound)
    private ImageView iv_rong_clound;


    @ViewInject(R.id.iv_call_phone)
    private ImageView iv_call_phone;


    private GoodsDetailBean bean;

    private PhotoImgAdapter<String> adapter;


    private List<String> listPhoto = new ArrayList<String>();

    private Context c = GoosdInfoDetailActivity.this;


    private PopCarAdapter popGoodsAdapter;


    @ViewInject(R.id.gv)
    private GridView gv;

    @Override
    public void initView() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        tv_title.setText("货源详情");


        switch (CAR_STATE) {
            case "1":
                tv_right.setText("投诉");
                break;
        }
        llBack.setOnClickListener(this);
        rl_chezhu_info.setOnClickListener(this);
        adapter = new PhotoImgAdapter<String>(GoosdInfoDetailActivity.this);
        gv.setAdapter(adapter);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            switch (getIntent().getStringExtra("type")) {
                case "1"://查看货主 2 编辑

                    ll_edit.setVisibility(View.GONE);
                    rl_chezhu_info.setVisibility(View.VISIBLE);
                    break;

                case "2":
                    rl_chezhu_info.setVisibility(View.GONE);
                    ll_edit.setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra("state"))) {
            if (getIntent().getStringExtra("state").equals("1")) {
                tv_01.setText("编辑货源");
                tv_02.setText("手动下架");
            }
            if (getIntent().getStringExtra("state").equals("2")) {
                tv_01.setText("重新上架");
                tv_02.setText("删除货源");
            }
            if (getIntent().getStringExtra("state").equals("3")) {
                tv_01.setText("申请上架");
                tv_02.setText("删除货源");
                tv_manege_state.setText("已被管理员下架!");
            }

            tv_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getIntent().getStringExtra("state")) {// "state":"1在线 2用户下架 3管理员下架"
                        case "1"://编辑
                            Intent intent = new Intent(GoosdInfoDetailActivity.this, IsuueGoodsActivity.class);
                            intent.putExtra("editId", getIntent().getStringExtra("supplyGoodsId"));//车源编号
                            intent.putExtra("edit", "edit");//编辑
                            startActivityForResult(intent, 1200);
                            break;
                        case "2"://上架
                            editInfo(getIntent().getStringExtra("supplyGoodsId"), "1");// editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                        case "3":
                            final AlertDialog dlgtwo = new AlertDialog.Builder(c)
                                    .create();
                            View viewtwo = LayoutInflater.from(c).inflate(
                                    R.layout.pop_check, null);
                            dlgtwo.setView(GoosdInfoDetailActivity.this.getLayoutInflater().inflate(
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
                            editInfo(getIntent().getStringExtra("supplyGoodsId"), "2");//editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                        case "2"://删除
                            editInfo(getIntent().getStringExtra("supplyGoodsId"), "0");//editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                        case "3"://删除
                            editInfo(getIntent().getStringExtra("supplyGoodsId"), "0");//editId =【1货源 2车源】 状态：0删除 1上架  2:用户下架
                            break;
                    }

                }
            });
        }

        tv_right.setOnClickListener(this);
        iv_call_phone.setOnClickListener(this);
        iv_rong_clound.setOnClickListener(this);
        tv_goods_name.addTextChangedListener(this);
    }

    private void editInfo(String editId, String editState) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.EDITVEHICLEGOODS);
        params.addBodyParameter("editType", "1");//编辑类型【1货源 2车源】
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

    @Subscribe
    public void update(MainSendEvent e) {
        if (e != null && !TextUtils.isEmpty((String) e.getT())) {
            supplyCarId = (String) e.getT();
            tv_goods_name.setText(e.getStringMsgData());
            deductionMoney();
        }
    }

    /**
     * 扣费
     */
    private void deductionMoney() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.SHIPPERINFO);
        params.addBodyParameter("fromId", SPutils.getString(GoosdInfoDetailActivity.this, "accountId")); //账户编号【车主】
        params.addBodyParameter("supplyCarId", supplyCarId);//车源Id
        params.addBodyParameter("toId", bean.getAccountId());//账户编号【货主】
        params.addBodyParameter("supplyGoodsId", getIntent().getStringExtra("supplyGoodsId"));//货源Id

        xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    String josonObject = result.optString("info");
                    ll_info_view.setVisibility(View.VISIBLE);
                    koufei = JSON.parseObject(json, KouFeiGoodsBean.class);
                    ImageLoader.getInstance().displayImage(koufei.getHeadImg(), photo);
                    tv_name.setText(!TextUtils.isEmpty(koufei.getUserName())?koufei.getUserName().substring(0,1)+"**":"");//
                    tv_addDate.setText("于" + koufei.getAddDate() + "加入平台");
                    tv_goodsCount.setText("累计发布" + koufei.getGoodsCount() + "条货源");


                } else {
                    Intent intent = new Intent(c, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });

    }


    private void GoodsDetail() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("supplyGoodsId"))) {

            XUtilsApi xUtilsApi = new XUtilsApi();
            RequestParams params = new RequestParams(Constant.GOODS_DETAIL);
            params.addBodyParameter("supplyGoodsId", getIntent().getStringExtra("supplyGoodsId"));
            xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                @Override
                public void OnSuccess(NewEntity content, JSONObject result) {
                    if (content.getResult().equals(ConstantValue.RESULT)) {
                        String json = result.optString("info");
                        JSONObject josonObject = result.optJSONObject("info");

                        bean = JSON.parseObject(json, GoodsDetailBean.class);
                        tv_shelvesDate.setText(bean.getShelvesDate());//上架时间
                        tv_entruckingDate.setText(bean.getEntruckingDate());//装车时间
                        if (!TextUtils.isEmpty(bean.getEntruckingDate())) {
                            tv_entruckingDate.setText(bean.getEntruckingDate() + "  " + bean.getEntruckingTime());//装车时间
                        }
                        tv_origin.setText(bean.getOrigin());//出发地
                        tv_destination.setText(bean.getDestination());//目的地
                        tv_goodsType.setText(bean.getGoodsName());//货物信息
                        tv_carManagement.setText(bean.getCarManagement());//用车要求
                        tv_hopePrice.setText(bean.getHopePrice() + "元");//期望价格
                        tv_referencePrice.setText(bean.getReferencePrice() + "元");//参考运费
                        tv_note.setText(bean.getNote());//备注
                        org.json.JSONArray array = josonObject.optJSONArray("goodsImg");
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                                String j = array.optString(i);
                                listPhoto.add(j);
                            }
                        }

                        adapter.addendData(listPhoto, true);
                        adapter.updateAdapter();
                        getGoodsfo();
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
    public void initData() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        GoodsDetail();
        CheckState.checkCarInfo(GoosdInfoDetailActivity.this, "2");
    }

    private void getGoodsfo() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("history")) && !TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            ll_edit.setVisibility(View.GONE);
            ll_info_view.setVisibility(View.VISIBLE);
            XUtilsApi xUtilsApi = new XUtilsApi();
            RequestParams params = new RequestParams(Constant.CAROWNERS);
            params.addBodyParameter("typeId", "1");
            params.addBodyParameter("accountId", bean.getAccountId());
            params.addBodyParameter("objectId", getIntent().getStringExtra("objectId"));
            xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                @Override
                public void OnSuccess(NewEntity content, JSONObject result) {
                    if (content.getResult().equals(ConstantValue.RESULT)) {
                        String json = result.optString("info");
                        String josonObject = result.optString("info");
                        ll_info_view.setVisibility(View.VISIBLE);
                        koufei = JSON.parseObject(json, KouFeiGoodsBean.class);
                        ImageLoader.getInstance().displayImage(koufei.getHeadImg(), photo);
                        tv_name.setText(koufei.getUserName());//
                        tv_goods_name.setText(koufei.getName());
                        tv_addDate.setText("于" + koufei.getAddDate() + "加入平台");
                        tv_goodsCount.setText("累计发布" + koufei.getGoodsCount() + "条货源");


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
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;


            case R.id.rl_chezhu_info://查看货主信息
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(GoosdInfoDetailActivity.this, checkResultMessage);
                        return;
                    case "1"://车主审核通过
                        if (!CAR_NUMBER_STATE.equals("-1") && !CAR_NUMBER_STATE.equals("")) {

                            getAddedCarInfo();//得到上架车源

                        } else {
                            popShow("您没有可用的车源不可以查看货主信息!", CarInfoActivity.class);
                        }
                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }


                break;
            case R.id.tv_right:
                Intent intent1 = new Intent(c, TouSuActivity.class);
                intent1.putExtra("id", getIntent().getStringExtra("supplyGoodsId"));
                intent1.putExtra("name", "投诉货主");
                intent1.putExtra("objectId", "1");//1货源 2车源
                startActivity(intent1);
                break;
            case R.id.iv_rong_clound://融云
                if (RongIM.getInstance() != null) {
                    ConstantValue.RongName = koufei.getUserName();
                    RongIM.getInstance().startPrivateChat(this, TextUtils.isEmpty(koufei.getAccountId())?getIntent().getStringExtra("accountId"):koufei.getAccountId(), "title");
                }
                break;

            case R.id.iv_call_phone://电话
                final AlertDialog dlgtwo = new AlertDialog.Builder(GoosdInfoDetailActivity.this)
                        .create();
                View viewtwo = LayoutInflater.from(GoosdInfoDetailActivity.this).inflate(
                        R.layout.pop_check, null);
                dlgtwo.setView(GoosdInfoDetailActivity.this.getLayoutInflater().inflate(
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

    private <T> void popShow(String msg, final Class<T> ativity) {

        final AlertDialog dlgtwo = new AlertDialog.Builder(GoosdInfoDetailActivity.this)
                .create();
        View viewtwo = LayoutInflater.from(GoosdInfoDetailActivity.this).inflate(
                R.layout.pop_check, null);
        dlgtwo.setView(GoosdInfoDetailActivity.this.getLayoutInflater().inflate(
                R.layout.pop_check, null));
        dlgtwo.show();
        dlgtwo.getWindow().setContentView(viewtwo);
        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
        TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
        title.setText(msg);
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
                                           Utils.startActivity(GoosdInfoDetailActivity.this, ativity); //发布车源
                                       }
                                   }

        );
    }

    private void popCheck(String message) {

        final AlertDialog dlgtwo = new AlertDialog.Builder(c)
                .create();
        View viewtwo = LayoutInflater.from(c).inflate(
                R.layout.pop_check, null);
        dlgtwo.setView(GoosdInfoDetailActivity.this.getLayoutInflater().inflate(
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
                        Intent t = new Intent(GoosdInfoDetailActivity.this, ShippeAuditActivity.class);//完善信、信息
                        t.putExtra("checkResult", checkResult);
                        startActivity(t);
                        break;
                    case "1":
                        switch (CAR_NUMBER_STATE) {
                            case "-1":
                            case "2":
                                Intent gongsi = new Intent(GoosdInfoDetailActivity.this, GongSiRenZhengActivity.class);
                                gongsi.putExtra("state", "0");
                                startActivityForResult(gongsi, 100);
                                break;
                        }
                        break;

                }
            }
        });
    }


    private void getAddedCarInfo() {

        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.MYINFORMATIONLIST);
        params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
        params.addBodyParameter("typeId", "1");//1在线

        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    if (JSON.parseArray(json, CarManageBean.class).size() > 0) {
                        new PopCar(GoosdInfoDetailActivity.this, JSON.parseArray(json, CarManageBean.class)).showPopupWindow();
                    } else {
                        popShow("您没有发布车源不可以查看货主", IsuueCarActivity.class);
                    }

                }
            }
        });


    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!tv_goods_name.getText().toString().trim().equals("查看货主信息并联系")) {
            rl_chezhu_info.setClickable(false);
            iv_goods_info_next.setVisibility(View.GONE);

        } else {
            rl_chezhu_info.setClickable(true);
            iv_goods_info_next.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
