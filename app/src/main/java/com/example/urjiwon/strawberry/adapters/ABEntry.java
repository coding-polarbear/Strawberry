package com.example.urjiwon.strawberry.adapters;

import android.net.Uri;

/**
 * Created by verak on 2017-02-15.
 */

public class ABEntry {
    private String name;
    private Uri photo;
    private String times;
    private String message;
    public ABEntry(String _name, String times, Uri _photo ) {
        this.name = _name;
        this.times = times;
        this.photo = _photo;
    }

    public ABEntry(String _name, String message, String times, Uri _photo ) {
        this.name = _name;
        this.times = times;
        this.photo = _photo;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public String getNameE() {
        return name;
    }

    public String getTimeE() {
        return times;
    }

    public Uri getPhotoIdE() {
        return photo;
    }

}
