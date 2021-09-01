package com.example.renitto.ktu_master.Models;

/**
 * Created by Renitto on 7/31/2016.
 */
public class Important_University_Contacts {
    String name;
    String position;
    String primary_phone_no;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Important_University_Contacts(String name, String position, String primary_phone_no) {
        this.name = name;
        this.position = position;
        this.primary_phone_no = primary_phone_no;
    }

    public String getPrimary_phone_no() {
        return primary_phone_no;
    }

    public void setPrimary_phone_no(String primary_phone_no) {
        this.primary_phone_no = primary_phone_no;
    }
}
