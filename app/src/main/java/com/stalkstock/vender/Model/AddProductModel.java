package com.stalkstock.vender.Model;

import android.widget.ImageView;

public class AddProductModel {
     int  image;

     String nameproduct;
     int editimage;
     String  g;
     String  type;
     String  number;
     String btn1;
     String btn2;

     public AddProductModel(int image, String nameproduct, int editimage, String g, String type, String number, String btn1, String btn2) {
          this.image = image;
          this.nameproduct = nameproduct;
          this.editimage = editimage;
          this.g = g;
          this.type = type;
          this.number = number;
          this.btn1 = btn1;
          this.btn2 = btn2;
     }

     public int getImage() {
          return image;
     }

     public void setImage(int image) {
          this.image = image;
     }

     public int getEditimage() {
          return editimage;
     }

     public void setEditimage(int editimage) {
          this.editimage = editimage;
     }

     public String getNameproduct() {
          return nameproduct;
     }

     public void setNameproduct(String nameproduct) {
          this.nameproduct = nameproduct;
     }

     public String getG() {
          return g;
     }

     public void setG(String g) {
          this.g = g;
     }

     public String getType() {
          return type;
     }

     public void setType(String type) {
          this.type = type;
     }

     public String getNumber() {
          return number;
     }

     public void setNumber(String number) {
          this.number = number;
     }

     public String getBtn1() {
          return btn1;
     }

     public void setBtn1(String btn1) {
          this.btn1 = btn1;
     }

     public String getBtn2() {
          return btn2;
     }

     public void setBtn2(String btn2) {
          this.btn2 = btn2;
     }
}
