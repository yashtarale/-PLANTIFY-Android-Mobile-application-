package com.example.plantify;

import androidx.annotation.RequiresPermission;

public class ReadwriteUserDetails {
    public String fullName,dob,gender,mobile;
    //constructor
    //we need empty constructor to get snapshot of data from firebase
    public ReadwriteUserDetails(){};

    public ReadwriteUserDetails(String textDob, String textGender, String textMobile){

        this.dob=textDob;
        this.gender=textGender;
        this.mobile=textMobile;




    }
}
