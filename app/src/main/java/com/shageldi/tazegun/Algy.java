package com.shageldi.tazegun;

import java.util.Date;

public class Algy {
    String id;
    String algy;
    String nira;
    Date sene;

    public Algy(String id, String algy, String nira, Date sene) {
        this.id = id;
        this.algy = algy;
        this.nira = nira;
        this.sene = sene;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlgy() {
        return algy;
    }

    public void setAlgy(String algy) {
        this.algy = algy;
    }

    public String getNira() {
        return nira;
    }

    public void setNira(String nira) {
        this.nira = nira;
    }

    public Date getSene() {
        return sene;
    }

    public void setSene(Date sene) {
        this.sene = sene;
    }
}
