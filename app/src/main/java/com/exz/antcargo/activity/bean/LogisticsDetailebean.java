package com.exz.antcargo.activity.bean;

import java.util.List;

/**
 * Created by pc on 2016/9/26.
 */

public class LogisticsDetailebean {


    /**
     * destinationList : [{"address":"南三环欣欣路与长安路交汇处大成物流园内","phone":"13852485020","latitude":"34.206611","name":"铭速物流","longitude":"117.227811"}]
     * name : 徐州光大物流公司
     * originList : [{"address":"颖都大厦","phone":"0516-85645800","latitude":"34.26943","name":"徐州亿网分部","longitude":"117.179822"},{"address":"火车东站","phone":"0516-81115800","latitude":"34.271277","name":"火车站分部","longitude":"117.213901"}]
     * alongRoadList : [{"name":"徐州"},{"name":"南京"},{"name":"苏州"}]
     * content : %E7%89%A9%E6%B5%81%E4%BB%8B%E7%BB%8D
     */
    private List<DestinationListEntity> destinationList;
    private String name;
    private List<OriginListEntity> originList;
    private List<AlongRoadListEntity> alongRoadList;
    private String content;

    public void setDestinationList(List<DestinationListEntity> destinationList) {
        this.destinationList = destinationList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginList(List<OriginListEntity> originList) {
        this.originList = originList;
    }

    public void setAlongRoadList(List<AlongRoadListEntity> alongRoadList) {
        this.alongRoadList = alongRoadList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<DestinationListEntity> getDestinationList() {
        return destinationList;
    }

    public String getName() {
        return name;
    }

    public List<OriginListEntity> getOriginList() {
        return originList;
    }

    public List<AlongRoadListEntity> getAlongRoadList() {
        return alongRoadList;
    }

    public String getContent() {
        return content;
    }

    public class DestinationListEntity {
        /**
         * address : 南三环欣欣路与长安路交汇处大成物流园内
         * phone : 13852485020
         * latitude : 34.206611
         * name : 铭速物流
         * longitude : 117.227811
         */
        private String address;
        private String phone;
        private String latitude;
        private String name;
        private String longitude;

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getName() {
            return name;
        }

        public String getLongitude() {
            return longitude;
        }
    }

    public class OriginListEntity {
        /**
         * address : 颖都大厦
         * phone : 0516-85645800
         * latitude : 34.26943
         * name : 徐州亿网分部
         * longitude : 117.179822
         */
        private String address;
        private String phone;
        private String latitude;
        private String name;
        private String longitude;

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getName() {
            return name;
        }

        public String getLongitude() {
            return longitude;
        }
    }

    public class AlongRoadListEntity {
        /**
         * name : 徐州
         */
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

