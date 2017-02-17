package com.exz.antcargo.activity.bean;

/**
 * Created by pc on 2016/9/20.
 */

public class CheckGoodsInfo {

    /**
     * result : success
     * message : 审核结果提示
     * info : {"accountId":"9","checkState":"2","checkResult":{"area":{"check":"1","value":"泉山区"},"idCardReverse":{"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543642457202.jpg"},"province":{"check":"1","value":"江苏省"},"city":{"check":"1","value":"徐州市"},"idCard":{"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543658082503.jpg"},"contact":{"check":"1","value":"13236027713"},"idCardNumber":{"check":"1","value":"320324199503206548"},"userName":{"check":"1","value":"sundoy"},"idCardPositive":{"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543642457201.jpg"},"message":""}}
     */
    private String result;
    private String message;
    private InfoEntity info;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public class InfoEntity {
        /**
         * accountId : 9
         * checkState : 2
         * checkResult : {"area":{"check":"1","value":"泉山区"},"idCardReverse":{"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543642457202.jpg"},"province":{"check":"1","value":"江苏省"},"city":{"check":"1","value":"徐州市"},"idCard":{"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543658082503.jpg"},"contact":{"check":"1","value":"13236027713"},"idCardNumber":{"check":"1","value":"320324199503206548"},"userName":{"check":"1","value":"sundoy"},"idCardPositive":{"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543642457201.jpg"},"message":""}
         */
        private String accountId;
        private String checkState;
        private CheckResultEntity checkResult;

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public void setCheckState(String checkState) {
            this.checkState = checkState;
        }

        public void setCheckResult(CheckResultEntity checkResult) {
            this.checkResult = checkResult;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getCheckState() {
            return checkState;
        }

        public CheckResultEntity getCheckResult() {
            return checkResult;
        }

        public class CheckResultEntity {
            /**
             * area : {"check":"1","value":"泉山区"}
             * idCardReverse : {"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543642457202.jpg"}
             * province : {"check":"1","value":"江苏省"}
             * city : {"check":"1","value":"徐州市"}
             * idCard : {"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543658082503.jpg"}
             * contact : {"check":"1","value":"13236027713"}
             * idCardNumber : {"check":"1","value":"320324199503206548"}
             * userName : {"check":"1","value":"sundoy"}
             * idCardPositive : {"check":"1","value":"http://myhd.xzsem.cn/shipper/20160902041543642457201.jpg"}
             * message :
             */
            private AreaEntity area;
            private IdCardReverseEntity idCardReverse;
            private ProvinceEntity province;
            private CityEntity city;
            private IdCardEntity idCard;
            private ContactEntity contact;
            private IdCardNumberEntity idCardNumber;
            private UserNameEntity userName;
            private IdCardPositiveEntity idCardPositive;
            private String message;

            public void setArea(AreaEntity area) {
                this.area = area;
            }

            public void setIdCardReverse(IdCardReverseEntity idCardReverse) {
                this.idCardReverse = idCardReverse;
            }

            public void setProvince(ProvinceEntity province) {
                this.province = province;
            }

            public void setCity(CityEntity city) {
                this.city = city;
            }

            public void setIdCard(IdCardEntity idCard) {
                this.idCard = idCard;
            }

            public void setContact(ContactEntity contact) {
                this.contact = contact;
            }

            public void setIdCardNumber(IdCardNumberEntity idCardNumber) {
                this.idCardNumber = idCardNumber;
            }

            public void setUserName(UserNameEntity userName) {
                this.userName = userName;
            }

            public void setIdCardPositive(IdCardPositiveEntity idCardPositive) {
                this.idCardPositive = idCardPositive;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public AreaEntity getArea() {
                return area;
            }

            public IdCardReverseEntity getIdCardReverse() {
                return idCardReverse;
            }

            public ProvinceEntity getProvince() {
                return province;
            }

            public CityEntity getCity() {
                return city;
            }

            public IdCardEntity getIdCard() {
                return idCard;
            }

            public ContactEntity getContact() {
                return contact;
            }

            public IdCardNumberEntity getIdCardNumber() {
                return idCardNumber;
            }

            public UserNameEntity getUserName() {
                return userName;
            }

            public IdCardPositiveEntity getIdCardPositive() {
                return idCardPositive;
            }

            public String getMessage() {
                return message;
            }

            public class AreaEntity {
                /**
                 * check : 1
                 * value : 泉山区
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

            public class IdCardReverseEntity {
                /**
                 * check : 1
                 * value : http://myhd.xzsem.cn/shipper/20160902041543642457202.jpg
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

            public class ProvinceEntity {
                /**
                 * check : 1
                 * value : 江苏省
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

            public class CityEntity {
                /**
                 * check : 1
                 * value : 徐州市
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

            public class IdCardEntity {
                /**
                 * check : 1
                 * value : http://myhd.xzsem.cn/shipper/20160902041543658082503.jpg
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

            public class ContactEntity {
                /**
                 * check : 1
                 * value : 13236027713
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

            public class IdCardNumberEntity {
                /**
                 * check : 1
                 * value : 320324199503206548
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

            public class UserNameEntity {
                /**
                 * check : 1
                 * value : sundoy
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

            public class IdCardPositiveEntity {
                /**
                 * check : 1
                 * value : http://myhd.xzsem.cn/shipper/20160902041543642457201.jpg
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
    }
}
