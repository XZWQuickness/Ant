package com.exz.antcargo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import cn.qqtheme.framework.picker.AddressPicker;
import io.rong.imkit.utils.BitmapUtil;

/**
 * Created by pc on 2016/8/22.
 * 车主认证
 */
@ContentView(R.layout.activity_car_of_data)
public class IdentityAuditActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private Context c=IdentityAuditActivity.this;

    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    @ViewInject(R.id.tv_car_info)
    private TextView tv_car_info;


    @ViewInject(R.id.bt_send_check)
    private Button bt_send_check;

    @ViewInject(R.id.rl_add_car_info)
    private RelativeLayout rl_add_car_info;
    /**
     * 相册请求参数
     */
    private static final int PHOTO_PICKED_REQUEST_DATA = 3021;
    private File tempFile = null;

    /**
     * 相机请求参数
     */
    private static final int CAMERA_REQUEST_DATA = 3023;
    /**
     * 图片编辑请求参数
     */
    private static final int PIC_EDIT_REQUEST_DATA = 3025;
    private Bitmap photo;

    private String trueName = ""; //    真实姓名
    private String contactInfo = "";//    联系方式
    private String IDCardNum = "";//    身份证号
    private String IDCardFrontImg = "";//    身份证正面照片（数据流）
    private String IDCardBackImg = "";//    身份证背面照片（数据流）
    private String holdIDCardImg = "";//    手持身份证照片（数据流）
    private String drivingLicenseImg = "";//    驾驶证照片（数据流）
    private String truckImg = "";//     车辆照片
    private String travelLicenseImg = "";//    行驶证照片
    //=================1是审核通过==2未通过=======================
    private String trueNameState = ""; //    真实姓名状态
    private String contactInfoState = "";//    联系方式状态
    private String IDCardNumState = "";//    身份证号状态
    private String drivingLicenseNumState = "";//    驾驶证号状态
    private String IDCardFrontImgState = "";//    身份证正面照片（数据流）状态
    private String IDCardBackImgState = "";//    身份证背面照片（数据流）状态
    private String holdIDCardImgState = "";//    手持身份证照片（数据流）状态
    private String drivingLicenseImgState = "";//    驾驶证照片（数据流）状态
    private String plateNumState = "";//     车牌号状态
    private String truckImgState = "";//     车辆照片状态
    private String travelLicenseImgState = "";//    行驶证照片状态

    @ViewInject(R.id.ed_true_name)
    private EditText ed_true_name;

    @ViewInject(R.id.ed_contact_mode)
    private EditText ed_contactInfo;

    @ViewInject(R.id.ed_IDCar_num)
    private EditText ed_IDCardNum;


    @ViewInject(R.id.iv_IDCardFrontImg)
    private ImageView iv_IDCardFrontImg;

    @ViewInject(R.id.iv_IDCardBackImg)
    private ImageView iv_IDCardBackImg;

    @ViewInject(R.id.iv_holdIDCardImg)
    private ImageView iv_holdIDCardImg;

    @ViewInject(R.id.iv_drivingLicenseImg)
    private ImageView iv_drivingLicenseImg;


    @ViewInject(R.id.iv_truckImg)
    private ImageView iv_truckImg;

    @ViewInject(R.id.iv_travelLicenseImg)
    private ImageView iv_travelLicenseImg;


    @ViewInject(R.id.tv_workCity)
    private TextView tv_workCity;


    @ViewInject(R.id.ll_workcity)
    private LinearLayout ll_workcity;
    private String ProvinceId = "";
    private String CityId = "";
    private String AreaId = "";
    private String CityState = "";
    private String carInfoState = "";
    private String state = "";

    private   AddressPicker picker;

    @Override
    public void initView() {
        tv_title.setText("车主资料");
        tv_right.setText("跳过");
        llBack.setOnClickListener(this);
        bt_send_check.setOnClickListener(this);
        rl_add_car_info.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        ll_workcity.setOnClickListener(this);
        iv_IDCardFrontImg.setOnClickListener(this);
        iv_IDCardBackImg.setOnClickListener(this);
        iv_holdIDCardImg.setOnClickListener(this);
        iv_drivingLicenseImg.setOnClickListener(this);
        iv_truckImg.setOnClickListener(this);
        iv_travelLicenseImg.setOnClickListener(this);
        ConstantValue.modelsName = "";
        ConstantValue.modelsId = "";
        ConstantValue.vehicleLengthName = "";
        ConstantValue.deadweightTonnage = "";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ConstantValue.modelsName = "";
        ConstantValue.modelsId = "";
        ConstantValue.vehicleLengthName = "";
        ConstantValue.vehicleWidth = "";
        ConstantValue.deadweightTonnage = "";
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("checkResult")) && !getIntent().getStringExtra("checkResult").equals("-1")) {
            XUtilsApi x = new XUtilsApi();
            try {
                JSONObject json = new JSONObject(getIntent().getStringExtra("checkResult"));
                JSONObject trueName = json.optJSONObject("userName");
                JSONObject contactInfo = json.optJSONObject("contact");
                JSONObject IDCardNum = json.optJSONObject("idCardNumber");
                JSONObject province = json.optJSONObject("province");
                JSONObject city = json.optJSONObject("city");
                JSONObject area = json.optJSONObject("area");

                JSONObject models = json.optJSONObject("models");
                JSONObject vehicleLength = json.optJSONObject("vehicleLength");
                JSONObject deadweightTonnage = json.optJSONObject("deadweightTonnage");


                JSONObject truckImg = json.optJSONObject("vehicles");
                JSONObject travelLicenseImg = json.optJSONObject("vehicleLicense");
                JSONObject IDCardFrontImg = json.optJSONObject("idCardPositive");
                JSONObject IDCardBackImg = json.optJSONObject("idCardReverse");
                JSONObject holdIDCardImg = json.optJSONObject("idCard");
                JSONObject drivingLicenseImg = json.optJSONObject("vehicleLicense");
                tv_workCity.setText(province.optString("value") + city.optString("value") + area.optString("value"));
                ed_true_name.setText(trueName.optString("value"));
                this.trueName = trueName.optString("value");
                ed_contactInfo.setText(contactInfo.optString("value"));
                this.contactInfo = contactInfo.optString("value");
                ed_IDCardNum.setText(IDCardNum.optString("value"));
                this.IDCardNum = IDCardNum.optString("value");


                tv_car_info.setText(models.optString("value") + vehicleLength.optString("value") + deadweightTonnage.optString("value"));
                trueNameState = trueName.optString("check");
                contactInfoState = contactInfo.optString("check");
                IDCardNumState = IDCardNum.optString("check");
                IDCardFrontImgState = IDCardFrontImg.optString("check");
                IDCardBackImgState = IDCardBackImg.optString("check");
                holdIDCardImgState = holdIDCardImg.optString("check");
                drivingLicenseImgState = drivingLicenseImg.optString("check");
                truckImgState = truckImg.optString("check");
                travelLicenseImgState = travelLicenseImg.optString("check");
                String provinceState = province.optString("check");
                String cityState = city.optString("check");
                String areaState = city.optString("check");
                String modelsState = province.optString("check");
                String vehicleLengthState = city.optString("check");
                String deadweightTonnageState = city.optString("check");

                if (trueNameState.equals("2")) {
                    ed_true_name.setTextColor(Color.parseColor("#FF290C"));
                }

                if (contactInfoState.equals("2")) {
                    ed_contactInfo.setTextColor(Color.parseColor("#FF290C"));
                }
                if (provinceState.equals("2") || cityState.equals("2") || areaState.equals("2")) {
                    CityState = "2";
                    tv_workCity.setTextColor(Color.parseColor("#FF290C"));
                }
                if (modelsState.equals("2") || vehicleLengthState.equals("2") || deadweightTonnageState.equals("2")) {
                    carInfoState = "2";
                    tv_workCity.setTextColor(Color.parseColor("#FF290C"));
                }
                if (IDCardNumState.equals("2")) {
                    ed_IDCardNum.setTextColor(Color.parseColor("#FF290C"));
                }

                if (IDCardFrontImgState.equals("1")) {
                    x.imageLoad(iv_IDCardFrontImg, IDCardFrontImg.optString("value"),R.mipmap.id_card_front_img);

                }

                if (IDCardBackImgState.equals("1")) {
                    x.imageLoad(iv_IDCardBackImg, IDCardBackImg.optString("value"),R.mipmap.id_card_back_img);
                }
                if (holdIDCardImgState.equals("1")) {
                    x.imageLoad(iv_holdIDCardImg, holdIDCardImg.optString("value"),R.mipmap.paizhao);
                }
                if (drivingLicenseImgState.equals("1")) {
                    x.imageLoad(iv_drivingLicenseImg, drivingLicenseImg.optString("value"),R.mipmap.paizhao);
                }
                if (truckImgState.equals("1")) {
                    x.imageLoad(iv_truckImg, truckImg.optString("value"),R.mipmap.paizhao);
                }
                if (travelLicenseImgState.equals("1")) {
                    x.imageLoad(iv_travelLicenseImg, travelLicenseImg.optString("value"),R.mipmap.paizhao);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ed_true_name.addTextChangedListener(this);
        ed_contactInfo.addTextChangedListener(this);
        ed_IDCardNum.addTextChangedListener(this);

        initCity();
    }

    private void initCity() {
        final ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        if (!TextUtils.isEmpty(SPutils.getString(IdentityAuditActivity.this, "city"))) {
            data.addAll(JSON.parseArray(SPutils.getString(IdentityAuditActivity.this, "city"), AddressPicker.Province.class));
            picker = new AddressPicker(IdentityAuditActivity.this, data);
            picker.setSelectedItem(ConstantValue.ProvinceName, ConstantValue.CityName, ConstantValue.Area);
        }else{
            RequestParams params = new RequestParams(Constant.WORFK_CITY);
            params.setMethod(HttpMethod.POST);
            x.http().post(params, new Callback.CommonCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    JSONObject j = result.optJSONObject("info");
                    String provinces = j.optString("provinces");
                    SPutils.save(IdentityAuditActivity.this, "city", provinces);
                    data.addAll(JSON.parseArray(provinces, AddressPicker.Province.class));
                    picker = new AddressPicker(IdentityAuditActivity.this, data);
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

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_back:
                ConstantValue.modelsName = "";
                ConstantValue.modelsId = "";
                ConstantValue.vehicleLengthName = "";
                ConstantValue.vehicleWidth = "";
                ConstantValue.deadweightTonnage = "";
                finish();
                break;

            case R.id.bt_send_check://提交认证


                trueName = ed_true_name.getText().toString().trim();
                contactInfo = ed_contactInfo.getText().toString().trim();
                IDCardNum = ed_IDCardNum.getText().toString().trim();
                String workCity = this.tv_workCity.getText().toString().trim();
                String car_info = this.tv_car_info.getText().toString().trim();
                if (TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
                    if (TextUtils.isEmpty(trueName) || TextUtils.isEmpty(contactInfo) || TextUtils.isEmpty(IDCardNum) || TextUtils.isEmpty(workCity) || TextUtils.isEmpty(car_info)
                            || TextUtils.isEmpty(IDCardFrontImg) || TextUtils.isEmpty(IDCardBackImg) || TextUtils.isEmpty(holdIDCardImg)
                            || TextUtils.isEmpty(truckImg) ) {
                        Intent intent = new Intent(IdentityAuditActivity.this, DiaLogActivity.class)
                                .putExtra("message", "亲~信息要填写完整哦!");
                        startActivity(intent);
                        return;
                    }
                }

                if (trueNameState.equals("2") || contactInfoState.equals("2") || IDCardNumState.equals("2") || drivingLicenseNumState.equals("2")
                        || IDCardFrontImgState.equals("2") || IDCardBackImgState.equals("2") || holdIDCardImgState.equals("2")
                        || plateNumState.equals("2") || truckImgState.equals("2")
                        ) {
                    Intent intent = new Intent(IdentityAuditActivity.this, DiaLogActivity.class)
                            .putExtra("message", "亲~您还没有修改未通过的信息哦!");
                    startActivity(intent);
                    return;
                }

                sumbintInfo(trueName, contactInfo, IDCardNum, IDCardFrontImg, IDCardBackImg, holdIDCardImg, drivingLicenseImg, truckImg, travelLicenseImg);

                break;


            case R.id.rl_add_car_info://添加车辆信息
                Utils.startActivityForResult(IdentityAuditActivity.this, AddCarInfoActivity.class);
                break;

            case R.id.tv_right://跳过
                Utils.startActivity(IdentityAuditActivity.this, MainActivity.class);
                break;

            case R.id.iv_IDCardFrontImg: //    身份证正面照片（数据流）
                state = "1";
                Utils.isNetworkConnected(IdentityAuditActivity.this);
                new popupwindows(IdentityAuditActivity.this, iv_IDCardFrontImg);
                break;

            case R.id.iv_IDCardBackImg: //    身份证背面照片（数据流）
                state = "2";
                Utils.isNetworkConnected(IdentityAuditActivity.this);
                new popupwindows(IdentityAuditActivity.this, iv_IDCardFrontImg);
                break;

            case R.id.iv_holdIDCardImg: //   手持身份证（数据流）
                state = "3";
                Utils.isNetworkConnected(IdentityAuditActivity.this);
                new popupwindows(IdentityAuditActivity.this, iv_IDCardFrontImg);
                break;

            case R.id.iv_drivingLicenseImg: //  驾驶证（数据流）
                state = "4";
                Utils.isNetworkConnected(IdentityAuditActivity.this);
                new popupwindows(IdentityAuditActivity.this, iv_IDCardFrontImg);
                break;


            case R.id.iv_truckImg: //  车辆照片（数据流）
                state = "5";
                Utils.isNetworkConnected(IdentityAuditActivity.this);
                new popupwindows(IdentityAuditActivity.this, iv_IDCardFrontImg);
                break;

            case R.id.iv_travelLicenseImg: //  行驶证照片
                state = "6";
                Utils.isNetworkConnected(IdentityAuditActivity.this);
                new popupwindows(IdentityAuditActivity.this, iv_IDCardFrontImg);
                break;
            case R.id.ll_workcity:
                    picker.setSelectedItem(ConstantValue.ProvinceName, ConstantValue.CityName, ConstantValue.Area);
                    picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                        @Override
                        public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
                            tv_workCity.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                            ProvinceId = province.getAreaId();
                            CityId = city.getAreaId();
                            AreaId = county.getAreaId();
                            if (!TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
                                CityState = "3";
                            }
                        }
                    });
                    picker.show();

                break;
        }
    }

    private void sumbintInfo(String trueName, String contactInfo, String idCardNum, String idCardFrontImg, String idCardBackImg, String holdIDCardImg, String drivingLicenseImg, String truckImg, String travelLicenseImg) {
        String url = "";
        if (TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
            url = Constant.SUBINT_OWNER_INFO;
        } else {
            url = Constant.EDit_CHECK_OWNER_INFO;

        }
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        if (TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
            params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
            params.addBodyParameter("userName", trueName);
            params.addBodyParameter("contact", contactInfo);
            params.addBodyParameter("provinceId", ProvinceId);
            params.addBodyParameter("cityId", CityId);
            params.addBodyParameter("areaId", AreaId);
            params.addBodyParameter("idCardNumber", IDCardNum);
            params.addBodyParameter("modelsId", ConstantValue.modelsId);//车型的Id
            params.addBodyParameter("vehicleLength", ConstantValue.vehicleLengthName);//车长的id
            params.addBodyParameter("vehicleWidth", ConstantValue.vehicleWidth);//车宽
            params.addBodyParameter("deadweightTonnage", ConstantValue.deadweightTonnage);//载重
            params.addBodyParameter("idCardPositive", IDCardFrontImg);
            params.addBodyParameter("idCardReverse", IDCardBackImg);
            params.addBodyParameter("idCard", holdIDCardImg);//手持
            params.addBodyParameter("vehicles", truckImg);//车辆照片
            params.addBodyParameter("drivingLicenseImg", drivingLicenseImg);//驾驶证照片
            params.addBodyParameter("vehicleLicense", travelLicenseImg);//行驶证照片
        } else {
            params.addBodyParameter("accountId",SPutils.getString(c, "accountId"));
            if (trueNameState.equals("3")) {

                params.addBodyParameter("userName", trueName);
            }
            if (contactInfoState.equals("3")) {

                params.addBodyParameter("contact", contactInfo);
            }
            if (CityState.equals("3")) {
                params.addBodyParameter("provinceId", ProvinceId);
                params.addBodyParameter("cityId", CityId);
                params.addBodyParameter("areaId", AreaId);
            }

            if (IDCardNumState.equals("3")) {

                params.addBodyParameter("idCardNumber", IDCardNum);
            }
            if (carInfoState.equals("3")) {
                params.addBodyParameter("modelsId", ConstantValue.modelsId);
                params.addBodyParameter("vehicleLengthName", ConstantValue.vehicleLengthName);
                params.addBodyParameter("vehicleWidth",  ConstantValue.vehicleWidth);
                params.addBodyParameter("deadweightTonnage", ConstantValue.deadweightTonnage);
            }


            if (IDCardFrontImgState.equals("3")) {

                params.addBodyParameter("idCardPositive", IDCardFrontImg);
            }

            if (IDCardBackImgState.equals("3")) {

                params.addBodyParameter("idCardReverse", IDCardBackImg);
            }

            if (holdIDCardImgState.equals("3")) {

                params.addBodyParameter("idCard", holdIDCardImg);
            }
            if (truckImgState.equals("3")) {

                params.addBodyParameter("vehicles", truckImg);
            }
            if (drivingLicenseImgState.equals("3")) {

                params.addBodyParameter("driverLicense", drivingLicenseImg);
            }

            if (travelLicenseImgState.equals("3")) {

                params.addBodyParameter("vehicleLicense", travelLicenseImg);
            }


        }


        xUtilsApi.sendUrl(IdentityAuditActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    Intent intent = new Intent(IdentityAuditActivity.this, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(IdentityAuditActivity.this, DiaLogActivity.class)
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
        if (!trueName.equals(ed_true_name.getText().toString()) && trueNameState.equals("2")) {
            ed_true_name.setTextColor(Color.parseColor("#808080"));
            trueNameState = "3";
        }
        if (!contactInfo.equals(ed_contactInfo.getText().toString()) && contactInfoState.equals("2")) {
            ed_contactInfo.setTextColor(Color.parseColor("#808080"));
            contactInfoState = "3";
        }
        if (!IDCardNum.equals(ed_IDCardNum.getText().toString()) && IDCardNumState.equals("2")) {
            ed_IDCardNum.setTextColor(Color.parseColor("#808080"));
            IDCardNumState = "3";
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    class popupwindows extends PopupWindow {

        public popupwindows(final Context context, View parent) {
            View view = View.inflate(context, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.fade_ins));
            final LinearLayout pop = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            pop.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();
            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt3.setOnTouchListener(new View.OnTouchListener() {

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {

                        case KeyEvent.ACTION_DOWN:
                            dismiss();
                            break;
                    }
                    return false;
                }
            });
            bt2.setOnTouchListener(new View.OnTouchListener() { // 从相册中选取

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {

                        case KeyEvent.ACTION_DOWN:

                            Intent intentx = new Intent(Intent.ACTION_PICK);
                            intentx.setDataAndType(
                                    MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                                    "image/*");
                            startActivityForResult(intentx,
                                    PHOTO_PICKED_REQUEST_DATA);
                            dismiss();
                            break;
                    }
                    return false;
                }
            });
            bt1.setOnTouchListener(new View.OnTouchListener() { // 拍照

                @SuppressWarnings("static-access")
                @SuppressLint({"ClickableViewAccessibility", "ShowToast"})
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {

                        case KeyEvent.ACTION_DOWN:
                            Intent intent = new Intent(
                                    "android.media.action.IMAGE_CAPTURE");
                            // 判断存储卡是否可以用，可用进行存储
                            if (hasSdcard()) {
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                        .fromFile(new File(Environment
                                                .getExternalStorageDirectory(),
                                                "feendback_photo.jpg")));
                            }
                            startActivityForResult(intent, CAMERA_REQUEST_DATA);
                            dismiss();
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PIC_EDIT_REQUEST_DATA:
                    photo = data.getParcelableExtra("data");
                    if (photo != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bytes = stream.toByteArray();

                        switch (state) {

                            case "1": //身份正

                                IDCardFrontImg = BitmapUtil.getBase64FromBitmap(photo);
                                iv_IDCardFrontImg.setImageBitmap( Utils.base64ToBitmap(IDCardFrontImg));
                                IDCardFrontImgState = "3";
                                break;
                            case "2"://身份背面
                                IDCardBackImg = BitmapUtil.getBase64FromBitmap(photo);
                                iv_IDCardBackImg.setImageBitmap( Utils.base64ToBitmap(IDCardBackImg));
                                IDCardBackImgState = "3";
                                break;
                            case "3"://手持身份
                                holdIDCardImg = BitmapUtil.getBase64FromBitmap(photo);
                                iv_holdIDCardImg.setImageBitmap( Utils.base64ToBitmap(holdIDCardImg));
                                holdIDCardImgState = "3";
                                break;
                            case "4"://驾驶证
                                drivingLicenseImg = BitmapUtil.getBase64FromBitmap(photo);
                                iv_drivingLicenseImg.setImageBitmap( Utils.base64ToBitmap(drivingLicenseImg));
                                drivingLicenseImgState = "3";
                                break;
                            case "5"://车辆
                                truckImg = BitmapUtil.getBase64FromBitmap(photo);
                                iv_truckImg.setImageBitmap( Utils.base64ToBitmap(truckImg));
                                truckImgState = "3";
                                break;
                            case "6"://行驶证
                                travelLicenseImg = BitmapUtil.getBase64FromBitmap(photo);
                                iv_travelLicenseImg.setImageBitmap( Utils.base64ToBitmap(travelLicenseImg));
                                travelLicenseImgState = "3";
                                break;
                        }
                    }

                    break;
                /**
                 * 相机拍照
                 */
                case CAMERA_REQUEST_DATA:
                    if (hasSdcard()) {
                        tempFile = new File(
                                Environment.getExternalStorageDirectory(),
                                "feendback_photo.jpg");
                        crop(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(IdentityAuditActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                /**
                 * 相册选择
                 */
                case PHOTO_PICKED_REQUEST_DATA:
                    if (data != null) {
                        Uri uri = data.getData();
                        crop(uri);
                    }
                    break;

            }
        }
        if (requestCode == 100) {
            if(!TextUtils.isEmpty(ConstantValue.modelsName )){
                carInfoState = "3";
                tv_car_info.setText(ConstantValue.modelsName + " "+ConstantValue.vehicleLengthName + " ×米 " + ConstantValue.vehicleWidth + " ×米 " + ConstantValue.deadweightTonnage+"吨");
            }

        }
    }

    private void crop(Uri uri) {
        // TODO Auto-generated method stub
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PIC_EDIT_REQUEST_DATA);
    }
}
