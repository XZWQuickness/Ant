package com.exz.antcargo.activity.app;


import android.app.Application;

import com.amap.api.location.LocationManagerProxy;
import com.amap.api.maps.model.LatLng;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.bean.User;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.LocationUtils;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import okhttp3.OkHttpClient;



/**
 * Created by pc on 2016/8/18.
 */

public class MyApplication extends Application {

    private static User user;
    private static LatLng latLng;

    public static LatLng getLatLng() {
        return latLng;
    }

    public static void setLatLng(LatLng latLng) {
        MyApplication.latLng = latLng;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        x.Ext.init(this);
        x.Ext.setDebug(false);
        JPushInterface.init(this);
        // 初始化ImageLoader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.photo)
                .showImageOnFail(R.mipmap.photo).cacheInMemory(true)
                .cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(20 * 1024 * 1024)//
                .discCacheFileCount(100).build();
        ImageLoader.getInstance().init(config);

        ConstantValue.AccountType = SPutils.getString(getApplicationContext(), "accountType");
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        LocationUtils locationUtils = new LocationUtils();
        locationUtils.setLocationListener(new LocationUtils.LocationCallBack() {
            @Override
            public void Location() {
            }

            @Override
            public void LocationFail() {
            }
        });
        locationUtils.checkLocation(getApplicationContext(),
                LocationManagerProxy.getInstance(getApplicationContext()));
        SealAppContext.init(this);

//        if (RongIM.getInstance() != null) {
//            InputProvider.ExtendProvider[] ep = {
//                    new CameraInputProvider(RongContext.getInstance()),
//                    new ImageInputProvider(RongContext.getInstance())};
//            // 我需要让他在什么会话类型中的 bar 展示
//            RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, ep);
//        }
        getKeFuPhoneNum();
    }

    private void getKeFuPhoneNum() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.KEFU_CALL);
        xUtilsApi.sendUrl(getApplicationContext(), "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject json = result.optJSONObject("info");
                    ConstantValue.TEL = json.optString("tel");

                }
            }
        });
    }


    // 判断 用户是否登录
    public static boolean checkUserLogin() {
        if (user != null && !"".equals(user.getId())) {
            return true;
        }
        return false;
    }

    // 获取用户的登录userid
    public static String getLoginUserId() {
        if (user == null) {
            return "";
        } else {
            return user.getId();
        }
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

}
