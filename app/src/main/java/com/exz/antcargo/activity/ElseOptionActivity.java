package com.exz.antcargo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.adapter.PhotoAdapter;
import com.exz.antcargo.activity.bean.GoodsBaoZhaungBean;
import com.exz.antcargo.activity.bean.GoodsDetailBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.SendPhoto;
import com.exz.antcargo.activity.utils.Base64Coder;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by pc on 2016/9/25.
 * 其他选项
 */
@ContentView(R.layout.activiy_else_option)
public class ElseOptionActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_goods_baozhuang)
    private LinearLayout ll_goods_baozhuang;
    @ViewInject(R.id.tv_title)
    private TextView tv_tilte;
    @ViewInject(R.id.ll_back)
    private LinearLayout back;

    private ArrayList<String> goodsList;

    private List<GoodsBaoZhaungBean> list;

    private Context c = ElseOptionActivity.this;


    @ViewInject(R.id.tv_right)
    private TextView tv_right;


    @ViewInject(R.id.ed_dun)
    private EditText ed_dun;

    @ViewInject(R.id.ed_fang)
    private EditText ed_fang;

    @ViewInject(R.id.ed_goods_num)
    private EditText ed_goods_num;


    @ViewInject(R.id.ed_chechang)
    private EditText ed_chechang;

    @ViewInject(R.id.ed_car_width)
    private EditText ed_car_width;

    @ViewInject(R.id.ed_load)
    private EditText ed_load;


    @ViewInject(R.id.ed_note)
    private EditText ed_note;


    @ViewInject(R.id.tv_goods_baozhaung)
    private TextView tv_goods_baozhaung;

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;


    String useCarTypeId = "0";
    String carLenght = "";
    String carWidth = "";
    String deadweightTonnage = "";
    String goodsWeight = "";
    String goodsVolume = "";
    String goodsPackagingId = "";
    String goodsCount = "";
    String load = "";
    String note = "暂无";

    private PhotoAdapter adapter;
    private int position = 0;
    private List<String> photoList = new ArrayList<String>();


    @ViewInject(R.id.gv)
    private GridView gv;

    @ViewInject(R.id.rg)
    private RadioGroup rg;
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


    private String deleteGoodsImg = "";
    private List<String> goodsImgsStr = new ArrayList<String>();

    private GoodsDetailBean bean;

    private String goodsImg = "";

    @ViewInject(R.id.rb_01)
    private RadioButton rb_01;

    @ViewInject(R.id.rb_02)
    private RadioButton rb_02;

    @ViewInject(R.id.rb_03)
    private RadioButton rb_03;


    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


        tv_tilte.setText("其他选项");
        tv_right.setText("确定");
        back.setOnClickListener(this);
        ll_goods_baozhuang.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        adapter = new PhotoAdapter(ElseOptionActivity.this);
        gv.setAdapter(adapter);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {

                    case R.id.rb_01:
                        useCarTypeId = "0";
                        break;


                    case R.id.rb_02:
                        useCarTypeId = "1";
                        break;


                    case R.id.rb_03:
                        useCarTypeId = "3";
                        break;


                }
            }
        });
        ll_back.setOnClickListener(this);
    }

    @Subscribe
    public void isPhoto(SendPhoto event) {
        if (event != null && event.getStringMsgData().equals("add")) {
            Utils.isNetworkConnected(ElseOptionActivity.this);
            new popupwindows(c, gv);
        } else if (event != null && event.getStringMsgData().equals("delete")) {
            String delete = (String) event.getT();
            if (delete.contains("http")) {
                deleteGoodsImg += delete + ",";
            }
            photoList.remove(event.getPosition());
            adapter.updateAdapter();
        }
    }

    @Override
    public void initData() {


        if (null != getIntent().getSerializableExtra("bean")) {
            bean = (GoodsDetailBean) getIntent().getSerializableExtra("bean");
            ed_chechang.setText(bean.getCarLenght());
            ed_car_width.setText(bean.getCarWidth());
            ed_load.setText(bean.getDeadweightTonnage());
            ed_dun.setText(bean.getGoodsWeight());
            ed_fang.setText(bean.getGoodsVolume());
            ed_goods_num.setText(bean.getGoodsVolume());
            tv_goods_baozhaung.setText(bean.getGoodsPackagingName());
            ed_note.setText(bean.getNote());
            switch (bean.getUseCarTypeId()) {
                case "0":
                    rb_01.setChecked(true);
                    break;

                case "1":
                    rb_02.setChecked(true);
                    break;

                case "3":
                    rb_03.setChecked(true);
                    break;

            }

            if (!TextUtils.isEmpty(bean.getGoodsImg())&&bean.getGoodsImg().length() > 0) {
                String photo[] = bean.getGoodsImg().split(",");

                for (String p : photo) {
                    photoList.add(p);
                }
                adapter.addendData(photoList, true);
                adapter.updateAdapter();

            }


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_goods_baozhuang://货物包装

                XUtilsApi xUtilsApi = new XUtilsApi();
                RequestParams params = new RequestParams(Constant.PACKING);
                xUtilsApi.sendUrl(c, "post", params, true, new XUtilsApi.URLSuccessListenter() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void OnSuccess(NewEntity content, JSONObject result) {
                        if (content.getResult().equals(ConstantValue.RESULT)) {
                            String info = result.optString("info");
                            list = JSON.parseArray(info, GoodsBaoZhaungBean.class);
                            goodsList = new ArrayList<String>();
                            for (GoodsBaoZhaungBean i : list) {
                                goodsList.add(i.getName());
                            }
                            OptionPicker optionPicker = new OptionPicker(ElseOptionActivity.this, goodsList);
                            optionPicker.setOffset(2);//显示五条
                            optionPicker.setTextSize(14);
                            optionPicker.setSelectedIndex(position);
                            optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                                @Override
                                public void onOptionPicked(int position, String option) {
                                    tv_goods_baozhaung.setText(option);
                                    goodsPackagingId = list.get(position).getGoodspackId();

                                }
                            });
                            optionPicker.show();
                        } else {
                            Intent intent = new Intent(c, DiaLogActivity.class)
                                    .putExtra("message", content.getMessage());
                            startActivity(intent);
                        }
                    }
                });

                break;

            case R.id.tv_right:
                for (String i : photoList) {
                    if (!i.contains("http")) {
                        goodsImg += i + ",";

                    }
                }
                bean = new GoodsDetailBean();
                if (!TextUtils.isEmpty(ed_chechang.getText().toString().trim())) {
                    carLenght = ed_chechang.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(ed_car_width.getText().toString().trim())) {

                    carWidth = ed_car_width.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(ed_load.getText().toString().trim())) {
                    load = ed_load.getText().toString().trim();
                }


                if (!TextUtils.isEmpty(ed_dun.getText().toString().trim())) {
                    goodsWeight = ed_dun.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(ed_fang.getText().toString().trim())) {
                    goodsVolume = ed_fang.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(ed_goods_num.getText().toString().trim())) {
                    goodsCount = ed_goods_num.getText().toString().trim();
                }
                if (!TextUtils.isEmpty(ed_note.getText().toString().trim())) {
                    note = ed_note.getText().toString().trim();
                }

                bean.setCarLenght(carLenght);
                bean.setCarWidth(carWidth);
                bean.setDeadweightTonnage(load);
                bean.setUseCarTypeId(useCarTypeId);

                bean.setGoodsWeight(goodsWeight);
                bean.setGoodsVolume(goodsVolume);
                bean.setSetGoodsPackagingId(goodsPackagingId);
                bean.setGoodsCount(goodsCount);

                bean.setGoodsImg(goodsImg.length() <= 0 ? "" : goodsImg);
                bean.setDeleteGoodsImg(deleteGoodsImg.length() > 0 ? deleteGoodsImg : "");
                bean.setNote(note);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("goodsDetailBean", bean);

                intent.putExtras(bundle);
                setResult(113, intent);
                finish();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PIC_EDIT_REQUEST_DATA:
                    photo = data.getParcelableExtra("data");
                    if (photo != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                        byte[] bytes = stream.toByteArray();
                        photoList.add(Base64Coder.encodeLines(bytes));
                        adapter.addendData(photoList, true);
                        adapter.updateAdapter();

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
