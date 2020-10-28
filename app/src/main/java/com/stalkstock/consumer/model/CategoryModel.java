package com.stalkstock.consumer.model;

public class CategoryModel {
    String abc;
    Integer icon;

    public CategoryModel(String abc, Integer icon) {
        this.abc = abc;
        this.icon = icon;
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
