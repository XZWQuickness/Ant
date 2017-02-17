package com.exz.antcargo.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.priviewimage.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by pc on 2016/9/19.
 * 预览图片
 */
@ContentView(R.layout.activity_preview_image)
public class PreviewImageActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;

    @ViewInject(R.id.preview_only)
    private PhotoView photoview;

    @Override
    public void initView() {
        ll_back.setOnClickListener(this);
        photoview.enable();// 设置photoview可以放大
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("url"), photoview);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_01:
                break;
        }
    }
}
