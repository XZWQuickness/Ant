package com.exz.antcargo.activity.fragemt;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.AccountYuEActivity;
import com.exz.antcargo.activity.CarInfoActivity;
import com.exz.antcargo.activity.CarInfoManageActivity;
import com.exz.antcargo.activity.CashDepositPayActivity;
import com.exz.antcargo.activity.DiaLogActivity;
import com.exz.antcargo.activity.IdentityAuditActivity;
import com.exz.antcargo.activity.LiShiZuJiActivity;
import com.exz.antcargo.activity.LoginActivity;
import com.exz.antcargo.activity.SttingActivity;
import com.exz.antcargo.activity.UserInfoActivity;
import com.exz.antcargo.activity.XuNiBiActivity;
import com.exz.antcargo.activity.bean.MineCarInfoBean;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

import static com.exz.antcargo.activity.utils.ConstantValue.CAR_NUMBER_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.CAR_STATE;
import static com.exz.antcargo.activity.utils.ConstantValue.checkResultMessage;


/**
 * Created by pc on 2016/7/14.
 */
@ContentView(R.layout.fragment_min_car)
public class MinCarFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.rl_user_info)
    private RelativeLayout rl_user_info;

    @ViewInject(R.id.rl_stting)
    private RelativeLayout rl_stting;

    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;


    @ViewInject(R.id.ll_xunibi)
    private LinearLayout ll_xunibi;

    @ViewInject(R.id.rl_huihua)
    private RelativeLayout rl_huihua;


    @ViewInject(R.id.ll_shilian_cashdeposit)
    private LinearLayout ll_shilian_cashdeposit; //失联保证金

    @ViewInject(R.id.rl_car_info)
    private RelativeLayout rl_car_info; //车辆信息

    @ViewInject(R.id.rl_lishi_zuji)
    private RelativeLayout rl_lishi_zuji; //历史足迹

    @ViewInject(R.id.rl_car_info_manage)
    private RelativeLayout rl_car_info_manage; //车源信息管理

    @ViewInject(R.id.ll_pay)
    private LinearLayout ll_pay; //车源信息管理


    @ViewInject(R.id.tv_nickname)
    private TextView tv_nickname;

    @ViewInject(R.id.tv_account)
    private TextView tv_account;


    @ViewInject(R.id.tv_rong_message)
    private TextView tv_rong_message;



    @ViewInject(R.id.tv_price)
    private TextView tv_price;

    @ViewInject(R.id.tv_virtualCurrency)
    private TextView tv_virtualCurrency;

    @ViewInject(R.id.tv_margin)
    private TextView tv_margin;

    @ViewInject(R.id.tv_renzheng_car)
    private TextView tv_renzheng_car;


    @ViewInject(R.id.iv_head_phto)
    private ImageView iv_head_phto;

    @ViewInject(R.id.iv_chezhu)
    private ImageView iv_chezhu;

    @ViewInject(R.id.iv_cheliang)
    private ImageView iv_cheliang;


    @ViewInject(R.id.sw)
    private SwipeRefreshLayout mSwipeLayout;

    private MineCarInfoBean bean;
    private String checkResult = "";

    String path_img = "";


    @Override
    public void initView() {

        rl_user_info.setOnClickListener(this);
        rl_stting.setOnClickListener(this);
        ll_xunibi.setOnClickListener(this);
        ll_shilian_cashdeposit.setOnClickListener(this);
        rl_car_info.setOnClickListener(this);
        rl_lishi_zuji.setOnClickListener(this);
        rl_car_info_manage.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        ll_pay.setOnClickListener(this);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.yeelow,
                R.color.blueness,
                R.color.gary);

        rl_huihua.setOnClickListener(this);

        sendImgFriend();
        path_img = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ic_logo.png";
    }

    @Override
    public void initData() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        isPassCheck(Constant.IS_PASS_CHECK_OWNER);
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

    @Subscribe
    public void update(MainSendEvent event) {

        if (event != null && event.getStringMsgData().equals("minCarFragment")) {
            if (!TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId")) && !CAR_STATE.equals("-1")) {

                getCarInfo();
            }else{
                tv_nickname.setText(SPutils.getString(getActivity(),"name"));
                iv_chezhu.setBackgroundResource(R.drawable.chezhuweirenzheng);
                iv_cheliang.setBackgroundResource(R.drawable.cheliangbeiju);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SPutils.getString(getActivity(), "accountId")) && !CAR_STATE.equals("-1")) {
            getCarInfo();
        }else{
            tv_nickname.setText(SPutils.getString(getActivity(),"name"));
        }

    }

    private void getCarInfo() {
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(Constant.MINE_CAR_INFO);
        params.addBodyParameter("AccountId", SPutils.getString(getActivity(), "accountId"));
        xUtilsApi.sendUrl(getActivity(), "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    String json = result.optString("info");
                    bean = JSON.parseObject(json, MineCarInfoBean.class);
                    tv_nickname.setText(bean.getUserName());//真实姓名
                    tv_account.setText(bean.getMobile());//手机号
                    ConstantValue.PRICE = bean.getPrice();
                    ConstantValue.MARGIN = bean.getMargin();

                    RongIM.getInstance()
                            .setCurrentUserInfo(
                                    new UserInfo(
                                            SPutils.getString(getActivity(), "accountId"),
                                            bean.getUserName(),
                                            Uri.parse(bean
                                                    .getHeadImg())));
                    tv_price.setText(ConstantValue.PRICE);//钱
                    tv_margin.setText(ConstantValue.MARGIN);//保证金
                    tv_virtualCurrency.setText(bean.getVirtualCurrency());//虚拟币
                    ImageLoader.getInstance().displayImage(bean.getHeadImg(), iv_head_phto);//头像

                    CAR_STATE = bean.getOwnerState();
                    switch (bean.getOwnerState())//车主认证状态 【-1没有提交资料  0未审核 1审核通过 2拒绝 3禁用】
                    {
                        case "-1":
                            iv_chezhu.setBackgroundResource(R.drawable.chezhuweirenzheng);
                            break;

                        case "0":
                            iv_chezhu.setBackgroundResource(R.drawable.chezhushenhezhong);
                            break;
                        case "1":
                            iv_chezhu.setBackgroundResource(R.mipmap.chezhu_renzhen);
                            break;

                        case "2":
                            iv_chezhu.setBackgroundResource(R.drawable.chezhushenhebeiju);
                            break;

                    }
                    switch (bean.getVehicleState()) { //【-1没有提交资料  0未审核 1审核通过 2拒绝 3禁用】

                        case "-1":
                            iv_cheliang.setBackgroundResource(R.drawable.cheliangbeiju);

                            break;
                        case "0":
                            iv_cheliang.setBackgroundResource(R.drawable.cheliangshenhezhong);
                            break;
                        case "1":
                            iv_cheliang.setBackgroundResource(R.drawable.car_renzhen);

                            break;
                        case "2":
                            iv_cheliang.setBackgroundResource(R.drawable.cheliangshenhebeiju);
                            break;
                        case "3":
                            break;

                    }
                    if (TextUtils.isEmpty(bean.getCertificationVehicleNumber()) || bean.getCertificationVehicleNumber().equals("0")) {//车辆 0 审核中 大于1通过
                        CAR_NUMBER_STATE = "-1";
                        if (CAR_STATE.equals("1")) {
                            checkResultMessage = "车辆审核未通过";
                            popCheck(checkResultMessage);
                        }

                    } else {
                        CAR_NUMBER_STATE = "1";
                        tv_renzheng_car.setText("已认证" + bean.getCertificationVehicleNumber() + "辆车");
                        iv_cheliang.setBackgroundResource(R.drawable.car_renzhen);

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
    public void onClick(View view) {
        CheckState.checkCarInfo(getActivity(), ConstantValue.AccountType);
        switch (view.getId()) { //个人信息
            case R.id.rl_user_info:
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Intent userInfoIntent = new Intent(getActivity(), UserInfoActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("bean", bean);
                userInfoIntent.putExtras(mBundle);
                startActivity(userInfoIntent);

                break;


            case R.id.ll_pay://账户余额
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Intent yueIntnent = new Intent(getActivity(), AccountYuEActivity.class);
                yueIntnent.putExtra("price", bean.getPrice());
                startActivity(yueIntnent);
                break;

            case R.id.ll_xunibi://虚拟币记录
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Utils.startActivity(getActivity(), XuNiBiActivity.class);
                break;

            case R.id.ll_shilian_cashdeposit://失联保证金
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Utils.startActivity(getActivity(), CashDepositPayActivity.class);
                break;

            case R.id.rl_car_info://车辆信息
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Utils.startActivity(getActivity(), CarInfoActivity.class);
                break;

            case R.id.rl_lishi_zuji://历史足迹
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Utils.startActivity(getActivity(), LiShiZuJiActivity.class);
                break;

            case R.id.rl_car_info_manage://车源信息管理

                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                Utils.startActivity(getActivity(), CarInfoManageActivity.class);
                break;
            case R.id.rl_share:
                showShare();
                break;

            case R.id.rl_huihua: //会话列表
                switch (CAR_STATE) {

                    case "-1"://车主未提交资料
                        popCheck(checkResultMessage);
                        return;
                    case "0"://车主审核中
                        PopTitleMes.showPopSingle(getActivity(), checkResultMessage);
                        return;
//                    case "1"://车主审核通过
//                        switch (CAR_NUMBER_STATE) {
//                            case "-1"://公司未提交资料
//                                popCheck(checkResultMessage);
//                                return;
//                        }
//                        break;
                    case "2"://车主审核被拒
                        popCheck(checkResultMessage);
                        return;
                }
                //启动会话列表界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(getActivity());

                break;
            case R.id.rl_stting://设置
                Utils.startActivity(getActivity(), SttingActivity.class);
                break;

        }
    }


    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://myhd666.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://myhd666.com/");
        oks.setImagePath(path_img);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://myhd666.com/");

// 启动分享GUI
        oks.show(getActivity());
    }

    public void sendImgFriend() {
        InputStream abpath = getClass().getResourceAsStream("/assets/ic_logo.png");
        try {
            writetoSDCard(InputStreamToByte(abpath));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param bs 把字节数组写到SDCard中,然后再读取该图片
     */
    public void writetoSDCard(byte[] bs) {
        try {
            FileOutputStream out = new FileOutputStream(new File("/sdcard/ic_logo.png")); //重新命名的图片为test.png.想要获取的图片的路径就是该图片的路径
            try {
                out.write(bs);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    @Override
    public void onRefresh() {
        isPassCheck(Constant.IS_PASS_CHECK_OWNER);
        mSwipeLayout.setRefreshing(false);
    }
 /*
    * 审核结果接口
    *
    * checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
    * */

    private void isPassCheck(String url) {

        String j = "";
        XUtilsApi xUtilsApi = new XUtilsApi();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", SPutils.getString(getActivity(), "name"));
        params.addBodyParameter("password", SPutils.getString(getActivity(), "password"));
        xUtilsApi.sendUrl(getActivity(), "post", params, false, new XUtilsApi.URLSuccessListenter() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void OnSuccess(NewEntity content, JSONObject result) {
                Intent t1 = new Intent(getActivity(), IdentityAuditActivity.class);//完善信、信息
                if (content.getResult().equals(ConstantValue.RESULT)) {
                    JSONObject optJSONObject = result.optJSONObject("info");
                    SPutils.save(getActivity(), "accountId", optJSONObject.optString("accountId"));
                    CAR_STATE = optJSONObject.optString("checkState");
                    switch (optJSONObject.optString("checkState")) {//checkState'-1:未提交审核信息 0未审核(审核中) 1审核通过 2拒绝 3禁用
                        case "-1"://未提交审核信息
                            checkResultMessage = "亲~您没有提交认证信息哦!";
                            popCheck(checkResultMessage);
                            break;
                        case "0"://0未审核(审核中)
                            getCarInfo();
                            checkResultMessage = "亲~正在审核中哦!";
                            break;
                        case "1":
                            getCarInfo();
                            break;

                        case "2"://2拒绝
                            getCarInfo();
                            checkResultMessage = "亲~您提交认证信息没有通过哦!";
                            checkResult = optJSONObject.optString("checkResult");
                            popCheck(checkResultMessage);
                            break;

                        case "3":// 3禁用
                            Utils.startActivity(getActivity(), LoginActivity.class);
                            Intent intent = new Intent(getActivity(), DiaLogActivity.class);
                            intent.putExtra("message", optJSONObject.optJSONObject("checkResult").optString("message"));
                            startActivity(intent);
                            break;

                    }


                } else {
                    Intent intent = new Intent(getActivity(), DiaLogActivity.class);
                    intent.putExtra("message", content.getMessage());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 120:
                ImageLoader.getInstance().displayImage(getActivity().getIntent().getStringExtra("headImg"), iv_head_phto);
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

                switch (CAR_STATE) {
                    case "-1":
                    case "2":
                        Intent t1 = new Intent(getActivity(), IdentityAuditActivity.class);//完善信、信息
                        t1.putExtra("checkResult", checkResult);
                        startActivity(t1);
                        break;
                    case "1":
                        switch (CAR_NUMBER_STATE) {
                            case "-1":
                                Utils.startActivity(getActivity(), CarInfoActivity.class);
                                break;
                        }
                        break;
                }

            }
        });
    }
}
