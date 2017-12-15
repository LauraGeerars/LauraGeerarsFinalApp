package com.example.laurageerars.laurageerarsfinalfinalapp;

import com.google.firebase.database.IgnoreExtraProperties;

// Activity for defining the class Favoriet in Firebase.

@IgnoreExtraProperties
public class Favoriet {
    public String title;
    public String objectNumber;

    public Favoriet(){

    }

    // Create Favoriet object, with title and objectnumber as value which has to be saved
    public Favoriet(String title, String objectNumber) {
        this.title = title;
        this.objectNumber = objectNumber;
    }

    public String getTitle() {
        return title;
    }
}
