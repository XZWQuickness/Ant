package com.exz.antcargo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.MinGoodsInfoBean;
import com.exz.antcargo.activity.bean.MineCarInfoBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Base64Coder;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.nostra13.universalimageloader.core.ImageLoader;

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
 * Created by pc on 2016/8/23.
 * 个人信息
 */
@ContentView(R.layout.activity_userinfo)
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout llBack;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;


    @ViewInject(R.id.iv_photo)
    private ImageView iv_photo;


    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    @ViewInject(R.id.tv_id_car_num)
    private TextView tv_id_car_num;

    @ViewInject(R.id.tv_contact)
    private TextView tv_contact;

    @ViewInject(R.id.tv_workCity)
    private TextView tv_workCity;


    @ViewInject(R.id.ll_workcity)
    private LinearLayout ll_workcity;

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
    private Context c = UserInfoActivity.this;

    private MineCarInfoBean carInfoBean;
    private MinGoodsInfoBean goodsInfoBean;
    private String Province = "";
    private String City= "";
    private String Area = "";
    private ArrayList<AddressPicker.Province> data;

    @Override
    public void initView() {
        tv_title.setText("个人信息");
        llBack.setOnClickListener(this);
        if (null != getIntent().getSerializableExtra("bean")) {
            carInfoBean = (MineCarInfoBean) getIntent().getSerializableExtra("bean");
            ImageLoader.getInstance().displayImage(carInfoBean.getHeadImg(), iv_photo);
            tv_name.setText(carInfoBean.getUserName());
            tv_id_car_num.setText(carInfoBean.getIdCardNumber());
            tv_contact.setText(carInfoBean.getContact());
            Province = carInfoBean.getWorkProvince();
            City= carInfoBean.getWorkCity();
            Area = carInfoBean.getWorkArea();
            tv_workCity.setText(Province+City+Area);

        } else if (null != getIntent().getSerializableExtra("beanGoods")) {
            goodsInfoBean = (MinGoodsInfoBean) getIntent().getSerializableExtra("beanGoods");
            ImageLoader.getInstance().displayImage(goodsInfoBean.getHeadImg(), iv_photo);
            tv_name.setText(goodsInfoBean.getUserName());
            tv_id_car_num.setText(goodsInfoBean.getIdCardNumber());
            tv_contact.setText(goodsInfoBean.getContact());
            tv_workCity.setText(goodsInfoBean.getWorkPlace());
            Province = goodsInfoBean.getWorkProvince();
            City= goodsInfoBean.getWorkCity();
            Area = goodsInfoBean.getWorkArea();
            tv_workCity.setText(Province+City+Area);
        }
        ll_workcity.setOnClickListener(this);
    }

    @Override
    public void initData() {
        iv_photo.setOnClickListener(this);
        initPicker();
    }

    private void initPicker() {
        data = new ArrayList<AddressPicker.Province>();
        if (!TextUtils.isEmpty(SPutils.getString(c, "city"))) {

            data.addAll(JSON.parseArray(SPutils.getString(UserInfoActivity.this, "city"), AddressPicker.Province.class));


        } else {
            RequestParams params = new RequestParams(Constant.WORFK_CITY);
            params.setMethod(HttpMethod.POST);
            x.http().post(params, new Callback.CommonCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    JSONObject j = result.optJSONObject("info");
                    String provinces = j.optString("provinces");
                    SPutils.save(c, "city", provinces);

                    data.addAll(JSON.parseArray(SPutils.getString(UserInfoActivity.this, "city"), AddressPicker.Province.class));
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
                finish();
                break;

            case R.id.iv_photo:

                Utils.isNetworkConnected(UserInfoActivity.this);
                new popupwindows(UserInfoActivity.this, iv_photo);
                break;

            case R.id.ll_workcity:

                AddressPicker pickerMudidi = new AddressPicker(UserInfoActivity.this, data);
                pickerMudidi.setSelectedItem(Province, City, Area);
                pickerMudidi.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(final AddressPicker.Province province, final AddressPicker.City city, final AddressPicker.County county) {

                        XUtilsApi xUtilsApi = new XUtilsApi();
                        RequestParams params = new RequestParams(Constant.CHANGEPHOTO);
                        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
                        params.addBodyParameter("ProvinceId", province.getAreaId());
                        params.addBodyParameter("CityId", city.getAreaId());
                        params.addBodyParameter("AreaId", county.getAreaId());

                        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

                            @Override
                            public void OnSuccess(NewEntity content, JSONObject result) {
                                if (content.getResult().equals(ConstantValue.RESULT)) {
                                    Province = province.getAreaName();
                                    City = city.getAreaName();
                                    Area = county.getAreaName();
                                    Intent intetn = new Intent();
                                    setResult(120, intetn);
                                    tv_workCity.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());

                                } else {
                                    Intent intent = new Intent(c, DiaLogActivity.class)
                                            .putExtra("message", content.getMessage());
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                });

                pickerMudidi.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                });
                pickerMudidi.show();


                break;

        }
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
                        photo.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                        byte[] bytes = stream.toByteArray();
                        iv_photo.setImageBitmap(photo);


                        XUtilsApi xUtilsApi = new XUtilsApi();
                        RequestParams params = new RequestParams(Constant.CHANGEPHOTO);
                        params.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
                        params.addBodyParameter("headImg", Base64Coder.encodeLines(bytes));

                        xUtilsApi.sendUrl(c, "post", params, false, new XUtilsApi.URLSuccessListenter() {

                            @Override
                            public void OnSuccess(NewEntity content, JSONObject result) {
                                if (content.getResult().equals(ConstantValue.RESULT)) {
                                    Intent intetn = new Intent();
                                    intetn.putExtra("headImg", result.optJSONObject("info").optString("headImg"));
                                    setResult(120, intetn);

                                } else {
                                    Intent intent = new Intent(c, DiaLogActivity.class)
                                            .putExtra("message", content.getMessage());
                                    startActivity(intent);
                                }
                            }
                        });
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
                        Toast.makeText(UserInfoActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
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

    private void sumbintPhotoImg(String headImg, String ProvinceId, String CityId, String AreaId) {

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
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PIC_EDIT_REQUEST_DATA);
    }
}
