package com.example.deeppatel.car_rerntal.Customer;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Random;

public class Customer implements Serializable {

    private String customerName;
    private String licenseNo;
    private int color;

    public Customer(String customerName, String licenseNo) {
        this.customerName = customerName;
        this.licenseNo = licenseNo;
        this.color = getRandColor();
    }

    public int getColor() {
        return color;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public static int getRandColor(){

        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

    }

}
