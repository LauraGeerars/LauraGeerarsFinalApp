package com.example.laurageerars.laurageerarsfinalfinalapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Favoriet {
    public String title;

    public Favoriet(){

    }

    //Create Favoriet object, with title as value which has to be saved
    public Favoriet(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
