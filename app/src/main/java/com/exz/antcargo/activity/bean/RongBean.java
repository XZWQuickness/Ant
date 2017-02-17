package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/17.
 */

public class RongBean {

    /**
     * accountId : 账号的id
     * friendName : 好友名字
     * friendPhoto : 好友头像地址
     */
    private String accountId;
    private String friendName;
    private String friendPhoto;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public void setFriendPhoto(String friendPhoto) {
        this.friendPhoto = friendPhoto;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendPhoto() {
        return friendPhoto;
    }
}
