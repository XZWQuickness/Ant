package com.exz.antcargo.activity.utils;

/**
 * @author mmxs
 */
public class MainSendEvent<T> {
    protected String mstrMsg;
    protected int position;
    private T t;
    private boolean b;

    public MainSendEvent(String msg) {
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

    public MainSendEvent(T t) {
        this.t = t;
    }

    public MainSendEvent(T t,String mstrMsg) {
        this.t = t;
        this.mstrMsg = mstrMsg;
    }

    public MainSendEvent(T t,String mstrMsg,int position) {
        this.t = t;
        this.mstrMsg = mstrMsg;
        this.position = position;
    }
    public MainSendEvent(T t,String mstrMsg,boolean b) {
        this.t = t;
        this.mstrMsg = mstrMsg;
        this.b = b;
    }

    public T getT() {
        return t;
    }
}
