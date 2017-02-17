package com.exz.antcargo.activity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/5.
 */

public class MineCarInfoBean implements Serializable {

    /**
     * ownerState : 车主认证状态 【-1没有提交资料  0未审核 1审核通过 2拒绝 3禁用】
     * margin : 保证金为空返零
     * headImg : 用户头像
     * price : 余额为空返回0
     * contact : 联系方式
     * mobile : 登录手机号
     * registrationID : 融云token
     * idCardNumber : 身份证号码
     * userName : 用户姓名
     * virtualCurrency : 虚拟币为空返零
     */
    private String ownerState;
    private String margin;
    private String headImg;
    private String price;
    private String contact;
    private String mobile;
    private String idCardNumber;
    private String userName;
    private String vehicleState;
    private String virtualCurrency;
    private String CertificationVehicleNumber;
    private String rongCloudToken;
    private String workProvince;
    private String workCity;
    private String workArea;

    public String getWorkProvince() {
        return workProvince;
    }

    public void setWorkProvince(String workProvince) {
        this.workProvince = workProvince;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }


    public String getRongCloudToken() {
        return rongCloudToken;
    }

    public void setRongCloudToken(String rongCloudToken) {
        this.rongCloudToken = rongCloudToken;
    }

    public String getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(String vehicleState) {
        this.vehicleState = vehicleState;
    }

    public String getCertificationVehicleNumber() {
        return CertificationVehicleNumber;
    }

    public void setCertificationVehicleNumber(String certificationVehicleNumber) {
        CertificationVehicleNumber = certificationVehicleNumber;
    }

    public void setOwnerState(String ownerState) {
        this.ownerState = ownerState;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setVirtualCurrency(String virtualCurrency) {
        this.virtualCurrency = virtualCurrency;
    }

    public String getOwnerState() {
        return ownerState;
    }

    public String getMargin() {
        return margin;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getPrice() {
        return price;
    }

    public String getContact() {
        return contact;
    }

    public String getMobile() {
        return mobile;
    }


    public String getIdCardNumber() {
        return idCardNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getVirtualCurrency() {
        return virtualCurrency;
    }
}
