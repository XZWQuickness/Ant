package com.exz.antcargo.activity.fragemt;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.BalanceListActivity;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.GongSiRenZhengActivity;
import com.exz.antcargo.activity.GoodsManageActivity;
import com.exz.antcargo.activity.IdentityAuditActivity;
import com.exz.antcargo.activity.LiShiZuJiGoodsActivity;
import com.exz.antcargo.activity.LoginActivity;
import com.exz.antcargo.activity.ShippeAuditActivity;
import com.exz.antcargo.activity.SttingActivity;
import com.exz.antcargo.activity.UserInfoActivity;
import com.exz.antcargo.activity.bean.MinGoodsInfoBean;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.CheckState;
import com.exz.antcargo.activity.utils.Constant;
import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.exz.antcargo.activity.utils.PopTitleMes;
import com.exz.antcargo.activity.utils.SPutils;
import com.exz.antcargo.activity.utils.Utils;
import com.exz.antcargo.activity.utils.XUtilsApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

import static com.exz.antcargo.activity.utils.ConstantValue.COMPANY_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.GOODS_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResult;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResultMessage;


/**
 * Created by pc on 2016/7/14.
 */
@ContentView(R.layout.fragment_min_goods)
public class MinGoodsFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.rl_user_info)
    private RelativeLayout rl_user_info;

    @ViewInject(R.id.rl_stting)
    private RelativeLayout rl_stting;

    @ViewInject(R.id.rl_lishi_zuji)
    private RelativeLayout rl_lishi_zuji; //历史足迹

    @ViewInject(R.id.rl_car_info_manage)
    private RelativeLayout rl_car_info_manage; //货源信息管理

    @ViewInject(R.id.rl_gognsi_renzheng)
    private RelativeLayout rl_gognsi_renzheng; //公司认证


    @ViewInject(R.id.tv_nickname)
    private TextView tv_nickname;

    @ViewInject(R.id.tv_account)
    private TextView tv_account;

    @ViewInject(R.id.tv_rong_message)
    private TextView tv_rong_message;


    @ViewInject(R.id.iv_head_phto)
    private ImageView iv_head_phto;

    @ViewInject(R.id.iv_huozhu)
    private ImageView iv_huozhu;


    @ViewInject(R.id.iv_next)
    private ImageView iv_next;



    @ViewInject(R.id.iv_gongsi)
    private ImageView iv_gongsi;

    private MinGoodsInfoBean bean;

    @ViewInject(R.id.tv_gongsi_state)
    private TextView tv_gongsi_state;


    @ViewInject(R.id.sw)
    private SwipeRefreshLayout mSwipeLayout;


    @ViewInject(R.id.rl_huihua)
    private RelativeLayout rl_huihua;


    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        rl_huihua.setOnClickListener(this);
        rl_user_info.setOnClickListener(this);
        rl_stting.setOnClickListener(this);
        rl_lishi_zuji.setOnClickListener(this);
        rl_car_info_manage.setOnClickListener(this);
        rl_gognsi_renzheng.setOnClickListener(this);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.yeelow,
                R.color.blueness,
                R.color.gary);
        isPassCheck(Constant.IS_PASS_CHECK);//货主认证
    }

    @Subscribe
    public void update(MainSendEvent event) {

        if (event != null && event.getStringMsgData().equals("minGoodsFragment") && !TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId")) && !GOODS_STATE.equals("-1")) {
            getGoodsInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId")) && !GOODS_STATE.equals("-1")) {
            getGoodsInfo();
        } else {
            tv_nickname.setText(SPutils.getString(getActivity(), "name"));
            iv_huozhu.setBackgroundResource(R.drawable.huozhuweirenzheng);
            iv_gongsi.setBackgroundResource(R.drawable.gongsiweirenzheng);
        }
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//
//        isPassCheck(Constant.IS_PASS_CHECK);//货主认证
//    }

    private void getGoodsInfo() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.MINE_GOODS_INFO);
        params.addBodyParameter("AccountId", SPutils.getString(getActivity(), "accountId"));

        xUtilsApi.sendUrl(getActivity(), "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    bean = JSON.parseObject(json, MinGoodsInfoBean.class);
                    tv_nickname.setText(bean.getUserName());//真实姓名
                    tv_account.setText(bean.getMobile());//手机号
                    ImageLoader.getInstance().displayImage(bean.getHeadImg(), iv_head_phto);//头像
                    RongIM.getInstance()
                            .setCurrentUserInfo(
                                    new UserInfo(
                                            SPutils.getString(getActivity(), "accountId"),
                                            bean.getUserName(),
                                            Uri.parse(bean
                                                    .getHeadImg())));
                    GOODS_STATE = bean.getShipperState();
                    switch (bean.getShipperState())//车主认证状态 【-1没有提交资料  0未审核 1审核通过 2拒绝 3禁用】
                    {
                        case "-1":
                            iv_huozhu.setBackgroundResource(R.drawable.huozhuweirenzheng);
                            break;

                        case "0":
                            iv_huozhu.setBackgroundResource(R.drawable.huozhushenhezhong);
                            break;
                        case "1":
                            iv_huozhu.setBackgroundResource(R.mipmap.huozhu_renzhen);
                            break;

                        case "2":
                            iv_huozhu.setBackgroundResource(R.drawable.huozhushenhebeiju);
                            break;


                    }
                    COMPANY_STATE = bean.getCompanyState();
                    switch (bean.getCompanyState()) {//【-1没有提交资料 0未审核 1审核通过 2拒绝 3禁用】
                        case "-1":
                            iv_gongsi.setBackgroundResource(R.drawable.gongsiweirenzheng);
                            tv_gongsi_state.setText("未认证");
                            checkResultMessage = "亲~您没有提交公司认证信息哦!";
//                            if (bean.getShipperState().equals("1")) {
//
//                                popCheck(checkResultMessage);
//                            }
                            break;
                        case "0":

                            iv_gongsi.setBackgroundResource(R.drawable.gonsishenhezhong);
                            tv_gongsi_state.setText("审核中");
                            checkResultMessage = "亲~您的提交的公司信息正在审核中哦!";
                            break;
                        case "1":
                            iv_gongsi.setBackgroundResource(R.drawable.gongsirenzhengguo);
                            tv_gongsi_state.setText("已认证");
                            iv_next.setVisibility(View.INVISIBLE);
                            break;
                        case "2":
                            iv_gongsi.setBackgroundResource(R.drawable.gongsishenhebeiju);
                            tv_gongsi_state.setText("审核被拒");
                            checkResultMessage = "亲~您提交认证公司信息没有通过哦!";
//                            if (bean.getShipperState().equals("1")) {
//                                popCheck(checkResultMessage);
//                            }
                            break;
                    }

                } else {
                    Intent intent = new Intent(getActivity(), DiaLogActivity.class)
                            .putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initData() {

//获取融云未读消息数量
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
    }

    //获取融云未读消息数量
    public RongIM.OnReceiveUnreadCountChangedListener mCountListener = new RongIM.OnReceiveUnreadCountChangedListener() {
        @Override
        public void onMessageIncreased(int count) {
            if (count > 0) {

                tv_rong_message.setText(count + "未读  ");
            } else {
                tv_rong_message.setText("");
            }
        }
    };


/*
    * 审核结果接口
    *
    * checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
    * */

    private void isPassCheck(String url) {

        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", SPutils.getString(getActivity(), "name"));
        params.addBodyParameter("password", SPutils.getString(getActivity(), "password"));
        xUtilsApi.sendUrl(getActivity(), "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                Intent intent = new Intent(getActivity(), DiaLogActivity.class);
                Intent t1 = new Intent(getActivity(), IdentityAuditActivity.class);//完善信、信息
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    SPutils.save(getActivity(), "accountId", optJSONObject.optString("accountId"));

                    GOODS_STATE = optJSONObject.optString("checkState");
                    switch (optJSONObject.optString("checkState")) {//checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
                        case "-1"://未提交审核信息
                            checkResultMessage = "亲~您没有提交认证信息哦!";
                            popCheck(checkResultMessage);
                            break;
                        case "0"://0未审核(审核中)
                            getGoodsInfo();
                            checkResultMessage = "亲~正在审核中哦!";
                            break;
                        case "1":
                            getGoodsInfo();
                            break;

                        case "2"://2拒绝
                            getGoodsInfo();
                            checkResult = optJSONObject.optString("checkResult");
                            checkResultMessage = "亲~您提交认证信息没有通过哦!";
                            popCheck(checkResultMessage);
                            break;

                        case "3":// 3禁用
                            Utils.startActivity(getActivity(), LoginActivity.class);
                            intent.putExtra("message", optJSONObject.optJSONObject("checkResult").optString("message"));
                            startActivity(intent);
                            break;

                    }


                } else {

                    intent.putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        CheckState.checkCarInfo(getActivity(), ConstantValue.AccountType);
        Intent intent = new Intent(getActivity(), BalanceListActivity.class);
        switch (view.getId()) { //个人信息
            case R.id.rl_user_info:
                switch (GOODS_STATE) {

                    case "-1"://货主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://货主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
                    case "1"://货主审核通过
                        break;
                    case "2"://货主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Intent userInfoIntent = new Intent(getActivity(), UserInfoActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("beanGoods", bean);
                userInfoIntent.putExtras(mBundle);
                startActivity(userInfoIntent);
                break;

            case R.id.rl_gognsi_renzheng://公司认证
                if (!tv_gongsi_state.getText().toString().trim().equals("已认证") && !tv_gongsi_state.getText().toString().trim().equals("审核中")) {
                    Intent gongsi = new Intent(getActivity(), GongSiRenZhengActivity.class);
                    gongsi.putExtra("state", bean == null ? "" : bean.getCompanyState());
                    startActivityForResult(gongsi, 100);
                }

                break;


            case R.id.rl_lishi_zuji://历史足迹

                switch (GOODS_STATE) {

                    case "-1"://货主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://货主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
                    case "1"://货主审核通过
//                        switch (COMPANY_STATE){
//                            case "-1"://公司未提交资料
//                            case "2":
//                                popCheck(checkResultMessage);
//                                return;
//                        }
                        break;
                    case "2"://货主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }

                Utils.startActivity(getActivity(), LiShiZuJiGoodsActivity.class);
                break;

            case R.id.rl_car_info_manage://货源信息管理
                switch (GOODS_STATE) {

                    case "-1"://货主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://货主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
                    case "1"://货主审核通过
//                        switch (COMPANY_STATE){
//                            case "-1"://公司未提交资料
//                            case "2":
//                                popCheck(checkResultMessage);
//                                return;
//                        }
                        break;
                    case "2"://货主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Utils.startActivity(getActivity(), GoodsManageActivity.class);
                break;
            case R.id.rl_huihua:
                switch (GOODS_STATE) {

                    case "-1"://货主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://货主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
                    case "1"://货主审核通过
//                        switch (COMPANY_STATE){
//                            case "-1"://公司未提交资料
//                            case "2":
//                                popCheck(checkResultMessage);
//                                return;
//                        }
                        break;
                    case "2"://货主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                //启动聚合会话列表界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(getActivity());
                break;
            case R.id.rl_stting://设置
                Utils.startActivity(getActivity(), SttingActivity.class);
                break;

        }
    }

    @Override
    public void onRefresh() {
        isPassCheck(Constant.IS_PASS_CHECK);//货主认证
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                initData();
                break;
        }
    }


    private void popCheck(String message) {

        final AlertDialog dlgtwo = new AlertDialog.Builder(getActivity())
                .create();
        View viewtwo = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_check, null);
        dlgtwo.setView(getActivity().getLayoutInflater().inflate(
                R.layout.pop_check, null));
        dlgtwo.show();
        dlgtwo.getWindow().setContentView(viewtwo);
        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
        TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
        title.setText(message);
        queding.setText("认证");
        quxiao.setText("暂不");
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dlgtwo.dismiss();

            }
        });
        queding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dlgtwo.dismiss();
                switch (GOODS_STATE) {
                    case "-1":
                    case "2":
                        Intent t = new Intent(getActivity(), ShippeAuditActivity.class);//完善信、信息
                        t.putExtra("checkResult", checkResult);
                        startActivity(t);
                        break;
                    case "1":
//                        switch (COMPANY_STATE) {
//                            case "-1":
//                            case "2":
//                                Intent gongsi = new Intent(getActivity(), GongSiRenZhengActivity.class);
//                                gongsi.putExtra("state", bean.getCompanyState());
//                                startActivityForResult(gongsi, 100);
//                                break;
//                        }
                        break;

                }


            }
        });
    }
}
