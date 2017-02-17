package com.exz.antcargo.activity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.exz.antcargo.activity.GoosdInfoDetailActivity;
import com.exz.antcargo.activity.MainActivity;
import com.exz.antcargo.activity.utils.ConstantValue;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(TextUtils.isEmpty(ConstantValue.EXTRA)){
            Intent newIntent = new Intent(context, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
        }else{
            try {
                JSONObject json=new JSONObject(ConstantValue.EXTRA);
                Intent intent1 = new Intent(context, GoosdInfoDetailActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("supplyGoodsId", json.optString("supplyGoodsId"));
                intent1.putExtra("type", "1");//type 1 查看货主 2 编辑货主
                context.startActivity(intent1);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }

}
