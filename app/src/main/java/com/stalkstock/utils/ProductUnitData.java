package com.stalkstock.utils;

public class ProductUnitData {

    public ProductUnitData(String title, String unit, Boolean selected) {
        this.title = title;
        this.unit = unit;
        this.selected = selected;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    String title="";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    String unit="";
    Boolean selected=false;

}
