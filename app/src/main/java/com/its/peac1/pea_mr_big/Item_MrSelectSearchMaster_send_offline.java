package com.its.peac1.pea_mr_big;

public class Item_MrSelectSearchMaster_send_offline {
        String ca ;
        String pea_meter ;
        String  name ;
        String address ;
        String ReadDatePlan ;
        String mru  ;
        String  url ;




        public Item_MrSelectSearchMaster_send_offline(String ca, String pea_meter, String name, String address,String ReadDatePlan,String mru ,String url) {
            this.ca = ca ;
            this.pea_meter = pea_meter ;
            this.name = name ;
            this.address =  address;
            this.ReadDatePlan = ReadDatePlan ;
            this.mru = mru ;
            this.url = url ;
        }

        public String geturl() {
            return url;
        }

        public void setSurl(String url) {
            url = url;
        }

        public String getMru() {
            return mru;
        }

        public void setMru(String mru) {
            this.mru = mru;
        }

        public String getCa() {
            return ca;
        }

        public String getPea_meter() {
            return pea_meter;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public void setCa(String ca) {
            this.ca = ca;
        }

        public void setPea_meter(String pea_meter) {
            this.pea_meter = pea_meter;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        public void setReadDatePlan(String readDatePlan) {
            ReadDatePlan = readDatePlan;
        }



        public String getReadDatePlan() {
            return ReadDatePlan;
        }

    }