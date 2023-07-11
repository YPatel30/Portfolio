// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{

    private static final long serialVersionUID = 1237725019205013407L;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private String name;

    public Album () {
        photos = null;
        name = "";
    }

    public Album(String name) {
        this.name=name;
        photos=new ArrayList<Photo>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public String toString() {
        return getName();
    }
}
