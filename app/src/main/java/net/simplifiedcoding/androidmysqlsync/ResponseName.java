package net.simplifiedcoding.androidmysqlsync;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ResponseName implements Serializable {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public ResponseName(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
