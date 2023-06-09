package com.translator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetroResponse {
    @SerializedName("Response")
    @Expose
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
