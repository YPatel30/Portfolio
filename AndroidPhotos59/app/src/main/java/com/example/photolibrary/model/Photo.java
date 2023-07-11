// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable{

    private static final long serialVersionUID = 759770130392048280L;
    private String image;
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public ArrayList<Tag> getTags(){
        return tags;
    }

    public Photo(String image){
        this.image = image;
    }

    public String getImage () {
        return image;
    }
}
