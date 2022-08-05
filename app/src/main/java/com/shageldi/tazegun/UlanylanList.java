package com.shageldi.tazegun;

public class UlanylanList {
    String id;
    String bal_tagamy;
    String gornushi;
    String mocberi;
    String senesi;
    String username;

    public UlanylanList(String id, String bal_tagamy, String gornushi, String mocberi, String senesi, String username) {
        this.id = id;
        this.bal_tagamy = bal_tagamy;
        this.gornushi = gornushi;
        this.mocberi = mocberi;
        this.senesi = senesi;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBal_tagamy() {
        return bal_tagamy;
    }

    public void setBal_tagamy(String bal_tagamy) {
        this.bal_tagamy = bal_tagamy;
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

    public String getSenesi() {
        return senesi;
    }

    public void setSenesi(String senesi) {
        this.senesi = senesi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
