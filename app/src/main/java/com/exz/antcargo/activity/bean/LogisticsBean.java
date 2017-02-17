package com.exz.antcargo.activity.bean;

import java.io.Serializable;

/**
 * Created by pc on 2016/9/13.
 */

public class LogisticsBean implements Serializable {


    /**
     * img : http://myhd.xzsem.cn/logisticsImg/20160926151816.png
     * company_contact : 13852485020
     * address : 徐州市淮海西路120号
     * lid : 3
     * origin : 河北省石家庄市长安区
     * destination : 上海上海市黄浦区
     * name : 徐州光大物流公司
     */
    private String img;
    private String company_contact;
    private String address;
    private String lid;
    private String origin;
    private String destination;
    private String name;

    public void setImg(String img) {
        this.img = img;
    }

    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public String getCompany_contact() {
        return company_contact;
    }

    public String getAddress() {
        return address;
    }

    public String getLid() {
        return lid;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }
}
