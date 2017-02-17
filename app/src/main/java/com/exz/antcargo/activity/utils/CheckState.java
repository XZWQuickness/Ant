package com.exz.antcargo.activity.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.activity.bean.CheckCarInfo;
import com.exz.antcargo.activity.bean.CheckGoodsInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.exz.antcargo.activity.utils.ConstantValue.CAR_NUMBER_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.CAR_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.GOODS_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResult;

/**
 * Created by pc on 2016/9/20.
 */

public class CheckState {

    public static void checkCarInfo(Activity activity, String type) {
        if (TextUtils.isEmpty(SPutils.getString(activity, "name")) && TextUtils.isEmpty(SPutils.getString(activity, "password"))) {
            return;
        }
        if (type.equals("2")) { //车主

            XUtilsApi xUtilsApi = new XUtilsApi();
            RequestParams params = new RequestParams(Constant.IS_PASS_CHECK_OWNER);
            params.addBodyParameter("mobile", SPutils.getString(activity, "name"));
            params.addBodyParameter("password", SPutils.getString(activity, "password"));

            x.http().post(params, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                cllBack.CheckCarCallBack(JSON.parseObject(result, CheckCarInfo.class));

                    CheckCarInfo checkCarInfo = JSON.parseObject(result, CheckCarInfo.class);

//                    checkCarInfo.getInfo()

                    CAR_STATE = checkCarInfo.getInfo().getCheckState();
                    if (TextUtils.isEmpty(checkCarInfo.getInfo().getThroughVehicle()) || checkCarInfo.getInfo().getThroughVehicle().equals("0")) {
                        CAR_NUMBER_STATE = "-1";
                    } else {
                        CAR_NUMBER_STATE = "1";
                    }
                    switch (checkCarInfo.getInfo().getCheckState()) {
                        case "-1"://未提交审核信息
                            ConstantValue.checkResultMessage = "亲~您没有提交认证信息哦!";
                            break;
                        case "0"://0未审核(审核中)
                            ConstantValue.checkResultMessage = "亲~正在审核中哦!";
                            break;

                        case "1":
                            break;
                        case "2"://2拒绝
                            ConstantValue.checkResultMessage = "亲~您提交认证信息没有通过哦!";
                            checkResult = checkCarInfo.getInfo().getCheckResult().toString();
                            break;
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

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        } else { //货主
            XUtilsApi xUtilsApi = new XUtilsApi();
            RequestParams params = new RequestParams(Constant.IS_PASS_CHECK);
            params.addBodyParameter("mobile", SPutils.getString(activity, "name"));
            params.addBodyParameter("password", SPutils.getString(activity, "password"));

            x.http().post(params, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                cllBack.CheckCarCallBack(JSON.parseObject(result, CheckCarInfo.class));

                    CheckGoodsInfo checkCarInfo = JSON.parseObject(result, CheckGoodsInfo.class);

                    GOODS_STATE = checkCarInfo.getInfo().getCheckState();
                    switch (checkCarInfo.getInfo().getCheckState()) {
                        case "-1"://未提交审核信息
                            ConstantValue.checkResultMessage = "亲~您没有提交认证信息哦!";
                            break;
                        case "0"://0未审核(审核中)
                            ConstantValue.checkResultMessage = "亲~正在审核中哦!";
                            break;

                        case "1":
                            break;
                        case "2"://2拒绝
                            ConstantValue.checkResultMessage = "亲~您提交认证信息没有通过哦!";
                            checkResult = checkCarInfo.getInfo().getCheckResult().toString();
                            break;
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

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }

    }

    public interface CallBack {
        void CheckCarCallBack(CheckCarInfo bean);
    }
}
