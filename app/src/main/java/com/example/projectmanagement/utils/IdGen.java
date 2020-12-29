package com.example.projectmanagement.utils;

import com.example.projectmanagement.pages.login.SignUpActivity;

import static com.example.projectmanagement.models.Models.PropertyInfo.PROPERTY_SUR;

public interface IdGen {
    static String getPropertyId(String name) {
        return name + "-" + SignUpActivity.time + "-" + PROPERTY_SUR;
    }
}
