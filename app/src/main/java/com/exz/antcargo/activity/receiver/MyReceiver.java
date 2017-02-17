package com.exz.antcargo.activity.receiver;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

import static com.exz.antcargo.activity.utils.ConstantValue.EXTRA;


/**
 * @author yangshuai
 * @version 创建时间：2015-4-13 上午10:07:10 类说明 :自定义Receive接受推送
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle
                    .getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {

            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            Intent i = new Intent(context, MainActivity.class);
            i.putExtras(bundle);
            i.putExtra("message", bundle.getString(JPushInterface.EXTRA_ALERT));
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
            JPushInterface.reportNotificationOpened(context,
                    bundle.getString(JPushInterface.EXTRA_MSG_ID));// 用于上报用户的通知栏被打开

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {

            Log.d(TAG,
                    "[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
                            + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction())) {
            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction()
                    + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }


    // send msg to MainActivity
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void processCustomMessage(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        // 第一步：获取状态通知栏管理：
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 第二步：实例化通知栏构造器NotificationCompat.Builder：

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.logo);

        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
        mBuilder.setDefaults(Notification.DEFAULT_SOUND
                | Notification.FLAG_ONGOING_EVENT);
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.notitfication_layout);
        mBuilder.setContent(rv);// 设置自定义notification布局
        rv.setImageViewResource(R.id.icon, R.drawable.logo);
        rv.setTextViewText(R.id.title, "蚂蚁货的");
        rv.setTextViewText(R.id.text,
                bundle.getString(JPushInterface.EXTRA_MESSAGE));
        Notification notify = mBuilder.build();// API
        mBuilder.setTicker(bundle.getString(JPushInterface.EXTRA_MESSAGE));
        EXTRA=bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent it = new Intent(context, NotificationClickReceiver.class);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.logo));// 设置下拉图标
        mBuilder.setAutoCancel(false);
        PendingIntent pi = PendingIntent.getBroadcast(context, notify.flags,
                it, notify.flags);
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.cancel(notify.flags);
        notify.contentIntent = pi;
        //
        mNotificationManager.notify((int) (System.currentTimeMillis() / 100),
                notify);



    }

}
