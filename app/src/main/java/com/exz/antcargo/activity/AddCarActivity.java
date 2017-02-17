package com.exz.antcargo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.AuditCarInfoBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.fragemt.SelectCarXingIsCarLongDialog;
import com.exz.antcargo.activity.okhttp.OKHttpApi;
import com.exz.antcargo.activity.utils.Base64Coder;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.LinkedHashMap;

/**
 * Created by pc on 2016/8/22.
 * 添加车辆
 */
@ContentView(R.layout.activity_add_car)
public class AddCarActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_modelsNam)
    private TextView tv_modelsNam;

    @ViewInject(R.id.ed_car_lenth)
    private EditText ed_car_lenth;


    @ViewInject(R.id.ed_car_width)
    private EditText ed_car_width;


    @ViewInject(R.id.ed_deadweightTonnage)
    private EditText ed_deadweightTonnage;


    @ViewInject(R.id.ll_chexing)
    private LinearLayout ll_chexing;

    @ViewInject(R.id.tv_right)
    private TextView tv_right;


    private Context c = AddCarActivity.this;
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

    private String vehiclesState = "", vehicleLicenseState = "", vehicleWidthState = "", modelsNameState = "", vehicleLengthNameState = "", vehicleWithState = "", deadweightTonnageState = "";
    private String vehiclesImg = "", vehicleLicenseImg = "";
    private String modelsName = "", vehicleLengthName = "", vehicleWith = "", deadweightTonnageName = "";
    @ViewInject(R.id.rl_vehicles)
    private RelativeLayout rl_vehicles;

    @ViewInject(R.id.rl_vehicleLicense)
    private RelativeLayout rl_vehicleLicense;

    @ViewInject(R.id.iv_vehicles)
    private ImageView iv_vehicles;

    @ViewInject(R.id.iv_vehicleLicense)
    private ImageView iv_vehicleLicense;

    @ViewInject(R.id.bt_sumbint)
    private Button bt_sumbint;


    private String state = "";

    private AuditCarInfoBean bean;

    @Override
    public void initView() {
        tv_title.setText("添加车辆");
        tv_right.setText("添加");
        llBack.setOnClickListener(this);
        ll_chexing.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void initData() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        bt_sumbint.setVisibility(View.INVISIBLE);
        rl_vehicles.setOnClickListener(this);
        rl_vehicleLicense.setOnClickListener(this);
        if (getIntent().getSerializableExtra("bean") != null) {
            bean = (AuditCarInfoBean) getIntent().getSerializableExtra("bean");
            tv_modelsNam.setText(bean.getCheckResult().getModelsName().getValue());
            ed_car_lenth.setText(bean.getCheckResult().getVehicleLength().getValue());

            ed_car_width.setText(bean.getCheckResult().getVehicleWidth().getValue());
            ed_deadweightTonnage.setText(bean.getCheckResult().getDeadweightTonnage().getValue());

            modelsNameState = bean.getCheckResult().getModelsName().getCheck();

            vehicleLengthNameState = bean.getCheckResult().getVehicleLicense().getCheck();

            vehicleWith = bean.getCheckResult().getVehicleWidth().getValue();
            vehicleWithState = bean.getCheckResult().getVehicleWidth().getCheck();

            deadweightTonnageState = bean.getCheckResult().getDeadweightTonnage().getCheck();

            vehiclesState = bean.getCheckResult().getVehicles().getCheck();
            vehicleLicenseState = bean.getCheckResult().getVehicleLicense().getCheck();

            modelsName = bean.getCheckResult().getModelsName().getValue();
            vehicleLengthName = bean.getCheckResult().getVehicleLicense().getValue();
            deadweightTonnageName = bean.getCheckResult().getDeadweightTonnage().getValue();

            if (bean.getCheckResult().getModelsName().getCheck().equals("2")) {
                tv_modelsNam.setTextColor(Color.parseColor("#FF290C"));
            }
            if (bean.getCheckResult().getVehicleLicense().getCheck().equals("2")) {

                ed_car_lenth.setTextColor(Color.parseColor("#FF290C"));
            }
            if (bean.getCheckResult().getVehicleWidth().getCheck().equals("2")) {

                ed_car_width.setTextColor(Color.parseColor("#FF290C"));
            }
            if (bean.getCheckResult().getDeadweightTonnage().getCheck().equals("2")) {

                ed_deadweightTonnage.setTextColor(Color.parseColor("#FF290C"));
            }

            if (bean.getCheckResult().getVehicles().getCheck().equals("2")) {
                ImageLoader.getInstance().displayImage(bean.getCheckResult().getVehicles().getValue(), iv_vehicles);
            }
            if (bean.getCheckResult().getVehicleLicense().getCheck().equals("2")) {
                ImageLoader.getInstance().displayImage(bean.getCheckResult().getVehicles().getValue(), iv_vehicleLicense);
            }

        }
        tv_modelsNam.addTextChangedListener(this);
        ed_car_lenth.addTextChangedListener(this);
        ed_car_width.addTextChangedListener(this);
        ed_deadweightTonnage.addTextChangedListener(this);
    }

    public void onClick(View view) {
        SelectCarXingIsCarLongDialog dialog = new SelectCarXingIsCarLongDialog();
        Bundle args = new Bundle();
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_chexing://车型
                args.putString("name", "请选择车型");
                args.putString("state", "1");
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "EditNameDialog");
                break;

            case R.id.rl_vehicles:
                state = "1";
                Utils.isNetworkConnected(AddCarActivity.this);
                new popupwindows(c, rl_vehicles);
                break;


            case R.id.rl_vehicleLicense:
                state = "2";
                Utils.isNetworkConnected(AddCarActivity.this);
                new popupwindows(c, rl_vehicles);
                break;


            case R.id.tv_right:
                String chexing = tv_modelsNam.getText().toString().trim();
                String chezhang = ed_car_lenth.getText().toString().trim();
                String vehicleWidth = ed_car_width.getText().toString().trim();
                String deadweightTonnage = ed_deadweightTonnage.getText().toString().trim();


                String url = "";
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                if (getIntent().getSerializableExtra("bean") == null) {
                    if (TextUtils.isEmpty(chexing) || TextUtils.isEmpty(chezhang) || TextUtils.isEmpty(vehicleWidth) || TextUtils.isEmpty(deadweightTonnage) || TextUtils.isEmpty(vehiclesImg)) {
                        Toast.makeText(this, "亲~信息没有完善哦!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    url = Constant.COMMITVEHICLEINFO;
                    map.put("accountId", SPutils.getString(c, "accountId"));
                    map.put("modelsId", ConstantValue.modelsId);//车型的id
                    map.put("vehicleLength", chezhang);//车长的id
                    map.put("vehicleWidth", vehicleWidth);//车宽
                    map.put("deadweightTonnage", deadweightTonnage);//载重
                    map.put("vehicles", vehiclesImg);//驾驶证
                    map.put("vehicleLicense", vehicleLicenseImg);//行驶证
                } else {
                    url = Constant.EditVehicleInfo;
                    map.put("vehicleId", bean.getVehicleId());
                    if (modelsNameState.equals("3")) {
                        map.put("modelsId", ConstantValue.modelsId);
                    }
                    if (vehicleLengthNameState.equals("3")) {
                        map.put("vehicleLength", chezhang);
                    }
                    if (vehicleWithState.equals("3")) {
                        map.put("vehicleWidth", vehicleWidth);
                    }


                    if (deadweightTonnageState.equals("3")) {
                        map.put("deadweightTonnage", deadweightTonnage);
                    }
                    if (vehiclesState.equals("3")) {
                        map.put("vehicles", vehiclesImg);
                    }
                    if (vehicleLicenseState.equals("3")) {
                        map.put("vehicleLicense", vehicleLicenseImg);
                    }
                }

                OKHttpApi.sendUrl(c, url, map, true, new OKHttpApi.OKURLSuccessListenter() {
                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {

                        setResult(100);
                        Intent intent = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", content.getMessage());
                        startActivity(intent);
                        finish();
                    }
                });

                break;


        }
    }

    @Subscribe
    public void onEventMainThread(MainSendEvent event) {
        if (event != null) {
            tv_modelsNam.setText(ConstantValue.modelsName);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!modelsName.equals(tv_modelsNam.getText().toString()) && modelsNameState.equals("2")) {
            tv_modelsNam.setTextColor(Color.parseColor("#808080"));
            modelsNameState = "3";
        }
        if (!vehicleLengthName.equals(ed_car_lenth.getText().toString()) && vehicleLengthNameState.equals("2")) {
            ed_car_lenth.setTextColor(Color.parseColor("#808080"));
            vehicleLengthNameState = "3";
        }

        if (!vehicleWith.equals(ed_car_width.getText().toString()) && vehicleWithState.equals("2")) {
            ed_car_width.setTextColor(Color.parseColor("#808080"));
            vehicleWithState = "3";
        }


        if (!deadweightTonnageName.equals(ed_deadweightTonnage.getText().toString()) && deadweightTonnageState.equals("2")) {
            ed_deadweightTonnage.setTextColor(Color.parseColor("#808080"));
            deadweightTonnageState = "3";
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

                            case "1": //车辆照片
                                vehiclesImg = Base64Coder.encodeLines(bytes);
                                iv_vehicles.setImageBitmap(photo);
                                vehiclesState = "3";
                                break;
                            case "2"://行驶证照片
                                vehicleLicenseImg = Base64Coder.encodeLines(bytes);
                                iv_vehicleLicense.setImageBitmap(photo);
                                vehiclesState = "3";
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
                        Toast.makeText(c, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
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
        intent.putExtra("outputY", 350);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PIC_EDIT_REQUEST_DATA);
    }
}
