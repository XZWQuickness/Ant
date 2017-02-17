package com.exz.antcargo.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.exz.antcargo.R;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;


public class MainActivity2 extends Activity implements View.OnClickListener {

    private String token = "GyOvQW00g+yUz2XOTQDioSwULIv3zBPvxDoEnkyzCoIkhQlBpo2/3RJiRmTYM/FiYzVhPqGXu3E=";
    private String token2 = "3dq5b5jivbDu8YzTUHrpB6sB94h5CualonTgq+tRNFJt3XKobZmDIfuqO+z18mTtSHLm2O2qf6aAnLvAOtL/gA==";
    private TextView tv_chat_1, tv_chat_2, tv_pop;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }


    private void initView() {
        tv_chat_1 = (TextView) findViewById(R.id.tv_chat_1);
        tv_chat_2 = (TextView) findViewById(R.id.tv_chat_2);
        tv_pop = (TextView) findViewById(R.id.tv_pop);
        tv_chat_1.setOnClickListener(this);
        tv_chat_2.setOnClickListener(this);
        tv_pop.setOnClickListener(this);
        if (RongIM.getInstance() != null) {
            InputProvider.ExtendProvider[] ep = {
                    new CameraInputProvider(RongContext.getInstance()),
                    new ImageInputProvider(RongContext.getInstance())};
            // 我需要让他在什么会话类型中的 bar 展示
            RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, ep);
        }
    }

    private void connentRongClud(String token) {

        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {

                Log.d("LoginActivity", "--onTokenIncorrect");
                Toast.makeText(MainActivity2.this, "onTokenIncorrect", Toast.LENGTH_SHORT).show();

            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {

                Log.d("LoginActivity", "--onSuccess" + userid);
                Toast.makeText(MainActivity2.this, "--onSuccess" + userid, Toast.LENGTH_SHORT).show();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

                Log.d("LoginActivity", "--onError" + errorCode);
                Toast.makeText(MainActivity2.this, "--onError" + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_chat_1:
                connentRongClud(token);
//启动会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(this, "125", "title");
                break;

            case R.id.tv_chat_2:
                connentRongClud(token2);
//启动会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(this, "124", "title");
                break;

            case R.id.tv_pop:
                View menuView = LayoutInflater.from(MainActivity2.this).inflate(R.layout.pop_release, null);

                popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, true);
                popupWindow.setAnimationStyle(R.style.Animpop);
                // 加上下面两行可以用back键关闭popupwindow，否则必须调用dismiss();
                ColorDrawable dw = new ColorDrawable(-000001);
                popupWindow.setBackgroundDrawable(dw);
                popupWindow.update();
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                backgroundAlpha(0.5f);
                popupWindow.showAtLocation(findViewById(R.id.tv_pop), Gravity.CENTER
                        | Gravity.CENTER, 0, 0);
//                menuView.findViewById(R.id.pop).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        popupWindow.dismiss();
//                    }
//                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        try {
                            Thread.sleep(700);
                            backgroundAlpha(1.0f);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

        }
        return super.onKeyDown(keyCode, event);
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
}
