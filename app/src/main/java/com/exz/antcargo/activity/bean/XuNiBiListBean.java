package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/6.
 */

public class XuNiBiListBean {

    /**
     * count : 数量
     * time : 时间
     * type : 赠送 或者 花费,
     */
    private String count;
    private String time;
    private String type;

    public void setCount(String count) {
        this.count = count;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
