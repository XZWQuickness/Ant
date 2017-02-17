package com.exz.antcargo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.exz.antcargo.activity.utils.ConstantValue;
import com.exz.antcargo.activity.utils.MainSendEvent;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler
{

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	private String code = "";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, ConstantValue.APP_ID);
		api.handleIntent(getIntent(), this);
		if ("-1".equals(code))//
		{
			EventBus.getDefault().post(new MainSendEvent("weixin","-1"));
			finish();

		} else if ("-2".equals(code))////用户取消支付后的界面
		{
			EventBus.getDefault().post(new MainSendEvent("weixin","-2"));
			finish();
		} else if ("0".equals(code))
		{
			EventBus.getDefault().post(new MainSendEvent("weixin","0"));
			finish();
		}

	}

	/**
	 * 处理微信发出的向第三方应用请求app message
	 * <p>
	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 * 做点其他的事情，包括根本不打开任何页面
	 */
	public void onGetMessageFromWXReq(WXMediaMessage msg)
	{
		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(
				getPackageName());
		startActivity(iLaunchMyself);
	}

	/**
	 * 处理微信向第三方应用发起的消息
	 * <p>
	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作 回调。
	 * <p>
	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 */
	public void onShowMessageFromWXReq(WXMediaMessage msg)
	{
		if (msg != null && msg.mediaObject != null
				&& (msg.mediaObject instanceof WXAppExtendObject))
		{
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req)
	{
	}

	@Override
	public void onResp(BaseResp resp)
	{

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX)
		{
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setTitle("提示");
			code = String.valueOf(resp.errCode);

			// builder.setMessage(getString(R.string.pay_result_callback_msg,
			// resp.errStr +";code=" + String.valueOf(resp.errCode)));
			// builder.show();
		}
	}
}