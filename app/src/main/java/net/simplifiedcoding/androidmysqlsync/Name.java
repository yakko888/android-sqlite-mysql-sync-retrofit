package net.simplifiedcoding.androidmysqlsync;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Name implements Serializable{

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private int status;

    public Name(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }
}
