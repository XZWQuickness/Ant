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
import com.exz.antcargo.activity.utils.Base64Coder;
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

/**
 * Created by pc on 2016/8/22.
 * 货主认证
 */
@ContentView(R.layout.activity_shippe_audit)
public class ShippeAuditActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private Context c = ShippeAuditActivity.this;

    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    @ViewInject(R.id.tv_workCity)
    private TextView tv_workCity;


    @ViewInject(R.id.bt_send_check)
    private Button bt_send_check;

    @ViewInject(R.id.iv_IdCardPositive)
    private ImageView iv_IdCardPositive;//正面身份证

    @ViewInject(R.id.iv_IDCardBackImg)
    private ImageView iv_IDCardBackImg;//背面身份证

    @ViewInject(R.id.iv_holdIDCardImg)
    private ImageView iv_holdIDCardImg;//背面身份证

    @ViewInject(R.id.ed_true_name)
    private EditText ed_true_name;

    @ViewInject(R.id.ed_contact_mode)
    private EditText ed_contactInfo;


    @ViewInject(R.id.ed_idCardNumber)
    private EditText ed_idCardNumber;


    @ViewInject(R.id.rl_hold)
    private RelativeLayout rl_hold;

    private String IDCardFrontImg = "";//    身份证正面照片（数据流）
    private String IDCardBackImg = "";//    身份证背面照片（数据流）
    private String holdIDCardImg = "";//    手持身份证照片（数据流）
    private String IDCardFrontImgState = "";//    身份证正面照片 状态
    private String IDCardBackImgState = "";//    身份证背面照片 状态
    private String holdIDCardImgState = "";//    手持身份证照片状态
    private String trueNameState = ""; //    真实姓名状态
    private String contactInfoState = "";//    联系方式状态
    private String IDCardNumState = "";//   身份证号
    private String trueName = ""; //    真实姓名
    private String contactInfo = "";//    联系方式
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

    private String state = "";
    @ViewInject(R.id.ll_workcity)
    private LinearLayout ll_workcity;
    private String idCardNumber = "";

    private String ProvinceId = "";
    private String CityId = "";
    private String AreaId = "";
    private String CityState = "";


    @Override
    public void initView() {
        tv_title.setText("货主资料");
        tv_right.setText("跳过");

        llBack.setOnClickListener(this);
        bt_send_check.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_IdCardPositive.setOnClickListener(this);
        rl_hold.setOnClickListener(this);
        ll_workcity.setOnClickListener(this);
        iv_IDCardBackImg.setOnClickListener(this);

        ed_true_name.addTextChangedListener(this);
        ed_contactInfo.addTextChangedListener(this);
        ed_idCardNumber.addTextChangedListener(this);
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
                JSONObject IDCardFrontImg = json.optJSONObject("idCardPositive");
                JSONObject IDCardBackImg = json.optJSONObject("idCardReverse");
                JSONObject holdIDCardImg = json.optJSONObject("idCard");
                JSONObject province = json.optJSONObject("province");
                JSONObject city = json.optJSONObject("city");
                JSONObject area = json.optJSONObject("area");

                ed_true_name.setText(trueName.optString("value"));
                this.trueName = trueName.optString("value");
                ed_contactInfo.setText(contactInfo.optString("value"));
                this.contactInfo = contactInfo.optString("value");
                ed_idCardNumber.setText(IDCardNum.optString("value"));
                this.idCardNumber = IDCardNum.optString("value");
                tv_workCity.setText(province.optString("value") + city.optString("value") + area.optString("value"));

                trueNameState = trueName.optString("check");
                contactInfoState = contactInfo.optString("check");
                IDCardNumState = IDCardNum.optString("check");
                IDCardFrontImgState = IDCardFrontImg.optString("check");
                IDCardBackImgState = IDCardBackImg.optString("check");
                holdIDCardImgState = holdIDCardImg.optString("check");
                String provinceState = province.optString("check");
                String cityState = city.optString("check");
                String areaState = city.optString("check");
                if (trueNameState.equals("2")) {
                    ed_true_name.setTextColor(Color.parseColor("#FF290C"));
                }

                if (provinceState.equals("2") || cityState.equals("2") || areaState.equals("2")) {
                    CityState = "2";
                    tv_workCity.setTextColor(Color.parseColor("#FF290C"));
                }

                if (contactInfoState.equals("2")) {
                    ed_contactInfo.setTextColor(Color.parseColor("#FF290C"));
                }

                if (IDCardNumState.equals("2")) {
                    ed_idCardNumber.setTextColor(Color.parseColor("#FF290C"));
                }

                if (IDCardFrontImgState.equals("1")) {
                    x.imageLoad(iv_IdCardPositive, IDCardFrontImg.optString("value"),R.mipmap.id_card_front_img);

                }

                if (IDCardBackImgState.equals("1")) {
                    x.imageLoad(iv_IDCardBackImg, IDCardBackImg.optString("value"),R.mipmap.id_card_back_img);
                }
                if (holdIDCardImgState.equals("1")) {
                    x.imageLoad(iv_holdIDCardImg, holdIDCardImg.optString("value"),R.mipmap.paizhao);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void onClick(View view) {
        final ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.bt_send_check://认证提交成功

                trueName = ed_true_name.getText().toString().trim();
                contactInfo = ed_contactInfo.getText().toString().trim();
                idCardNumber = ed_idCardNumber.getText().toString().trim();

                if (TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
                    if (TextUtils.isEmpty(trueName) || TextUtils.isEmpty(contactInfo)
                            || TextUtils.isEmpty(IDCardFrontImg) || TextUtils.isEmpty(IDCardBackImg)) {
                        Intent intent = new Intent(ShippeAuditActivity.this, DiaLogActivity.class)
                                .putExtra("message", "亲~信息要填写完整哦!");
                        startActivity(intent);
                        return;
                    }

                }

                sumbintInfo(trueName, contactInfo, idCardNumber, IDCardFrontImg, IDCardBackImg);


                break;

            case R.id.tv_right://跳过
                Utils.startActivity(ShippeAuditActivity.this, MainActivity.class);
                break;

            case R.id.iv_IdCardPositive://身份证正面
                state = "1";
                Utils.isNetworkConnected(ShippeAuditActivity.this);
                new popupwindows(ShippeAuditActivity.this, iv_IdCardPositive);
                break;

            case R.id.iv_IDCardBackImg://身份证背面
                state = "2";
                Utils.isNetworkConnected(ShippeAuditActivity.this);
                new popupwindows(ShippeAuditActivity.this, iv_IDCardBackImg);
                break;

            case R.id.rl_hold://身份证手持
                state = "3";
                Utils.isNetworkConnected(ShippeAuditActivity.this);
                new popupwindows(ShippeAuditActivity.this, iv_holdIDCardImg);
                break;

            case R.id.ll_workcity:
                if (!TextUtils.isEmpty(SPutils.getString(ShippeAuditActivity.this, "city"))) {
                    SPutils.save(ShippeAuditActivity.this, "city", SPutils.getString(ShippeAuditActivity.this, "city"));
                    data.addAll(JSON.parseArray(SPutils.getString(ShippeAuditActivity.this, "city"), AddressPicker.Province.class));
                    AddressPicker picker = new AddressPicker(ShippeAuditActivity.this, data);
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
                    return;
                }
                RequestParams params = new RequestParams(Constant.WORFK_CITY);
                params.setMethod(HttpMethod.POST);
                x.http().post(params, new Callback.CommonCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        JSONObject j = result.optJSONObject("info");
                        String provinces = j.optString("provinces");
                        SPutils.save(ShippeAuditActivity.this, "city", provinces);
                        data.addAll(JSON.parseArray(provinces, AddressPicker.Province.class));
                        AddressPicker picker = new AddressPicker(ShippeAuditActivity.this, data);
                        picker.setSelectedItem(ConstantValue.ProvinceName, ConstantValue.CityName, ConstantValue.Area);
                        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                            @Override
                            public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
                                tv_workCity.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                                ProvinceId = province.getAreaId();
                                CityId = province.getAreaId();
                                AreaId = province.getAreaId();
                                if (!TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
                                    CityState = "3";
                                }
                            }
                        });
                        picker.show();
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

                break;


        }
    }

    private void sumbintInfo(String trueName, String contactInfo, String idCardNumber, String idCardFrontImg, String idCardBackImg) {

        String url = "";
        XUtilsApi xUtilsApi = new XUtilsApi();
        if (TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
            url = Constant.COMMINT_SHIPPER_INFO;
        } else {
            url = Constant.EDIT_SHIPPER_INFO;

        }
        RequestParams params = new RequestParams(url);
        if (TextUtils.isEmpty(getIntent().getStringExtra("checkResult"))) {
            params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
            params.addBodyParameter("userName", trueName);
            params.addBodyParameter("provinceId", ProvinceId);
            params.addBodyParameter("cityId", CityId);
            params.addBodyParameter("areaId", AreaId);
            params.addBodyParameter("contact", contactInfo);
//            params.addBodyParameter("idCardNumber", idCardNumber);
            params.addBodyParameter("idCardPositive", IDCardFrontImg);
            params.addBodyParameter("idCardReverse", IDCardBackImg);
//            params.addBodyParameter("idCard", holdIDCardImg);
        } else {
            params.addBodyParameter("AccountId", SPutils.getString(c, "accountId"));
            if (trueNameState.equals("3")) {

                params.addBodyParameter("userName", trueName);
            }
            if (contactInfoState.equals("3")) {

                params.addBodyParameter("contact", contactInfo);
            }
//            if (IDCardNumState.equals("3")) {
//                params.addBodyParameter("idCardNumber", idCardNumber);
//            }
            if (CityState.equals("3")) {
                params.addBodyParameter("provinceId", ProvinceId);
                params.addBodyParameter("cityId", CityId);
                params.addBodyParameter("areaId", AreaId);
            }

            if (IDCardFrontImgState.equals("3")) {

                params.addBodyParameter("idCardPositive", IDCardFrontImg);
            }

            if (IDCardBackImgState.equals("3")) {

                params.addBodyParameter("idCardReverse", IDCardBackImg);
            }

//            if (holdIDCardImgState.equals("3")) {
//
//                params.addBodyParameter("idCard", holdIDCardImg);
//            }


        }

        xUtilsApi.sendUrl(ShippeAuditActivity.this, "post", params, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    Intent intent = new Intent(ShippeAuditActivity.this, DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ShippeAuditActivity.this, DiaLogActivity.class)
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
        if (!idCardNumber.equals(ed_idCardNumber.getText().toString()) && IDCardNumState.equals("2")) {
            ed_idCardNumber.setTextColor(Color.parseColor("#808080"));
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
                                IDCardFrontImg = Base64Coder.encodeLines(bytes);
                                iv_IdCardPositive.setImageBitmap(Utils.base64ToBitmap(IDCardFrontImg));
                                IDCardFrontImgState = "3";
                                break;
                            case "2"://身份背面
                                IDCardBackImg = Base64Coder.encodeLines(bytes);
                                iv_IDCardBackImg.setImageBitmap(Utils.base64ToBitmap(IDCardBackImg));
                                IDCardBackImgState = "3";
                                break;
                            case "3"://手持身份
                                holdIDCardImg = Base64Coder.encodeLines(bytes);
                                iv_holdIDCardImg.setImageBitmap(Utils.base64ToBitmap(holdIDCardImg));
                                holdIDCardImgState = "3";
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
                        Toast.makeText(ShippeAuditActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
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
