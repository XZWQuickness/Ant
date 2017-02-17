package com.exz.antcargo.activity.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exz.antcargo.activity.ChoiceLocationActivity;
import com.exz.antcargo.activity.MapActivity;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.LocationMessage;

/**
 * 融云相关监听 事件集合类
 * Created by AMing on 16/1/7.
 * Company RongCloud
 */
public class SealAppContext implements RongIM.ConversationBehaviorListener, RongIM.LocationProvider {
    private Context mContext;

    private static SealAppContext mRongCloudInstance;

    private RongIM.LocationProvider.LocationCallback mLastLocationCallback;

    private static ArrayList<Activity> mActivities;

    public SealAppContext(Context mContext) {
        this.mContext = mContext;
        initListener();
        mActivities = new ArrayList<>();
    }

    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (SealAppContext.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new SealAppContext(context);
                }
            }
        }

    }

    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static SealAppContext getInstance() {
        return mRongCloudInstance;
    }

    /**
     * init 后就能设置的监听
     */
    private void initListener() {
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。
        RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
    }


    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    public RongIM.LocationProvider.LocationCallback getLastLocationCallback() {
        return mLastLocationCallback;
    }

    public void setLastLocationCallback(RongIM.LocationProvider.LocationCallback lastLocationCallback) {
        this.mLastLocationCallback = lastLocationCallback;
    }
    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        if(message.getContent() instanceof LocationMessage){
            Intent intent=new Intent(context, MapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name","地理位置");
            intent.putExtra("location",message.getContent());
            context.startActivity(intent);

        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }

    //选择地址
    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        SealAppContext.getInstance().setLastLocationCallback(locationCallback);
        Intent intent=new Intent(context, ChoiceLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("name","选择位置");
        context.startActivity(intent);
    }
}
