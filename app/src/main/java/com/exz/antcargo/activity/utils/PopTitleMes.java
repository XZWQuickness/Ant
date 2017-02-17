package com.exz.antcargo.activity.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exz.antcargo.R;

/**
 * Created by pc on 2016/9/19.
 */

public class PopTitleMes {


    public static void showPopSingle(Activity activity, String message) {
        final AlertDialog dlgsingle = new AlertDialog.Builder(activity)
                .create();
        View viewtwo = LayoutInflater.from(activity).inflate(
                R.layout.activiy_dialog, null);
        dlgsingle.setView(activity.getLayoutInflater().inflate(
                R.layout.activiy_dialog, null));
        dlgsingle.show();
        dlgsingle.getWindow().setContentView(viewtwo);
        TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView confirm = (Button) viewtwo.findViewById(R.id.confirm);
        title.setText(message);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgsingle.dismiss();
            }
        });
    }


    public static void showPopTwo(Activity activity, String message) {
        final AlertDialog dlgtwo = new AlertDialog.Builder(activity)
                .create();
        View viewtwo = LayoutInflater.from(activity).inflate(
                R.layout.pop_check, null);
        dlgtwo.setView(activity.getLayoutInflater().inflate(
                R.layout.pop_check, null));
        dlgtwo.show();
        dlgtwo.getWindow().setContentView(viewtwo);
        TextView queding = (TextView) viewtwo.findViewById(R.id.queding);
        TextView title = (TextView) viewtwo.findViewById(R.id.title);
        TextView quxiao = (TextView) viewtwo.findViewById(R.id.quxiao);
        title.setText(message);
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dlgtwo.dismiss();

            }
        });
        queding.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View arg0) {
                                       }
                                   }

        );
    }


}
