package com.shageldi.tazegun;

public class KiciSokSklad {
    String id;
    String gornushi;
    String sany;
    String gowrumi;

    public KiciSokSklad(String id, String gornushi, String sany,String gowrumi) {
        this.id = id;
        this.gornushi = gornushi;
        this.sany = sany;
        this.gowrumi=gowrumi;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGornushi() {
        return gornushi;
    }

    public void setGornushi(String gornushi) {
        this.gornushi = gornushi;
    }

    public String getSany() {
        return sany;
    }

    public void setSany(String sany) {
        this.sany = sany;
    }

    public String getGowrumi() {
        return gowrumi;
    }

    public void setGowrumi(String gowrumi) {
        this.gowrumi = gowrumi;
    }
}
