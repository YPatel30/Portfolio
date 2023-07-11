// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.model;

import java.io.Serializable;

public class Tag implements Serializable{

    private static final long serialVersionUID = -4411233571260876183L;

    private String nameOfTag;
    private String valueOfTag;

    public Tag(String n, String v) {
        nameOfTag = n;
        valueOfTag = v;
    }

    public String getName() {
        return nameOfTag;
    }

    public String getValue() {
        return valueOfTag;
    }

    public String toString() {
        return nameOfTag + "=" + valueOfTag;
    }

}
