package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/19.
 */

public class SendPhoto<T> {
    protected String mstrMsg;
    protected int position;
    private T t;
    private boolean b;

    public SendPhoto(String msg) {
        mstrMsg = msg;
    }

    public String getStringMsgData() {
        return mstrMsg;
    }
    public int getPosition() {
        return position;
    }
    public boolean getBoolean() {
        return b;
    }

    public SendPhoto(T t) {
        this.t = t;
    }

    public SendPhoto(T t,String mstrMsg) {
        this.t = t;
        this.mstrMsg = mstrMsg;
    }

    public SendPhoto(T t,String mstrMsg,int position) {
        this.t = t;
        this.mstrMsg = mstrMsg;
        this.position = position;
    }
    public SendPhoto(T t,String mstrMsg,boolean b) {
        this.t = t;
        this.mstrMsg = mstrMsg;
        this.b = b;
    }

    public T getT() {
        return t;
    }
}
