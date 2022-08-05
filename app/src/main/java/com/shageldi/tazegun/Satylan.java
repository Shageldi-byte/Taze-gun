package com.shageldi.tazegun;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Satylan {
    String id;
    String tagamy;
    String sany;
    String nira;
    String baha;
    Date sene;
    String gornushi;

    public Satylan(String id, String tagamy, String sany, String nira, String baha, Date sene, String gornushi) {
        this.id = id;
        this.tagamy = tagamy;
        this.sany = sany;
        this.nira = nira;
        this.baha = baha;
        this.sene = sene;
        this.gornushi = gornushi;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagamy() {
        return tagamy;
    }

    public void setTagamy(String tagamy) {
        this.tagamy = tagamy;
    }

    public String getSany() {
        return sany;
    }

    public void setSany(String sany) {
        this.sany = sany;
    }

    public String getNira() {
        return nira;
    }

    public void setNira(String nira) {
        this.nira = nira;
    }

    public String getBaha() {
        return baha;
    }

    public void setBaha(String baha) {
        this.baha = baha;
    }

    public Date getSene() {
        return sene;
    }

    public void setSene(Date sene) {
        this.sene = sene;
    }

    public String getGornushi() {
        return gornushi;
    }

    public void setGornushi(String gornushi) {
        this.gornushi = gornushi;
    }
}
