package com.exz.antcargo.activity;


import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.MineCarInfoBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.RongBean;
import com.exz.antcargo.activity.fragemt.HomeFragment;
import com.exz.antcargo.activity.fragemt.LogisticsFragment;
import com.exz.antcargo.activity.fragemt.MinCarFragment;
import com.exz.antcargo.activity.fragemt.MinGoodsFragment;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by pc on 2016/8/19.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, RongIM.UserInfoProvider {

    @ViewInject(R.id.rg)
    private RadioGroup rg;
    @ViewInject(R.id.rb_main_1)
    private RadioButton rb_main_1;
    @ViewInject(R.id.rb_main_2)
    private RadioButton rb_main_2;
    @ViewInject(R.id.rb_main_3)
    private RadioButton rb_main_3;
    private FragmentManager fm;
    private long exitTime;

    private HomeFragment home;

    private Fragment fragment;
    private LogisticsFragment logistics;
    private MinCarFragment minCarFragment;

    private MinGoodsFragment minGoodsFragment;

    private String rongCludType = "";//给融云数据类型

    private MineCarInfoBean bean;

    private List<RongBean> rongList;

    private TextView de_num;

    @Override
    public void initView() {
        fm = getSupportFragmentManager();
        rg.setOnCheckedChangeListener(this);
        rb_main_1.setChecked(true);
        de_num = (TextView) findViewById(R.id.de_num);
    }

    @Override
    public void initData() {
        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.DISCUSSION,
                Conversation.ConversationType.GROUP,
                Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE,
                Conversation.ConversationType.APP_PUBLIC_SERVICE};

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(
                        mCountListener, conversationTypes);
            }
        }, 500);


        bDUpdate();
//        Update();
    }

    /*
    *
    * 360自动更新
     */
//    private void Update() {
//        UpdateHelper.getInstance().init(MainActivity.this, R.color.blueness);
//        UpdateHelper.getInstance().setDebugMode(false);
//        long intervalMillis = 10 * 1000L;           //第一次调用startUpdateSilent出现弹窗后，如果10秒内进行第二次调用不会查询更新
//        UpdateHelper.getInstance().autoUpdate(this.getPackageName(), false, intervalMillis);
//
//
//    }

    /*
    * 百度自动更新
    */
    private void bDUpdate() {
        BDAutoUpdateSDK.uiUpdateAction(MainActivity.this, new UICheckUpdateCallback() {
            @Override
            public void onCheckComplete() {

            }
        });
    }

    public RongIM.OnReceiveUnreadCountChangedListener mCountListener = new RongIM.OnReceiveUnreadCountChangedListener() {
        @Override
        public void onMessageIncreased(int count) {
            if (count == 0) {
                de_num.setVisibility(View.GONE);
            } else if (count > 0 && count < 100) {
                de_num.setVisibility(View.VISIBLE);
                de_num.setText(count + "");
            } else if (count > 100) {
                de_num.setVisibility(View.VISIBLE);
                de_num.setText("...");
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        JPushInterface.onResume(MainActivity.this);
        if (!TextUtils.isEmpty(SPutils.getString(MainActivity.this, "accountId"))) {
            switch (ConstantValue.AccountType) {// 1 是货主 2 车主

                case "1":
                    getInfo(Constant.MINE_GOODS_INFO);
                    rongCludType = "2";
                    break;

                case "2":
                    getInfo(Constant.MINE_CAR_INFO);
                    rongCludType = "1";
                    break;
            }
        }
    }

    private void getInfo(String url) {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("AccountId", SPutils.getString(MainActivity.this, "accountId"));
        x.http().post(params, new Callback.CommonCallback<JSONObject>
                () {
            @Override
            public void onSuccess(JSONObject result) {
                if (result.optString("result").equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    bean = JSON.parseObject(json, MineCarInfoBean.class);
                    connentRongClud(bean.getRongCloudToken());

                }
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

    private void connentRongClud(String tokrn) {

        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        RongIM.connect(tokrn, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
//                Toast.makeText(getApplicationContext(), "onTokenIncorrect", Toast.LENGTH_SHORT).show();

            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {

//                Toast.makeText(getApplicationContext(), "--onSuccess" + userid, Toast.LENGTH_SHORT).show();
                addRongCludInfo();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

//                Toast.makeText(getApplicationContext(), "--onError" + errorCode, Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 给融云数据
     */
    private void addRongCludInfo() {
        RequestParams p = new RequestParams(Constant.CHATFRIENDLIST);
        p.addBodyParameter("accountType", rongCludType);
        XUtilsApi xUtilsApi = new XUtilsApi();
        xUtilsApi.sendUrl(MainActivity.this, "post", p, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String optJSONObject = result.optString("info");

                    rongList = JSON.parseArray(optJSONObject, RongBean.class);

                    for (RongBean i : JSON.parseArray(optJSONObject, RongBean.class)) {
                        RongIM.getInstance()
                                .setCurrentUserInfo(
                                        new UserInfo(
                                                i.getAccountId(),
                                                i.getFriendName(),
                                                Uri.parse(i
                                                        .getFriendPhoto())));


                    }


                }
            }
        });
        RongIM.setUserInfoProvider(this, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(MainActivity.this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_1:
                if (home == null) {

                    home = new HomeFragment(); // 首页
                }
                switchContent(fragment, home);
                fragment = home;
                break;
            case R.id.rb_main_2:
                if (TextUtils.isEmpty(SPutils.getString(MainActivity.this, "accountId"))) {

                    Utils.startActivity(MainActivity.this, LoginActivity.class);
                    rb_main_1.setChecked(true);
                    return;
                }

                if (logistics == null) {

                    logistics = new LogisticsFragment();// 物流
                }
                switchContent(fragment, logistics);
                fragment = logistics;
                break;
            case R.id.rb_main_3:// 我的

                if (TextUtils.isEmpty(SPutils.getString(MainActivity.this, "accountId"))) {

                    Utils.startActivity(MainActivity.this, LoginActivity.class);
                    rb_main_1.setChecked(true);
                    return;

                }


                if (SPutils.getString(MainActivity.this, "accountType").equals("2")) {
                    if (minCarFragment == null) {

                        minCarFragment = new MinCarFragment();
                    }
                    switchContent(fragment, minCarFragment);
                    fragment = minCarFragment;
                    EventBus.getDefault().post(new MainSendEvent("minCarFragment"));
                } else if (SPutils.getString(MainActivity.this, "accountType").equals("1")) {
                    if (minGoodsFragment == null) {

                        minGoodsFragment = new MinGoodsFragment();
                    }
                    switchContent(fragment, minGoodsFragment);
                    fragment = minGoodsFragment;
                    EventBus.getDefault().post(new MainSendEvent("minGoodsFragment"));
                }

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (!LogisticsFragment.expandTabView.onPressBack()) {
//                finish();
//            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finishAffinity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction = fm.beginTransaction();

        if (from != null) {
            transaction.hide(from);
        }
        if (!to.isAdded()) { // 先判断是否被add过
            transaction.add(R.id.fl_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }


    @Override
    public UserInfo getUserInfo(String userId) {
        if (rongList != null) {
            for (RongBean i : rongList) {
                if (i.getAccountId().equals(userId)) {
                    return new UserInfo(i.getAccountId(), i.getFriendName(),
                            Uri.parse(i.getFriendPhoto()));
                }
            }
        }
        return null;
    }
}
