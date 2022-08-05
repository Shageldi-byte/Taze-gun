package com.shageldi.tazegun;

public class SkladList {
    String id;
    String gornushi;
    String premium;
    String sany;

    public SkladList(String id, String gornushi, String premium, String sany) {
        this.id = id;
        this.gornushi = gornushi;
        this.premium = premium;
        this.sany = sany;
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

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getSany() {
        return sany;
    }

    public void setSany(String sany) {
        this.sany = sany;
    }
}
