package com.exz.antcargo.activity.fragemt;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.xutils.x;

/**
 * Created by pc on 2016/8/22.
 */

abstract class BaseDiaLogFragment extends DialogFragment {

    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return x.view().inject(this, inflater, container);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());

        }
        initView();
        initData();
    }
    public abstract void initView();
    public abstract void initData();

}
