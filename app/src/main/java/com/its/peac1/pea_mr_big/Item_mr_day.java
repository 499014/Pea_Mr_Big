package com.its.peac1.pea_mr_big;



public class Item_mr_day {
    private String ReadDatePlan;
    private String total;
    private String readed;
    private String notread;
    private String Status;

    public Item_mr_day(String readDatePlan, String total, String readed, String notread,String Status) {
        ReadDatePlan = readDatePlan;
        this.total = total;
        this.readed = readed;
        this.notread = notread;
        this.Status = Status ;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getReadDatePlan() {
        return ReadDatePlan;
    }

    public String getTotal() {
        return total;
    }

    public String getReaded() {
        return readed;
    }

    public String getNotread() {
        return notread;
    }

    public void setReadDatePlan(String readDatePlan) {
        ReadDatePlan = readDatePlan;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }

    public void setNotread(String notread) {
        this.notread = notread;
    }
}
