package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/7.
 */

public class BalanceListBean {

    /**
     * tyep : 1金额加 -1金额减
     * price : 充值金额
     * time : 时间
     */
    private String price;
    private String time;
    /**
     * reason :
     * state : 1
     */
    private String reason;
    private String state;


    public void setPrice(String price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public String getState() {
        return state;
    }
}
