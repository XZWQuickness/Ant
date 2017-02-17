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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.CompanyCheckBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Base64Coder;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by pc on 2016/8/24.
 * 公司认证
 */
@ContentView(R.layout.activity_gongsi_renzheng)
public class GongSiRenZhengActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @ViewInject(R.id.ed_companyName)
    private EditText ed_companyName;

    @ViewInject(R.id.ed_companyAddress)
    private EditText ed_companyAddress;

    @ViewInject(R.id.ed_position)
    private EditText ed_position;

    @ViewInject(R.id.iv_doorFirst)
    private ImageView iv_doorFirst;

    @ViewInject(R.id.iv_businessLicense)
    private ImageView iv_businessLicense;


    @ViewInject(R.id.iv_businessCard)
    private ImageView iv_businessCard;

    @ViewInject(R.id.bt_send_check)
    private Button bt_send_check;


    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    private String companyName = "";
    private String companyAddress = "";
    private String position = "";
    private String doorFirstImg = "";
    private String businessLicenseImg = "";
    private String businessCardImg = "";

    private String companyNameState = "";
    private String companyAddressState = "";
    private String positionState = "";
    private String doorFirstImgState = "";
    private String businessLicenseImgState = "";
    private String businessCardImgState = "";


    private Context c = GongSiRenZhengActivity.this;
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
    private String doorFirstState = "";
    private String businessLicenseState = "";
    private String businessCardState = "";


    @Override
    public void initView() {
        tvTitle.setText("公司认证");
        ll_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        bt_send_check.setOnClickListener(this);
        iv_doorFirst.setOnClickListener(this);
        iv_businessLicense.setOnClickListener(this);
        iv_businessCard.setOnClickListener(this);

        //checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
        String state = getIntent().getStringExtra("state");
        switch (state) {
            case "2":
            case "0":
                checkInfo();
                break;
        }
        ed_companyName.addTextChangedListener(this);
        ed_companyAddress.addTextChangedListener(this);
        ed_position.addTextChangedListener(this);
    }

    private void checkInfo() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams p = new RequestParams(Constant.CHECK_COMPANY_RESULT);
        p.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
        xUtilsApi.sendUrl(c, "post", p, true, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                Intent intent = new Intent(c, DiaLogActivity.class);
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    String checkResult = optJSONObject.optString("checkResult");
                    CompanyCheckBean bean = JSON.parseObject(checkResult, CompanyCheckBean.class);
                    //公司名字
                    ed_companyName.setText(bean.getCompanyName().getValue());
                    companyName = bean.getCompanyName().getValue();
                    if (bean.getCompanyName().getCheck().equals("2")) {
                        companyNameState = "2";
                        ed_companyName.setTextColor(Color.parseColor("#FF290C"));
                    }

                    //公司地址
                    ed_companyAddress.setText(bean.getCompanyAddress().getValue());
                    companyAddress = bean.getCompanyAddress().getValue();
                    if (bean.getCompanyAddress().getCheck().equals("2")) {
                        companyAddressState = "2";
                        ed_companyAddress.setTextColor(Color.parseColor("#FF290C"));
                    }


                    //公司地址
                    ed_position.setText(bean.getPosition().getValue());
                    position = bean.getPosition().getValue();
                    if (bean.getPosition().getCheck().equals("2")) {
                        positionState = "2";
                        ed_position.setTextColor(Color.parseColor("#FF290C"));
                    }

                    //公司门图片
                    doorFirstState=bean.getDoorFirst().getCheck();
                    if (bean.getDoorFirst().getCheck().equals("1")) {
                        ImageLoader.getInstance().displayImage(bean.getDoorFirst().getValue(), iv_doorFirst);
                    }

                    //公司营业执照
                    businessLicenseState=bean.getBusinessLicense().getCheck();
                    if (bean.getBusinessLicense().getCheck().equals("1")) {
                        ImageLoader.getInstance().displayImage(bean.getBusinessLicense().getValue(), iv_businessLicense);
                    }

                    //个人名片
                    businessCardState=bean.getBusinessCard().getCheck();
                    if (bean.getBusinessCard().getCheck().equals("1")) {
                        if(!TextUtils.isEmpty(bean.getBusinessCard().getValue())){

                            ImageLoader.getInstance().displayImage(bean.getBusinessCard().getValue(), iv_businessCard);
                        }
                    }


                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_doorFirst://公司们头像
                state = "1";
                Utils.isNetworkConnected(GongSiRenZhengActivity.this);
                new popupwindows(c, iv_doorFirst);
                break;
            case R.id.iv_businessLicense://个人名片
                state = "2";
                Utils.isNetworkConnected(GongSiRenZhengActivity.this);
                new popupwindows(c, iv_businessLicense);
                break;

            case R.id.iv_businessCard://营业执照
                state = "3";
                Utils.isNetworkConnected(GongSiRenZhengActivity.this);
                new popupwindows(c, iv_businessCard);
                break;

            case R.id.bt_send_check:
                String gongsiName = ed_companyName.getText().toString().trim();
                String gongsiAddress = ed_companyAddress.getText().toString().trim();
                String zhiwei = ed_position.getText().toString().trim();


                XUtilsApi xUtilsApi = new XUtilsApi();
                String url = "";
                if (getIntent().getStringExtra("state").equals("-1")) {
                    url = Constant.COMMIT_COMPANY_INFO;
                    if (TextUtils.isEmpty(gongsiName) || TextUtils.isEmpty(gongsiAddress) || TextUtils.isEmpty(zhiwei) || TextUtils.isEmpty(doorFirstImg) || TextUtils.isEmpty(businessLicenseImg) || TextUtils.isEmpty(businessLicenseImg)) {
                        Intent intent = new Intent(c, DiaLogActivity.class)
                                .putExtra("message", "亲~信息要填写完整哦!");
                        startActivity(intent);
                        return;
                    }
                } else {
                    url = Constant.EDIT_COMPANY_INFO;
                }
                RequestParams p = new RequestParams(url);
                p.addBodyParameter("accountId", SPutils.getString(c, "accountId"));
                if (getIntent().getStringExtra("state").equals("-1")) {

                    p.addBodyParameter("companyName", gongsiName);//公司名字
                    p.addBodyParameter("companyAddress", gongsiAddress);//公司地址
                    p.addBodyParameter("position", zhiwei);//职位
                    p.addBodyParameter("doorFirst", doorFirstImg);//公司门照片
                    p.addBodyParameter("businessLicense", businessLicenseImg);//营业执照
                    p.addBodyParameter("businessCard", businessCardImg);//个人名片
                } else {

                    if (companyNameState.equals("3")) {

                        p.addBodyParameter("companyName", gongsiName);//公司名字
                    }
                    if (companyAddressState.equals("3")) {

                        p.addBodyParameter("companyAddress", gongsiAddress);//公司地址
                    }
                    if (positionState.equals("3")) {

                        p.addBodyParameter("position", zhiwei);//职位
                    }
                    if (doorFirstState.equals("3")) {

                        p.addBodyParameter("doorFirst", doorFirstImg);//公司门照片
                    }
                    if (businessLicenseState.equals("3")) {

                        p.addBodyParameter("businessLicense", businessLicenseImg);//营业执照
                    }
                    if (businessCardImg.equals("3")) {

                        p.addBodyParameter("businessCard", businessCardImg);//个人名片
                    }
                }


                xUtilsApi.sendUrl(c, "post", p, true, new XUtilsApi.URLSuccessListenter() {

                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        Intent intent = new Intent(c, DiaLogActivity.class);
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            intent.putExtra("message", content.getMessage());
                            startActivity(intent);
                            finish();
                        } else {
                            intent.putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });


                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!companyName.equals(ed_companyName.getText().toString()) && companyNameState.equals("2")) {
            ed_companyName.setTextColor(Color.parseColor("#808080"));
            companyNameState = "3";
        }
        if (!companyAddress.equals(ed_companyAddress.getText().toString()) && companyAddressState.equals("2")) {
            ed_companyAddress.setTextColor(Color.parseColor("#808080"));
            companyAddressState = "3";
        }
        if (!position.equals(ed_position.getText().toString()) && positionState.equals("2")) {
            ed_position.setTextColor(Color.parseColor("#808080"));
            positionState = "3";
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

                            case "1"://公司门头
                                doorFirstImg = Base64Coder.encodeLines(bytes);
                                iv_doorFirst.setImageBitmap(photo);
                                doorFirstState = "3";
                                break;
                            case "2"://营业执照
                                businessLicenseImg = Base64Coder.encodeLines(bytes);
                                iv_businessLicense.setImageBitmap(photo);
                                businessLicenseState = "3";
                                break;
                            case "3"://个人照片
                                businessCardImg = Base64Coder.encodeLines(bytes);
                                iv_businessCard.setImageBitmap(photo);
                                businessCardState = "3";
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
