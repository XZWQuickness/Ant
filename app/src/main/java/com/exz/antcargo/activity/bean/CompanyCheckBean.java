package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/8.
 */

public class CompanyCheckBean {

    /**
     * businessLicense : {"check":"2","value":"http://myhd.xzsem.cn/shipper/20160908054318051953302.jpg"}
     * companyName : {"check":"2","value":"xsa"}
     * companyAddress : {"check":"2","value":"sds"}
     * doorFirst : {"check":"2","value":"http://myhd.xzsem.cn/shipper/20160908054318051953301.jpg"}
     * position : {"check":"2","value":"sdds"}
     * message :
     * businessCard : {"check":"2","value":"http://myhd.xzsem.cn/shipper/20160908054318051953303.jpg"}
     */
    private BusinessLicenseEntity businessLicense;
    private CompanyNameEntity companyName;
    private CompanyAddressEntity companyAddress;
    private DoorFirstEntity doorFirst;
    private PositionEntity position;
    private String message;
    private BusinessCardEntity businessCard;

    public void setBusinessLicense(BusinessLicenseEntity businessLicense) {
        this.businessLicense = businessLicense;
    }

    public void setCompanyName(CompanyNameEntity companyName) {
        this.companyName = companyName;
    }

    public void setCompanyAddress(CompanyAddressEntity companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setDoorFirst(DoorFirstEntity doorFirst) {
        this.doorFirst = doorFirst;
    }

    public void setPosition(PositionEntity position) {
        this.position = position;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBusinessCard(BusinessCardEntity businessCard) {
        this.businessCard = businessCard;
    }

    public BusinessLicenseEntity getBusinessLicense() {
        return businessLicense;
    }

    public CompanyNameEntity getCompanyName() {
        return companyName;
    }

    public CompanyAddressEntity getCompanyAddress() {
        return companyAddress;
    }

    public DoorFirstEntity getDoorFirst() {
        return doorFirst;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public String getMessage() {
        return message;
    }

    public BusinessCardEntity getBusinessCard() {
        return businessCard;
    }

    public class BusinessLicenseEntity {
        /**
         * check : 2
         * value : http://myhd.xzsem.cn/shipper/20160908054318051953302.jpg
         */
        private String check;
        private String value;

        public void setCheck(String check) {
            this.check = check;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCheck() {
            return check;
        }

        public String getValue() {
            return value;
        }
    }

    public class CompanyNameEntity {
        /**
         * check : 2
         * value : xsa
         */
        private String check;
        private String value;

        public void setCheck(String check) {
            this.check = check;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCheck() {
            return check;
        }

        public String getValue() {
            return value;
        }
    }

    public class CompanyAddressEntity {
        /**
         * check : 2
         * value : sds
         */
        private String check;
        private String value;

        public void setCheck(String check) {
            this.check = check;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCheck() {
            return check;
        }

        public String getValue() {
            return value;
        }
    }

    public class DoorFirstEntity {
        /**
         * check : 2
         * value : http://myhd.xzsem.cn/shipper/20160908054318051953301.jpg
         */
        private String check;
        private String value;

        public void setCheck(String check) {
            this.check = check;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCheck() {
            return check;
        }

        public String getValue() {
            return value;
        }
    }

    public class PositionEntity {
        /**
         * check : 2
         * value : sdds
         */
        private String check;
        private String value;

        public void setCheck(String check) {
            this.check = check;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCheck() {
            return check;
        }

        public String getValue() {
            return value;
        }
    }

    public class BusinessCardEntity {
        /**
         * check : 2
         * value : http://myhd.xzsem.cn/shipper/20160908054318051953303.jpg
         */
        private String check;
        private String value;

        public void setCheck(String check) {
            this.check = check;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCheck() {
            return check;
        }

        public String getValue() {
            return value;
        }
    }
}
