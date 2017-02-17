package com.exz.antcargo.activity.okhttp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exz.antcargo.R;
import com.exz.antcargo.activity.bean.NewEntity;
import com.exz.antcargo.activity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.Response;


public class OKHttpApi  {
    public static Dialog dialog;
    public static View dialogView;


    public static Dialog makeProgressDialog(Context context) {

        Dialog dialog = new Dialog(context,
                R.style.loading_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    /**
     * 发送http请求
     *
     * @param context    上下文
     * @param withDialog 是否需要dialog
     */
    public static void sendUrl(final Context context, String url,
                               LinkedHashMap<String, String> map, final boolean withDialog, final OKURLSuccessListenter listenter) {
        if (withDialog == true) {
            if (dialog == null) {
                dialog = makeProgressDialog(context);
                dialog.show();
                dialogView = View.inflate(context, R.layout.item_dialog, null);
                RelativeLayout layout = (RelativeLayout) dialogView
                        .findViewById(R.id.rl_bg);// ..


                // main.xml中的ImageView
                ImageView spaceshipImage = (ImageView) dialogView
                        .findViewById(R.id.iv_progress);
                TextView tipTextView = (TextView) dialogView
                        .findViewById(R.id.tv_text);// 提示文字
                // 加载动画
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                        context, R.anim.loading_animation);
                // 使用ImageView显示动画
                spaceshipImage.startAnimation(hyperspaceJumpAnimation);
                dialog.setContentView(dialogView, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT));

            } else {
                dialog.show();
            }
        }
      OkHttpUtils.post().url(url).params(map).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                String result = response.body().string();
                if (withDialog) {
                    dialog.dismiss();
                }
                if (listenter != null) {
                    NewEntity t = JSON.parseObject(result,
                            NewEntity.class);

                    try {
                        JSONObject object = new JSONObject(
                                result);
                        listenter.OnSuccess(t, object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                return result;
            }

            @Override
            public void onError(Call call, Exception ex, int id) {
                if (withDialog) {
                    dialog.dismiss();
                }
                Utils.toast(context, "网络请求出错!" + ex.getMessage() + "\n" + ex.toString());
            }

            @Override
            public void onResponse(Object response, int id) {
                if (withDialog) {
                    dialog.dismiss();
                }
            }
        });
    }




    public interface OKURLSuccessListenter {
        public void OnSuccess(NewEntity content, JSONObject result);

    }
}
