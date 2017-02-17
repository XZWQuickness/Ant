package com.exz.antcargo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.utils.ConstantValue;

import io.rong.imlib.model.Conversation;

/**
 * Created by pc on 2016/8/18.
 * 您的会话界面
 */

public class ConversationActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    private LinearLayout ll_back;
    private TextView tv_title;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
//        if (RongIM.getInstance() != null) {
//            InputProvider.ExtendProvider[] ep = {
//                    new CameraInputProvider(RongContext.getInstance()),
//                    new ImageInputProvider(RongContext.getInstance())};
//            // 我需要让他在什么会话类型中的 bar 展示
//            RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, ep);
//        }
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(ConstantValue.RongName);
        String queryParameter = getIntent().getData()
                .getQueryParameter("title");
        if (TextUtils.isEmpty(ConstantValue.RongName)) {

            tv_title.setText(queryParameter);
        } else {
            tv_title.setText(ConstantValue.RongName);

        }
        ll_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.tv_title:
                tv_title.setText(ConstantValue.RongName);
                break;


        }
    }
}
