package com.example.laurageerars.laurageerarsfinalfinalapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Favoriet {
    public String title;
    public String objectNumber;

    public Favoriet(){

    }

    //Create Favoriet object, with title as value which has to be saved
    public Favoriet(String title, String objectNumber) {
        this.title = title;
        this.objectNumber = objectNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getObjectNumber() {
        return objectNumber;
    }
}
