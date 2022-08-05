package com.shageldi.tazegun;

public class GalanList {
    String id;
    String icon;
    String name;
    String galan_mocberi;

    public GalanList(String id, String icon, String name, String galan_mocberi) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.galan_mocberi = galan_mocberi;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGalan_mocberi() {
        return galan_mocberi;
    }

    public void setGalan_mocberi(String galan_mocberi) {
        this.galan_mocberi = galan_mocberi;
    }
}
