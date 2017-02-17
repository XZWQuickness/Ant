package com.exz.antcargo.activity;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exz.antcargo.R;
import com.exz.antcargo.activity.utils.ConstantValue;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

/**
 * Created by Xia on 2016/9/16.
 * 融云会话列表
 */
@ContentView(R.layout.activity_huihua_list)
public class HuiHuaListActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.ll_back)
    private LinearLayout ll_back;


    @ViewInject(R.id.tv_title)
    private TextView tv_title;



    @Override
    public void initView() {
        ll_back.setOnClickListener(this);
        tv_title.setText("会话列表");
        RongIM.getInstance().setConversationListBehaviorListener(new MyConversationBehaviorListener());//融云会话列表
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;

        }
    }

    private class MyConversationBehaviorListener implements RongIM.ConversationListBehaviorListener{

        @Override
        public boolean onConversationClick(Context arg0, View arg1,
                                           UIConversation arg2)
        {
            ConstantValue.RongName="";
            return false;
        }

        @Override
        public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
            return false;
        }

        @Override
        public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
            return false;
        }

        @Override
        public boolean onConversationLongClick(Context arg0, View arg1,
                                               UIConversation arg2)
        {
            return false;
        }
    }
}
