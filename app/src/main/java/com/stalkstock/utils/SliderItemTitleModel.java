package com.stalkstock.utils;

public class SliderItemTitleModel {

    String title="";

    public SliderItemTitleModel(String title, String isSelected) {
        this.title = title;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    String isSelected="";
}
