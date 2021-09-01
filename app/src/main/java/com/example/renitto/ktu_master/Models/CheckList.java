package com.example.renitto.ktu_master.Models;

/**
 * Created by Renitto on 7/30/2016.
 */
public class CheckList {
    String header;
    String description;

    String date;
    boolean check_status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CheckList(String header, String description, boolean check_status , String date) {
        this.header = header;
        this.description = description;
        this.check_status = check_status;
        this.date = date;
    }

    public boolean isCheck_status() {
        return check_status;
    }

    public void setCheck_status(boolean check_status) {
        this.check_status = check_status;
    }
}
