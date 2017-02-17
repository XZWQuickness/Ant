package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/8/26.
 */

public class PopuWindowCheXingBean {

    String name;
    String typeId;

    boolean selectState;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isSelectState() {
        return selectState;
    }

    public void setSelectState(boolean selectState) {
        this.selectState = selectState;
    }
}
