package com.exz.antcargo.activity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/8.
 */

public class MinGoodsInfoBean implements Serializable {

    /**
     * headImg : http://myhd.xzsem.cn/userImg/20160906043801370725901.jpg
     * contact :
     * companyState : -1
     * mobile : 13236027714
     * registrationID : aa23244fdg4d21a
     * idCardNumber :
     * userName :
     * shipperState : -1
     */
    private String headImg;
    private String contact;
    private String companyState;
    private String mobile;
    private String idCardNumber;
    private String userName;
    private String shipperState;
    private String workPlace;
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

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getRongCloudToken() {
        return rongCloudToken;
    }

    public void setRongCloudToken(String rongCloudToken) {
        this.rongCloudToken = rongCloudToken;
    }

    private String rongCloudToken;

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCompanyState(String companyState) {
        this.companyState = companyState;
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

    public void setShipperState(String shipperState) {
        this.shipperState = shipperState;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getContact() {
        return contact;
    }

    public String getCompanyState() {
        return companyState;
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

    public String getShipperState() {
        return shipperState;
    }
}
