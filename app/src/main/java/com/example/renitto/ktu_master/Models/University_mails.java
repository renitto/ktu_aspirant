package com.example.renitto.ktu_master.Models;

/**
 * Created by Renitto on 7/31/2016.
 */
public class University_mails {

    String department;
    String email;

    public University_mails(String department, String email) {
        this.department = department;
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
