package com.shageldi.tazegun;

import java.util.Date;

public class SyryoList {
    String id;
    String gornushi;
    String mocberi;
    String ulanyldy_geldi;
    Date senesi;
    String username;

    public SyryoList(String id, String gornushi, String mocberi, String ulanyldy_geldi, Date senesi, String username) {
        this.id = id;
        this.gornushi = gornushi;
        this.mocberi = mocberi;
        this.ulanyldy_geldi = ulanyldy_geldi;
        this.senesi = senesi;
        this.username = username;
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

    public String getMocberi() {
        return mocberi;
    }

    public void setMocberi(String mocberi) {
        this.mocberi = mocberi;
    }

    public String getUlanyldy_geldi() {
        return ulanyldy_geldi;
    }

    public void setUlanyldy_geldi(String ulanyldy_geldi) {
        this.ulanyldy_geldi = ulanyldy_geldi;
    }

    public Date getSenesi() {
        return senesi;
    }

    public void setSenesi(Date senesi) {
        this.senesi = senesi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
